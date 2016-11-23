package com.xz.retrofit2xrequest.dao.request;


import com.xz.retrofit2xrequest.BaseAppliaction;
import com.xz.retrofit2xrequest.R;

/**
 * 请求错误
 * @author xiongzhi 2016-11-22
 *
 */
public class RequestExceptionInfo {

	/**
	 * 请求发送成功
	 */
	public final static int REQUEST_SUCCESS = 0;

	/**
	 * 服务器异常
	 */
	public final static int SERVER_ERROR = 1;

	/**
	 * 服务器错误，需要提示消息
	 */
	public final static int SERVER_EXCEPTION_SHOW_MESSAGE = 2;

	/**
	 * 请求发送失败
	 */
	public final static int REQUEST_SEND_ERROR = 3;

	/**
	 * 请求回调数据解析异常
	 */
	public final static int RESPONSE_ANALYSIS_ERROR = 4;

	/**
	 * 上传图片错误
	 */
	public final static int REQUEST_SEND_FIEL_ERROR = 5;

	private int mErrorId;

	private String mErrorMessage;

	public RequestExceptionInfo(){};

	public RequestExceptionInfo(int errorId){setErrorId(errorId);}

	/**
	 * 获取error id
	 * @return
	 */
	public int getErrorId() {
		return mErrorId;
	}

	/**
	 * 获取error id
	 * @return
	 */
	public RequestExceptionInfo setErrorId(int errorId) {
		this.mErrorId = errorId;
		switch (errorId) {
			case REQUEST_SUCCESS:
				setErrorMessage(BaseAppliaction.getApplication().getResources()
						.getString(R.string.request_success_message));
				break;
			case SERVER_ERROR:
				setErrorMessage(BaseAppliaction.getApplication().getResources()
						.getString(R.string.request_server_error_message, SERVER_ERROR));
				break;
			case SERVER_EXCEPTION_SHOW_MESSAGE:
				setErrorMessage(BaseAppliaction.getApplication().getResources()
						.getString(R.string.request_server_exception_message, SERVER_EXCEPTION_SHOW_MESSAGE));
				break;
			case REQUEST_SEND_ERROR:
				setErrorMessage(BaseAppliaction.getApplication().getResources()
						.getString(R.string.request_send_error, REQUEST_SEND_ERROR));
				break;
			case RESPONSE_ANALYSIS_ERROR:
				setErrorMessage(BaseAppliaction.getApplication().getResources()
						.getString(R.string.response_analysis_error, RESPONSE_ANALYSIS_ERROR));
				break;
			case REQUEST_SEND_FIEL_ERROR:
				setErrorMessage(BaseAppliaction.getApplication().getResources()
						.getString(R.string.request_send_file_error, RESPONSE_ANALYSIS_ERROR));
				break;



			default:

				break;
		}
		return this;
	}

	/**
	 * 获取errorMessage
	 * @return
	 */
	public String getErrorMessage() {
		return mErrorMessage;
	}

	/**
	 * 获取errorMessage
	 * @return
	 */
	public RequestExceptionInfo setErrorMessage(String errorMessage) {
		this.mErrorMessage = errorMessage;
		return this;
	}
}
