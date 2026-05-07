package com.pdfpro.app.storage

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    fun addFavorite(path: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.add(path)
        saveFavorites(favorites)
    }

    fun removeFavorite(path: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.remove(path)
        saveFavorites(favorites)
    }

    fun isFavorite(path: String): Boolean {
        return getFavorites().contains(path)
    }

    fun getFavorites(): Set<String> {
        val json = prefs.getString("paths", "[]") ?: "[]"
        val array = JSONArray(json)
        return (0 until array.length()).map { array.getString(it) }.toSet()
    }

    private fun saveFavorites(favorites: Set<String>) {
        val array = JSONArray(favorites)
        prefs.edit().putString("paths", array.toString()).apply()
    }
}
