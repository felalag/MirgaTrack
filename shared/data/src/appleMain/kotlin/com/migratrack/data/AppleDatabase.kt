package com.migratrack.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.migratrack.data.db.MigraTrackDatabase
import platform.Foundation.NSFileManager

object AppleDatabase {
    private const val FILE_NAME = "migratrack.db"

    /**
     * Opens the database with the system SQLite.
     * @param directory folder for the file; null keeps the driver default inside the app sandbox.
     *   Pass [appGroupDirectory] once widgets or the watch extension need to read the same file.
     */
    fun driver(directory: String? = null): SqlDriver =
        NativeSqliteDriver(
            schema = MigraTrackDatabase.Schema,
            name = FILE_NAME,
            onConfiguration = { config ->
                if (directory == null) config
                else config.copy(extendedConfig = config.extendedConfig.copy(basePath = directory))
            },
        )

    /** Container shared by the app and its extensions, or null without the App Group entitlement. */
    fun appGroupDirectory(groupId: String): String? =
        NSFileManager.defaultManager.containerURLForSecurityApplicationGroupIdentifier(groupId)?.path
}
