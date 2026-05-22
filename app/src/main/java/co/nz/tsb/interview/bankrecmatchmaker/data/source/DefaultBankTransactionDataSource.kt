package co.nz.tsb.interview.bankrecmatchmaker.data.source

import co.nz.tsb.interview.bankrecmatchmaker.data.BankTransaction
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultBankTransactionDataSource
    @Inject
    constructor() : BankTransactionDataSource {
        override suspend fun getBankTransactions(): BankTransaction {
            delay(1_000)

            return BankTransaction(
                id = "1",
                amountInCents = 1_000_000L,
            )
        }
    }
