package co.nz.tsb.interview.bankrecmatchmaker.util

import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoneyFormatter
    @Inject
    constructor() {
        private val currencyFormatter: NumberFormat =
            NumberFormat.getCurrencyInstance(Locale.US).apply {
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }

        fun format(amountInCents: Long): String = currencyFormatter.format(amountInCents / 100.0)
    }
