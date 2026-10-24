package com.example.financeapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.financeapp.legacy.Transaction

internal enum class TransactionType { INCOME, EXPENSE }

internal enum class TranscationError {
    INVALID_INPUT, AMOUNT_TOO_LARGE
}

internal data class Transcation (
    val amount: Int,
    val type: TransactionType,
    val description: String,
)

internal data class MainUiState(
    val amount: String = "",
    val description: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val  balance: Int = 0,
    val TranscationCount: Int = 0,
    val transactions: List<Transaction> = emptyList(),
    val error: TranscationError? = null,
)

internal sealed interface MainUiEvent {
    data class AmountChanged(val value: String) : MainUiEvent
    data class DescriptionChanged(val value: String) : MainUiEvent
    data class TypeChanged(val type: TransactionType) : MainUiEvent
    data class AddTranscation(val transcation: Transcation) : MainUiEvent
    data object InvalidTranscation : MainUiEvent
    data object AmountTooLarge : MainUiEvent
}

internal class TransactionViewModel : ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set
}