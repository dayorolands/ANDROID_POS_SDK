package com.interswitchng.smartpos.shared.services.utils

import java.text.SimpleDateFormat
import java.util.*

internal object DateUtils {

    @JvmField
    val timeAndDateFormatter = SimpleDateFormat("MMddHHmmss", Locale.ROOT)  // field 7

    @JvmField
    val timeFormatter = SimpleDateFormat("HHmmss", Locale.ROOT) // field 12

    @JvmField
    val monthFormatter = SimpleDateFormat("MMdd", Locale.ROOT) // field 13

    val dateFormatter by lazy {  SimpleDateFormat("yyMMdd", Locale.ROOT) }

    val yearAndMonthFormatter by lazy {  SimpleDateFormat("yyMM", Locale.ROOT) }

    val timeOfDateFormat by lazy {  SimpleDateFormat("hh:mm aa, MMMM dd, yyyy", Locale.ROOT) }

    val shortDateFormat by lazy {  SimpleDateFormat( "dd MMMM, yyyy", Locale.ROOT) }

    val universalDateFormat by lazy {  SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.sssZ", Locale.ROOT) }

    val hourMinuteFormat by lazy { SimpleDateFormat("HH:mm", Locale.ROOT) }

    val dayMonthFormat by lazy { SimpleDateFormat("EEE, MMM d", Locale.ROOT) }
}