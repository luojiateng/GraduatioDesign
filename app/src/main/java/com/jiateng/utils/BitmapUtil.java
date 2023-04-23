package com.jiateng.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

/**
 * 关于图片的工具类压缩等
 */

public class BitmapUtil {

    private static String PHOTO_FILE_NAME = "PMSManagerPhoto";

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public static int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }

    //转换为圆形状的bitmap
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }


    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public static String compressImage(String filePath) {

        //原文件
        File oldFile = new File(filePath);


        //压缩文件路径 照片路径/
        String targetPath = oldFile.getPath();
        int quality = 50;//压缩比例0-100
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比(在上传图片的时候可能你会感觉图片有些模糊可以调节缩放比例到适合)
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 质量压缩Bitmap方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage1(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 以下是bitmap转file(带压缩转换
     *
     * @param context
     * @param bitmap
     * @param kb
     * @return
     */

    /*
     * bitmap转file(带压缩转换 可以自己设定 默认100kb) 上传服务器的时候使用
     */
    public static String bitmapToFileWhithCompress(Context context, Bitmap bitmap, int kb) {
        String sdPath = getDiskCacheDir(context);
        String name = new DateFormat().format("yyyyMMddhhmmss",
                Calendar.getInstance(Locale.CHINA)) + ".jpg";
        String picPath = sdPath + "/" + name;
        File outImage = new File(picPath);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //调用了下面封装好的压缩方法返回已经压缩的bitmap 然后再调用cmpress 输出流把bitmap转走 100不再压缩 因为已经压缩好了
        compressImage(bitmap, kb).compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        //返回一个压缩图片绝对路径
        return picPath;
    }

    /**
     * 获取缓存文件夹的相对路径
     */
    public static String getDiskCacheDir(Context ctx) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = ctx.getExternalCacheDir().getPath();
        } else {
            cachePath = ctx.getCacheDir().getPath();
        }
        return cachePath;
    }

    //无回调有返回值的压缩方法
    public static Bitmap compressImage(Bitmap image, int kb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > kb) { //循环判断如果压缩后图片是否大于设定的kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * file转bitmap(进行压缩,防止内存泄漏)
     */
    public static Bitmap fileToBitmap(String imagePath, int kb) {
        Bitmap bitmap = null;
        try {
            File file = new File(imagePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            FileInputStream fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, options);
            //将bitmap进行压缩防止内存泄漏
            bitmap = compressImage(bitmap, kb);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*
     * bitmap转file(原图转换)
     */
    public static String bitmapToFile(Context context, Bitmap bitmap) {
        String sdPath = getDiskCacheDir(context);
        String name = new DateFormat().format("yyyyMMddhhmmss",
                Calendar.getInstance(Locale.CHINA)) + ".jpg";
        String picPath = sdPath + "/" + name;
        File outImage = new File(picPath);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        //返回一个图片路径
        return picPath;
    }
}

