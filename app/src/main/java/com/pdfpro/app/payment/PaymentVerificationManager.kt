package com.pdfpro.app.payment

import android.content.Context
import androidx.core.content.edit

object PaymentVerificationManager {
    private const val PREFS_NAME = "payment_prefs"
    private const val KEY_ADS_REMOVED = "ads_removed"
    private const val KEY_TXID = "txid"
    private const val KEY_COIN = "coin_type"
    private const val KEY_DATE = "verified_date"

    fun verifyPayment(context: Context, txid: String, coinType: CoinType): Boolean {
        // TODO: Implement actual blockchain verification
        val verified = txid.isNotBlank() && txid.length > 10
        if (verified) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
                putBoolean(KEY_ADS_REMOVED, true)
                putString(KEY_TXID, txid)
                putString(KEY_COIN, coinType.name)
                putLong(KEY_DATE, System.currentTimeMillis())
            }
        }
        return verified
    }

    fun isAdsRemoved(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getBoolean(KEY_ADS_REMOVED, false)
    }

    fun getPaymentInfo(context: Context): PaymentInfo? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (!prefs.getBoolean(KEY_ADS_REMOVED, false)) return null
        return PaymentInfo(
            txid = prefs.getString(KEY_TXID, "") ?: "",
            coinType = prefs.getString(KEY_COIN, "") ?: "",
            date = prefs.getLong(KEY_DATE, 0)
        )
    }

    data class PaymentInfo(val txid: String, val coinType: String, val date: Long)
}
