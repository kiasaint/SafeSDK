package com.codersworld.safelib.listeners;

import com.codersworld.safelib.beans.AllLocksBean;
import com.codersworld.safelib.beans.GateRecordsBean;

import java.util.ArrayList;

public interface OnSafeAuthListener {
    void onSafeAuth(String code,String message);
    void onSafeDevices(String code, String message, ArrayList<AllLocksBean.InfoBean> mListLocks);
    void onSafeRecords(String code, String message, ArrayList<GateRecordsBean.InfoBean> mListLocks);
    void onSafeLockAction(String code, String message,String type);
}
