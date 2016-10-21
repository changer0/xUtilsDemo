package com.lulu.xutilsdemo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Lulu on 2016/10/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化 xUtils 3
        x.Ext.init(this);
    }
}
