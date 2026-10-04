package com.migratrack.export.xlsx

/**
 * Writes a ZIP archive with uncompressed (STORED) entries.
 * Kotlin/Native has no java.util.zip, and an XLSX package does not need compression to open.
 */
internal class StoredZip {
    private class Entry(val nameBytes: ByteArray, val data: ByteArray, val crc: Int, val offset: Int) {
        // Bit 11 marks a UTF-8 name; ASCII names leave it clear, as most writers do.
        val flags: Int = if (nameBytes.any { it < 0 }) FLAG_UTF8_NAMES else 0
    }

    private val out = ByteSink()
    private val entries = mutableListOf<Entry>()

    fun add(path: String, data: ByteArray) {
        val entry = Entry(path.encodeToByteArray(), data, Crc32.of(data), out.size)
        out.int(LOCAL_HEADER)
        out.short(VERSION_NEEDED)
        out.short(entry.flags)
        out.short(METHOD_STORED)
        out.short(DOS_TIME)
        out.short(DOS_DATE)
        out.int(entry.crc)
        out.int(data.size) // compressed size
        out.int(data.size) // uncompressed size
        out.short(entry.nameBytes.size)
        out.short(0) // extra field length
        out.bytes(entry.nameBytes)
        out.bytes(data)
        entries += entry
    }

    fun finish(): ByteArray {
        val directoryOffset = out.size
        for (entry in entries) {
            out.int(CENTRAL_HEADER)
            out.short(VERSION_NEEDED) // version made by
            out.short(VERSION_NEEDED)
            out.short(entry.flags)
            out.short(METHOD_STORED)
            out.short(DOS_TIME)
            out.short(DOS_DATE)
            out.int(entry.crc)
            out.int(entry.data.size)
            out.int(entry.data.size)
            out.short(entry.nameBytes.size)
            out.short(0) // extra field length
            out.short(0) // comment length
            out.short(0) // disk number
            out.short(0) // internal attributes
            out.int(0) // external attributes
            out.int(entry.offset)
            out.bytes(entry.nameBytes)
        }
        val directorySize = out.size - directoryOffset
        out.int(END_OF_DIRECTORY)
        out.short(0) // this disk
        out.short(0) // disk with the directory
        out.short(entries.size)
        out.short(entries.size)
        out.int(directorySize)
        out.int(directoryOffset)
        out.short(0) // comment length
        return out.toByteArray()
    }

    private companion object {
        const val LOCAL_HEADER = 0x04034b50
        const val CENTRAL_HEADER = 0x02014b50
        const val END_OF_DIRECTORY = 0x06054b50
        const val VERSION_NEEDED = 20
        const val FLAG_UTF8_NAMES = 0x0800
        const val METHOD_STORED = 0

        // 1980-01-01 00:00, the earliest DOS timestamp; real file times add nothing to an export.
        const val DOS_TIME = 0
        const val DOS_DATE = (0 shl 9) or (1 shl 5) or 1
    }
}

/** Little-endian byte buffer. */
internal class ByteSink {
    private var buffer = ByteArray(8 * 1024)
    var size: Int = 0
        private set

    fun short(value: Int) {
        ensure(2)
        buffer[size++] = value.toByte()
        buffer[size++] = (value shr 8).toByte()
    }

    fun int(value: Int) {
        ensure(4)
        buffer[size++] = value.toByte()
        buffer[size++] = (value shr 8).toByte()
        buffer[size++] = (value shr 16).toByte()
        buffer[size++] = (value shr 24).toByte()
    }

    fun bytes(data: ByteArray) {
        ensure(data.size)
        data.copyInto(buffer, size)
        size += data.size
    }

    fun toByteArray(): ByteArray = buffer.copyOf(size)

    private fun ensure(extra: Int) {
        if (size + extra > buffer.size) buffer = buffer.copyOf(maxOf(buffer.size * 2, size + extra))
    }
}

internal object Crc32 {
    private val TABLE = IntArray(256) { n ->
        var c = n
        repeat(8) { c = if (c and 1 != 0) (c ushr 1) xor 0xEDB88320.toInt() else c ushr 1 }
        c
    }

    fun of(data: ByteArray): Int {
        var crc = -1
        for (byte in data) crc = TABLE[(crc xor byte.toInt()) and 0xFF] xor (crc ushr 8)
        return crc.inv()
    }
}
