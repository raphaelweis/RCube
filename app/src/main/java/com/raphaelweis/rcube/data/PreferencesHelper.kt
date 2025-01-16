package com.raphaelweis.rcube.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    fun saveUserId(userId: Long) {
        sharedPreferences.edit().putString("user_id", userId.toString()).apply()
    }

    fun getUserId(): Long? {
        return sharedPreferences.getString("user_id", null)?.toLong()
    }

    fun deleteUserId() {
        sharedPreferences.edit().remove("user_id").apply()
    }
}
