package module.video.jnc.myffmpeg.tool;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static final String APP_ROOT = "sdcard/FFmpeg/";
    public static final String APP_VIDEO = "sdcard/FFmpeg/video/";
    public static final String APP_WATER_MARK = "sdcard/FFmpeg/water_mark/";
    public static final String APP_FILTER = "sdcard/FFmpeg/filter/";
    public static final String APP_CROP = "sdcard/FFmpeg/crop/";
    public static final String APP_SCALE = "sdcard/FFmpeg/scale/";
    public static final String APP_CLIP = "sdcard/FFmpeg/clip/";
    public static final String APP_GIF = "sdcard/FFmpeg/gif/";
    public static final String APP_REVERSE = "sdcard/FFmpeg/reverse/";
    public static final String APP_DUB = "sdcard/FFmpeg/dub/";
    public static String saveFileToWaterMark(String name, byte[] buffer) {
        File file = new File(APP_WATER_MARK);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(APP_WATER_MARK + name);
            fos.write(buffer);
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return APP_WATER_MARK + name;
    }

    public static String saveBitmapToWaterMark(String name, Bitmap bmp) {
        File file = new File(APP_WATER_MARK);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(APP_WATER_MARK + name);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return APP_WATER_MARK + name;
    }

    public static void makeWaterDir() {
        File file = new File(APP_WATER_MARK);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void makeFilterDir() {
        File file = new File(APP_FILTER);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void makeCropDir() {
        File file = new File(APP_CROP);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void makeScaleDir() {
        File file = new File(APP_SCALE);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void makeClipDir() {
        File file = new File(APP_CLIP);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void makeGifDir() {
        File file = new File(APP_GIF);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public static void makeReverse() {
        File file = new File(APP_REVERSE);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void makeDubDir() {
        File file = new File(APP_DUB);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}