package com.msnegi.websearchretrofit.web;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msnegi.websearchretrofit.R;
import com.msnegi.websearchretrofit.tools.Constants;
import com.msnegi.websearchretrofit.tools.Utilities;
import com.msnegi.websearchretrofit.views.ProgressDialog;
import com.msnegi.websearchretrofit.web.ServiceCallback;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiUtils {

    private ProgressDialog progressDialog;
    Call<ServiceResponse> apiService = null;
    private boolean isAsync = false;
    private boolean canCancel = true;
    public long startTime = 0;

    public ApiUtils(){}

    public ApiUtils setAsync(boolean async) {
        isAsync = async;
        return this;
    }

    public ApiUtils setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
        return this;
    }

    public static APIServices getAPIService(){
        return RetrofitClient.getClient(Constants.BASE_URL).create(APIServices.class);
    }

    public void callService(final Context context, final APIServices.ServiceType serviceType, HashMap<String, String> params, final ServiceCallback serviceCallback){
        //params.put("customer_id", Constants.user != null ? Constants.user.getUid() : "");
        //params.put("device_type", Constants.DEVICE_TYPE);
        //params.put("region", Utilities.getUserLocation(context));

        switch (serviceType){
            case CREATE_USER:
                apiService = getAPIService().storeUserRegistrationPro(params);
                break;
            case LOGIN:
                apiService = getAPIService().getStoreUserAccessPro(params);
                break;
            case CHANGE_PASSWORD:
                apiService = getAPIService().storeUserChangePasswordPro(params);
                break;
            default:
                return;
        }

        final String url = apiService.request().url().url().toString();
        final Request request = apiService.request();

        Utilities.ShowMessages(request.body().toString());
        Utilities.ShowMessages(url);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setCancelable(canCancel);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(canCancel && keyCode == KeyEvent.KEYCODE_BACK){
                    progressDialog.setCanceled(true);
                    progressDialog.dismiss();
                    apiService.cancel();
                    serviceCallback.cancel();
                }
                return false;
            }
        });

        if(!isAsync)
            progressDialog.show();

        startTime = System.currentTimeMillis();

        apiService.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {

                long elapsedTime = System.currentTimeMillis() - startTime;
                //System.out.println("Total elapsed http request/response time in milliseconds: " + elapsedTime);
                //saveLog(url, elapsedTime);

                try {
                    if (progressDialog != null)
                        progressDialog.cancel();
                    if (response.isSuccessful() && response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("ok") && response.body().getData() != null) {
                                serviceCallback.success(serviceType, getObject(response.body().getData()), "");
                            } else {
                                serviceCallback.fail(serviceType, response.body().getMsg());
                            }
                        } else {
                            serviceCallback.fail(serviceType, context.getString(R.string.parse_error));
                        }
                    } else {
                        serviceCallback.fail(serviceType, context.getString(R.string.server_error));
                    }
                }catch (Exception e){
                    serviceCallback.fail(serviceType, context.getString(R.string.parse_error));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable throwable) {
                if(progressDialog != null && !progressDialog.isCanceled()) {
                    progressDialog.dismiss();
                    serviceCallback.fail(serviceType, context.getString(R.string.internet_error));
                }
            }
        });
    }

    public void callServiceObject(final Context context, final APIServices.ServiceType serviceType, HashMap<String, Object> params, final ServiceCallback serviceCallback){
        //params.put("customer_id", Constants.user != null ? Constants.user.getUid() : "");
        //params.put("device_type", Constants.DEVICE_TYPE);
        //params.put("region", Utilities.getUserLocation(context));

        switch (serviceType){
            case PRODUCT_LIST:
                //apiService = getAPIService().productlist(params);
                break;
            default:
                return;
        }

        final String url = apiService.request().url().url().toString();
        final Request request = apiService.request();

        Utilities.ShowMessages(request.body().toString());
        Utilities.ShowMessages(url);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setCancelable(canCancel);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(canCancel && keyCode == KeyEvent.KEYCODE_BACK){
                    progressDialog.setCanceled(true);
                    progressDialog.dismiss();
                    apiService.cancel();
                    serviceCallback.cancel();
                }
                return false;
            }
        });

        if(!isAsync)
            progressDialog.show();

        apiService.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if(progressDialog != null)
                    progressDialog.cancel();
                if(response.isSuccessful() && response.code() == 200){
                    if(response.body() != null){
                        if(response.body().getStatus().equalsIgnoreCase("ok") && response.body().getData() != null) {
                            serviceCallback.success(serviceType, getObject(response.body().getData()), "");
                        }else {
                            serviceCallback.fail(serviceType, response.body().getMsg());
                        }
                    }else {
                        serviceCallback.fail(serviceType, context.getString(R.string.parse_error));
                    }
                }else {
                    serviceCallback.fail(serviceType, context.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable throwable) {
                if(progressDialog != null && !progressDialog.isCanceled()) {
                    progressDialog.dismiss();
                    serviceCallback.fail(serviceType, context.getString(R.string.internet_error));
                }
            }
        });
    }

    public void callServiceWithFile(final Context context, final APIServices.ServiceType serviceType, HashMap<String, String> params, List<File> files, final ServiceCallback serviceCallback){
      /*  switch (serviceType){
                List<MultipartBody.Part> mulPartList = new ArrayList<>();
                for(File file : files){
                    mulPartList.add(MultipartBody.Part.createFormData("attachment[" + files.indexOf(file) + "]", file.getName(),
                            new ProgressRequestBody(file, new ProgressRequestBody.UploadCallbacks() {
                                @Override
                                public void onProgressUpdate(int percentage) {
                                    System.out.println(percentage + "-------------");
                                }

                                @Override
                                public void onError() {

                                }

                                @Override
                                public void onFinish() {
                                    System.out.println("Finish -------------");
                                }
                            })));
                }
                apiService = getAPIService().saveForm(params.get("form_id"), params.get("form_type"), params.get("user_id"), params.get("project_name"),
                        params.get("data"), mulPartList);
                break;
            default:
                return;
        }*/

        final String url = apiService.request().url().url().toString();
        Request request = apiService.request();

        Utilities.ShowMessages(url);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                apiService.cancel();
                serviceCallback.cancel();
                progressDialog.dismiss();
            }
        });
        progressDialog.show();

        apiService.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if(progressDialog != null)
                    progressDialog.cancel();
                if(response.isSuccessful() && response.code() == 200){
                    if(response.body() != null){
                        if(response.body().getStatus().equalsIgnoreCase("ok") && response.body().getData() != null) {
                            serviceCallback.success(serviceType, response.body().getData(), "");
                        }else {
                            serviceCallback.success(serviceType, response.body().getData(), response.body().getMsg());
                        }
                    }else {
                        serviceCallback.fail(serviceType, context.getString(R.string.parse_error));
                    }
                }else {
                    serviceCallback.fail(serviceType, context.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable throwable) {
                if(progressDialog != null)
                    progressDialog.dismiss();
                serviceCallback.fail(serviceType, context.getString(R.string.internet_error));
            }
        });
    }

    public Object getObject(Object object) {
        Object objectToSend = new Object();
        try {
            if (object == null)
                return null;
            Gson gson =  new GsonBuilder().serializeNulls().create();
            String gsonString = gson.toJson(object);
            if (object instanceof ArrayList) {
                List<Object> objects = new ArrayList<>();
                for (Object objectA : (ArrayList) object) {
                    gsonString = gson.toJson(objectA);
                    objectToSend = new JSONObject(gsonString);
                    objects.add(objectToSend);
                }
                return objects;
            } else {
                //objectToSend = gson.fromJson(gsonString, type);
                if(gsonString.startsWith("{"))
                    objectToSend = new JSONObject(gsonString);
                else {
                    gsonString = gsonString.replaceAll("\"", "");
                    return gsonString;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return objectToSend;
    }

    public void saveLog(String url, long elapsedTime) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "CelloLog");
            if (!root.exists()) {
                root.mkdirs();
            }

            File gpxfile = new File(root, "ResponseTime.txt");
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(url+ " : " + (elapsedTime/1000)+ "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*public void getProfile(final Context context, int type, APIServices.ServiceType serviceType, String userId, final ServiceCallback serviceCallback) {
        switch (serviceType) {
            case GET_USER_BY_ID:
                apiService = getAPIService().getUserById(userId);
                break;
            default:
                return;
        }

        final String url = apiService.request().url().url().toString();
        *//*final Request request = apiService.request();

        Utilities.ShowMessages(request.body().toString());
        Utilities.ShowMessages(url);*//*

        serviceCallback.loading(context.getString(R.string.loading_started));
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setCancelable(canCancel);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (canCancel && keyCode == KeyEvent.KEYCODE_BACK) {
                    progressDialog.setCanceled(true);
                    progressDialog.dismiss();
                    apiService.cancel();
                    serviceCallback.cancel();
                }
                return false;
            }
        });

        if (!isAsync)
            progressDialog.show();

        if (!Utilities.isInternetAvailable(context)) {
            serviceCallback.fail(url, context.getString(R.string.no_internet_error));
            return;
        }

        apiService.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                try {
                    if (progressDialog != null)
                        progressDialog.cancel();
                    if (response.isSuccessful() && response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("ok") && response.body().getData() != null) {
                                serviceCallback.success(type, getObject(response.body().getData()), response.body().getMsg());
                            } else if (response.body().getStatus().equalsIgnoreCase("prompt")) {
                                serviceCallback.success(type, getObject(response.body().getData()), response.body().getStatus() + "#" + response.body().getMsg());
                            } else {
                                serviceCallback.fail(url, response.body().getMsg());
                            }
                        } else {
                            serviceCallback.fail(url, context.getString(R.string.parse_error));
                        }
                    } else {
                        serviceCallback.fail(url, context.getString(R.string.server_error));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Utilities.ShowMessages(e.getMessage());
                    serviceCallback.fail(url, context.getString(R.string.internet_error));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable throwable) {
                if (progressDialog != null && !progressDialog.isCanceled()) {
                    progressDialog.dismiss();
                    serviceCallback.fail(url, context.getString(R.string.internet_error));
                }
            }
        });
    }*/

    /*

    public void callServiceWithFile(final Context context, int type, HashMap<String, String> params, List<File> files, final ServiceCallback serviceCallback){

        HashMap<String, RequestBody> partParams = new HashMap<>();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        for(Map.Entry<String, String> entry : params.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
            partParams.put(entry.getKey(), RequestBody.create(MediaType.get("text/plain"), entry.getValue()));
        }

        for(File file : files)
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.get("image/*"), file));

        apiService = getAPIService().saveUserProfile(partParams, MultipartBody.Part.createFormData("file", files.get(0).getName(), RequestBody.create(MediaType.get("image/*"), files.get(0))));
        final String url = apiService.request().url().url().toString();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                apiService.cancel();
                serviceCallback.cancel();
                progressDialog.dismiss();
            }
        });
        progressDialog.show();

        apiService.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                try {
                    if (progressDialog != null)
                        progressDialog.cancel();
                    if (response.isSuccessful() && response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("ok") && response.body().getData() != null) {
                                serviceCallback.success(type, getObject(response.body().getData()), response.body().getMsg());
                            } else if (response.body().getStatus().equalsIgnoreCase("prompt")) {
                                serviceCallback.success(type, getObject(response.body().getData()), response.body().getStatus() + "#" + response.body().getMsg());
                            } else {
                                serviceCallback.fail(url, response.body().getMsg());
                            }
                        } else {
                            serviceCallback.fail(url, context.getString(R.string.parse_error));
                        }
                    } else {
                        serviceCallback.fail(url, context.getString(R.string.server_error));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Utilities.ShowMessages(e.getMessage());
                    serviceCallback.fail(url, context.getString(R.string.internet_error));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable throwable) {
                if (progressDialog != null && !progressDialog.isCanceled()) {
                    progressDialog.dismiss();
                    serviceCallback.fail(url, context.getString(R.string.internet_error));
                }
            }
        });
    }
    */

    /*HashMap<String, String> params = new HashMap<>();
                    params.put(ProfilePresenter.serviceParams.FirstName.name(), firstName);
                    params.put(ProfilePresenter.serviceParams.LastName.name(), lastName);
                    params.put(ProfilePresenter.serviceParams.PhoneNo.name(), phoneNo);
                    params.put(ProfilePresenter.serviceParams.UserId.name(), userId);

    List<File> files = new ArrayList<File>();
    if (!file.isEmpty()) {
        //params.put(ProfilePresenter.serviceParams.file.name(), file);
        files.add(new File(file));
    }

    getRemoteServices().callServiceWithFile(getContext(), type.ordinal(), params, files, new ServiceCallback()  {*/

}
