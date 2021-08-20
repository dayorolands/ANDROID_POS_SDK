package com.interswitchng.smartpos.shared.models.posconfig


/**
 * This class represents the configuration for a textual print-out
 *
 * - isTitle determines if the text is a Title
 * - isBold indicates the text should be bold
 * - displayCenter indicates that the text should be centralized
 */
data class PrintStringConfiguration(
        val isTitle: Boolean = false,
        val isBold: Boolean = false,
        val displayCenter: Boolean = false
)