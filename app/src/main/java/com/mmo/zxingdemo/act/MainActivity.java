package com.mmo.zxingdemo.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mmo.zxing.android.CaptureActivity;
import com.mmo.zxing.bean.ZxingConfig;
import com.mmo.zxing.common.Constant;
import com.mmo.zxingdemo.R;
import com.mmo.zxingdemo.util.ToolUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final int REQUEST_CODE_SCAN = 6000;
    private Button scand_btn, picter_btn, copy_btn;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView()
    {
        scand_btn = findViewById(R.id.scand_btn);
        picter_btn = findViewById(R.id.picter_btn);
        copy_btn = findViewById(R.id.copy_btn);

        content = findViewById(R.id.content);

        scand_btn.setOnClickListener(this);
        picter_btn.setOnClickListener(this);
        copy_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if (view == scand_btn)
        {

            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            /*ZxingConfig是配置类
             *可以设置是否显示底部布局，闪光灯，相册，
             * 是否播放提示音  震动
             * 设置扫描框颜色等
             * 也可以不传这个参数
             * */
            ZxingConfig config = new ZxingConfig();
            // config.setPlayBeep(false);//是否播放扫描声音 默认为true
            //  config.setShake(false);//是否震动  默认为true
            // config.setDecodeBarCode(false);//是否扫描条形码 默认为true
            //                                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
            //                                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
            //                                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        } else if (view == picter_btn)
        {

            selectPicter();
        } else if (view == copy_btn)
        {

            ToolUtil.copyData(content.getText().toString());
        }
    }

    private void selectPicter()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, 5000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5000)
        {

            if (resultCode == RESULT_OK)
            {
                Bitmap bitmap = BitmapFactory.decodeFile(ToolUtil.getPicFile(data));
                final String codeStr = ToolUtil.BitmapCreate(bitmap);
                content.setText(codeStr);

            }
        } else if (requestCode == REQUEST_CODE_SCAN)
        {
            if (data != null)
            {
                String rlt = data.getStringExtra(Constant.CODED_CONTENT);
                content.setText("扫描结果为：" + rlt);

            }

        }

    }
}
