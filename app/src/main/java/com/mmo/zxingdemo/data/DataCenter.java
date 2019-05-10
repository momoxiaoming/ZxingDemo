package com.mmo.zxingdemo.data;

import android.content.Context;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/9
 *     desc  : new class
 * </pre>
 */
public class DataCenter
{
    private static DataCenter mIntence;

    public static DataCenter getInstance()
    {
        if (null == mIntence)
        {
            synchronized (DataCenter.class)
            {
                if (null == mIntence)
                {
                    mIntence = new DataCenter();
                }
            }
        }
        return mIntence;
    }


    private  Context context;

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
}
