package com.migratrack.app.feature.legal

import kotlin.test.Test
import kotlin.test.assertEquals

class LegalMarkdownTest {
    @Test
    fun skipsFrontMatterAndTitleAndSplitsSections() {
        val text = parseLegalMarkdown(
            """
            ---
            permalink: /privacy/
            ---

            # Privacy Policy

            Effective October 14, 2026

            ## Summary

            First paragraph
            continues here.

            Second paragraph.

            ## Changes

            Last paragraph.
            """.trimIndent(),
        )

        assertEquals(listOf("Effective October 14, 2026"), text.intro)
        assertEquals(listOf("Summary", "Changes"), text.sections.map { it.heading })
        assertEquals(listOf("First paragraph continues here.", "Second paragraph."), text.sections[0].paragraphs)
        assertEquals(listOf("Last paragraph."), text.sections[1].paragraphs)
    }

    @Test
    fun readsTextWithoutFrontMatter() {
        val text = parseLegalMarkdown("## Only\n\nOne line.")

        assertEquals(emptyList(), text.intro)
        assertEquals(listOf("One line."), text.sections.single().paragraphs)
    }
}
