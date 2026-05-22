package co.nz.tsb.interview.bankrecmatchmaker.data

data class AccountingRecord(
    val id: String,
    val paidTo: String,
    val transactionDate: String,
    val amountInCents: Long,
    val documentType: DocumentType,
)

enum class DocumentType {
    Invoice,
    Bill,
}
