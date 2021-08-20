package com.interswitchng.smartpos.shared.models.transaction.cardpaycode


/**
 * This class serves as a communication channel to emv card reader,
 * providing hooks that notify the consumer of the card reader's status.
 *
 */
sealed class EmvMessage {

    /**
     * Indicates when to show the user to insert emv card
     */
    object InsertCard: EmvMessage()

    /**
     * Indicates when a card has been detected
     */
    object CardDetected: EmvMessage()

    /**
     * Indicates when the information from a card has been read, and
     * passes the type of card as its parameter
     *
     * @param cardType  the type of emv card that was read by the card reader
     *
     */
    class CardRead(val cardType: CardType, val cardPan: String): EmvMessage()

    /**
     * Indicates when the card has been abruptly removed
     */
    object CardRemoved: EmvMessage()

    /**
     * Indicates when to show the user a pin-pad to insert the card pin
     */
    object EnterPin: EmvMessage()

    /**
     * Indicates characters that have been typed into the pin-pad
     *
     * @param text  an asterix filled string with the length of characters typed by the user
     */
    class PinText(val text: String): EmvMessage()

    /**
     * Indicates when the user has entered the wrong pin, and the remaining count is provided
     *
     * @param remainCount an integer indicating the number of tries left to input a pin
     */
    class PinError(val remainCount: Int): EmvMessage()

    /**
     * Indicates when a user inputs a incomplete pin
     */
    object IncompletePin: EmvMessage()

    /**
     * Indicates when the pin provided has been successfully validated by the card as OK
     */
    object PinOk: EmvMessage()

    /**
     * Indicates when the current transaction has been cancelled either by the user or card or reader
     *
     * @param code an integer indicating the error code
     * @param reason a text providing the description of the error code
     */
    class TransactionCancelled(val code: Int, val reason: String): EmvMessage()

    /**
     * Indicates when the transaction is currently being processed online
     */

    object ProcessingTransaction: EmvMessage()

}