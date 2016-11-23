package com.xz.retrofit2xrequest.dao.request;

/**
 * 数据访问层请求回调<br/>
 * 这里的T应该是com.shishi.android.models
 */
public abstract class RequestCallback<T> {
	public abstract void onCompleted(T t);

	public abstract void onError(RequestExceptionInfo e);
}

