package com.migratrack.export

import com.migratrack.export.xlsx.Cell
import com.migratrack.export.xlsx.Column
import com.migratrack.export.xlsx.ReportWorkbook
import com.migratrack.export.xlsx.Sheet
import com.migratrack.export.xlsx.XlsxWriter
import kotlinx.datetime.LocalDateTime
import java.io.ByteArrayInputStream
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.io.path.createTempFile
import kotlin.io.path.writeBytes
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** Reads the generated package back with java.util.zip, which checks every CRC and size we wrote. */
class XlsxPackageTest {

    @Test
    fun packageHasEveryPartAsWellFormedXml() {
        val bytes = ReportWorkbook.write(report(listOf(episode(20), episode(21))), EnglishReportStrings)
        val parts = unzip(bytes)

        val expected = setOf(
            "[Content_Types].xml", "_rels/.rels", "xl/workbook.xml", "xl/_rels/workbook.xml.rels",
            "xl/styles.xml", "xl/sharedStrings.xml",
            "xl/worksheets/sheet1.xml", "xl/worksheets/sheet2.xml", "xl/worksheets/sheet3.xml", "xl/worksheets/sheet4.xml",
        )
        assertEquals(expected, parts.keys)
        val parser = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        parts.values.forEach { parser.parse(ByteArrayInputStream(it)) }
    }

    @Test
    fun centralDirectoryAgreesWithTheEntries() {
        val bytes = XlsxWriter.write(listOf(Sheet("Data", listOf(Column("Name", 10.0)), listOf(listOf(Cell.Text("Кофеин & <сыр>"))))))
        val file = createTempFile(suffix = ".xlsx").apply { writeBytes(bytes) }.toFile()
        ZipFile(file).use { zip ->
            val entries = zip.entries().toList()
            assertEquals(7, entries.size)
            entries.forEach { entry ->
                assertEquals(ZipEntry.STORED, entry.method)
                val data = zip.getInputStream(entry).readBytes()
                assertEquals(entry.crc, CRC32().apply { update(data) }.value)
            }
        }
        val sharedStrings = unzip(bytes).getValue("xl/sharedStrings.xml").decodeToString()
        assertTrue("Кофеин &amp; &lt;сыр&gt;" in sharedStrings)
    }

    @Test
    fun datesBecomeExcelSerialNumbers() {
        val bytes = XlsxWriter.write(
            listOf(Sheet("Data", listOf(Column("When", 18.0)), listOf(listOf(Cell.DateTime(LocalDateTime(2026, 10, 24, 12, 0)))))),
        )
        val sheet = unzip(bytes).getValue("xl/worksheets/sheet1.xml").decodeToString()
        // 2026-10-24 is day 46319 after 1899-12-30; noon adds half a day.
        assertTrue("""<c r="A2" s="2"><v>46319.5</v></c>""" in sheet, sheet)
    }

    private fun unzip(bytes: ByteArray): Map<String, ByteArray> = buildMap {
        ZipInputStream(ByteArrayInputStream(bytes)).use { zip ->
            generateSequence { zip.nextEntry }.forEach { put(it.name, zip.readBytes()) }
        }
    }
}
