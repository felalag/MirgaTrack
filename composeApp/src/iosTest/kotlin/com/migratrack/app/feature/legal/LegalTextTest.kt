package com.migratrack.app.feature.legal

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class LegalTextTest {
    @Test
    fun englishTextComesFromTheFilesFolderItself() = runTest {
        assertEquals("files/privacy-policy.md", LegalDocument.PrivacyPolicy.path(""))
        assertEquals("files/pl/privacy-policy.md", LegalDocument.PrivacyPolicy.path("pl"))
        assertTrue(readLegalText(LegalDocument.TermsOfService).contains("# Terms of Service"))
    }

    @Test
    fun aLanguageWithoutTranslationGetsTheEnglishText() = runTest {
        assertTrue(readLegalText(LegalDocument.PrivacyPolicy, folder = "xx").contains("# Privacy Policy"))
    }
}
