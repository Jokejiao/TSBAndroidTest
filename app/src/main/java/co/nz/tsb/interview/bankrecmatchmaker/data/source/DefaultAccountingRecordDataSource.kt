package co.nz.tsb.interview.bankrecmatchmaker.data.source

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord
import co.nz.tsb.interview.bankrecmatchmaker.data.DocumentType
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultAccountingRecordDataSource
    @Inject
    constructor() : AccountingRecordDataSource {
        override suspend fun getAccountingRecords(): List<AccountingRecord> {
            delay(1_000)

            return listOf(
                AccountingRecord(
                    id = "record-001",
                    paidTo = "City Limousines",
                    transactionDate = "30 Aug",
                    amountInCents = 249_00L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-002",
                    paidTo = "Ridgeway University",
                    transactionDate = "12 Sep",
                    amountInCents = 618_50L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-003",
                    paidTo = "Cube Land",
                    transactionDate = "22 Sep",
                    amountInCents = 495_00L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-004",
                    paidTo = "Bayside Club",
                    transactionDate = "23 Sep",
                    amountInCents = 234_00L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-005",
                    paidTo = "SMART Agency",
                    transactionDate = "12 Sep",
                    amountInCents = 250_00L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-006",
                    paidTo = "PowerDirect",
                    transactionDate = "11 Sep",
                    amountInCents = 108_60L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-007",
                    paidTo = "PC Complete",
                    transactionDate = "17 Sep",
                    amountInCents = 216_99L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-008",
                    paidTo = "Truxton Properties",
                    transactionDate = "17 Sep",
                    amountInCents = 181_25L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-009",
                    paidTo = "MCO Cleaning Services",
                    transactionDate = "17 Sep",
                    amountInCents = 170_50L,
                    documentType = DocumentType.INVOICE,
                ),
                AccountingRecord(
                    id = "record-010",
                    paidTo = "Gateway Motors",
                    transactionDate = "18 Sep",
                    amountInCents = 411_35L,
                    documentType = DocumentType.INVOICE,
                ),
            )
        }
    }
