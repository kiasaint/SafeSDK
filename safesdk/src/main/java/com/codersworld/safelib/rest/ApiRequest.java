package com.codersworld.safelib.rest;



import com.codersworld.safelib.helpers.Tags;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRequest {

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> makeLogin(@Field("val") String params);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> getAllV3Locks(@Field("val") String params);
    @POST(Tags.SB_GET_ALL_V3_LOCKS)
    @FormUrlEncoded
    Call<String> getAllV3Locks1(@Query("cat") String cat,@Field("cid") String cid);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> updateLockData(@Field("val") String params);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> updateLockName(@Field("val") String params);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> deleteLock(@Field("val") String params);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> saveArmMode(@Field("val") String params);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> uploadGeneratedOTP(@Field("val") String params);

    /*  ttlock */
    @POST(Tags.SB_API_TTLOCK_AUTH_TOKEN)
    @FormUrlEncoded
    Call<String> ttlockAuth(@Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("username") String username, @Field("password") String password);

    @GET(Tags.SB_API_TTLOCK_USER_KEYLIST)
    Call<ResponseBody> getUserKeyList(@QueryMap Map<String, String> params);

    @POST(Tags.SB_API_TTLOCK_GATEWAY_LIST)
    @FormUrlEncoded
    Call<String> getGatewayList(@Field("clientId") String clientId,
                                @Field("accessToken") String accessToken,
                                @Field("pageNo") String pageNo,
                                @Field("pageSize") String pageSize,
                                @Field("date") String date
    );

    @POST(Tags.SB_API_TTLOCK_UPLOAD_DETAIL)
    @FormUrlEncoded
    Call<String> uploadGatewayDetail(@Field("clientId") String clientId,
                                     @Field("accessToken") String accessToken,
                                     @Field("gatewayId") String gatewayId,
                                     @Field("modelNum") String modelNum,
                                     @Field("hardwareRevision") String hardwareRevision,
                                     @Field("firmwareRevision") String firmwareRevision,
                                     @Field("networkName") String networkName,
                                     @Field("date") String date
    );

    @POST(Tags.SB_API_TTLOCK_RENAME)
    @FormUrlEncoded
    Call<String> renameGateway(@Field("clientId") String clientId,
                               @Field("accessToken") String accessToken,
                               @Field("gatewayId") String gatewayId,
                               @Field("modelNum") String modelNum,
                               @Field("hardwareRevision") String hardwareRevision,
                               @Field("firmwareRevision") String firmwareRevision,
                               @Field("networkName") String networkName,
                               @Field("date") String date
    );

    @POST(Tags.SB_API_TTLOCK_RENAME_LOCK)
    @FormUrlEncoded
    Call<String> renameTTLock(@Field("clientId") String clientId,
                               @Field("accessToken") String accessToken,
                               @Field("lockAlias") String lockAlias,
                               @Field("date") String date,
                               @Field("lockId") String lockId
    );

    @POST(Tags.SB_API_TTLOCK_INIT_SUCCESS)
    @FormUrlEncoded
    Call<String> gatewayIsInitSuccess(@Field("clientId") String clientId,
                                      @Field("accessToken") String accessToken,
                                      @Field("gatewayNetMac") String gatewayNetMac,
                                      @Field("date") String date
    );

    @POST(Tags.SB_API_TTLOCK_INIT)
    @FormUrlEncoded
    Call<String> iniTTLock(@Field("clientId") String clientId,
                           @Field("accessToken") String accessToken,
                           @Field("lockData") String gatewayNetMac,
                           @Field("lockAlias") String lockAlias,
                           @Field("date") String date
    );

    @POST(Tags.SB_API_LOCK_RECORDS_LIST)
    @FormUrlEncoded
    Call<String> getLockRecords(@Field("clientId") String clientId,
                           @Field("accessToken") String accessToken,
                           @Field("pageNo") String pageNo,
                           @Field("date") String date,
                           @Field("lockId") String lockId,
                           @Field("startDate") String startDate,
                           @Field("endDate") String endDate
    );
     @POST(Tags.SB_API_TTLOCK_UNLOCK_REMOTLY)
    @FormUrlEncoded
    Call<String> remotlyUnlockDevice(@Field("clientId") String clientId,
                                     @Field("accessToken") String accessToken,
                                     @Field("date") String date,
                                     @Field("lockId") String lockId
    );

    @POST(Tags.SB_UNLOCK_GATE_UPLOAD)
    @FormUrlEncoded
    Call<String> unlockPendingGates(@FieldMap Map<String, String> mParams);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> saveLockStatus(@Field("val") String val);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> addLocksToServer(@Field("val") String val);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> gateUnlock(@Field("val") String val);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> getGateRecords(@Field("val") String val);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> getAllVehicles(@Field("val") String val);

    @POST(Tags.SB_API_METHOD)
    @FormUrlEncoded
    Call<String> getAllVehiclesData(@Field("val") String val);

    @GET(Tags.SB_API_CHECK_BLUETOOTH_ACCESS)
    Call<String> checkBluetoothAccess(@Query("contactid") String contactid);

    }
