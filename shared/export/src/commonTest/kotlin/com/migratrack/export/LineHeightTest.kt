package com.migratrack.export

import com.migratrack.export.pdf.lineHeightMultiple
import kotlin.test.Test
import kotlin.test.assertEquals

class LineHeightTest {

    @Test
    fun indianScriptsGetTheLineHeightOfTheirFont() {
        listOf("इब्बंदि", "ব্যথা", "ઇબ્બંદિ", "ਦਰਦ", "ଯନ୍ତ୍ରଣା", "ಅಟ್ಯಾಕ್", "അറ്റാക്ക്", "தூண்டி", "10 ఎటాక్‌లలో 2").forEach {
            assertEquals(1.3f, lineHeightMultiple(it), it)
        }
    }

    @Test
    fun otherScriptsKeepTheLineHeightOfTheFont() {
        listOf("Photophobia (Light)", "Светобоязнь", "สิ่งกระตุ้น", "편두통", "صداع نصفي", "6/10").forEach {
            assertEquals(1f, lineHeightMultiple(it), it)
        }
    }
}
