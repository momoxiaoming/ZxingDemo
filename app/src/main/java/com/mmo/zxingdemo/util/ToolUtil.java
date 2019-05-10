package com.mmo.zxingdemo.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.mmo.zxingdemo.data.DataCenter;


/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/5/9
 *     desc  : new class
 * </pre>
 */
public class ToolUtil
{

    /**
     * 根据相册回调的intent,得到file
     *
     * @param intent
     * @return
     */
    public static String getPicFile(Intent intent) {
        if (intent != null)
        {

            try
            {
                Uri selectedImage = intent.getData(); //获取系统返回的照片的Uri
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = DataCenter.getInstance().getContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);  //获取照片路径
                cursor.close();



                    return picturePath;


            } catch (Exception e)
            {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        }
        return "";
    }
    public static String BitmapCreate(Bitmap scanBitmap) {
        String result = "";
        try {

            LuminanceSource source1 = new PlanarYUVLuminanceSource(rgb2YUV(scanBitmap), scanBitmap.getWidth(),
                    scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(), scanBitmap.getHeight(),true);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source1));
            MultiFormatReader reader1 = new MultiFormatReader();
            Result result1;

            result1 = reader1.decode(binaryBitmap);
            result = result1.getText();
            return result;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return result;
    }

    private static byte[] rgb2YUV(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int len = width * height;
        byte[] yuv = new byte[len * 3 / 2];
        int y, u, v;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = pixels[i * width + j] & 0x00FFFFFF;

                int r = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 16) & 0xFF;

                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

                y = y < 16 ? 16 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                yuv[i * width + j] = (byte) y;

            }
        }
        return yuv;
    }


    public static void copyData(String msg){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) DataCenter.getInstance().getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", msg);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
