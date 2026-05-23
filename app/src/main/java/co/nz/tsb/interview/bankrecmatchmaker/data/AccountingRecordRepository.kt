package co.nz.tsb.interview.bankrecmatchmaker.data

interface AccountingRecordRepository {
    suspend fun getAccountingRecords(): List<AccountingRecord>
}
