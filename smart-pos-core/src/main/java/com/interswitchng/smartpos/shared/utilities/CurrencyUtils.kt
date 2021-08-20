package com.interswitchng.smartpos.shared.utilities

import java.util.*


internal object CurrencyUtils {
    private val currencyLocaleMap: SortedMap<Currency, Locale>

    private val locale = mapOf(
            "936" to "GHS",
            "566" to "NGN",
            "710" to "ZAR"
    )

    init {
        currencyLocaleMap = TreeMap(Comparator<Currency> { c1, c2 -> c1.currencyCode.compareTo(c2.currencyCode) })
        for (locale in Locale.getAvailableLocales()) {
            try {
                val currency = Currency.getInstance(locale)
                currencyLocaleMap[currency] = locale
            } catch (e: Exception) { }
        }
    }


    fun getCurrencySymbol(currencyCode: String): String {
        return this.locale[currencyCode]?.let {
            val currency = Currency.getInstance(it)
            return currency.getSymbol(currencyLocaleMap[currency])
        } ?: ""
    }
}