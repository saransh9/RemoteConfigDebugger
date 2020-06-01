package com.remoteconfigdebugger.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * Class to initialise library from manifest
 */

class RemoteConfigDebuggerInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {

        if (this.context != null) {
            RemoteConfigDebugger.initialiseApp(this.context!!)
            Log.i("RemoteConfigDebugger", "RemoteConfigDebugger initialization successful")
        } else {
            Log.i("RemoteConfigDebugger", "RemoteConfigDebugger initialization unsuccessful")

        }
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}
