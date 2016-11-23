package com.xz.retrofit2xrequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xz.retrofit2xrequest.dao.request.RequestExceptionInfo;
import com.xz.retrofit2xrequest.dao.request.RequestUICallback;
import com.xz.retrofit2xrequest.dao.request.UserRequest;
import com.xz.retrofit2xrequest.dao.request.responseBean.BaseResqonseBean;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UserRequest().getAllUser(new RequestUICallback<BaseResqonseBean>(){
            @Override
            public void onCompleted(BaseResqonseBean response) {
                Log.i("log", "ui-onCompleted" );

            }

            @Override
            public void onError(RequestExceptionInfo e) {
                Log.i("log", "ui-onError");
            }
        });

    }
}
