package com.codersworld.safelib.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.codersworld.safelib.utils.CommonMethods;
import com.example.librarymain.DhanukaMain;

import org.json.JSONException;
import org.json.JSONObject;

public class AESHelper {

    public String safeEncryption(Context context, String strParam) {
        UserSessions mUserSession = new UserSessions(context);
        String token = (mUserSession != null && CommonMethods.isValidString(mUserSession.getAccessToken(context))) ? mUserSession.getAccessToken(context) : "";
        String userid = (mUserSession != null && mUserSession.getUserInfo(context) != null) ? mUserSession.getUserInfo(context).getEmail() : "";
        try {
            Log.e("before_enc",token+"\n"+strParam);
            strParam = DhanukaMain.SafeOBuddyEncryptUtils(strParam, token, userid);
            return strParam;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String safeDecryption(String response, final Activity context) {
        String responses;
        try {
            responses = DhanukaMain.SafeOBuddyDecryptUtils(response);
            Log.e("response123",response);
            JSONObject jsonObject = new JSONObject(responses);
            int success = jsonObject.getInt("success");
            if (success == 9999) {
                return "";
            } else {
                return responses;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
