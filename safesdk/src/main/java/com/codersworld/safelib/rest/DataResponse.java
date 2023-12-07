package com.codersworld.safelib.rest;


public interface DataResponse<T> {

    void onSuccess(T response);

    void onFaliure(String error);
}
