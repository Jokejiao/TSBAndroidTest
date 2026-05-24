package co.nz.tsb.interview.bankrecmatchmaker.domain

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord
import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecordRepository
import co.nz.tsb.interview.bankrecmatchmaker.data.DocumentType
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class FindMatchUseCaseTest {
    @Test
    fun `returns exact match candidate when one record amount equals target`() =
        runTest {
            val repository =
                FakeAccountingRecordRepository(
                    records =
                        listOf(
                            AccountingRecord("1", "City Limousines", "30 Aug", 249_00, DocumentType.INVOICE),
                            AccountingRecord("2", "PowerDirect", "11 Sep", 108_60, DocumentType.INVOICE),
                        ),
                )

            val useCase = FindMatchUseCase(repository)

            val result = useCase(108_60)

            assertEquals(1, result.matchCandidates.size)
            assertEquals(
                "2",
                result.matchCandidates
                    .first()
                    .records
                    .first()
                    .id,
            )
        }

    @Test
    fun `returns empty candidates when no record matches target`() =
        runTest {
            val repository =
                FakeAccountingRecordRepository(
                    records =
                        listOf(
                            AccountingRecord("1", "City Limousines", "30 Aug", 249_00, DocumentType.INVOICE),
                            AccountingRecord("2", "PowerDirect", "11 Sep", 108_60, DocumentType.INVOICE),
                        ),
                )

            val useCase = FindMatchUseCase(repository)

            val result = useCase(10_000_00)

            assertTrue(result.matchCandidates.isEmpty())
        }

    private class FakeAccountingRecordRepository(
        private val records: List<AccountingRecord>,
    ) : AccountingRecordRepository {
        override suspend fun getAccountingRecords(): List<AccountingRecord> = records
    }
}
