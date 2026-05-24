package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord
import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecordRepository
import javax.inject.Inject

class FindMatchUseCase
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

            /* Comment out the above code snippet and uncomment the below code to enable
                backtracking solution for finding all combinations of records that sum up to the target amount. */

//            val backtrackingCandidates =
//                findAllSubsetsByBacktracking(records, targetAmountInCents)
//
//            val matchCandidates =
//                backtrackingCandidates
//                    .map { selectedRecords ->
//                        MatchCandidate(records = selectedRecords)
//                    }

            return MatchResult(
                records = records,
                matchCandidates = matchCandidates,
            )
        }

        private fun findAllSubsetsByBacktracking(
            records: List<AccountingRecord>,
            targetAmountInCents: Long,
        ): List<List<AccountingRecord>> {
            if (targetAmountInCents < 0) return emptyList()

            if (targetAmountInCents == 0L) {
                return listOf(emptyList())
            }

            val validRecords =
                records
                    .filter { it.amountInCents in 1..targetAmountInCents }
                    .sortedByDescending { it.amountInCents }

            val results = mutableListOf<List<AccountingRecord>>()
            val selected = mutableListOf<AccountingRecord>()
            val failedStates = mutableSetOf<Pair<Int, Long>>()

            fun dfs(
                startIndex: Int,
                remaining: Long,
            ) {
                if (remaining == 0L) {
                    results += selected.toList()
                    return
                }

                if (startIndex >= validRecords.size) {
                    return
                }

                val state = startIndex to remaining

                // Skip states already proven impossible.
                if (state in failedStates) {
                    return
                }

                val previousResultCount = results.size

                for (i in startIndex until validRecords.size) {
                    val record = validRecords[i]

                    // Prune impossible branches early.
                    if (record.amountInCents > remaining) {
                        continue
                    }

                    selected += record

                    dfs(
                        startIndex = i + 1,
                        remaining = remaining - record.amountInCents,
                    )

                    selected.removeAt(selected.lastIndex)
                }

                // Memoize only if this state produced no valid combinations.
                if (results.size == previousResultCount) {
                    failedStates += state
                }
            }

            dfs(
                startIndex = 0,
                remaining = targetAmountInCents,
            )

            return results
        }
    }
