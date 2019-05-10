package com.mmo.zxingdemo.act;

import android.app.Application;

import com.mmo.zxingdemo.data.DataCenter;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/9
 *     desc  : new class
 * </pre>
 */
public class QrcodeApplication extends Application
{


    @Override
    public void onCreate()
    {
        super.onCreate();

        DataCenter.getInstance().setContext(this);

    }
}
