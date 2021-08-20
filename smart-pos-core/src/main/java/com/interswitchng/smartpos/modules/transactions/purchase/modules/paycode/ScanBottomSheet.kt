package com.interswitchng.smartpos.modules.transactions.purchase.modules.paycode

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.DecodeHintType
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.google.zxing.client.android.Intents
import com.interswitchng.smartpos.R
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import kotlinx.android.synthetic.main.isw_content_scan_bottom_sheet.*


internal class ScanBottomSheet : BottomSheetDialogFragment() {
    private lateinit var beepManager: BeepManager
    private val scanCallback = ScannerCallback()
    private var askedPermission = false
    private var scanResultCallback: ScanResultCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ISW_FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.isw_content_scan_bottom_sheet, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val sheetDialog = it as BottomSheetDialog
            val bottomSheet: FrameLayout = sheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        barcodeScannerView.barcodeView.decoderFactory = DefaultDecoderFactory(formats, mutableMapOf<DecodeHintType, Any>(), "")
        barcodeScannerView.initializeFromIntent(getScanIntent())
        barcodeScannerView.decodeSingle(scanCallback)

        beepManager = BeepManager(requireActivity())

        // set close listener
        closeBtn.setOnClickListener { cancel(CODE_SCAN_CANCELLED) }
    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= 23) openCameraWithPermission()
        else barcodeScannerView.resume()

        beepManager.updatePrefs()
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerView.pause()
    }

    override fun onStart() {
        super.onStart()
        barcodeScannerView.decodeSingle(scanCallback)
    }

    private fun getScanIntent(): Intent {
        return Intent()
                .putExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, false)
                .putExtra(Intents.Scan.BEEP_ENABLED, true)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scanResultCallback = parentFragment as ScanResultCallback
    }

    override fun onDetach() {
        super.onDetach()
        scanResultCallback = null
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun hasCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }

    @TargetApi(23)
    private fun openCameraWithPermission() {

        if (hasCameraPermission()) {
            barcodeScannerView.resume()
        } else if (!askedPermission) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), cameraPermissionReqCode)
            askedPermission = true
        } else {
            // reset flag
            askedPermission = false

            // show reason alert
            AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.isw_app_name))
                    .setMessage(getString(R.string.isw_message_camera_permission))
                    .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss(); openCameraWithPermission() }
                    .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss(); cancel(CODE_CAMERA_PERMISSION_DENIED) }
                    .setOnCancelListener { cancel(CODE_SCAN_CANCELLED) }
                    .show()
        }

        // Wait for permission result
    }


    private fun cancel(code: Int) {
        scanResultCallback?.onScanError(code)
        dismiss()
    }


    inner class ScannerCallback : BarcodeCallback {

        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null) {
                toast("Unable to get scanned result, cancel and try again")
                return
            }
            toast(result.text)

            // return result and dismiss
            beepManager.playBeepSoundAndVibrate()
            scanResultCallback?.onScanComplete(result.text)
            dismiss()
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    interface ScanResultCallback {

        fun onScanError(code: Int)

        fun onScanComplete(result: String)
    }

    companion object {
        private var cameraPermissionReqCode = 250

        const val CODE_SCAN_CANCELLED = 123
        const val CODE_CAMERA_PERMISSION_DENIED = 124
        const val CODE_OTHER_ERROR = -111

        fun newInstance() = ScanBottomSheet()
    }
}