package com.xz.retrofit2xrequest.dao.request.retrofit;

import com.google.gson.JsonObject;
import com.xz.retrofit2xrequest.dao.request.URLs;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 针对Retrofit请求的封装
 */
public class RetrofitRequest {

    private static RetrofitRequest mInstance;

    private RetrofitRequest() {}

    public static RetrofitRequest getInstance() {
        // TODO 是否要写出单例还需要查看API文档，判断单例情况下高并发情况
        if (null == mInstance) {
            mInstance = new RetrofitRequest();
        }
        return mInstance;
    }

    /**
     * 请求超时时间
     */
    private final int DEFAULT_TIMEOUT = 5;

    /**
     * RetrofitRequest 内部使用，不对外开放
     */
    private final String REQUEST_TYPE_GET = "get";
    private final String REQUEST_TYPE_POST = "post";
    private final String REQUEST_TYPE_PUT = "put";
    private final String REQUEST_TYPE_DELETE = "delete";

    private Retrofit mRetrofit;
    private RetrofitRequesService mRetrofitRequesService;

    private Retrofit getRetrofit(String baseURL) {
        if (null == mRetrofit) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            mRetrofit = new Retrofit.Builder()
                    .client(httpClientBuilder.build()).baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return mRetrofit;
    }

    public RetrofitRequesService getRetrofitRequesService() {
        if (mRetrofitRequesService == null) {
            mRetrofitRequesService = getRetrofit(URLs.BASE_URL).create(
                    RetrofitRequesService.class);
        }
        return mRetrofitRequesService;
    }

    private void baseRequest(String url, Map<String, String> params,
                             String requestType, final RetrofitRequestCallback callBack) {

        Call<JsonObject> call = null;

        switch (requestType) {
            case REQUEST_TYPE_GET:
                call = getRetrofitRequesService().getRequest(url, params);
                break;
            case REQUEST_TYPE_POST:
                call = getRetrofitRequesService().postRequest(url, params);
                break;
            case REQUEST_TYPE_PUT:
                call = getRetrofitRequesService().putRequest(url, params);
                break;
            case REQUEST_TYPE_DELETE:
                call = getRetrofitRequesService().deleteRequest(url, params);
                break;
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call,
                                   Response<JsonObject> response) {
                baseResponse(callBack, call, response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable e) {
                baseFailure(callBack, call, e);
            }
        });
    }

    private void baseUploadFile(String url, Map<String, String> params, Map<String, RequestBody> fiels, final RetrofitRequestCallback callBack) {
        Call<JsonObject> call = getRetrofitRequesService().postUploadFiles(url, params, fiels);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call,
                                   Response<JsonObject> response) {
                if (callBack != null) {
                    callBack.onCompleted(response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
            }
        });

    }


    private void baseResponse(RetrofitRequestCallback callBack,
                              Call<JsonObject> call, Response<JsonObject> response) {
        callBack.onCompleted(response);
    }

    private void baseFailure(RetrofitRequestCallback callBack,
                             Call<JsonObject> call, Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    public void get(String url, Map<String, String> params,
                    final RetrofitRequestCallback callBack) {

        baseRequest(url, params, REQUEST_TYPE_GET, callBack);
    }

    public void post(String url, Map<String, String> params,
                     final RetrofitRequestCallback callBack) {

        baseRequest(url, params, REQUEST_TYPE_POST, callBack);
    }

    public void put(String url, Map<String, String> params,
                    final RetrofitRequestCallback callBack) {

        baseRequest(url, params, REQUEST_TYPE_PUT, callBack);
    }

    public void delete(String url, Map<String, String> params,
                       final RetrofitRequestCallback callBack) {

        baseRequest(url, params, REQUEST_TYPE_DELETE, callBack);
    }

    /**
     * 文件上传
     *
     * @param url
     * @param params
     * @param files
     * @param callBack
     */
    public void postFiles(String url, Map<String, String> params, Map<String, RequestBody> files, RetrofitRequestCallback callBack) {
        baseUploadFile(url, params, files, callBack);
    }

    public interface RetrofitRequesService {

        @GET()
        Call<JsonObject> getRequest(@Url String url, @QueryMap Map<String, String> params);

        @POST()
        Call<JsonObject> postRequest(@Url String url, @QueryMap Map<String, String> params);

        @PUT()
        Call<JsonObject> putRequest(@Url String url, @QueryMap Map<String, String> params);

        @DELETE()
        Call<JsonObject> deleteRequest(@Url String url, @QueryMap Map<String, String> params);

        @Multipart
        @POST()
        Call<JsonObject> postUploadFiles(@Url String url
                , @QueryMap Map<String, String> params
                , @PartMap() Map<String, RequestBody> files);
    }

    /**
     * 请求回调
     */
    public static abstract class RetrofitRequestCallback {

        public abstract void onCompleted(Response<JsonObject> response);

        public abstract void onError(Throwable e);
    }
}
