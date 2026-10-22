package com.msnegi.websearchretrofit.web;

import com.msnegi.websearchretrofit.web.APIServices;

public interface ServiceCallback {
    public void  success(APIServices.ServiceType serviceType,Object result, String msg);
    public void  fail(APIServices.ServiceType serviceType, String msg);
    public void  cancel();
}
