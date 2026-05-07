package com.pdfpro.app.payment

object CryptoAddresses {
    const val BTC_ADDRESS = "YOUR_BTC_ADDRESS_HERE"
    const val LTC_ADDRESS = "YOUR_LTC_ADDRESS_HERE"
    const val USDT_ADDRESS = "YOUR_USDT_TRC20_ADDRESS_HERE"

    fun getAddress(coinType: CoinType): String {
        return when (coinType) {
            CoinType.BTC -> BTC_ADDRESS
            CoinType.LTC -> LTC_ADDRESS
            CoinType.USDT -> USDT_ADDRESS
        }
    }
}
