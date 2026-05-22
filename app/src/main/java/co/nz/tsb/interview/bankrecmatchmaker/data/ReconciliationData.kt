package co.nz.tsb.interview.bankrecmatchmaker.data

data class ReconciliationData(
    val transaction: BankTransaction,
    val records: List<AccountingRecord>
)
