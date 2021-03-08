package ru.carwash.controllers

import android.content.Context
import com.carwash.carwash.R

class DataProcessor { // класс для сохранения-получения данных из памяти устройства

    companion object{

        private const val CAR_WASH: String = "CAR_WASH"

        // saving methods
        fun saveData(context: Context, key: String, data: String) {
            var prefs = context.getSharedPreferences(CAR_WASH, Context.MODE_PRIVATE)
            var edit = prefs.edit()
            edit.putString(key,data)
            edit.apply()
        }
        fun saveData(context: Context, key: String, data: Int) {
            var prefs = context.getSharedPreferences(CAR_WASH, Context.MODE_PRIVATE)
            var edit = prefs.edit()
            edit.putInt(key,data)
            edit.apply()
        }
        fun saveData(context: Context, key: String, data: Boolean) {
            var prefs = context.getSharedPreferences(CAR_WASH, Context.MODE_PRIVATE)
            var edit = prefs.edit()
            edit.putBoolean(key,data)
            edit.apply()
        }

        // getting methods
        fun getInt(context: Context, key: String, default: Int): Int {
            var prefs = context.getSharedPreferences(CAR_WASH, Context.MODE_PRIVATE)
            return prefs.getInt(key, default)
        }
        fun getString(context: Context, key: String, default: String): String? {
            var prefs = context.getSharedPreferences(CAR_WASH, Context.MODE_PRIVATE)
            return prefs.getString(key, default)
        }
        fun getBoolean(context: Context, key: String, default: Boolean): Boolean {
            var prefs = context.getSharedPreferences(CAR_WASH, Context.MODE_PRIVATE)
            return prefs.getBoolean(key, default)
        }
    }
}