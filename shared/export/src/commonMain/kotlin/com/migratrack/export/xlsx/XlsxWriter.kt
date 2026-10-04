package com.migratrack.export.xlsx

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.daysUntil

sealed interface Cell {
    data class Text(val value: String) : Cell
    data class Number(val value: Double) : Cell
    data class DateTime(val value: LocalDateTime) : Cell
    data object Empty : Cell
}

data class Sheet(
    val name: String,
    val columns: List<Column>,
    val rows: List<List<Cell>>,
)

data class Column(val title: String, val width: Double)

/**
 * Writes a minimal SpreadsheetML package: a bold frozen header row, shared strings, and real date cells.
 * Shared strings rather than inline ones, because older Apple Numbers versions showed inline strings blank.
 */
object XlsxWriter {

    fun write(sheets: List<Sheet>): ByteArray {
        require(sheets.isNotEmpty()) { "A workbook needs at least one sheet" }
        val strings = SharedStrings()
        val worksheets = sheets.map { worksheet(it, strings) }
        val zip = StoredZip()
        zip.add("[Content_Types].xml", contentTypes(sheets.size).encodeToByteArray())
        zip.add("_rels/.rels", ROOT_RELS.encodeToByteArray())
        zip.add("xl/workbook.xml", workbook(sheets).encodeToByteArray())
        zip.add("xl/_rels/workbook.xml.rels", workbookRels(sheets.size).encodeToByteArray())
        zip.add("xl/styles.xml", STYLES.encodeToByteArray())
        zip.add("xl/sharedStrings.xml", strings.toXml().encodeToByteArray())
        worksheets.forEachIndexed { index, xml ->
            zip.add("xl/worksheets/sheet${index + 1}.xml", xml.encodeToByteArray())
        }
        return zip.finish()
    }

    private class SharedStrings {
        private val positions = LinkedHashMap<String, Int>()
        private var references = 0

        fun indexOf(value: String): Int {
            references++
            return positions.getOrPut(value) { positions.size }
        }

        fun toXml() = buildString {
            append(XML_HEADER)
            append("""<sst xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" count="$references" uniqueCount="${positions.size}">""")
            for (value in positions.keys) append("""<si><t xml:space="preserve">${escape(value)}</t></si>""")
            append("</sst>")
        }
    }

    private fun contentTypes(sheetCount: Int) = buildString {
        append(XML_HEADER)
        append("""<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">""")
        append("""<Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>""")
        append("""<Default Extension="xml" ContentType="application/xml"/>""")
        append("""<Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/>""")
        append("""<Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>""")
        append("""<Override PartName="/xl/sharedStrings.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml"/>""")
        for (i in 1..sheetCount) {
            append("""<Override PartName="/xl/worksheets/sheet$i.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>""")
        }
        append("</Types>")
    }

    private fun workbook(sheets: List<Sheet>) = buildString {
        append(XML_HEADER)
        append("""<workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"><sheets>""")
        sheets.forEachIndexed { index, sheet ->
            append("""<sheet name="${escape(sheetName(sheet.name))}" sheetId="${index + 1}" r:id="rId${index + 1}"/>""")
        }
        append("</sheets></workbook>")
    }

    private fun workbookRels(sheetCount: Int) = buildString {
        append(XML_HEADER)
        append("""<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">""")
        for (i in 1..sheetCount) {
            append("""<Relationship Id="rId$i" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" Target="worksheets/sheet$i.xml"/>""")
        }
        append("""<Relationship Id="rId${sheetCount + 1}" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>""")
        append("""<Relationship Id="rId${sheetCount + 2}" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings" Target="sharedStrings.xml"/>""")
        append("</Relationships>")
    }

    private fun worksheet(sheet: Sheet, strings: SharedStrings) = buildString {
        append(XML_HEADER)
        append("""<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">""")
        append("""<sheetViews><sheetView workbookViewId="0"><pane ySplit="1" topLeftCell="A2" activePane="bottomLeft" state="frozen"/></sheetView></sheetViews>""")
        append("<cols>")
        sheet.columns.forEachIndexed { index, column ->
            append("""<col min="${index + 1}" max="${index + 1}" width="${column.width}" customWidth="1"/>""")
        }
        append("</cols><sheetData>")
        appendRow(1, sheet.columns.map { Cell.Text(it.title) }, header = true, strings)
        sheet.rows.forEachIndexed { index, row -> appendRow(index + 2, row, header = false, strings) }
        append("</sheetData></worksheet>")
    }

