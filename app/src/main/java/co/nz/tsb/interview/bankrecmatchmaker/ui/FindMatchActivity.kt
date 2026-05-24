package co.nz.tsb.interview.bankrecmatchmaker.ui

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.nz.tsb.interview.bankrecmatchmaker.R
import co.nz.tsb.interview.bankrecmatchmaker.ui.model.FindMatchUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToLong

@AndroidEntryPoint
class FindMatchActivity : AppCompatActivity() {
    private val viewModel: FindMatchViewModel by viewModels()

    private lateinit var matchText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var adapter: MatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        matchText = findViewById(R.id.match_text)
        progressBar = findViewById(R.id.progress_bar)
        errorText = findViewById(R.id.error_text)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_find_match)

        adapter =
            MatchAdapter(
                onItemClicked = viewModel::onRecordSelected,
            )

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        observeUiState()

        val targetAmountInCents = readTargetAmountInCents()
        viewModel.loadReconciliationData(targetAmountInCents)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::render)
            }
        }
    }

    private fun render(state: FindMatchUiState) {
        progressBar.isVisible = state.isLoading

        errorText.isVisible = state.errorMessage != null
        errorText.text = state.errorMessage

        matchText.text =
            getString(
                R.string.select_matches,
                state.remainingToMatchText,
            )

        adapter.submitList(state.records)
    }

    private fun readTargetAmountInCents(): Long {
        val targetAmount =
            intent.getFloatExtra(
                TARGET_MATCH_VALUE,
                DEFAULT_TARGET_AMOUNT,
            )

        return (targetAmount * 100).roundToLong()
    }

    companion object {
        const val TARGET_MATCH_VALUE = "co.nz.tsb.interview.target_match_value"
        private const val DEFAULT_TARGET_AMOUNT = 10_000f

        // Set to 108.6f to demonstrate Task 2 auto-selection behaviour.
        // In production the value could come from navigation arguments.
//        private const val DEFAULT_TARGET_AMOUNT = 108.6f

        // Set to 944.09f to demonstrate Task 3 auto-selection behaviour.
        // Please go to FindMatchViewModel.kt and uncomment the backtracking solution
//        private const val DEFAULT_TARGET_AMOUNT = 944.09f
    }
}
