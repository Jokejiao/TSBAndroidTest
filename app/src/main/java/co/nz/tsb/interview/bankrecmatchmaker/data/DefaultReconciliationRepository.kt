package co.nz.tsb.interview.bankrecmatchmaker.data

import co.nz.tsb.interview.bankrecmatchmaker.data.source.AccountingRecordDataSource
import co.nz.tsb.interview.bankrecmatchmaker.data.source.BankTransactionDataSource
import javax.inject.Inject

class DefaultReconciliationRepository
    @Inject
    constructor(
        private val bankTransactionDataSource: BankTransactionDataSource,
        private val accountingRecordDataSource: AccountingRecordDataSource,
    ) : ReconciliationRepository {
        override suspend fun getReconciliationData(): ReconciliationData {
            val transaction = bankTransactionDataSource.getBankTransactions()
            val records = accountingRecordDataSource.getAccountingRecords()

            return ReconciliationData(
                transaction = transaction,
                records = records,
            )
        }
    }
