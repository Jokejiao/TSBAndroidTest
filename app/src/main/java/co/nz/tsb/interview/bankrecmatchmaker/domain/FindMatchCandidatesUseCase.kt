package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecordRepository
import javax.inject.Inject

class FindMatchCandidatesUseCase
    @Inject
    constructor(
        private val accountingRecordRepository: AccountingRecordRepository,
    ) {
        suspend operator fun invoke(targetAmountInCents: Long): MatchResult {
            val records = accountingRecordRepository.getAccountingRecords()

            val matchCandidates =
                records
                    .filter { it.amountInCents == targetAmountInCents }
                    .map { record ->
                        MatchCandidate(records = listOf(record))
                    }

            return MatchResult(
                records = records,
                matchCandidates = matchCandidates,
            )

            // TODO:
            // Extend this to find multi-record candidates where the sum of selected
            // records equals the transaction amount. This is a classic subset-sum
            // problem and can be solved with dynamic programming or backtracking.
            //
            // If multiple candidates are found, we should not silently select an
            // arbitrary one in a real reconciliation flow. A future UI improvement
            // could allow the user to review and iterate through all suggested
            // candidates before confirming a match.

            // For this exercise, auto-select the first exact candidate.
            // In a real product, if multiple candidates exist, the UI should present all
            // candidates and let the user confirm the correct one.
        }
    }