    private fun StringBuilder.appendRow(number: Int, cells: List<Cell>, header: Boolean, strings: SharedStrings) {
        append("""<row r="$number">""")
        cells.forEachIndexed { index, cell ->
            val ref = columnName(index) + number
            when (cell) {
                is Cell.Text -> {
                    val style = if (header) """ s="$STYLE_BOLD"""" else ""
                    append("""<c r="$ref" t="s"$style><v>${strings.indexOf(cell.value)}</v></c>""")
                }
                is Cell.Number -> append("""<c r="$ref"><v>${formatNumber(cell.value)}</v></c>""")
                is Cell.DateTime -> append("""<c r="$ref" s="$STYLE_DATE_TIME"><v>${serial(cell.value)}</v></c>""")
                Cell.Empty -> Unit
            }
        }
        append("</row>")
    }

    /** Excel stores dates as days since 1899-12-30, with the time as the fraction. */
    internal fun serial(value: LocalDateTime): String {
        val days = EXCEL_EPOCH.daysUntil(value.date)
        val seconds = value.hour * 3600 + value.minute * 60 + value.second
        return formatNumber(days + seconds / 86_400.0)
    }

    internal fun columnName(index: Int): String {
        var n = index + 1
        val name = StringBuilder()
        while (n > 0) {
            val rem = (n - 1) % 26
            name.insert(0, 'A' + rem)
            n = (n - 1) / 26
        }
        return name.toString()
    }

    private fun formatNumber(value: Double): String {
        if (value == value.toLong().toDouble()) return value.toLong().toString()
        // Six decimals keep minute precision for date serials and avoid exponent notation.
        val scaled = kotlin.math.round(value * 1_000_000).toLong()
        val whole = scaled / 1_000_000
        val fraction = (scaled % 1_000_000).toString().padStart(6, '0').trimEnd('0')
        return if (fraction.isEmpty()) whole.toString() else "$whole.$fraction"
    }

    /** Excel rejects sheet names over 31 characters or with any of []:*?/\ in them. */
    private fun sheetName(name: String): String =
        name.filterNot { it in "[]:*?/\\" }.take(31).ifBlank { "Sheet" }

    internal fun escape(value: String): String = buildString(value.length) {
        for (ch in value) {
            when {
                ch == '&' -> append("&amp;")
                ch == '<' -> append("&lt;")
                ch == '>' -> append("&gt;")
                ch == '"' -> append("&quot;")
                // XML 1.0 forbids most control characters, even escaped.
                ch < ' ' && ch != '\t' && ch != '\n' && ch != '\r' -> Unit
                else -> append(ch)
            }
        }
    }

    private val EXCEL_EPOCH = LocalDate(1899, 12, 30)
    private const val STYLE_BOLD = 1
    private const val STYLE_DATE_TIME = 2
    private const val XML_HEADER = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>"""

    private const val ROOT_RELS = XML_HEADER +
        """<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">""" +
        """<Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/>""" +
        "</Relationships>"

    // Cell style 0 is the default, 1 is the bold header, 2 is "yyyy-mm-dd hh:mm".
    private const val STYLES = XML_HEADER +
        """<styleSheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">""" +
        """<numFmts count="1"><numFmt numFmtId="164" formatCode="yyyy-mm-dd hh:mm"/></numFmts>""" +
        """<fonts count="2"><font><sz val="11"/><name val="Calibri"/><family val="2"/></font>""" +
        """<font><b/><sz val="11"/><name val="Calibri"/><family val="2"/></font></fonts>""" +
        """<fills count="2"><fill><patternFill patternType="none"/></fill><fill><patternFill patternType="gray125"/></fill></fills>""" +
        """<borders count="1"><border><left/><right/><top/><bottom/><diagonal/></border></borders>""" +
        """<cellStyleXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0"/></cellStyleXfs>""" +
        """<cellXfs count="3"><xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"/>""" +
        """<xf numFmtId="0" fontId="1" fillId="0" borderId="0" xfId="0" applyFont="1"/>""" +
        """<xf numFmtId="164" fontId="0" fillId="0" borderId="0" xfId="0" applyNumberFormat="1"/></cellXfs>""" +
        """<cellStyles count="1"><cellStyle name="Normal" xfId="0" builtinId="0"/></cellStyles>""" +
        "</styleSheet>"
}
