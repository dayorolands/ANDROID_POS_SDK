package com.interswitchng.smartpos.shared.interfaces.device

import android.graphics.Bitmap
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus


/**
 * This interface provides access to the POS device's printer functionality. It is up to each device to
 * specify how they want to handle printing slips.
 */

interface DevicePrinter {

    /**
     * Prints the slip (@link [PrintObject]) containing all the information to be printed, and the
     * user argument is used to know which user this copy of slip is for. The method returns a
     * print status, indicating the success or failure of the print operation.
     *
     * @param slip a list of printable objects that contain information to be printed out
     * @return     the status of this print operation, whether it was successful or not
     * @see PrintStatus
     */
    fun printSlip(slip: List<PrintObject>, isReceipt: Boolean = true): PrintStatus

    /**
     * Checks the devices's printer to determine if it is available to perform a print operation, and returns
     * a print status indicating success or failure of trying to print.
     *
     * @return     print status indicating success or failure of trying to perform a print operation
     * @see   PrintStatus
     */
    fun canPrint(): PrintStatus

    /**
     * Prints the company logo that has been configured at terminal setup.
     */
    fun printCompanyLogo()

}