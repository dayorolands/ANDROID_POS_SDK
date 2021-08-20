package com.interswitch.smartpos.emv.telpo.emv;

import android.util.Log;

import com.telpo.emv.EmvApp;
import com.telpo.emv.EmvCAPK;
import com.telpo.emv.EmvService;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class DefaultAPPCAPK {
    // ASCII charset
    private static Charset charset = Charset.forName("ascii");

    public static void Log(String mes) {
        Log.w("test", mes);
    }


    public static void Add_All_CAPK_Test(){
        int result = 0;
        int capkID = 0;
        boolean dbResult = false;
        //CAPKDB capkdb = new CAPKDB();
        //CAPKDBDao capkdbDao = new CAPKDBDao(mContext);
        //这是EMV level2 测试规范要求的CAPK
        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_80 = new EmvCAPK();
        capk_pobc_80.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_80.KeyID = (byte)0x80;
        capk_pobc_80.HashInd = (byte)0x01;
        capk_pobc_80.ArithInd = (byte)0x01;
        capk_pobc_80.Modul = new  byte[]{
                (byte)0xCC,(byte)0xDB,(byte)0xA6,(byte)0x86,(byte)0xE2,(byte)0xEF,(byte)0xB8,(byte)0x4C,(byte)
                0xE2,(byte)0xEA,(byte)0x01,(byte)0x20,(byte)0x9E,(byte)0xEB,(byte)0x53,(byte)0xBE,(byte)
                0xF2,(byte)0x1A,(byte)0xB6,(byte)0xD3,(byte)0x53,(byte)0x27,(byte)0x4F,(byte)0xF8,(byte)
                0x39,(byte)0x1D,(byte)0x70,(byte)0x35,(byte)0xD7,(byte)0x6E,(byte)0x21,(byte)0x56,(byte)
                0xCA,(byte)0xED,(byte)0xD0,(byte)0x75,(byte)0x10,(byte)0xE0,(byte)0x7D,(byte)0xAF,(byte)
                0xCA,(byte)0xCA,(byte)0xBB,(byte)0x7C,(byte)0xCB,(byte)0x09,(byte)0x50,(byte)0xBA,(byte)
                0x2F,(byte)0x0A,(byte)0x3C,(byte)0xEC,(byte)0x31,(byte)0x3C,(byte)0x52,(byte)0xEE,(byte)
                0x6C,(byte)0xD0,(byte)0x9E,(byte)0xF0,(byte)0x04,(byte)0x01,(byte)0xA3,(byte)0xD6,(byte)
                0xCC,(byte)0x5F,(byte)0x68,(byte)0xCA,(byte)0x5F,(byte)0xCD,(byte)0x0A,(byte)0xC6,(byte)
                0x13,(byte)0x21,(byte)0x41,(byte)0xFA,(byte)0xFD,(byte)0x1C,(byte)0xFA,(byte)0x36,(byte)
                0xA2,(byte)0x69,(byte)0x2D,(byte)0x02,(byte)0xDD,(byte)0xC2,(byte)0x7E,(byte)0xDA,(byte)
                0x4C,(byte)0xD5,(byte)0xBE,(byte)0xA6,(byte)0xFF,(byte)0x21,(byte)0x91,(byte)0x3B,(byte)
                0x51,(byte)0x3C,(byte)0xE7,(byte)0x8B,(byte)0xF3,(byte)0x3E,(byte)0x68,(byte)0x77,(byte)
                0xAA,(byte)0x5B,(byte)0x60,(byte)0x5B,(byte)0xC6,(byte)0x9A,(byte)0x53,(byte)0x4F,(byte)
                0x37,(byte)0x77,(byte)0xCB,(byte)0xED,(byte)0x63,(byte)0x76,(byte)0xBA,(byte)0x64,(byte)
                0x9C,(byte)0x72,(byte)0x51,(byte)0x6A,(byte)0x7E,(byte)0x16,(byte)0xAF,(byte)0x85
        };
        capk_pobc_80.Exponent = new byte[]{0x01,0x00,0x01};
        capk_pobc_80.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_80.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };
        result =  EmvService.Emv_AddCapk(capk_pobc_80);
        Log("Add CAPK capk_pobc_80:" + result + " ID:" + capk_pobc_80.KeyID);
        if(result == EmvService.EMV_TRUE){
            //capkdb = new CAPKDB();
            //DataExchange.CAPKtoDB(capk_pobc_80,capkdb);
            //capkdb.bEnable = true;
            //capkdb.CAPKID = capkID++;
            //dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_80 database:" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_C0 = new EmvCAPK();
        capk_pobc_C0.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_C0.KeyID = (byte)0xC0;
        capk_pobc_C0.HashInd = (byte)0x01;
        capk_pobc_C0.ArithInd = (byte)0x01;
        capk_pobc_C0.Modul = new  byte[]{
                (byte)0xC7,(byte)0xCD,(byte)0xB6,(byte)0xF2,(byte)0xA3,(byte)0xFE,(byte)0x80,(byte)0xA8,(byte)
                0x83,(byte)0x4C,(byte)0xDD,(byte)0xDD,(byte)0x32,(byte)0x6E,(byte)0x10,(byte)0x82,(byte)
                0xAA,(byte)0x22,(byte)0x88,(byte)0xF4,(byte)0x7C,(byte)0x46,(byte)0x4D,(byte)0x57,(byte)
                0xB3,(byte)0x47,(byte)0x18,(byte)0x19,(byte)0x34,(byte)0x31,(byte)0x71,(byte)0x1A,(byte)
                0x44,(byte)0x11,(byte)0x91,(byte)0x48,(byte)0x05,(byte)0x50,(byte)0x44,(byte)0xCF,(byte)
                0xE3,(byte)0x31,(byte)0x37,(byte)0x08,(byte)0xBE,(byte)0xD0,(byte)0xC9,(byte)0x8E,(byte)
                0x1C,(byte)0x58,(byte)0x9B,(byte)0x0F,(byte)0x53,(byte)0xCF,(byte)0x6D,(byte)0x7E,(byte)
                0x82,(byte)0x9F,(byte)0xCD,(byte)0x90,(byte)0x6D,(byte)0x21,(byte)0xA9,(byte)0x0F,(byte)
                0xD4,(byte)0xCB,(byte)0x6B,(byte)0xAF,(byte)0x13,(byte)0x11,(byte)0x0C,(byte)0x46,(byte)
                0x85,(byte)0x10,(byte)0x7C,(byte)0x27,(byte)0xE0,(byte)0x09,(byte)0x81,(byte)0xDB,(byte)
                0x29,(byte)0xDC,(byte)0x0A,(byte)0xC1,(byte)0x86,(byte)0xE6,(byte)0xD7,(byte)0x01,(byte)
                0x57,(byte)0x7F,(byte)0x23,(byte)0x86,(byte)0x56,(byte)0x26,(byte)0x24,(byte)0x4E,(byte)
                0x1F,(byte)0x9B,(byte)0x2C,(byte)0xD1,(byte)0xDD,(byte)0xFC,(byte)0xB9,(byte)0xE8,(byte)
                0x99,(byte)0xB4,(byte)0x1F,(byte)0x50,(byte)0x84,(byte)0xD8,(byte)0xCC,(byte)0xC1,(byte)
                0x78,(byte)0xA7,(byte)0xC3,(byte)0xF4,(byte)0x54,(byte)0x6C,(byte)0xF9,(byte)0x31,(byte)
                0x87,(byte)0x10,(byte)0x6F,(byte)0xAB,(byte)0x05,(byte)0x5A,(byte)0x7A,(byte)0xC6,(byte)
                0x7D,(byte)0xF6,(byte)0x2E,(byte)0x77,(byte)0x8C,(byte)0xB8,(byte)0x88,(byte)0x23,(byte)
                0xBA,(byte)0x58,(byte)0xCF,(byte)0x75,(byte)0x46,(byte)0xC2,(byte)0xB0,(byte)0x9F
        };
        capk_pobc_C0.Exponent = new byte[]{0x01,0x00,0x01};
        capk_pobc_C0.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_C0.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_C0);
        Log("Add CAPK capk_pobc_C0:" + result + " ID:" + capk_pobc_C0.KeyID);
        if(result == EmvService.EMV_TRUE){
            //capkdb = new CAPKDB();
            //DataExchange.CAPKtoDB(capk_pobc_C0,capkdb);
            //capkdb.bEnable = true;
            //capkdb.CAPKID = capkID++;
            //dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_C0 database:" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_C1 = new EmvCAPK();
        capk_pobc_C1.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_C1.KeyID = (byte)0xC1;
        capk_pobc_C1.HashInd = (byte)0x01;
        capk_pobc_C1.ArithInd = (byte)0x01;
        capk_pobc_C1.Modul = new  byte[]{
                (byte)0x92,(byte)0xF0,(byte)0x83,(byte)0xCB,(byte)0xE4,(byte)0x6F,(byte)0x8D,(byte)0xCC,(byte)
                0x0C,(byte)0x04,(byte)0xE4,(byte)0x98,(byte)0xBA,(byte)0x99,(byte)0x52,(byte)0xBA,(byte)
                0x9D,(byte)0x4C,(byte)0x09,(byte)0xC8,(byte)0x0D,(byte)0xD2,(byte)0x77,(byte)0xE5,(byte)
                0x79,(byte)0xF0,(byte)0x7E,(byte)0x45,(byte)0x77,(byte)0x28,(byte)0x46,(byte)0xFA,(byte)
                0x43,(byte)0xDD,(byte)0x3A,(byte)0xB3,(byte)0x1C,(byte)0xC6,(byte)0xB0,(byte)0x8D,(byte)
                0xD1,(byte)0x86,(byte)0x95,(byte)0x71,(byte)0x59,(byte)0x49,(byte)0xFB,(byte)0x10,(byte)
                0x8E,(byte)0x53,(byte)0xA0,(byte)0x71,(byte)0xD3,(byte)0x93,(byte)0xA7,(byte)0xFD,(byte)
                0xDB,(byte)0xF9,(byte)0xC5,(byte)0xFB,(byte)0x0B,(byte)0x05,(byte)0x07,(byte)0x13,(byte)
                0x87,(byte)0x97,(byte)0x31,(byte)0x74,(byte)0x80,(byte)0xFC,(byte)0x48,(byte)0xD6,(byte)
                0x33,(byte)0xED,(byte)0x38,(byte)0xB4,(byte)0x01,(byte)0xA4,(byte)0x51,(byte)0x44,(byte)
                0x3A,(byte)0xD7,(byte)0xF1,(byte)0x5F,(byte)0xAC,(byte)0xDA,(byte)0x45,(byte)0xA6,(byte)
                0x2A,(byte)0xBE,(byte)0x24,(byte)0xFF,(byte)0x63,(byte)0x43,(byte)0xAD,(byte)0xD0,(byte)
                0x90,(byte)0x9E,(byte)0xA8,(byte)0x38,(byte)0x93,(byte)0x48,(byte)0xE5,(byte)0x4E,(byte)
                0x26,(byte)0xF8,(byte)0x42,(byte)0x88,(byte)0x0D,(byte)0x1A,(byte)0x69,(byte)0xF9,(byte)
                0x21,(byte)0x43,(byte)0x68,(byte)0xBA,(byte)0x30,(byte)0xC1,(byte)0x8D,(byte)0xE5,(byte)
                0xC5,(byte)0xE0,(byte)0xCB,(byte)0x92,(byte)0x53,(byte)0xB5,(byte)0xAB,(byte)0xC5,(byte)
                0x5F,(byte)0xB6,(byte)0xEF,(byte)0x0A,(byte)0x73,(byte)0x8D,(byte)0x92,(byte)0x74,(byte)
                0x94,(byte)0xA3,(byte)0x0B,(byte)0xBF,(byte)0x82,(byte)0xE3,(byte)0x40,(byte)0x28,(byte)
                0x53,(byte)0x63,(byte)0xB6,(byte)0xFA,(byte)0xA1,(byte)0x56,(byte)0x73,(byte)0x82,(byte)
                0x9D,(byte)0xBB,(byte)0x21,(byte)0x0E,(byte)0x71,(byte)0x0D,(byte)0xA5,(byte)0x8E,(byte)
                0xE9,(byte)0xE5,(byte)0x78,(byte)0xE7,(byte)0xCE,(byte)0x55,(byte)0xDC,(byte)0x81,(byte)
                0x2A,(byte)0xB7,(byte)0xD6,(byte)0xDC,(byte)0xCE,(byte)0x0E,(byte)0x3B,(byte)0x1A,(byte)
                0xE1,(byte)0x79,(byte)0xD6,(byte)0x64,(byte)0xF3,(byte)0x35,(byte)0x6E,(byte)0xB9,(byte)
                0x51,(byte)0xE3,(byte)0xC9,(byte)0x1A,(byte)0x1C,(byte)0xBB,(byte)0xF6,(byte)0xA7,(byte)
                0xCA,(byte)0x8D,(byte)0x0C,(byte)0x7E,(byte)0xC9,(byte)0xC6,(byte)0xAF,(byte)0x7A,(byte)
                0x49,(byte)0x41,(byte)0xC5,(byte)0x05,(byte)0x10,(byte)0x99,(byte)0xB9,(byte)0x78,(byte)
                0x4E,(byte)0x56,(byte)0xC9,(byte)0x16,(byte)0x20,(byte)0x67,(byte)0xB8,(byte)0xC3,(byte)
                0xB1,(byte)0x5C,(byte)0x5F,(byte)0xA4,(byte)0x48,(byte)0x0A,(byte)0x64,(byte)0x5C,(byte)
                0xD2,(byte)0x52,(byte)0x6A,(byte)0x69,(byte)0xC8,(byte)0x0B,(byte)0xA8,(byte)0xEF,(byte)
                0x36,(byte)0x1B,(byte)0xE2,(byte)0xAA,(byte)0x94,(byte)0x17,(byte)0xDE,(byte)0xFC,(byte)
                0xE3,(byte)0x5B,(byte)0x62,(byte)0xB0,(byte)0xC9,(byte)0xCF,(byte)0x09,(byte)0x7D
        };
        capk_pobc_C1.Exponent = new byte[]{0x01,0x00,0x01};
        capk_pobc_C1.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_C1.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_C1);
        Log("Add CAPK capk_pobc_C1:" + result + " ID:" + capk_pobc_C1.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_C1,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_C1 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_61 = new EmvCAPK();
        capk_pobc_61.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_61.KeyID = (byte)0x61;
        capk_pobc_61.HashInd = (byte)0x01;
        capk_pobc_61.ArithInd = (byte)0x01;
        capk_pobc_61.Modul = new  byte[]{
                (byte)0x83,(byte)0x4D,(byte)0x2A,(byte)0x38,(byte)0x7C,(byte)0x5A,(byte)0x5F,(byte)0x17,(byte)
                0x6E,(byte)0xF3,(byte)0xE6,(byte)0x6C,(byte)0xAA,(byte)0xF8,(byte)0x3F,(byte)0x19,(byte)
                0x4B,(byte)0x15,(byte)0xAA,(byte)0xD2,(byte)0x47,(byte)0x0C,(byte)0x78,(byte)0xC7,(byte)
                0x7D,(byte)0x6E,(byte)0xB3,(byte)0x8E,(byte)0xDA,(byte)0xE3,(byte)0xA2,(byte)0xF9,(byte)
                0xBA,(byte)0x16,(byte)0x23,(byte)0xF6,(byte)0xA5,(byte)0x8C,(byte)0x89,(byte)0x2C,(byte)
                0xC9,(byte)0x25,(byte)0x63,(byte)0x2D,(byte)0xFF,(byte)0x48,(byte)0xCE,(byte)0x95,(byte)
                0x4B,(byte)0x21,(byte)0xA5,(byte)0x3E,(byte)0x1F,(byte)0x1E,(byte)0x43,(byte)0x66,(byte)
                0xBE,(byte)0x40,(byte)0x3C,(byte)0x27,(byte)0x9B,(byte)0x90,(byte)0x02,(byte)0x7C,(byte)
                0xBC,(byte)0x72,(byte)0x60,(byte)0x5D,(byte)0xB6,(byte)0xC7,(byte)0x90,(byte)0x49,(byte)
                0xB8,(byte)0x99,(byte)0x2C,(byte)0xB4,(byte)0x91,(byte)0x2E,(byte)0xFA,(byte)0x27,(byte)
                0x0B,(byte)0xEC,(byte)0xAB,(byte)0x3A,(byte)0x7C,(byte)0xEF,(byte)0xE0,(byte)0x5B,(byte)
                0xFA,(byte)0x46,(byte)0xE4,(byte)0xC7,(byte)0xBB,(byte)0xCF,(byte)0x7C,(byte)0x7A,(byte)
                0x17,(byte)0x3B,(byte)0xD9,(byte)0x88,(byte)0xD9,(byte)0x89,(byte)0xB3,(byte)0x2C,(byte)
                0xB7,(byte)0x9F,(byte)0xAC,(byte)0x8E,(byte)0x35,(byte)0xFB,(byte)0xE1,(byte)0x86,(byte)
                0x0E,(byte)0x7E,(byte)0xA9,(byte)0xF2,(byte)0x38,(byte)0xA9,(byte)0x2A,(byte)0x35,(byte)
                0x93,(byte)0x55,(byte)0x2D,(byte)0x03,(byte)0xD1,(byte)0xE3,(byte)0x86,(byte)0x01
        };
        capk_pobc_61.Exponent = new byte[]{0x03};
        capk_pobc_61.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_61.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_61);
        Log("Add CAPK capk_pobc_61:" + result + " ID:" + capk_pobc_61.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_61,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_61 database:" + dbResult);
        }
        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_62 = new EmvCAPK();
        capk_pobc_62.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_62.KeyID = (byte)0x62;
        capk_pobc_62.HashInd = (byte)0x01;
        capk_pobc_62.ArithInd = (byte)0x01;
        capk_pobc_62.Modul = new  byte[]{
                (byte)0xB5,(byte)0xCD,(byte)0xD1,(byte)0xE5,(byte)0x36,(byte)0x88,(byte)0x19,(byte)0xFC,(byte)
                0x3E,(byte)0xA6,(byte)0x5B,(byte)0x80,(byte)0xC6,(byte)0x81,(byte)0x17,(byte)0xBB,(byte)
                0xC2,(byte)0x9F,(byte)0x90,(byte)0x96,(byte)0xEB,(byte)0xD2,(byte)0x17,(byte)0x26,(byte)
                0x9B,(byte)0x58,(byte)0x3B,(byte)0x07,(byte)0x45,(byte)0xE0,(byte)0xC1,(byte)0x64,(byte)
                0x33,(byte)0xD5,(byte)0x4B,(byte)0x8E,(byte)0xF3,(byte)0x87,(byte)0xB1,(byte)0xE6,(byte)
                0xCD,(byte)0xDA,(byte)0xED,(byte)0x49,(byte)0x23,(byte)0xC3,(byte)0x9E,(byte)0x37,(byte)
                0x0E,(byte)0x5C,(byte)0xAD,(byte)0xFE,(byte)0x04,(byte)0x17,(byte)0x73,(byte)0x02,(byte)
                0x3A,(byte)0x6B,(byte)0xC0,(byte)0xA0,(byte)0x33,(byte)0xB0,(byte)0x03,(byte)0x1B,(byte)
                0x00,(byte)0x48,(byte)0xF1,(byte)0x8A,(byte)0xC1,(byte)0x59,(byte)0x77,(byte)0x3C,(byte)
                0xB6,(byte)0x69,(byte)0x5E,(byte)0xE9,(byte)0x9F,(byte)0x55,(byte)0x1F,(byte)0x41,(byte)
                0x48,(byte)0x83,(byte)0xFB,(byte)0x05,(byte)0xE5,(byte)0x26,(byte)0x40,(byte)0xE8,(byte)
                0x93,(byte)0xF4,(byte)0x81,(byte)0x60,(byte)0x82,(byte)0x24,(byte)0x1D,(byte)0x7B,(byte)
                0xFA,(byte)0x36,(byte)0x40,(byte)0x96,(byte)0x00,(byte)0x03,(byte)0xAD,(byte)0x75,(byte)
                0x17,(byte)0x89,(byte)0x5C,(byte)0x50,(byte)0xE1,(byte)0x84,(byte)0xAA,(byte)0x95,(byte)
                0x63,(byte)0x67,(byte)0xB7,(byte)0xBF,(byte)0xFC,(byte)0x6D,(byte)0x86,(byte)0x16,(byte)
                0xA7,(byte)0xB5,(byte)0x7E,(byte)0x2D,(byte)0x44,(byte)0x7A,(byte)0xB3,(byte)0xE1
        };
        capk_pobc_62.Exponent = new byte[]{0x01,0x00,0x01};
        capk_pobc_62.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_62.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_62);
        Log("Add CAPK capk_pobc_62:" + result);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_62,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_62 database:" + dbResult);
        }
        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_63 = new EmvCAPK();
        capk_pobc_63.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_63.KeyID = (byte)0x63;
        capk_pobc_63.HashInd = (byte)0x01;
        capk_pobc_63.ArithInd = (byte)0x01;
        capk_pobc_63.Modul = new  byte[]{
                (byte)0x86,(byte)0x7E,(byte)0xCA,(byte)0x26,(byte)0xA5,(byte)0x74,(byte)0x72,(byte)0xDE,(byte)
                0xFB,(byte)0x6C,(byte)0xA9,(byte)0x42,(byte)0x89,(byte)0x31,(byte)0x2B,(byte)0xA3,(byte)
                0x9C,(byte)0x63,(byte)0x05,(byte)0x25,(byte)0x18,(byte)0xDC,(byte)0x48,(byte)0x0B,(byte)
                0x6E,(byte)0xD4,(byte)0x91,(byte)0xAC,(byte)0xC3,(byte)0x7C,(byte)0x02,(byte)0x88,(byte)
                0x46,(byte)0xF4,(byte)0xD7,(byte)0xB7,(byte)0x9A,(byte)0xFA,(byte)0xEE,(byte)0xFA,(byte)
                0x07,(byte)0xFB,(byte)0x01,(byte)0x1D,(byte)0xAA,(byte)0x46,(byte)0xC0,(byte)0x60,(byte)
                0x21,(byte)0xE9,(byte)0x32,(byte)0xD5,(byte)0x01,(byte)0xBF,(byte)0x52,(byte)0xF2,(byte)
                0x83,(byte)0x4A,(byte)0xDE,(byte)0x3A,(byte)0xC7,(byte)0x68,(byte)0x9E,(byte)0x94,(byte)
                0xB2,(byte)0x48,(byte)0xB2,(byte)0x8F,(byte)0x3F,(byte)0xE2,(byte)0x80,(byte)0x36,(byte)
                0x69,(byte)0xDE,(byte)0xDA,(byte)0x00,(byte)0x09,(byte)0x88,(byte)0xDA,(byte)0x12,(byte)
                0x49,(byte)0xF9,(byte)0xA8,(byte)0x91,(byte)0x55,(byte)0x8A,(byte)0x05,(byte)0xA1,(byte)
                0xE5,(byte)0xA7,(byte)0xBD,(byte)0x2C,(byte)0x28,(byte)0x2F,(byte)0xE1,(byte)0x8D,(byte)
                0x20,(byte)0x41,(byte)0x89,(byte)0xA9,(byte)0x99,(byte)0x4D,(byte)0x4A,(byte)0xDD,(byte)
                0x86,(byte)0xC0,(byte)0xCE,(byte)0x50,(byte)0x95,(byte)0x2E,(byte)0xD8,(byte)0xBC,(byte)
                0xEC,(byte)0x0C,(byte)0xE6,(byte)0x33,(byte)0x67,(byte)0x91,(byte)0x88,(byte)0x28,(byte)
                0x5E,(byte)0x51,(byte)0xE1,(byte)0xBE,(byte)0xD8,(byte)0x40,(byte)0xFC,(byte)0xBF,(byte)
                0xC1,(byte)0x09,(byte)0x53,(byte)0x93,(byte)0x9A,(byte)0xF4,(byte)0x9D,(byte)0xB9,(byte)
                0x00,(byte)0x48,(byte)0x91,(byte)0x2E,(byte)0x48,(byte)0xB4,(byte)0x41,(byte)0x81
        };
        capk_pobc_63.Exponent = new byte[]{0x03};
        capk_pobc_63.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_63.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_63);
        Log("Add CAPK capk_pobc_63:" + result + " ID:" + capk_pobc_63.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_63,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_63 database:" + dbResult);
        }
        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_64 = new EmvCAPK();
        capk_pobc_64.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_64.KeyID = (byte)0x64;
        capk_pobc_64.HashInd = (byte)0x01;
        capk_pobc_64.ArithInd = (byte)0x01;
        capk_pobc_64.Modul = new  byte[]{
                (byte)0x91,(byte)0x12,(byte)0x3E,(byte)0xCF,(byte)0x02,(byte)0x30,(byte)0xE3,(byte)0xCB,(byte)
                0x24,(byte)0x5C,(byte)0x88,(byte)0xDD,(byte)0xFA,(byte)0x3E,(byte)0xE5,(byte)0x7B,(byte)
                0xC5,(byte)0x8E,(byte)0xD0,(byte)0x0B,(byte)0x36,(byte)0x7B,(byte)0x38,(byte)0x75,(byte)
                0xFC,(byte)0xB7,(byte)0x95,(byte)0x48,(byte)0x87,(byte)0x26,(byte)0x80,(byte)0xF6,(byte)
                0x01,(byte)0xE8,(byte)0xC8,(byte)0x39,(byte)0xAC,(byte)0x07,(byte)0x21,(byte)0xBA,(byte)
                0xB3,(byte)0xB8,(byte)0x9E,(byte)0xD2,(byte)0x16,(byte)0x07,(byte)0x28,(byte)0x1C,(byte)
                0x89,(byte)0x19,(byte)0xBF,(byte)0x72,(byte)0x62,(byte)0x66,(byte)0xEA,(byte)0xB8,(byte)
                0x48,(byte)0x50,(byte)0x2A,(byte)0xD8,(byte)0x74,(byte)0xB5,(byte)0x10,(byte)0x7A,(byte)
                0x4E,(byte)0x65,(byte)0x4E,(byte)0xF6,(byte)0xD3,(byte)0x77,(byte)0x73,(byte)0x34,(byte)
                0x3F,(byte)0x46,(byte)0x14,(byte)0x35,(byte)0xC8,(byte)0x6E,(byte)0x4A,(byte)0x8F,(byte)
                0x86,(byte)0x6F,(byte)0xB1,(byte)0x8C,(byte)0x7C,(byte)0xBA,(byte)0x49,(byte)0x7B,(byte)
                0x42,(byte)0x62,(byte)0x90,(byte)0xC3,(byte)0x8D,(byte)0x19,(byte)0x6E,(byte)0x2A,(byte)
                0xFF,(byte)0x33,(byte)0xC0,(byte)0x90,(byte)0x6F,(byte)0x92,(byte)0x96,(byte)0xF2,(byte)
                0x97,(byte)0xE1,(byte)0x56,(byte)0xDC,(byte)0x60,(byte)0x2A,(byte)0x5E,(byte)0x65,(byte)
                0x3C,(byte)0xA1,(byte)0x16,(byte)0x8F,(byte)0x11,(byte)0x09,(byte)0x26,(byte)0x11,(byte)
                0x14,(byte)0xBF,(byte)0x7B,(byte)0xE8,(byte)0x12,(byte)0x7A,(byte)0x3E,(byte)0x80,(byte)
                0x07,(byte)0x19,(byte)0x18,(byte)0x30,(byte)0x13,(byte)0x42,(byte)0x99,(byte)0x39,(byte)
                0x5C,(byte)0xE2,(byte)0xB3,(byte)0x22,(byte)0x22,(byte)0x86,(byte)0x67,(byte)0xB7,(byte)
                0x6E,(byte)0x07,(byte)0x2E,(byte)0xB7,(byte)0xFD,(byte)0x5D,(byte)0x0F,(byte)0xB3,(byte)
                0xA8,(byte)0x3E,(byte)0x8A,(byte)0xD1,(byte)0xD7,(byte)0xF6,(byte)0xFD,(byte)0x81
        };
        capk_pobc_64.Exponent = new byte[]{0x03};
        capk_pobc_64.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_64.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_64);
        Log("Add CAPK capk_pobc_64:" + result + " ID:" + capk_pobc_64.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_64,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_64 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_65 = new EmvCAPK();
        capk_pobc_65.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_65.KeyID = (byte)0x65;
        capk_pobc_65.HashInd = (byte)0x01;
        capk_pobc_65.ArithInd = (byte)0x01;
        capk_pobc_65.Modul = new  byte[]{
                (byte)0x81,(byte)0xBA,(byte)0x1E,(byte)0x6B,(byte)0x9F,(byte)0x67,(byte)0x1C,(byte)0xFC,(byte)
                0x84,(byte)0x8C,(byte)0xA2,(byte)0xAC,(byte)0xD8,(byte)0xE1,(byte)0x7A,(byte)0xF4,(byte)
                0x06,(byte)0xB4,(byte)0xD3,(byte)0x29,(byte)0xD1,(byte)0xEC,(byte)0xA5,(byte)0xD0,(byte)
                0x1B,(byte)0xC0,(byte)0x94,(byte)0xA8,(byte)0x7C,(byte)0x30,(byte)0xAF,(byte)0x49,(byte)
                0x86,(byte)0x79,(byte)0x44,(byte)0xC6,(byte)0x32,(byte)0xE8,(byte)0x18,(byte)0x50,(byte)
                0x74,(byte)0x65,(byte)0x5F,(byte)0xA5,(byte)0x35,(byte)0xAD,(byte)0x8C,(byte)0xA4,(byte)
                0x2A,(byte)0x83,(byte)0xB4,(byte)0x1A,(byte)0xAA,(byte)0xEA,(byte)0x85,(byte)0x9F,(byte)
                0x43,(byte)0x2F,(byte)0xA0,(byte)0xB8,(byte)0x18,(byte)0xE7,(byte)0x2D,(byte)0xC0,(byte)
                0x7E,(byte)0xD3,(byte)0xF7,(byte)0x7F,(byte)0xB3,(byte)0x18,(byte)0xA4,(byte)0x75,(byte)
                0xA2,(byte)0x61,(byte)0xC0,(byte)0x76,(byte)0x0A,(byte)0x15,(byte)0x6E,(byte)0x5D,(byte)
                0xDC,(byte)0x15,(byte)0x7A,(byte)0xE8,(byte)0xB7,(byte)0x9B,(byte)0xA7,(byte)0x2D,(byte)
                0x89,(byte)0xD6,(byte)0x9F,(byte)0xFF,(byte)0x75,(byte)0x46,(byte)0x19,(byte)0xE9,(byte)
                0x28,(byte)0xF1,(byte)0x51,(byte)0x6A,(byte)0x2A,(byte)0x72,(byte)0xC0,(byte)0xF8,(byte)
                0x6B,(byte)0x09,(byte)0xB8,(byte)0xEA,(byte)0x25,(byte)0xF8,(byte)0x6D,(byte)0xC5,(byte)
                0xA4,(byte)0x8E,(byte)0xBC,(byte)0x5A,(byte)0x16,(byte)0xF8,(byte)0x3F,(byte)0xBA,(byte)
                0x8F,(byte)0xC4,(byte)0xE3,(byte)0xA9,(byte)0x82,(byte)0x78,(byte)0x91,(byte)0x22,(byte)
                0x49,(byte)0xF4,(byte)0xE0,(byte)0x79,(byte)0xBC,(byte)0xBC,(byte)0x06,(byte)0xE7,(byte)
                0xBE,(byte)0xD9,(byte)0xAE,(byte)0xD3,(byte)0x97,(byte)0x87,(byte)0x9D,(byte)0x27,(byte)
                0x9E,(byte)0xD9,(byte)0x19,(byte)0x25,(byte)0x39,(byte)0x49,(byte)0x01,(byte)0x26,(byte)
                0x09,(byte)0x49,(byte)0xBC,(byte)0xCE,(byte)0x6F,(byte)0xA1,(byte)0x16,(byte)0x97,(byte)
                0x98,(byte)0xA2,(byte)0x71,(byte)0x5D,(byte)0xAE,(byte)0x32,(byte)0x98,(byte)0x8B,(byte)
                0xEF,(byte)0xBE,(byte)0x96,(byte)0x21,(byte)0xAE,(byte)0x15,(byte)0xE0,(byte)0xC1
        };
        capk_pobc_65.Exponent = new byte[]{0x01,0x00,0x01};
        capk_pobc_65.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_65.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_65);
        Log("Add CAPK capk_pobc_65:" + result + " ID:" + capk_pobc_65.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_65,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_65 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_66 = new EmvCAPK();
        capk_pobc_66.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_66.KeyID = (byte)0x66;
        capk_pobc_66.HashInd = (byte)0x01;
        capk_pobc_66.ArithInd = (byte)0x01;
        capk_pobc_66.Modul = new  byte[]{
                (byte)0x7F,(byte)0x5A,(byte)0x39,(byte)0x45,(byte)0x79,(byte)0x4D,(byte)0x6B,(byte)0x15,(byte)
                0xF5,(byte)0xF2,(byte)0x6B,(byte)0x4A,(byte)0x21,(byte)0xA6,(byte)0x3A,(byte)0x5E,(byte)
                0xF3,(byte)0x55,(byte)0x40,(byte)0xD8,(byte)0xC8,(byte)0xC0,(byte)0x99,(byte)0x15,(byte)
                0x1F,(byte)0x22,(byte)0x79,(byte)0x78,(byte)0x0A,(byte)0x5C,(byte)0x18,(byte)0xA3,(byte)
                0x17,(byte)0x70,(byte)0x3C,(byte)0x98,(byte)0x63,(byte)0x2E,(byte)0x80,(byte)0x4D,(byte)
                0x25,(byte)0x57,(byte)0x6A,(byte)0x7B,(byte)0x46,(byte)0x0C,(byte)0x05,(byte)0x06,(byte)
                0x1E,(byte)0x03,(byte)0x97,(byte)0x5E,(byte)0x50,(byte)0xFB,(byte)0xD7,(byte)0x49,(byte)
                0x5B,(byte)0x3A,(byte)0xDC,(byte)0x8E,(byte)0x42,(byte)0x5E,(byte)0x53,(byte)0xDF,(byte)
                0x76,(byte)0xFA,(byte)0x40,(byte)0xB0,(byte)0x35,(byte)0xE8,(byte)0x7F,(byte)0x69,(byte)
                0xAB,(byte)0xF8,(byte)0x76,(byte)0x5A,(byte)0x52,(byte)0x52,(byte)0x3F,(byte)0x3B,(byte)
                0x1A,(byte)0x39,(byte)0xB1,(byte)0x95,(byte)0x28,(byte)0xB0,(byte)0x02,(byte)0x23,(byte)
                0x90,(byte)0x15,(byte)0xFA,(byte)0xDB,(byte)0xA5,(byte)0x92,(byte)0x10,(byte)0x51
        };
        capk_pobc_66.Exponent = new byte[]{0x01,0x00,0x01};
        capk_pobc_66.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_66.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_66);
        Log("Add CAPK capk_pobc_66:" + result + " ID:" + capk_pobc_66.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_66,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_66 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_05 = new EmvCAPK();
        capk_pobc_05.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_05.KeyID = (byte)0x05;
        capk_pobc_05.HashInd = (byte)0x01;
        capk_pobc_05.ArithInd = (byte)0x01;
        capk_pobc_05.Modul = new  byte[]{
                (byte)0x97,(byte)0xCF,(byte)0x8B,(byte)0xAD,(byte)0x30,(byte)0xCA,(byte)0xE0,(byte)0xF9,(byte)
                0xA8,(byte)0x92,(byte)0x85,(byte)0x45,(byte)0x4D,(byte)0xDD,(byte)0xE9,(byte)0x67,(byte)
                0xAA,(byte)0xFB,(byte)0xCD,(byte)0x4B,(byte)0xC0,(byte)0xB7,(byte)0x8F,(byte)0x29,(byte)
                0xEC,(byte)0xB1,(byte)0x00,(byte)0x52,(byte)0x86,(byte)0xF1,(byte)0x5F,(byte)0x6D,(byte)
                0x75,(byte)0x32,(byte)0xA9,(byte)0xC4,(byte)0x76,(byte)0x60,(byte)0x7C,(byte)0x73,(byte)
                0xFF,(byte)0x74,(byte)0x24,(byte)0x31,(byte)0x6D,(byte)0xFC,(byte)0x74,(byte)0x18,(byte)
                0x94,(byte)0xAA,(byte)0x52,(byte)0xED,(byte)0xBA,(byte)0xF9,(byte)0x09,(byte)0x71,(byte)
                0x9C,(byte)0x7B,(byte)0x53,(byte)0x44,(byte)0x83,(byte)0x43,(byte)0xB4,(byte)0x5C,(byte)
                0xF2,(byte)0xF0,(byte)0x0A,(byte)0x8A,(byte)0xBF,(byte)0xB7,(byte)0x8C,(byte)0xEE,(byte)
                0xBE,(byte)0x84,(byte)0x89,(byte)0x33,(byte)0xAA,(byte)0xED,(byte)0x97,(byte)0xDB,(byte)
                0xE8,(byte)0x4F,(byte)0x07,(byte)0x30,(byte)0xF3,(byte)0x4F,(byte)0xB1,(byte)0xAA,(byte)
                0x15,(byte)0x28,(byte)0xD3,(byte)0xD6,(byte)0xEC,(byte)0x75,(byte)0xB7,(byte)0x32,(byte)
                0x52,(byte)0xA3,(byte)0x0D,(byte)0x0C,(byte)0x71,(byte)0x75,(byte)0x18,(byte)0xBE,(byte)
                0x36,(byte)0x45,(byte)0x8A,(byte)0xDD,(byte)0x0F,(byte)0xBF,(byte)0x85,(byte)0x4C,(byte)
                0x65,(byte)0x49,(byte)0x7F,(byte)0x3F,(byte)0x54,(byte)0x08,(byte)0x41,(byte)0x54,(byte)
                0xB6,(byte)0x0F,(byte)0x51,(byte)0x56,(byte)0x13,(byte)0x61,(byte)0xEE,(byte)0x8E,(byte)
                0x85,(byte)0xF7,(byte)0x42,(byte)0xA5,(byte)0x40,(byte)0x05,(byte)0x52,(byte)0x4C,(byte)
                0xB0,(byte)0x0F,(byte)0xEB,(byte)0xC3,(byte)0x34,(byte)0x27,(byte)0x6E,(byte)0x0E,(byte)
                0x63,(byte)0xDA,(byte)0xD8,(byte)0x6C,(byte)0x07,(byte)0x9A,(byte)0x9A,(byte)0x3D,(byte)
                0xF5,(byte)0xDD,(byte)0x32,(byte)0xBE,(byte)0xCA,(byte)0xDE,(byte)0x1A,(byte)0xB2,(byte)
                0xB7,(byte)0x1F,(byte)0x5F,(byte)0x0A,(byte)0x0E,(byte)0x95,(byte)0xA4,(byte)0x00,(byte)
                0x0D,(byte)0x01,(byte)0xF1,(byte)0x04,(byte)0x4A,(byte)0x57,(byte)0x8A,(byte)0xAD,(byte)
                0x92,(byte)0xE9,(byte)0xFD,(byte)0xE9,(byte)0x2E,(byte)0x3C,(byte)0x6A,(byte)0xA3,(byte)
                0xDC,(byte)0xD4,(byte)0x91,(byte)0x3D,(byte)0xFA,(byte)0x55,(byte)0x52,(byte)0x53,(byte)
                0x7E,(byte)0x7D,(byte)0xE7,(byte)0x5E,(byte)0x24,(byte)0x1F,(byte)0xAE,(byte)0xD4,(byte)
                0x55,(byte)0xD7,(byte)0x6C,(byte)0xB8,(byte)0xFC,(byte)0xAF,(byte)0xEE,(byte)0xD3,(byte)
                0xFD,(byte)0x6D,(byte)0xAB,(byte)0x24,(byte)0xD7,(byte)0xA9,(byte)0xC3,(byte)0x28,(byte)
                0x52,(byte)0xF8,(byte)0x66,(byte)0xC7,(byte)0x51,(byte)0xD7,(byte)0x71,(byte)0x0F,(byte)
                0x49,(byte)0x4A,(byte)0x0D,(byte)0xF1,(byte)0x1B,(byte)0x67,(byte)0xFA,(byte)0xEC,(byte)
                0xDD,(byte)0x87,(byte)0xA9,(byte)0xA4,(byte)0xE2,(byte)0xCC,(byte)0x44,(byte)0xF6,(byte)
                0xF2,(byte)0x7E,(byte)0x46,(byte)0xE3,(byte)0xC0,(byte)0xCC,(byte)0xCD,(byte)0x0F
        };
        capk_pobc_05.Exponent = new byte[]{0x03};
        capk_pobc_05.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_05.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_05);
        Log("Add CAPK capk_pobc_05:" + result + " ID:" + capk_pobc_05.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_05,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_05 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_08 = new EmvCAPK();
        capk_pobc_08.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_08.KeyID = (byte)0x08;
        capk_pobc_08.HashInd = (byte)0x01;
        capk_pobc_08.ArithInd = (byte)0x01;
        capk_pobc_08.Modul = new  byte[]{
                (byte)0xB6,(byte)0x16,(byte)0x45,(byte)0xED,(byte)0xFD,(byte)0x54,(byte)0x98,(byte)0xFB,(byte)
                0x24,(byte)0x64,(byte)0x44,(byte)0x03,(byte)0x7A,(byte)0x0F,(byte)0xA1,(byte)0x8C,(byte)
                0x0F,(byte)0x10,(byte)0x1E,(byte)0xBD,(byte)0x8E,(byte)0xFA,(byte)0x54,(byte)0x57,(byte)
                0x3C,(byte)0xE6,(byte)0xE6,(byte)0xA7,(byte)0xFB,(byte)0xF6,(byte)0x3E,(byte)0xD2,(byte)
                0x1D,(byte)0x66,(byte)0x34,(byte)0x08,(byte)0x52,(byte)0xB0,(byte)0x21,(byte)0x1C,(byte)
                0xF5,(byte)0xEE,(byte)0xF6,(byte)0xA1,(byte)0xCD,(byte)0x98,(byte)0x9F,(byte)0x66,(byte)
                0xAF,(byte)0x21,(byte)0xA8,(byte)0xEB,(byte)0x19,(byte)0xDB,(byte)0xD8,(byte)0xDB,(byte)
                0xC3,(byte)0x70,(byte)0x6D,(byte)0x13,(byte)0x53,(byte)0x63,(byte)0xA0,(byte)0xD6,(byte)
                0x83,(byte)0xD0,(byte)0x46,(byte)0x30,(byte)0x4F,(byte)0x5A,(byte)0x83,(byte)0x6B,(byte)
                0xC1,(byte)0xBC,(byte)0x63,(byte)0x28,(byte)0x21,(byte)0xAF,(byte)0xE7,(byte)0xA2,(byte)
                0xF7,(byte)0x5D,(byte)0xA3,(byte)0xC5,(byte)0x0A,(byte)0xC7,(byte)0x4C,(byte)0x54,(byte)
                0x5A,(byte)0x75,(byte)0x45,(byte)0x62,(byte)0x20,(byte)0x41,(byte)0x37,(byte)0x16,(byte)
                0x96,(byte)0x63,(byte)0xCF,(byte)0xCC,(byte)0x0B,(byte)0x06,(byte)0xE6,(byte)0x7E,(byte)
                0x21,(byte)0x09,(byte)0xEB,(byte)0xA4,(byte)0x1B,(byte)0xC6,(byte)0x7F,(byte)0xF2,(byte)
                0x0C,(byte)0xC8,(byte)0xAC,(byte)0x80,(byte)0xD7,(byte)0xB6,(byte)0xEE,(byte)0x1A,(byte)
                0x95,(byte)0x46,(byte)0x5B,(byte)0x3B,(byte)0x26,(byte)0x57,(byte)0x53,(byte)0x3E,(byte)
                0xA5,(byte)0x6D,(byte)0x92,(byte)0xD5,(byte)0x39,(byte)0xE5,(byte)0x06,(byte)0x43,(byte)
                0x60,(byte)0xEA,(byte)0x48,(byte)0x50,(byte)0xFE,(byte)0xD2,(byte)0xD1,(byte)0xBF
        };
        capk_pobc_08.Exponent = new byte[]{0x03};
        capk_pobc_08.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_08.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_08);
        Log("Add CAPK capk_pobc_08:" + result + " ID:" + capk_pobc_08.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_08,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_08 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_09 = new EmvCAPK();
        capk_pobc_09.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_09.KeyID = (byte)0x08;
        capk_pobc_09.HashInd = (byte)0x01;
        capk_pobc_09.ArithInd = (byte)0x01;
        capk_pobc_09.Modul = new  byte[]{
                (byte)0xEB,(byte)0x37,(byte)0x4D,(byte)0xFC,(byte)0x5A,(byte)0x96,(byte)0xB7,(byte)0x1D,(byte)
                0x28,(byte)0x63,(byte)0x87,(byte)0x5E,(byte)0xDA,(byte)0x2E,(byte)0xAF,(byte)0xB9,(byte)
                0x6B,(byte)0x1B,(byte)0x43,(byte)0x9D,(byte)0x3E,(byte)0xCE,(byte)0x0B,(byte)0x18,(byte)
                0x26,(byte)0xA2,(byte)0x67,(byte)0x2E,(byte)0xEE,(byte)0xFA,(byte)0x79,(byte)0x90,(byte)
                0x28,(byte)0x67,(byte)0x76,(byte)0xF8,(byte)0xBD,(byte)0x98,(byte)0x9A,(byte)0x15,(byte)
                0x14,(byte)0x1A,(byte)0x75,(byte)0xC3,(byte)0x84,(byte)0xDF,(byte)0xC1,(byte)0x4F,(byte)
                0xEF,(byte)0x92,(byte)0x43,(byte)0xAA,(byte)0xB3,(byte)0x27,(byte)0x07,(byte)0x65,(byte)
                0x9B,(byte)0xE9,(byte)0xE4,(byte)0x79,(byte)0x7A,(byte)0x24,(byte)0x7C,(byte)0x2F,(byte)
                0x0B,(byte)0x6D,(byte)0x99,(byte)0x37,(byte)0x2F,(byte)0x38,(byte)0x4A,(byte)0xF6,(byte)
                0x2F,(byte)0xE2,(byte)0x3B,(byte)0xC5,(byte)0x4B,(byte)0xCD,(byte)0xC5,(byte)0x7A,(byte)
                0x9A,(byte)0xCD,(byte)0x1D,(byte)0x55,(byte)0x85,(byte)0xC3,(byte)0x03,(byte)0xF2,(byte)
                0x01,(byte)0xEF,(byte)0x4E,(byte)0x8B,(byte)0x80,(byte)0x6A,(byte)0xFB,(byte)0x80,(byte)
                0x9D,(byte)0xB1,(byte)0xA3,(byte)0xDB,(byte)0x1C,(byte)0xD1,(byte)0x12,(byte)0xAC,(byte)
                0x88,(byte)0x4F,(byte)0x16,(byte)0x4A,(byte)0x67,(byte)0xB9,(byte)0x9C,(byte)0x7D,(byte)
                0x6E,(byte)0x5A,(byte)0x8A,(byte)0x6D,(byte)0xF1,(byte)0xD3,(byte)0xCA,(byte)0xE6,(byte)
                0xD7,(byte)0xED,(byte)0x3D,(byte)0x5B,(byte)0xE7,(byte)0x25,(byte)0xB2,(byte)0xDE,(byte)
                0x4A,(byte)0xDE,(byte)0x23,(byte)0xFA,(byte)0x67,(byte)0x9B,(byte)0xF4,(byte)0xEB,(byte)
                0x15,(byte)0xA9,(byte)0x3D,(byte)0x8A,(byte)0x6E,(byte)0x29,(byte)0xC7,(byte)0xFF,(byte)
                0xA1,(byte)0xA7,(byte)0x0D,(byte)0xE2,(byte)0xE5,(byte)0x4F,(byte)0x59,(byte)0x3D,(byte)
                0x90,(byte)0x8A,(byte)0x3B,(byte)0xF9,(byte)0xEB,(byte)0xBD,(byte)0x76,(byte)0x0B,(byte)
                0xBF,(byte)0xDC,(byte)0x8D,(byte)0xB8,(byte)0xB5,(byte)0x44,(byte)0x97,(byte)0xE6,(byte)
                0xC5,(byte)0xBE,(byte)0x0E,(byte)0x4A,(byte)0x4D,(byte)0xAC,(byte)0x29,(byte)0xE5
        };
        capk_pobc_09.Exponent = new byte[]{0x03};
        capk_pobc_09.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_09.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_09);
        Log("Add CAPK capk_pobc_09:" + result + " ID:" + capk_pobc_09.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_09,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_09 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_0B = new EmvCAPK();
        capk_pobc_0B.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_0B.KeyID = (byte)0x0B;
        capk_pobc_0B.HashInd = (byte)0x01;
        capk_pobc_0B.ArithInd = (byte)0x01;
        capk_pobc_0B.Modul = new  byte[]{
                (byte)0xCF,(byte)0x9F,(byte)0xDF,(byte)0x46,(byte)0xB3,(byte)0x56,(byte)0x37,(byte)0x8E,(byte)
                0x9A,(byte)0xF3,(byte)0x11,(byte)0xB0,(byte)0xF9,(byte)0x81,(byte)0xB2,(byte)0x1A,(byte)
                0x1F,(byte)0x22,(byte)0xF2,(byte)0x50,(byte)0xFB,(byte)0x11,(byte)0xF5,(byte)0x5C,(byte)
                0x95,(byte)0x87,(byte)0x09,(byte)0xE3,(byte)0xC7,(byte)0x24,(byte)0x19,(byte)0x18,(byte)
                0x29,(byte)0x34,(byte)0x83,(byte)0x28,(byte)0x9E,(byte)0xAE,(byte)0x68,(byte)0x8A,(byte)
                0x09,(byte)0x4C,(byte)0x02,(byte)0xC3,(byte)0x44,(byte)0xE2,(byte)0x99,(byte)0x9F,(byte)
                0x31,(byte)0x5A,(byte)0x72,(byte)0x84,(byte)0x1F,(byte)0x48,(byte)0x9E,(byte)0x24,(byte)
                0xB1,(byte)0xBA,(byte)0x00,(byte)0x56,(byte)0xCF,(byte)0xAB,(byte)0x3B,(byte)0x47,(byte)
                0x9D,(byte)0x0E,(byte)0x82,(byte)0x64,(byte)0x52,(byte)0x37,(byte)0x5D,(byte)0xCD,(byte)
                0xBB,(byte)0x67,(byte)0xE9,(byte)0x7E,(byte)0xC2,(byte)0xAA,(byte)0x66,(byte)0xF4,(byte)
                0x60,(byte)0x1D,(byte)0x77,(byte)0x4F,(byte)0xEA,(byte)0xEF,(byte)0x77,(byte)0x5A,(byte)
                0xCC,(byte)0xC6,(byte)0x21,(byte)0xBF,(byte)0xEB,(byte)0x65,(byte)0xFB,(byte)0x00,(byte)
                0x53,(byte)0xFC,(byte)0x5F,(byte)0x39,(byte)0x2A,(byte)0xA5,(byte)0xE1,(byte)0xD4,(byte)
                0xC4,(byte)0x1A,(byte)0x4D,(byte)0xE9,(byte)0xFF,(byte)0xDF,(byte)0xDF,(byte)0x13,(byte)
                0x27,(byte)0xC4,(byte)0xBB,(byte)0x87,(byte)0x4F,(byte)0x1F,(byte)0x63,(byte)0xA5,(byte)
                0x99,(byte)0xEE,(byte)0x39,(byte)0x02,(byte)0xFE,(byte)0x95,(byte)0xE7,(byte)0x29,(byte)
                0xFD,(byte)0x78,(byte)0xD4,(byte)0x23,(byte)0x4D,(byte)0xC7,(byte)0xE6,(byte)0xCF,(byte)
                0x1A,(byte)0xBA,(byte)0xBA,(byte)0xA3,(byte)0xF6,(byte)0xDB,(byte)0x29,(byte)0xB7,(byte)
                0xF0,(byte)0x5D,(byte)0x1D,(byte)0x90,(byte)0x1D,(byte)0x2E,(byte)0x76,(byte)0xA6,(byte)
                0x06,(byte)0xA8,(byte)0xCB,(byte)0xFF,(byte)0xFF,(byte)0xEC,(byte)0xBD,(byte)0x91,(byte)
                0x8F,(byte)0xA2,(byte)0xD2,(byte)0x78,(byte)0xBD,(byte)0xB4,(byte)0x3B,(byte)0x04,(byte)
                0x34,(byte)0xF5,(byte)0xD4,(byte)0x51,(byte)0x34,(byte)0xBE,(byte)0x1C,(byte)0x27,(byte)
                0x81,(byte)0xD1,(byte)0x57,(byte)0xD5,(byte)0x01,(byte)0xFF,(byte)0x43,(byte)0xE5,(byte)
                0xF1,(byte)0xC4,(byte)0x70,(byte)0x96,(byte)0x7C,(byte)0xD5,(byte)0x7C,(byte)0xE5,(byte)
                0x3B,(byte)0x64,(byte)0xD8,(byte)0x29,(byte)0x74,(byte)0xC8,(byte)0x27,(byte)0x59,(byte)
                0x37,(byte)0xC5,(byte)0xD8,(byte)0x50,(byte)0x2A,(byte)0x12,(byte)0x52,(byte)0xA8,(byte)
                0xA5,(byte)0xD6,(byte)0x08,(byte)0x8A,(byte)0x25,(byte)0x9B,(byte)0x69,(byte)0x4F,(byte)
                0x98,(byte)0x64,(byte)0x8D,(byte)0x9A,(byte)0xF2,(byte)0xCB,(byte)0x0E,(byte)0xFD,(byte)
                0x9D,(byte)0x94,(byte)0x3C,(byte)0x69,(byte)0xF8,(byte)0x96,(byte)0xD4,(byte)0x9F,(byte)
                0xA3,(byte)0x97,(byte)0x02,(byte)0x16,(byte)0x2A,(byte)0xCB,(byte)0x5A,(byte)0xF2,(byte)
                0x9B,(byte)0x90,(byte)0xBA,(byte)0xDE,(byte)0x00,(byte)0x5B,(byte)0xC1,(byte)0x57
        };
        capk_pobc_0B.Exponent = new byte[]{0x03};
        capk_pobc_0B.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_0B.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_0B);
        Log("Add CAPK capk_pobc_0B:" + result + " ID:" + capk_pobc_0B.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_0B,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_0B database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_84 = new EmvCAPK();
        capk_pobc_84.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_84.KeyID = (byte)0x84;
        capk_pobc_84.HashInd = (byte)0x01;
        capk_pobc_84.ArithInd = (byte)0x01;
        capk_pobc_84.Modul = new  byte[]{
                (byte)0xF9,(byte)0xEA,(byte)0x55,(byte)0x03,(byte)0xCF,(byte)0xE4,(byte)0x30,(byte)0x38,(byte)
                0x59,(byte)0x6C,(byte)0x72,(byte)0x06,(byte)0x45,(byte)0xA9,(byte)0x4E,(byte)0x01,(byte)
                0x54,(byte)0x79,(byte)0x3D,(byte)0xE7,(byte)0x3A,(byte)0xE5,(byte)0xA9,(byte)0x35,(byte)
                0xD1,(byte)0xFB,(byte)0x9D,(byte)0x0F,(byte)0xE7,(byte)0x72,(byte)0x86,(byte)0xB6,(byte)
                0x12,(byte)0x61,(byte)0xE3,(byte)0xBB,(byte)0x1D,(byte)0x3D,(byte)0xFE,(byte)0xC5,(byte)
                0x47,(byte)0x44,(byte)0x99,(byte)0x92,(byte)0xE2,(byte)0x03,(byte)0x7C,(byte)0x01,(byte)
                0xFF,(byte)0x4E,(byte)0xFB,(byte)0x88,(byte)0xDA,(byte)0x8A,(byte)0x82,(byte)0xF3,(byte)
                0x0F,(byte)0xEA,(byte)0x31,(byte)0x98,(byte)0xD5,(byte)0xD1,(byte)0x67,(byte)0x54,(byte)
                0x24,(byte)0x7A,(byte)0x16,(byte)0x26,(byte)0xE9,(byte)0xCF,(byte)0xFB,(byte)0x4C,(byte)
                0xD9,(byte)0xE3,(byte)0x13,(byte)0x99,(byte)0x99,(byte)0x0E,(byte)0x43,(byte)0xFC,(byte)
                0xA7,(byte)0x7C,(byte)0x74,(byte)0x4A,(byte)0x93,(byte)0x68,(byte)0x5A,(byte)0x26,(byte)
                0x0A,(byte)0x20,(byte)0xE6,(byte)0xA6,(byte)0x07,(byte)0xF3,(byte)0xEE,(byte)0x3F,(byte)
                0xAE,(byte)0x2A,(byte)0xBB,(byte)0xE9,(byte)0x96,(byte)0x78,(byte)0xC9,(byte)0xF1,(byte)
                0x9D,(byte)0xFD,(byte)0x2D,(byte)0x8E,(byte)0xA7,(byte)0x67,(byte)0x89,(byte)0x23,(byte)
                0x9D,(byte)0x13,(byte)0x36,(byte)0x9D,(byte)0x7D,(byte)0x2D,(byte)0x56,(byte)0xAF,(byte)
                0x3F,(byte)0x27,(byte)0x93,(byte)0x06,(byte)0x89,(byte)0x50,(byte)0xB5,(byte)0xBD,(byte)
                0x80,(byte)0x8C,(byte)0x46,(byte)0x25,(byte)0x71,(byte)0x66,(byte)0x2D,(byte)0x43,(byte)
                0x64,(byte)0xB3,(byte)0x0A,(byte)0x25,(byte)0x82,(byte)0x95,(byte)0x9D,(byte)0xB2,(byte)
                0x38,(byte)0x33,(byte)0x3B,(byte)0xAD,(byte)0xAC,(byte)0xB4,(byte)0x42,(byte)0xF9,(byte)
                0x51,(byte)0x6B,(byte)0x5C,(byte)0x33,(byte)0x6C,(byte)0x8A,(byte)0x61,(byte)0x3F,(byte)
                0xE0,(byte)0x14,(byte)0xB7,(byte)0xD7,(byte)0x73,(byte)0x58,(byte)0x1A,(byte)0xE1,(byte)
                0x0F,(byte)0xDF,(byte)0x7B,(byte)0xDB,(byte)0x26,(byte)0x69,(byte)0x01,(byte)0x2D
        };
        capk_pobc_84.Exponent = new byte[]{0x03};
        capk_pobc_84.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_84.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_84);
        Log("Add CAPK capk_pobc_84:" + result + " ID:" + capk_pobc_84.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_84,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_84 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_85 = new EmvCAPK();
        capk_pobc_85.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33};
        capk_pobc_85.KeyID = (byte)0x85;
        capk_pobc_85.HashInd = (byte)0x01;
        capk_pobc_85.ArithInd = (byte)0x01;
        capk_pobc_85.Modul = new  byte[]{
                (byte)0xC9,(byte)0x24,(byte)0x2E,(byte)0xC6,(byte)0x03,(byte)0x0F,(byte)0x10,(byte)0xE5,(byte)
                0x22,(byte)0x5E,(byte)0x72,(byte)0x2A,(byte)0xA1,(byte)0x7D,(byte)0x9D,(byte)0xC8,(byte)
                0x94,(byte)0x29,(byte)0x92,(byte)0x33,(byte)0xAE,(byte)0xC3,(byte)0x21,(byte)0x9B,(byte)
                0x95,(byte)0x0D,(byte)0x4F,(byte)0x24,(byte)0x3A,(byte)0xF5,(byte)0x30,(byte)0xFA,(byte)
                0x13,(byte)0xE3,(byte)0xA3,(byte)0x1A,(byte)0xFA,(byte)0xA0,(byte)0xD4,(byte)0xBF,(byte)
                0x4D,(byte)0xE5,(byte)0x62,(byte)0xB6,(byte)0xB4,(byte)0xC3,(byte)0x10,(byte)0x8A,(byte)
                0xEB,(byte)0xBC,(byte)0x6C,(byte)0xB0,(byte)0x80,(byte)0xF9,(byte)0x07,(byte)0x70,(byte)
                0xD5,(byte)0x32,(byte)0xF2,(byte)0x41,(byte)0xBC,(byte)0x15,(byte)0x36,(byte)0x40,(byte)
                0x1E,(byte)0x1B,(byte)0xF7,(byte)0x2F,(byte)0x9D,(byte)0xC1,(byte)0xB0,(byte)0x89,(byte)
                0x33,(byte)0xB9,(byte)0xBF,(byte)0x77,(byte)0x40,(byte)0x3F,(byte)0x6A,(byte)0x0F,(byte)
                0xB5,(byte)0x77,(byte)0x7B,(byte)0xAA,(byte)0x4C,(byte)0x9B,(byte)0xE9,(byte)0x15,(byte)
                0x74,(byte)0xBB,(byte)0xBF,(byte)0xB5,(byte)0x21,(byte)0x34,(byte)0x2A,(byte)0x20,(byte)
                0x38,(byte)0x67,(byte)0x90,(byte)0x51,(byte)0x22,(byte)0x21,(byte)0xF4,(byte)0x77,(byte)
                0xFB,(byte)0xC5,(byte)0x3F,(byte)0xF1,(byte)0xB6,(byte)0x53,(byte)0x3A,(byte)0x01,(byte)
                0x58,(byte)0x15,(byte)0x43,(byte)0x54,(byte)0x10,(byte)0xEC,(byte)0x27,(byte)0x2F,(byte)
                0x0A,(byte)0x34,(byte)0xEA,(byte)0x07,(byte)0x35,(byte)0xC4,(byte)0x39,(byte)0x67,(byte)
                0x7D,(byte)0x7E,(byte)0x46,(byte)0xFB,(byte)0xA7,(byte)0x66,(byte)0xEC,(byte)0x00,(byte)
                0xCE,(byte)0xD5,(byte)0x9B,(byte)0x67,(byte)0x15,(byte)0xE3,(byte)0x41,(byte)0x2D,(byte)
                0x6F,(byte)0xB8,(byte)0xA9,(byte)0x34,(byte)0xBF,(byte)0x9D,(byte)0x14,(byte)0x97,(byte)
                0xA2,(byte)0x4A,(byte)0x62,(byte)0x52,(byte)0xC5,(byte)0x2D,(byte)0x75,(byte)0x86,(byte)
                0xFD,(byte)0x66,(byte)0xA4,(byte)0x50,(byte)0xFB,(byte)0x5D,(byte)0x2B,(byte)0x44,(byte)
                0x84,(byte)0xEC,(byte)0x92,(byte)0x30,(byte)0x61,(byte)0x43,(byte)0x96,(byte)0x22,(byte)
                0xBC,(byte)0x05,(byte)0x35,(byte)0x31,(byte)0x6C,(byte)0xD4,(byte)0x23,(byte)0x1C,(byte)
                0x13,(byte)0xC6,(byte)0x27,(byte)0xBF,(byte)0x4D,(byte)0x2E,(byte)0xDE,(byte)0x1C,(byte)
                0x02,(byte)0xC8,(byte)0x02,(byte)0x46,(byte)0x46,(byte)0x58,(byte)0xF1,(byte)0xB9,(byte)
                0xD7,(byte)0xFF,(byte)0x23,(byte)0xA3,(byte)0x69,(byte)0x85,(byte)0x10,(byte)0xFA,(byte)
                0x90,(byte)0xD0,(byte)0xC3,(byte)0x16,(byte)0x49,(byte)0x42,(byte)0xFB,(byte)0x35,(byte)
                0x92,(byte)0x55,(byte)0xCD,(byte)0x82,(byte)0x3C,(byte)0xB2,(byte)0x63,(byte)0x5B,(byte)
                0x3F,(byte)0x16,(byte)0x7F,(byte)0xBD,(byte)0xFC,(byte)0x90,(byte)0x06,(byte)0x41,(byte)
                0xB9,(byte)0x70,(byte)0xD6,(byte)0x02,(byte)0xA2,(byte)0x77,(byte)0x1A,(byte)0x7F,(byte)
                0x4F,(byte)0x94,(byte)0xDF,(byte)0x6D,(byte)0x34,(byte)0xBE,(byte)0x8B,(byte)0xBB
        };
        capk_pobc_85.Exponent = new byte[]{0x03};
        capk_pobc_85.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_pobc_85.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_pobc_85);
        Log("Add CAPK capk_pobc_85:" + result + " ID:" + capk_pobc_85.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_pobc_85,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_pobc_85 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_E1 = new EmvCAPK();
        capk_E1.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x99,(byte)0x99};
        capk_E1.KeyID = (byte)0xE1;
        capk_E1.HashInd = (byte)0x01;
        capk_E1.ArithInd = (byte)0x01;
        capk_E1.Modul = new  byte[]{
                (byte)0x99,(byte)0xC5,(byte)0xB7,(byte)0x0A,(byte)0xA6,(byte)0x1B,(byte)0x4F,(byte)0x4C,(byte)
                0x51,(byte)0xB6,(byte)0xF9,(byte)0x0B,(byte)0x0E,(byte)0x3B,(byte)0xFB,(byte)0x7A,(byte)
                0x3E,(byte)0xE0,(byte)0xE7,(byte)0xDB,(byte)0x41,(byte)0xBC,(byte)0x46,(byte)0x68,(byte)
                0x88,(byte)0xB3,(byte)0xEC,(byte)0x8E,(byte)0x99,(byte)0x77,(byte)0xC7,(byte)0x62,(byte)
                0x40,(byte)0x7E,(byte)0xF1,(byte)0xD7,(byte)0x9E,(byte)0x0A,(byte)0xFB,(byte)0x28,(byte)
                0x23,(byte)0x10,(byte)0x0A,(byte)0x02,(byte)0x0C,(byte)0x3E,(byte)0x80,(byte)0x20,(byte)
                0x59,(byte)0x3D,(byte)0xB5,(byte)0x0E,(byte)0x90,(byte)0xDB,(byte)0xEA,(byte)0xC1,(byte)
                0x8B,(byte)0x78,(byte)0xD1,(byte)0x3F,(byte)0x96,(byte)0xBB,(byte)0x2F,(byte)0x57,(byte)
                0xEE,(byte)0xDD,(byte)0xC3,(byte)0x0F,(byte)0x25,(byte)0x65,(byte)0x92,(byte)0x41,(byte)
                0x7C,(byte)0xDF,(byte)0x73,(byte)0x9C,(byte)0xA6,(byte)0x80,(byte)0x4A,(byte)0x10,(byte)
                0xA2,(byte)0x9D,(byte)0x28,(byte)0x06,(byte)0xE7,(byte)0x74,(byte)0xBF,(byte)0xA7,(byte)
                0x51,(byte)0xF2,(byte)0x2C,(byte)0xF3,(byte)0xB6,(byte)0x5B,(byte)0x38,(byte)0xF3,(byte)
                0x7F,(byte)0x91,(byte)0xB4,(byte)0xDA,(byte)0xF8,(byte)0xAE,(byte)0xC9,(byte)0xB8,(byte)
                0x03,(byte)0xF7,(byte)0x61,(byte)0x0E,(byte)0x06,(byte)0xAC,(byte)0x9E,(byte)0x6B
        };
        capk_E1.Exponent = new byte[]{0x03};
        capk_E1.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_E1.CheckSum = new byte[]{
                (byte)0xF8,(byte)0x70,(byte)0x7B,(byte)0x9B,(byte)0xED,(byte)0xF0,(byte)0x31,(byte)0xE5,(byte)0x8A,(byte)0x9F,(byte)0x84
                ,(byte)0x36,(byte)0x31,(byte)0xB9,(byte)0x0C,(byte)0x90,(byte)0xD8,(byte)0x0E,(byte)0xD6,(byte)0x95};

        result =  EmvService.Emv_AddCapk(capk_E1);
        Log("Add CAPK capk_E1:" + result + " ID:" + capk_E1.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_E1,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_E1 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_E2 = new EmvCAPK();
        capk_E2.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x99,(byte)0x99};
        capk_E2.KeyID = (byte)0xE2;
        capk_E2.HashInd = (byte)0x01;
        capk_E2.ArithInd = (byte)0x01;
        capk_E2.Modul = new  byte[]{
                (byte)0xBD,(byte)0x23,(byte)0x2E,(byte)0x34,(byte)0x8B,(byte)0x11,(byte)0x8E,(byte)0xB3,(byte)
                0xF6,(byte)0x44,(byte)0x6E,(byte)0xF4,(byte)0xDA,(byte)0x6C,(byte)0x3B,(byte)0xAC,(byte)
                0x9B,(byte)0x2A,(byte)0xE5,(byte)0x10,(byte)0xC5,(byte)0xAD,(byte)0x10,(byte)0x7D,(byte)
                0x38,(byte)0x34,(byte)0x32,(byte)0x55,(byte)0xD2,(byte)0x1C,(byte)0x4B,(byte)0xDF,(byte)
                0x49,(byte)0x52,(byte)0xA4,(byte)0x2E,(byte)0x92,(byte)0xC6,(byte)0x33,(byte)0xB1,(byte)
                0xCE,(byte)0x4B,(byte)0xFE,(byte)0xC3,(byte)0x9A,(byte)0xFB,(byte)0x6D,(byte)0xFE,(byte)
                0x14,(byte)0x7E,(byte)0xCB,(byte)0xB9,(byte)0x1D,(byte)0x68,(byte)0x1D,(byte)0xAC,(byte)
                0x15,(byte)0xFB,(byte)0x0E,(byte)0x19,(byte)0x8E,(byte)0x9A,(byte)0x7E,(byte)0x46,(byte)
                0x36,(byte)0xBD,(byte)0xCA,(byte)0x10,(byte)0x7B,(byte)0xCD,(byte)0xA3,(byte)0x38,(byte)
                0x4F,(byte)0xCB,(byte)0x28,(byte)0xB0,(byte)0x6A,(byte)0xFE,(byte)0xF9,(byte)0x0F,(byte)
                0x09,(byte)0x9E,(byte)0x70,(byte)0x84,(byte)0x51,(byte)0x1F,(byte)0x3C,(byte)0xC0,(byte)
                0x10,(byte)0xD4,(byte)0x34,(byte)0x35,(byte)0x03,(byte)0xE1,(byte)0xE5,(byte)0xA6,(byte)
                0x72,(byte)0x64,(byte)0xB4,(byte)0x36,(byte)0x7D,(byte)0xAA,(byte)0x9A,(byte)0x39,(byte)
                0x49,(byte)0x49,(byte)0x92,(byte)0x72,(byte)0xE9,(byte)0xB5,(byte)0x02,(byte)0x2F
        };
        capk_E2.Exponent = new byte[]{0x03};
        capk_E2.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_E2.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_E2);
        Log("Add CAPK capk_E2:" + result + " ID:" + capk_E2.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_E2,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_E2 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_E3 = new EmvCAPK();
        capk_E3.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x99,(byte)0x99};
        capk_E3.KeyID = (byte)0xE3;
        capk_E3.HashInd = (byte)0x01;
        capk_E3.ArithInd = (byte)0x01;
        capk_E3.Modul = new  byte[]{
                (byte)0xBC,(byte)0x01,(byte)0xE1,(byte)0x22,(byte)0x23,(byte)0xE1,(byte)0xA4,(byte)0x1E,(byte)
                0x88,(byte)0xBF,(byte)0xFA,(byte)0x80,(byte)0x10,(byte)0x93,(byte)0xC5,(byte)0xF8,(byte)
                0xCE,(byte)0xC5,(byte)0xCD,(byte)0x05,(byte)0xDB,(byte)0xBD,(byte)0xBB,(byte)0x78,(byte)
                0x7C,(byte)0xE8,(byte)0x72,(byte)0x49,(byte)0xE8,(byte)0x80,(byte)0x83,(byte)0x27,(byte)
                0xC2,(byte)0xD2,(byte)0x18,(byte)0x99,(byte)0x1F,(byte)0x97,(byte)0xA1,(byte)0x13,(byte)
                0x1E,(byte)0x8A,(byte)0x25,(byte)0xB0,(byte)0x12,(byte)0x2E,(byte)0xD1,(byte)0x1E,(byte)
                0x70,(byte)0x9C,(byte)0x53,(byte)0x3E,(byte)0x88,(byte)0x86,(byte)0xA1,(byte)0x25,(byte)
                0x9A,(byte)0xDD,(byte)0xFD,(byte)0xCB,(byte)0xB3,(byte)0x96,(byte)0x60,(byte)0x4D,(byte)
                0x24,(byte)0xE5,(byte)0x05,(byte)0xA2,(byte)0xD0,(byte)0xB5,(byte)0xDD,(byte)0x03,(byte)
                0x84,(byte)0xFB,(byte)0x00,(byte)0x02,(byte)0xA7,(byte)0xA1,(byte)0xEB,(byte)0x39,(byte)
                0xBC,(byte)0x8A,(byte)0x11,(byte)0x33,(byte)0x9C,(byte)0x7A,(byte)0x94,(byte)0x33,(byte)
                0xA9,(byte)0x48,(byte)0x33,(byte)0x77,(byte)0x61,(byte)0xBE,(byte)0x73,(byte)0xBC,(byte)
                0x49,(byte)0x7B,(byte)0x8E,(byte)0x58,(byte)0x73,(byte)0x6D,(byte)0xA4,(byte)0x63,(byte)
                0x65,(byte)0x38,(byte)0xAD,(byte)0x28,(byte)0x2D,(byte)0x3C,(byte)0xD3,(byte)0xDB
        };
        capk_E3.Exponent = new byte[]{0x01,0x00,0x01};
        capk_E3.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_E3.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_E3);
        Log("Add CAPK capk_E3:" + result + " ID:" + capk_E3.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_E3,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_E3 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_E4 = new EmvCAPK();
        capk_E4.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x99,(byte)0x99};
        capk_E4.KeyID = (byte)0xE4;
        capk_E4.HashInd = (byte)0x01;
        capk_E4.ArithInd = (byte)0x01;
        capk_E4.Modul = new  byte[]{
                (byte)0xCB,(byte)0xF2,(byte)0xE4,(byte)0x0F,(byte)0x08,(byte)0x36,(byte)0xC9,(byte)0xA5,(byte)
                0xE3,(byte)0x90,(byte)0xA3,(byte)0x7B,(byte)0xE3,(byte)0xB8,(byte)0x09,(byte)0xBD,(byte)
                0xF5,(byte)0xD7,(byte)0x40,(byte)0xCB,(byte)0x1D,(byte)0xA3,(byte)0x8C,(byte)0xFC,(byte)
                0x05,(byte)0xD5,(byte)0xF8,(byte)0xD6,(byte)0xB7,(byte)0x74,(byte)0x5B,(byte)0x5E,(byte)
                0x9A,(byte)0x3F,(byte)0xA6,(byte)0x96,(byte)0x1E,(byte)0x55,(byte)0xFF,(byte)0x20,(byte)
                0x41,(byte)0x21,(byte)0x08,(byte)0x52,(byte)0x5E,(byte)0x66,(byte)0xB9,(byte)0x70,(byte)
                0xF9,(byte)0x02,(byte)0xF7,(byte)0xFF,(byte)0x43,(byte)0x05,(byte)0xDD,(byte)0x83,(byte)
                0x2C,(byte)0xD0,(byte)0x76,(byte)0x3E,(byte)0x3A,(byte)0xA8,(byte)0xB8,(byte)0x17,(byte)
                0x3F,(byte)0x84,(byte)0x77,(byte)0x71,(byte)0x00,(byte)0xB1,(byte)0x04,(byte)0x7B,(byte)
                0xD1,(byte)0xD7,(byte)0x44,(byte)0x50,(byte)0x93,(byte)0x12,(byte)0xA0,(byte)0x93,(byte)
                0x2E,(byte)0xD2,(byte)0x5F,(byte)0xED,(byte)0x52,(byte)0xA9,(byte)0x59,(byte)0x43,(byte)
                0x07,(byte)0x68,(byte)0xCC,(byte)0xD9,(byte)0x02,(byte)0xFD,(byte)0x8C,(byte)0x8A,(byte)
                0xD9,(byte)0x12,(byte)0x3E,(byte)0x6A,(byte)0xDD,(byte)0xB3,(byte)0xF3,(byte)0x4B,(byte)
                0x92,(byte)0xE7,(byte)0x92,(byte)0x4D,(byte)0x72,(byte)0x9C,(byte)0xB6,(byte)0x47,(byte)
                0x35,(byte)0x33,(byte)0xAE,(byte)0x2B,(byte)0x2B,(byte)0x55,(byte)0xBF,(byte)0x0E,(byte)
                0x44,(byte)0x96,(byte)0x4F,(byte)0xDE,(byte)0xA8,(byte)0x44,(byte)0x01,(byte)0x17
        };
        capk_E4.Exponent = new byte[]{0x03};
        capk_E4.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_E4.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_E4);
        Log("Add CAPK capk_E4:" + result + " ID:" + capk_E4.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_E4,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_E4 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_E5 = new EmvCAPK();
        capk_E5.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x99,(byte)0x99};
        capk_E5.KeyID = (byte)0xE5;
        capk_E5.HashInd = (byte)0x01;
        capk_E5.ArithInd = (byte)0x01;
        capk_E5.Modul = new  byte[]{
                (byte)0xD4,(byte)0xFD,(byte)0xAE,(byte)0x94,(byte)0xDE,(byte)0xDB,(byte)0xEC,(byte)0xC6,(byte)
                0xD2,(byte)0x0D,(byte)0x38,(byte)0xB0,(byte)0x1E,(byte)0x91,(byte)0x82,(byte)0x6D,(byte)
                0xC6,(byte)0x95,(byte)0x43,(byte)0x38,(byte)0x37,(byte)0x99,(byte)0x17,(byte)0xB2,(byte)
                0xBB,(byte)0x8A,(byte)0x6B,(byte)0x36,(byte)0xB5,(byte)0xD3,(byte)0xB0,(byte)0xC5,(byte)
                0xED,(byte)0xA6,(byte)0x0B,(byte)0x33,(byte)0x74,(byte)0x48,(byte)0xBA,(byte)0xFF,(byte)
                0xEB,(byte)0xCC,(byte)0x3A,(byte)0xBD,(byte)0xBA,(byte)0x86,(byte)0x9E,(byte)0x8D,(byte)
                0xAD,(byte)0xEC,(byte)0x6C,(byte)0x87,(byte)0x01,(byte)0x10,(byte)0xC4,(byte)0x2F,(byte)
                0x5A,(byte)0xAB,(byte)0x90,(byte)0xA1,(byte)0x8F,(byte)0x4F,(byte)0x86,(byte)0x7F,(byte)
                0x72,(byte)0xE3,(byte)0x38,(byte)0x6F,(byte)0xFC,(byte)0x7E,(byte)0x67,(byte)0xE7,(byte)
                0xFF,(byte)0x94,(byte)0xEB,(byte)0xA0,(byte)0x79,(byte)0xE5,(byte)0x31,(byte)0xB3,(byte)
                0xCF,(byte)0x32,(byte)0x95,(byte)0x17,(byte)0xE8,(byte)0x1C,(byte)0x5D,(byte)0xD9,(byte)
                0xB3,(byte)0xDC,(byte)0x65,(byte)0xDB,(byte)0x5F,(byte)0x90,(byte)0x43,(byte)0x19,(byte)
                0x0B,(byte)0xE0,(byte)0xBE,(byte)0x89,(byte)0x7E,(byte)0x5F,(byte)0xE4,(byte)0x8A,(byte)
                0xDF,(byte)0x5D,(byte)0x3B,(byte)0xFA,(byte)0x05,(byte)0x85,(byte)0xE0,(byte)0x76,(byte)
                0xE5,(byte)0x54,(byte)0xF2,(byte)0x6E,(byte)0xC6,(byte)0x98,(byte)0x14,(byte)0x79,(byte)
                0x7F,(byte)0x15,(byte)0x66,(byte)0x9F,(byte)0x4A,(byte)0x25,(byte)0x5C,(byte)0x13
        };
        capk_E5.Exponent = new byte[]{0x03};
        capk_E5.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_E5.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_E5);
        Log("Add CAPK capk_E5:" + result + " ID:" + capk_E5.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_E5,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_E5 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_E6 = new EmvCAPK();
        capk_E6.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x99,(byte)0x99};
        capk_E6.KeyID = (byte)0xE6;
        capk_E6.HashInd = (byte)0x01;
        capk_E6.ArithInd = (byte)0x01;
        capk_E6.Modul = new  byte[]{
                (byte)0xEB,(byte)0xF9,(byte)0xFA,(byte)0xEC,(byte)0xC3,(byte)0xE5,(byte)0xC3,(byte)0x15,(byte)
                0x70,(byte)0x96,(byte)0x94,(byte)0x66,(byte)0x47,(byte)0x75,(byte)0xD3,(byte)0xFB,(byte)
                0xDA,(byte)0x5A,(byte)0x50,(byte)0x4D,(byte)0x89,(byte)0x34,(byte)0x4D,(byte)0xD9,(byte)
                0x20,(byte)0xC5,(byte)0x56,(byte)0x96,(byte)0xE8,(byte)0x91,(byte)0xD9,(byte)0xAB,(byte)
                0x62,(byte)0x25,(byte)0x98,(byte)0xA9,(byte)0xD6,(byte)0xAB,(byte)0x8F,(byte)0xBF,(byte)
                0x35,(byte)0xE4,(byte)0x59,(byte)0x9C,(byte)0xAB,(byte)0x7E,(byte)0xB2,(byte)0x2F,(byte)
                0x95,(byte)0x69,(byte)0x92,(byte)0xF8,(byte)0xAB,(byte)0x2E,(byte)0x65,(byte)0x35,(byte)
                0xDE,(byte)0xCB,(byte)0x6B,(byte)0x57,(byte)0x6F,(byte)0xA0,(byte)0x67,(byte)0x5F,(byte)
                0x97,(byte)0xC2,(byte)0x3D,(byte)0xD4,(byte)0xC3,(byte)0x74,(byte)0xA6,(byte)0x6E,(byte)
                0x6A,(byte)0xF4,(byte)0x19,(byte)0xC9,(byte)0xD2,(byte)0x04,(byte)0xD0,(byte)0xB9,(byte)
                0xF9,(byte)0x3C,(byte)0x08,(byte)0xD7,(byte)0x89,(byte)0xD6,(byte)0x38,(byte)0x05,(byte)
                0x66,(byte)0x0F,(byte)0xBB,(byte)0x62,(byte)0x9D,(byte)0xF1,(byte)0xB4,(byte)0x88,(byte)
                0xCF,(byte)0xA1,(byte)0xD7,(byte)0xA1,(byte)0x3E,(byte)0x9B,(byte)0x72,(byte)0x94,(byte)
                0x37,(byte)0xEE,(byte)0xAF,(byte)0xE7,(byte)0x18,(byte)0xEF,(byte)0xA8,(byte)0x59,(byte)
                0x34,(byte)0x8B,(byte)0xA0,(byte)0xD7,(byte)0x68,(byte)0x12,(byte)0xA9,(byte)0x9F,(byte)
                0x31,(byte)0xCD,(byte)0x36,(byte)0x4F,(byte)0x2A,(byte)0x4F,(byte)0xD4,(byte)0x2F
        };
        capk_E6.Exponent = new byte[]{0x01,0x00,0x01};
        capk_E6.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_E6.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_E6);
        Log("Add CAPK capk_E6:" + result + " ID:" + capk_E6.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_E6,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_E6 database:" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_MasterCard_FE = new EmvCAPK();
        capk_MasterCard_FE.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x04};
        capk_MasterCard_FE.KeyID = (byte)0xFE;
        capk_MasterCard_FE.HashInd = (byte)0x01;
        capk_MasterCard_FE.ArithInd = (byte)0x01;
        capk_MasterCard_FE.Modul = new  byte[]{
                (byte)0xE7,(byte)0x63,(byte)0x17,(byte)0x96,(byte)0x51,(byte)0x75,(byte)0xA0,(byte)0x8B,(byte)
                0xEE,(byte)0x51,(byte)0x0F,(byte)0x58,(byte)0x83,(byte)0x0E,(byte)0x87,(byte)0xB2,(byte)
                0x62,(byte)0xC7,(byte)0x0D,(byte)0x52,(byte)0x98,(byte)0x03,(byte)0x24,(byte)0x5F,(byte)
                0xA8,(byte)0xB8,(byte)0x8E,(byte)0x0C,(byte)0x75,(byte)0x35,(byte)0x62,(byte)0xDE,(byte)
                0x7A,(byte)0xEB,(byte)0x5A,(byte)0x9E,(byte)0x3E,(byte)0x6C,(byte)0x1A,(byte)0x98,(byte)
                0xE9,(byte)0x4D,(byte)0x8D,(byte)0xB7,(byte)0xC3,(byte)0x14,(byte)0x07,(byte)0xDA,(byte)
                0xC5,(byte)0xD0,(byte)0x71,(byte)0xE0,(byte)0x6B,(byte)0x80,(byte)0xB0,(byte)0x9E,(byte)
                0x14,(byte)0x6F,(byte)0x22,(byte)0xDB,(byte)0x85,(byte)0xF1,(byte)0xD7,(byte)0x2D,(byte)
                0x1E,(byte)0xA1,(byte)0x8D,(byte)0x22,(byte)0x60,(byte)0x00,(byte)0x32,(byte)0xC6,(byte)
                0xDD,(byte)0x40,(byte)0xE3,(byte)0x71,(byte)0x4D,(byte)0x5A,(byte)0xDA,(byte)0x7D,(byte)
                0xE9,(byte)0xD7,(byte)0xD0,(byte)0x1E,(byte)0x88,(byte)0x39,(byte)0x1F,(byte)0x89,(byte)
                0x31,(byte)0x56,(byte)0xD6,(byte)0xF4,(byte)0xBF,(byte)0x13,(byte)0xE9,(byte)0x06,(byte)
                0x35,(byte)0x59,(byte)0xDA,(byte)0x07,(byte)0x86,(byte)0xDE,(byte)0x9B,(byte)0xDE,(byte)
                0x6B,(byte)0x1C,(byte)0x9B,(byte)0x0B,(byte)0xB9,(byte)0x68,(byte)0xED,(byte)0xDE,(byte)
                0x07,(byte)0x14,(byte)0x5A,(byte)0xBF,(byte)0x87,(byte)0x7B,(byte)0x93,(byte)0x16,(byte)
                0x82,(byte)0xCC,(byte)0xB1,(byte)0xFB,(byte)0x80,(byte)0x07,(byte)0x28,(byte)0x72,(byte)
                0x4D,(byte)0x04,(byte)0xAF,(byte)0x24,(byte)0x1E,(byte)0x28,(byte)0x27,(byte)0xE0,(byte)
                0xFA,(byte)0x1F,(byte)0x62,(byte)0x59,(byte)0x19,(byte)0x14,(byte)0xFF,(byte)0x25
        };
        capk_MasterCard_FE.Exponent = new byte[]{0x01,0x00,0x01};
        capk_MasterCard_FE.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_MasterCard_FE.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_MasterCard_FE);
        Log("Add CAPK capk_MasterCard_FE:" + result + " ID:" + capk_MasterCard_FE.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_MasterCard_FE,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_MasterCard_FE database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_MasterCard_FC = new EmvCAPK();
        capk_MasterCard_FC.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x04};
        capk_MasterCard_FC.KeyID = (byte)0xFC;
        capk_MasterCard_FC.HashInd = (byte)0x01;
        capk_MasterCard_FC.ArithInd = (byte)0x01;
        capk_MasterCard_FC.Modul = new  byte[]{
                (byte)0xB3,(byte)0x29,(byte)0x6C,(byte)0x91,(byte)0xF4,(byte)0x79,(byte)0x5B,(byte)0xD9,(byte)
                0x71,(byte)0x12,(byte)0x60,(byte)0x69,(byte)0x03,(byte)0x40,(byte)0x7B,(byte)0x6E,(byte)
                0xFF,(byte)0x3A,(byte)0xB3,(byte)0x92,(byte)0x46,(byte)0xE9,(byte)0x10,(byte)0x95,(byte)
                0xE5,(byte)0x1D,(byte)0x17,(byte)0x86,(byte)0x7D,(byte)0xA4,(byte)0xAD,(byte)0xE5,(byte)
                0x9A,(byte)0x48,(byte)0xBE,(byte)0x2F,(byte)0xE9,(byte)0xB5,(byte)0x27,(byte)0x10,(byte)
                0x28,(byte)0x3D,(byte)0x3D,(byte)0x32,(byte)0x26,(byte)0x0E,(byte)0x2C,(byte)0x7D,(byte)
                0x24,(byte)0x72,(byte)0x14,(byte)0xC5,(byte)0x7D,(byte)0x46,(byte)0xAA,(byte)0x64,(byte)
                0x65,(byte)0xE4,(byte)0x7E,(byte)0x0A,(byte)0x4B,(byte)0x3F,(byte)0xFA,(byte)0xAD,(byte)
                0x8A,(byte)0x7F,(byte)0x6A,(byte)0x19,(byte)0x07,(byte)0x55,(byte)0xBC,(byte)0xCF,(byte)
                0xE3,(byte)0xF3,(byte)0xFB,(byte)0x39,(byte)0x89,(byte)0xA9,(byte)0xF6,(byte)0xB1,(byte)
                0xC9,(byte)0xE1,(byte)0x84,(byte)0x5B,(byte)0xCC,(byte)0xCA,(byte)0xD6,(byte)0xF2,(byte)
                0x0B,(byte)0x1D,(byte)0xAC,(byte)0x60,(byte)0x33,(byte)0x60,(byte)0x02,(byte)0x34,(byte)
                0xE8,(byte)0x1D,(byte)0xAC,(byte)0x41,(byte)0x53,(byte)0x21,(byte)0x2B,(byte)0x0F,(byte)
                0x76,(byte)0x0C,(byte)0x23,(byte)0x09,(byte)0x91,(byte)0x92,(byte)0xAA,(byte)0x6C,(byte)
                0x4C,(byte)0x90,(byte)0x83,(byte)0xBE,(byte)0xFF,(byte)0xD9,(byte)0xA7,(byte)0x9D,(byte)
                0x2A,(byte)0x27,(byte)0xB0,(byte)0x8F,(byte)0xEC,(byte)0xC8,(byte)0xE5,(byte)0xD4,(byte)
                0x37,(byte)0xD6,(byte)0xC6,(byte)0x85,(byte)0x50,(byte)0xA8,(byte)0x39,(byte)0xB1,(byte)
                0x29,(byte)0x41,(byte)0x51,(byte)0xDA,(byte)0xBA,(byte)0x9D,(byte)0x9C,(byte)0xB2,(byte)
                0xF1,(byte)0x60,(byte)0xF6,(byte)0x0F,(byte)0x74,(byte)0x92,(byte)0x89,(byte)0xF5,(byte)
                0x00,(byte)0xC8,(byte)0xC7,(byte)0xF3,(byte)0x34,(byte)0xBD,(byte)0x20,(byte)0xEB,(byte)
                0xAC,(byte)0x4A,(byte)0xB1,(byte)0x09,(byte)0xCF,(byte)0x3C,(byte)0x18,(byte)0x2F,(byte)
                0x1B,(byte)0x78,(byte)0x1C,(byte)0x7C,(byte)0x09,(byte)0x7A,(byte)0x79,(byte)0x03,(byte)
                0x53,(byte)0x07,(byte)0x46,(byte)0xC4,(byte)0x49,(byte)0xB9,(byte)0x9E,(byte)0x39,(byte)
                0xE4,(byte)0xDB,(byte)0x64,(byte)0x93,(byte)0xDD,(byte)0x2A,(byte)0x02,(byte)0xE3,(byte)
                0x7C,(byte)0x62,(byte)0xAE,(byte)0x8B,(byte)0xC9,(byte)0xA7,(byte)0x47,(byte)0x0E,(byte)
                0xCC,(byte)0xCF,(byte)0x8D,(byte)0xC0,(byte)0x6A,(byte)0x18,(byte)0xC3,(byte)0x3C,(byte)
                0xD2,(byte)0x4B,(byte)0x30,(byte)0xD5,(byte)0x6F,(byte)0x25,(byte)0xD2,(byte)0x75,(byte)
                0x5C,(byte)0xE8,(byte)0x2A,(byte)0xA4,(byte)0xDE,(byte)0x4D,(byte)0x2E,(byte)0xAE,(byte)
                0xC0,(byte)0x77,(byte)0x50,(byte)0xA0,(byte)0x3D,(byte)0xB7,(byte)0x5E,(byte)0xBD,(byte)
                0x0D,(byte)0x8E,(byte)0xBC,(byte)0x9F,(byte)0x2A,(byte)0x1D,(byte)0x85,(byte)0xA0,(byte)
                0xD2,(byte)0x52,(byte)0xEF,(byte)0xF4,(byte)0x03,(byte)0x29,(byte)0xBE,(byte)0x05
        };
        capk_MasterCard_FC.Exponent = new byte[]{0x01,0x00,0x01};
        capk_MasterCard_FC.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_MasterCard_FC.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_MasterCard_FC);
        Log("Add CAPK capk_MasterCard_FC:" + result + " ID:" + capk_MasterCard_FC.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_MasterCard_FC,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_MasterCard_FC database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_MasterCard_FD = new EmvCAPK();
        capk_MasterCard_FD.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x04};
        capk_MasterCard_FD.KeyID = (byte)0xFD;
        capk_MasterCard_FD.HashInd = (byte)0x01;
        capk_MasterCard_FD.ArithInd = (byte)0x01;
        capk_MasterCard_FD.Modul = new  byte[]{
                (byte)0xC9,(byte)0x48,(byte)0x5D,(byte)0xBE,(byte)0xB5,(byte)0xE4,(byte)0x04,(byte)0x15,(byte)
                0xD1,(byte)0xB3,(byte)0x97,(byte)0x52,(byte)0x4F,(byte)0x47,(byte)0x68,(byte)0x5F,(byte)
                0x30,(byte)0x6C,(byte)0xFD,(byte)0xC4,(byte)0x99,(byte)0xD4,(byte)0xE2,(byte)0xE7,(byte)
                0xD0,(byte)0xCB,(byte)0xAF,(byte)0x22,(byte)0x2C,(byte)0xFA,(byte)0x81,(byte)0x84,(byte)
                0xBD,(byte)0x11,(byte)0x1D,(byte)0xAE,(byte)0xED,(byte)0xC9,(byte)0xCC,(byte)0x6E,(byte)
                0xC8,(byte)0x54,(byte)0x0C,(byte)0x3F,(byte)0x72,(byte)0x71,(byte)0xEA,(byte)0x99,(byte)
                0x90,(byte)0x11,(byte)0x9C,(byte)0xC5,(byte)0xC4,(byte)0x31,(byte)0x80,(byte)0x50,(byte)
                0x1D,(byte)0x9F,(byte)0x45,(byte)0x25,(byte)0x2D,(byte)0x68,(byte)0x35,(byte)0x05,(byte)
                0x3F,(byte)0xAE,(byte)0x35,(byte)0x69,(byte)0x6A,(byte)0xE8,(byte)0xCD,(byte)0x67,(byte)
                0xA3,(byte)0x25,(byte)0x64,(byte)0x74,(byte)0x49,(byte)0xCF,(byte)0x5E,(byte)0x59,(byte)
                0x4D,(byte)0xA8,(byte)0xF6,(byte)0x27,(byte)0x20,(byte)0x9F,(byte)0x7F,(byte)0x03,(byte)
                0xAE,(byte)0x8D,(byte)0x6D,(byte)0xFC,(byte)0x0D,(byte)0xB3,(byte)0xE7,(byte)0x9E,(byte)
                0x28,(byte)0xE4,(byte)0x15,(byte)0xDF,(byte)0x29,(byte)0xA5,(byte)0xB5,(byte)0x7D,(byte)
                0x68,(byte)0x14,(byte)0x85,(byte)0x6C,(byte)0xC3,(byte)0x0A,(byte)0x96,(byte)0xDA,(byte)
                0x5B,(byte)0x88,(byte)0x90,(byte)0x36,(byte)0x3E,(byte)0x50,(byte)0x7F,(byte)0xCB,(byte)
                0x2E,(byte)0x28,(byte)0x3D,(byte)0xA1,(byte)0xEB,(byte)0xB5,(byte)0xF1,(byte)0x8E,(byte)
                0x8E,(byte)0x24,(byte)0x10,(byte)0x2B,(byte)0x7D,(byte)0x01,(byte)0x92,(byte)0xBB,(byte)
                0x8E,(byte)0x35,(byte)0xA4,(byte)0xF7,(byte)0xCD,(byte)0x05,(byte)0xA4,(byte)0x35
        };
        capk_MasterCard_FD.Exponent = new byte[]{0x01,0x00,0x01};
        capk_MasterCard_FD.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_MasterCard_FD.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_MasterCard_FD);
        Log("Add CAPK capk_MasterCard_FD:" + result + " ID:" + capk_MasterCard_FD.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_MasterCard_FD,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_MasterCard_FD database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_MasterCard_FB = new EmvCAPK();
        capk_MasterCard_FB.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x04};
        capk_MasterCard_FB.KeyID = (byte)0xFB;
        capk_MasterCard_FB.HashInd = (byte)0x01;
        capk_MasterCard_FB.ArithInd = (byte)0x01;
        capk_MasterCard_FB.Modul = new  byte[]{
                (byte)0x9B,(byte)0x17,(byte)0x06,(byte)0x03,(byte)0xA4,(byte)0x89,(byte)0xC7,(byte)0x54,(byte)
                0x6C,(byte)0x45,(byte)0xDA,(byte)0x57,(byte)0xB8,(byte)0xFF,(byte)0xD1,(byte)0xDB,(byte)
                0x20,(byte)0x61,(byte)0x24,(byte)0x0F,(byte)0x0E,(byte)0x8C,(byte)0x6D,(byte)0x1F,(byte)
                0x9A,(byte)0xBD,(byte)0xC6,(byte)0xB2,(byte)0x65,(byte)0xAA,(byte)0x89,(byte)0x11,(byte)
                0x91,(byte)0x5C,(byte)0x1A,(byte)0x4E,(byte)0xAB,(byte)0xD8,(byte)0xD0,(byte)0xED,(byte)
                0x47,(byte)0x55,(byte)0xD1,(byte)0xB9,(byte)0x02,(byte)0xBA,(byte)0x06,(byte)0xFE,(byte)
                0x5A,(byte)0x64,(byte)0x5B,(byte)0x78,(byte)0x6C,(byte)0xD2,(byte)0x41,(byte)0x29,(byte)
                0x55,(byte)0x17,(byte)0xD4,(byte)0x4E,(byte)0xF1,(byte)0xA7,(byte)0xC2,(byte)0x5D,(byte)
                0x75,(byte)0xAF,(byte)0xE0,(byte)0xEB,(byte)0x28,(byte)0x06,(byte)0x6E,(byte)0x4D,(byte)
                0x69,(byte)0xFE,(byte)0xE7,(byte)0xAB,(byte)0xAF,(byte)0xDD,(byte)0x5E,(byte)0xEB,(byte)
                0x23,(byte)0x0F,(byte)0x14,(byte)0xE4,(byte)0x02,(byte)0xC9,(byte)0x84,(byte)0x08,(byte)
                0x25,(byte)0xFA,(byte)0x77,(byte)0xEA,(byte)0xD1,(byte)0x2B,(byte)0x5F,(byte)0x1C,(byte)
                0x54,(byte)0x94,(byte)0x70,(byte)0x1D,(byte)0xE1,(byte)0x89,(byte)0x7F,(byte)0x65,(byte)
                0xFE,(byte)0x6B,(byte)0xF1,(byte)0x06,(byte)0xD4,(byte)0x75,(byte)0x45,(byte)0xEB,(byte)
                0xF7,(byte)0x0C,(byte)0xE7,(byte)0xC1,(byte)0x58,(byte)0x06,(byte)0x8C,(byte)0x61,(byte)
                0xF0,(byte)0x77,(byte)0x35,(byte)0x34,(byte)0xDB,(byte)0x74,(byte)0x2A,(byte)0xB8,(byte)
                0x3C,(byte)0x28,(byte)0x03,(byte)0x8C,(byte)0x14,(byte)0x94,(byte)0xF1,(byte)0x59,(byte)
                0x05,(byte)0xD0,(byte)0xAD,(byte)0x17,(byte)0xCF,(byte)0x1B,(byte)0xD3,(byte)0x8D
        };
        capk_MasterCard_FB.Exponent = new byte[]{0x01,0x00,0x01};
        capk_MasterCard_FB.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_MasterCard_FB.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_MasterCard_FB);
        Log("Add CAPK capk_MasterCard_FB:" + result + " ID:" + capk_MasterCard_FB.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_MasterCard_FB,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_MasterCard_FB database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_MasterCard_FA = new EmvCAPK();
        capk_MasterCard_FA.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x04};
        capk_MasterCard_FA.KeyID = (byte)0xFA;
        capk_MasterCard_FA.HashInd = (byte)0x01;
        capk_MasterCard_FA.ArithInd = (byte)0x01;
        capk_MasterCard_FA.Modul = new  byte[]{
                (byte)0xA4,(byte)0x20,(byte)0x3E,(byte)0x0C,(byte)0x7B,(byte)0xEB,(byte)0x27,(byte)0x09,(byte)
                0x7B,(byte)0x63,(byte)0xC1,(byte)0x03,(byte)0xC1,(byte)0x9F,(byte)0xDC,(byte)0xDA,(byte)
                0x67,(byte)0x1A,(byte)0xEA,(byte)0x7F,(byte)0x81,(byte)0x30,(byte)0x65,(byte)0x75,(byte)
                0x6F,(byte)0x3B,(byte)0x9B,(byte)0x81,(byte)0x81,(byte)0x0C,(byte)0xBD,(byte)0x4B,(byte)
                0xC4,(byte)0xDE,(byte)0xC5,(byte)0x48,(byte)0xFB,(byte)0xF1,(byte)0xF3,(byte)0xCD,(byte)
                0xAE,(byte)0x51,(byte)0xF8,(byte)0x47,(byte)0x23,(byte)0x5C,(byte)0xBF,(byte)0x2C,(byte)
                0x8B,(byte)0xAD,(byte)0xD8,(byte)0xAC,(byte)0xA7,(byte)0xC9,(byte)0x3B,(byte)0xEA,(byte)
                0x3D,(byte)0x44,(byte)0xE8,(byte)0x0E,(byte)0xD6,(byte)0xA7,(byte)0xB7,(byte)0x0E,(byte)
                0x29,(byte)0x62,(byte)0x26,(byte)0x19,(byte)0xDB,(byte)0x42,(byte)0x0A,(byte)0xCC,(byte)
                0xCE,(byte)0x07,(byte)0xE1,(byte)0xDD,(byte)0x4E,(byte)0x6C,(byte)0x35,(byte)0x4F,(byte)
                0x35,(byte)0x9F,(byte)0xBD,(byte)0xC9,(byte)0xC5,(byte)0xB7,(byte)0x08,(byte)0x13,(byte)
                0x92,(byte)0x6F,(byte)0x77,(byte)0xD8,(byte)0x27,(byte)0xE5,(byte)0x2B,(byte)0x19,(byte)
                0xDA,(byte)0xF0,(byte)0x9B,(byte)0xFA,(byte)0xE5,(byte)0x27,(byte)0x44,(byte)0x38,(byte)
                0xBB,(byte)0x8F,(byte)0x61,(byte)0xD1,(byte)0x77,(byte)0x53,(byte)0xC9,(byte)0xEC,(byte)
                0x0A,(byte)0x8E,(byte)0xFA,(byte)0x3B,(byte)0x7E,(byte)0x46,(byte)0xF0,(byte)0x26,(byte)
                0x92,(byte)0x16,(byte)0x0D,(byte)0x26,(byte)0x53,(byte)0xCD,(byte)0xBC,(byte)0xC7,(byte)
                0x1B,(byte)0x7D,(byte)0x48,(byte)0xBD,(byte)0x37,(byte)0x96,(byte)0x83,(byte)0x16,(byte)
                0xEB,(byte)0x44,(byte)0x4F,(byte)0x65,(byte)0x04,(byte)0xB9,(byte)0x42,(byte)0x1B,(byte)
                0x7D,(byte)0xD3,(byte)0x03,(byte)0x5A,(byte)0x2C,(byte)0x11,(byte)0x7D,(byte)0x8B,(byte)
                0x1F,(byte)0x76,(byte)0xA8,(byte)0x97,(byte)0x54,(byte)0x40,(byte)0xDA,(byte)0x95,(byte)
                0x63,(byte)0x61,(byte)0x81,(byte)0x02,(byte)0x39,(byte)0x7B,(byte)0x88,(byte)0x1C,(byte)
                0xEF,(byte)0x8A,(byte)0xDA,(byte)0x76,(byte)0x89,(byte)0xED,(byte)0xFA,(byte)0xCE,(byte)
                0x32,(byte)0x48,(byte)0x2A,(byte)0x2D,(byte)0xFF,(byte)0xED,(byte)0x65,(byte)0x6E,(byte)
                0x7F,(byte)0x95,(byte)0x1D,(byte)0xB8,(byte)0x41,(byte)0xDA,(byte)0x78,(byte)0x36,(byte)
                0x8C,(byte)0x62,(byte)0x93,(byte)0xBF,(byte)0xC1,(byte)0x05,(byte)0x3A,(byte)0x86,(byte)
                0xA8,(byte)0x45,(byte)0xBF,(byte)0xA6,(byte)0x57,(byte)0x8E,(byte)0x4B,(byte)0x69,(byte)
                0xF1,(byte)0x00,(byte)0xB4,(byte)0x2B,(byte)0x55,(byte)0x8F,(byte)0xDE,(byte)0x1A,(byte)
                0xEC,(byte)0xEC,(byte)0x6D,(byte)0x25,(byte)0x07,(byte)0x41,(byte)0xBC,(byte)0x78,(byte)
                0x3A,(byte)0xA8,(byte)0xA6,(byte)0x8A,(byte)0x42,(byte)0x61,(byte)0xE7,(byte)0xBB,(byte)
                0x92,(byte)0x46,(byte)0xB1,(byte)0x05,(byte)0x87,(byte)0xA4,(byte)0x98,(byte)0xD6,(byte)
                0x8D,(byte)0xD9,(byte)0x55,(byte)0xCE,(byte)0x8B,(byte)0x2B,(byte)0x24,(byte)0x33
        };
        capk_MasterCard_FA.Exponent = new byte[]{0x01,0x00,0x01};
        capk_MasterCard_FA.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_MasterCard_FA.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_MasterCard_FA);
        Log("Add CAPK capk_MasterCard_FA:" + result + " ID:" + capk_MasterCard_FA.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_MasterCard_FA,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_MasterCard_FA database:" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_MasterCard_FF = new EmvCAPK();
        capk_MasterCard_FF.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x04};
        capk_MasterCard_FF.KeyID = (byte)0xFF;
        capk_MasterCard_FF.HashInd = (byte)0x01;
        capk_MasterCard_FF.ArithInd = (byte)0x01;
        capk_MasterCard_FF.Modul = new  byte[]{
                (byte)0xF6,(byte)0x9D,(byte)0xBB,(byte)0x5E,(byte)0x15,(byte)0x98,(byte)0x3E,(byte)0xAE,(byte)
                0x3C,(byte)0xCF,(byte)0x31,(byte)0xCF,(byte)0x4E,(byte)0x47,(byte)0x09,(byte)0x8C,(byte)
                0x2F,(byte)0xC1,(byte)0x6F,(byte)0x97,(byte)0xA0,(byte)0xC7,(byte)0x10,(byte)0xF8,(byte)
                0x47,(byte)0x77,(byte)0xEF,(byte)0xA9,(byte)0x96,(byte)0x22,(byte)0xD8,(byte)0x65,(byte)
                0x02,(byte)0xB1,(byte)0x38,(byte)0x72,(byte)0x8A,(byte)0xB1,(byte)0x2E,(byte)0x34,(byte)
                0x81,(byte)0xA8,(byte)0x4D,(byte)0x20,(byte)0xE0,(byte)0x14,(byte)0xAD,(byte)0x2D,(byte)
                0x63,(byte)0x4D,(byte)0x28,(byte)0x36,(byte)0xF2,(byte)0x7F,(byte)0x29,(byte)0x49,(byte)
                0x24,(byte)0xB8,(byte)0x95,(byte)0xA8,(byte)0x7F,(byte)0x91,(byte)0xF8,(byte)0x1B,(byte)
                0x81,(byte)0x69,(byte)0xD4,(byte)0xDF,(byte)0xDA,(byte)0xD8,(byte)0xD7,(byte)0xCB,(byte)
                0xD7,(byte)0x41,(byte)0x80,(byte)0x4C,(byte)0xD6,(byte)0x1B,(byte)0x46,(byte)0x7C,(byte)
                0x7A,(byte)0x9A,(byte)0xCF,(byte)0xEC,(byte)0xEB,(byte)0x71,(byte)0x18,(byte)0x8C,(byte)
                0xAA,(byte)0x73,(byte)0xA9,(byte)0x07,(byte)0x54,(byte)0x76,(byte)0x99,(byte)0xD4,(byte)
                0x5C,(byte)0x9C,(byte)0x7D,(byte)0x20,(byte)0x98,(byte)0xAC,(byte)0x29,(byte)0x66,(byte)
                0x26,(byte)0x64,(byte)0x17,(byte)0xF6,(byte)0x65,(byte)0xA4,(byte)0x6B,(byte)0xDD,(byte)
                0x01,(byte)0x2C,(byte)0x09,(byte)0x7D,(byte)0xBD,(byte)0x33,(byte)0xD1,(byte)0xD1,(byte)
                0x1A,(byte)0xFF,(byte)0x6E,(byte)0xC8,(byte)0xA9,(byte)0xC0,(byte)0xAD,(byte)0x81,(byte)
                0x4A,(byte)0x65,(byte)0xB4,(byte)0x82,(byte)0x62,(byte)0xCA,(byte)0x01,(byte)0x16,(byte)
                0x36,(byte)0x07,(byte)0x9A,(byte)0x32,(byte)0x8C,(byte)0x1A,(byte)0xAE,(byte)0xB7
        };
        capk_MasterCard_FF.Exponent = new byte[]{0x01,0x00,0x01};
        capk_MasterCard_FF.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_MasterCard_FF.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_MasterCard_FF);
        Log("Add CAPK capk_MasterCard_FF:" + result + " ID:" + capk_MasterCard_FF.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_MasterCard_FF,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_MasterCard_FF database:" + dbResult);
        }




        EmvCAPK capk_MasterCard_05 = new EmvCAPK();
        capk_MasterCard_05.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x04};
        capk_MasterCard_05.KeyID = (byte)0x05;
        capk_MasterCard_05.HashInd = (byte)0x01;
        capk_MasterCard_05.ArithInd = (byte)0x01;
        capk_MasterCard_05.Modul = new  byte[]{
                (byte) 0xB8, (byte) 0x04, (byte) 0x8A, (byte) 0xBC, (byte) 0x30, (byte) 0xC9, (byte) 0x0D, (byte) 0x97, (byte) 0x63, (byte) 0x36, (byte) 0x54, (byte) 0x3E, (byte) 0x3F, (byte) 0xD7, (byte) 0x09, (byte) 0x1C,
                (byte) 0x8F, (byte) 0xE4, (byte) 0x80, (byte) 0x0D, (byte) 0xF8, (byte) 0x20, (byte) 0xED, (byte) 0x55, (byte) 0xE7, (byte) 0xE9, (byte) 0x48, (byte) 0x13, (byte) 0xED, (byte) 0x00, (byte) 0x55, (byte) 0x5B,
                (byte) 0x57, (byte) 0x3F, (byte) 0xEC, (byte) 0xA3, (byte) 0xD8, (byte) 0x4A, (byte) 0xF6, (byte) 0x13, (byte) 0x1A, (byte) 0x65, (byte) 0x1D, (byte) 0x66, (byte) 0xCF, (byte) 0xF4, (byte) 0x28, (byte) 0x4F,
                (byte) 0xB1, (byte) 0x3B, (byte) 0x63, (byte) 0x5E, (byte) 0xDD, (byte) 0x0E, (byte) 0xE4, (byte) 0x01, (byte) 0x76, (byte) 0xD8, (byte) 0xBF, (byte) 0x04, (byte) 0xB7, (byte) 0xFD, (byte) 0x1C, (byte) 0x7B,
                (byte) 0xAC, (byte) 0xF9, (byte) 0xAC, (byte) 0x73, (byte) 0x27, (byte) 0xDF, (byte) 0xAA, (byte) 0x8A, (byte) 0xA7, (byte) 0x2D, (byte) 0x10, (byte) 0xDB, (byte) 0x3B, (byte) 0x8E, (byte) 0x70, (byte) 0xB2,
                (byte) 0xDD, (byte) 0xD8, (byte) 0x11, (byte) 0xCB, (byte) 0x41, (byte) 0x96, (byte) 0x52, (byte) 0x5E, (byte) 0xA3, (byte) 0x86, (byte) 0xAC, (byte) 0xC3, (byte) 0x3C, (byte) 0x0D, (byte) 0x9D, (byte) 0x45,
                (byte) 0x75, (byte) 0x91, (byte) 0x64, (byte) 0x69, (byte) 0xC4, (byte) 0xE4, (byte) 0xF5, (byte) 0x3E, (byte) 0x8E, (byte) 0x1C, (byte) 0x91, (byte) 0x2C, (byte) 0xC6, (byte) 0x18, (byte) 0xCB, (byte) 0x22,
                (byte) 0xDD, (byte) 0xE7, (byte) 0xC3, (byte) 0x56, (byte) 0x8E, (byte) 0x90, (byte) 0x02, (byte) 0x2E, (byte) 0x6B, (byte) 0xBA, (byte) 0x77, (byte) 0x02, (byte) 0x02, (byte) 0xE4, (byte) 0x52, (byte) 0x2A,
                (byte) 0x2D, (byte) 0xD6, (byte) 0x23, (byte) 0xD1, (byte) 0x80, (byte) 0xE2, (byte) 0x15, (byte) 0xBD, (byte) 0x1D, (byte) 0x15, (byte) 0x07, (byte) 0xFE, (byte) 0x3D, (byte) 0xC9, (byte) 0x0C, (byte) 0xA3,
                (byte) 0x10, (byte) 0xD2, (byte) 0x7B, (byte) 0x3E, (byte) 0xFC, (byte) 0xCD, (byte) 0x8F, (byte) 0x83, (byte) 0xDE, (byte) 0x30, (byte) 0x52, (byte) 0xCA, (byte) 0xD1, (byte) 0xE4, (byte) 0x89, (byte) 0x38,
                (byte) 0xC6, (byte) 0x8D, (byte) 0x09, (byte) 0x5A, (byte) 0xAC, (byte) 0x91, (byte) 0xB5, (byte) 0xF3, (byte) 0x7E, (byte) 0x28, (byte) 0xBB, (byte) 0x49, (byte) 0xEC, (byte) 0x7E, (byte) 0xD5, (byte) 0x97
        };
        capk_MasterCard_05.Exponent = new byte[]{0x00,0x00,0x03};
        capk_MasterCard_05.ExpDate = new byte[]{0x21,0x12,0x31};
        capk_MasterCard_05.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_MasterCard_05);
        Log("Add CAPK capk_MasterCard_FF:" + result + " ID:" + capk_MasterCard_05.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_MasterCard_FF,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_MasterCard_FF database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_JCB_02 = new EmvCAPK();
        capk_JCB_02.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x65};
        capk_JCB_02.KeyID = (byte)0x02;
        capk_JCB_02.HashInd = (byte)0x01;
        capk_JCB_02.ArithInd = (byte)0x01;
        capk_JCB_02.Modul = new  byte[]{
                (byte)0xBB,(byte)0x7F,(byte)0x51,(byte)0x98,(byte)0x3F,(byte)0xD8,(byte)0x70,(byte)0x7F,(byte)
                0xD6,(byte)0x22,(byte)0x7C,(byte)0x23,(byte)0xDE,(byte)0xF5,(byte)0xD5,(byte)0x37,(byte)
                0x7A,(byte)0x5A,(byte)0x73,(byte)0x7C,(byte)0xEF,(byte)0x3C,(byte)0x52,(byte)0x52,(byte)
                0xE5,(byte)0x78,(byte)0xEF,(byte)0xE1,(byte)0x36,(byte)0xDF,(byte)0x87,(byte)0xB5,(byte)
                0x04,(byte)0x73,(byte)0xF9,(byte)0x34,(byte)0x1F,(byte)0x16,(byte)0x40,(byte)0xC8,(byte)
                0xD2,(byte)0x58,(byte)0x03,(byte)0x4E,(byte)0x14,(byte)0xC1,(byte)0x69,(byte)0x93,(byte)
                0xFC,(byte)0xE6,(byte)0xC6,(byte)0xB8,(byte)0xC3,(byte)0xCE,(byte)0xEB,(byte)0x65,(byte)
                0xFC,(byte)0x8F,(byte)0xBC,(byte)0xD8,(byte)0xEB,(byte)0x77,(byte)0xB3,(byte)0xB0,(byte)
                0x5A,(byte)0xC7,(byte)0xC4,(byte)0xD0,(byte)0x9E,(byte)0x0F,(byte)0xA1,(byte)0xBA,(byte)
                0x2E,(byte)0xFE,(byte)0x87,(byte)0xD3,(byte)0x18,(byte)0x4D,(byte)0xB6,(byte)0x71,(byte)
                0x8A,(byte)0xE4,(byte)0x1A,(byte)0x7C,(byte)0xAD,(byte)0x89,(byte)0xB8,(byte)0xDC,(byte)
                0xE0,(byte)0xFE,(byte)0x80,(byte)0xCE,(byte)0xB5,(byte)0x23,(byte)0xD5,(byte)0xD6,(byte)
                0x47,(byte)0xF9,(byte)0xDB,(byte)0x58,(byte)0xA3,(byte)0x1D,(byte)0x2E,(byte)0x71,(byte)
                0xAC,(byte)0x67,(byte)0x7E,(byte)0x67,(byte)0xFA,(byte)0x6E,(byte)0x75,(byte)0x82,(byte)
                0x07,(byte)0x36,(byte)0xC9,(byte)0x89,(byte)0x37,(byte)0x61,(byte)0xEE,(byte)0x4A,(byte)
                0xCD,(byte)0x11,(byte)0xF3,(byte)0x1D,(byte)0xBD,(byte)0xC3,(byte)0x49,(byte)0xEF
        };
        capk_JCB_02.Exponent = new byte[]{0x01,0x00,0x01};
        capk_JCB_02.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_JCB_02.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_JCB_02);
        Log("Add CAPK capk_JCB_02:" + result + " ID:" + capk_JCB_02.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_JCB_02,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_JCB_02 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_JCB_03 = new EmvCAPK();
        capk_JCB_03.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x65};
        capk_JCB_03.KeyID = (byte)0x03;
        capk_JCB_03.HashInd = (byte)0x01;
        capk_JCB_03.ArithInd = (byte)0x01;
        capk_JCB_03.Modul = new  byte[]{
                (byte)0xC9,(byte)0xE6,(byte)0xC1,(byte)0xF3,(byte)0xC6,(byte)0x94,(byte)0x9A,(byte)0x8A,(byte)
                0x42,(byte)0xA9,(byte)0x1F,(byte)0x8D,(byte)0x02,(byte)0x24,(byte)0x13,(byte)0x2B,(byte)
                0x28,(byte)0x65,(byte)0xE6,(byte)0xD9,(byte)0x53,(byte)0xA5,(byte)0xB5,(byte)0xA5,(byte)
                0x4C,(byte)0xFF,(byte)0xB0,(byte)0x41,(byte)0x24,(byte)0x39,(byte)0xD5,(byte)0x4A,(byte)
                0xEB,(byte)0xA7,(byte)0x9E,(byte)0x9B,(byte)0x39,(byte)0x9A,(byte)0x6C,(byte)0x10,(byte)
                0x46,(byte)0x84,(byte)0xDF,(byte)0x3F,(byte)0xB7,(byte)0x27,(byte)0xC7,(byte)0xF5,(byte)
                0x59,(byte)0x84,(byte)0xDB,(byte)0x7A,(byte)0x45,(byte)0x0E,(byte)0x6A,(byte)0xA9,(byte)
                0x17,(byte)0xE1,(byte)0x10,(byte)0xA7,(byte)0xF2,(byte)0x34,(byte)0x3A,(byte)0x00,(byte)
                0x24,(byte)0xD2,(byte)0x78,(byte)0x5D,(byte)0x9E,(byte)0xBE,(byte)0x09,(byte)0xF6,(byte)
                0x01,(byte)0xD5,(byte)0x92,(byte)0x36,(byte)0x2F,(byte)0xDB,(byte)0x23,(byte)0x77,(byte)
                0x00,(byte)0xB5,(byte)0x67,(byte)0xBA,(byte)0x14,(byte)0xBB,(byte)0xE2,(byte)0xA6,(byte)
                0xD3,(byte)0xD2,(byte)0x3C,(byte)0xF1,(byte)0x27,(byte)0x0B,(byte)0x3D,(byte)0xD8,(byte)
                0x22,(byte)0xB5,(byte)0x49,(byte)0x65,(byte)0x49,(byte)0xBF,(byte)0x88,(byte)0x49,(byte)
                0x48,(byte)0xF5,(byte)0x5A,(byte)0x0D,(byte)0x30,(byte)0x83,(byte)0x48,(byte)0xC4,(byte)
                0xB7,(byte)0x23,(byte)0xBA,(byte)0xFB,(byte)0x6A,(byte)0x7F,(byte)0x39,(byte)0x75,(byte)
                0xAC,(byte)0x39,(byte)0x7C,(byte)0xAD,(byte)0x3C,(byte)0x5D,(byte)0x0F,(byte)0xC2,(byte)
                0xD1,(byte)0x78,(byte)0x71,(byte)0x6F,(byte)0x5E,(byte)0x8E,(byte)0x79,(byte)0xE7,(byte)
                0x5B,(byte)0xEB,(byte)0x1C,(byte)0x84,(byte)0xFA,(byte)0x20,(byte)0x2F,(byte)0x80,(byte)
                0xE6,(byte)0x80,(byte)0x69,(byte)0xA9,(byte)0x84,(byte)0xE0,(byte)0x08,(byte)0x70,(byte)
                0x6B,(byte)0x30,(byte)0xC2,(byte)0x12,(byte)0x30,(byte)0x54,(byte)0x56,(byte)0x20,(byte)
                0x15,(byte)0x40,(byte)0x78,(byte)0x79,(byte)0x25,(byte)0xE8,(byte)0x6A,(byte)0x8B,(byte)
                0x28,(byte)0xB1,(byte)0x29,(byte)0xA1,(byte)0x1A,(byte)0xF2,(byte)0x04,(byte)0xB3,(byte)
                0x87,(byte)0xCB,(byte)0x6E,(byte)0xE4,(byte)0x3D,(byte)0xB5,(byte)0x3D,(byte)0x15,(byte)
                0xA4,(byte)0x6E,(byte)0x13,(byte)0x90,(byte)0x1B,(byte)0xEB,(byte)0xD5,(byte)0xCE,(byte)
                0xCF,(byte)0x48,(byte)0x54,(byte)0x25,(byte)0x1D,(byte)0x9E,(byte)0x98,(byte)0x75,(byte)
                0xB1,(byte)0x6E,(byte)0x82,(byte)0xAD,(byte)0x1C,(byte)0x59,(byte)0x38,(byte)0xA9,(byte)
                0x72,(byte)0x84,(byte)0x2C,(byte)0x8F,(byte)0x1A,(byte)0x42,(byte)0xEB,(byte)0xB5,(byte)
                0xAE,(byte)0x53,(byte)0x36,(byte)0xB0,(byte)0x4F,(byte)0xF3,(byte)0xDA,(byte)0x8B,(byte)
                0x8D,(byte)0xFB,(byte)0xE6,(byte)0x06,(byte)0xFC,(byte)0xA8,(byte)0xB9,(byte)0x08,(byte)
                0x4E,(byte)0xE0,(byte)0x5B,(byte)0xF6,(byte)0x79,(byte)0x50,(byte)0xBA,(byte)0x89,(byte)
                0x89,(byte)0x7C,(byte)0xD0,(byte)0x89,(byte)0xF9,(byte)0x24,(byte)0xDB,(byte)0xCD
        };
        capk_JCB_03.Exponent = new byte[]{0x03};
        capk_JCB_03.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_JCB_03.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_JCB_03);
        Log("Add CAPK capk_JCB_03:" + result + " ID:" + capk_JCB_03.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_JCB_03,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_JCB_03 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_VISA_50 = new EmvCAPK();
        capk_VISA_50.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03};
        capk_VISA_50.KeyID = (byte)0x50;
        capk_VISA_50.HashInd = (byte)0x01;
        capk_VISA_50.ArithInd = (byte)0x01;
        capk_VISA_50.Modul = new  byte[]{
                (byte)0xD1,(byte)0x11,(byte)0x97,(byte)0x59,(byte)0x00,(byte)0x57,(byte)0xB8,(byte)0x41,(byte)
                0x96,(byte)0xC2,(byte)0xF4,(byte)0xD1,(byte)0x1A,(byte)0x8F,(byte)0x3C,(byte)0x05,(byte)
                0x40,(byte)0x8F,(byte)0x42,(byte)0x2A,(byte)0x35,(byte)0xD7,(byte)0x02,(byte)0xF9,(byte)
                0x01,(byte)0x06,(byte)0xEA,(byte)0x5B,(byte)0x01,(byte)0x9B,(byte)0xB2,(byte)0x8A,(byte)
                0xE6,(byte)0x07,(byte)0xAA,(byte)0x9C,(byte)0xDE,(byte)0xBC,(byte)0xD0,(byte)0xD8,(byte)
                0x1A,(byte)0x38,(byte)0xD4,(byte)0x8C,(byte)0x7E,(byte)0xBB,(byte)0x00,(byte)0x62,(byte)
                0xD2,(byte)0x87,(byte)0x36,(byte)0x9E,(byte)0xC0,(byte)0xC4,(byte)0x21,(byte)0x24,(byte)
                0x24,(byte)0x6A,(byte)0xC3,(byte)0x0D,(byte)0x80,(byte)0xCD,(byte)0x60,(byte)0x2A,(byte)
                0xB7,(byte)0x23,(byte)0x8D,(byte)0x51,(byte)0x08,(byte)0x4D,(byte)0xED,(byte)0x46,(byte)
                0x98,(byte)0x16,(byte)0x2C,(byte)0x59,(byte)0xD2,(byte)0x5E,(byte)0xAC,(byte)0x1E,(byte)
                0x66,(byte)0x25,(byte)0x5B,(byte)0x4D,(byte)0xB2,(byte)0x35,(byte)0x25,(byte)0x26,(byte)
                0xEF,(byte)0x09,(byte)0x82,(byte)0xC3,(byte)0xB8,(byte)0xAD,(byte)0x3D,(byte)0x1C,(byte)
                0xCE,(byte)0x85,(byte)0xB0,(byte)0x1D,(byte)0xB5,(byte)0x78,(byte)0x8E,(byte)0x75,(byte)
                0xE0,(byte)0x9F,(byte)0x44,(byte)0xBE,(byte)0x73,(byte)0x61,(byte)0x36,(byte)0x6D,(byte)
                0xEF,(byte)0x9D,(byte)0x1E,(byte)0x13,(byte)0x17,(byte)0xB0,(byte)0x5E,(byte)0x5D,(byte)
                0x0F,(byte)0xF5,(byte)0x29,(byte)0x0F,(byte)0x88,(byte)0xA0,(byte)0xDB,(byte)0x47
        };
        capk_VISA_50.Exponent = new byte[]{0x01,0x00,0x01};
        capk_VISA_50.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_VISA_50.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_VISA_50);
        Log("Add CAPK capk_VISA_50:" + result + " ID:" + capk_VISA_50.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_VISA_50,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_VISA_50 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_VISA_51 = new EmvCAPK();
        capk_VISA_51.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03};
        capk_VISA_51.KeyID = (byte)0x51;
        capk_VISA_51.HashInd = (byte)0x01;
        capk_VISA_51.ArithInd = (byte)0x01;
        capk_VISA_51.Modul = new  byte[]{
                (byte)0xDB,(byte)0x5F,(byte)0xA2,(byte)0x9D,(byte)0x1F,(byte)0xDA,(byte)0x8C,(byte)0x16,(byte)
                0x34,(byte)0xB0,(byte)0x4D,(byte)0xCC,(byte)0xFF,(byte)0x14,(byte)0x8A,(byte)0xBE,(byte)
                0xE6,(byte)0x3C,(byte)0x77,(byte)0x20,(byte)0x35,(byte)0xC7,(byte)0x98,(byte)0x51,(byte)
                0xD3,(byte)0x51,(byte)0x21,(byte)0x07,(byte)0x58,(byte)0x6E,(byte)0x02,(byte)0xA9,(byte)
                0x17,(byte)0xF7,(byte)0xC7,(byte)0xE8,(byte)0x85,(byte)0xE7,(byte)0xC4,(byte)0xA7,(byte)
                0xD5,(byte)0x29,(byte)0x71,(byte)0x0A,(byte)0x14,(byte)0x53,(byte)0x34,(byte)0xCE,(byte)
                0x67,(byte)0xDC,(byte)0x41,(byte)0x2C,(byte)0xB1,(byte)0x59,(byte)0x7B,(byte)0x77,(byte)
                0xAA,(byte)0x25,(byte)0x43,(byte)0xB9,(byte)0x8D,(byte)0x19,(byte)0xCF,(byte)0x2C,(byte)
                0xB8,(byte)0x0C,(byte)0x52,(byte)0x2B,(byte)0xDB,(byte)0xEA,(byte)0x0F,(byte)0x1B,(byte)
                0x11,(byte)0x3F,(byte)0xA2,(byte)0xC8,(byte)0x62,(byte)0x16,(byte)0xC8,(byte)0xC6,(byte)
                0x10,(byte)0xA2,(byte)0xD5,(byte)0x8F,(byte)0x29,(byte)0xCF,(byte)0x33,(byte)0x55,(byte)
                0xCE,(byte)0xB1,(byte)0xBD,(byte)0x3E,(byte)0xF4,(byte)0x10,(byte)0xD1,(byte)0xED,(byte)
                0xD1,(byte)0xF7,(byte)0xAE,(byte)0x0F,(byte)0x16,(byte)0x89,(byte)0x79,(byte)0x79,(byte)
                0xDE,(byte)0x28,(byte)0xC6,(byte)0xEF,(byte)0x29,(byte)0x3E,(byte)0x0A,(byte)0x19,(byte)
                0x28,(byte)0x2B,(byte)0xD1,(byte)0xD7,(byte)0x93,(byte)0xF1,(byte)0x33,(byte)0x15,(byte)
                0x23,(byte)0xFC,(byte)0x71,(byte)0xA2,(byte)0x28,(byte)0x80,(byte)0x04,(byte)0x68,(byte)
                0xC0,(byte)0x1A,(byte)0x36,(byte)0x53,(byte)0xD1,(byte)0x4C,(byte)0x6B,(byte)0x48,(byte)
                0x51,(byte)0xA5,(byte)0xC0,(byte)0x29,(byte)0x47,(byte)0x8E,(byte)0x75,(byte)0x7F
        };
        capk_VISA_51.Exponent = new byte[]{0x03};
        capk_VISA_51.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_VISA_51.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_VISA_51);
        Log("Add CAPK capk_VISA_51:" + result + " ID:" + capk_VISA_51.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_VISA_51,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_VISA_51 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_VISA_53 = new EmvCAPK();
        capk_VISA_53.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03};
        capk_VISA_53.KeyID = (byte)0x53;
        capk_VISA_53.HashInd = (byte)0x01;
        capk_VISA_53.ArithInd = (byte)0x01;
        capk_VISA_53.Modul = new  byte[]{
                (byte)0xBC,(byte)0xD8,(byte)0x37,(byte)0x21,(byte)0xBE,(byte)0x52,(byte)0xCC,(byte)0xCC,(byte)
                0x4B,(byte)0x64,(byte)0x57,(byte)0x32,(byte)0x1F,(byte)0x22,(byte)0xA7,(byte)0xDC,(byte)
                0x76,(byte)0x9F,(byte)0x54,(byte)0xEB,(byte)0x80,(byte)0x25,(byte)0x91,(byte)0x3B,(byte)
                0xE8,(byte)0x04,(byte)0xD9,(byte)0xEA,(byte)0xBB,(byte)0xFA,(byte)0x19,(byte)0xB3,(byte)
                0xD7,(byte)0xC5,(byte)0xD3,(byte)0xCA,(byte)0x65,(byte)0x8D,(byte)0x76,(byte)0x8C,(byte)
                0xAF,(byte)0x57,(byte)0x06,(byte)0x7E,(byte)0xEC,(byte)0x83,(byte)0xC7,(byte)0xE6,(byte)
                0xE9,(byte)0xF8,(byte)0x1D,(byte)0x05,(byte)0x86,(byte)0x70,(byte)0x3E,(byte)0xD9,(byte)
                0xDD,(byte)0xDA,(byte)0xDD,(byte)0x20,(byte)0x67,(byte)0x5D,(byte)0x63,(byte)0x42,(byte)
                0x49,(byte)0x80,(byte)0xB1,(byte)0x0E,(byte)0xB3,(byte)0x64,(byte)0xE8,(byte)0x1E,(byte)
                0xB3,(byte)0x7D,(byte)0xB4,(byte)0x0E,(byte)0xD1,(byte)0x00,(byte)0x34,(byte)0x4C,(byte)
                0x92,(byte)0x88,(byte)0x86,(byte)0xFF,(byte)0x4C,(byte)0xCC,(byte)0x37,(byte)0x20,(byte)
                0x3E,(byte)0xE6,(byte)0x10,(byte)0x6D,(byte)0x5B,(byte)0x59,(byte)0xD1,(byte)0xAC,(byte)
                0x10,(byte)0x2E,(byte)0x2C,(byte)0xD2,(byte)0xD7,(byte)0xAC,(byte)0x17,(byte)0xF4,(byte)
                0xD9,(byte)0x6C,(byte)0x39,(byte)0x8E,(byte)0x5F,(byte)0xD9,(byte)0x93,(byte)0xEC,(byte)
                0xB4,(byte)0xFF,(byte)0xDF,(byte)0x79,(byte)0xB1,(byte)0x75,(byte)0x47,(byte)0xFF,(byte)
                0x9F,(byte)0xA2,(byte)0xAA,(byte)0x8E,(byte)0xEF,(byte)0xD6,(byte)0xCB,(byte)0xDA,(byte)
                0x12,(byte)0x4C,(byte)0xBB,(byte)0x17,(byte)0xA0,(byte)0xF8,(byte)0x52,(byte)0x81,(byte)
                0x46,(byte)0x38,(byte)0x71,(byte)0x35,(byte)0xE2,(byte)0x26,(byte)0xB0,(byte)0x05,(byte)
                0xA4,(byte)0x74,(byte)0xB9,(byte)0x06,(byte)0x2F,(byte)0xF2,(byte)0x64,(byte)0xD2,(byte)
                0xFF,(byte)0x8E,(byte)0xFA,(byte)0x36,(byte)0x81,(byte)0x4A,(byte)0xA2,(byte)0x95,(byte)
                0x00,(byte)0x65,(byte)0xB1,(byte)0xB0,(byte)0x4C,(byte)0x0A,(byte)0x1A,(byte)0xE9,(byte)
                0xB2,(byte)0xF6,(byte)0x9D,(byte)0x4A,(byte)0x4A,(byte)0xA9,(byte)0x79,(byte)0xD6,(byte)
                0xCE,(byte)0x95,(byte)0xFE,(byte)0xE9,(byte)0x48,(byte)0x5E,(byte)0xD0,(byte)0xA0,(byte)
                0x3A,(byte)0xEE,(byte)0x9B,(byte)0xD9,(byte)0x53,(byte)0xE8,(byte)0x1C,(byte)0xFD,(byte)
                0x1E,(byte)0xF6,(byte)0xE8,(byte)0x14,(byte)0xDF,(byte)0xD3,(byte)0xC2,(byte)0xCE,(byte)
                0x37,(byte)0xAE,(byte)0xFA,(byte)0x38,(byte)0xC1,(byte)0xF9,(byte)0x87,(byte)0x73,(byte)
                0x71,(byte)0xE9,(byte)0x1D,(byte)0x6A,(byte)0x5E,(byte)0xB5,(byte)0x9F,(byte)0xDE,(byte)
                0xDF,(byte)0x75,(byte)0xD3,(byte)0x32,(byte)0x5F,(byte)0xA3,(byte)0xCA,(byte)0x66,(byte)
                0xCD,(byte)0xFB,(byte)0xA0,(byte)0xE5,(byte)0x71,(byte)0x46,(byte)0xCC,(byte)0x78,(byte)
                0x98,(byte)0x18,(byte)0xFF,(byte)0x06,(byte)0xBE,(byte)0x5F,(byte)0xCC,(byte)0x50,(byte)
                0xAB,(byte)0xD3,(byte)0x62,(byte)0xAE,(byte)0x4B,(byte)0x80,(byte)0x99,(byte)0x6D
        };
        capk_VISA_53.Exponent = new byte[]{0x03};
        capk_VISA_53.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_VISA_53.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_VISA_53);
        Log("Add CAPK capk_VISA_53:" + result + " ID:" + capk_VISA_53.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_VISA_53,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_VISA_53 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_VISA_96 = new EmvCAPK();
        capk_VISA_96.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03};
        capk_VISA_96.KeyID = (byte)0x96;
        capk_VISA_96.HashInd = (byte)0x01;
        capk_VISA_96.ArithInd = (byte)0x01;
        capk_VISA_96.Modul = new  byte[]{
                (byte)0xB7,(byte)0x45,(byte)0x86,(byte)0xD1,(byte)0x9A,(byte)0x20,(byte)0x7B,(byte)0xE6,(byte)
                0x62,(byte)0x7C,(byte)0x5B,(byte)0x0A,(byte)0xAF,(byte)0xBC,(byte)0x44,(byte)0xA2,(byte)
                0xEC,(byte)0xF5,(byte)0xA2,(byte)0x94,(byte)0x2D,(byte)0x3A,(byte)0x26,(byte)0xCE,(byte)
                0x19,(byte)0xC4,(byte)0xFF,(byte)0xAE,(byte)0xEE,(byte)0x92,(byte)0x05,(byte)0x21,(byte)
                0x86,(byte)0x89,(byte)0x22,(byte)0xE8,(byte)0x93,(byte)0xE7,(byte)0x83,(byte)0x82,(byte)
                0x25,(byte)0xA3,(byte)0x94,(byte)0x7A,(byte)0x26,(byte)0x14,(byte)0x79,(byte)0x6F,(byte)
                0xB2,(byte)0xC0,(byte)0x62,(byte)0x8C,(byte)0xE8,(byte)0xC1,(byte)0x1E,(byte)0x38,(byte)
                0x25,(byte)0xA5,(byte)0x6D,(byte)0x3B,(byte)0x1B,(byte)0xBA,(byte)0xEF,(byte)0x78,(byte)
                0x3A,(byte)0x5C,(byte)0x6A,(byte)0x81,(byte)0xF3,(byte)0x6F,(byte)0x86,(byte)0x25,(byte)
                0x39,(byte)0x51,(byte)0x26,(byte)0xFA,(byte)0x98,(byte)0x3C,(byte)0x52,(byte)0x16,(byte)
                0xD3,(byte)0x16,(byte)0x6D,(byte)0x48,(byte)0xAC,(byte)0xDE,(byte)0x8A,(byte)0x43,(byte)
                0x12,(byte)0x12,(byte)0xFF,(byte)0x76,(byte)0x3A,(byte)0x7F,(byte)0x79,(byte)0xD9,(byte)
                0xED,(byte)0xB7,(byte)0xFE,(byte)0xD7,(byte)0x6B,(byte)0x48,(byte)0x5D,(byte)0xE4,(byte)
                0x5B,(byte)0xEB,(byte)0x82,(byte)0x9A,(byte)0x3D,(byte)0x47,(byte)0x30,(byte)0x84,(byte)
                0x8A,(byte)0x36,(byte)0x6D,(byte)0x33,(byte)0x24,(byte)0xC3,(byte)0x02,(byte)0x70,(byte)
                0x32,(byte)0xFF,(byte)0x8D,(byte)0x16,(byte)0xA1,(byte)0xE4,(byte)0x4D,(byte)0x8D
        };
        capk_VISA_96.Exponent = new byte[]{0x03};
        capk_VISA_96.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_VISA_96.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_VISA_96);
        Log("Add CAPK capk_VISA_96:" + result + " ID:" + capk_VISA_96.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_VISA_96,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_VISA_96 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_VISA_57 = new EmvCAPK();
        capk_VISA_57.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03};
        capk_VISA_57.KeyID = (byte)0x57;
        capk_VISA_57.HashInd = (byte)0x01;
        capk_VISA_57.ArithInd = (byte)0x01;
        capk_VISA_57.Modul = new  byte[]{
                (byte)0x94,(byte)0x2B,(byte)0x7F,(byte)0x2B,(byte)0xA5,(byte)0xEA,(byte)0x30,(byte)0x73,(byte)
                0x12,(byte)0xB6,(byte)0x3D,(byte)0xF7,(byte)0x7C,(byte)0x52,(byte)0x43,(byte)0x61,(byte)
                0x8A,(byte)0xCC,(byte)0x20,(byte)0x02,(byte)0xBD,(byte)0x7E,(byte)0xCB,(byte)0x74,(byte)
                0xD8,(byte)0x21,(byte)0xFE,(byte)0x7B,(byte)0xDC,(byte)0x78,(byte)0xBF,(byte)0x28,(byte)
                0xF4,(byte)0x9F,(byte)0x74,(byte)0x19,(byte)0x0A,(byte)0xD9,(byte)0xB2,(byte)0x3B,(byte)
                0x97,(byte)0x13,(byte)0xB1,(byte)0x40,(byte)0xFF,(byte)0xEC,(byte)0x1F,(byte)0xB4,(byte)
                0x29,(byte)0xD9,(byte)0x3F,(byte)0x56,(byte)0xBD,(byte)0xC7,(byte)0xAD,(byte)0xE4,(byte)
                0xAC,(byte)0x07,(byte)0x5D,(byte)0x75,(byte)0x53,(byte)0x2C,(byte)0x1E,(byte)0x59,(byte)
                0x0B,(byte)0x21,(byte)0x87,(byte)0x4C,(byte)0x79,(byte)0x52,(byte)0xF2,(byte)0x9B,(byte)
                0x8C,(byte)0x0F,(byte)0x0C,(byte)0x1C,(byte)0xE3,(byte)0xAE,(byte)0xED,(byte)0xC8,(byte)
                0xDA,(byte)0x25,(byte)0x34,(byte)0x31,(byte)0x23,(byte)0xE7,(byte)0x1D,(byte)0xCF,(byte)
                0x86,(byte)0xC6,(byte)0x99,(byte)0x8E,(byte)0x15,(byte)0xF7,(byte)0x56,(byte)0xE3
        };
        capk_VISA_57.Exponent = new byte[]{0x01,0x00,0x01};
        capk_VISA_57.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_VISA_57.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_VISA_57);
        Log("Add CAPK capk_VISA_57:" + result + " ID:" + capk_VISA_57.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_VISA_57,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_VISA_57 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_VISA_58 = new EmvCAPK();
        capk_VISA_58.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03};
        capk_VISA_58.KeyID = (byte)0x58;
        capk_VISA_58.HashInd = (byte)0x01;
        capk_VISA_58.ArithInd = (byte)0x01;
        capk_VISA_58.Modul = new  byte[]{
                (byte)0x99,(byte)0x55,(byte)0x2C,(byte)0x4A,(byte)0x1E,(byte)0xCD,(byte)0x68,(byte)0xA0,(byte)
                0x26,(byte)0x01,(byte)0x57,(byte)0xFC,(byte)0x41,(byte)0x51,(byte)0xB5,(byte)0x99,(byte)
                0x28,(byte)0x37,(byte)0x44,(byte)0x5D,(byte)0x3F,(byte)0xC5,(byte)0x73,(byte)0x65,(byte)
                0xCA,(byte)0x56,(byte)0x92,(byte)0xC8,(byte)0x7B,(byte)0xE3,(byte)0x58,(byte)0xCD,(byte)
                0xCD,(byte)0xF2,(byte)0xC9,(byte)0x2F,(byte)0xB6,(byte)0x83,(byte)0x75,(byte)0x22,(byte)
                0x84,(byte)0x2A,(byte)0x48,(byte)0xEB,(byte)0x11,(byte)0xCD,(byte)0xFF,(byte)0xE2,(byte)
                0xFD,(byte)0x91,(byte)0x77,(byte)0x0C,(byte)0x72,(byte)0x21,(byte)0xE4,(byte)0xAF,(byte)
                0x62,(byte)0x07,(byte)0xC2,(byte)0xDE,(byte)0x40,(byte)0x04,(byte)0xC7,(byte)0xDE,(byte)
                0xE1,(byte)0xB6,(byte)0x27,(byte)0x6D,(byte)0xC6,(byte)0x2D,(byte)0x52,(byte)0xA8,(byte)
                0x7D,(byte)0x2C,(byte)0xD0,(byte)0x1F,(byte)0xBF,(byte)0x2D,(byte)0xC4,(byte)0x06,(byte)
                0x5D,(byte)0xB5,(byte)0x28,(byte)0x24,(byte)0xD2,(byte)0xA2,(byte)0x16,(byte)0x7A,(byte)
                0x06,(byte)0xD1,(byte)0x9E,(byte)0x6A,(byte)0x0F,(byte)0x78,(byte)0x10,(byte)0x71,(byte)
                0xCD,(byte)0xB2,(byte)0xDD,(byte)0x31,(byte)0x4C,(byte)0xB9,(byte)0x44,(byte)0x41,(byte)
                0xD8,(byte)0xDC,(byte)0x0E,(byte)0x93,(byte)0x63,(byte)0x17,(byte)0xB7,(byte)0x7B,(byte)
                0xF0,(byte)0x6F,(byte)0x51,(byte)0x77,(byte)0xF6,(byte)0xC5,(byte)0xAB,(byte)0xA3,(byte)
                0xA3,(byte)0xBC,(byte)0x6A,(byte)0xA3,(byte)0x02,(byte)0x09,(byte)0xC9,(byte)0x72,(byte)
                0x60,(byte)0xB7,(byte)0xA1,(byte)0xAD,(byte)0x3A,(byte)0x19,(byte)0x2C,(byte)0x9B,(byte)
                0x8C,(byte)0xD1,(byte)0xD1,(byte)0x53,(byte)0x57,(byte)0x0A,(byte)0xFC,(byte)0xC8,(byte)
                0x7C,(byte)0x3C,(byte)0xD6,(byte)0x81,(byte)0xD1,(byte)0x3E,(byte)0x99,(byte)0x7F,(byte)
                0xE3,(byte)0x3B,(byte)0x39,(byte)0x63,(byte)0xA0,(byte)0xA1,(byte)0xC7,(byte)0x97,(byte)
                0x72,(byte)0xAC,(byte)0xF9,(byte)0x91,(byte)0x03,(byte)0x3E,(byte)0x1B,(byte)0x83,(byte)
                0x97,(byte)0xAD,(byte)0x03,(byte)0x41,(byte)0x50,(byte)0x0E,(byte)0x48,(byte)0xA2,(byte)
                0x47,(byte)0x70,(byte)0xBC,(byte)0x4C,(byte)0xBE,(byte)0x19,(byte)0xD2,(byte)0xCC,(byte)
                0xF4,(byte)0x19,(byte)0x50,(byte)0x4F,(byte)0xDB,(byte)0xF0,(byte)0x38,(byte)0x9B,(byte)
                0xC2,(byte)0xF2,(byte)0xFD,(byte)0xCD,(byte)0x4D,(byte)0x44,(byte)0xE6,(byte)0x1F
        };
        capk_VISA_58.Exponent = new byte[]{0x01,0x00,0x01};
        capk_VISA_58.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_VISA_58.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_VISA_58);
        Log("Add CAPK capk_VISA_58:" + result + " ID:" + capk_VISA_58.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_VISA_58,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_VISA_58 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_VISA_54 = new EmvCAPK();
        capk_VISA_54.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x03};
        capk_VISA_54.KeyID = (byte)0x54;
        capk_VISA_54.HashInd = (byte)0x01;
        capk_VISA_54.ArithInd = (byte)0x01;
        capk_VISA_54.Modul = new  byte[]{
                (byte)0xC6,(byte)0xDD,(byte)0xC0,(byte)0xB7,(byte)0x64,(byte)0x5F,(byte)0x7F,(byte)0x16,(byte)
                0x28,(byte)0x6A,(byte)0xB7,(byte)0xE4,(byte)0x11,(byte)0x66,(byte)0x55,(byte)0xF5,(byte)
                0x6D,(byte)0xD0,(byte)0xC9,(byte)0x44,(byte)0x76,(byte)0x60,(byte)0x40,(byte)0xDC,(byte)
                0x68,(byte)0x66,(byte)0x4D,(byte)0xD9,(byte)0x73,(byte)0xBD,(byte)0x3B,(byte)0xFD,(byte)
                0x4C,(byte)0x52,(byte)0x5B,(byte)0xCB,(byte)0xB9,(byte)0x52,(byte)0x72,(byte)0xB6,(byte)
                0xB3,(byte)0xAD,(byte)0x9B,(byte)0xA8,(byte)0x86,(byte)0x03,(byte)0x03,(byte)0xAD,(byte)
                0x08,(byte)0xD9,(byte)0xE8,(byte)0xCC,(byte)0x34,(byte)0x4A,(byte)0x40,(byte)0x70,(byte)
                0xF4,(byte)0xCF,(byte)0xB9,(byte)0xEE,(byte)0xAF,(byte)0x29,(byte)0xC8,(byte)0xA3,(byte)
                0x46,(byte)0x08,(byte)0x50,(byte)0xC2,(byte)0x64,(byte)0xCD,(byte)0xA3,(byte)0x9B,(byte)
                0xBE,(byte)0x3A,(byte)0x7E,(byte)0x7D,(byte)0x08,(byte)0xA6,(byte)0x9C,(byte)0x31,(byte)
                0xB5,(byte)0xC8,(byte)0xDD,(byte)0x9F,(byte)0x94,(byte)0xDD,(byte)0xBC,(byte)0x92,(byte)
                0x65,(byte)0x75,(byte)0x8C,(byte)0x0E,(byte)0x73,(byte)0x99,(byte)0xAD,(byte)0xCF,(byte)
                0x43,(byte)0x62,(byte)0xCA,(byte)0xEE,(byte)0x45,(byte)0x8D,(byte)0x41,(byte)0x4C,(byte)
                0x52,(byte)0xB4,(byte)0x98,(byte)0x27,(byte)0x48,(byte)0x81,(byte)0xB1,(byte)0x96,(byte)
                0xDA,(byte)0xCC,(byte)0xA7,(byte)0x27,(byte)0x3F,(byte)0x68,(byte)0x7F,(byte)0x2A,(byte)
                0x65,(byte)0xFA,(byte)0xEB,(byte)0x80,(byte)0x9D,(byte)0x4B,(byte)0x2A,(byte)0xC1,(byte)
                0xD3,(byte)0xD1,(byte)0xEF,(byte)0xB4,(byte)0xF6,(byte)0x49,(byte)0x03,(byte)0x22,(byte)
                0x31,(byte)0x8B,(byte)0xD2,(byte)0x96,(byte)0xD1,(byte)0x53,(byte)0xB3,(byte)0x07,(byte)
                0xA3,(byte)0x28,(byte)0x3A,(byte)0xB4,(byte)0xE5,(byte)0xBE,(byte)0x6E,(byte)0xBD,(byte)
                0x91,(byte)0x03,(byte)0x59,(byte)0xA8,(byte)0x56,(byte)0x5E,(byte)0xB9,(byte)0xC4,(byte)
                0x36,(byte)0x0D,(byte)0x24,(byte)0xBA,(byte)0xAC,(byte)0xA3,(byte)0xDB,(byte)0xFE,(byte)
                0x39,(byte)0x3F,(byte)0x3D,(byte)0x6C,(byte)0x83,(byte)0x0D,(byte)0x60,(byte)0x3C,(byte)
                0x6F,(byte)0xC1,(byte)0xE8,(byte)0x34,(byte)0x09,(byte)0xDF,(byte)0xCD,(byte)0x80,(byte)
                0xD3,(byte)0xA3,(byte)0x3B,(byte)0xA2,(byte)0x43,(byte)0x81,(byte)0x3B,(byte)0xBB,(byte)
                0x4C,(byte)0xEA,(byte)0xF9,(byte)0xCB,(byte)0xAB,(byte)0x6B,(byte)0x74,(byte)0xB0,(byte)
                0x01,(byte)0x16,(byte)0xF7,(byte)0x2A,(byte)0xB2,(byte)0x78,(byte)0xA8,(byte)0x8A,(byte)
                0x01,(byte)0x1D,(byte)0x70,(byte)0x07,(byte)0x1E,(byte)0x06,(byte)0xCA,(byte)0xB1,(byte)
                0x40,(byte)0x64,(byte)0x64,(byte)0x38,(byte)0xD9,(byte)0x86,(byte)0xD4,(byte)0x82,(byte)
                0x81,(byte)0x62,(byte)0x4B,(byte)0x85,(byte)0xB3,(byte)0xB2,(byte)0xEB,(byte)0xB9,(byte)
                0xA6,(byte)0xAB,(byte)0x3B,(byte)0xF2,(byte)0x17,(byte)0x8F,(byte)0xCC,(byte)0x30,(byte)
                0x11,(byte)0xE7,(byte)0xCA,(byte)0xF2,(byte)0x48,(byte)0x97,(byte)0xAE,(byte)0x7D
        };
        capk_VISA_54.Exponent = new byte[]{0x01,0x00,0x01};
        capk_VISA_54.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_VISA_54.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_VISA_54);
        Log("Add CAPK capk_VISA_54:" + result + " ID:" + capk_VISA_54.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_VISA_54,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_VISA_54 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_AMEX_60 = new EmvCAPK();
        capk_AMEX_60.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x25};
        capk_AMEX_60.KeyID = (byte)0x60;
        capk_AMEX_60.HashInd = (byte)0x01;
        capk_AMEX_60.ArithInd = (byte)0x01;
        capk_AMEX_60.Modul = new  byte[]{
                (byte)0xA8,(byte)0xEE,(byte)0x74,(byte)0xED,(byte)0xEF,(byte)0x3C,(byte)0x0D,(byte)0xCA,(byte)
                0x51,(byte)0x02,(byte)0xFF,(byte)0x9B,(byte)0x57,(byte)0x07,(byte)0x97,(byte)0x5F,(byte)
                0xF6,(byte)0x7B,(byte)0x60,(byte)0xD6,(byte)0x4B,(byte)0x5E,(byte)0x73,(byte)0x22,(byte)
                0xD4,(byte)0x8D,(byte)0xE9,(byte)0xD3,(byte)0xBB,(byte)0x61,(byte)0x53,(byte)0xF6,(byte)
                0x35,(byte)0x12,(byte)0xA0,(byte)0x91,(byte)0xB6,(byte)0x06,(byte)0xDD,(byte)0x8F,(byte)
                0xD5,(byte)0xF6,(byte)0xA1,(byte)0x45,(byte)0x88,(byte)0x32,(byte)0x4E,(byte)0xF8,(byte)
                0x82,(byte)0x78,(byte)0x44,(byte)0xC7,(byte)0xFF,(byte)0xC0,(byte)0xBA,(byte)0xB2,(byte)
                0x33,(byte)0x4A,(byte)0xE5,(byte)0x20,(byte)0x77,(byte)0x70,(byte)0x07,(byte)0x8B,(byte)
                0x69,(byte)0xCD,(byte)0xC3,(byte)0xF2,(byte)0xC6,(byte)0x66,(byte)0xCF,(byte)0x69,(byte)
                0xE2,(byte)0x8E,(byte)0x16,(byte)0xE1,(byte)0x81,(byte)0x67,(byte)0x14,(byte)0xC4,(byte)
                0xDF,(byte)0x31,(byte)0x3B,(byte)0xEF,(byte)0x53,(byte)0x9C,(byte)0xC0,(byte)0x1D,(byte)
                0xA9,(byte)0xDD,(byte)0x2D,(byte)0x6F,(byte)0x47,(byte)0xDE,(byte)0x4F,(byte)0x24,(byte)
                0x7C,(byte)0x50,(byte)0x0B,(byte)0x56,(byte)0x1C,(byte)0x09,(byte)0x91,(byte)0x66,(byte)
                0xAD,(byte)0x4F,(byte)0xC1,(byte)0x6D,(byte)0xF1,(byte)0x2D,(byte)0xFB,(byte)0x68,(byte)
                0x4A,(byte)0xC4,(byte)0x8D,(byte)0x35,(byte)0xCD,(byte)0xD2,(byte)0xC4,(byte)0x7A,(byte)
                0x13,(byte)0xA8,(byte)0x6A,(byte)0x5A,(byte)0x16,(byte)0x23,(byte)0x06,(byte)0xF6,(byte)
                0x4E,(byte)0x33,(byte)0xB0,(byte)0x92,(byte)0xAB,(byte)0x74,(byte)0xED,(byte)0xA7,(byte)
                0x1A,(byte)0x40,(byte)0x91,(byte)0xD9,(byte)0x6E,(byte)0x3D,(byte)0xAA,(byte)0x47
        };
        capk_AMEX_60.Exponent = new byte[]{0x01,0x00,0x01};
        capk_AMEX_60.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_AMEX_60.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_AMEX_60);
        Log("Add CAPK capk_AMEX_60:" + result + " ID:" + capk_AMEX_60.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_AMEX_60,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_AMEX_60 database:" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_AMEX_61 = new EmvCAPK();
        capk_AMEX_61.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x25};
        capk_AMEX_61.KeyID = (byte)0x61;
        capk_AMEX_61.HashInd = (byte)0x01;
        capk_AMEX_61.ArithInd = (byte)0x01;
        capk_AMEX_61.Modul = new  byte[]{
                (byte)0x86,(byte)0xC7,(byte)0x25,(byte)0x46,(byte)0x65,(byte)0xE1,(byte)0x7C,(byte)0xE6,(byte)
                0x93,(byte)0x4D,(byte)0xF7,(byte)0xD0,(byte)0x82,(byte)0x56,(byte)0x9F,(byte)0x20,(byte)
                0x8D,(byte)0x1C,(byte)0xC1,(byte)0xAD,(byte)0x8E,(byte)0x9F,(byte)0xB2,(byte)0xFE,(byte)
                0x23,(byte)0xE3,(byte)0xD7,(byte)0x46,(byte)0x7B,(byte)0xE5,(byte)0x0B,(byte)0x4F,(byte)
                0x87,(byte)0x4F,(byte)0x90,(byte)0x6A,(byte)0xDF,(byte)0x22,(byte)0x80,(byte)0xEC,(byte)
                0x9D,(byte)0x20,(byte)0x4F,(byte)0x6D,(byte)0x10,(byte)0xC0,(byte)0x37,(byte)0xA2,(byte)
                0x3C,(byte)0xE5,(byte)0xFD,(byte)0x82,(byte)0x83,(byte)0xC9,(byte)0xED,(byte)0x47,(byte)
                0xD1,(byte)0xC6,(byte)0x69,(byte)0xAB,(byte)0xDD,(byte)0x7C,(byte)0x1C,(byte)0xB3,(byte)
                0x56,(byte)0xC7,(byte)0x0B,(byte)0xCD,(byte)0xC4,(byte)0x4E,(byte)0x5C,(byte)0x8A,(byte)
                0xE2,(byte)0x31,(byte)0x55,(byte)0x5F,(byte)0x7B,(byte)0x78,(byte)0x6A,(byte)0xC9,(byte)
                0xC3,(byte)0x15,(byte)0x5B,(byte)0xCD,(byte)0x51,(byte)0xF2,(byte)0x8E,(byte)0xFB,(byte)
                0xC1,(byte)0xB3,(byte)0x3C,(byte)0xC8,(byte)0x72,(byte)0x77,(byte)0x04,(byte)0x92,(byte)
                0x19,(byte)0xB2,(byte)0xC8,(byte)0x90,(byte)0x95,(byte)0x27,(byte)0x36,(byte)0xC4,(byte)
                0x71,(byte)0x34,(byte)0x87,(byte)0x11,(byte)0x16,(byte)0x78,(byte)0x91,(byte)0x1D,(byte)
                0x9F,(byte)0x42,(byte)0xE0,(byte)0x80,(byte)0x74,(byte)0xCF,(byte)0x52,(byte)0x4E,(byte)
                0x65,(byte)0xD7,(byte)0x21,(byte)0xD7,(byte)0x27,(byte)0xF0,(byte)0x54,(byte)0xE6,(byte)
                0xB5,(byte)0xE8,(byte)0x5E,(byte)0xC9,(byte)0x2B,(byte)0x3E,(byte)0xB5,(byte)0x9F,(byte)
                0xFE,(byte)0xE9,(byte)0x26,(byte)0xDD,(byte)0x6C,(byte)0x31,(byte)0x4D,(byte)0xF5,(byte)
                0x55,(byte)0xC9,(byte)0x4A,(byte)0xD4,(byte)0x87,(byte)0xA9,(byte)0x9B,(byte)0x67,(byte)
                0xCB,(byte)0x7C,(byte)0x7B,(byte)0xA5,(byte)0xE4,(byte)0x6A,(byte)0x5B,(byte)0x81,(byte)
                0x3D,(byte)0xDB,(byte)0x91,(byte)0x8B,(byte)0x8E,(byte)0x3E,(byte)0x04,(byte)0x23,(byte)
                0xF4,(byte)0x30,(byte)0x2A,(byte)0x58,(byte)0x68,(byte)0x6D,(byte)0x12,(byte)0x63,(byte)
                0xC0,(byte)0xBA,(byte)0xCA,(byte)0x9E,(byte)0x82,(byte)0x06,(byte)0x8C,(byte)0x49,(byte)
                0x32,(byte)0x89,(byte)0xE3,(byte)0xE6,(byte)0x93,(byte)0x6E,(byte)0xCA,(byte)0x5F,(byte)
                0x9F,(byte)0x77,(byte)0xE0,(byte)0x6B,(byte)0x0D,(byte)0x6F,(byte)0xBD,(byte)0xA7,(byte)
                0x18,(byte)0x81,(byte)0x8B,(byte)0x83,(byte)0x50,(byte)0x20,(byte)0x09,(byte)0x8C,(byte)
                0x67,(byte)0x1C,(byte)0x5D,(byte)0xD7,(byte)0xE9,(byte)0xB8,(byte)0xE8,(byte)0xE8,(byte)
                0x41,(byte)0xD2,(byte)0xDF,(byte)0x32,(byte)0xEE,(byte)0x94,(byte)0xA7,(byte)0xF4,(byte)
                0x74,(byte)0x84,(byte)0x84,(byte)0xCA,(byte)0x44,(byte)0x10,(byte)0x8A,(byte)0xB2,(byte)
                0x41,(byte)0xA5,(byte)0x26,(byte)0x3B,(byte)0xA1,(byte)0xFF,(byte)0x00,(byte)0xD5,(byte)
                0x13,(byte)0x60,(byte)0xDD,(byte)0xDC,(byte)0x74,(byte)0x9D,(byte)0x30,(byte)0xA1
        };
        capk_AMEX_61.Exponent = new byte[]{0x01,0x00,0x01};
        capk_AMEX_61.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_AMEX_61.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_AMEX_61);
        Log("Add CAPK capk_AMEX_61:" + result + " ID:" + capk_AMEX_61.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_AMEX_61,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_AMEX_61 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_D0 = new EmvCAPK();
        capk_D0.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x52};
        capk_D0.KeyID = (byte)0xD0;
        capk_D0.HashInd = (byte)0x01;
        capk_D0.ArithInd = (byte)0x01;
        capk_D0.Modul = new  byte[]{
                (byte)0xD0,(byte)0x5C,(byte)0x2A,(byte)0x09,(byte)0xD0,(byte)0x9C,(byte)0x90,(byte)0x31,(byte)
                0x36,(byte)0x6E,(byte)0xC0,(byte)0x92,(byte)0xBC,(byte)0xAC,(byte)0x67,(byte)0xD4,(byte)
                0xB1,(byte)0xB4,(byte)0xF8,(byte)0x8B,(byte)0x10,(byte)0x00,(byte)0x5E,(byte)0x1F,(byte)
                0xC4,(byte)0x5C,(byte)0x1B,(byte)0x48,(byte)0x3A,(byte)0xE7,(byte)0xEB,(byte)0x86,(byte)
                0xFF,(byte)0x0E,(byte)0x88,(byte)0x4A,(byte)0x19,(byte)0xC0,(byte)0x59,(byte)0x5A,(byte)
                0x6C,(byte)0x34,(byte)0xF0,(byte)0x63,(byte)0x86,(byte)0xD7,(byte)0x76,(byte)0xA2,(byte)
                0x1D,(byte)0x62,(byte)0x0F,(byte)0xC9,(byte)0xF9,(byte)0xC4,(byte)0x98,(byte)0xAD,(byte)
                0xCA,(byte)0x00,(byte)0xE6,(byte)0x6D,(byte)0x12,(byte)0x9B,(byte)0xCD,(byte)0xD4,(byte)
                0x78,(byte)0x98,(byte)0x37,(byte)0xB9,(byte)0x6D,(byte)0xCC,(byte)0x7F,(byte)0x09,(byte)
                0xDA,(byte)0x94,(byte)0xCC,(byte)0xAC,(byte)0x5A,(byte)0xC7,(byte)0xCF,(byte)0xC0,(byte)
                0x7F,(byte)0x46,(byte)0x00,(byte)0xDF,(byte)0x78,(byte)0xE4,(byte)0x93,(byte)0xDC,(byte)
                0x19,(byte)0x57,(byte)0xDE,(byte)0xBA,(byte)0x3F,(byte)0x48,(byte)0x38,(byte)0xA4,(byte)
                0xB8,(byte)0xBD,(byte)0x4C,(byte)0xEF,(byte)0xE4,(byte)0xE4,(byte)0xC6,(byte)0x11,(byte)
                0x90,(byte)0x85,(byte)0xE5,(byte)0xBB,(byte)0x21,(byte)0x07,(byte)0x73,(byte)0x41,(byte)
                0xC5,(byte)0x68,(byte)0xA2,(byte)0x1D,(byte)0x65,(byte)0xD0,(byte)0x49,(byte)0xD6,(byte)
                0x66,(byte)0x80,(byte)0x7C,(byte)0x39,(byte)0xC4,(byte)0x01,(byte)0xCD,(byte)0xFE,(byte)
                0xE7,(byte)0xF7,(byte)0xF9,(byte)0x9B,(byte)0x8F,(byte)0x9C,(byte)0xB3,(byte)0x4A,(byte)
                0x88,(byte)0x41,(byte)0xEA,(byte)0x62,(byte)0xE8,(byte)0x3E,(byte)0x8D,(byte)0x63
        };
        capk_D0.Exponent = new byte[]{0x01,0x00,0x01};
        capk_D0.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_D0.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_D0);
        Log("Add CAPK capk_D0:" + result + " ID:" + capk_D0.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_D0,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_D0 database:" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_D1 = new EmvCAPK();
        capk_D1.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x52};
        capk_D1.KeyID = (byte)0xD1;
        capk_D1.HashInd = (byte)0x01;
        capk_D1.ArithInd = (byte)0x01;
        capk_D1.Modul = new  byte[]{
                (byte)0xA7,(byte)0x1A,(byte)0xF9,(byte)0x77,(byte)0xC1,(byte)0x07,(byte)0x93,(byte)0x04,(byte)
                0xD6,(byte)0xDF,(byte)0xF3,(byte)0xF6,(byte)0x65,(byte)0xAB,(byte)0x6D,(byte)0xB3,(byte)
                0xFB,(byte)0xDF,(byte)0xA1,(byte)0xB1,(byte)0x70,(byte)0x28,(byte)0x7A,(byte)0xC6,(byte)
                0xD7,(byte)0xBC,(byte)0x0A,(byte)0xFC,(byte)0xB7,(byte)0xA2,(byte)0x02,(byte)0xA4,(byte)
                0xC8,(byte)0x15,(byte)0xE1,(byte)0xFC,(byte)0x2E,(byte)0x34,(byte)0xF7,(byte)0x5A,(byte)
                0x05,(byte)0x25,(byte)0x64,(byte)0xEE,(byte)0x21,(byte)0x48,(byte)0xA3,(byte)0x9C,(byte)
                0xD6,(byte)0xB0,(byte)0xF3,(byte)0x9C,(byte)0xFA,(byte)0xEF,(byte)0x95,(byte)0xF0,(byte)
                0x29,(byte)0x4A,(byte)0x86,(byte)0xC3,(byte)0x19,(byte)0x8E,(byte)0x34,(byte)0x9F,(byte)
                0xF8,(byte)0x2E,(byte)0xEC,(byte)0xE6,(byte)0x33,(byte)0xD5,(byte)0x0E,(byte)0x58,(byte)
                0x60,(byte)0xA1,(byte)0x50,(byte)0x82,(byte)0xB4,(byte)0xB3,(byte)0x42,(byte)0xA9,(byte)
                0x09,(byte)0x28,(byte)0x02,(byte)0x40,(byte)0x57,(byte)0xDD,(byte)0x51,(byte)0xA2,(byte)
                0x40,(byte)0x1D,(byte)0x78,(byte)0x1B,(byte)0x67,(byte)0xAE,(byte)0x75,(byte)0x98,(byte)
                0xD5,(byte)0xD1,(byte)0xFF,(byte)0x26,(byte)0xA4,(byte)0x41,(byte)0x97,(byte)0x0A,(byte)
                0x19,(byte)0xA3,(byte)0xA5,(byte)0x80,(byte)0x11,(byte)0xCA,(byte)0x19,(byte)0x28,(byte)
                0x42,(byte)0x79,(byte)0xA8,(byte)0x55,(byte)0x67,(byte)0xD3,(byte)0x11,(byte)0x92,(byte)
                0x64,(byte)0x80,(byte)0x6C,(byte)0xAF,(byte)0x76,(byte)0x11,(byte)0x22,(byte)0xA7,(byte)
                0x1F,(byte)0xC0,(byte)0x49,(byte)0x2A,(byte)0xC8,(byte)0xD8,(byte)0xD4,(byte)0x2B,(byte)
                0x03,(byte)0x6C,(byte)0x39,(byte)0x4F,(byte)0xC4,(byte)0x94,(byte)0xE0,(byte)0x3B,(byte)
                0x43,(byte)0x60,(byte)0x0D,(byte)0x7E,(byte)0x02,(byte)0xCB,(byte)0x52,(byte)0x67,(byte)
                0x75,(byte)0x5A,(byte)0xCE,(byte)0x64,(byte)0x43,(byte)0x7C,(byte)0xFA,(byte)0x7B,(byte)
                0x47,(byte)0x5A,(byte)0xD4,(byte)0x0D,(byte)0xDC,(byte)0x93,(byte)0xB8,(byte)0xC9,(byte)
                0xBC,(byte)0xAD,(byte)0x63,(byte)0x80,(byte)0x1F,(byte)0xC4,(byte)0x92,(byte)0xFD,(byte)
                0x25,(byte)0x16,(byte)0x40,(byte)0xE4,(byte)0x1F,(byte)0xD1,(byte)0x3F,(byte)0x6E,(byte)
                0x23,(byte)0x1F,(byte)0x56,(byte)0xF9,(byte)0x72,(byte)0x83,(byte)0x44,(byte)0x7A,(byte)
                0xB4,(byte)0x4C,(byte)0xBE,(byte)0x11,(byte)0x91,(byte)0x0D,(byte)0xB3,(byte)0xC7,(byte)
                0x52,(byte)0x43,(byte)0x78,(byte)0x4A,(byte)0xA9,(byte)0xBD,(byte)0xF5,(byte)0x75,(byte)
                0x39,(byte)0xC3,(byte)0x1B,(byte)0x51,(byte)0xC9,(byte)0xF3,(byte)0x5B,(byte)0xF8,(byte)
                0xBC,(byte)0x24,(byte)0x95,(byte)0x76,(byte)0x28,(byte)0x81,(byte)0x25,(byte)0x54,(byte)
                0x78,(byte)0x26,(byte)0x4B,(byte)0x79,(byte)0x2B,(byte)0xBD,(byte)0xCA,(byte)0x64,(byte)
                0x98,(byte)0x77,(byte)0x7A,(byte)0xE9,(byte)0x12,(byte)0x0E,(byte)0xD9,(byte)0x35,(byte)
                0xBB,(byte)0x3E,(byte)0x8B,(byte)0xEA,(byte)0x3E,(byte)0xAB,(byte)0x13,(byte)0xD9
        };
        capk_D1.Exponent = new byte[]{0x01,0x00,0x01};
        capk_D1.ExpDate = new byte[]{0x15,0x12,0x31};
        capk_D1.CheckSum = new byte[]{
                (byte)0xCC,(byte)0x95,(byte)0x85,(byte)0xE8,(byte)0xE6,(byte)0x37,(byte)0x19,(byte)0x1C,(byte)0x10,(byte)0xFC,(byte)0xEC,(byte)0xB3,(byte)0x2B,(byte)0x5A,(byte)0xE1,(byte)0xB9,(byte)0xD4,(byte)0x10,(byte)0xB5,(byte)0x2D
        };

        result =  EmvService.Emv_AddCapk(capk_D1);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk_D1.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_D1,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_D1 database:" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK verve_05 = new EmvCAPK();
        verve_05.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x71};
        verve_05.KeyID = (byte)0x05;
        verve_05.HashInd = (byte)0x01;
        verve_05.ArithInd = (byte)0x01;
        verve_05.Modul = StringUtil.hexStringToByte("B036A8CAE0593A480976BFE84F8A67759E52B3D9F4A68CCC37FE720E594E5694CD1AE20E1B120D7A18FA5C70E044D3B12E932C9BBD9FDEA4BE11071EF8CA3AF48FF2B5DDB307FC752C5C73F5F274D4238A92B4FCE66FC93DA18E6C1CC1AA3CFAFCB071B67DAACE96D9314DB494982F5C967F698A05E1A8A69DA931B8E566270F04EAB575F5967104118E4F12ABFF9DEC92379CD955A10675282FE1B60CAD13F9BB80C272A40B6A344EA699FB9EFA6867");
        verve_05.Exponent = new byte[]{0x03};
        verve_05.ExpDate = new byte[]{0x24,0x12,0x31};
        verve_05.CheckSum = StringUtil.hexStringToByte("676822D335AB0D2C3848418CB546DF7B6A6C32C0");

        result =  EmvService.Emv_AddCapk(verve_05);
        Log("Add CAPK capk_D1:" + result + " ID:" + verve_05.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_D1,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_D1 database:" + dbResult);
        }

        EmvCAPK verve_04 = new EmvCAPK();
        verve_04.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x71};
        verve_04.KeyID = (byte)0x04;
        verve_04.HashInd = (byte)0x01;
        verve_04.ArithInd = (byte)0x01;
        verve_04.Modul = StringUtil.hexStringToByte("D13CD5E1B921E4E0F0D40E2DE14CCE73E3A34ED2DCFA826531D8195641091E37C8474D19B686E8243F089A69F7B18D2D34CB4824F228F7750F96D1EFBDFF881F259A8C04DE64915A3A3D7CB846135F4083C93CDE755BC808886F600542DFF085558D5EA7F45CB15EC835064AA856D602A0A44CD021F54CF8EC0CC680B54B3665ABE74A7C43D02897FF84BB4CB98BC91D");
        verve_04.Exponent = new byte[]{0x03};
        verve_04.ExpDate = new byte[]{0x17,0x12,0x31};
        verve_04.CheckSum = StringUtil.hexStringToByte("8B36A3E3D814CE6C6EBEAAF27674BB7BC67275B1");

        result =  EmvService.Emv_AddCapk(verve_04);
        Log("Add CAPK capk_D1:" + result + " ID:" + verve_04.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_D1,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_D1 database:" + dbResult);
        }

        EmvCAPK verve_03 = new EmvCAPK();
        verve_03.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x71};
        verve_03.KeyID = (byte)0x03;
        verve_03.HashInd = (byte)0x01;
        verve_03.ArithInd = (byte)0x01;
        verve_03.Modul = StringUtil.hexStringToByte("d06238b856cf2c8890a7f668ca17c19247498d193a7c11e7105dedeee6a873e8189e50493e9b17547c42ea4fa88bbef30bb6bc2409246ccc95f36622a7f4d92d46444f20b1b24bf63c5b28395d8ef18c23205c2119dfe5fba2fbfc311b2fe8a6a75b35a7dab72d421792a500cdfd8133b8a97d84a49c0bd22d52d06ea5e0ef3e471d47d8370c37aa48b564689d0035d9");
        verve_03.Exponent = new byte[]{0x03};
        verve_03.ExpDate = new byte[]{0x17,0x12,0x31};
        verve_03.CheckSum = StringUtil.hexStringToByte("319F3C608B67F1118C729B0E1516EAB07CB290C8");

        result =  EmvService.Emv_AddCapk(verve_03);
        Log("Add CAPK capk_D1:" + result + " ID:" + verve_03.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_D1,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_D1 database:" + dbResult);
        }

        EmvCAPK verve_06 = new EmvCAPK();
        verve_06.RID =  new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x71};
        verve_06.KeyID = (byte)0x06;
        verve_06.HashInd = (byte)0x01;
        verve_06.ArithInd = (byte)0x01;
        verve_06.Modul = StringUtil.hexStringToByte("D2DA0134B4DFC93A75EE8960C99896D50A91527B87BA7B16CDB77E5B6FDB750EB70B54026CADDA1D562C77A2C6DA541E94BC415D43E68489B16980F2E887C09E4CF90E2E639B179277BBA0E982CCD1F80521D1457209125B3ABCD309E1B92B5AEDA2EB1CBF933BEAD9CE7365E52B7D17FCB405AA28E5DE6AA3F08E764F745E70859ABCBA41E570A6E4367B3D6FECE723B73ABF3EB53DCDE3816E8A813460447021509D0DFDF2EEEE74CC35485FB55C26836EB3BF9C7DEBEE6C0B77B7BE059233801CF76B321FCA25FB1E63117AE1865E23161EC39D7B1FB84256C2BE72BF8EC771548DB9F00BEF77C509FADA15E2B53FF950D383F96211D3");
        verve_06.Exponent = new byte[]{0x03};
        verve_06.ExpDate = new byte[]{0x28,0x12,0x28};
        verve_06.CheckSum = StringUtil.hexStringToByte("F5BAB84ECE5F8BD45511E5CA861B80C7E6C51F55");

        result =  EmvService.Emv_AddCapk(verve_06);
        Log("Add CAPK capk_D1:" + result + " ID:" + verve_06.KeyID);
        if(result == EmvService.EMV_TRUE){
//            capkdb = new CAPKDB();
//            DataExchange.CAPKtoDB(capk_D1,capkdb);
//            capkdb.bEnable = true;
//            capkdb.CAPKID = capkID++;
//            dbResult = capkdbDao.create(capkdb);
            Log("Create capk_D1 database:" + dbResult);
        }





    }

    public static void Add_All_CAPK(){
        EmvCAPK capk = new EmvCAPK();
        int result;

        //埃及卡
      /*  capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x08, 0x18};//"A000000818",
        capk.KeyID = (byte) 0x01;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("98A65BEC8EBEA0460CF042B06EBD9B9B0F2BB288D913F2CC614E9755E1D29E4C47B04485A772DB9506BDD1CD94811509318A5E518CF0E787627189210C1C0815CD751217B73ABF65C46A936426413D90FFC62005B90C85DA8EB702D884128F9E093D8474E1D3B8376EA506DD78B0AEBD405C88FD907EFF9FED564EDCF2362AF2028346A2F2AA57C2658AE5CFBC7FF7F3");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x24, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("F527081CF371DD7E1FD4FA414A665036E0F5E6E5");//F527081CF371DD7E1FD4FA414A665036E0F5E6E5
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x03, 0x33};
        capk.KeyID = (byte) 0x04;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("BC853E6B5365E89E7EE9317C94B02D0ABB0DBD91C05A224A2554AA29ED9FCB9D86EB9CCBB322A57811F86188AAC7351C72BD9EF196C5A01ACEF7A4EB0D2AD63D9E6AC2E7836547CB1595C68BCBAFD0F6728760F3A7CA7B97301B7E0220184EFC4F653008D93CE098C0D93B45201096D1ADFF4CF1F9FC02AF759DA27CD6DFD6D789B099F16F378B6100334E63F3D35F3251A5EC78693731F5233519CDB380F5AB8C0F02728E91D469ABD0EAE0D93B1CC66CE127B29C7D77441A49D09FCA5D6D9762FC74C31BB506C8BAE3C79AD6C2578775B95956B5370D1D0519E37906B384736233251E8F09AD79DFBE2C6ABFADAC8E4D8624318C27DAF1");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x24, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("F527081CF371DD7E1FD4FA414A665036E0F5E6E5");//F527081CF371DD7E1FD4FA414A665036E0F5E6E5
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));*/
        //==================================================




        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x03;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("B3E5E667506C47CAAFB12A2633819350846697DD65A796E5CE77C57C626A66F70BB630911612AD2832909B8062291BECA46CD33B66A6F9C9D48CED8B4FC8561C8A1D8FB15862C9EB60178DEA2BE1F82236FFCFF4F3843C272179DCDD384D541053DA6A6A0D3CE48FDC2DC4E3E0EEE15F");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("FE70AB3B4D5A1B9924228ADF8027C758483A8B7E");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x05;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("D0135CE8A4436C7F9D5CC66547E30EA402F98105B71722E24BC08DCC80AB7E71EC23B8CE6A1DC6AC2A8CF55543D74A8AE7B388F9B174B7F0D756C22CBB5974F9016A56B601CCA64C71F04B78E86C501B193A5556D5389ECE4DEA258AB97F52A3");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("86DF041E7995023552A79E2623E49180C0CD957A");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x06;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("F934FC032BE59B609A9A649E04446F1B365D1D23A1E6574E490170527EDF32F398326159B39B63D07E95E6276D7FCBB786925182BC0667FBD8F6566B361CA41A38DDF227091B87FA4F47BAC780AC47E15A6A0FB65393EB3473E8D193A07EB579");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("A0DF5DAA385AE3E0E21BFD34D9D8A30506B19B12");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x08;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("D9FD6ED75D51D0E30664BD157023EAA1FFA871E4DA65672B863D255E81E137A51DE4F72BCC9E44ACE12127F87E263D3AF9DD9CF35CA4A7B01E907000BA85D24954C2FCA3074825DDD4C0C8F186CB020F683E02F2DEAD3969133F06F7845166ACEB57CA0FC2603445469811D293BFEFBAFAB57631B3DD91E796BF850A25012F1AE38F05AA5C4D6D03B1DC2E568612785938BBC9B3CD3A910C1DA55A5A9218ACE0F7A21287752682F15832A678D6E1ED0B");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x24, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("20D213126955DE205ADC2FD2822BD22DE21CF9A8");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x09;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("9D912248DE0A4E39C1A7DDE3F6D2588992C1A4095AFBD1824D1BA74847F2BC4926D2EFD904B4B54954CD189A54C5D1179654F8F9B0D2AB5F0357EB642FEDA95D3912C6576945FAB897E7062CAA44A4AA06B8FE6E3DBA18AF6AE3738E30429EE9BE03427C9D64F695FA8CAB4BFE376853EA34AD1D76BFCAD15908C077FFE6DC5521ECEF5D278A96E26F57359FFAEDA19434B937F1AD999DC5C41EB11935B44C18100E857F431A4A5A6BB65114F174C2D7B59FDF237D6BB1DD0916E644D709DED56481477C75D95CDD68254615F7740EC07F330AC5D67BCD75BF23D28A140826C026DBDE971A37CD3EF9B8DF644AC385010501EFC6509D7A41");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("1FF80A40173F52D7D27E0F26A146A1C8CCB29046");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x10;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("9F2701C0909CCBD8C3ED3E071C69F776160022FF3299807ED7A035ED5752770E232D56CC3BE159BD8F0CA8B59435688922F406F55C75639457BBABEFE9A86B2269EF223E34B91AA6DF2CCAD03B4AD4B443D61575CA960845E6C69040101E231D9EF811AD99B0715065A0E661449C41B4B023B7716D1E4AFF1C90704E55AE1225");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("833B1947778036B6D759FCE3F618DDEB2749372C");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x20;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("998D2AD946A60FC597D93807DB54B2B0A550871E43F1779F073AF08D9B04ABD17C8A7DAA3E66EE443F30F92648FC53DA57A78364B062FEDB50F7235B937E16E5F6D9E6BA8F106FB325ECA25125111CE04B43098CDEA8A41426FC6D94F8A47619EDB12789581808692CFBA1F38E8008CC5E02066A1889D52F77B9A121E6597F39");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("7AC3D80EF01E9A998F0A77181E64B36747DC51EB");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x50;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("D11197590057B84196C2F4D11A8F3C05408F422A35D702F90106EA5B019BB28AE607AA9CDEBCD0D81A38D48C7EBB0062D287369EC0C42124246AC30D80CD602AB7238D51084DED4698162C59D25EAC1E66255B4DB2352526EF0982C3B8AD3D1CCE85B01DB5788E75E09F44BE7361366DEF9D1E1317B05E5D0FF5290F88A0DB47");
        capk.Exponent = new byte[]{0x11};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("B769775668CACB5D22A647D1D993141EDAB7237B");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x51;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("BBE43877CC28C0CE1E14BC14E8477317E218364531D155BB8AC5B63C0D6E284DD24259193899F9C04C30BAF167D57929451F67AEBD3BBD0D41444501847D8F02F2C2A2D14817D97AE2625DC163BF8B484C40FFB51749CEDDE9434FB2A0A41099");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("D3D90B35BA8C48731171EAC407D89005ACF6F9DA");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x52;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("B831414E0B4613922BD35B4B36802BC1E1E81C95A27C958F5382003DF646154CA92FC1CE02C3BE047A45E9B02A9089B4B90278237C965192A0FCC86BB49BC82AE6FDC2DE709006B86C7676EFDF597626FAD633A4F7DC48C445D37EB55FCB3B1ABB95BAAA826D5390E15FD14ED403FA2D0CB841C650609524EC555E3BC56CA957");
        capk.Exponent = new byte[]{0x11};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("73A7CA6BA7DB3C37B78E86952BC4EC7754925D54");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x53;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("BCD83721BE52CCCC4B6457321F22A7DC769F54EB8025913BE804D9EABBFA19B3D7C5D3CA658D768CAF57067EEC83C7E6E9F81D0586703ED9DDDADD20675D63424980B10EB364E81EB37DB40ED100344C928886FF4CCC37203EE6106D5B59D1AC102E2CD2D7AC17F4D96C398E5FD993ECB4FFDF79B17547FF9FA2AA8EEFD6CBDA124CBB17A0F8528146387135E226B005A474B9062FF264D2FF8EFA36814AA2950065B1B04C0A1AE9B2F69D4A4AA979D6CE95FEE9485ED0A03AEE9BD953E81CFD1EF6E814DFD3C2CE37AEFA38C1F9877371E91D6A5EB59FDEDF75D3325FA3CA66CDFBA0E57146CC789818FF06BE5FCC50ABD362AE4B80996D");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("A84A53964513A5D9363B4BA13AF5D43B83A83CE7");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================
        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x58;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("99552C4A1ECD68A0260157FC4151B5992837445D3FC57365CA5692C87BE358CDCDF2C92FB6837522842A48EB11CDFFE2FD91770C7221E4AF6207C2DE4004C7DEE1B6276DC62D52A87D2CD01FBF2DC4065DB52824D2A2167A06D19E6A0F781071CDB2DD314CB94441D8DC0E936317B77BF06F5177F6C5ABA3A3BC6AA30209C97260B7A1AD3A192C9B8CD1D153570AFCC87C3CD681D13E997FE33B3963A0A1C79772ACF991033E1B8397AD0341500E48A24770BC4CBE19D2CCF419504FDBF0389BC2F2FDCD4D44E61F");
        capk.Exponent = new byte[]{0x11};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("E6D302EBE7DC6F267E4D00F7D488F0AB6235F105");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x96;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("B74586D19A207BE6627C5B0AAFBC44A2ECF5A2942D3A26CE19C4FFAEEE920521868922E893E7838225A3947A2614796FB2C0628CE8C11E3825A56D3B1BBAEF783A5C6A81F36F8625395126FA983C5216D3166D48ACDE8A431212FF763A7F79D9EDB7FED76B485DE45BEB829A3D4730848A366D3324C3027032FF8D16A1E44D8D");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("7616E9AC8BE014AF88CA11A8FB17967B7394030E");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x97;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("AF0754EAED977043AB6F41D6312AB1E22A6809175BEB28E70D5F99B2DF18CAE73519341BBBD327D0B8BE9D4D0E15F07D36EA3E3A05C892F5B19A3E9D3413B0D97E7AD10A5F5DE8E38860C0AD004B1E06F4040C295ACB457A788551B6127C0B29");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("8001CA76C1203955E2C62841CD6F201087E564BF");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x98;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("CA026E52A695E72BD30AF928196EEDC9FAF4A619F2492E3FB31169789C276FFBB7D43116647BA9E0D106A3542E3965292CF77823DD34CA8EEC7DE367E08070895077C7EFAD939924CB187067DBF92CB1E785917BD38BACE0C194CA12DF0CE5B7A50275AC61BE7C3B436887CA98C9FD39");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("E7AC9AA8EED1B5FF1BD532CF1489A3E5557572C1");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================
        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0x99;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("AB79FCC9520896967E776E64444E5DCDD6E13611874F3985722520425295EEA4BD0C2781DE7F31CD3D041F565F747306EED62954B17EDABA3A6C5B85A1DE1BEB9A34141AF38FCF8279C9DEA0D5A6710D08DB4124F041945587E20359BAB47B7575AD94262D4B25F264AF33DEDCF28E09615E937DE32EDC03C54445FE7E382777");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("4ABFFD6B1C51212D05552E431C5B17007D2F5E6D");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================
        capk = new EmvCAPK();
        capk.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x03};
        capk.KeyID = (byte) 0xF3;
        capk.HashInd = (byte) 0x01;
        capk.ArithInd = (byte) 0x01;
        capk.Modul = StringUtil.hexStringToByte("98F0C770F23864C2E766DF02D1E833DFF4FFE92D696E1642F0A88C5694C6479D16DB1537BFE29E4FDC6E6E8AFD1B0EB7EA0124723C333179BF19E93F10658B2F776E829E87DAEDA9C94A8B3382199A350C077977C97AFF08FD11310AC950A72C3CA5002EF513FCCC286E646E3C5387535D509514B3B326E1234F9CB48C36DDD44B416D23654034A66F403BA511C5EFA3");
        capk.Exponent = new byte[]{0x03};
        capk.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk.CheckSum = StringUtil.hexStringToByte("128EB33128E63E38C9A83A2B1A9349E178F82196");
        result = EmvService.Emv_AddCapk(capk);
        Log("Add CAPK capk_D1:" + result + " ID:" + capk.KeyID + "RID:"+ StringUtil.bytesToHexString(capk.RID));
        //==================================================

        addPaypassCAPK();
        Add_All_CAPK_Test();
        Add_Default_CAPK();

    }

    static void addPaypassCAPK() {
        int result;
        EmvCAPK capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0x00;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = new byte[]{
                (byte) 0xB8, (byte) 0x04, (byte) 0x8A, (byte) 0xBC, (byte) 0x30, (byte) 0xC9, (byte) 0x0D, (byte) 0x97,
                (byte) 0x63, (byte) 0x36, (byte) 0x54, (byte) 0x3E, (byte) 0x3F, (byte) 0xD7, (byte) 0x09, (byte) 0x1C,
                (byte) 0x8F, (byte) 0xE4, (byte) 0x80, (byte) 0x0D, (byte) 0xF8, (byte) 0x20, (byte) 0xED, (byte) 0x55,
                (byte) 0xE7, (byte) 0xE9, (byte) 0x48, (byte) 0x13, (byte) 0xED, (byte) 0x00, (byte) 0x55, (byte) 0x5B,
                (byte) 0x57, (byte) 0x3F, (byte) 0xEC, (byte) 0xA3, (byte) 0xD8, (byte) 0x4A, (byte) 0xF6, (byte) 0x13,
                (byte) 0x1A, (byte) 0x65, (byte) 0x1D, (byte) 0x66, (byte) 0xCF, (byte) 0xF4, (byte) 0x28, (byte) 0x4F,
                (byte) 0xB1, (byte) 0x3B, (byte) 0x63, (byte) 0x5E, (byte) 0xDD, (byte) 0x0E, (byte) 0xE4, (byte) 0x01,
                (byte) 0x76, (byte) 0xD8, (byte) 0xBF, (byte) 0x04, (byte) 0xB7, (byte) 0xFD, (byte) 0x1C, (byte) 0x7B,
                (byte) 0xAC, (byte) 0xF9, (byte) 0xAC, (byte) 0x73, (byte) 0x27, (byte) 0xDF, (byte) 0xAA, (byte) 0x8A,
                (byte) 0xA7, (byte) 0x2D, (byte) 0x10, (byte) 0xDB, (byte) 0x3B, (byte) 0x8E, (byte) 0x70, (byte) 0xB2,
                (byte) 0xDD, (byte) 0xD8, (byte) 0x11, (byte) 0xCB, (byte) 0x41, (byte) 0x96, (byte) 0x52, (byte) 0x5E,
                (byte) 0xA3, (byte) 0x86, (byte) 0xAC, (byte) 0xC3, (byte) 0x3C, (byte) 0x0D, (byte) 0x9D, (byte) 0x45,
                (byte) 0x75, (byte) 0x91, (byte) 0x64, (byte) 0x69, (byte) 0xC4, (byte) 0xE4, (byte) 0xF5, (byte) 0x3E,
                (byte) 0x8E, (byte) 0x1C, (byte) 0x91, (byte) 0x2C, (byte) 0xC6, (byte) 0x18, (byte) 0xCB, (byte) 0x22,
                (byte) 0xDD, (byte) 0xE7, (byte) 0xC3, (byte) 0x56, (byte) 0x8E, (byte) 0x90, (byte) 0x02, (byte) 0x2E,
                (byte) 0x6B, (byte) 0xBA, (byte) 0x77, (byte) 0x02, (byte) 0x02, (byte) 0xE4, (byte) 0x52, (byte) 0x2A,
                (byte) 0x2D, (byte) 0xD6, (byte) 0x23, (byte) 0xD1, (byte) 0x80, (byte) 0xE2, (byte) 0x15, (byte) 0xBD,
                (byte) 0x1D, (byte) 0x15, (byte) 0x07, (byte) 0xFE, (byte) 0x3D, (byte) 0xC9, (byte) 0x0C, (byte) 0xA3,
                (byte) 0x10, (byte) 0xD2, (byte) 0x7B, (byte) 0x3E, (byte) 0xFC, (byte) 0xCD, (byte) 0x8F, (byte) 0x83,
                (byte) 0xDE, (byte) 0x30, (byte) 0x52, (byte) 0xCA, (byte) 0xD1, (byte) 0xE4, (byte) 0x89, (byte) 0x38,
                (byte) 0xC6, (byte) 0x8D, (byte) 0x09, (byte) 0x5A, (byte) 0xAC, (byte) 0x91, (byte) 0xB5, (byte) 0xF3,
                (byte) 0x7E, (byte) 0x28, (byte) 0xBB, (byte) 0x49, (byte) 0xEC, (byte) 0x7E, (byte) 0xD5, (byte) 0x97
        };
        capk_01.Exponent = new byte[]{0x05};
        capk_01.ExpDate = new byte[]{0x21, 0x12, 0x31};
        capk_01.CheckSum = new byte[]{(byte) 0xEB, (byte) 0xFA, (byte) 0x0D, (byte) 0x5D, (byte) 0x06, (byte) 0xD8, (byte) 0xCE, (byte) 0x70,
                (byte) 0x2D, (byte) 0xA3, (byte) 0xEA, (byte) 0xE8, (byte) 0x90, (byte) 0x70, (byte) 0x1D, (byte) 0x45,
                (byte) 0xE2, (byte) 0x74, (byte) 0xC8, (byte) 0x45
        };
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0x06;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = new byte[]{
                (byte) 0xCB, (byte) 0x26, (byte) 0xFC, (byte) 0x83, (byte) 0x0B, (byte) 0x43, (byte) 0x78, (byte) 0x5B,
                (byte) 0x2B, (byte) 0xCE, (byte) 0x37, (byte) 0xC8, (byte) 0x1E, (byte) 0xD3, (byte) 0x34, (byte) 0x62,
                (byte) 0x2F, (byte) 0x96, (byte) 0x22, (byte) 0xF4, (byte) 0xC8, (byte) 0x9A, (byte) 0xAE, (byte) 0x64,
                (byte) 0x10, (byte) 0x46, (byte) 0xB2, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x88, (byte) 0x3F,
                (byte) 0x30, (byte) 0x7F, (byte) 0xB7, (byte) 0xC9, (byte) 0x74, (byte) 0x16, (byte) 0x2D, (byte) 0xA7,
                (byte) 0x2F, (byte) 0x7A, (byte) 0x4E, (byte) 0xC7, (byte) 0x5D, (byte) 0x9D, (byte) 0x65, (byte) 0x73,
                (byte) 0x36, (byte) 0x86, (byte) 0x5B, (byte) 0x8D, (byte) 0x30, (byte) 0x23, (byte) 0xD3, (byte) 0xD6,
                (byte) 0x45, (byte) 0x66, (byte) 0x76, (byte) 0x25, (byte) 0xC9, (byte) 0xA0, (byte) 0x7A, (byte) 0x6B,
                (byte) 0x7A, (byte) 0x13, (byte) 0x7C, (byte) 0xF0, (byte) 0xC6, (byte) 0x41, (byte) 0x98, (byte) 0xAE,
                (byte) 0x38, (byte) 0xFC, (byte) 0x23, (byte) 0x80, (byte) 0x06, (byte) 0xFB, (byte) 0x26, (byte) 0x03,
                (byte) 0xF4, (byte) 0x1F, (byte) 0x4F, (byte) 0x3B, (byte) 0xB9, (byte) 0xDA, (byte) 0x13, (byte) 0x47,
                (byte) 0x27, (byte) 0x0F, (byte) 0x2F, (byte) 0x5D, (byte) 0x8C, (byte) 0x60, (byte) 0x6E, (byte) 0x42,
                (byte) 0x09, (byte) 0x58, (byte) 0xC5, (byte) 0xF7, (byte) 0xD5, (byte) 0x0A, (byte) 0x71, (byte) 0xDE,
                (byte) 0x30, (byte) 0x14, (byte) 0x2F, (byte) 0x70, (byte) 0xDE, (byte) 0x46, (byte) 0x88, (byte) 0x89,
                (byte) 0xB5, (byte) 0xE3, (byte) 0xA0, (byte) 0x86, (byte) 0x95, (byte) 0xB9, (byte) 0x38, (byte) 0xA5,
                (byte) 0x0F, (byte) 0xC9, (byte) 0x80, (byte) 0x39, (byte) 0x3A, (byte) 0x9C, (byte) 0xBC, (byte) 0xE4,
                (byte) 0x4A, (byte) 0xD2, (byte) 0xD6, (byte) 0x4F, (byte) 0x63, (byte) 0x0B, (byte) 0xB3, (byte) 0x3A,
                (byte) 0xD3, (byte) 0xF5, (byte) 0xF5, (byte) 0xFD, (byte) 0x49, (byte) 0x5D, (byte) 0x31, (byte) 0xF3,
                (byte) 0x78, (byte) 0x18, (byte) 0xC1, (byte) 0xD9, (byte) 0x40, (byte) 0x71, (byte) 0x34, (byte) 0x2E,
                (byte) 0x07, (byte) 0xF1, (byte) 0xBE, (byte) 0xC2, (byte) 0x19, (byte) 0x4F, (byte) 0x60, (byte) 0x35,
                (byte) 0xBA, (byte) 0x5D, (byte) 0xED, (byte) 0x39, (byte) 0x36, (byte) 0x50, (byte) 0x0E, (byte) 0xB8,
                (byte) 0x2D, (byte) 0xFD, (byte) 0xA6, (byte) 0xE8, (byte) 0xAF, (byte) 0xB6, (byte) 0x55, (byte) 0xB1,
                (byte) 0xEF, (byte) 0x3D, (byte) 0x0D, (byte) 0x7E, (byte) 0xBF, (byte) 0x86, (byte) 0xB6, (byte) 0x6D,
                (byte) 0xD9, (byte) 0xF2, (byte) 0x9F, (byte) 0x6B, (byte) 0x1D, (byte) 0x32, (byte) 0x4F, (byte) 0xE8,
                (byte) 0xB2, (byte) 0x6C, (byte) 0xE3, (byte) 0x8A, (byte) 0xB2, (byte) 0x01, (byte) 0x3D, (byte) 0xD1,
                (byte) 0x3F, (byte) 0x61, (byte) 0x1E, (byte) 0x7A, (byte) 0x59, (byte) 0x4D, (byte) 0x67, (byte) 0x5C,
                (byte) 0x44, (byte) 0x32, (byte) 0x35, (byte) 0x0E, (byte) 0xA2, (byte) 0x44, (byte) 0xCC, (byte) 0x34,
                (byte) 0xF3, (byte) 0x87, (byte) 0x3C, (byte) 0xBA, (byte) 0x06, (byte) 0x59, (byte) 0x29, (byte) 0x87,
                (byte) 0xA1, (byte) 0xD7, (byte) 0xE8, (byte) 0x52, (byte) 0xAD, (byte) 0xC2, (byte) 0x2E, (byte) 0xF5,
                (byte) 0xA2, (byte) 0xEE, (byte) 0x28, (byte) 0x13, (byte) 0x20, (byte) 0x31, (byte) 0xE4, (byte) 0x8F,
                (byte) 0x74, (byte) 0x03, (byte) 0x7E, (byte) 0x3B, (byte) 0x34, (byte) 0xAB, (byte) 0x74, (byte) 0x7F

        };
        capk_01.Exponent = new byte[]{0x03};
        capk_01.ExpDate = new byte[]{0x21, 0x12, 0x31};
        capk_01.CheckSum = new byte[]{(byte) 0xF9, (byte) 0x10, (byte) 0xA1, (byte) 0x50, (byte) 0x4D, (byte) 0x5F, (byte) 0xFB, (byte) 0x79,
                (byte) 0x3D, (byte) 0x94, (byte) 0xF3, (byte) 0xB5, (byte) 0x00, (byte) 0x76, (byte) 0x5E, (byte) 0x1A,
                (byte) 0xBC, (byte) 0xAD, (byte) 0x72, (byte) 0xD9
        };
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0x09;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("967B6264436C96AA9305776A5919C70DA796340F9997A6C6EF7BEF1D4DBF9CB4289FB7990ABFF1F3AE692F12844B2452A50AE075FB327976A40E8028F279B1E3CCB623957D696FC1225CA2EC950E2D415E9AA931FF18B13168D661FBD06F0ABB");
        capk_01.Exponent = new byte[]{0x03};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("1D90595C2EF9FC6E71B0C721118333DF8A71FE21");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0x22;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("BBE43877CC28C0CE1E14BC14E8477317E218364531D155BB8AC5B63C0D6E284DD24259193899F9C04C30BAF167D57929451F67AEBD3BBD0D41444501847D8F02F2C2A2D14817D97AE2625DC163BF8B484C40FFB51749CEDDE9434FB2A0A41099");
        capk_01.Exponent = new byte[]{0x03};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("008C39B1D119498268B07843349427AC6E98F807");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0x52;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("B831414E0B4613922BD35B4B36802BC1E1E81C95A27C958F5382003DF646154CA92FC1CE02C3BE047A45E9B02A9089B4B90278237C965192A0FCC86BB49BC82AE6FDC2DE709006B86C7676EFDF597626FAD633A4F7DC48C445D37EB55FCB3B1ABB95BAAA826D5390E15FD14ED403FA2D0CB841C650609524EC555E3BC56CA957");
        capk_01.Exponent = new byte[]{0x11};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("DEB81EDB2626A4BB6AE23B77D19A77539D0E6716");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0xF0;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("7563C51B5276AA6370AB8405522414645832B6BEF2A989C771475B2E8DC654DC8A5BFF9E28E31FF1A370A40DC3FFEB06BC85487D5F1CB61C2441FD71CBCD05D883F8DE413B243AFC9DCA768B061E35B884B5D21B6B016AA36BA12DABCFE49F8E528C893C34C7D4793977E4CC99AB09640D9C7AAB7EC5FF3F40E3D4D18DF7E3A7");
        capk_01.Exponent = new byte[]{0x03};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("AE667445F8DE6F82C38800E5EBABA322F03F58F2");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0xFA;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("9C6BE5ADB10B4BE3DCE2099B4B210672B89656EBA091204F613ECC623BEDC9C6D77B660E8BAEEA7F7CE30F1B153879A4E36459343D1FE47ACDBD41FCD710030C2BA1D9461597982C6E1BDD08554B726F5EFF7913CE59E79E357295C321E26D0B8BE270A9442345C753E2AA2ACFC9D30850602FE6CAC00C6DDF6B8D9D9B4879B2826B042A07F0E5AE526A3D3C4D22C72B9EAA52EED8893866F866387AC05A1399");
        capk_01.Exponent = new byte[]{0x03};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("0ABCADAD2C7558CA9C7081AE55DDDC714F8D45F8");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0xFB;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("A9548DFB398B48123FAF41E6CFA4AE1E2352B518AB4BCEFECDB0B3EDEC090287D88B12259F361C1CC088E5F066494417E8EE8BBF8991E2B32FF16F994697842B3D6CB37A2BB5742A440B6356C62AA33DB3C455E59EDDF7864701D03A5B83EE9E9BD83AB93302AC2DFE63E66120B051CF081F56326A71303D952BB336FF12610D");
        capk_01.Exponent = new byte[]{0x02};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("6C7289632919ABEE6E1163D7E6BF693FD88EBD35");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0xFC;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("B37BFD2A9674AD6221C1A001081C62653DC280B0A9BD052C677C913CE7A0D902E77B12F4D4D79037B1E9B923A8BB3FAC3C612045BB3914F8DF41E9A1B61BFA5B41705A691D09CE6F530FE48B30240D98F4E692FFD6AADB87243BA8597AB237586ECF258F4148751BE5DA5A3BE6CC34BD");
        capk_01.Exponent = new byte[]{0x02};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("7FB377EEBBCF7E3A6D04015D10E1BDCB15E21B80");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0xFD;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("B3572BA49AE4C7B7A0019E5189E142CFCDED9498DDB5F0470567AB0BA713B8DA226424622955B54B937ABFEFAAD97919E377621E22196ABC1419D5ADC123484209EA7CB7029E66A0D54C5B45C8AD615AEDB6AE9E0A2F75310EA8961287241245");
        capk_01.Exponent = new byte[]{0x02};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("23CF0D702E0AEFE518E4FA6B836D3CD45B8AAA71");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);


        //==================================================
        capk_01 = new EmvCAPK();
        capk_01.RID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04};
        capk_01.KeyID = (byte) 0xFF;
        capk_01.HashInd = (byte) 0x01;
        capk_01.ArithInd = (byte) 0x01;
        capk_01.Modul = StringUtil.hexStringToByte("B855CC64313AF99C453D181642EE7DD21A67D0FF50C61FE213BCDC18AFBCD07722EFDD2594EFDC227DA3DA23ADCC90E3FA907453ACC954C47323BEDCF8D4862C457D25F47B16D7C3502BE081913E5B0482D838484065DA5F6659E00A9E5D570ADA1EC6AF8C57960075119581FC81468D");
        capk_01.Exponent = new byte[]{0x03};
        capk_01.ExpDate = new byte[]{0x25, 0x12, 0x31};
        capk_01.CheckSum = StringUtil.hexStringToByte("B4E769CECF7AAC4783F305E0B110602A07A6355B");
        result = EmvService.Emv_AddCapk(capk_01);
        Log("add Capk " + StringUtil.bytesToHexString_upcase(capk_01.RID) + "(" + Integer.toHexString(capk_01.KeyID) + ")" + " : " + result);

    }

    public static void Add_All_APP(){

        Add_Default_APP();
        String name = "";
        int result = 0;
        boolean dbResult = false;

        //progressContext = "Adding APP_Visa";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        //埃及测试
/*        EmvApp APP_aiji1 = new EmvApp();
        name = "aiji";
        try {
            APP_aiji1.AppName = name.getBytes("ascii");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //A000000818100101

      *//*  Terminal capabilities:

        "0000000000",                                                 *//**//* "TAC Default"      *//**//*

                "0000000000",                                                 *//**//* "TAC Denial"       *//**//*

                "0000000000",                                                *//**//* "TAC Online"       *//**//*

                "9F1A0295059A039C01",                             *//**//* "Default TDOL"     *//**//*

                "9f3704",                                           *//**//* "Default DDOL"     *//*

                APP_aiji1.AID = new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x08,(byte)0x18,(byte)0x10,(byte)0x01,(byte)0x01};
        APP_aiji1.SelFlag = (byte)0x00;
        APP_aiji1.Priority = (byte)0x00;
        APP_aiji1.TargetPer = (byte)20;
        APP_aiji1.MaxTargetPer = (byte)50;
        APP_aiji1.FloorLimitCheck = (byte)1;
        APP_aiji1.RandTransSel = (byte)1;
        APP_aiji1.VelocityCheck = (byte)1;
        APP_aiji1.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_aiji1.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji1.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji1.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji1.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji1.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_aiji1.DDOL = new byte[]{(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_aiji1.TDOL = new byte[]{(byte)0x9F,(byte)0x1A,(byte)0x02,(byte)0x95,(byte)0x05,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01};
        APP_aiji1.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_aiji1);
        Log("ADD APP_aiji1:" + result);
        if(result == EmvService.EMV_TRUE){

            Log("creat APP_aiji1 database1 :" + dbResult);
        }*/

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

/*        //埃及测试
        EmvApp APP_aiji2 = new EmvApp();
        name = "aiji2";
        try {
            APP_aiji1.AppName = name.getBytes("ascii");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //A000000818100101

      *//*  Terminal capabilities:

        "0000000000",                                                 *//**//* "TAC Default"      *//**//*

                "0000000000",                                                 *//**//* "TAC Denial"       *//**//*

                "0000000000",                                                *//**//* "TAC Online"       *//**//*

                "9F1A0295059A039C01",                             *//**//* "Default TDOL"     *//**//*

                "9f3704",                                           *//**//* "Default DDOL"     *//*

        APP_aiji2.AID = new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x08,(byte)0x18,(byte)0x20,(byte)0x01,(byte)0x01};
        APP_aiji2.SelFlag = (byte)0x00;
        APP_aiji2.Priority = (byte)0x00;
        APP_aiji2.TargetPer = (byte)20;
        APP_aiji2.MaxTargetPer = (byte)50;
        APP_aiji2.FloorLimitCheck = (byte)1;
        APP_aiji2.RandTransSel = (byte)1;
        APP_aiji2.VelocityCheck = (byte)1;
        APP_aiji2.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_aiji2.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji2.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji2.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji2.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_aiji2.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_aiji2.DDOL = new byte[]{(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_aiji2.TDOL = new byte[]{(byte)0x9F,(byte)0x1A,(byte)0x02,(byte)0x95,(byte)0x05,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01};
        APP_aiji2.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_aiji2);
        Log("ADD APP_aiji2:" + result);
        if(result == EmvService.EMV_TRUE){

            Log("creat APP_aiji2 database2 :" + dbResult);
        }*/



        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/



        EmvApp APP_Visa = new EmvApp();
        name = "Visa";
        APP_Visa.AppName = name.getBytes(charset);
        APP_Visa.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10};
        APP_Visa.SelFlag = (byte)0x00;
        APP_Visa.Priority = (byte)0x00;
        APP_Visa.TargetPer = (byte)20;
        APP_Visa.MaxTargetPer = (byte)50;
        APP_Visa.FloorLimitCheck = (byte)1;
        APP_Visa.RandTransSel = (byte)1;
        APP_Visa.VelocityCheck = (byte)1;
        APP_Visa.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Visa.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00};
        APP_Visa.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_Visa.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Visa.TDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x02,(byte)0x06};
        APP_Visa.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_Visa);
        Log("ADD APP_Visa:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Visa,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Visa database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/


        EmvApp APP_Verve = new EmvApp();
        name = "Verve";
        APP_Verve.AppName = name.getBytes(charset);
        APP_Verve.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x71, (byte) 0x00, (byte) 0x01}; //A0000003710001
        APP_Verve.SelFlag = (byte)0x00;
        APP_Verve.Priority = (byte)0x00;
        APP_Verve.TargetPer = (byte)20;
        APP_Verve.MaxTargetPer = (byte)50;
        APP_Verve.FloorLimitCheck = (byte)1;
        APP_Verve.RandTransSel = (byte)1;
        APP_Verve.VelocityCheck = (byte)1;
        APP_Verve.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Verve.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00};
        APP_Verve.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Verve.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Verve.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Verve.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_Verve.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Verve.TDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x02,(byte)0x06};
        APP_Verve.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_Verve);
        Log("ADD APP_Verve:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Visa,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Visa database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_Non_EMV";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_Non_EMV = new EmvApp();
        name = "Non_EMV";
        APP_Non_EMV.AppName = name.getBytes(charset);
        APP_Non_EMV.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x99, (byte) 0x90, (byte) 0x90};
        APP_Non_EMV.SelFlag = (byte)0x00;
        APP_Non_EMV.Priority = (byte)0x00;
        APP_Non_EMV.TargetPer = (byte)0;
        APP_Non_EMV.MaxTargetPer = (byte)0;
        APP_Non_EMV.FloorLimitCheck = (byte)1;
        APP_Non_EMV.RandTransSel = (byte)1;
        APP_Non_EMV.VelocityCheck = (byte)1;
        APP_Non_EMV.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_Non_EMV.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Non_EMV.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Non_EMV.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Non_EMV.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Non_EMV.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_Non_EMV.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Non_EMV.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Non_EMV.Version = new byte[]{(byte)0x00,(byte)0x09};

        result = EmvService.Emv_AddApp(APP_Non_EMV);
        Log("ADD APP_Non_EMV:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Non_EMV,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Non_EMV database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_Test";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_Test = new EmvApp();
        name = "Test";
        APP_Test.AppName = name.getBytes(charset);
        APP_Test.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x99, (byte) 0x99, (byte) 0x01};
        APP_Test.SelFlag = (byte)0x00;
        APP_Test.Priority = (byte)0x00;
        APP_Test.TargetPer = (byte)0;
        APP_Test.MaxTargetPer = (byte)0;
        APP_Test.FloorLimitCheck = (byte)1;
        APP_Test.RandTransSel = (byte)1;
        APP_Test.VelocityCheck = (byte)1;
        APP_Test.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_Test.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Test.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Test.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Test.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Test.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_Test.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Test.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Test.Version = new byte[]{(byte)0x99,(byte)0x99};

        result = EmvService.Emv_AddApp(APP_Test);
        Log("ADD APP_Test:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Test,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Test database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_ANO3";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_ANO3 = new EmvApp();
        name = "ANO3";
        APP_ANO3.AppName = name.getBytes(charset);
        APP_ANO3.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10, (byte) 0x03};
        APP_ANO3.SelFlag = (byte)0x00;
        APP_ANO3.Priority = (byte)0x00;
        APP_ANO3.TargetPer = (byte)0;
        APP_ANO3.MaxTargetPer = (byte)0;
        APP_ANO3.FloorLimitCheck = (byte)1;
        APP_ANO3.RandTransSel = (byte)1;
        APP_ANO3.VelocityCheck = (byte)1;
        APP_ANO3.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_ANO3.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO3.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO3.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO3.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO3.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_ANO3.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO3.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO3.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_ANO3);
        Log("ADD APP_ANO3:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_ANO3,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_ANO3 database :" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_ANO4";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_ANO4 = new EmvApp();
        name = "ANO4";
        APP_ANO4.AppName = name.getBytes(charset);
        APP_ANO4.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10, (byte) 0x04};
        APP_ANO4.SelFlag = (byte)0x00;
        APP_ANO4.Priority = (byte)0x00;
        APP_ANO4.TargetPer = (byte)0;
        APP_ANO4.MaxTargetPer = (byte)0;
        APP_ANO4.FloorLimitCheck = (byte)1;
        APP_ANO4.RandTransSel = (byte)1;
        APP_ANO4.VelocityCheck = (byte)1;
        APP_ANO4.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_ANO4.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO4.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO4.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO4.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO4.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_ANO4.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO4.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO4.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_ANO4);
        Log("ADD APP_ANO4:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_ANO4,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_ANO4 database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_ANO5";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_ANO5 = new EmvApp();
        name = "ANO5";
        APP_ANO5.AppName = name.getBytes(charset);
        APP_ANO5.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10, (byte) 0x05};
        APP_ANO5.SelFlag = (byte)0x00;
        APP_ANO5.Priority = (byte)0x00;
        APP_ANO5.TargetPer = (byte)0;
        APP_ANO5.MaxTargetPer = (byte)0;
        APP_ANO5.FloorLimitCheck = (byte)1;
        APP_ANO5.RandTransSel = (byte)1;
        APP_ANO5.VelocityCheck = (byte)1;
        APP_ANO5.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_ANO5.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO5.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO5.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO5.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO5.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_ANO5.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO5.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO5.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_ANO5);
        Log("ADD APP_ANO5:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_ANO5,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_ANO5 database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_ANO6";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_ANO6 = new EmvApp();
        name = "ANO6";
        APP_ANO6.AppName = name.getBytes(charset);
        APP_ANO6.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10, (byte) 0x06};
        APP_ANO6.SelFlag = (byte)0x00;
        APP_ANO6.Priority = (byte)0x00;
        APP_ANO6.TargetPer = (byte)0;
        APP_ANO6.MaxTargetPer = (byte)0;
        APP_ANO6.FloorLimitCheck = (byte)1;
        APP_ANO6.RandTransSel = (byte)1;
        APP_ANO6.VelocityCheck = (byte)1;
        APP_ANO6.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_ANO6.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO6.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO6.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO6.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO6.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_ANO6.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO6.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO6.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_ANO6);
        Log("ADD APP_ANO6:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_ANO6,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_ANO6 database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_ANO7";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_ANO7 = new EmvApp();
        name = "ANO7";
        APP_ANO7.AppName = name.getBytes(charset);
        APP_ANO7.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10, (byte) 0x07};
        APP_ANO7.SelFlag = (byte)0x01;
        APP_ANO7.Priority = (byte)0x00;
        APP_ANO7.TargetPer = (byte)0;
        APP_ANO7.MaxTargetPer = (byte)0;
        APP_ANO7.FloorLimitCheck = (byte)1;
        APP_ANO7.RandTransSel = (byte)1;
        APP_ANO7.VelocityCheck = (byte)1;
        APP_ANO7.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_ANO7.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO7.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO7.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO7.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANO7.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_ANO7.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO7.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANO7.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_ANO7);
        Log("ADD APP_ANO7:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_ANO7,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_ANO7 database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_MasterCard";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_MasterCard = new EmvApp();
        name = "Master Card";
        APP_MasterCard.AppName = name.getBytes(charset);
        APP_MasterCard.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x10, (byte) 0x10};
        APP_MasterCard.SelFlag = (byte)0x00;
        APP_MasterCard.Priority = (byte)0x00;
        APP_MasterCard.TargetPer = (byte)0;
        APP_MasterCard.MaxTargetPer = (byte)0;
        APP_MasterCard.FloorLimitCheck = (byte)1;
        APP_MasterCard.RandTransSel = (byte)1;
        APP_MasterCard.VelocityCheck = (byte)1;
        APP_MasterCard.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_MasterCard.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_MasterCard.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_MasterCard.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_MasterCard.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_MasterCard.AcquierId = new byte[]{(byte)0x01,(byte)0x22,(byte)0x55,(byte)0x66,(byte)0x33,(byte)0x40};
        APP_MasterCard.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_MasterCard.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_MasterCard.Version = new byte[]{(byte)0x00,(byte)0x02};

        result = EmvService.Emv_AddApp(APP_MasterCard);
        Log("ADD APP_MasterCard:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_MasterCard,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1342";
//            aiddb.MerchantID = "363630303030303030303030303131";//这是Hex值
//            aiddb.MerchantName = "53484F502032";//(SHOP 2)      //这是Hex值
//            aiddb.TerminalID = "4261636B31323334";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "";                     //这是Hex值
//            aiddb.TransReferCurrCode = "";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_MasterCard database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_JCB";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_JCB = new EmvApp();
        name = "JCB";
        APP_JCB.AppName = name.getBytes(charset);
        APP_JCB.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x65, (byte) 0x10, (byte) 0x10};
        APP_JCB.SelFlag = (byte)0x00;
        APP_JCB.Priority = (byte)0x00;
        APP_JCB.TargetPer = (byte)0;
        APP_JCB.MaxTargetPer = (byte)0;
        APP_JCB.FloorLimitCheck = (byte)1;
        APP_JCB.RandTransSel = (byte)1;
        APP_JCB.VelocityCheck = (byte)1;
        APP_JCB.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_JCB.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_JCB.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_JCB.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_JCB.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_JCB.AcquierId = new byte[]{(byte)0x01,(byte)0x22,(byte)0x55,(byte)0x66,(byte)0x33,(byte)0x40};
        APP_JCB.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_JCB.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_JCB.Version = new byte[]{(byte)0x02,(byte)0x00};

        result = EmvService.Emv_AddApp(APP_JCB);
        Log("ADD APP_JCB:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_JCB,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1662";
//            aiddb.MerchantID = "363630303030303030303030303232";//这是Hex值
//            aiddb.MerchantName = "53484F502033";//(SHOP 3)      //这是Hex值
//            aiddb.TerminalID = "4261636B31313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "";                     //这是Hex值
//            aiddb.TransReferCurrCode = "";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_JCB database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_AMEX";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_AMEX = new EmvApp();
        name = "AMEX";
        APP_AMEX.AppName = name.getBytes(charset);
        APP_AMEX.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x25, (byte) 0x01, (byte) 0x05, (byte) 0x01};
        APP_AMEX.SelFlag = (byte)0x00;
        APP_AMEX.Priority = (byte)0x00;
        APP_AMEX.TargetPer = (byte)0;
        APP_AMEX.MaxTargetPer = (byte)0;
        APP_AMEX.FloorLimitCheck = (byte)1;
        APP_AMEX.RandTransSel = (byte)1;
        APP_AMEX.VelocityCheck = (byte)1;
        APP_AMEX.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_AMEX.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_AMEX.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_AMEX.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_AMEX.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_AMEX.AcquierId = new byte[]{(byte)0x01,(byte)0x22,(byte)0x39,(byte)0x66,(byte)0x78,(byte)0x90};
        APP_AMEX.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_AMEX.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_AMEX.Version = new byte[]{(byte)0x00,(byte)0x01};

        result = EmvService.Emv_AddApp(APP_AMEX);
        Log("ADD APP_AMEX:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_AMEX,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1872";
//            aiddb.MerchantID = "363630303030303030303030303636";//这是Hex值
//            aiddb.MerchantName = "53484F502035";//(SHOP 5)      //这是Hex值
//            aiddb.TerminalID = "4261636B31353132";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "";                     //这是Hex值
//            aiddb.TransReferCurrCode = "";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_AMEX database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_Discover";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_Discover = new EmvApp();
        name = "Discover";
        APP_Discover.AppName = name.getBytes(charset);
        APP_Discover.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x52, (byte) 0x30, (byte) 0x10};
        APP_Discover.SelFlag = (byte)0x00;
        APP_Discover.Priority = (byte)0x00;
        APP_Discover.TargetPer = (byte)0;
        APP_Discover.MaxTargetPer = (byte)0;
        APP_Discover.FloorLimitCheck = (byte)1;
        APP_Discover.RandTransSel = (byte)1;
        APP_Discover.VelocityCheck = (byte)1;
        APP_Discover.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Discover.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Discover.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Discover.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Discover.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Discover.AcquierId = new byte[]{(byte)0x01,(byte)0x28,(byte)0x75,(byte)0x66,(byte)0x78,(byte)0x90};
        APP_Discover.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Discover.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Discover.Version = new byte[]{(byte)0x00,(byte)0x01};

        result = EmvService.Emv_AddApp(APP_Discover);
        Log("ADD APP_Discover:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Discover,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1644";
//            aiddb.MerchantID = "303030303030303030303030303838";//这是Hex值
//            aiddb.MerchantName = "53484F502034";//(SHOP 4)      //这是Hex值
//            aiddb.TerminalID = "4261636B31373132";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "";                     //这是Hex值
//            aiddb.TransReferCurrCode = "";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Discover database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_CUP_01";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_CUP_01 = new EmvApp();
        name = "China Union Pay";
        APP_CUP_01.AppName = name.getBytes(charset);
        APP_CUP_01.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01, (byte) 0x01};
        APP_CUP_01.SelFlag = (byte)0x00;
        APP_CUP_01.Priority = (byte)0x00;
        APP_CUP_01.TargetPer = (byte)0;
        APP_CUP_01.MaxTargetPer = (byte)0;
        APP_CUP_01.FloorLimitCheck = (byte)1;
        APP_CUP_01.RandTransSel = (byte)1;
        APP_CUP_01.VelocityCheck = (byte)1;
        APP_CUP_01.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_CUP_01.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_01.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_01.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_01.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_01.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_CUP_01.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_CUP_01.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_CUP_01.Version = new byte[]{(byte)0x00,(byte)0x30};

        result = EmvService.Emv_AddApp(APP_CUP_01);
        Log("ADD APP_CUP_01:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_CUP_01,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_CUP_01 database :" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        //progressContext = "Adding APP_CUP_02";
        //sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_CUP_02 = new EmvApp();
        name = "China Union Pay";
        APP_CUP_02.AppName = name.getBytes(charset);
        APP_CUP_02.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01, (byte) 0x02};
        APP_CUP_02.SelFlag = (byte)0x00;
        APP_CUP_02.Priority = (byte)0x00;
        APP_CUP_02.TargetPer = (byte)0;
        APP_CUP_02.MaxTargetPer = (byte)0;
        APP_CUP_02.FloorLimitCheck = (byte)1;
        APP_CUP_02.RandTransSel = (byte)1;
        APP_CUP_02.VelocityCheck = (byte)1;
        APP_CUP_02.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_CUP_02.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_02.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_02.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_02.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_02.AcquierId = new byte[]{(byte)0x01,(byte)0x26,(byte)0x69,(byte)0x66,(byte)0x78,(byte)0x90};
        APP_CUP_02.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_CUP_02.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_CUP_02.Version = new byte[]{(byte)0x00,(byte)0x30};

        result = EmvService.Emv_AddApp(APP_CUP_02);
        Log("ADD APP_CUP_02:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_CUP_02,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1879";
//            aiddb.MerchantID = "303030303030303030303030303738";//这是Hex值
//            aiddb.MerchantName = "53484F502036";//(SHOP 6)      //这是Hex值
//            aiddb.TerminalID = "4261636B39363132";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "";                     //这是Hex值
//            aiddb.TransReferCurrCode = "";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_CUP_02 database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

//        progressContext = "Adding APP_CUP_03";
//        sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_CUP_03 = new EmvApp();
        name = "China Union Pay";
        APP_CUP_03.AppName = name.getBytes(charset);
        APP_CUP_03.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01, (byte) 0x03};
        APP_CUP_03.SelFlag = (byte)0x00;
        APP_CUP_03.Priority = (byte)0x00;
        APP_CUP_03.TargetPer = (byte)0;
        APP_CUP_03.MaxTargetPer = (byte)0;
        APP_CUP_03.FloorLimitCheck = (byte)1;
        APP_CUP_03.RandTransSel = (byte)1;
        APP_CUP_03.VelocityCheck = (byte)1;
        APP_CUP_03.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_CUP_03.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_03.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_03.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_03.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_CUP_03.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_CUP_03.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_CUP_03.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_CUP_03.Version = new byte[]{(byte)0x00,(byte)0x30};

        result = EmvService.Emv_AddApp(APP_CUP_03);
        Log("ADD APP_CUP_03:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_CUP_03,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_CUP_03 database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

//        progressContext = "Adding APP_ANOD";
//        sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_ANOD = new EmvApp();
        name = "ANOD";
        APP_ANOD.AppName = name.getBytes(charset);
        APP_ANOD.AID = new byte[]{(byte) 0xA1, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55};
        APP_ANOD.SelFlag = (byte)0x00;
        APP_ANOD.Priority = (byte)0x00;
        APP_ANOD.TargetPer = (byte)0;
        APP_ANOD.MaxTargetPer = (byte)0;
        APP_ANOD.FloorLimitCheck = (byte)1;
        APP_ANOD.RandTransSel = (byte)1;
        APP_ANOD.VelocityCheck = (byte)1;
        APP_ANOD.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_ANOD.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOD.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOD.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOD.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOD.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_ANOD.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANOD.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANOD.Version = new byte[]{(byte)0x12,(byte)0x34};

        result = EmvService.Emv_AddApp(APP_ANOD);
        Log("ADD APP_ANOD:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_ANOD,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_ANOD database :" + dbResult);
        }

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

//        progressContext = "Adding APP_ANOE";
//        sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp APP_ANOE = new EmvApp();
        name = "ANOE";
        APP_ANOE.AppName = name.getBytes(charset);
        APP_ANOE.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09};
        APP_ANOE.SelFlag = (byte)0x00;
        APP_ANOE.Priority = (byte)0x00;
        APP_ANOE.TargetPer = (byte)0;
        APP_ANOE.MaxTargetPer = (byte)0;
        APP_ANOE.FloorLimitCheck = (byte)1;
        APP_ANOE.RandTransSel = (byte)1;
        APP_ANOE.VelocityCheck = (byte)1;
        APP_ANOE.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50};//9F1B:FloorLimit
        APP_ANOE.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOE.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOE.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOE.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_ANOE.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        APP_ANOE.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANOE.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_ANOE.Version = new byte[]{(byte)0x12,(byte)0x34};

        result = EmvService.Emv_AddApp(APP_ANOE);
        Log("ADD APP_ANOE:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_ANOE,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_ANOE database :" + dbResult);
        }


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

//        progressContext = "Adding PBOC_TEST_APP";
//        sendMsgInner(HANDLE_UPDATE_PROCESSING);
        EmvApp PBOC_TEST_APP = new EmvApp();
        PBOC_TEST_APP.AID = new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x33,(byte)0x01,(byte)0x01};
        PBOC_TEST_APP.Priority = (byte)0x00;
        PBOC_TEST_APP.TargetPer = (byte)0;
        PBOC_TEST_APP.MaxTargetPer = (byte)0;
        PBOC_TEST_APP.FloorLimitCheck = (byte)1;
        PBOC_TEST_APP.RandTransSel = (byte)1;
        PBOC_TEST_APP.VelocityCheck = (byte)1;
        PBOC_TEST_APP.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x20,(byte)0x00};//9F1B:FloorLimit
        PBOC_TEST_APP.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        PBOC_TEST_APP.TACDenial = new byte[]{(byte)0x00,(byte)0x10,(byte)0x00,(byte)0x00,(byte)0x00};
        PBOC_TEST_APP.TACOnline = new byte[]{(byte)0xD8,(byte)0x40,(byte)0x04,(byte)0xF8,(byte)0x00};
        PBOC_TEST_APP.TACDefault = new byte[]{(byte)0xD8,(byte)0x40,(byte)0x00,(byte)0xA8,(byte)0x00};
        PBOC_TEST_APP.AcquierId = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56};
        PBOC_TEST_APP.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        PBOC_TEST_APP.TDOL = new byte[]{(byte)0x0F,(byte)0x9F,(byte)0x02,(byte)0x06,(byte)0x5F,(byte)0x2A,(byte)0x02,(byte)0x9A,(byte)0x03,(byte)0x9C,(byte)0x01,(byte)0x95,(byte)0x05,(byte)0x9F,(byte)0x37,(byte)0x04};
        PBOC_TEST_APP.Version = new byte[]{(byte)0x00,(byte)0x8C};

        result = EmvService.Emv_AddApp(PBOC_TEST_APP);
        Log("ADD PBOC_TEST_APP:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(PBOC_TEST_APP,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat PBOC_TEST_APP database :" + dbResult);
        }


        addPaypassAid();



        EmvApp APP_Visa1 = new EmvApp();
        name = "Visa1";
        APP_Visa.AppName = name.getBytes(charset);
        APP_Visa1.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x10, (byte) 0x10};
        APP_Visa1.SelFlag = (byte)0x00;
        APP_Visa1.Priority = (byte)0x00;
        APP_Visa1.TargetPer = (byte)20;
        APP_Visa1.MaxTargetPer = (byte)50;
        APP_Visa1.FloorLimitCheck = (byte)1;
        APP_Visa1.RandTransSel = (byte)1;
        APP_Visa1.VelocityCheck = (byte)1;
        APP_Visa1.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Visa1.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00};
        APP_Visa1.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa1.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa1.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa1.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_Visa1.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Visa1.TDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x02,(byte)0x06};
        APP_Visa1.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_Visa1);
        Log("ADD APP_Visa:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Visa,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Visa database :" + dbResult);
        }

        EmvApp APP_Visa2 = new EmvApp();
        name = "Visa2";
        APP_Visa.AppName = name.getBytes(charset);
        APP_Visa2.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20, (byte) 0x10};
        APP_Visa2.SelFlag = (byte)0x00;
        APP_Visa2.Priority = (byte)0x00;
        APP_Visa2.TargetPer = (byte)20;
        APP_Visa2.MaxTargetPer = (byte)50;
        APP_Visa2.FloorLimitCheck = (byte)1;
        APP_Visa2.RandTransSel = (byte)1;
        APP_Visa2.VelocityCheck = (byte)1;
        APP_Visa2.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Visa2.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00};
        APP_Visa2.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa2.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa2.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa2.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_Visa2.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Visa2.TDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x02,(byte)0x06};
        APP_Visa2.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_Visa2);
        Log("ADD APP_Visa:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Visa,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Visa database :" + dbResult);
        }

        EmvApp APP_Visa3 = new EmvApp();
        name = "Visa3";
        APP_Visa.AppName = name.getBytes(charset);
        APP_Visa3.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20, (byte) 0x20};
        APP_Visa3.SelFlag = (byte)0x00;
        APP_Visa3.Priority = (byte)0x00;
        APP_Visa3.TargetPer = (byte)20;
        APP_Visa3.MaxTargetPer = (byte)50;
        APP_Visa3.FloorLimitCheck = (byte)1;
        APP_Visa3.RandTransSel = (byte)1;
        APP_Visa3.VelocityCheck = (byte)1;
        APP_Visa3.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Visa3.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00};
        APP_Visa3.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa3.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa3.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa3.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_Visa3.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Visa3.TDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x02,(byte)0x06};
        APP_Visa3.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_Visa3);
        Log("ADD APP_Visa:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Visa,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Visa database :" + dbResult);
        }

        EmvApp APP_Visa4 = new EmvApp();
        name = "Visa4";
        APP_Visa.AppName = name.getBytes(charset);
        APP_Visa4.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x80, (byte) 0x10};
        APP_Visa4.SelFlag = (byte)0x00;
        APP_Visa4.Priority = (byte)0x00;
        APP_Visa4.TargetPer = (byte)20;
        APP_Visa4.MaxTargetPer = (byte)50;
        APP_Visa4.FloorLimitCheck = (byte)1;
        APP_Visa4.RandTransSel = (byte)1;
        APP_Visa4.VelocityCheck = (byte)1;
        APP_Visa4.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Visa4.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00};
        APP_Visa4.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa4.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa4.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa4.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_Visa4.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Visa4.TDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x02,(byte)0x06};
        APP_Visa4.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_Visa4);
        Log("ADD APP_Visa:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Visa,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Visa database :" + dbResult);
        }

        /*EmvApp APP_Visa5 = new EmvApp();
        name = "Visa5";
        try {
            APP_Visa.AppName = name.getBytes("ascii");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        APP_Visa5.AID = new byte[]{(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x05,(byte)0x00,(byte)0x01};
        APP_Visa5.SelFlag = (byte)0x00;
        APP_Visa5.Priority = (byte)0x00;
        APP_Visa5.TargetPer = (byte)20;
        APP_Visa5.MaxTargetPer = (byte)50;
        APP_Visa5.FloorLimitCheck = (byte)1;
        APP_Visa5.RandTransSel = (byte)1;
        APP_Visa5.VelocityCheck = (byte)1;
        APP_Visa5.FloorLimit = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x50,(byte)0x00};//9F1B:FloorLimit
        APP_Visa5.Threshold = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00};
        APP_Visa5.TACDenial = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa5.TACOnline = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa5.TACDefault = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
        APP_Visa5.AcquierId = new byte[]{(byte)0x01,(byte)0x23,(byte)0x45,(byte)0x67,(byte)0x89,(byte)0x10};
        APP_Visa5.DDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x37,(byte)0x04};
        APP_Visa5.TDOL = new byte[]{(byte)0x03,(byte)0x9F,(byte)0x02,(byte)0x06};
        APP_Visa5.Version = new byte[]{(byte)0x00,(byte)0x96};

        result = EmvService.Emv_AddApp(APP_Visa5);
        Log("ADD APP_Visa:" + result);
        if(result == EmvService.EMV_TRUE){
//            aiddb = new AIDDB();
//            DataExchange.AIDtoDB(APP_Visa,aiddb);
//            aiddb.bEnable = true;
//            aiddb.bTDOL = true;
//            aiddb.MerchantCategoryCode = "1234";
//            aiddb.MerchantID = "303030303030303030303030303030";//这是Hex值
//            aiddb.MerchantName = "53484F502031";//(SHOP 1)      //这是Hex值
//            aiddb.TerminalID = "46726F6E74313233";              //这是Hex值
//            aiddb.TransCurrCode = "0840";                       //这是Hex值
//            aiddb.TransCurrExponent = "02";                     //这是Hex值
//            aiddb.TransReferCurrCode = "0840";                  //这是Hex值
//            aiddb.TransReferCurrExponent = "02";                //这是Hex值
//            dbResult = aiddbDao.create(aiddb);
            Log("creat APP_Visa database :" + dbResult);
        }
*/
    }

    static void addPaypassAid() {
        int result;
        String name;
        EmvApp APP_1 = new EmvApp();
        name = "Mastercard";
        APP_1.AppName = name.getBytes(charset);
        APP_1.AID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04, 0x10, 0x10};
        APP_1.SelFlag = (byte) 0x00;
        APP_1.Priority = (byte) 0x00;
        APP_1.TargetPer = (byte) 20;
        APP_1.MaxTargetPer = (byte) 50;
        APP_1.FloorLimitCheck = (byte) 1;
        APP_1.RandTransSel = (byte) 1;
        APP_1.VelocityCheck = (byte) 1;
        APP_1.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x50, (byte) 0x00};//9F1B:FloorLimit
        APP_1.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00};
        APP_1.TACDenial = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.TACOnline = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.TACDefault = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.AcquierId = new byte[]{(byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0x10};
        APP_1.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_1.TDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x02, (byte) 0x06};
        APP_1.Version = new byte[]{(byte) 0x00, (byte) 0x96};
        APP_1.RiskManData = new byte[]{0x6C, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        result = EmvService.Emv_AddApp(APP_1);


        APP_1 = new EmvApp();
        name = "Maestro";
        APP_1.AppName = name.getBytes(charset);
        APP_1.AID = new byte[]{(byte) 0xA0, 0x00, 0x00, 0x00, 0x04, 0x30, 0x60};
        APP_1.SelFlag = (byte) 0x00;
        APP_1.Priority = (byte) 0x00;
        APP_1.TargetPer = (byte) 0;
        APP_1.MaxTargetPer = (byte) 0;
        APP_1.FloorLimitCheck = (byte) 1;
        APP_1.RandTransSel = (byte) 1;
        APP_1.VelocityCheck = (byte) 1;
        APP_1.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x50, (byte) 0x00};//9F1B:FloorLimit
        APP_1.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00};
        APP_1.TACDenial = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.TACOnline = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.TACDefault = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.AcquierId = new byte[]{(byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0x10};
        APP_1.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_1.TDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x02, (byte) 0x06};
        APP_1.Version = new byte[]{(byte) 0x00, (byte) 0x96};
        APP_1.RiskManData = new byte[]{0x44, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00};
        result = EmvService.Emv_AddApp(APP_1);

        APP_1 = new EmvApp();
        name = "Test1";
        APP_1.AppName = name.getBytes(charset);
        APP_1.AID = new byte[]{(byte) 0xB0, 0x12, 0x34, 0x56, 0x78};
        APP_1.SelFlag = (byte) 0x00;
        APP_1.Priority = (byte) 0x00;
        APP_1.TargetPer = (byte) 0;
        APP_1.MaxTargetPer = (byte) 0;
        APP_1.FloorLimitCheck = (byte) 1;
        APP_1.RandTransSel = (byte) 1;
        APP_1.VelocityCheck = (byte) 1;
        APP_1.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x50, (byte) 0x00};//9F1B:FloorLimit
        APP_1.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00};
        APP_1.TACDenial = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.TACOnline = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.TACDefault = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_1.AcquierId = new byte[]{(byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0x10};
        APP_1.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_1.TDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x02, (byte) 0x06};
        APP_1.Version = new byte[]{(byte) 0x00, (byte) 0x96};
        APP_1.RiskManData = new byte[]{0x6C, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        result = EmvService.Emv_AddApp(APP_1);
    }

    public static void Add_Default_APP() {
        String name = "";
        int result = 0;
        boolean dbResult = false;

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvApp APP_CUP_01 = new EmvApp();
        name = "China Union Pay";
        APP_CUP_01.AppName = name.getBytes(charset);
        APP_CUP_01.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01, (byte) 0x01};
        APP_CUP_01.SelFlag = (byte) 0x00;
        APP_CUP_01.Priority = (byte) 0x00;
        APP_CUP_01.TargetPer = (byte) 0;
        APP_CUP_01.MaxTargetPer = (byte) 0;
        APP_CUP_01.FloorLimitCheck = (byte) 1;
        APP_CUP_01.RandTransSel = (byte) 1;
        APP_CUP_01.VelocityCheck = (byte) 1;
        APP_CUP_01.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x50, (byte) 0x00};//9F1B:FloorLimit
        APP_CUP_01.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_01.TACDenial = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_01.TACOnline = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_01.TACDefault = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_01.AcquierId = new byte[]{(byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0x10};
        APP_CUP_01.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_CUP_01.TDOL = new byte[]{(byte) 0x0F, (byte) 0x9F, (byte) 0x02, (byte) 0x06, (byte) 0x5F, (byte) 0x2A, (byte) 0x02, (byte) 0x9A, (byte) 0x03, (byte) 0x9C, (byte) 0x01, (byte) 0x95, (byte) 0x05, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_CUP_01.Version = new byte[]{(byte) 0x00, (byte) 0x30};

        result = EmvService.Emv_AddApp(APP_CUP_01);
        Log("ADD APP_CUP_01:" + result);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvApp APP_CUP_02 = new EmvApp();
        name = "China Union Pay";
        APP_CUP_02.AppName = name.getBytes(charset);
        APP_CUP_02.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01, (byte) 0x02};
        APP_CUP_02.SelFlag = (byte) 0x00;
        APP_CUP_02.Priority = (byte) 0x00;
        APP_CUP_02.TargetPer = (byte) 0;
        APP_CUP_02.MaxTargetPer = (byte) 0;
        APP_CUP_02.FloorLimitCheck = (byte) 1;
        APP_CUP_02.RandTransSel = (byte) 1;
        APP_CUP_02.VelocityCheck = (byte) 1;
        APP_CUP_02.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x50, (byte) 0x00};//9F1B:FloorLimit
        APP_CUP_02.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_02.TACDenial = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_02.TACOnline = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_02.TACDefault = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_02.AcquierId = new byte[]{(byte) 0x01, (byte) 0x26, (byte) 0x69, (byte) 0x66, (byte) 0x78, (byte) 0x90};
        APP_CUP_02.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_CUP_02.TDOL = new byte[]{(byte) 0x0F, (byte) 0x9F, (byte) 0x02, (byte) 0x06, (byte) 0x5F, (byte) 0x2A, (byte) 0x02, (byte) 0x9A, (byte) 0x03, (byte) 0x9C, (byte) 0x01, (byte) 0x95, (byte) 0x05, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_CUP_02.Version = new byte[]{(byte) 0x00, (byte) 0x30};

        result = EmvService.Emv_AddApp(APP_CUP_02);
        Log("ADD APP_CUP_02:" + result);
        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvApp APP_CUP_03 = new EmvApp();
        name = "China Union Pay";
        APP_CUP_03.AppName = name.getBytes(charset);
        APP_CUP_03.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01, (byte) 0x03};
        APP_CUP_03.SelFlag = (byte) 0x00;
        APP_CUP_03.Priority = (byte) 0x00;
        APP_CUP_03.TargetPer = (byte) 0;
        APP_CUP_03.MaxTargetPer = (byte) 0;
        APP_CUP_03.FloorLimitCheck = (byte) 1;
        APP_CUP_03.RandTransSel = (byte) 1;
        APP_CUP_03.VelocityCheck = (byte) 1;
        APP_CUP_03.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x50};//9F1B:FloorLimit
        APP_CUP_03.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_03.TACDenial = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_03.TACOnline = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_03.TACDefault = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        APP_CUP_03.AcquierId = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x56};
        APP_CUP_03.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_CUP_03.TDOL = new byte[]{(byte) 0x0F, (byte) 0x9F, (byte) 0x02, (byte) 0x06, (byte) 0x5F, (byte) 0x2A, (byte) 0x02, (byte) 0x9A, (byte) 0x03, (byte) 0x9C, (byte) 0x01, (byte) 0x95, (byte) 0x05, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        APP_CUP_03.Version = new byte[]{(byte) 0x00, (byte) 0x30};

        result = EmvService.Emv_AddApp(APP_CUP_03);
        Log("ADD APP_CUP_03:" + result);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/
        EmvApp PBOC_TEST_APP = new EmvApp();
        PBOC_TEST_APP.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01};
        PBOC_TEST_APP.Priority = (byte) 0x00;
        PBOC_TEST_APP.TargetPer = (byte) 0;
        PBOC_TEST_APP.MaxTargetPer = (byte) 0;
        PBOC_TEST_APP.FloorLimitCheck = (byte) 1;
        PBOC_TEST_APP.RandTransSel = (byte) 1;
        PBOC_TEST_APP.VelocityCheck = (byte) 1;
        PBOC_TEST_APP.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x00};//9F1B:FloorLimit
        PBOC_TEST_APP.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACDenial = new byte[]{(byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACOnline = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x04, (byte) 0xF8, (byte) 0x00};
        PBOC_TEST_APP.TACDefault = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x00, (byte) 0xA8, (byte) 0x00};
        PBOC_TEST_APP.AcquierId = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x56};
        PBOC_TEST_APP.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.TDOL = new byte[]{(byte) 0x0F, (byte) 0x9F, (byte) 0x02, (byte) 0x06, (byte) 0x5F, (byte) 0x2A, (byte) 0x02, (byte) 0x9A, (byte) 0x03, (byte) 0x9C, (byte) 0x01, (byte) 0x95, (byte) 0x05, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.Version = new byte[]{(byte) 0x00, (byte) 0x8C};

        result = EmvService.Emv_AddApp(PBOC_TEST_APP);
        Log("ADD PBOC_TEST_APP:" + result);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/
        EmvApp qPBOC_test1 = new EmvApp();
        PBOC_TEST_APP.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01,(byte) 0x01};
        PBOC_TEST_APP.Priority = (byte) 0x00;
        PBOC_TEST_APP.TargetPer = (byte) 0;
        PBOC_TEST_APP.MaxTargetPer = (byte) 0;
        PBOC_TEST_APP.FloorLimitCheck = (byte) 1;
        PBOC_TEST_APP.RandTransSel = (byte) 1;
        PBOC_TEST_APP.VelocityCheck = (byte) 1;
        PBOC_TEST_APP.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x00};//9F1B:FloorLimit
        PBOC_TEST_APP.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACDenial = new byte[]{(byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACOnline = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x04, (byte) 0xF8, (byte) 0x00};
        PBOC_TEST_APP.TACDefault = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x00, (byte) 0xA8, (byte) 0x00};
        PBOC_TEST_APP.AcquierId = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x56};
        PBOC_TEST_APP.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.TDOL = new byte[]{(byte) 0x0F, (byte) 0x9F, (byte) 0x02, (byte) 0x06, (byte) 0x5F, (byte) 0x2A, (byte) 0x02, (byte) 0x9A, (byte) 0x03, (byte) 0x9C, (byte) 0x01, (byte) 0x95, (byte) 0x05, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.Version = new byte[]{(byte) 0x00, (byte) 0x8C};

        result = EmvService.Emv_AddApp(qPBOC_test1);
        Log("ADD PBOC_TEST_APP:" + result);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/
        EmvApp qPBOC_test2 = new EmvApp();
        PBOC_TEST_APP.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01,(byte) 0x02};
        PBOC_TEST_APP.Priority = (byte) 0x00;
        PBOC_TEST_APP.TargetPer = (byte) 0;
        PBOC_TEST_APP.MaxTargetPer = (byte) 0;
        PBOC_TEST_APP.FloorLimitCheck = (byte) 1;
        PBOC_TEST_APP.RandTransSel = (byte) 1;
        PBOC_TEST_APP.VelocityCheck = (byte) 1;
        PBOC_TEST_APP.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x00};//9F1B:FloorLimit
        PBOC_TEST_APP.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACDenial = new byte[]{(byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACOnline = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x04, (byte) 0xF8, (byte) 0x00};
        PBOC_TEST_APP.TACDefault = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x00, (byte) 0xA8, (byte) 0x00};
        PBOC_TEST_APP.AcquierId = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x56};
        PBOC_TEST_APP.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.TDOL = new byte[]{(byte) 0x0F, (byte) 0x9F, (byte) 0x02, (byte) 0x06, (byte) 0x5F, (byte) 0x2A, (byte) 0x02, (byte) 0x9A, (byte) 0x03, (byte) 0x9C, (byte) 0x01, (byte) 0x95, (byte) 0x05, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.Version = new byte[]{(byte) 0x00, (byte) 0x8C};

        result = EmvService.Emv_AddApp(qPBOC_test2);
        Log("ADD PBOC_TEST_APP:" + result);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/
        EmvApp qPBOC_test3 = new EmvApp();
        PBOC_TEST_APP.AID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33, (byte) 0x01, (byte) 0x01,(byte) 0x03};
        PBOC_TEST_APP.Priority = (byte) 0x00;
        PBOC_TEST_APP.TargetPer = (byte) 0;
        PBOC_TEST_APP.MaxTargetPer = (byte) 0;
        PBOC_TEST_APP.FloorLimitCheck = (byte) 1;
        PBOC_TEST_APP.RandTransSel = (byte) 1;
        PBOC_TEST_APP.VelocityCheck = (byte) 1;
        PBOC_TEST_APP.FloorLimit = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x00};//9F1B:FloorLimit
        PBOC_TEST_APP.Threshold = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACDenial = new byte[]{(byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        PBOC_TEST_APP.TACOnline = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x04, (byte) 0xF8, (byte) 0x00};
        PBOC_TEST_APP.TACDefault = new byte[]{(byte) 0xD8, (byte) 0x40, (byte) 0x00, (byte) 0xA8, (byte) 0x00};
        PBOC_TEST_APP.AcquierId = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x56};
        PBOC_TEST_APP.DDOL = new byte[]{(byte) 0x03, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.TDOL = new byte[]{(byte) 0x0F, (byte) 0x9F, (byte) 0x02, (byte) 0x06, (byte) 0x5F, (byte) 0x2A, (byte) 0x02, (byte) 0x9A, (byte) 0x03, (byte) 0x9C, (byte) 0x01, (byte) 0x95, (byte) 0x05, (byte) 0x9F, (byte) 0x37, (byte) 0x04};
        PBOC_TEST_APP.Version = new byte[]{(byte) 0x00, (byte) 0x8C};

        result = EmvService.Emv_AddApp(qPBOC_test3);
        Log("ADD PBOC_TEST_APP:" + result);




    }

    public static void Add_Default_CAPK() {
        int result = 0;
        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_80 = new EmvCAPK();
        capk_pobc_80.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_80.KeyID = (byte) 0x80;
        capk_pobc_80.HashInd = (byte) 0x01;
        capk_pobc_80.ArithInd = (byte) 0x01;
        capk_pobc_80.Modul = new byte[]{
                (byte) 0xCC, (byte) 0xDB, (byte) 0xA6, (byte) 0x86, (byte) 0xE2, (byte) 0xEF, (byte) 0xB8, (byte) 0x4C, (byte)
                0xE2, (byte) 0xEA, (byte) 0x01, (byte) 0x20, (byte) 0x9E, (byte) 0xEB, (byte) 0x53, (byte) 0xBE, (byte)
                0xF2, (byte) 0x1A, (byte) 0xB6, (byte) 0xD3, (byte) 0x53, (byte) 0x27, (byte) 0x4F, (byte) 0xF8, (byte)
                0x39, (byte) 0x1D, (byte) 0x70, (byte) 0x35, (byte) 0xD7, (byte) 0x6E, (byte) 0x21, (byte) 0x56, (byte)
                0xCA, (byte) 0xED, (byte) 0xD0, (byte) 0x75, (byte) 0x10, (byte) 0xE0, (byte) 0x7D, (byte) 0xAF, (byte)
                0xCA, (byte) 0xCA, (byte) 0xBB, (byte) 0x7C, (byte) 0xCB, (byte) 0x09, (byte) 0x50, (byte) 0xBA, (byte)
                0x2F, (byte) 0x0A, (byte) 0x3C, (byte) 0xEC, (byte) 0x31, (byte) 0x3C, (byte) 0x52, (byte) 0xEE, (byte)
                0x6C, (byte) 0xD0, (byte) 0x9E, (byte) 0xF0, (byte) 0x04, (byte) 0x01, (byte) 0xA3, (byte) 0xD6, (byte)
                0xCC, (byte) 0x5F, (byte) 0x68, (byte) 0xCA, (byte) 0x5F, (byte) 0xCD, (byte) 0x0A, (byte) 0xC6, (byte)
                0x13, (byte) 0x21, (byte) 0x41, (byte) 0xFA, (byte) 0xFD, (byte) 0x1C, (byte) 0xFA, (byte) 0x36, (byte)
                0xA2, (byte) 0x69, (byte) 0x2D, (byte) 0x02, (byte) 0xDD, (byte) 0xC2, (byte) 0x7E, (byte) 0xDA, (byte)
                0x4C, (byte) 0xD5, (byte) 0xBE, (byte) 0xA6, (byte) 0xFF, (byte) 0x21, (byte) 0x91, (byte) 0x3B, (byte)
                0x51, (byte) 0x3C, (byte) 0xE7, (byte) 0x8B, (byte) 0xF3, (byte) 0x3E, (byte) 0x68, (byte) 0x77, (byte)
                0xAA, (byte) 0x5B, (byte) 0x60, (byte) 0x5B, (byte) 0xC6, (byte) 0x9A, (byte) 0x53, (byte) 0x4F, (byte)
                0x37, (byte) 0x77, (byte) 0xCB, (byte) 0xED, (byte) 0x63, (byte) 0x76, (byte) 0xBA, (byte) 0x64, (byte)
                0x9C, (byte) 0x72, (byte) 0x51, (byte) 0x6A, (byte) 0x7E, (byte) 0x16, (byte) 0xAF, (byte) 0x85
        };
        capk_pobc_80.Exponent = new byte[]{0x01, 0x00, 0x01};
        capk_pobc_80.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_80.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };
        result = EmvService.Emv_AddCapk(capk_pobc_80);
        Log("Add CAPK capk_pobc_80:" + result + " ID:" + capk_pobc_80.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_C0 = new EmvCAPK();
        capk_pobc_C0.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_C0.KeyID = (byte) 0xC0;
        capk_pobc_C0.HashInd = (byte) 0x01;
        capk_pobc_C0.ArithInd = (byte) 0x01;
        capk_pobc_C0.Modul = new byte[]{
                (byte) 0xC7, (byte) 0xCD, (byte) 0xB6, (byte) 0xF2, (byte) 0xA3, (byte) 0xFE, (byte) 0x80, (byte) 0xA8, (byte)
                0x83, (byte) 0x4C, (byte) 0xDD, (byte) 0xDD, (byte) 0x32, (byte) 0x6E, (byte) 0x10, (byte) 0x82, (byte)
                0xAA, (byte) 0x22, (byte) 0x88, (byte) 0xF4, (byte) 0x7C, (byte) 0x46, (byte) 0x4D, (byte) 0x57, (byte)
                0xB3, (byte) 0x47, (byte) 0x18, (byte) 0x19, (byte) 0x34, (byte) 0x31, (byte) 0x71, (byte) 0x1A, (byte)
                0x44, (byte) 0x11, (byte) 0x91, (byte) 0x48, (byte) 0x05, (byte) 0x50, (byte) 0x44, (byte) 0xCF, (byte)
                0xE3, (byte) 0x31, (byte) 0x37, (byte) 0x08, (byte) 0xBE, (byte) 0xD0, (byte) 0xC9, (byte) 0x8E, (byte)
                0x1C, (byte) 0x58, (byte) 0x9B, (byte) 0x0F, (byte) 0x53, (byte) 0xCF, (byte) 0x6D, (byte) 0x7E, (byte)
                0x82, (byte) 0x9F, (byte) 0xCD, (byte) 0x90, (byte) 0x6D, (byte) 0x21, (byte) 0xA9, (byte) 0x0F, (byte)
                0xD4, (byte) 0xCB, (byte) 0x6B, (byte) 0xAF, (byte) 0x13, (byte) 0x11, (byte) 0x0C, (byte) 0x46, (byte)
                0x85, (byte) 0x10, (byte) 0x7C, (byte) 0x27, (byte) 0xE0, (byte) 0x09, (byte) 0x81, (byte) 0xDB, (byte)
                0x29, (byte) 0xDC, (byte) 0x0A, (byte) 0xC1, (byte) 0x86, (byte) 0xE6, (byte) 0xD7, (byte) 0x01, (byte)
                0x57, (byte) 0x7F, (byte) 0x23, (byte) 0x86, (byte) 0x56, (byte) 0x26, (byte) 0x24, (byte) 0x4E, (byte)
                0x1F, (byte) 0x9B, (byte) 0x2C, (byte) 0xD1, (byte) 0xDD, (byte) 0xFC, (byte) 0xB9, (byte) 0xE8, (byte)
                0x99, (byte) 0xB4, (byte) 0x1F, (byte) 0x50, (byte) 0x84, (byte) 0xD8, (byte) 0xCC, (byte) 0xC1, (byte)
                0x78, (byte) 0xA7, (byte) 0xC3, (byte) 0xF4, (byte) 0x54, (byte) 0x6C, (byte) 0xF9, (byte) 0x31, (byte)
                0x87, (byte) 0x10, (byte) 0x6F, (byte) 0xAB, (byte) 0x05, (byte) 0x5A, (byte) 0x7A, (byte) 0xC6, (byte)
                0x7D, (byte) 0xF6, (byte) 0x2E, (byte) 0x77, (byte) 0x8C, (byte) 0xB8, (byte) 0x88, (byte) 0x23, (byte)
                0xBA, (byte) 0x58, (byte) 0xCF, (byte) 0x75, (byte) 0x46, (byte) 0xC2, (byte) 0xB0, (byte) 0x9F
        };
        capk_pobc_C0.Exponent = new byte[]{0x01, 0x00, 0x01};
        capk_pobc_C0.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_C0.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_C0);
        Log("Add CAPK capk_pobc_C0:" + result + " ID:" + capk_pobc_C0.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_C1 = new EmvCAPK();
        capk_pobc_C1.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_C1.KeyID = (byte) 0xC1;
        capk_pobc_C1.HashInd = (byte) 0x01;
        capk_pobc_C1.ArithInd = (byte) 0x01;
        capk_pobc_C1.Modul = new byte[]{
                (byte) 0x92, (byte) 0xF0, (byte) 0x83, (byte) 0xCB, (byte) 0xE4, (byte) 0x6F, (byte) 0x8D, (byte) 0xCC, (byte)
                0x0C, (byte) 0x04, (byte) 0xE4, (byte) 0x98, (byte) 0xBA, (byte) 0x99, (byte) 0x52, (byte) 0xBA, (byte)
                0x9D, (byte) 0x4C, (byte) 0x09, (byte) 0xC8, (byte) 0x0D, (byte) 0xD2, (byte) 0x77, (byte) 0xE5, (byte)
                0x79, (byte) 0xF0, (byte) 0x7E, (byte) 0x45, (byte) 0x77, (byte) 0x28, (byte) 0x46, (byte) 0xFA, (byte)
                0x43, (byte) 0xDD, (byte) 0x3A, (byte) 0xB3, (byte) 0x1C, (byte) 0xC6, (byte) 0xB0, (byte) 0x8D, (byte)
                0xD1, (byte) 0x86, (byte) 0x95, (byte) 0x71, (byte) 0x59, (byte) 0x49, (byte) 0xFB, (byte) 0x10, (byte)
                0x8E, (byte) 0x53, (byte) 0xA0, (byte) 0x71, (byte) 0xD3, (byte) 0x93, (byte) 0xA7, (byte) 0xFD, (byte)
                0xDB, (byte) 0xF9, (byte) 0xC5, (byte) 0xFB, (byte) 0x0B, (byte) 0x05, (byte) 0x07, (byte) 0x13, (byte)
                0x87, (byte) 0x97, (byte) 0x31, (byte) 0x74, (byte) 0x80, (byte) 0xFC, (byte) 0x48, (byte) 0xD6, (byte)
                0x33, (byte) 0xED, (byte) 0x38, (byte) 0xB4, (byte) 0x01, (byte) 0xA4, (byte) 0x51, (byte) 0x44, (byte)
                0x3A, (byte) 0xD7, (byte) 0xF1, (byte) 0x5F, (byte) 0xAC, (byte) 0xDA, (byte) 0x45, (byte) 0xA6, (byte)
                0x2A, (byte) 0xBE, (byte) 0x24, (byte) 0xFF, (byte) 0x63, (byte) 0x43, (byte) 0xAD, (byte) 0xD0, (byte)
                0x90, (byte) 0x9E, (byte) 0xA8, (byte) 0x38, (byte) 0x93, (byte) 0x48, (byte) 0xE5, (byte) 0x4E, (byte)
                0x26, (byte) 0xF8, (byte) 0x42, (byte) 0x88, (byte) 0x0D, (byte) 0x1A, (byte) 0x69, (byte) 0xF9, (byte)
                0x21, (byte) 0x43, (byte) 0x68, (byte) 0xBA, (byte) 0x30, (byte) 0xC1, (byte) 0x8D, (byte) 0xE5, (byte)
                0xC5, (byte) 0xE0, (byte) 0xCB, (byte) 0x92, (byte) 0x53, (byte) 0xB5, (byte) 0xAB, (byte) 0xC5, (byte)
                0x5F, (byte) 0xB6, (byte) 0xEF, (byte) 0x0A, (byte) 0x73, (byte) 0x8D, (byte) 0x92, (byte) 0x74, (byte)
                0x94, (byte) 0xA3, (byte) 0x0B, (byte) 0xBF, (byte) 0x82, (byte) 0xE3, (byte) 0x40, (byte) 0x28, (byte)
                0x53, (byte) 0x63, (byte) 0xB6, (byte) 0xFA, (byte) 0xA1, (byte) 0x56, (byte) 0x73, (byte) 0x82, (byte)
                0x9D, (byte) 0xBB, (byte) 0x21, (byte) 0x0E, (byte) 0x71, (byte) 0x0D, (byte) 0xA5, (byte) 0x8E, (byte)
                0xE9, (byte) 0xE5, (byte) 0x78, (byte) 0xE7, (byte) 0xCE, (byte) 0x55, (byte) 0xDC, (byte) 0x81, (byte)
                0x2A, (byte) 0xB7, (byte) 0xD6, (byte) 0xDC, (byte) 0xCE, (byte) 0x0E, (byte) 0x3B, (byte) 0x1A, (byte)
                0xE1, (byte) 0x79, (byte) 0xD6, (byte) 0x64, (byte) 0xF3, (byte) 0x35, (byte) 0x6E, (byte) 0xB9, (byte)
                0x51, (byte) 0xE3, (byte) 0xC9, (byte) 0x1A, (byte) 0x1C, (byte) 0xBB, (byte) 0xF6, (byte) 0xA7, (byte)
                0xCA, (byte) 0x8D, (byte) 0x0C, (byte) 0x7E, (byte) 0xC9, (byte) 0xC6, (byte) 0xAF, (byte) 0x7A, (byte)
                0x49, (byte) 0x41, (byte) 0xC5, (byte) 0x05, (byte) 0x10, (byte) 0x99, (byte) 0xB9, (byte) 0x78, (byte)
                0x4E, (byte) 0x56, (byte) 0xC9, (byte) 0x16, (byte) 0x20, (byte) 0x67, (byte) 0xB8, (byte) 0xC3, (byte)
                0xB1, (byte) 0x5C, (byte) 0x5F, (byte) 0xA4, (byte) 0x48, (byte) 0x0A, (byte) 0x64, (byte) 0x5C, (byte)
                0xD2, (byte) 0x52, (byte) 0x6A, (byte) 0x69, (byte) 0xC8, (byte) 0x0B, (byte) 0xA8, (byte) 0xEF, (byte)
                0x36, (byte) 0x1B, (byte) 0xE2, (byte) 0xAA, (byte) 0x94, (byte) 0x17, (byte) 0xDE, (byte) 0xFC, (byte)
                0xE3, (byte) 0x5B, (byte) 0x62, (byte) 0xB0, (byte) 0xC9, (byte) 0xCF, (byte) 0x09, (byte) 0x7D
        };
        capk_pobc_C1.Exponent = new byte[]{0x01, 0x00, 0x01};
        capk_pobc_C1.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_C1.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_C1);
        Log("Add CAPK capk_pobc_C1:" + result + " ID:" + capk_pobc_C1.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_61 = new EmvCAPK();
        capk_pobc_61.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_61.KeyID = (byte) 0x61;
        capk_pobc_61.HashInd = (byte) 0x01;
        capk_pobc_61.ArithInd = (byte) 0x01;
        capk_pobc_61.Modul = new byte[]{
                (byte) 0x83, (byte) 0x4D, (byte) 0x2A, (byte) 0x38, (byte) 0x7C, (byte) 0x5A, (byte) 0x5F, (byte) 0x17, (byte)
                0x6E, (byte) 0xF3, (byte) 0xE6, (byte) 0x6C, (byte) 0xAA, (byte) 0xF8, (byte) 0x3F, (byte) 0x19, (byte)
                0x4B, (byte) 0x15, (byte) 0xAA, (byte) 0xD2, (byte) 0x47, (byte) 0x0C, (byte) 0x78, (byte) 0xC7, (byte)
                0x7D, (byte) 0x6E, (byte) 0xB3, (byte) 0x8E, (byte) 0xDA, (byte) 0xE3, (byte) 0xA2, (byte) 0xF9, (byte)
                0xBA, (byte) 0x16, (byte) 0x23, (byte) 0xF6, (byte) 0xA5, (byte) 0x8C, (byte) 0x89, (byte) 0x2C, (byte)
                0xC9, (byte) 0x25, (byte) 0x63, (byte) 0x2D, (byte) 0xFF, (byte) 0x48, (byte) 0xCE, (byte) 0x95, (byte)
                0x4B, (byte) 0x21, (byte) 0xA5, (byte) 0x3E, (byte) 0x1F, (byte) 0x1E, (byte) 0x43, (byte) 0x66, (byte)
                0xBE, (byte) 0x40, (byte) 0x3C, (byte) 0x27, (byte) 0x9B, (byte) 0x90, (byte) 0x02, (byte) 0x7C, (byte)
                0xBC, (byte) 0x72, (byte) 0x60, (byte) 0x5D, (byte) 0xB6, (byte) 0xC7, (byte) 0x90, (byte) 0x49, (byte)
                0xB8, (byte) 0x99, (byte) 0x2C, (byte) 0xB4, (byte) 0x91, (byte) 0x2E, (byte) 0xFA, (byte) 0x27, (byte)
                0x0B, (byte) 0xEC, (byte) 0xAB, (byte) 0x3A, (byte) 0x7C, (byte) 0xEF, (byte) 0xE0, (byte) 0x5B, (byte)
                0xFA, (byte) 0x46, (byte) 0xE4, (byte) 0xC7, (byte) 0xBB, (byte) 0xCF, (byte) 0x7C, (byte) 0x7A, (byte)
                0x17, (byte) 0x3B, (byte) 0xD9, (byte) 0x88, (byte) 0xD9, (byte) 0x89, (byte) 0xB3, (byte) 0x2C, (byte)
                0xB7, (byte) 0x9F, (byte) 0xAC, (byte) 0x8E, (byte) 0x35, (byte) 0xFB, (byte) 0xE1, (byte) 0x86, (byte)
                0x0E, (byte) 0x7E, (byte) 0xA9, (byte) 0xF2, (byte) 0x38, (byte) 0xA9, (byte) 0x2A, (byte) 0x35, (byte)
                0x93, (byte) 0x55, (byte) 0x2D, (byte) 0x03, (byte) 0xD1, (byte) 0xE3, (byte) 0x86, (byte) 0x01
        };
        capk_pobc_61.Exponent = new byte[]{0x03};
        capk_pobc_61.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_61.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_61);
        Log("Add CAPK capk_pobc_61:" + result + " ID:" + capk_pobc_61.KeyID);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_62 = new EmvCAPK();
        capk_pobc_62.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_62.KeyID = (byte) 0x62;
        capk_pobc_62.HashInd = (byte) 0x01;
        capk_pobc_62.ArithInd = (byte) 0x01;
        capk_pobc_62.Modul = new byte[]{
                (byte) 0xB5, (byte) 0xCD, (byte) 0xD1, (byte) 0xE5, (byte) 0x36, (byte) 0x88, (byte) 0x19, (byte) 0xFC, (byte)
                0x3E, (byte) 0xA6, (byte) 0x5B, (byte) 0x80, (byte) 0xC6, (byte) 0x81, (byte) 0x17, (byte) 0xBB, (byte)
                0xC2, (byte) 0x9F, (byte) 0x90, (byte) 0x96, (byte) 0xEB, (byte) 0xD2, (byte) 0x17, (byte) 0x26, (byte)
                0x9B, (byte) 0x58, (byte) 0x3B, (byte) 0x07, (byte) 0x45, (byte) 0xE0, (byte) 0xC1, (byte) 0x64, (byte)
                0x33, (byte) 0xD5, (byte) 0x4B, (byte) 0x8E, (byte) 0xF3, (byte) 0x87, (byte) 0xB1, (byte) 0xE6, (byte)
                0xCD, (byte) 0xDA, (byte) 0xED, (byte) 0x49, (byte) 0x23, (byte) 0xC3, (byte) 0x9E, (byte) 0x37, (byte)
                0x0E, (byte) 0x5C, (byte) 0xAD, (byte) 0xFE, (byte) 0x04, (byte) 0x17, (byte) 0x73, (byte) 0x02, (byte)
                0x3A, (byte) 0x6B, (byte) 0xC0, (byte) 0xA0, (byte) 0x33, (byte) 0xB0, (byte) 0x03, (byte) 0x1B, (byte)
                0x00, (byte) 0x48, (byte) 0xF1, (byte) 0x8A, (byte) 0xC1, (byte) 0x59, (byte) 0x77, (byte) 0x3C, (byte)
                0xB6, (byte) 0x69, (byte) 0x5E, (byte) 0xE9, (byte) 0x9F, (byte) 0x55, (byte) 0x1F, (byte) 0x41, (byte)
                0x48, (byte) 0x83, (byte) 0xFB, (byte) 0x05, (byte) 0xE5, (byte) 0x26, (byte) 0x40, (byte) 0xE8, (byte)
                0x93, (byte) 0xF4, (byte) 0x81, (byte) 0x60, (byte) 0x82, (byte) 0x24, (byte) 0x1D, (byte) 0x7B, (byte)
                0xFA, (byte) 0x36, (byte) 0x40, (byte) 0x96, (byte) 0x00, (byte) 0x03, (byte) 0xAD, (byte) 0x75, (byte)
                0x17, (byte) 0x89, (byte) 0x5C, (byte) 0x50, (byte) 0xE1, (byte) 0x84, (byte) 0xAA, (byte) 0x95, (byte)
                0x63, (byte) 0x67, (byte) 0xB7, (byte) 0xBF, (byte) 0xFC, (byte) 0x6D, (byte) 0x86, (byte) 0x16, (byte)
                0xA7, (byte) 0xB5, (byte) 0x7E, (byte) 0x2D, (byte) 0x44, (byte) 0x7A, (byte) 0xB3, (byte) 0xE1
        };
        capk_pobc_62.Exponent = new byte[]{0x01, 0x00, 0x01};
        capk_pobc_62.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_62.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_62);
        Log("Add CAPK capk_pobc_62:" + result);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_63 = new EmvCAPK();
        capk_pobc_63.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_63.KeyID = (byte) 0x63;
        capk_pobc_63.HashInd = (byte) 0x01;
        capk_pobc_63.ArithInd = (byte) 0x01;
        capk_pobc_63.Modul = new byte[]{
                (byte) 0x86, (byte) 0x7E, (byte) 0xCA, (byte) 0x26, (byte) 0xA5, (byte) 0x74, (byte) 0x72, (byte) 0xDE, (byte)
                0xFB, (byte) 0x6C, (byte) 0xA9, (byte) 0x42, (byte) 0x89, (byte) 0x31, (byte) 0x2B, (byte) 0xA3, (byte)
                0x9C, (byte) 0x63, (byte) 0x05, (byte) 0x25, (byte) 0x18, (byte) 0xDC, (byte) 0x48, (byte) 0x0B, (byte)
                0x6E, (byte) 0xD4, (byte) 0x91, (byte) 0xAC, (byte) 0xC3, (byte) 0x7C, (byte) 0x02, (byte) 0x88, (byte)
                0x46, (byte) 0xF4, (byte) 0xD7, (byte) 0xB7, (byte) 0x9A, (byte) 0xFA, (byte) 0xEE, (byte) 0xFA, (byte)
                0x07, (byte) 0xFB, (byte) 0x01, (byte) 0x1D, (byte) 0xAA, (byte) 0x46, (byte) 0xC0, (byte) 0x60, (byte)
                0x21, (byte) 0xE9, (byte) 0x32, (byte) 0xD5, (byte) 0x01, (byte) 0xBF, (byte) 0x52, (byte) 0xF2, (byte)
                0x83, (byte) 0x4A, (byte) 0xDE, (byte) 0x3A, (byte) 0xC7, (byte) 0x68, (byte) 0x9E, (byte) 0x94, (byte)
                0xB2, (byte) 0x48, (byte) 0xB2, (byte) 0x8F, (byte) 0x3F, (byte) 0xE2, (byte) 0x80, (byte) 0x36, (byte)
                0x69, (byte) 0xDE, (byte) 0xDA, (byte) 0x00, (byte) 0x09, (byte) 0x88, (byte) 0xDA, (byte) 0x12, (byte)
                0x49, (byte) 0xF9, (byte) 0xA8, (byte) 0x91, (byte) 0x55, (byte) 0x8A, (byte) 0x05, (byte) 0xA1, (byte)
                0xE5, (byte) 0xA7, (byte) 0xBD, (byte) 0x2C, (byte) 0x28, (byte) 0x2F, (byte) 0xE1, (byte) 0x8D, (byte)
                0x20, (byte) 0x41, (byte) 0x89, (byte) 0xA9, (byte) 0x99, (byte) 0x4D, (byte) 0x4A, (byte) 0xDD, (byte)
                0x86, (byte) 0xC0, (byte) 0xCE, (byte) 0x50, (byte) 0x95, (byte) 0x2E, (byte) 0xD8, (byte) 0xBC, (byte)
                0xEC, (byte) 0x0C, (byte) 0xE6, (byte) 0x33, (byte) 0x67, (byte) 0x91, (byte) 0x88, (byte) 0x28, (byte)
                0x5E, (byte) 0x51, (byte) 0xE1, (byte) 0xBE, (byte) 0xD8, (byte) 0x40, (byte) 0xFC, (byte) 0xBF, (byte)
                0xC1, (byte) 0x09, (byte) 0x53, (byte) 0x93, (byte) 0x9A, (byte) 0xF4, (byte) 0x9D, (byte) 0xB9, (byte)
                0x00, (byte) 0x48, (byte) 0x91, (byte) 0x2E, (byte) 0x48, (byte) 0xB4, (byte) 0x41, (byte) 0x81
        };
        capk_pobc_63.Exponent = new byte[]{0x03};
        capk_pobc_63.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_63.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_63);
        Log("Add CAPK capk_pobc_63:" + result + " ID:" + capk_pobc_63.KeyID);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_64 = new EmvCAPK();
        capk_pobc_64.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_64.KeyID = (byte) 0x64;
        capk_pobc_64.HashInd = (byte) 0x01;
        capk_pobc_64.ArithInd = (byte) 0x01;
        capk_pobc_64.Modul = new byte[]{
                (byte) 0x91, (byte) 0x12, (byte) 0x3E, (byte) 0xCF, (byte) 0x02, (byte) 0x30, (byte) 0xE3, (byte) 0xCB, (byte)
                0x24, (byte) 0x5C, (byte) 0x88, (byte) 0xDD, (byte) 0xFA, (byte) 0x3E, (byte) 0xE5, (byte) 0x7B, (byte)
                0xC5, (byte) 0x8E, (byte) 0xD0, (byte) 0x0B, (byte) 0x36, (byte) 0x7B, (byte) 0x38, (byte) 0x75, (byte)
                0xFC, (byte) 0xB7, (byte) 0x95, (byte) 0x48, (byte) 0x87, (byte) 0x26, (byte) 0x80, (byte) 0xF6, (byte)
                0x01, (byte) 0xE8, (byte) 0xC8, (byte) 0x39, (byte) 0xAC, (byte) 0x07, (byte) 0x21, (byte) 0xBA, (byte)
                0xB3, (byte) 0xB8, (byte) 0x9E, (byte) 0xD2, (byte) 0x16, (byte) 0x07, (byte) 0x28, (byte) 0x1C, (byte)
                0x89, (byte) 0x19, (byte) 0xBF, (byte) 0x72, (byte) 0x62, (byte) 0x66, (byte) 0xEA, (byte) 0xB8, (byte)
                0x48, (byte) 0x50, (byte) 0x2A, (byte) 0xD8, (byte) 0x74, (byte) 0xB5, (byte) 0x10, (byte) 0x7A, (byte)
                0x4E, (byte) 0x65, (byte) 0x4E, (byte) 0xF6, (byte) 0xD3, (byte) 0x77, (byte) 0x73, (byte) 0x34, (byte)
                0x3F, (byte) 0x46, (byte) 0x14, (byte) 0x35, (byte) 0xC8, (byte) 0x6E, (byte) 0x4A, (byte) 0x8F, (byte)
                0x86, (byte) 0x6F, (byte) 0xB1, (byte) 0x8C, (byte) 0x7C, (byte) 0xBA, (byte) 0x49, (byte) 0x7B, (byte)
                0x42, (byte) 0x62, (byte) 0x90, (byte) 0xC3, (byte) 0x8D, (byte) 0x19, (byte) 0x6E, (byte) 0x2A, (byte)
                0xFF, (byte) 0x33, (byte) 0xC0, (byte) 0x90, (byte) 0x6F, (byte) 0x92, (byte) 0x96, (byte) 0xF2, (byte)
                0x97, (byte) 0xE1, (byte) 0x56, (byte) 0xDC, (byte) 0x60, (byte) 0x2A, (byte) 0x5E, (byte) 0x65, (byte)
                0x3C, (byte) 0xA1, (byte) 0x16, (byte) 0x8F, (byte) 0x11, (byte) 0x09, (byte) 0x26, (byte) 0x11, (byte)
                0x14, (byte) 0xBF, (byte) 0x7B, (byte) 0xE8, (byte) 0x12, (byte) 0x7A, (byte) 0x3E, (byte) 0x80, (byte)
                0x07, (byte) 0x19, (byte) 0x18, (byte) 0x30, (byte) 0x13, (byte) 0x42, (byte) 0x99, (byte) 0x39, (byte)
                0x5C, (byte) 0xE2, (byte) 0xB3, (byte) 0x22, (byte) 0x22, (byte) 0x86, (byte) 0x67, (byte) 0xB7, (byte)
                0x6E, (byte) 0x07, (byte) 0x2E, (byte) 0xB7, (byte) 0xFD, (byte) 0x5D, (byte) 0x0F, (byte) 0xB3, (byte)
                0xA8, (byte) 0x3E, (byte) 0x8A, (byte) 0xD1, (byte) 0xD7, (byte) 0xF6, (byte) 0xFD, (byte) 0x81
        };
        capk_pobc_64.Exponent = new byte[]{0x03};
        capk_pobc_64.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_64.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_64);
        Log("Add CAPK capk_pobc_64:" + result + " ID:" + capk_pobc_64.KeyID);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_65 = new EmvCAPK();
        capk_pobc_65.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_65.KeyID = (byte) 0x65;
        capk_pobc_65.HashInd = (byte) 0x01;
        capk_pobc_65.ArithInd = (byte) 0x01;
        capk_pobc_65.Modul = new byte[]{
                (byte) 0x81, (byte) 0xBA, (byte) 0x1E, (byte) 0x6B, (byte) 0x9F, (byte) 0x67, (byte) 0x1C, (byte) 0xFC, (byte)
                0x84, (byte) 0x8C, (byte) 0xA2, (byte) 0xAC, (byte) 0xD8, (byte) 0xE1, (byte) 0x7A, (byte) 0xF4, (byte)
                0x06, (byte) 0xB4, (byte) 0xD3, (byte) 0x29, (byte) 0xD1, (byte) 0xEC, (byte) 0xA5, (byte) 0xD0, (byte)
                0x1B, (byte) 0xC0, (byte) 0x94, (byte) 0xA8, (byte) 0x7C, (byte) 0x30, (byte) 0xAF, (byte) 0x49, (byte)
                0x86, (byte) 0x79, (byte) 0x44, (byte) 0xC6, (byte) 0x32, (byte) 0xE8, (byte) 0x18, (byte) 0x50, (byte)
                0x74, (byte) 0x65, (byte) 0x5F, (byte) 0xA5, (byte) 0x35, (byte) 0xAD, (byte) 0x8C, (byte) 0xA4, (byte)
                0x2A, (byte) 0x83, (byte) 0xB4, (byte) 0x1A, (byte) 0xAA, (byte) 0xEA, (byte) 0x85, (byte) 0x9F, (byte)
                0x43, (byte) 0x2F, (byte) 0xA0, (byte) 0xB8, (byte) 0x18, (byte) 0xE7, (byte) 0x2D, (byte) 0xC0, (byte)
                0x7E, (byte) 0xD3, (byte) 0xF7, (byte) 0x7F, (byte) 0xB3, (byte) 0x18, (byte) 0xA4, (byte) 0x75, (byte)
                0xA2, (byte) 0x61, (byte) 0xC0, (byte) 0x76, (byte) 0x0A, (byte) 0x15, (byte) 0x6E, (byte) 0x5D, (byte)
                0xDC, (byte) 0x15, (byte) 0x7A, (byte) 0xE8, (byte) 0xB7, (byte) 0x9B, (byte) 0xA7, (byte) 0x2D, (byte)
                0x89, (byte) 0xD6, (byte) 0x9F, (byte) 0xFF, (byte) 0x75, (byte) 0x46, (byte) 0x19, (byte) 0xE9, (byte)
                0x28, (byte) 0xF1, (byte) 0x51, (byte) 0x6A, (byte) 0x2A, (byte) 0x72, (byte) 0xC0, (byte) 0xF8, (byte)
                0x6B, (byte) 0x09, (byte) 0xB8, (byte) 0xEA, (byte) 0x25, (byte) 0xF8, (byte) 0x6D, (byte) 0xC5, (byte)
                0xA4, (byte) 0x8E, (byte) 0xBC, (byte) 0x5A, (byte) 0x16, (byte) 0xF8, (byte) 0x3F, (byte) 0xBA, (byte)
                0x8F, (byte) 0xC4, (byte) 0xE3, (byte) 0xA9, (byte) 0x82, (byte) 0x78, (byte) 0x91, (byte) 0x22, (byte)
                0x49, (byte) 0xF4, (byte) 0xE0, (byte) 0x79, (byte) 0xBC, (byte) 0xBC, (byte) 0x06, (byte) 0xE7, (byte)
                0xBE, (byte) 0xD9, (byte) 0xAE, (byte) 0xD3, (byte) 0x97, (byte) 0x87, (byte) 0x9D, (byte) 0x27, (byte)
                0x9E, (byte) 0xD9, (byte) 0x19, (byte) 0x25, (byte) 0x39, (byte) 0x49, (byte) 0x01, (byte) 0x26, (byte)
                0x09, (byte) 0x49, (byte) 0xBC, (byte) 0xCE, (byte) 0x6F, (byte) 0xA1, (byte) 0x16, (byte) 0x97, (byte)
                0x98, (byte) 0xA2, (byte) 0x71, (byte) 0x5D, (byte) 0xAE, (byte) 0x32, (byte) 0x98, (byte) 0x8B, (byte)
                0xEF, (byte) 0xBE, (byte) 0x96, (byte) 0x21, (byte) 0xAE, (byte) 0x15, (byte) 0xE0, (byte) 0xC1
        };
        capk_pobc_65.Exponent = new byte[]{0x01, 0x00, 0x01};
        capk_pobc_65.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_65.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_65);
        Log("Add CAPK capk_pobc_65:" + result + " ID:" + capk_pobc_65.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_66 = new EmvCAPK();
        capk_pobc_66.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_66.KeyID = (byte) 0x66;
        capk_pobc_66.HashInd = (byte) 0x01;
        capk_pobc_66.ArithInd = (byte) 0x01;
        capk_pobc_66.Modul = new byte[]{
                (byte) 0x7F, (byte) 0x5A, (byte) 0x39, (byte) 0x45, (byte) 0x79, (byte) 0x4D, (byte) 0x6B, (byte) 0x15, (byte)
                0xF5, (byte) 0xF2, (byte) 0x6B, (byte) 0x4A, (byte) 0x21, (byte) 0xA6, (byte) 0x3A, (byte) 0x5E, (byte)
                0xF3, (byte) 0x55, (byte) 0x40, (byte) 0xD8, (byte) 0xC8, (byte) 0xC0, (byte) 0x99, (byte) 0x15, (byte)
                0x1F, (byte) 0x22, (byte) 0x79, (byte) 0x78, (byte) 0x0A, (byte) 0x5C, (byte) 0x18, (byte) 0xA3, (byte)
                0x17, (byte) 0x70, (byte) 0x3C, (byte) 0x98, (byte) 0x63, (byte) 0x2E, (byte) 0x80, (byte) 0x4D, (byte)
                0x25, (byte) 0x57, (byte) 0x6A, (byte) 0x7B, (byte) 0x46, (byte) 0x0C, (byte) 0x05, (byte) 0x06, (byte)
                0x1E, (byte) 0x03, (byte) 0x97, (byte) 0x5E, (byte) 0x50, (byte) 0xFB, (byte) 0xD7, (byte) 0x49, (byte)
                0x5B, (byte) 0x3A, (byte) 0xDC, (byte) 0x8E, (byte) 0x42, (byte) 0x5E, (byte) 0x53, (byte) 0xDF, (byte)
                0x76, (byte) 0xFA, (byte) 0x40, (byte) 0xB0, (byte) 0x35, (byte) 0xE8, (byte) 0x7F, (byte) 0x69, (byte)
                0xAB, (byte) 0xF8, (byte) 0x76, (byte) 0x5A, (byte) 0x52, (byte) 0x52, (byte) 0x3F, (byte) 0x3B, (byte)
                0x1A, (byte) 0x39, (byte) 0xB1, (byte) 0x95, (byte) 0x28, (byte) 0xB0, (byte) 0x02, (byte) 0x23, (byte)
                0x90, (byte) 0x15, (byte) 0xFA, (byte) 0xDB, (byte) 0xA5, (byte) 0x92, (byte) 0x10, (byte) 0x51
        };
        capk_pobc_66.Exponent = new byte[]{0x01, 0x00, 0x01};
        capk_pobc_66.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_66.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_66);
        Log("Add CAPK capk_pobc_66:" + result + " ID:" + capk_pobc_66.KeyID);

        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_05 = new EmvCAPK();
        capk_pobc_05.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_05.KeyID = (byte) 0x05;
        capk_pobc_05.HashInd = (byte) 0x01;
        capk_pobc_05.ArithInd = (byte) 0x01;
        capk_pobc_05.Modul = new byte[]{
                (byte) 0x97, (byte) 0xCF, (byte) 0x8B, (byte) 0xAD, (byte) 0x30, (byte) 0xCA, (byte) 0xE0, (byte) 0xF9, (byte)
                0xA8, (byte) 0x92, (byte) 0x85, (byte) 0x45, (byte) 0x4D, (byte) 0xDD, (byte) 0xE9, (byte) 0x67, (byte)
                0xAA, (byte) 0xFB, (byte) 0xCD, (byte) 0x4B, (byte) 0xC0, (byte) 0xB7, (byte) 0x8F, (byte) 0x29, (byte)
                0xEC, (byte) 0xB1, (byte) 0x00, (byte) 0x52, (byte) 0x86, (byte) 0xF1, (byte) 0x5F, (byte) 0x6D, (byte)
                0x75, (byte) 0x32, (byte) 0xA9, (byte) 0xC4, (byte) 0x76, (byte) 0x60, (byte) 0x7C, (byte) 0x73, (byte)
                0xFF, (byte) 0x74, (byte) 0x24, (byte) 0x31, (byte) 0x6D, (byte) 0xFC, (byte) 0x74, (byte) 0x18, (byte)
                0x94, (byte) 0xAA, (byte) 0x52, (byte) 0xED, (byte) 0xBA, (byte) 0xF9, (byte) 0x09, (byte) 0x71, (byte)
                0x9C, (byte) 0x7B, (byte) 0x53, (byte) 0x44, (byte) 0x83, (byte) 0x43, (byte) 0xB4, (byte) 0x5C, (byte)
                0xF2, (byte) 0xF0, (byte) 0x0A, (byte) 0x8A, (byte) 0xBF, (byte) 0xB7, (byte) 0x8C, (byte) 0xEE, (byte)
                0xBE, (byte) 0x84, (byte) 0x89, (byte) 0x33, (byte) 0xAA, (byte) 0xED, (byte) 0x97, (byte) 0xDB, (byte)
                0xE8, (byte) 0x4F, (byte) 0x07, (byte) 0x30, (byte) 0xF3, (byte) 0x4F, (byte) 0xB1, (byte) 0xAA, (byte)
                0x15, (byte) 0x28, (byte) 0xD3, (byte) 0xD6, (byte) 0xEC, (byte) 0x75, (byte) 0xB7, (byte) 0x32, (byte)
                0x52, (byte) 0xA3, (byte) 0x0D, (byte) 0x0C, (byte) 0x71, (byte) 0x75, (byte) 0x18, (byte) 0xBE, (byte)
                0x36, (byte) 0x45, (byte) 0x8A, (byte) 0xDD, (byte) 0x0F, (byte) 0xBF, (byte) 0x85, (byte) 0x4C, (byte)
                0x65, (byte) 0x49, (byte) 0x7F, (byte) 0x3F, (byte) 0x54, (byte) 0x08, (byte) 0x41, (byte) 0x54, (byte)
                0xB6, (byte) 0x0F, (byte) 0x51, (byte) 0x56, (byte) 0x13, (byte) 0x61, (byte) 0xEE, (byte) 0x8E, (byte)
                0x85, (byte) 0xF7, (byte) 0x42, (byte) 0xA5, (byte) 0x40, (byte) 0x05, (byte) 0x52, (byte) 0x4C, (byte)
                0xB0, (byte) 0x0F, (byte) 0xEB, (byte) 0xC3, (byte) 0x34, (byte) 0x27, (byte) 0x6E, (byte) 0x0E, (byte)
                0x63, (byte) 0xDA, (byte) 0xD8, (byte) 0x6C, (byte) 0x07, (byte) 0x9A, (byte) 0x9A, (byte) 0x3D, (byte)
                0xF5, (byte) 0xDD, (byte) 0x32, (byte) 0xBE, (byte) 0xCA, (byte) 0xDE, (byte) 0x1A, (byte) 0xB2, (byte)
                0xB7, (byte) 0x1F, (byte) 0x5F, (byte) 0x0A, (byte) 0x0E, (byte) 0x95, (byte) 0xA4, (byte) 0x00, (byte)
                0x0D, (byte) 0x01, (byte) 0xF1, (byte) 0x04, (byte) 0x4A, (byte) 0x57, (byte) 0x8A, (byte) 0xAD, (byte)
                0x92, (byte) 0xE9, (byte) 0xFD, (byte) 0xE9, (byte) 0x2E, (byte) 0x3C, (byte) 0x6A, (byte) 0xA3, (byte)
                0xDC, (byte) 0xD4, (byte) 0x91, (byte) 0x3D, (byte) 0xFA, (byte) 0x55, (byte) 0x52, (byte) 0x53, (byte)
                0x7E, (byte) 0x7D, (byte) 0xE7, (byte) 0x5E, (byte) 0x24, (byte) 0x1F, (byte) 0xAE, (byte) 0xD4, (byte)
                0x55, (byte) 0xD7, (byte) 0x6C, (byte) 0xB8, (byte) 0xFC, (byte) 0xAF, (byte) 0xEE, (byte) 0xD3, (byte)
                0xFD, (byte) 0x6D, (byte) 0xAB, (byte) 0x24, (byte) 0xD7, (byte) 0xA9, (byte) 0xC3, (byte) 0x28, (byte)
                0x52, (byte) 0xF8, (byte) 0x66, (byte) 0xC7, (byte) 0x51, (byte) 0xD7, (byte) 0x71, (byte) 0x0F, (byte)
                0x49, (byte) 0x4A, (byte) 0x0D, (byte) 0xF1, (byte) 0x1B, (byte) 0x67, (byte) 0xFA, (byte) 0xEC, (byte)
                0xDD, (byte) 0x87, (byte) 0xA9, (byte) 0xA4, (byte) 0xE2, (byte) 0xCC, (byte) 0x44, (byte) 0xF6, (byte)
                0xF2, (byte) 0x7E, (byte) 0x46, (byte) 0xE3, (byte) 0xC0, (byte) 0xCC, (byte) 0xCD, (byte) 0x0F
        };
        capk_pobc_05.Exponent = new byte[]{0x03};
        capk_pobc_05.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_05.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_05);
        Log("Add CAPK capk_pobc_05:" + result + " ID:" + capk_pobc_05.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_08 = new EmvCAPK();
        capk_pobc_08.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_08.KeyID = (byte) 0x08;
        capk_pobc_08.HashInd = (byte) 0x01;
        capk_pobc_08.ArithInd = (byte) 0x01;
        capk_pobc_08.Modul = new byte[]{
                (byte) 0xB6, (byte) 0x16, (byte) 0x45, (byte) 0xED, (byte) 0xFD, (byte) 0x54, (byte) 0x98, (byte) 0xFB, (byte)
                0x24, (byte) 0x64, (byte) 0x44, (byte) 0x03, (byte) 0x7A, (byte) 0x0F, (byte) 0xA1, (byte) 0x8C, (byte)
                0x0F, (byte) 0x10, (byte) 0x1E, (byte) 0xBD, (byte) 0x8E, (byte) 0xFA, (byte) 0x54, (byte) 0x57, (byte)
                0x3C, (byte) 0xE6, (byte) 0xE6, (byte) 0xA7, (byte) 0xFB, (byte) 0xF6, (byte) 0x3E, (byte) 0xD2, (byte)
                0x1D, (byte) 0x66, (byte) 0x34, (byte) 0x08, (byte) 0x52, (byte) 0xB0, (byte) 0x21, (byte) 0x1C, (byte)
                0xF5, (byte) 0xEE, (byte) 0xF6, (byte) 0xA1, (byte) 0xCD, (byte) 0x98, (byte) 0x9F, (byte) 0x66, (byte)
                0xAF, (byte) 0x21, (byte) 0xA8, (byte) 0xEB, (byte) 0x19, (byte) 0xDB, (byte) 0xD8, (byte) 0xDB, (byte)
                0xC3, (byte) 0x70, (byte) 0x6D, (byte) 0x13, (byte) 0x53, (byte) 0x63, (byte) 0xA0, (byte) 0xD6, (byte)
                0x83, (byte) 0xD0, (byte) 0x46, (byte) 0x30, (byte) 0x4F, (byte) 0x5A, (byte) 0x83, (byte) 0x6B, (byte)
                0xC1, (byte) 0xBC, (byte) 0x63, (byte) 0x28, (byte) 0x21, (byte) 0xAF, (byte) 0xE7, (byte) 0xA2, (byte)
                0xF7, (byte) 0x5D, (byte) 0xA3, (byte) 0xC5, (byte) 0x0A, (byte) 0xC7, (byte) 0x4C, (byte) 0x54, (byte)
                0x5A, (byte) 0x75, (byte) 0x45, (byte) 0x62, (byte) 0x20, (byte) 0x41, (byte) 0x37, (byte) 0x16, (byte)
                0x96, (byte) 0x63, (byte) 0xCF, (byte) 0xCC, (byte) 0x0B, (byte) 0x06, (byte) 0xE6, (byte) 0x7E, (byte)
                0x21, (byte) 0x09, (byte) 0xEB, (byte) 0xA4, (byte) 0x1B, (byte) 0xC6, (byte) 0x7F, (byte) 0xF2, (byte)
                0x0C, (byte) 0xC8, (byte) 0xAC, (byte) 0x80, (byte) 0xD7, (byte) 0xB6, (byte) 0xEE, (byte) 0x1A, (byte)
                0x95, (byte) 0x46, (byte) 0x5B, (byte) 0x3B, (byte) 0x26, (byte) 0x57, (byte) 0x53, (byte) 0x3E, (byte)
                0xA5, (byte) 0x6D, (byte) 0x92, (byte) 0xD5, (byte) 0x39, (byte) 0xE5, (byte) 0x06, (byte) 0x43, (byte)
                0x60, (byte) 0xEA, (byte) 0x48, (byte) 0x50, (byte) 0xFE, (byte) 0xD2, (byte) 0xD1, (byte) 0xBF
        };
        capk_pobc_08.Exponent = new byte[]{0x03};
        capk_pobc_08.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_08.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_08);
        Log("Add CAPK capk_pobc_08:" + result + " ID:" + capk_pobc_08.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_09 = new EmvCAPK();
        capk_pobc_09.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_09.KeyID = (byte) 0x08;
        capk_pobc_09.HashInd = (byte) 0x01;
        capk_pobc_09.ArithInd = (byte) 0x01;
        capk_pobc_09.Modul = new byte[]{
                (byte) 0xEB, (byte) 0x37, (byte) 0x4D, (byte) 0xFC, (byte) 0x5A, (byte) 0x96, (byte) 0xB7, (byte) 0x1D, (byte)
                0x28, (byte) 0x63, (byte) 0x87, (byte) 0x5E, (byte) 0xDA, (byte) 0x2E, (byte) 0xAF, (byte) 0xB9, (byte)
                0x6B, (byte) 0x1B, (byte) 0x43, (byte) 0x9D, (byte) 0x3E, (byte) 0xCE, (byte) 0x0B, (byte) 0x18, (byte)
                0x26, (byte) 0xA2, (byte) 0x67, (byte) 0x2E, (byte) 0xEE, (byte) 0xFA, (byte) 0x79, (byte) 0x90, (byte)
                0x28, (byte) 0x67, (byte) 0x76, (byte) 0xF8, (byte) 0xBD, (byte) 0x98, (byte) 0x9A, (byte) 0x15, (byte)
                0x14, (byte) 0x1A, (byte) 0x75, (byte) 0xC3, (byte) 0x84, (byte) 0xDF, (byte) 0xC1, (byte) 0x4F, (byte)
                0xEF, (byte) 0x92, (byte) 0x43, (byte) 0xAA, (byte) 0xB3, (byte) 0x27, (byte) 0x07, (byte) 0x65, (byte)
                0x9B, (byte) 0xE9, (byte) 0xE4, (byte) 0x79, (byte) 0x7A, (byte) 0x24, (byte) 0x7C, (byte) 0x2F, (byte)
                0x0B, (byte) 0x6D, (byte) 0x99, (byte) 0x37, (byte) 0x2F, (byte) 0x38, (byte) 0x4A, (byte) 0xF6, (byte)
                0x2F, (byte) 0xE2, (byte) 0x3B, (byte) 0xC5, (byte) 0x4B, (byte) 0xCD, (byte) 0xC5, (byte) 0x7A, (byte)
                0x9A, (byte) 0xCD, (byte) 0x1D, (byte) 0x55, (byte) 0x85, (byte) 0xC3, (byte) 0x03, (byte) 0xF2, (byte)
                0x01, (byte) 0xEF, (byte) 0x4E, (byte) 0x8B, (byte) 0x80, (byte) 0x6A, (byte) 0xFB, (byte) 0x80, (byte)
                0x9D, (byte) 0xB1, (byte) 0xA3, (byte) 0xDB, (byte) 0x1C, (byte) 0xD1, (byte) 0x12, (byte) 0xAC, (byte)
                0x88, (byte) 0x4F, (byte) 0x16, (byte) 0x4A, (byte) 0x67, (byte) 0xB9, (byte) 0x9C, (byte) 0x7D, (byte)
                0x6E, (byte) 0x5A, (byte) 0x8A, (byte) 0x6D, (byte) 0xF1, (byte) 0xD3, (byte) 0xCA, (byte) 0xE6, (byte)
                0xD7, (byte) 0xED, (byte) 0x3D, (byte) 0x5B, (byte) 0xE7, (byte) 0x25, (byte) 0xB2, (byte) 0xDE, (byte)
                0x4A, (byte) 0xDE, (byte) 0x23, (byte) 0xFA, (byte) 0x67, (byte) 0x9B, (byte) 0xF4, (byte) 0xEB, (byte)
                0x15, (byte) 0xA9, (byte) 0x3D, (byte) 0x8A, (byte) 0x6E, (byte) 0x29, (byte) 0xC7, (byte) 0xFF, (byte)
                0xA1, (byte) 0xA7, (byte) 0x0D, (byte) 0xE2, (byte) 0xE5, (byte) 0x4F, (byte) 0x59, (byte) 0x3D, (byte)
                0x90, (byte) 0x8A, (byte) 0x3B, (byte) 0xF9, (byte) 0xEB, (byte) 0xBD, (byte) 0x76, (byte) 0x0B, (byte)
                0xBF, (byte) 0xDC, (byte) 0x8D, (byte) 0xB8, (byte) 0xB5, (byte) 0x44, (byte) 0x97, (byte) 0xE6, (byte)
                0xC5, (byte) 0xBE, (byte) 0x0E, (byte) 0x4A, (byte) 0x4D, (byte) 0xAC, (byte) 0x29, (byte) 0xE5
        };
        capk_pobc_09.Exponent = new byte[]{0x03};
        capk_pobc_09.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_09.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_09);
        Log("Add CAPK capk_pobc_09:" + result + " ID:" + capk_pobc_09.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_0B = new EmvCAPK();
        capk_pobc_0B.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_0B.KeyID = (byte) 0x0B;
        capk_pobc_0B.HashInd = (byte) 0x01;
        capk_pobc_0B.ArithInd = (byte) 0x01;
        capk_pobc_0B.Modul = new byte[]{
                (byte) 0xCF, (byte) 0x9F, (byte) 0xDF, (byte) 0x46, (byte) 0xB3, (byte) 0x56, (byte) 0x37, (byte) 0x8E, (byte)
                0x9A, (byte) 0xF3, (byte) 0x11, (byte) 0xB0, (byte) 0xF9, (byte) 0x81, (byte) 0xB2, (byte) 0x1A, (byte)
                0x1F, (byte) 0x22, (byte) 0xF2, (byte) 0x50, (byte) 0xFB, (byte) 0x11, (byte) 0xF5, (byte) 0x5C, (byte)
                0x95, (byte) 0x87, (byte) 0x09, (byte) 0xE3, (byte) 0xC7, (byte) 0x24, (byte) 0x19, (byte) 0x18, (byte)
                0x29, (byte) 0x34, (byte) 0x83, (byte) 0x28, (byte) 0x9E, (byte) 0xAE, (byte) 0x68, (byte) 0x8A, (byte)
                0x09, (byte) 0x4C, (byte) 0x02, (byte) 0xC3, (byte) 0x44, (byte) 0xE2, (byte) 0x99, (byte) 0x9F, (byte)
                0x31, (byte) 0x5A, (byte) 0x72, (byte) 0x84, (byte) 0x1F, (byte) 0x48, (byte) 0x9E, (byte) 0x24, (byte)
                0xB1, (byte) 0xBA, (byte) 0x00, (byte) 0x56, (byte) 0xCF, (byte) 0xAB, (byte) 0x3B, (byte) 0x47, (byte)
                0x9D, (byte) 0x0E, (byte) 0x82, (byte) 0x64, (byte) 0x52, (byte) 0x37, (byte) 0x5D, (byte) 0xCD, (byte)
                0xBB, (byte) 0x67, (byte) 0xE9, (byte) 0x7E, (byte) 0xC2, (byte) 0xAA, (byte) 0x66, (byte) 0xF4, (byte)
                0x60, (byte) 0x1D, (byte) 0x77, (byte) 0x4F, (byte) 0xEA, (byte) 0xEF, (byte) 0x77, (byte) 0x5A, (byte)
                0xCC, (byte) 0xC6, (byte) 0x21, (byte) 0xBF, (byte) 0xEB, (byte) 0x65, (byte) 0xFB, (byte) 0x00, (byte)
                0x53, (byte) 0xFC, (byte) 0x5F, (byte) 0x39, (byte) 0x2A, (byte) 0xA5, (byte) 0xE1, (byte) 0xD4, (byte)
                0xC4, (byte) 0x1A, (byte) 0x4D, (byte) 0xE9, (byte) 0xFF, (byte) 0xDF, (byte) 0xDF, (byte) 0x13, (byte)
                0x27, (byte) 0xC4, (byte) 0xBB, (byte) 0x87, (byte) 0x4F, (byte) 0x1F, (byte) 0x63, (byte) 0xA5, (byte)
                0x99, (byte) 0xEE, (byte) 0x39, (byte) 0x02, (byte) 0xFE, (byte) 0x95, (byte) 0xE7, (byte) 0x29, (byte)
                0xFD, (byte) 0x78, (byte) 0xD4, (byte) 0x23, (byte) 0x4D, (byte) 0xC7, (byte) 0xE6, (byte) 0xCF, (byte)
                0x1A, (byte) 0xBA, (byte) 0xBA, (byte) 0xA3, (byte) 0xF6, (byte) 0xDB, (byte) 0x29, (byte) 0xB7, (byte)
                0xF0, (byte) 0x5D, (byte) 0x1D, (byte) 0x90, (byte) 0x1D, (byte) 0x2E, (byte) 0x76, (byte) 0xA6, (byte)
                0x06, (byte) 0xA8, (byte) 0xCB, (byte) 0xFF, (byte) 0xFF, (byte) 0xEC, (byte) 0xBD, (byte) 0x91, (byte)
                0x8F, (byte) 0xA2, (byte) 0xD2, (byte) 0x78, (byte) 0xBD, (byte) 0xB4, (byte) 0x3B, (byte) 0x04, (byte)
                0x34, (byte) 0xF5, (byte) 0xD4, (byte) 0x51, (byte) 0x34, (byte) 0xBE, (byte) 0x1C, (byte) 0x27, (byte)
                0x81, (byte) 0xD1, (byte) 0x57, (byte) 0xD5, (byte) 0x01, (byte) 0xFF, (byte) 0x43, (byte) 0xE5, (byte)
                0xF1, (byte) 0xC4, (byte) 0x70, (byte) 0x96, (byte) 0x7C, (byte) 0xD5, (byte) 0x7C, (byte) 0xE5, (byte)
                0x3B, (byte) 0x64, (byte) 0xD8, (byte) 0x29, (byte) 0x74, (byte) 0xC8, (byte) 0x27, (byte) 0x59, (byte)
                0x37, (byte) 0xC5, (byte) 0xD8, (byte) 0x50, (byte) 0x2A, (byte) 0x12, (byte) 0x52, (byte) 0xA8, (byte)
                0xA5, (byte) 0xD6, (byte) 0x08, (byte) 0x8A, (byte) 0x25, (byte) 0x9B, (byte) 0x69, (byte) 0x4F, (byte)
                0x98, (byte) 0x64, (byte) 0x8D, (byte) 0x9A, (byte) 0xF2, (byte) 0xCB, (byte) 0x0E, (byte) 0xFD, (byte)
                0x9D, (byte) 0x94, (byte) 0x3C, (byte) 0x69, (byte) 0xF8, (byte) 0x96, (byte) 0xD4, (byte) 0x9F, (byte)
                0xA3, (byte) 0x97, (byte) 0x02, (byte) 0x16, (byte) 0x2A, (byte) 0xCB, (byte) 0x5A, (byte) 0xF2, (byte)
                0x9B, (byte) 0x90, (byte) 0xBA, (byte) 0xDE, (byte) 0x00, (byte) 0x5B, (byte) 0xC1, (byte) 0x57
        };
        capk_pobc_0B.Exponent = new byte[]{0x03};
        capk_pobc_0B.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_0B.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_0B);
        Log("Add CAPK capk_pobc_0B:" + result + " ID:" + capk_pobc_0B.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_84 = new EmvCAPK();
        capk_pobc_84.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_84.KeyID = (byte) 0x84;
        capk_pobc_84.HashInd = (byte) 0x01;
        capk_pobc_84.ArithInd = (byte) 0x01;
        capk_pobc_84.Modul = new byte[]{
                (byte) 0xF9, (byte) 0xEA, (byte) 0x55, (byte) 0x03, (byte) 0xCF, (byte) 0xE4, (byte) 0x30, (byte) 0x38, (byte)
                0x59, (byte) 0x6C, (byte) 0x72, (byte) 0x06, (byte) 0x45, (byte) 0xA9, (byte) 0x4E, (byte) 0x01, (byte)
                0x54, (byte) 0x79, (byte) 0x3D, (byte) 0xE7, (byte) 0x3A, (byte) 0xE5, (byte) 0xA9, (byte) 0x35, (byte)
                0xD1, (byte) 0xFB, (byte) 0x9D, (byte) 0x0F, (byte) 0xE7, (byte) 0x72, (byte) 0x86, (byte) 0xB6, (byte)
                0x12, (byte) 0x61, (byte) 0xE3, (byte) 0xBB, (byte) 0x1D, (byte) 0x3D, (byte) 0xFE, (byte) 0xC5, (byte)
                0x47, (byte) 0x44, (byte) 0x99, (byte) 0x92, (byte) 0xE2, (byte) 0x03, (byte) 0x7C, (byte) 0x01, (byte)
                0xFF, (byte) 0x4E, (byte) 0xFB, (byte) 0x88, (byte) 0xDA, (byte) 0x8A, (byte) 0x82, (byte) 0xF3, (byte)
                0x0F, (byte) 0xEA, (byte) 0x31, (byte) 0x98, (byte) 0xD5, (byte) 0xD1, (byte) 0x67, (byte) 0x54, (byte)
                0x24, (byte) 0x7A, (byte) 0x16, (byte) 0x26, (byte) 0xE9, (byte) 0xCF, (byte) 0xFB, (byte) 0x4C, (byte)
                0xD9, (byte) 0xE3, (byte) 0x13, (byte) 0x99, (byte) 0x99, (byte) 0x0E, (byte) 0x43, (byte) 0xFC, (byte)
                0xA7, (byte) 0x7C, (byte) 0x74, (byte) 0x4A, (byte) 0x93, (byte) 0x68, (byte) 0x5A, (byte) 0x26, (byte)
                0x0A, (byte) 0x20, (byte) 0xE6, (byte) 0xA6, (byte) 0x07, (byte) 0xF3, (byte) 0xEE, (byte) 0x3F, (byte)
                0xAE, (byte) 0x2A, (byte) 0xBB, (byte) 0xE9, (byte) 0x96, (byte) 0x78, (byte) 0xC9, (byte) 0xF1, (byte)
                0x9D, (byte) 0xFD, (byte) 0x2D, (byte) 0x8E, (byte) 0xA7, (byte) 0x67, (byte) 0x89, (byte) 0x23, (byte)
                0x9D, (byte) 0x13, (byte) 0x36, (byte) 0x9D, (byte) 0x7D, (byte) 0x2D, (byte) 0x56, (byte) 0xAF, (byte)
                0x3F, (byte) 0x27, (byte) 0x93, (byte) 0x06, (byte) 0x89, (byte) 0x50, (byte) 0xB5, (byte) 0xBD, (byte)
                0x80, (byte) 0x8C, (byte) 0x46, (byte) 0x25, (byte) 0x71, (byte) 0x66, (byte) 0x2D, (byte) 0x43, (byte)
                0x64, (byte) 0xB3, (byte) 0x0A, (byte) 0x25, (byte) 0x82, (byte) 0x95, (byte) 0x9D, (byte) 0xB2, (byte)
                0x38, (byte) 0x33, (byte) 0x3B, (byte) 0xAD, (byte) 0xAC, (byte) 0xB4, (byte) 0x42, (byte) 0xF9, (byte)
                0x51, (byte) 0x6B, (byte) 0x5C, (byte) 0x33, (byte) 0x6C, (byte) 0x8A, (byte) 0x61, (byte) 0x3F, (byte)
                0xE0, (byte) 0x14, (byte) 0xB7, (byte) 0xD7, (byte) 0x73, (byte) 0x58, (byte) 0x1A, (byte) 0xE1, (byte)
                0x0F, (byte) 0xDF, (byte) 0x7B, (byte) 0xDB, (byte) 0x26, (byte) 0x69, (byte) 0x01, (byte) 0x2D
        };
        capk_pobc_84.Exponent = new byte[]{0x03};
        capk_pobc_84.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_84.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_84);
        Log("Add CAPK capk_pobc_84:" + result + " ID:" + capk_pobc_84.KeyID);


        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/

        EmvCAPK capk_pobc_85 = new EmvCAPK();
        capk_pobc_85.RID = new byte[]{(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x33};
        capk_pobc_85.KeyID = (byte) 0x85;
        capk_pobc_85.HashInd = (byte) 0x01;
        capk_pobc_85.ArithInd = (byte) 0x01;
        capk_pobc_85.Modul = new byte[]{
                (byte) 0xC9, (byte) 0x24, (byte) 0x2E, (byte) 0xC6, (byte) 0x03, (byte) 0x0F, (byte) 0x10, (byte) 0xE5, (byte)
                0x22, (byte) 0x5E, (byte) 0x72, (byte) 0x2A, (byte) 0xA1, (byte) 0x7D, (byte) 0x9D, (byte) 0xC8, (byte)
                0x94, (byte) 0x29, (byte) 0x92, (byte) 0x33, (byte) 0xAE, (byte) 0xC3, (byte) 0x21, (byte) 0x9B, (byte)
                0x95, (byte) 0x0D, (byte) 0x4F, (byte) 0x24, (byte) 0x3A, (byte) 0xF5, (byte) 0x30, (byte) 0xFA, (byte)
                0x13, (byte) 0xE3, (byte) 0xA3, (byte) 0x1A, (byte) 0xFA, (byte) 0xA0, (byte) 0xD4, (byte) 0xBF, (byte)
                0x4D, (byte) 0xE5, (byte) 0x62, (byte) 0xB6, (byte) 0xB4, (byte) 0xC3, (byte) 0x10, (byte) 0x8A, (byte)
                0xEB, (byte) 0xBC, (byte) 0x6C, (byte) 0xB0, (byte) 0x80, (byte) 0xF9, (byte) 0x07, (byte) 0x70, (byte)
                0xD5, (byte) 0x32, (byte) 0xF2, (byte) 0x41, (byte) 0xBC, (byte) 0x15, (byte) 0x36, (byte) 0x40, (byte)
                0x1E, (byte) 0x1B, (byte) 0xF7, (byte) 0x2F, (byte) 0x9D, (byte) 0xC1, (byte) 0xB0, (byte) 0x89, (byte)
                0x33, (byte) 0xB9, (byte) 0xBF, (byte) 0x77, (byte) 0x40, (byte) 0x3F, (byte) 0x6A, (byte) 0x0F, (byte)
                0xB5, (byte) 0x77, (byte) 0x7B, (byte) 0xAA, (byte) 0x4C, (byte) 0x9B, (byte) 0xE9, (byte) 0x15, (byte)
                0x74, (byte) 0xBB, (byte) 0xBF, (byte) 0xB5, (byte) 0x21, (byte) 0x34, (byte) 0x2A, (byte) 0x20, (byte)
                0x38, (byte) 0x67, (byte) 0x90, (byte) 0x51, (byte) 0x22, (byte) 0x21, (byte) 0xF4, (byte) 0x77, (byte)
                0xFB, (byte) 0xC5, (byte) 0x3F, (byte) 0xF1, (byte) 0xB6, (byte) 0x53, (byte) 0x3A, (byte) 0x01, (byte)
                0x58, (byte) 0x15, (byte) 0x43, (byte) 0x54, (byte) 0x10, (byte) 0xEC, (byte) 0x27, (byte) 0x2F, (byte)
                0x0A, (byte) 0x34, (byte) 0xEA, (byte) 0x07, (byte) 0x35, (byte) 0xC4, (byte) 0x39, (byte) 0x67, (byte)
                0x7D, (byte) 0x7E, (byte) 0x46, (byte) 0xFB, (byte) 0xA7, (byte) 0x66, (byte) 0xEC, (byte) 0x00, (byte)
                0xCE, (byte) 0xD5, (byte) 0x9B, (byte) 0x67, (byte) 0x15, (byte) 0xE3, (byte) 0x41, (byte) 0x2D, (byte)
                0x6F, (byte) 0xB8, (byte) 0xA9, (byte) 0x34, (byte) 0xBF, (byte) 0x9D, (byte) 0x14, (byte) 0x97, (byte)
                0xA2, (byte) 0x4A, (byte) 0x62, (byte) 0x52, (byte) 0xC5, (byte) 0x2D, (byte) 0x75, (byte) 0x86, (byte)
                0xFD, (byte) 0x66, (byte) 0xA4, (byte) 0x50, (byte) 0xFB, (byte) 0x5D, (byte) 0x2B, (byte) 0x44, (byte)
                0x84, (byte) 0xEC, (byte) 0x92, (byte) 0x30, (byte) 0x61, (byte) 0x43, (byte) 0x96, (byte) 0x22, (byte)
                0xBC, (byte) 0x05, (byte) 0x35, (byte) 0x31, (byte) 0x6C, (byte) 0xD4, (byte) 0x23, (byte) 0x1C, (byte)
                0x13, (byte) 0xC6, (byte) 0x27, (byte) 0xBF, (byte) 0x4D, (byte) 0x2E, (byte) 0xDE, (byte) 0x1C, (byte)
                0x02, (byte) 0xC8, (byte) 0x02, (byte) 0x46, (byte) 0x46, (byte) 0x58, (byte) 0xF1, (byte) 0xB9, (byte)
                0xD7, (byte) 0xFF, (byte) 0x23, (byte) 0xA3, (byte) 0x69, (byte) 0x85, (byte) 0x10, (byte) 0xFA, (byte)
                0x90, (byte) 0xD0, (byte) 0xC3, (byte) 0x16, (byte) 0x49, (byte) 0x42, (byte) 0xFB, (byte) 0x35, (byte)
                0x92, (byte) 0x55, (byte) 0xCD, (byte) 0x82, (byte) 0x3C, (byte) 0xB2, (byte) 0x63, (byte) 0x5B, (byte)
                0x3F, (byte) 0x16, (byte) 0x7F, (byte) 0xBD, (byte) 0xFC, (byte) 0x90, (byte) 0x06, (byte) 0x41, (byte)
                0xB9, (byte) 0x70, (byte) 0xD6, (byte) 0x02, (byte) 0xA2, (byte) 0x77, (byte) 0x1A, (byte) 0x7F, (byte)
                0x4F, (byte) 0x94, (byte) 0xDF, (byte) 0x6D, (byte) 0x34, (byte) 0xBE, (byte) 0x8B, (byte) 0xBB
        };
        capk_pobc_85.Exponent = new byte[]{0x03};
        capk_pobc_85.ExpDate = new byte[]{0x15, 0x12, 0x31};
        capk_pobc_85.CheckSum = new byte[]{
                (byte) 0xCC, (byte) 0x95, (byte) 0x85, (byte) 0xE8, (byte) 0xE6, (byte) 0x37, (byte) 0x19, (byte) 0x1C, (byte) 0x10, (byte) 0xFC, (byte) 0xEC, (byte) 0xB3, (byte) 0x2B, (byte) 0x5A, (byte) 0xE1, (byte) 0xB9, (byte) 0xD4, (byte) 0x10, (byte) 0xB5, (byte) 0x2D
        };

        result = EmvService.Emv_AddCapk(capk_pobc_85);
        Log("Add CAPK capk_pobc_85:" + result + " ID:" + capk_pobc_85.KeyID);
        /*----------------------------------------------------------------------- division line-----------------------------------------------------------------------------------------*/
    }
}
