package co.nz.tsb.interview.bankrecmatchmaker.ui.model

data class FindMatchUiState(
    val isLoading: Boolean = false,
    val transactionAmountText: String = "",
    val remainingToMatchText: String = "",
    val records: List<AccountingRecordUiModel> = emptyList(),
    val errorMessage: String? = null,
)
