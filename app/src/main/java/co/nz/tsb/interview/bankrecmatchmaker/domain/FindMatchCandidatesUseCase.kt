package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.ReconciliationRepository
import javax.inject.Inject

class FindMatchCandidatesUseCase
    @Inject
    constructor(
        private val reconciliationRepository: ReconciliationRepository,
    ) {
        suspend operator fun invoke(): List<MatchCandidate> {
            val reconciliationData = reconciliationRepository.getReconciliationData()
            val targetAmount = reconciliationData.transaction.amountInCents

            return reconciliationData.records
                .filter {
                    it.amountInCents == targetAmount
                }.map { record ->
                    MatchCandidate(
                        records = listOf(record),
                    )
                }

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
