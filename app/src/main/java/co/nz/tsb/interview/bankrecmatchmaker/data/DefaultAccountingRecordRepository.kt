package co.nz.tsb.interview.bankrecmatchmaker.data

import co.nz.tsb.interview.bankrecmatchmaker.data.source.AccountingRecordDataSource
import javax.inject.Inject

class DefaultAccountingRecordRepository
    @Inject
    constructor(
        private val accountingRecordDataSource: AccountingRecordDataSource,
    ) : AccountingRecordRepository {
        override suspend fun getAccountingRecords(): List<AccountingRecord> = accountingRecordDataSource.getAccountingRecords()
    }
