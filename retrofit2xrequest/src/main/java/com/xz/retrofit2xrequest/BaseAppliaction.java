package com.xz.retrofit2xrequest;

import android.app.Application;

/**
 * Created by DELL on 2016/11/23.
 */

public class BaseAppliaction  extends Application {
    private static  BaseAppliaction application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    /**
     * 获取application 对象
     *
     * @return
     */
    public static BaseAppliaction getApplication() {
        return application;
    }
}
