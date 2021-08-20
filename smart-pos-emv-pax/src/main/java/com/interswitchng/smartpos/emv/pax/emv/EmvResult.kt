package com.interswitchng.smartpos.emv.pax.emv


import com.pax.jemv.clcommon.RetCode


enum class EmvResult private constructor(val errCode: Int, val errMsg: String) {
    EMV_OK(RetCode.EMV_OK, "success"),
    EMV_ERR_ICC_RESET(RetCode.ICC_RESET_ERR, "iccString reset error"),
    EMV_ERR_ICC_CMD(RetCode.ICC_CMD_ERR, "iccString cmd error"),
    EMV_ERR_ICC_BLOCK(RetCode.ICC_BLOCK, "iccString block"),
    EMV_ERR_RSP(RetCode.EMV_RSP_ERR, "emv response error"),
    EMV_ERR_APP_BLOCK(RetCode.EMV_APP_BLOCK, "emv application block"),
    EMV_ERR_NO_APP(RetCode.EMV_NO_APP, "emv no application"),
    EMV_ERR_USER_CANCEL(RetCode.EMV_USER_CANCEL, "emv user cancel"),
    EMV_ERR_TIME_OUT(RetCode.EMV_TIME_OUT, "emv time out"),
    EMV_ERR_DATA(RetCode.EMV_DATA_ERR, "emv data error"),
    EMV_ERR_NOT_ACCEPT(RetCode.EMV_NOT_ACCEPT, "emv not accept"),
    EMV_ERR_DENIAL(RetCode.EMV_DENIAL, "emv denial"),
    EMV_ERR_KEY_EXP(RetCode.EMV_KEY_EXP, "emv key expiry"),
    EMV_ERR_NO_PIN_PAD(RetCode.EMV_NO_PINPAD, "emv no pinpad"),
    EMV_ERR_NO_PASSWORD(RetCode.EMV_NO_PASSWORD, "emv no password"),
    EMV_ERR_SUM(RetCode.EMV_SUM_ERR, "emv checksum error"),
    EMV_ERR_NOT_FOUND(RetCode.EMV_NOT_FOUND, "emv not found"),
    EMV_ERR_NO_DATA(RetCode.EMV_NO_DATA, "emv no data"),
    EMV_ERR_OVERFLOW(RetCode.EMV_OVERFLOW, "emv overflow"),
    EMV_ERR_NO_TRANS_LOG(RetCode.NO_TRANS_LOG, "emv no trans log"),
    EMV_ERR_RECORD_NOT_EXIST(RetCode.RECORD_NOTEXIST, "emv recode not exist"),
    EMV_ERR_LOG_ITEM_NOT_EXIST(RetCode.LOGITEM_NOTEXIST, "emv log item not exist"),
    EMV_ERR_ICC_RSP_6985(RetCode.ICC_RSP_6985, "iccString response 6985"),
    EMV_ERR_FILE(RetCode.EMV_FILE_ERR, "emv file error"),
    EMV_ERR_PARAM(RetCode.EMV_PARAM_ERR, "emv parameter error"),
    EMV_ERR_NEXT_CVM(-8053, "emv next CVM"),
    EMV_ERR_QUIT_CVM(-8057, "emv quit CVM"),
    EMV_ERR_SELECT_NEXT(-8059, "emv select next"),
}