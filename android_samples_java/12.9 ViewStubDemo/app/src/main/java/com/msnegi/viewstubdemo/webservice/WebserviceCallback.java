package com.msnegi.viewstubdemo.webservice;

public interface WebserviceCallback {
    void success(Object obj, String message, String URL);

    void error(String message, String URL);
}
