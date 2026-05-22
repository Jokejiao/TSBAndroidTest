package co.nz.tsb.interview.bankrecmatchmaker.data.source

import co.nz.tsb.interview.bankrecmatchmaker.data.BankTransaction

interface BankTransactionDataSource {
    suspend fun getBankTransactions(): BankTransaction
}
