package com.interswitchng.smartpos.shared.interfaces.device

/**
 * This interface represents the POS device to be used by the library,
 * providing access to its printer and card reader.
 */
interface POSDevice {

    /**
     * The name of the POS terminal device
     */
    val name: String


    /**
     * The flag to check if POSDevice supports finger print
     */
    val hasFingerPrintReader: Boolean

    /**
     * A member implementation of the printer [DevicePrinter]
     */
    val printer: DevicePrinter



    /**
     * Returns the emv card reader implementation [EmvCardReader]
     * that processes emv card chip transactions
     */
    fun getEmvCardReader(): EmvCardReader

    /**
     * Function to load DUKPT Key (BDK) into the pos device
     *
     * @param initialKey the hex string containing the DUKPT initial key to be loaded into the terminal
     * @param ksn the initial key's serial number
     */
    fun loadInitialKey(initialKey: String, ksn: String)


    /**
     * Function to load NIBSS MASTER Key into the pos device
     *
     * @param masterKey the hex string containing the Master key
     */
    fun loadMasterKey(masterKey: String)


    /**
     * Function to load NIBSS PIN Key into the pos device
     *
     * @param pinKey the hex string containing the PIN key
     */
    fun loadPinKey(pinKey: String)


}