package com.pdfpro.app.payment

object TronAddressUtils {
    fun isValidTronAddress(address: String): Boolean {
        return address.startsWith("T") && address.length == 34
    }
}
