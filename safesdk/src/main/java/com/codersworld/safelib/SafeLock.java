package com.codersworld.safelib;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codersworld.safelib.helpers.AESHelper;
import com.codersworld.safelib.helpers.JKHelper;
import com.codersworld.safelib.helpers.Tags;
import com.codersworld.safelib.helpers.UserSessions;
import com.codersworld.safelib.listeners.OnAuthListener;
import com.codersworld.safelib.listeners.OnSafeAuthListener;
import com.codersworld.safelib.beans.AccountInfo;
import com.codersworld.safelib.beans.AllLocksBean;
import com.codersworld.safelib.beans.GateRecordsBean;
import com.codersworld.safelib.beans.LoginBean;
import com.codersworld.safelib.rest.ApiCall;
import com.codersworld.safelib.rest.OnResponse;
import com.codersworld.safelib.rest.UniverSelObjct;
import com.codersworld.safelib.utils.CommonMethods;
import com.codersworld.safelib.utils.SFProgress;
import com.depl.safelib.R;
import com.google.gson.Gson;
import com.ttlock.bl.sdk.api.TTLockClient;
import com.ttlock.bl.sdk.callback.ControlLockCallback;
import com.ttlock.bl.sdk.callback.GetBatteryLevelCallback;
import com.ttlock.bl.sdk.constant.ControlAction;
import com.ttlock.bl.sdk.entity.ControlLockResult;
import com.ttlock.bl.sdk.entity.LockError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SafeLock implements OnResponse<UniverSelObjct>, OnAuthListener {
    static Activity mActivity;
    static public Vibrator myVib;
    static long iniDateTime;
    static String strLat = "0.0";
    static String strLong = "0.0";
    static ApiCall mApiCall = null;
    OnSafeAuthListener mAuthListener = null;
    SQLiteDatabase database = null;

    public SafeLock(Activity activity, OnSafeAuthListener listener) {
        this.mActivity = activity;
        this.mAuthListener = listener;
        getInstance(mActivity);
        initApiCall();
    }

    public static void initApiCall() {
        if (mApiCall == null) {
            mApiCall = new ApiCall(mActivity);
        }
    }

    private static void getInstance(Activity activity) {
        mActivity = activity;
        myVib = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);
        statusCheck();
        try {
            final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
            bAdapter.enable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        iniDateTime = System.currentTimeMillis() + 1800000;
    }

    public static void statusCheck() {
        final LocationManager manager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    public static void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        mActivity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void authUser(String strUsername, String strPassword, String strAppVersion, String strAppName) {
        initApiCall();
        new UserSessions().saveAccessToken(mActivity,"");
        String param = Tags.SB_LOGIN_API + "&uid=" + strUsername + "&pwd=" + strPassword + "&GCMID=" + "&version=" + strAppVersion + "&lat=" + strLat + "&lng=" + strLong + "&EMIENo=" + CommonMethods.getIMEI(
                mActivity);
        Log.e("paramparam", param);
        AESHelper mAESHelper = new AESHelper();
        String encParam = mAESHelper.safeEncryption(mActivity, param);
        if (CommonMethods.isNetworkAvailable(mActivity)) {
            HashMap mMap = new HashMap<String, String>();
            mMap.put("val", encParam);
            mApiCall.userLogin(this, encParam);
        } else {
            onAuthResult("100", mActivity.getString(R.string.error_internet));
        }
    }

    ArrayList<AllLocksBean.InfoBean> mListLocks = new ArrayList();

    public void getDeviceList() {
        initApiCall();
        mListLocks = new ArrayList<>();
        String params = Tags.SB_GET_ALL_V3_LOCKS + "&cat=1&cid=" + UserSessions.getUserInfo(mActivity).getUid();
        AESHelper mAESHelper = new AESHelper();
        String encParam = mAESHelper.safeEncryption(mActivity, params);
      //  mApiCall.getAllV3Locks(this, "1",UserSessions.getUserInfo(mActivity).getUid());

        mApiCall.getAllV3Locks(this, encParam);
    }

    /*    public void getDeviceRecords(String strDate,String device_id,String strStartDate,String strEndDate) {
            initApiCall();
            String ClientID  = Tags.TT_CLIENT_ID;
            mApiCall.getLockRecords(this,ClientID,UserSessions.getAccessToken(mActivity),"1","100",strDate,device_id,strStartDate,strEndDate);

            mListLocks = new ArrayList<>();
            String params = Tags.SB_GET_ALL_V3_LOCKS + "&cat=1&cid=" + UserSessions.getUserInfo(mActivity).getUid() + "&deviceId=";
            AESHelper mAESHelper =new  AESHelper();
            String encParam = mAESHelper.safeEncryption(mActivity, params);
            mApiCall.getLockRecords(this, encParam);
        }*/
    public void getDeviceRecords(String startDate, String endDate, String device_id, String device_name) {
        initApiCall();
        mListLocks = new ArrayList<>();
        String params = Tags.SB_API_GET_GATE_RECORDS + "&contactid=" + UserSessions.getUserInfo(mActivity).getUid() +
                "&DeviceId=" + device_id + "&VehicleNumber=" + device_name +
                "&ToDate=" + endDate + "&FromDate=" + startDate + "&val1=" + "0" + "&val2=";
        AESHelper mAESHelper = new AESHelper();
        String encParam = mAESHelper.safeEncryption(mActivity, params);
        mApiCall.getGateRecords(this, encParam);
    }

    @Override
    public void onSuccess(UniverSelObjct response) {
        try {
            switch (response.getMethodname()) {
                case Tags.SB_LOGIN_API:
                    try {
                        LoginBean mLoginBean = (LoginBean) response.getResponse();
                        Log.e("mLoginBean", new Gson().toJson(mLoginBean));
                        if (mLoginBean.getSuccess() == 1) {
                            UserSessions.saveAccessToken(mActivity, (CommonMethods.isValidString(mLoginBean.getLoginvalidation().get(0).getToken())) ? mLoginBean.getLoginvalidation().get(0).getToken() : "");
                            if (CommonMethods.isValidString(mLoginBean.getLoginvalidation().get(0).getUid()) && !mLoginBean.getLoginvalidation().get(0).getUid().equalsIgnoreCase("0")) {
                                UserSessions.saveUserInfo(mActivity, mLoginBean.getLoginvalidation().get(0));
                                new JKHelper().makeTTAuthentication(mActivity, 4, this);
                                //moveToNext(mLoginBean.getLoginvalidation().get(0));
                            } else if (CommonMethods.isValidString(mLoginBean.getLoginvalidation().get(0).getReturnmessage())) {
                                onAuthResult("105", mLoginBean.getLoginvalidation().get(0).getReturnmessage());
                            } else {
                                onAuthResult("104", mActivity.getResources().getString(R.string.error_no_role));
                            }
                        } else if (CommonMethods.isValidString(mLoginBean.getMessage())) {
                            onAuthResult("103", mLoginBean.getMessage());
                        } else if (CommonMethods.isValidArrayList(mLoginBean.getLoginvalidation()) && CommonMethods.isValidString(mLoginBean.getLoginvalidation().get(0).getReturnmessage())) {
                            onAuthResult("102", mLoginBean.getLoginvalidation().get(0).getReturnmessage());
                        } else {
                            onAuthResult("101", mActivity.getString(R.string.something_wrong));
                        }
                    } catch (Exception e) {
                        onAuthResult("100", mActivity.getString(R.string.something_wrong));
                    }
                    break;
                case Tags.SB_GET_ALL_V3_LOCKS:
                    try {
                        AllLocksBean mAllLocksBean = (AllLocksBean) response.getResponse();
                        if (mAllLocksBean != null) {
                            /*if (CommonMethods.isValidString(mAllLocksBean.message)){
                                    msg=mAllLocksBean.message
                            }*/
                            if (mAllLocksBean.getSuccess() == 1) {
                                if (CommonMethods.isValidArrayList(mAllLocksBean.getNewusercreation())) {
                                    for (int a = 0; a < mAllLocksBean.getNewusercreation().size(); a++) {
                                        mAllLocksBean.getNewusercreation().get(a).setOwner_id(UserSessions.getUserInfo(mActivity).getUid());
                                    }
                                    onSafeDevices("106", mActivity.getString(R.string.something_wrong), mAllLocksBean.getNewusercreation());
                                } else {
                                    onSafeDevices("103", mActivity.getString(R.string.something_wrong), null);
                                }
                            } else {
                                onSafeDevices("102", mActivity.getString(R.string.something_wrong), null);
                            }
                        } else {
                            onSafeDevices("101", mActivity.getString(R.string.something_wrong), null);
                        }
                    } catch (Exception ex1) {
                        ex1.printStackTrace();
                        onSafeDevices("100", mActivity.getString(R.string.something_wrong), null);
                    }
                    break;
                case Tags.SB_API_GET_GATE_RECORDS:
                    try {
                        JSONObject jsonObjct = new JSONObject(response.getResponse().toString());
                        if (jsonObjct.getInt("success") == 1) {
                            GateRecordsBean gateOpenBean = new Gson().fromJson(response.getResponse().toString(), GateRecordsBean.class);
                            if (gateOpenBean != null && CommonMethods.isValidArrayList(gateOpenBean.getGetCOmmandSent())) {
                                for (int a = 0; a < gateOpenBean.getGetCOmmandSent().size(); a++) {
                                    GateRecordsBean.InfoBean mBean = gateOpenBean.getGetCOmmandSent().get(a);
                                    if (CommonMethods.isValidString(mBean.getSetupTime())) {
                                        String[] arr = mBean.getSentTime().split(" ");
                                        String dtStart = arr[0];
                                        gateOpenBean.getGetCOmmandSent().get(a).setDate(dtStart);
                                        long timeInMilliseconds = 0;
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        try {
                                            Date mDate = sdf.parse(mBean.getSetupTime());
                                            timeInMilliseconds = mDate.getTime();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        gateOpenBean.getGetCOmmandSent().get(a).setUnixTime(timeInMilliseconds);
                                        if (arr.length > 0) {
                                            gateOpenBean.getGetCOmmandSent().get(a).setSentTime(arr[1]);
                                        }
                                    }
                                }
                                onSafeRecords("106", "Success", gateOpenBean.getGetCOmmandSent());
                                //gateOpenBean.getCOmmandSent
                            } else {
                                onSafeRecords("100", mActivity.getString(R.string.something_wrong), null);
                            }
                        } else {
                            onSafeRecords("100","No records found for this device.", null);
                        }
                    } catch (Exception ex1) {
                        onSafeRecords("100", mActivity.getString(R.string.something_wrong), null);
                        ex1.printStackTrace();
                    }
                    break;
            }
        } catch (Exception e) {
            onAuthResult("100", mActivity.getString(R.string.error_internet));
        }
    }

    private void onSafeDevices(String code, String msg, ArrayList<AllLocksBean.InfoBean> mListLocks) {
        if (mAuthListener != null) {
            mAuthListener.onSafeDevices(code, msg, mListLocks);
        }
    }

    private void onSafeRecords(String code, String msg, ArrayList<GateRecordsBean.InfoBean> mListRecords) {
        if (mAuthListener != null) {
            mAuthListener.onSafeRecords(code, msg, mListRecords);
        }
    }

    private void onAuthResult(String code, String msg) {
        if (mAuthListener != null) {
            mAuthListener.onSafeAuth(code, msg);
        }
    }
    private void onLockAction(String code, String msg,String type) {
        if (mAuthListener != null) {
            mAuthListener.onSafeLockAction(code, msg,type);
        }
    }


    @Override
    public void onError(String type, String error) {
        try {
            switch (type) {
                case Tags.SB_LOGIN_API:
                    if (mAuthListener != null) {
                        onAuthResult("100", error);
                    }
                    break;
                case Tags.SB_GET_ALL_V3_LOCKS:
                    if (mAuthListener != null) {
                        onSafeDevices("100", error, null);
                    }
                    break;
                case Tags.SB_API_GET_GATE_RECORDS:
                    if (mAuthListener != null) {
                        onSafeRecords("100", error, null);
                    }
                    break;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onAuth(AccountInfo mInfo) {
        onAuthResult("106", mActivity.getString(R.string.login_success));
    }

    private void closeLock(String lockData, String macID) {
        long unlockdate = System.currentTimeMillis();
        if (iniDateTime < unlockdate) {
            return;
        }
        SFProgress.showProgressDialog(mActivity, true);
        TTLockClient.getDefault().controlLock(ControlAction.LOCK, lockData, macID, new ControlLockCallback() {
            @Override
            public void onControlLockSuccess(ControlLockResult controlLockResult) {
                Toast.makeText(mActivity, "lock is locked!", Toast.LENGTH_LONG).show();
                onLockAction("106","Device is locked successfully.","close lock");

                //unlockRecordUpload("Locked via App");
                SFProgress.hideProgressDialog(mActivity);

            }

            @Override
            public void onFail(LockError error) {
                Log.e("Lock_lock_error", error.getErrorCode() + "\n" + error.getDescription());
                Toast.makeText(mActivity, "Failed To Lock!--" + error.getDescription(), Toast.LENGTH_LONG).show();
                onLockAction("100","Failed to lock the device.","close lock");
                SFProgress.hideProgressDialog(mActivity);
            }
        });
    }


    private void openLock(long unlockdate, String lockID, String macID) {
        myVib.vibrate(100);
        if (iniDateTime < unlockdate) {
            Toast.makeText(mActivity, "Please Refresh Page", Toast.LENGTH_SHORT).show();
            onLockAction("100","Please Refresh Page","open lock");
            return;
        }
        SFProgress.showProgressDialog(mActivity, true);
        TTLockClient.getDefault().controlLock(ControlAction.UNLOCK, lockID, macID, new ControlLockCallback() {
            //TTLockClient.getDefault().controlLock(ControlAction.UNLOCK, mMyTestLockEKey.getLockData(), mMyTestLockEKey.getLockMac(), new ControlLockCallback() {
            @Override
            public void onControlLockSuccess(ControlLockResult controlLockResult) {
                Log.i("Safe Lock", "onControlLockSuccess: " + controlLockResult.controlAction);
                 SFProgress.hideProgressDialog(mActivity);
                TTLockClient.getDefault().getBatteryLevel(lockID, macID, new GetBatteryLevelCallback() {
                    @Override
                    public void onGetBatteryLevelSuccess(int electricQuantity) {
                       // Toast.makeText(mActivity, "lock battery is " + electricQuantity + "%", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(LockError error) {
                       // Toast.makeText(mActivity, error.getDescription(), Toast.LENGTH_SHORT).show();
                    }
                });
                if (myVib != null) {
                    myVib.vibrate(100);
                }
                try {
                    onLockAction("106","Lock opened successfully.","open lock");
                    //unlockRecordUpload("Opened via APP");
                } catch (Exception e) {
                    e.printStackTrace();
                    onLockAction("101",mActivity.getString(R.string.something_wrong),"open lock");
                }
            }

            @Override
            public void onFail(LockError error) {
                Log.e("Lock_unlock_error", error.getErrorCode() + "\n" + error.getDescription());
                onLockAction("102","failed to open the lock.","open lock");

                try {
                    if (error.getDescription().toLowerCase().contains("key") || error.getDescription().toLowerCase().contains("parameter format or content is incorrect")) {
                        authenticateTTLock();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 SFProgress.hideProgressDialog(mActivity);
                //unlockRecordUpload("Failed via APP");
            }
        });
    }

    private void authenticateTTLock() {
        new JKHelper().makeTTAuthentication(mActivity, 4, (OnAuthListener) mActivity);
    }


    public void hitApi() {
         StringRequest postRequest = new StringRequest(Request.Method.POST, Tags.BASE_URL_MEMBOCOOL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         try {
                            AESHelper jkHelper = new AESHelper();
                            String responses = jkHelper.safeDecryption(response, mActivity);
                            Log.e("respAfterDec",responses);
                            final int chunkSize = 20480;
                            for (int i = 0; i < responses.length(); i += chunkSize) {
                                Log.i("hitApiRemote", responses.substring(i, Math.min(responses.length(), i + chunkSize)));
                            }
                            JSONObject jsonObject = new JSONObject(responses);
                            int success = jsonObject.getInt("success");
                             if (success == 1) {

                              } else if (success == 0) {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
             @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    String param = Tags.SB_GET_ALL_V3_LOCKS + "&cat=1&cid=" + UserSessions.getUserInfo(mActivity).getUid();
/*
                    if (strtop5.equalsIgnoreCase("1")) {
                        param = param + "&istop5=" + strtop5;
                    }
                    if (strSingleItem.equalsIgnoreCase("1")) {
                        param = param + "&deviceId=" + strSearchValue;
                    }
*/
                    Log.e("beforeEnc",param);
                    AESHelper jkHelper = new AESHelper();
                    String finalparam = jkHelper.safeEncryption( mActivity,param);
                    params.put("val", finalparam);
                    Log.e("afterEnc",finalparam);
                    return params;
                } catch (Exception e) {
                    //method=GetV3lockdetail&cat=1&cid=96820&deviceId=
                    //method=GetV3lockdetail&cat=1&cid=96820
                    e.printStackTrace();
                    return params;
                }
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mActivity).add(postRequest);
    }


}
