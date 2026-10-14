package com.migratrack.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.model.ExportRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** Data checks for the migrations; the Gradle check task already compares the migrated schema with the current one. */
class MigrationTest {

    @Test
    fun versionTwoExportPreferencesIncludeTriggersAfterTheMigration() = runTest {
        val file = File.createTempFile("migratrack-v2", ".db").also { it.deleteOnExit() }
        File("src/commonMain/sqldelight/databases/2.db").copyTo(file, overwrite = true)
        val driver = JdbcSqliteDriver("jdbc:sqlite:${file.path}")
        driver.execute(null, "INSERT INTO export_preferences VALUES (1, 0, 1, 1, 'Last30Days')", 0)

        MigraTrackDatabase.Schema.migrate(driver, oldVersion = 2, newVersion = MigraTrackDatabase.Schema.version)
        val preferences = LocalStore(driver, Dispatchers.Unconfined).profiles.observeExportPreferences().first()

        assertEquals(false, preferences.includeMedications)
        assertEquals(ExportRange.Last30Days, preferences.range)
        assertTrue(preferences.includeTriggers)
    }
}
