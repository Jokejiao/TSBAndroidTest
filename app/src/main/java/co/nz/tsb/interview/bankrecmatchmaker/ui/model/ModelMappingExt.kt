package co.nz.tsb.interview.bankrecmatchmaker.ui.model

import co.nz.tsb.interview.bankrecmatchmaker.data.DocumentType

fun DocumentType.toDisplayString(): String =
    when (this) {
        DocumentType.INVOICE -> "Sales Invoice"
        DocumentType.BILL -> "Bill"
    }
