package com.codersworld.safelib.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginBean implements Serializable {
    @SerializedName("success")
    int success;
    @SerializedName("message")
    String message;
    @SerializedName("loginvalidation")
    ArrayList<InfoBean> loginvalidation;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<InfoBean> getLoginvalidation() {
        return loginvalidation;
    }

    public void setLoginvalidation(ArrayList<InfoBean> loginvalidation) {
        this.loginvalidation = loginvalidation;
    }

    public static class InfoBean implements Serializable {
        String returnmessage;
        String uid;
        String val1;
        String gcmstatus;
        String usercategory;
        String Blatlong;
        String val2;
        String val3;
        String val4;
        String btmainuserId;
        String btmailpwd;
        String btusername;
        String IsHotelUser;
        String clientid;
        String clientsecret;
        String MainContactId;
        String usertype;
        String token;
        String email;
        String password;
        String isbackupuser;
        String Phonetype;
        String subuserrights;
        String IsLockuser;
        String welcomename;
        String PasswordExpiredate;
        String showmessage;
        String City;
        String OTP;
        String Paymentoption;
        String Country;
        String CustPayId;
        String CurrentBalance;
        String CurrentBalancelastest;
        String Nocheckdate;
        String paymentscroll;
        String paymentmessage;

        public InfoBean() {
        }

        public String getCurrentBalancelastest() {
            return CurrentBalancelastest;
        }

        public void setCurrentBalancelastest(String currentBalancelastest) {
            CurrentBalancelastest = currentBalancelastest;
        }

        public String getNocheckdate() {
            return Nocheckdate;
        }

        public void setNocheckdate(String nocheckdate) {
            Nocheckdate = nocheckdate;
        }

        public String getPaymentscroll() {
            return paymentscroll;
        }

        public void setPaymentscroll(String paymentscroll) {
            this.paymentscroll = paymentscroll;
        }

        public String getPaymentmessage() {
            return paymentmessage;
        }

        public void setPaymentmessage(String paymentmessage) {
            this.paymentmessage = paymentmessage;
        }

        public String getIsbackupuser() {
            return isbackupuser;
        }

        public void setIsbackupuser(String isbackupuser) {
            this.isbackupuser = isbackupuser;
        }

        public String getPhonetype() {
            return Phonetype;
        }

        public void setPhonetype(String phonetype) {
            Phonetype = phonetype;
        }

        public String getSubuserrights() {
            return subuserrights;
        }

        public void setSubuserrights(String subuserrights) {
            this.subuserrights = subuserrights;
        }

        public String getIsLockuser() {
            return IsLockuser;
        }

        public void setIsLockuser(String isLockuser) {
            IsLockuser = isLockuser;
        }

        public String getWelcomename() {
            return welcomename;
        }

        public void setWelcomename(String welcomename) {
            this.welcomename = welcomename;
        }

        public String getPasswordExpiredate() {
            return PasswordExpiredate;
        }

        public void setPasswordExpiredate(String passwordExpiredate) {
            PasswordExpiredate = passwordExpiredate;
        }

        public String getShowmessage() {
            return showmessage;
        }

        public void setShowmessage(String showmessage) {
            this.showmessage = showmessage;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getOTP() {
            return OTP;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }

        public String getPaymentoption() {
            return Paymentoption;
        }

        public void setPaymentoption(String paymentoption) {
            Paymentoption = paymentoption;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getCustPayId() {
            return CustPayId;
        }

        public void setCustPayId(String custPayId) {
            CustPayId = custPayId;
        }

        public String getCurrentBalance() {
            return CurrentBalance;
        }

        public void setCurrentBalance(String currentBalance) {
            CurrentBalance = currentBalance;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getReturnmessage() {
            return returnmessage;
        }

        public void setReturnmessage(String returnmessage) {
            this.returnmessage = returnmessage;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getVal1() {
            return val1;
        }

        public void setVal1(String val1) {
            this.val1 = val1;
        }

        public String getGcmstatus() {
            return gcmstatus;
        }

        public void setGcmstatus(String gcmstatus) {
            this.gcmstatus = gcmstatus;
        }

        public String getUsercategory() {
            return usercategory;
        }

        public void setUsercategory(String usercategory) {
            this.usercategory = usercategory;
        }

        public String getBlatlong() {
            return Blatlong;
        }

        public void setBlatlong(String blatlong) {
            Blatlong = blatlong;
        }

        public String getVal2() {
            return val2;
        }

        public void setVal2(String val2) {
            this.val2 = val2;
        }

        public String getVal3() {
            return val3;
        }

        public void setVal3(String val3) {
            this.val3 = val3;
        }

        public String getVal4() {
            return val4;
        }

        public void setVal4(String val4) {
            this.val4 = val4;
        }

        public String getBtmainuserId() {
            return btmainuserId;
        }

        public void setBtmainuserId(String btmainuserId) {
            this.btmainuserId = btmainuserId;
        }

        public String getBtmailpwd() {
            return btmailpwd;
        }

        public void setBtmailpwd(String btmailpwd) {
            this.btmailpwd = btmailpwd;
        }

        public String getBtusername() {
            return btusername;
        }

        public void setBtusername(String btusername) {
            this.btusername = btusername;
        }

        public String getIsHotelUser() {
            return IsHotelUser;
        }

        public void setIsHotelUser(String isHotelUser) {
            IsHotelUser = isHotelUser;
        }

        public String getClientid() {
            return clientid;
        }

        public void setClientid(String clientid) {
            this.clientid = clientid;
        }

        public String getClientsecret() {
            return clientsecret;
        }

        public void setClientsecret(String clientsecret) {
            this.clientsecret = clientsecret;
        }

        public String getMainContactId() {
            return MainContactId;
        }

        public void setMainContactId(String mainContactId) {
            MainContactId = mainContactId;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
