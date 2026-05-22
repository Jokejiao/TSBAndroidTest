package co.nz.tsb.interview.bankrecmatchmaker.data

data class AccountingRecord(
    val id: String,
    val paidTo: String,
    val transactionDate: String,
    val amountInCents: Long,
    val documentType: DocumentType,
)

enum class DocumentType {
    INVOICE,
    BILL,
}

fun DocumentType.toDisplayString(): String =
    when (this) {
        DocumentType.INVOICE -> "Sales Invoice"
        DocumentType.BILL -> "Bill"
    }
