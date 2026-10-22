package com.msnegi.viewstubdemo.webservice

import com.msnegi.viewstubdemo.web.APIServices

public interface ServiceCallback {
    fun success(serviceType: APIServices.ServiceType, result: Any, msg: String)
    fun fail(serviceType: APIServices.ServiceType, msg: String)
    fun cancel()
}
