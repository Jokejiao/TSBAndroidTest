package co.nz.tsb.interview.bankrecmatchmaker.ui.model

data class AccountingRecordUiModel(
    val id: String,
    val paidTo: String,
    val transactionDate: String,
    val amountText: String,
    val documentType: String,
    val isSelected: Boolean,
)
