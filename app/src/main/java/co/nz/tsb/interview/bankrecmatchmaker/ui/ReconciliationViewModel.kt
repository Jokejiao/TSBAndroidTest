package co.nz.tsb.interview.bankrecmatchmaker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord
import co.nz.tsb.interview.bankrecmatchmaker.data.BankTransaction
import co.nz.tsb.interview.bankrecmatchmaker.domain.FindMatchCandidatesUseCase
import co.nz.tsb.interview.bankrecmatchmaker.ui.model.AccountingRecordUiModel
import co.nz.tsb.interview.bankrecmatchmaker.ui.model.ReconciliationUiState
import co.nz.tsb.interview.bankrecmatchmaker.ui.model.toDisplayString
import co.nz.tsb.interview.bankrecmatchmaker.util.MoneyFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReconciliationViewModel
    @Inject
    constructor(
        private val findMatchCandidatesUseCase: FindMatchCandidatesUseCase,
        private val moneyFormatter: MoneyFormatter,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ReconciliationUiState())
        val uiState: StateFlow<ReconciliationUiState> = _uiState.asStateFlow()

        private var transaction: BankTransaction? = null
        private var records: List<AccountingRecord> = emptyList()
        private var selectedRecordIds: Set<String> = emptySet()

        init {
            loadReconciliationData()
        }

        fun loadReconciliationData() {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(isLoading = true, errorMessage = null)
                }

                runCatching {
                    findMatchCandidatesUseCase()
                }.onSuccess { result ->
                    transaction = result.transaction
                    records = result.records

                    // For this exercise, auto-select the first available exact match candidate.
                    // In a real reconciliation product, if multiple candidates exist, the UI should
                    // be able to present all candidates (e.g. one by one through clicking a button)
                    // and let the user confirm the correct one.
                    selectedRecordIds =
                        result.matchedCandidates
                            .firstOrNull()
                            ?.records
                            ?.map { it.id }
                            ?.toSet()
                            .orEmpty()

                    publishUiState()
                }.onFailure {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Failed to load reconciliation data")
                    }
                }
            }
        }

        fun onRecordSelected(recordId: String) {
            selectedRecordIds =
                if (recordId in selectedRecordIds) {
                    selectedRecordIds - recordId
                } else {
                    selectedRecordIds + recordId
                }

            publishUiState()
        }

        private fun publishUiState() {
            val transaction = transaction ?: return

            val selectedTotalInCents =
                records
                    .filter { it.id in selectedRecordIds }
                    .sumOf { it.amountInCents }

            val remainingToMatchInCents =
                transaction.amountInCents - selectedTotalInCents

            _uiState.update {
                it.copy(
                    isLoading = false,
                    transactionAmountText = moneyFormatter.format(transaction.amountInCents),
                    remainingToMatchText = moneyFormatter.format(remainingToMatchInCents),
                    records =
                        records.map { record ->
                            AccountingRecordUiModel(
                                id = record.id,
                                paidTo = record.paidTo,
                                transactionDate = record.transactionDate,
                                amountText = moneyFormatter.format(record.amountInCents),
                                documentType = record.documentType.toDisplayString(),
                                isSelected = record.id in selectedRecordIds,
                            )
                        },
                    errorMessage = null,
                )
            }
        }
    }
