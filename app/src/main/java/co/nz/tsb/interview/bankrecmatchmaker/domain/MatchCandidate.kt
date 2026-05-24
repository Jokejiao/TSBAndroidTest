package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord

/**
 * A match candidate is a set of accounting records that could potentially be a match.
 */
data class MatchCandidate(
    val records: List<AccountingRecord>,
)
