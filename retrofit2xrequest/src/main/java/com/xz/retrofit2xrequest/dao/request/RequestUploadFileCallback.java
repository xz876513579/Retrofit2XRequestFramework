package com.xz.retrofit2xrequest.dao.request;


/**
 * View层的请求回调<br/>
 * 这里的T应该是UI或者数据库的BEAN
 */
public abstract class RequestUploadFileCallback<T> {
	public abstract void onCompleted(T t);

	public abstract void onError(RequestExceptionInfo e);
}

