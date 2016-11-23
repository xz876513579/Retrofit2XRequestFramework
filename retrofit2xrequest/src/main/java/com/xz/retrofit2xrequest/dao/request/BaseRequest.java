package com.xz.retrofit2xrequest.dao.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xz.retrofit2xrequest.dao.request.retrofit.RetrofitRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class BaseRequest {

	/**
	 * RetrofitRequest 内部使用，不对外开放
	 */
	private final String REQUEST_TYPE_GET = "get";
	private final String REQUEST_TYPE_POST = "post";
	private final String REQUEST_TYPE_PUT = "put";
	private final String REQUEST_TYPE_DELETE = "delete";

	/**
	 * 一般资源请求
	 * @param url
	 * @param params
	 * @param requestType
	 * @param callBack
     */
	private void baseRequest(String url, Map<String, String> params,
			String requestType, final RequestCallback callBack) {
	
		RetrofitRequest.RetrofitRequestCallback retrofitRequestCallback = new RetrofitRequest.RetrofitRequestCallback(){
			@Override
			public void onCompleted(Response<JsonObject> response) {
				if (response.code() == 200 && response.body() != null) {
					if (callBack != null) {
						try{
							// 获取RetrofitRequestCallback的T类型
							Type mySuperClass = callBack.getClass().getGenericSuperclass();
							Type type = ((ParameterizedType) mySuperClass)
									.getActualTypeArguments()[0];
							// 根据RetrofitRequestCallback的T类型解析并进行回调
							callBack.onCompleted(new Gson().fromJson(response.body(), type));
						}catch(Exception e){
							e.printStackTrace();
							onError(new RequestExceptionInfo(RequestExceptionInfo.RESPONSE_ANALYSIS_ERROR));
						}
						// TODO 添加对msg的自动提示
					}
				} else {
					// TODO 加入对4xx 5xx 的处理
				}
			}

			@Override
			public void onError(Throwable e) {
				onError(new RequestExceptionInfo(RequestExceptionInfo.REQUEST_SEND_ERROR));
			}
			
			public void onError(RequestExceptionInfo e){
				if(callBack != null){
					callBack.onError(e);
				}
			}
		};
		switch (requestType) {
		case REQUEST_TYPE_GET:
			RetrofitRequest.getInstance().get(url, params, retrofitRequestCallback);
			break;
		case REQUEST_TYPE_POST:
			RetrofitRequest.getInstance().post(url, params, retrofitRequestCallback);
			break;
		case REQUEST_TYPE_PUT:
			RetrofitRequest.getInstance().put(url, params, retrofitRequestCallback);
			break;
		case REQUEST_TYPE_DELETE:
			RetrofitRequest.getInstance().delete(url, params, retrofitRequestCallback);
			break;
		}
	}

	/**
	 * 发送资源文件
	 * @param url
	 * @param params
	 * @param fielPathList
	 * @param callBack
     */
	public void basePostUploadFiles(String url,Map<String,String> params,List<String> fielPathList,final RequestUploadFileCallback callBack){
		RetrofitRequest.RetrofitUploadFileCallback retrofitRequestCallback = new RetrofitRequest.RetrofitUploadFileCallback(){
			@Override
			public void onCompleted(Response<JsonObject> response) {
				if (response.code() == 200 && response.body() != null) {
					if (callBack != null) {
						try{
							// 获取RetrofitRequestCallback的T类型
							Type mySuperClass = callBack.getClass().getGenericSuperclass();
							Type type = ((ParameterizedType) mySuperClass)
									.getActualTypeArguments()[0];
							// 根据RetrofitRequestCallback的T类型解析并进行回调
							callBack.onCompleted(new Gson().fromJson(response.body(), type));
						}catch(Exception e){
							e.printStackTrace();
							onError(new RequestExceptionInfo(RequestExceptionInfo.RESPONSE_ANALYSIS_ERROR));
						}
						// TODO 添加对msg的自动提示
					}
				}else{
					onError(new RequestExceptionInfo(RequestExceptionInfo.REQUEST_SEND_FIEL_ERROR));
				}
			}

			@Override
			public void onError(Throwable e) {
				onError(new RequestExceptionInfo(RequestExceptionInfo.REQUEST_SEND_FIEL_ERROR));
			}
			
			public void onError(RequestExceptionInfo e){
				if(null != callBack){
					callBack.onError(e);
				}
			}
		};
		
		Map<String,RequestBody> filemap = new HashMap<String, RequestBody>();
		
		for(String fielpath:fielPathList){
			//创建 RequestBody，用于封装 请求RequestBody
			RequestBody requestFile =  RequestBody.create(MediaType.parse("multipart/form-data"), fielpath);
			//MultipartBody.Part is used to send also the actual file name
			//MultipartBody.Part body =   MultipartBody.Part.createFormData("image", "aaaa123.png", requestFile);
			//添加描述
			//String descriptionString = "hello, 这是文件描述";
			//RequestBody description =  RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
			filemap.put(fielpath, requestFile);
		}
		RetrofitRequest.getInstance().postFiles(url, params, filemap, retrofitRequestCallback);
	}
	
	
	public void get(String url, Map<String, String> params,
			final RequestCallback callBack) {
		baseRequest(url, params, REQUEST_TYPE_GET, callBack);
	}

	public void post(String url, Map<String, String> params,
			final RequestCallback callBack) {
		baseRequest(url, params, REQUEST_TYPE_POST, callBack);
	}

	public void put(String url, Map<String, String> params,
			final RequestCallback callBack) {
		baseRequest(url, params, REQUEST_TYPE_PUT, callBack);
	}

	public void delete(String url, Map<String, String> params,
			final RequestCallback callBack) {
		baseRequest(url, params, REQUEST_TYPE_DELETE, callBack);
	}
	
	public void postUploadFiles(String url,Map<String,String> params,List<String> fielPathList,RequestUploadFileCallback callback){
		basePostUploadFiles(url, params, fielPathList, callback);
	}
}
