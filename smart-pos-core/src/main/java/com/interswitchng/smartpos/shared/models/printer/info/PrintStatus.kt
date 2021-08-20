package com.interswitchng.smartpos.shared.models.printer.info

/**
 * This is a sum type that represents the result of a printout
 * indicating whether the print was successful or not
 *
 * - OK class indicates that print was successful
 * - Error class captures what error prevented the printout
 */
sealed class PrintStatus(val message: String) {
    class Ok(message: String): PrintStatus(message)
    class Error(message: String): PrintStatus(message)
}