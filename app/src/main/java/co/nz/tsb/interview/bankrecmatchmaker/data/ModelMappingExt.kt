package co.nz.tsb.interview.bankrecmatchmaker.data

fun DocumentType.toDisplayString(): String =
    when (this) {
        DocumentType.INVOICE -> "Sales Invoice"
        DocumentType.BILL -> "Bill"
    }
