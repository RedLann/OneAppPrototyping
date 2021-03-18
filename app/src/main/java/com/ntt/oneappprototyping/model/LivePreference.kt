package com.ntt.oneappprototyping.model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class LivePreference<T>(
    val sharedPrefs: SharedPreferences,
    val key: String,
    private val defValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == this.key) {
                value = getValueFromPreferences(key, defValue)
            }
        }

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}

class SharedPreferenceIntLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Int) :
    LivePreference<Int>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Int): Int = sharedPrefs.getInt(key, defValue)
}

class SharedPreferenceStringLiveData(sharedPrefs: SharedPreferences, key: String, defValue: String) :
    LivePreference<String>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: String): String = sharedPrefs.getString(key, defValue) ?: defValue
}

class SharedPreferenceBooleanLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Boolean) :
    LivePreference<Boolean>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Boolean): Boolean = sharedPrefs.getBoolean(key, defValue)
}

class SharedPreferenceFloatLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Float) :
    LivePreference<Float>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Float): Float = sharedPrefs.getFloat(key, defValue)
}

class SharedPreferenceLongLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Long) :
    LivePreference<Long>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Long): Long = sharedPrefs.getLong(key, defValue)
}

class SharedPreferenceStringSetLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Set<String>) :
    LivePreference<Set<String>>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Set<String>): Set<String> = sharedPrefs.getStringSet(key, defValue) ?: defValue
}

fun SharedPreferences.intLiveData(key: String, defValue: Int): LivePreference<Int> {
    return SharedPreferenceIntLiveData(this, key, defValue)
}
fun SharedPreferences.stringLiveData(key: String, defValue: String): LivePreference<String> {
    return SharedPreferenceStringLiveData(this, key, defValue)
}
fun SharedPreferences.booleanLiveData(key: String, defValue: Boolean): LivePreference<Boolean> {
    return SharedPreferenceBooleanLiveData(this, key, defValue)
}
fun SharedPreferences.floatLiveData(key: String, defValue: Float): LivePreference<Float> {
    return SharedPreferenceFloatLiveData(this, key, defValue)
}
fun SharedPreferences.longLiveData(key: String, defValue: Long): LivePreference<Long> {
    return SharedPreferenceLongLiveData(this, key, defValue)
}
fun SharedPreferences.stringSetLiveData(key: String, defValue: Set<String>): LivePreference<Set<String>> {
    return SharedPreferenceStringSetLiveData(this, key, defValue)
}
