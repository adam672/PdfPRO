package com.pdfpro.app.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    data class PaymentUiState(
        val selectedCoin: CoinType = CoinType.BTC,
        val txid: String = "",
        val isVerifying: Boolean = false,
        val isVerified: Boolean = false,
        val error: String? = null
    )

    fun selectCoin(coin: CoinType) {
        _uiState.value = _uiState.value.copy(selectedCoin = coin)
    }

    fun updateTxid(txid: String) {
        _uiState.value = _uiState.value.copy(txid = txid)
    }

    fun verifyPayment(context: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isVerifying = true, error = null)
            try {
                val verified = PaymentVerificationManager.verifyPayment(
                    context, _uiState.value.txid, _uiState.value.selectedCoin
                )
                _uiState.value = _uiState.value.copy(
                    isVerifying = false,
                    isVerified = verified,
                    error = if (!verified) "Verification failed" else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isVerifying = false,
                    error = e.message
                )
            }
        }
    }
}
