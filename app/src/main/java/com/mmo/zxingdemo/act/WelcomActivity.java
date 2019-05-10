package com.mmo.zxingdemo.act;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.mmo.zxingdemo.R;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/9
 *     desc  : new class
 * </pre>
 */
public class WelcomActivity extends BasePermissionActivity
{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_activity);

        String[] permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermission(permission, 0x0001);
    }

    @Override
    public void permissionSuccess(int requestCode)
    {
        if (requestCode == 0x0001)
        {

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                   startActivity(new Intent(WelcomActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    @Override
    public void permissionFail(int requestCode)
    {

    }
}
