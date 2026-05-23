package co.nz.tsb.interview.bankrecmatchmaker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.nz.tsb.interview.bankrecmatchmaker.R
import co.nz.tsb.interview.bankrecmatchmaker.ui.model.AccountingRecordUiModel

class MatchAdapter(
    private val onItemClicked: (String) -> Unit,
) : ListAdapter<AccountingRecordUiModel, MatchAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val listItem =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_match, parent, false) as CheckedListItem

        return ViewHolder(
            listItem = listItem,
            onItemClicked = onItemClicked,
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val listItem: CheckedListItem,
        private val onItemClicked: (String) -> Unit,
    ) : RecyclerView.ViewHolder(listItem) {
        private val mainText: TextView = listItem.findViewById(R.id.text_main)
        private val total: TextView = listItem.findViewById(R.id.text_total)
        private val subtextLeft: TextView = listItem.findViewById(R.id.text_sub_left)
        private val subtextRight: TextView = listItem.findViewById(R.id.text_sub_right)

        fun bind(item: AccountingRecordUiModel) {
            mainText.text = item.paidTo
            total.text = item.amountText
            subtextLeft.text = item.transactionDate
            subtextRight.text = item.documentType

            listItem.isChecked = item.isSelected
            listItem.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<AccountingRecordUiModel>() {
        override fun areItemsTheSame(
            oldItem: AccountingRecordUiModel,
            newItem: AccountingRecordUiModel,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AccountingRecordUiModel,
            newItem: AccountingRecordUiModel,
        ): Boolean = oldItem == newItem
    }
}
