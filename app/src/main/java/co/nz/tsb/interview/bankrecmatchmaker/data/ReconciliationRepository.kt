package co.nz.tsb.interview.bankrecmatchmaker.data

interface ReconciliationRepository {
    suspend fun getReconciliationData(): ReconciliationData
}
