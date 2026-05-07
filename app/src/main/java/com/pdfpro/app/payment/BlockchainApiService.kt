package com.pdfpro.app.payment

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BlockchainApiService {
    @GET("btc/main/txs/{txid}")
    suspend fun verifyBtcTransaction(@Path("txid") txid: String): ApiResponse

    @GET("ltc/main/txs/{txid}")
    suspend fun verifyLtcTransaction(@Path("txid") txid: String): ApiResponse

    @GET("v1/transactions/{txid}/events")
    suspend fun verifyTronTransaction(@Path("txid") txid: String, @Query("contract_address") contract: String): ApiResponse
}

data class ApiResponse(val confirmed: Boolean, val confirmations: Int)
