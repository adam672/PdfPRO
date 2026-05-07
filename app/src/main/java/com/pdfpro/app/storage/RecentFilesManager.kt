package com.pdfpro.app.storage

import android.content.Context
import android.content.SharedPreferences
import com.pdfpro.app.model.FileInfo
import org.json.JSONArray
import org.json.JSONObject

class RecentFilesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("recent_files", Context.MODE_PRIVATE)

    fun addRecentFile(fileInfo: FileInfo) {
        val files = getRecentFiles().toMutableList()
        files.removeAll { it.path == fileInfo.path }
        files.add(0, fileInfo)
        if (files.size > 50) files.removeLast()
        saveFiles(files)
    }

    fun getRecentFiles(): List<FileInfo> {
        val json = prefs.getString("files", "[]") ?: "[]"
        val array = JSONArray(json)
        return (0 until array.length()).map {
            val obj = array.getJSONObject(it)
            FileInfo(
                path = obj.getString("path"),
                name = obj.getString("name"),
                size = obj.getLong("size"),
                lastModified = obj.getLong("lastModified")
            )
        }
    }

    fun clearRecent() {
        prefs.edit().remove("files").apply()
    }

    private fun saveFiles(files: List<FileInfo>) {
        val array = JSONArray()
        files.forEach {
            val obj = JSONObject()
            obj.put("path", it.path)
            obj.put("name", it.name)
            obj.put("size", it.size)
            obj.put("lastModified", it.lastModified)
            array.put(obj)
        }
        prefs.edit().putString("files", array.toString()).apply()
    }
}
