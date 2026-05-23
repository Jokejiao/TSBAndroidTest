package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord
import co.nz.tsb.interview.bankrecmatchmaker.data.BankTransaction

data class ReconciliationResult(
    val transaction: BankTransaction,
    val records: List<AccountingRecord>,
    val matchedCandidates: List<MatchCandidate>,
)
