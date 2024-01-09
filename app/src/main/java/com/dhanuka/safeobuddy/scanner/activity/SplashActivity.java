package com.dhanuka.safeobuddy.scanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity ;

import com.codersworld.safelib.SafeLock;
import com.codersworld.safelib.beans.AllLocksBean;
import com.codersworld.safelib.beans.GateRecordsBean;
import com.codersworld.safelib.listeners.OnSafeAuthListener;
import com.codersworld.safelib.utils.CommonMethods;
import com.dhanuka.safeobuddy.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class SplashActivity extends AppCompatActivity implements OnSafeAuthListener {
    SafeLock mSafeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSafeLock = new SafeLock(SplashActivity.this, this);
        //startActivityForResult(new Intent(this, ScanActivity.class).putExtra("open_scanner", true), 102);
        // CommonMethods.successToast(this, AppUrls.GET_UOM_ITEM);
       // mSafeLock.authUser("shutterlock", "123456", "1.0", "Safe SDL demo");
        mSafeLock.authUser("uffizio", "uffizio123", "1.0", "Safe SDL demo");
       // mSafeLock.authUser("uffizio", "uffizio123", "1.0", "Safe SDL demo");
     }

    public void onScan(View v) {
        //mSafeLock.authUser("shutterlock", "123456", "1.0", "Safe SDL demo");
        mSafeLock.authUser("uffizio", "uffizio123", "1.0", "Safe SDL demo");
    }

    @Override
    public void onSafeAuth(String errorCode, String message) {
        Log.e("onSafeAuth", errorCode + "\n" + message);
        if (errorCode.equalsIgnoreCase("106")) {
            mSafeLock.getDeviceList();
        }
//J6o+BXjdFci24T7EcRf2bb4+n2ETbMq/f62L6WifcirH5SiYFdP92RPddHC6mnvrJW4WjEMQt0AioYE/0s79T0Rs/zCj/tEgek/5c/8GwHvDN8r8Rc4NeHkuB6vPxbZtNJlPxKsF/dgHZ8n2EzJMNvN5q99zKaav4udCUaFFCiQ=
//J6o+BXjdFci24T7EcRf2bb4+n2ETbMq/f62L6Wifcio+Kv/OikdeUxB5RF1jj6Ea+eMhs7wGXNtgLtkRv1vijIxHYGUD20j+dbaHAlvH+zx8cljnFqJkj0QWmz/Xe/Gq9Q1ZZc2rowimqh2Y3DMLMRiWX+fCDsd5Vs9KzYg9+LM=
    }

    @Override
    public void onSafeDevices(String errorCode, String message, ArrayList<AllLocksBean.InfoBean> mListLocks) {
        Log.e("onSafeDevices", errorCode + "\n" + message);
        if (errorCode.equalsIgnoreCase("106")) {
            if (CommonMethods.isValidArrayList(mListLocks)) {
                Log.e("mListLocks", new Gson().toJson(mListLocks));
                String startDate = CommonMethods.getCalculatedDate("MM/dd/yyyy", -27);
                String endDate = CommonMethods.getCurrentFormatedDate("MM/dd/yyyy");

                mSafeLock.openLock(System.currentTimeMillis(),"9605866");
              //  mSafeLock.openLock(System.currentTimeMillis(),"wifi smart meter");
                mSafeLock.getDeviceRecords(startDate, endDate, mListLocks.get(0).getDeviceID(), mListLocks.get(0).getVehicleNumber());
            }
            //mSafeLock.getDeviceList();
        }

    }

    @Override
    public void onSafeRecords(String errorCode, String message, ArrayList<GateRecordsBean.InfoBean> mListRecords) {
        Log.e("onSafeRecords", errorCode + "\n" + message);
        if (errorCode.equalsIgnoreCase("106")) {
            if (CommonMethods.isValidArrayList(mListRecords)) {
                Log.e("mListRecords", new Gson().toJson(mListRecords));
            }

        }
    }

    @Override
    public void onSafeLockAction(String code, String message, String type) {
Log.e("action_lock",code+"\n"+message+"\n"+type);
    }
}
