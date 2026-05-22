package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord

data class MatchCandidate(
    val records: List<AccountingRecord>,
) {
    val totalInCents: Long
        get() = records.sumOf { it.amountInCents }

    val isSingleMatch: Boolean
        get() = records.size == 1
}
