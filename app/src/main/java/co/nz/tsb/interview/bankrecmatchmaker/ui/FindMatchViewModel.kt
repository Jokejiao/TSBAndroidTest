package co.nz.tsb.interview.bankrecmatchmaker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecord
import co.nz.tsb.interview.bankrecmatchmaker.domain.FindMatchUseCase
import co.nz.tsb.interview.bankrecmatchmaker.ui.model.AccountingRecordUiModel
import co.nz.tsb.interview.bankrecmatchmaker.ui.model.FindMatchUiState
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
class FindMatchViewModel
    @Inject
    constructor(
        private val findMatchUseCase: FindMatchUseCase,
        private val moneyFormatter: MoneyFormatter,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FindMatchUiState())
        val uiState: StateFlow<FindMatchUiState> = _uiState.asStateFlow()

        private var targetAmountInCents: Long = 0L
        private var records: List<AccountingRecord> = emptyList()
        private var selectedRecordIds: Set<String> = emptySet()

        fun loadReconciliationData(targetAmountInCents: Long) {
            // Avoid reloading/recalculating after configuration changes when ViewModel state is already available.
            if (records.isNotEmpty() && this.targetAmountInCents == targetAmountInCents) {
                publishUiState()
                return
            }

            this.targetAmountInCents = targetAmountInCents

            viewModelScope.launch {
                _uiState.update {
                    it.copy(isLoading = true, errorMessage = null)
                }

                runCatching {
                    findMatchUseCase(targetAmountInCents)
                }.onSuccess { result ->
                    records = result.records

                    // For this exercise, auto-select the first available exact match candidate.
                    // In a real reconciliation product, if multiple candidates exist, the UI should
                    // be able to present all candidates (e.g. one by one through clicking a button)
                    // and let the user confirm the correct one.
                    selectedRecordIds =
                        result.matchCandidates
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
            val selectedTotalInCents =
                records
                    .filter { it.id in selectedRecordIds }
                    .sumOf { it.amountInCents }

            val remainingToMatchInCents =
                targetAmountInCents - selectedTotalInCents

            _uiState.update {
                it.copy(
                    isLoading = false,
                    transactionAmountText = moneyFormatter.format(targetAmountInCents),
                    remainingToMatchText = moneyFormatter.format(remainingToMatchInCents),
                    records =
                        records.map { record ->
                            AccountingRecordUiModel(
                                id = record.id,
                                paidTo = record.paidTo,
                                transactionDate = record.transactionDate,
                                amountText = moneyFormatter.format(record.amountInCents),
                                documentType = record.documentType.toDisplayString(),
                                isSelected = record.id in selectedRecordIds, // Checkbox state is fully driven by UI state (UDF).
                            )
                        },
                    errorMessage = null,
                )
            }
        }
    }
