package com.codersworld.safelib.helpers;

public class Tags {
    public static final String PREFS_NAME = "safelock_sdk";
    public static final String SB_ACCESS_TOKEN = "sl_tokenval";
    public static final String SB_USER_INFO = "Sl_user_info";
    public static final String SB_TT_KEY_LIST = "Sl_tt_key_list";
    public static final String SB_TT_ACCOUNT_INFO = "Sl_tt_account_info";


    /* ------- TTLOCK API URLS ---- */
    public static final String BASE_URL_TTLOCK = "https://euapi.ttlock.com";
    public static final String SB_API_TTLOCK_AUTH_TOKEN = "/oauth2/token";
    public static final String SB_API_TTLOCK_USER_KEYLIST = "/v3/key/list";
    public static final String SB_API_TTLOCK_GATEWAY_LIST = "/v3/gateway/list";
    public static final String SB_API_TTLOCK_UPLOAD_DETAIL = "/v3/gateway/uploadDetail";
    public static final String SB_API_TTLOCK_RENAME = "/v3/gateway/rename";
    public static final String SB_API_TTLOCK_RENAME_LOCK = "/v3/lock/rename";
    public static final String SB_API_TTLOCK_INIT_SUCCESS = "/v3/gateway/isInitSuccess";
    public static final String SB_API_TTLOCK_INIT = "/v3/lock/initialize";
    public static final String SB_API_TTLOCK_UNLOCK_REMOTLY = "/v3/lock/unlock";
    public static final String SB_API_LOCK_RECORDS_LIST = "/v3/lockRecord/list";
    public static final String TT_CLIENT_ID = "439063e312444f1f85050a52efcecd2e";
    public static final String TT_CLIENT_SECRET = "0ef1c49b70c02ae6314bde603d4e9b05";


    /* ------- API URLS ---- */

    public static final String SERVER_MID_PATH = "vehicletrack.membocool.com";
    public static final String SERVER_MID_PATH_MMTHINKBIZ = "mmthinkbiz.com/MobileService.aspx";
    public static final String SERVER_SLASH = "/";
    public static final String BASE_URL_MEMBOCOOL = "https:" + SERVER_SLASH + SERVER_SLASH + SERVER_MID_PATH + SERVER_SLASH;

    public static final String BASE_URL_MMTHINKBIZ = "http:" + SERVER_SLASH + SERVER_SLASH + SERVER_MID_PATH_MMTHINKBIZ + SERVER_SLASH;


    public static final String SB_API_METHOD = "Safeobuddy.aspx?method=apss";
    public static final String SB_LOGIN_API = "method=LoginValidation1";
    public static final String SB_GET_ALL_V3_LOCKS = "method=GetV3lockdetail";//""Safeobuddy.aspx?method=apss";
    public static final String SB_UPDATE_LOCK_DATA = "method=UpdateLockData";//""Safeobuddy.aspx?method=apss";
    public static final String SB_UPDATE_LOCK_NAME = "method=UpdateLockName";//""Safeobuddy.aspx?method=apss";
    public static final String SB_DELETE_LOCK = "method=Insertunlocktimeaccessdetail";//""Safeobuddy.aspx?method=apss";
    public static final String SB_OPEN_LOCK = "method=StartStopVehicleOpenGate";//""Safeobuddy.aspx?method=apss";
    public static final String SB_UNLOCK_GATE_UPLOAD = "NewRegistration?method=Uploadgateunlock";//""Safeobuddy.aspx?method=apss";
    public static final String SB_API_SAVE_LOCK_STATUS = "method=SaveLockStatus";//""Safeobuddy.aspx?method=apss";
    public static final String SB_API_ADD_NEW_LOCK = "method=Add_Lock_New";//""Safeobuddy.aspx?method=apss";
    public static final String SB_API_CHECK_BLUETOOTH_ACCESS = "ConService.aspx?method=checkbothlockaccess";//""Safeobuddy.aspx?method=apss";
    public static final String SB_API_GET_GATE_RECORDS = "method=GetVehicle_Lock_Summary";//""Safeobuddy.aspx?method=apss";
    public static final String SB_WATCH_LIST_API= "ConService.aspx?method=GetV3lockdetail";
    public static final String SB_GET_ALL_V3_LOCKS1= "ConService.aspx?method=GetV3lockdetail";

}
