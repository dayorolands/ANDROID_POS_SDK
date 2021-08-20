package com.interswitchng.smartpos.emv.pax.services;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import com.pax.dal.IPrinter;
import com.pax.dal.entity.EFontTypeAscii;
import com.pax.dal.entity.EFontTypeExtCode;
import com.pax.dal.exceptions.PrinterDevException;


/**
 * This class serves as the intermediary between the PosDevice printer and the SDK
 */
public class PaxPrinter {

    final static int PRINT_STATUS_OK = 0;
    private static PaxPrinter mPaxPrinter;
    private IPrinter printer;

    private PaxPrinter() {
        printer = POSDeviceImpl.getDal().getPrinter();
    }


    public static PaxPrinter getInstance() {
        if (mPaxPrinter == null) {
            mPaxPrinter = new PaxPrinter();
        }
        return mPaxPrinter;
    }

    public void init() {
        try {
            printer.init();

            logTrue("init");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("init", e.toString());
        }
    }

    void logTrue(String m) {
        Log.d("CardEx", m);
    }

    void logErr(String e, String s) {

        logTrue(e + " " + s);
    }

    public Pair<Integer, String> getStatus() {
        try {
            int status = printer.getStatus();
            logTrue("getStatus");
            return statusCode2Str(status);
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("getStatus", e.toString());
            return new Pair<>(0x0F, e.getMessage());
        }

    }

    public void fontSet(EFontTypeAscii asciiFontType, EFontTypeExtCode cFontType) {
        try {
            printer.fontSet(asciiFontType, cFontType);
            logTrue("fontSet");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("fontSet", e.toString());
        }

    }

    public void spaceSet(byte wordSpace, byte lineSpace) {
        try {
            printer.spaceSet(wordSpace, lineSpace);
            logTrue("spaceSet");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("spaceSet", e.toString());
        }
    }

    public void printStr(String str, String charset) {
        try {
            int status = printer.getStatus();
            printer.printStr(str, charset);
            logTrue("printStr");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("printStr", e.toString());
        }

    }

    public void step(int b) {
        try {
            printer.step(b);
            logTrue("setStep");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("setStep", e.toString());
        }
    }

    public void printBitmap(Bitmap bitmap) {
        try {
            printer.printBitmap(bitmap);
            logTrue("printBitmap");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("printBitmap", e.toString());
        }
    }

    public Pair<Integer, String> printBitmapSlip(Bitmap bitmap) {
        try {
            printer.printBitmap(bitmap);
            logTrue("printBitmap");
            return statusCode2Str(0);
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("printBitmap", e.toString());
            return new Pair<>(0x0F, e.getMessage());
        }
    }

    public Pair<Integer, String> start() {
        try {
            int res = printer.start();
            logTrue("start");
            return statusCode2Str(res);
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("start", e.toString());
            return new Pair<>(0x0F, e.getMessage());
        }

    }

    public void leftIndents(short indent) {
        try {
            printer.leftIndent(indent);
            logTrue("leftIndent");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("leftIndent", e.toString());
        }
    }

    public int getDotLine() {
        try {
            int dotLine = printer.getDotLine();
            logTrue("getDotLine");
            return dotLine;
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("getDotLine", e.toString());
            return -2;
        }
    }

    public void setGray(int level) {
        try {
            printer.setGray(level);
            logTrue("setGray");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("setGray", e.toString());
        }

    }

    public void setDoubleWidth(boolean isAscDouble, boolean isLocalDouble) {
        try {
            printer.doubleWidth(isAscDouble, isLocalDouble);
            logTrue("doubleWidth");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("doubleWidth", e.toString());
        }
    }

    public void setDoubleHeight(boolean isAscDouble, boolean isLocalDouble) {
        try {
            printer.doubleHeight(isAscDouble, isLocalDouble);
            logTrue("doubleHeight");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("doubleHeight", e.toString());
        }

    }

    public void setInvert(boolean isInvert) {
        try {
            printer.invert(isInvert);
            logTrue("setInvert");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("setInvert", e.toString());
        }

    }

    public String cutPaper(int mode) {
        try {
            printer.cutPaper(mode);
            logTrue("cutPaper");
            return "cut paper successful";
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("cutPaper", e.toString());
            return e.toString();
        }
    }

    public String getCutMode() {
        String resultStr = "";
        try {
            int mode = printer.getCutMode();
            logTrue("getCutMode");
            switch (mode) {
                case 0:
                    resultStr = "Only support full paper cut";
                    break;
                case 1:
                    resultStr = "Only support partial paper cutting ";
                    break;
                case 2:
                    resultStr = "support partial paper and full paper cutting ";
                    break;
                case -1:
                    resultStr = "No cutting knife,not support";
                    break;
                default:
                    break;
            }
            return resultStr;
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("getCutMode", e.toString());
            return e.toString();
        }
    }

    public Pair<Integer, String> statusCode2Str(int status) {
        String res = "";
        switch (status) {
            case PRINT_STATUS_OK:
                res = "Success ";
                break;
            case 1:
                res = "DevicePrinterImpl is busy ";
                break;
            case 2:
                res = "Out of paper ";
                break;
            case 3:
                res = "The format of print data packet error ";
                break;
            case 4:
                res = "DevicePrinterImpl malfunctions ";
                break;
            case 8:
                res = "DevicePrinterImpl over heats ";
                break;
            case 9:
                res = "DevicePrinterImpl voltage is too low";
                break;
            case 240:
                res = "Printing is unfinished ";
                break;
            case 252:
                res = " The printer has not installed font library ";
                break;
            case 254:
                res = "Data package is too long ";
                break;
            default:
                break;
        }

        logTrue(res);
        return new Pair<>(status,res);
    }
}
