package com.msnegi.websearchretrofit.web;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.*;

public interface APIServices {

    enum ServiceType{
        CREATE_USER,
        LOGIN,
        CHANGE_PASSWORD,
        FORGOT_PASSWORD,
        UPDATE_PROFILE,
        HOME_PAGE,
        NAVIGATION,
        PRODUCT_DETAIL,
        PRODUCT_LIST,
        ADD_TO_WISHLIST,
        REMOVE_FROM_WISHLIST,
        GET_WISHLIST,
        CLEAR_WISHLIST,
        SEARCH_PRODUCT_LIST,
        NOTIFICATIONS,
        SOCIAL_LOGIN,
        AUTO_SEARCH
    }

    @POST("DProBaseService/StoreUserRegistrationPro")
    Call<ServiceResponse> storeUserRegistrationPro(@Body() HashMap<String, String> params);

    @POST("DProBaseService/GetStoreUserAccessPro")
    Call<ServiceResponse> getStoreUserAccessPro(@Body() HashMap<String, String> params);

    @POST("DProBaseService/StoreUserChangePasswordPro")
    Call<ServiceResponse> storeUserChangePasswordPro(@Body() HashMap<String, String> params);

}
