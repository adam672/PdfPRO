package com.pdfpro.app.payment

enum class CoinType(val displayName: String, val network: String) {
    BTC("Bitcoin (BTC)", "Bitcoin Network"),
    LTC("Litecoin (LTC)", "Litecoin Network"),
    USDT("USDT (TRC20)", "TRON Network (TRC20 only)")
}
