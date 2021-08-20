package com.interswitchng.smartpos.shared.interfaces.library

import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType

/**
 * This interface serves as a communication channel to emv card reader,
 * providing hooks that notify the consumer of the card reader's status.
 *
 */
interface EmvCallback {

    /**
     * Indicates when to show the user to insert emv card
     */
    fun showInsertCard()

    /**
     * Indicates when a card has been detected
     */
    fun onCardDetected()

    /**
     * Indicates when the information from a card has been read, and
     * passes the type of card as its parameter
     *
     * @param cardType  the type of emv card that was read by the card reader
     *
     */
    fun onCardRead(cardType: CardType)

    /**
     * Indicates when the card has been abruptly removed
     */
    fun onCardRemoved()

    /**
     * Indicates when to show the user a pin-pad to insert the card pin
     */
    fun showEnterPin()

    /**
     * Indicates characters that have been typed into the pin-pad
     *
     * @param text  an asterix filled string with the length of characters typed by the user
     */
    fun setPinText(text: String)

    /**
     * Indicates when the pin provided has been successfully validated by the card as OK
     */
    fun showPinOk()

    /**
     * Indicates when the current transaction has been cancelled either by the user or card or reader
     *
     * @param code an integer indicating the error code
     * @param reason a text providing the description of the error code
     */
    fun onTransactionCancelled(code: Int, reason: String)

    /**
     * Indicates when the user has entered the wrong pin, and the remaining count is provided
     *
     * @param remainCount an integer indicating the number of tries left to input a pin
     */
    fun showPinError(remainCount: Int)
}