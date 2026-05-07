package com.pdfpro.app.payment

import android.content.Context

object AdManager {
    fun shouldShowAds(context: Context): Boolean {
        return !PaymentVerificationManager.isAdsRemoved(context)
    }
}
