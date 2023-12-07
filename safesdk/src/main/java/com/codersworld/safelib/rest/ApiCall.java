package com.codersworld.safelib.rest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.codersworld.safelib.helpers.UserSessions;
import com.codersworld.safelib.helpers.AESHelper;
import com.codersworld.safelib.helpers.Tags;
import com.codersworld.safelib.helpers.UserSessions;
import com.codersworld.safelib. beans.AccountInfo;
import com.codersworld.safelib.beans.AllLocksBean;
import com.codersworld.safelib.beans.KeyListObj;
import com.codersworld.safelib.beans.LoginBean;
import com.codersworld.safelib.rest.UniverSelObjct;
import com.codersworld.safelib.rest.ttlock.RetrofitAPIManager;
import com.codersworld.safelib.utils.CommonMethods;
import com.codersworld.safelib.utils.SFProgress;
import com.depl.safelib.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    public Activity mContext = null;
    public UserSessions mUserSessions = null;

    public ApiCall(Context applicationContext) {
    }
    public ApiCall(Activity ctx) {
        this.mContext = ctx;
        mUserSessions = new UserSessions(mContext);
    }
    public void userLogin(OnResponse<UniverSelObjct> onResponse, String strParams) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.makeLogin(strParams).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                try {
                    if (response != null) {
                        try {
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            Log.e("response123", response.body().toString());
                            LoginBean mBean = new Gson().fromJson(strResp, LoginBean.class);
                            Log.e("strResp11", new Gson().toJson(mBean));
                            onResponse.onSuccess(new UniverSelObjct(mBean, Tags.SB_LOGIN_API, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_LOGIN_API, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_LOGIN_API, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_LOGIN_API, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void ttlockAuth(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(3, 3).create(ApiRequest.class);
        mRequest.ttlockAuth(strParams[0], strParams[1], strParams[2], strParams[3]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                try {
                    if (response != null) {
                        try {
                            String strResp = response.body().toString();
                            Log.e("response123", response.body().toString());
                            AccountInfo mBean = new Gson().fromJson(strResp, AccountInfo.class);
                            Log.e("strResp11", new Gson().toJson(mBean));
                            onResponse.onSuccess(new UniverSelObjct(mBean, Tags.SB_API_TTLOCK_AUTH_TOKEN, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_API_TTLOCK_AUTH_TOKEN, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_API_TTLOCK_AUTH_TOKEN, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_API_TTLOCK_AUTH_TOKEN, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void getUserKeyList(OnResponse<UniverSelObjct> onResponse, HashMap<String, String> param) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< ResponseBody > call = mRequest.getUserKeyList(param);
        RetrofitAPIManager.enqueue(call,new TypeToken<KeyListObj>() {}, result->{
            if (!result.success) {
                onResponse.onError(Tags.SB_API_TTLOCK_USER_KEYLIST, mContext.getResources().getString(R.string.something_wrong));
                return;
            }else{
                KeyListObj keyListObj = result.getResult();
                onResponse.onSuccess(new UniverSelObjct(keyListObj, Tags.SB_API_TTLOCK_USER_KEYLIST, "true", ""));
            }

        }, requestError -> {
            onResponse.onError(Tags.SB_API_TTLOCK_USER_KEYLIST, mContext.getResources().getString(R.string.something_wrong));
        });
     }
    public void getGatewayList(OnResponse<UniverSelObjct> onResponse,  String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        Call<String> call = mRequest.getGatewayList(params[0],params[1],params[2],params[3],params[4]);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                if (json.contains("list")) {
                    onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_TTLOCK_GATEWAY_LIST, "true", ""));
                } else {
                    onResponse.onError(Tags.SB_API_TTLOCK_GATEWAY_LIST, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onResponse.onError(Tags.SB_API_TTLOCK_GATEWAY_LIST, mContext.getResources().getString(R.string.something_wrong));
            }
        });
     }

    public void uploadGatewayDetail(OnResponse<UniverSelObjct> onResponse, String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< String > call = mRequest.uploadGatewayDetail(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                if (CommonMethods.isValidString(json)){
                    try {
                        onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_TTLOCK_UPLOAD_DETAIL, "true", (params.length>8)?params[8]:""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onResponse.onError(Tags.SB_API_TTLOCK_UPLOAD_DETAIL, mContext.getResources().getString(R.string.something_wrong));
                    }
                } else {
                    onResponse.onError(Tags.SB_API_TTLOCK_UPLOAD_DETAIL, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onResponse.onError(Tags.SB_API_TTLOCK_UPLOAD_DETAIL, mContext.getResources().getString(R.string.something_wrong));
            }
        });
     }

   public void renameGateway(OnResponse<UniverSelObjct> onResponse, String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< String > call = mRequest.renameGateway(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                if (CommonMethods.isValidString(json)){
                    try {
                        onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_TTLOCK_RENAME, "true", (params.length>8)?params[8]:""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onResponse.onError(Tags.SB_API_TTLOCK_RENAME, mContext.getResources().getString(R.string.something_wrong));
                    }
                } else {
                    onResponse.onError(Tags.SB_API_TTLOCK_RENAME, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onResponse.onError(Tags.SB_API_TTLOCK_RENAME, mContext.getResources().getString(R.string.something_wrong));
            }
        });
     }

   public void renameTTLock(OnResponse<UniverSelObjct> onResponse, String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< String > call = mRequest.renameTTLock(params[0],params[1],params[2],params[3],params[4]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                if (CommonMethods.isValidString(json)){
                    try {
                        onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_TTLOCK_RENAME_LOCK, "true", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onResponse.onError(Tags.SB_API_TTLOCK_RENAME_LOCK, mContext.getResources().getString(R.string.something_wrong));
                    }
                } else {
                    onResponse.onError(Tags.SB_API_TTLOCK_RENAME_LOCK, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onResponse.onError(Tags.SB_API_TTLOCK_RENAME_LOCK, mContext.getResources().getString(R.string.something_wrong));
            }
        });
     }

   public void gatewayIsInitSuccess(OnResponse<UniverSelObjct> onResponse, String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< String > call = mRequest.gatewayIsInitSuccess(params[0],params[1],params[2],params[3]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                if (CommonMethods.isValidString(json)){
                    try {
                        onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_TTLOCK_INIT_SUCCESS, "true", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onResponse.onError(Tags.SB_API_TTLOCK_INIT_SUCCESS, mContext.getResources().getString(R.string.something_wrong));
                    }
                } else {
                    onResponse.onError(Tags.SB_API_TTLOCK_INIT_SUCCESS, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onResponse.onError(Tags.SB_API_TTLOCK_INIT_SUCCESS, mContext.getResources().getString(R.string.something_wrong));
            }
        });
     }
    public void getGateRecords(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.getGateRecords(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response != null) {
                        try {
                            Log.e("response123", response.body().toString());
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_API_GET_GATE_RECORDS, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("recharge_error","recharge_error : "+e.getMessage());
                            onResponse.onError(Tags.SB_API_GET_GATE_RECORDS, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_API_GET_GATE_RECORDS, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_API_GET_GATE_RECORDS, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void getAllV3Locks(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        Call<String>  mCall = mRequest.getAllV3Locks(strParams[0]);
        if(strParams.length>1){
            mCall = mRequest.getAllV3Locks1(strParams[0],strParams[1]);
        }
        mCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    if (response != null) {
                        try {
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            Log.e("locksResp", response.body().toString());
                            AllLocksBean mBean = new Gson().fromJson(strResp,AllLocksBean.class);
                            Log.e("locksResp11",new Gson().toJson(mBean));
                             onResponse.onSuccess(new UniverSelObjct(mBean, Tags.SB_GET_ALL_V3_LOCKS, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_GET_ALL_V3_LOCKS, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_GET_ALL_V3_LOCKS, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_GET_ALL_V3_LOCKS, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void updateLockData(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.updateLockData(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    if (response != null) {
                        try {
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            Log.e("response123", response.body().toString());
                              onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_UPDATE_LOCK_DATA, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_UPDATE_LOCK_DATA, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_UPDATE_LOCK_DATA, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_UPDATE_LOCK_DATA, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void updateLockName(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.updateLockName(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    if (response != null) {
                        try {
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            Log.e("response123", response.body().toString());
                             onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_UPDATE_LOCK_NAME, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_UPDATE_LOCK_NAME, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_UPDATE_LOCK_NAME, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_UPDATE_LOCK_NAME, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void deleteLock(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.deleteLock(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    if (response != null) {
                        try {
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            Log.e("response123", response.body().toString());
                             onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_DELETE_LOCK, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_DELETE_LOCK, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_DELETE_LOCK, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_DELETE_LOCK, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void saveArmMode(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.saveArmMode(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    if (response != null) {
                        try {
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            Log.e("response123", response.body().toString());
                             onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_UPDATE_LOCK_NAME, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_UPDATE_LOCK_NAME, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_UPDATE_LOCK_NAME, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_UPDATE_LOCK_NAME, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void uploadGeneratedOTP(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.uploadGeneratedOTP(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response != null) {
                        try {
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            Log.e("response123", response.body().toString());
                             onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_OPEN_LOCK, strParams[1], ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_OPEN_LOCK, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_OPEN_LOCK, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_OPEN_LOCK, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }
    public void unlockPendingGates(OnResponse<UniverSelObjct> onResponse, Map<String, String> mParams,Boolean isTrue) {
        if (isTrue){
            try {
                SFProgress.showProgressDialog(mContext, true);
            } catch (Exception e) {
            }
        }
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(2, 2).create(ApiRequest.class);
        mRequest.unlockPendingGates(mParams).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (isTrue){
                    try {
                        SFProgress.hideProgressDialog(mContext);
                    } catch (Exception e) {
                    }
                }
                try {
                    if (response != null) {
                        try {
                            String strResp = response.body().toString();
                            Log.e("response123", response.body().toString());
                            onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_UNLOCK_GATE_UPLOAD, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_UNLOCK_GATE_UPLOAD, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_UNLOCK_GATE_UPLOAD, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("asfdas", call.request().toString() + "");
                if (isTrue){
                    try {
                        SFProgress.hideProgressDialog(mContext);
                    } catch (Exception e) {
                    }
                }
                onResponse.onError(Tags.SB_UNLOCK_GATE_UPLOAD, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void saveLockStatus(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.saveLockStatus(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }

                try {
                    if (response != null) {
                        try {
                            Log.e("response123", response.body().toString());
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                             onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_API_SAVE_LOCK_STATUS, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("recharge_error","recharge_error : "+e.getMessage());
                            onResponse.onError(Tags.SB_API_SAVE_LOCK_STATUS, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_API_SAVE_LOCK_STATUS, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_API_SAVE_LOCK_STATUS, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void initTTLock(OnResponse<UniverSelObjct> onResponse, String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< String > call = mRequest.iniTTLock(params[0],params[1],params[2],params[3],params[4]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }

                String json = response.body();
                if (CommonMethods.isValidString(json)){
                    try {//LockInitResultObj
                        onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_TTLOCK_INIT, "true", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onResponse.onError(Tags.SB_API_TTLOCK_INIT, mContext.getResources().getString(R.string.something_wrong));
                    }
                } else {
                    onResponse.onError(Tags.SB_API_TTLOCK_INIT, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                onResponse.onError(Tags.SB_API_TTLOCK_INIT, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void remotlyUnlockDevice(OnResponse<UniverSelObjct> onResponse, String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< String > call = mRequest.remotlyUnlockDevice(params[0],params[1],params[2],params[3]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }

                if (CommonMethods.isValidString(json)){
                    try {//LockInitResultObj
                        onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_TTLOCK_UNLOCK_REMOTLY, "true", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onResponse.onError(Tags.SB_API_TTLOCK_UNLOCK_REMOTLY, mContext.getResources().getString(R.string.something_wrong));
                    }
                } else {
                    onResponse.onError(Tags.SB_API_TTLOCK_UNLOCK_REMOTLY, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onResponse.onError(Tags.SB_API_TTLOCK_UNLOCK_REMOTLY, mContext.getResources().getString(R.string.something_wrong));
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
            }
        });
    }

    public void addLocksToServer(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.addLocksToServer(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }

                try {
                    if (response != null) {
                        try {
                            Log.e("response123", response.body().toString());
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_API_ADD_NEW_LOCK, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("recharge_error","recharge_error : "+e.getMessage());
                            onResponse.onError(Tags.SB_API_ADD_NEW_LOCK, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_API_ADD_NEW_LOCK, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_API_ADD_NEW_LOCK, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

   public void getTroubleshoot(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.addLocksToServer(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }

                try {
                    if (response != null) {
                        try {
                            Log.e("response123", response.body().toString());
                            String strResp = new AESHelper().safeDecryption(response.body().toString(), mContext);
                            onResponse.onSuccess(new UniverSelObjct(strResp, Tags.SB_API_ADD_NEW_LOCK, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("recharge_error","recharge_error : "+e.getMessage());
                            onResponse.onError(Tags.SB_API_ADD_NEW_LOCK, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_API_ADD_NEW_LOCK, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_API_ADD_NEW_LOCK, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

 //SB_API_LOCK_RECORDS_LIST
    public void getLockRecords(OnResponse<UniverSelObjct> onResponse, String... params) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitAPIManager.provideClientApi();
        //mRequest.getUserKeyList(param).enqueue(
        Call< String > call = mRequest.getLockRecords(params[0],params[1],params[2],params[3],params[4],params[5],params[6]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }

                String json = response.body();
                if (CommonMethods.isValidString(json)){
                    try {//LockInitResultObj
                        onResponse.onSuccess(new UniverSelObjct(json, Tags.SB_API_LOCK_RECORDS_LIST, "true", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        onResponse.onError(Tags.SB_API_LOCK_RECORDS_LIST, mContext.getResources().getString(R.string.something_wrong));
                    }
                } else {
                    onResponse.onError(Tags.SB_API_LOCK_RECORDS_LIST, mContext.getResources().getString(R.string.something_wrong));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                onResponse.onError(Tags.SB_API_LOCK_RECORDS_LIST, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void checkBluetoothAccess(OnResponse<UniverSelObjct> onResponse, String... strParams) {
        try {
            SFProgress.showProgressDialog(mContext, true);
        } catch (Exception e) {
        }
        ApiRequest mRequest = RetrofitRequest.getRetrofitInstance(1, 2).create(ApiRequest.class);
        mRequest.checkBluetoothAccess(strParams[0]).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }

                try {
                    if (response != null) {
                        try {
                            Log.e("response123", response.body().toString());
                            onResponse.onSuccess(new UniverSelObjct(response.body().toString(), Tags.SB_API_CHECK_BLUETOOTH_ACCESS, "true", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onError(Tags.SB_API_CHECK_BLUETOOTH_ACCESS, mContext.getResources().getString(R.string.something_wrong));
                        }
                    } else {
                        onResponse.onError(Tags.SB_API_CHECK_BLUETOOTH_ACCESS, mContext.getResources().getString(R.string.something_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                try {
                    SFProgress.hideProgressDialog(mContext);
                } catch (Exception e) {
                }
                Log.e("asfdas", call.request().toString() + "");
                onResponse.onError(Tags.SB_API_CHECK_BLUETOOTH_ACCESS, mContext.getResources().getString(R.string.something_wrong));
            }
        });
    }


}
