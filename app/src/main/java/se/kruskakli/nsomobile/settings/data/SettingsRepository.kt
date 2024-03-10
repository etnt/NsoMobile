package se.kruskakli.nsomobile.settings.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


interface SettingsRepository {
    suspend fun saveSettings(settings: List<SettingsData>)
    suspend fun getSettings(): List<SettingsData>
}

class SettingsRepositoryImpl(
    private val context: Context
) : SettingsRepository {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "encrypted_settings",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveSettings(settings: List<SettingsData>) {
        val settingsJson = Json.encodeToString(settings)
        sharedPreferences.edit().putString("nsoMobileSettings", settingsJson).apply()
    }

    override suspend fun getSettings(): List<SettingsData> {
        val settingsJson = sharedPreferences.getString("nsoMobileSettings", "") ?: ""
        return if (settingsJson.isNotEmpty()) Json.decodeFromString(settingsJson) else emptyList()
    }
}

