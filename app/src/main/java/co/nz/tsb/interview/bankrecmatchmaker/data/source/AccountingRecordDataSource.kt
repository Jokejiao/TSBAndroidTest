package co.nz.tsb.interview.bankrecmatchmaker.data.source

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord

interface AccountingRecordDataSource {
    suspend fun getAccountingRecords(): List<AccountingRecord>
}
