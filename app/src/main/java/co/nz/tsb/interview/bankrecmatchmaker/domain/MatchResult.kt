package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord

data class MatchResult(
    val records: List<AccountingRecord>,
    val matchCandidates: List<MatchCandidate>,
)
