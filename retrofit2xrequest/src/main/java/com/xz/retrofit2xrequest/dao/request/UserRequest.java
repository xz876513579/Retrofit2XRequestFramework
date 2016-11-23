package com.xz.retrofit2xrequest.dao.request;

import android.util.Log;

import com.xz.retrofit2xrequest.dao.request.responseBean.BaseResqonseBean;

import java.util.HashMap;
import java.util.Map;

public class UserRequest {
	public void getAllUser(final RequestUICallback<BaseResqonseBean> callback){
		Map<String, String> params = new HashMap<String, String>();
//		添加URL或POST的BODY数据
//		params.put("xxxxx", companyUuid);
//		params.put("xxxxx", index);
		new BaseRequest().get(URLs.GET_ALL_USERS,
				params, new RequestCallback<BaseResqonseBean>() {
					@Override
					public void onCompleted(BaseResqonseBean response) {
						Log.i("log", "response-onCompleted" );
						//根据需求转换成UI需要的数据格式
						//callback.onCompleted(response);
					}

					@Override
					public void onError(RequestExceptionInfo e) {
						Log.i("log", "response-onError");
						callback.onError(e);
					}
				});
	}
}
