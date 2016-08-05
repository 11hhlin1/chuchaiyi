package com.gjj.applibrary.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;


import com.gjj.applibrary.app.AppLib;
import com.gjj.applibrary.log.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Util {
    public static final long TIME_MINUTE_MILLIS = 60 * 1000;
    public static final long TIME_HOUR_MILLIS = 60 * TIME_MINUTE_MILLIS;
    public static final long TIME_DAY_MILLIS = 24 * TIME_HOUR_MILLIS;
    public static final long TIME_WEEK_MILLIS = 7 * TIME_DAY_MILLIS;
    public static final long TIME_YEAR_MILLIS = 365 * TIME_DAY_MILLIS;

    private static String MM_DD_FORMAT1_STR;
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_MM_DD_FORMAT1;
    public static String M_D_FORMAT1_STR;
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_M_D_FORMAT1;

    private static final String YYYY_MM_DD_FORMAT1_STR = "yyyy.MM.dd";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_YYYY_MM_DD_FORMAT1;
    private static final String YYYY_M_D_FORMAT1_STR = "yyyy.M.d";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_YYYY_M_D_FORMAT1;

    private static final String YYYY_MM_DD_FORMAT2_STR = "yyyy-MM-dd";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_YYYY_MM_DD_FORMAT2;

    private static final String MM_DD_FORMAT_STR = "MM.dd";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_MM_DD_FORMAT;
    private static final String M_D_FORMAT_STR = "M.d";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_M_D_FORMAT;

    private static final String YYYY_MM_DD_HH_MM_SS_FORMAT_STR = "yyyyMMddHHmmss";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT;

    private static final String TIME_24_FORMAT_STR = "HH:mm";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_TIME_24_FORMAT;

    private static final String YYYY_MM_DD_HH_MM_SS_FORMAT_FORMAT_FOR_SERVER_STR = "yyyy-MM-dd HH:mm:ss";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER;

    private static final String YYYY_MM_DD_HH_MM_SS_FORMAT_FORMAT_FOR_SERVER_STR_2 = "yyyy.MM.dd HH:mm:ss";
    private static volatile ThreadLocal<DateFormat> THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER_2;
    private static volatile Pattern PATTERN_PLUS;

    private final static DecimalFormat ONE_DECIMAL_POINT_DF = new DecimalFormat("0.0");
    private final static DecimalFormat TWO_DECIMAL_POINT_DF = new DecimalFormat("0.00");
    private static final DecimalFormat sDecimalFormat2 = new DecimalFormat("#");
    private static volatile ThreadLocal<StringBuilder> threadSafeStrBuilder;

    private static volatile ThreadLocal<byte[]> threadSafeByteBuf;

    private static volatile DateFormat DATE_FORMAT;

    private static int mScreenWidth = -1;
    private static int mScreenHeight = -1;
    private static float mDensity = -1;

    public static Animation getBottomSlideInAnim() {
        Animation bottomSlideInAnim = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0);
        bottomSlideInAnim.setDuration(150);
        return bottomSlideInAnim;
    }

    public static Animation getBottomSlideOutAnim() {
        Animation bottomSlideOutAnim = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1);
        // ANIM_BOTTOM_SLIDE_OUT.setInterpolator(new AnticipateOvershootInterpolator());
        bottomSlideOutAnim.setDuration(150);
        return bottomSlideOutAnim;
    }

    public static File getImageFilesDir(Context context) {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/image");
        if (file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            L.e(e);
        }

        return file;
    }

    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     *
     *             public static byte[] readStream(InputStream inStream) throws Exception { ByteArrayOutputStream outSteam = new
     *             ByteArrayOutputStream(); byte[] buffer = new byte[1024]; int len = -1;
     *
     *             while ((len = inStream.read(buffer)) != -1) { outSteam.write(buffer, 0, len); }
     *
     *             outSteam.close(); inStream.close(); return outSteam.toByteArray(); }
     */

    /**
     * url编码
     *
     * @param s url字符串
     * @return 字节数组
     * @throws Exception
     */
    public static String urlEncode(String s) {
        try {
            return java.net.URLEncoder.encode(s, "utf-8");

        } catch (Exception e) {
            L.w(e);
            return s;
        }
    }

    public static float dp2px(float dp) {
        final float scale = AppLib.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(float sp) {
        final float scale = AppLib.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 从ImputStream里读取数字
     *
     * @param is InputStream
     * @return 读取的数字
     **/
    public static int readNumberFromInputStream(InputStream is) {
        try {
            byte[] buffer = new byte[8];
            int len = is.read(buffer);
            StringBuilder sb = getThreadSafeStringBuilder();
            int i = 0;

            while (i < len && buffer[i] != 0x0a && buffer[i] != 0x0d) {
                sb.append((char) buffer[i]);
                ++i;
            }

            return Integer.parseInt(sb.toString());

        } catch (Exception e) {
            L.w(e);
        }

        return 0;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context Context
     * @return 宽x高
     **/
    public static String getScreenResolution(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth() + "x" + display.getHeight();
    }

    @Deprecated
    /**
     * 此方法获取md5有错误，为了兼容前面版本，不能修改。
     * 小编推荐：NativeUtil.getMd5(str);
     */
    public static String getMD5String(byte[] bytes) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte md5Data[] = md.digest();
            StringBuilder hexString = getThreadSafeStringBuilder();

            for (int i = 0; i < md5Data.length; ++i)
                hexString.append(Integer.toHexString(0xFF & md5Data[i]));

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            L.w(e);
        }

        return null;
    }

    /**
     * 获取线程安全的StringBuilder
     **/
    public static StringBuilder getThreadSafeStringBuilder() {
        if (threadSafeStrBuilder == null) {
            synchronized (Util.class) {
                if (threadSafeStrBuilder == null) {
                    threadSafeStrBuilder = new ThreadLocal<StringBuilder>();
                }
            }
        }

        StringBuilder sb = threadSafeStrBuilder.get();

        if (sb == null) {
            sb = new StringBuilder();
            threadSafeStrBuilder.set(sb);
        }

        sb.delete(0, sb.length());
        return sb;
    }

    public static byte[] getThreadSafeByteBuffer() {
        if (threadSafeByteBuf == null) {
            synchronized (Util.class) {
                if (threadSafeByteBuf == null) {
                    threadSafeByteBuf = new ThreadLocal<byte[]>();
                }
            }
        }

        byte[] buf = threadSafeByteBuf.get();

        if (buf == null) {
            buf = new byte[1024 * 4]; // 4kb
            threadSafeByteBuf.set(buf);
        }

        return buf;
    }

    public static void showKeyboard(Context ctx) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void showKeyboard(Context ctx, View token) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(token, InputMethodManager.SHOW_IMPLICIT);
    }

    /*
     * public static boolean hideKeyboard(Context ctx) { InputMethodManager imm = (InputMethodManager)
     * ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
     * 
     * if (imm.isActive()) { imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); return true; }
     * 
     * return false; }
     */

    public static boolean hideKeyboard(Context ctx, IBinder binder) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive()) {
            return imm.hideSoftInputFromWindow(binder, 0);
        }

        return false;
    }

    private final static Pattern singleQuotePattern = Pattern.compile("'");

    public static String escapeDBSingleQuotes(String s) {
        if (s == null)
            return null;

        return singleQuotePattern.matcher(s).replaceAll("\''");
    }

    /**
     * 检测sd卡是否已经挂载
     **/
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 递归删除文件
     *
     * @param dir           需要删除的文件夹
     * @param includingSelf 自身是否删除
     * @return 删除的文件数
     **/
    // return disk space revoked
    public static long deleteFilesRecursively(File dir, boolean includingSelf) {
        long length = 0;

        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteFilesRecursively(f, true);
                    } else {
                        length += f.length();
                        f.delete();
                    }
                }
            }
        }

        if (includingSelf) {
            dir.delete();
        }

        return length;
    }

    /**
     * 检测app是不是在运行
     *
     * @param context Context
     * @param appName app name
     * @return true or false
     **/
    public static boolean isAppRunning(Context context, String appName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo info : activityManager.getRunningAppProcesses()) {
            if (info.processName.equals(appName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检测service是不是在运行
     *
     * @param context     Context
     * @param serviceName service name
     * @return true or false
     **/
    public static boolean isServiceRunning(Context context, String serviceName) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            for (ActivityManager.RunningServiceInfo info : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceName.equals(info.service.getClassName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            L.w(e);
        }

        return false;
    }

    /**
     * 格式化时间，例如2004.12.16 hh.mm.ss
     *
     * @param timeMillis 时间戳
     * @return 格式化后的字符串
     **/
    public static String formatTimeDateFull(long timeMillis) {
        if (THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER_2 == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER_2 == null) {
                    THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER_2 = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER_2.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_FORMAT_FORMAT_FOR_SERVER_STR_2);
            THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER_2.set(df);
        }
        return df.format(timeMillis * 1000l);
    }


    /**
     * 格式化时间，例如2004.12.16
     *
     * @param timeMillis 时间戳
     * @return 格式化后的字符串
     **/
    public static String formatTimeDate(long timeMillis) {
        if (THREAD_SAFE_YYYY_MM_DD_FORMAT1 == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_YYYY_MM_DD_FORMAT1 == null) {
                    THREAD_SAFE_YYYY_MM_DD_FORMAT1 = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_YYYY_MM_DD_FORMAT1.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYY_MM_DD_FORMAT1_STR);
            THREAD_SAFE_YYYY_MM_DD_FORMAT1.set(df);
        }
        return df.format(timeMillis * 1000l);
    }
    /**
     * 格式化时间，例如2004.5.16
     *
     * @param timeMillis 时间戳
     * @return 格式化后的字符串
     **/
    public static String formatTimeDate_YYYYMD(long timeMillis) {
        if (THREAD_SAFE_YYYY_M_D_FORMAT1 == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_YYYY_M_D_FORMAT1 == null) {
                    THREAD_SAFE_YYYY_M_D_FORMAT1 = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_YYYY_M_D_FORMAT1.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYY_M_D_FORMAT1_STR);
            THREAD_SAFE_YYYY_M_D_FORMAT1.set(df);
        }
        return df.format(timeMillis);
    }



    /**
     * 格式化时间，例如2004-12-16
     *
     * @param timeSeconds 时间戳
     * @return 格式化后的字符串
     **/
    public static String formatTimeDate2(long timeSeconds) {
        if (THREAD_SAFE_YYYY_MM_DD_FORMAT2 == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_YYYY_MM_DD_FORMAT2 == null) {
                    THREAD_SAFE_YYYY_MM_DD_FORMAT2 = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_YYYY_MM_DD_FORMAT2.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYY_MM_DD_FORMAT2_STR);
            THREAD_SAFE_YYYY_MM_DD_FORMAT2.set(df);
        }
        return df.format(timeSeconds * 1000);
    }

    /**
     * 格式化时间，例如12.16
     *
     * @param timeSecond Unix时间戳,单位秒
     * @return 格式化后的字符串
     **/
    public static String formatTimeDate3(long timeSecond) {
        if (THREAD_SAFE_MM_DD_FORMAT == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_MM_DD_FORMAT == null) {
                    THREAD_SAFE_MM_DD_FORMAT = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_MM_DD_FORMAT.get();
        if (df == null) {
            df = new SimpleDateFormat(MM_DD_FORMAT_STR);
            THREAD_SAFE_MM_DD_FORMAT.set(df);
        }
        return df.format(timeSecond * 1000);
    }

    /**
     * 格式化时间，5.16
     *
     * @param timeSecond Unix时间戳,单位秒
     * @return 格式化后的字符串
     **/
    public static String formatTimeDate3_MD(long timeSecond) {
        if (THREAD_SAFE_M_D_FORMAT == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_M_D_FORMAT == null) {
                    THREAD_SAFE_M_D_FORMAT = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_M_D_FORMAT.get();
        if (df == null) {
            df = new SimpleDateFormat(M_D_FORMAT_STR);
            THREAD_SAFE_M_D_FORMAT.set(df);
        }
        return df.format(timeSecond * 1000);
    }



    /**
     * 格式化时间，例如12.6-12.20
     *
     * @param timeBeginSecond Unix开始时间戳，单位秒
     * @param timeEndSecond   Unix结束时间戳，单位秒
     * @return 格式化后的字符串
     **/
    public static String formatTimeDate4(long timeBeginSecond, long timeEndSecond) {
        if (THREAD_SAFE_M_D_FORMAT == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_M_D_FORMAT == null) {
                    THREAD_SAFE_M_D_FORMAT = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_M_D_FORMAT.get();
        if (df == null) {
            df = new SimpleDateFormat(M_D_FORMAT_STR);
            THREAD_SAFE_M_D_FORMAT.set(df);
        }
        return df.format(timeBeginSecond * 1000) + "-" + df.format(timeEndSecond * 1000);
    }



    /**
     * 获取两个日期之间的天数 例如 2015.02.25-2015.02.26，返回2
     *
     * @param timeMillisBegin Unix时间戳，单位秒
     * @param timeMillisEnd   Unix时间戳，单位秒
     * @return 格式化后的字符串
     **/
    public static int getDaysBetween(long timeMillisBegin, long timeMillisEnd) {
        Calendar d1 = new GregorianCalendar();
        Calendar d2 = new GregorianCalendar();
        d1.setTimeInMillis(timeMillisBegin);
        d2.setTimeInMillis(timeMillisEnd);
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        // 2015.02.25-2015.02.26，差了两天, 所以此处需要+1
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR) + 1;
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 格式化时间，例如2004.6.16延迟2天，返回2004.6.18
     *
     * @param startDate
     * @param dayCount
     * @return 格式化后的字符串
     **/
    public static String delay2Date(String startDate, int dayCount) {
        if (THREAD_SAFE_YYYY_M_D_FORMAT1 == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_YYYY_M_D_FORMAT1 == null) {
                    THREAD_SAFE_YYYY_M_D_FORMAT1 = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_YYYY_M_D_FORMAT1.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYY_M_D_FORMAT1_STR);
            THREAD_SAFE_YYYY_M_D_FORMAT1.set(df);
        }

        Date date;
        try {
            date = df.parse(startDate);
        } catch (ParseException e) {
            L.e(e);
            return startDate;
        }
        date.setTime(date.getTime() + dayCount * TIME_DAY_MILLIS);
        return df.format(date);
    }

    /**
     * 格式化时间，例如20041216093000
     *
     * @param timeMillis 时间戳
     * @return 格式化后的字符串
     **/
    public static String formatTime_YYYY_MM_DD_HH_MM_SS(long timeMillis) {
        if (THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT == null) {
                    THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_FORMAT_STR);
            THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT.set(df);
        }
        return df.format(timeMillis);
    }

    /**
     * 格式化时间，24小时制
     *
     * @param date 需要格式化的日期
     * @return 格式化后的字符串
     *
     *         public static String formatTime24(Date date) { synchronized (THREAD_SAFE_TIME_24_FORMAT) { return THREAD_SAFE_TIME_24_FORMAT.format(date); } }
     **/

    /**
     * 格式化时间，24小时制
     *
     * @param timeMillis 时间戳
     * @return 格式化后的字符串
     **/
    public static String formatTime24(long timeMillis) {
        if (THREAD_SAFE_TIME_24_FORMAT == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_TIME_24_FORMAT == null) {
                    THREAD_SAFE_TIME_24_FORMAT = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_TIME_24_FORMAT.get();
        if (df == null) {
            df = new SimpleDateFormat(TIME_24_FORMAT_STR);
            THREAD_SAFE_TIME_24_FORMAT.set(df);
        }
        return df.format(timeMillis);
    }

    /**
     * format specified time string to target format
     *
     * @param serverTime
     * @param targetFormat
     * @return
     */
    public static String formatStringTime(long serverTime, String targetFormat) {
        if (TextUtils.isEmpty(targetFormat)) {
            return "";
        }

        try {
            SimpleDateFormat sdfTarget = new SimpleDateFormat(targetFormat);// 格式（显示时间）
            return sdfTarget.format(serverTime);
        } catch (Exception e) {
            L.w(e);
        }

        return "";
    }

    /**
     * 替换url里的空格
     *
     * @param url url字符串
     * @return 替换后的结果
     **/
    public static String replacePlusWithPercent20(String url) {
        if (url.contains("+")) {
            if (PATTERN_PLUS == null) {
                synchronized (Util.class) {
                    if (PATTERN_PLUS == null) {
                        PATTERN_PLUS = Pattern.compile("\\+");
                    }
                }
            }

            url = PATTERN_PLUS.matcher(url).replaceAll("%20");
        }

        return url;
    }

    private final static int ONE_KILOBYTE = 1024;
    private final static int ONE_MEGABYTE = ONE_KILOBYTE * 1024;
    private final static int ONE_GIGABYTE = ONE_MEGABYTE * 1024;

    /**
     * 格式化容量大小
     *
     * @param sizeInByte long类型的字节数
     * @return 格式后的大小
     **/
    public static String formatSizeInByte(long sizeInByte) {
        if (sizeInByte >= ONE_GIGABYTE)
            return ONE_DECIMAL_POINT_DF.format((double) sizeInByte / ONE_GIGABYTE) + "GB";

        else if (sizeInByte >= ONE_MEGABYTE)
            return ONE_DECIMAL_POINT_DF.format((double) sizeInByte / ONE_MEGABYTE) + "MB";

        else if (sizeInByte >= ONE_KILOBYTE)
            return ONE_DECIMAL_POINT_DF.format((double) sizeInByte / ONE_KILOBYTE) + "KB";

        else
            return sizeInByte + "B";
    }

    /**
     * 格式化容量大小 返回单位为MB
     *
     * @param sizeInByte long类型的字节数
     * @return 格式后的大小
     **/
    public static String formatSizeInByteToMB(long sizeInByte) {
        return ONE_DECIMAL_POINT_DF.format((double) sizeInByte / ONE_MEGABYTE) + "MB";
    }

    private final static int ONE_HOUR = 3600;
    private final static int ONE_MINUTE = 60;



    /**
     * 格式化数字
     *
     * @param number 待格式化的数字
     * @return 格式后的数字
     **/
    public static String formatNumberInPercent(double number) {
        if (number > 100) {
            number = 100;
        }
        return ONE_DECIMAL_POINT_DF.format(number) + "%";
    }

    public static void closeCloseable(Closeable obj) {
        try {
            // 修复小米MI2的JarFile没有实现Closeable导致崩溃问题
            if (obj instanceof Closeable && obj != null) {
                obj.close();
            }
        } catch (IOException e) {
            L.w(e);
        }
    }

    /**
     * 判断sdcard是否有足够的空间
     *
     * @param fileSize
     * @return
     */
    public static boolean isEnoughForFile(long fileSize) {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        // //sd卡分区数
        // int blockCounts = statFs.getBlockCount();
        // sd卡可用分区数
        int avCounts = statFs.getAvailableBlocks();
        // 一个分区数的大小
        long blockSize = statFs.getBlockSize();
        // sd卡可用空间
        long spaceLeft = avCounts * blockSize;

        if (spaceLeft < fileSize) {
            return false;
        }

        return true;
    }

    /*
     * public static boolean unzipFile(String zipFileName, File destDir) { return unzipFile(zipFileName, destDir, null); }
     */

    private final static int USE_FILE_COUNT_FOR_UNZIP_PROGRESS_THRESHOLD = 10;

    /**
     * 获取zip文件中的根目录
     *
     * @param zipFileName zip文件名
     * @return the root dirname or null if none is found
     **/
    public static String getRootDirNameInZipFile(String zipFileName) {
        ZipInputStream zis = null;

        try {
            zis = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry entry;

            if ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                int slashIndex = fileName.indexOf('/');

                if (slashIndex != -1)
                    return fileName.substring(0, slashIndex);

                if (entry.isDirectory())
                    return fileName;
            }

        } catch (IOException e) {
            L.w(e);

        } finally {
            Util.closeCloseable(zis);
        }

        return null;
    }

    /**
     * 解压文件
     *
     * @param destDir 解压目录
     * @param fis     解压的文件流
     **/
    public static boolean unzipFile(InputStream fis, File destDir) {
        final byte[] buffer = new byte[4096];
        ZipInputStream zis = null;

        try {
            // make sure the directory is existent
            destDir.mkdirs();
            zis = new ZipInputStream(fis);
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();

                if (entry.isDirectory()) {
                    new File(destDir, fileName).mkdirs();

                } else {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destDir, fileName)));
                    int lenRead;

                    while ((lenRead = zis.read(buffer)) != -1) {
                        bos.write(buffer, 0, lenRead);
                    }

                    bos.close();
                }

                zis.closeEntry();
            }

            return true;

        } catch (IOException e) {
            L.w(e);
        } finally {
            Util.closeCloseable(zis);
        }

        return false;
    }

    /**
     * 删除文件
     *
     * @param path 需要删除的文件路径
     **/
    public static void deleteFile(String path) {
        if (path != null) {
            File file = new File(path);

            if (file.exists())
                file.delete();
        }
    }

    /**
     * 取得文件名满足所指定的规则表达式的文件列表 ";"隔开
     */
    public static FilenameFilter getFileExtensionFilterByExpStr(String exp) {
        final String[] expArr = exp.split(";");
        return new FilenameFilter() {
            public boolean accept(File file, String name) {
                boolean flag = false;

                for (int i = 0; i < expArr.length; i++) {
                    if (name.endsWith(expArr[i]))
                        flag = true;

                    ;
                }

                return flag;
            }
        };
    }

    // from stackoverflow.com: http://stackoverflow.com/a/3549021/668963
    public static Bitmap decodeFile(File f, int maxWidth, int maxHeight) {
        Bitmap b = null;

        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;

            if (o.outHeight > maxHeight || o.outWidth > maxWidth) {
                scale = (int) Math
                        .pow(2, (int) Math.round(Math.log(maxWidth / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();

        } catch (IOException e) {
            L.w(e);
        }

        return b;
    }

    /**
     * 获取apk的签名
     *
     * @param apkPath apk路径
     * @return apk签名
     **/
    public static String getAPKSignatures(String apkPath) {
        String PATH_PackageParser = "android.content.pm.PackageParser";

        try {
            // apk包的文件路径
            // 这是一个Package 解释器, 是隐藏的
            // 构造函数的参数只有一个, apk文件的路径
            // PackageParser packageParser = new PackageParser(apkPath);
            Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
            Class<?>[] typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
            Object[] valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            Object pkgParser = pkgParserCt.newInstance(valueArgs);
            // 这个是与显示有关的, 里面涉及到一些像素显示等等, 我们使用默认的情况
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            typeArgs = new Class[4];
            typeArgs[0] = File.class;
            typeArgs[1] = String.class;
            typeArgs[2] = DisplayMetrics.class;
            typeArgs[3] = Integer.TYPE;
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage", typeArgs);
            valueArgs = new Object[4];
            valueArgs[0] = new File(apkPath);
            valueArgs[1] = apkPath;
            valueArgs[2] = metrics;
            valueArgs[3] = PackageManager.GET_SIGNATURES;
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);
            typeArgs = new Class[2];
            typeArgs[0] = pkgParserPkg.getClass();
            typeArgs[1] = Integer.TYPE;
            Method pkgParser_collectCertificatesMtd = pkgParserCls.getDeclaredMethod("collectCertificates", typeArgs);
            valueArgs = new Object[2];
            valueArgs[0] = pkgParserPkg;
            valueArgs[1] = PackageManager.GET_SIGNATURES;
            pkgParser_collectCertificatesMtd.invoke(pkgParser, valueArgs);
            // 应用程序信息包, 这个公开的, 不过有些函数, 变量没公开
            Field packageInfoFld = pkgParserPkg.getClass().getDeclaredField("mSignatures");
            Signature[] info = (Signature[]) packageInfoFld.get(pkgParserPkg);
            return info[0].toCharsString();

        } catch (Exception e) {
            L.w(e);
        }

        return null;
    }

    /**
     * 判断邮箱格式是否正确
     *
     * @param strEmail
     * @return true:正确 false:错误
     */
    public static boolean isEmail(String strEmail) {
        // final String emailPattern = "^([a-z0-9A-Z])+([a-z0-9A-Z_]{0,})@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        // final String emailPattern2 = "[a-zA-Z0-9]{1,}[a-zA-Z0-9_-]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        // final String emailPattern3 = "^([a-z0-9A-Z])+([a-z0-9A-Z_]?)@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final String emailPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        final Pattern pattern = Pattern.compile(emailPattern);
        final Matcher matcher = pattern.matcher(strEmail);

        if (matcher.matches()) {
            return true;
        }

        return false;
    }

    /**
     * 获取拓展卡大小
     **/
    public static long getExternalStorageSize() {
        String status = Environment.getExternalStorageState();

        // 是否只读
        if (status.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            status = Environment.MEDIA_MOUNTED;
        }

        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                long sdSize = totalBlocks * blockSize;
                return sdSize;

            } catch (IllegalArgumentException e) {
                L.w(e);
                status = Environment.MEDIA_REMOVED;
            }
        }

        return 0;
    }

    /**
     * 获取Android数据目录大小
     **/
    public static long getInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 是否存在sdcard
     **/
    public static boolean existSDcard() {
        boolean isExistSDcard = false;
        long lExternalSize = getExternalStorageSize();
        long lInternalSize = getInternalStorageSize();

        if (lExternalSize != 0 && lExternalSize != lInternalSize)
            isExistSDcard = true;

        return isExistSDcard;
    }

    /**
     * 重命名
     *
     * @param oldPath 原文件路径
     * @param newPath 新文件路径
     **/
    public static void rename(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        if (oldFile.exists())
            oldFile.renameTo(newFile);
    }

    public static void copyFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);

        if (srcFile.isFile()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            File destFile = new File(destPath);

            try {
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                byte[] buffer = new byte[2048];
                int lenRead;

                while ((lenRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, lenRead);
                }

            } catch (Exception e) {
                L.w(e);
                if (destFile.exists())
                    destFile.delete();

            } finally {
                closeCloseable(fis);
                closeCloseable(fos);
            }
        }
    }

    public static String keyFromGameIdAndPkgName(int gameId, String pkgName) {
        return gameId + pkgName;
    }

    /**
     * 获取cpu 核心
     *
     * @return cpu核心数
     **/
    public static int getCpuCores() {
        try {
            File cpuDir = new File("/sys/devices/system/cpu");
            return cpuDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return Pattern.matches("cpu[0-9]", filename);
                }
            }).length;

        } catch (Exception e) {
            L.w(e);
            return 1;
        }
    }

    /**
     * 获取cpu频率
     *
     * @return 使用频率
     **/
    public static int getCpuFreq() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq")));
            return Integer.parseInt(br.readLine());

        } catch (Exception e) {
            L.w(e);

        } finally {
            closeCloseable(br);
        }

        return 0;
    }

    /**
     * get screen width
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (mScreenWidth == -1) {
            getScreenProperties(context);
        }

        return mScreenWidth;
    }

    /**
     * get screen height
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (mScreenHeight == -1) {
            getScreenProperties(context);
        }

        return mScreenHeight;
    }

    private static void getScreenProperties(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mScreenWidth = display.getWidth();
        mScreenHeight = display.getHeight();
    }

    /**
     * convert px from dp
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = getScreenDensity(context);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * convert px from dp
     */
    public static int dip2px(float dpValue) {
        return dip2px(AppLib.getContext(), dpValue);
    }

    /**
     * convert dp from px
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = getScreenDensity(context);
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * get screen density
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        if (mDensity == -1) {
            mDensity = context.getResources().getDisplayMetrics().density;
        }

        return mDensity;
    }

    public static String formateTimeByTimestamp(long timestamp) {
        try {
            if (DATE_FORMAT == null) {
                DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            }

            return DATE_FORMAT.format(new Date(timestamp));

        } catch (Exception e) {
            L.w(e);
        }

        return "";
    }

    public final static long randomRequestId() {
        long ret = System.currentTimeMillis();
        ret = ret << 10;
        ret |= 0x03FF & new Random().nextInt(1024);
        return ret;
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JSONObject(json);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * 重新实现JSON的getLong方法
     *
     * @param jsObj
     * @param key
     * @return
     * @throws JSONException
     */
    public static long getJSONLong(JSONObject jsObj, String key) {
        return getJSONLong(jsObj, key, 0L);
    }

    /**
     * 重新实现JSON的getLong方法
     *
     * @param jsObj
     * @param key
     * @return
     * @throws JSONException
     */
    public static long getJSONLong(JSONObject jsObj, String key, long defValue) {
        try {
            Object object = jsObj.get(key);
            return object instanceof Number ? ((Number) object).longValue() : Long.parseLong((String) object);

        } catch (Exception e) {
            L.w(e);
        }

        return defValue;
    }



    public static String formatMoney(double money) {
        return TWO_DECIMAL_POINT_DF.format(money);
    }

    /**
     * 格式化容量大小 返回单位为MB
     *
     * @param sizeInByte long类型的字节数
     * @return 格式后的大小
     **/
    public static double formatByteToMB(long sizeInByte) {
        return (double) sizeInByte / ONE_MEGABYTE;
    }

    public static boolean isToday(long when) {
        Time time = new Time();
        time.set(when);
        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;
        time.set(System.currentTimeMillis());
        return (thenYear == time.year) && (thenMonth == time.month) && (thenMonthDay == time.monthDay);
    }

    /**
     * 用于判断距离上一次调用是否进入新的一天
     *
     * @return
     */
    public static boolean isNewDay(long when) {
        return !Util.isToday(when);
    }

    /**
     * 取总的ram大小
     *
     * @return
     */
    public static int getTotalRamInKiloBytes() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/meminfo")));
            String totalRam = br.readLine().split("\\s+")[1]; // the first line is: MemTotal: xxxx kB
            return Integer.parseInt(totalRam);

        } catch (Exception e) {
            L.w(e);

        } finally {
            closeCloseable(br);
        }

        return 0;
    }

    /**
     * 获取sdcard可用大小
     */
    public static long getAvailExternalStorageSizeInKiloBytes() {
        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED) || status.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                return availableBlocks * blockSize / 1024; // in kilobytes

            } catch (Exception e) {
                L.w(e);
            }
        }

        return 0;
    }

    /**
     * 获取手机存储可用大小
     */
    public static long getAvailInternalStorageSizeInKiloBytes() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize / 1024; // in kilobytes

        } catch (Exception e) {
            L.w(e);
        }

        return 0;
    }

    public static long getTotalAvailStorageSizeInKiloBytes() {
        return getAvailInternalStorageSizeInKiloBytes() + getAvailExternalStorageSizeInKiloBytes();
    }

    /**
     * 是否竖屏
     */
    public boolean isPortrait(Context context) {
        Configuration cf = context.getResources().getConfiguration();
        int ori = cf.orientation;
        return ori == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * GetJsonObject的封装
     *
     * @param object
     * @param key
     * @return
     */
    public static Object getJsonObject(JSONObject object, String key) {
        try {
            return object.has(key) ? object.get(key) : null;
        } catch (JSONException e) {
            L.w(e);
            return null;
        }
    }

    public static String getJSONString(JSONObject jsonObj, String key, String defalutValue) {
        if (jsonObj == null)
            return defalutValue;

        try {
            if (jsonObj.has(key)) {
                return jsonObj.getString(key);
            }
        } catch (JSONException e) {
            L.w(e);
        }

        return defalutValue;
    }

    public static String getJSONString(JSONObject jsonObj, String key) {
        return getJSONString(jsonObj, key, null);
    }

    public static int getJSONInt(JSONObject jsonObj, String key, int defValue) {
        if (jsonObj == null)
            return defValue;

        try {
            if (jsonObj.has(key))
                return jsonObj.getInt(key);

        } catch (Exception e) {
            L.w(e);
        }

        return defValue;
    }

    public static boolean getJSONBoolean(JSONObject jsonObj, String key) {
        if (jsonObj == null)
            return false;

        try {
            if (jsonObj.has(key)) {
                return jsonObj.getBoolean(key);
            }

        } catch (JSONException e) {
            L.w(e);
        }

        return false;
    }

    public static void putObject(JSONObject jsonObj, String key, Object obj) {
        if (jsonObj == null || key == null)
            return;

        try {
            jsonObj.put(key, obj);
        } catch (JSONException e) {
        }
    }

    /**
     * get JSONArray object from the json object , or return null if failed
     *
     * @param jsonObj
     * @param key
     * @return
     */
    public static JSONArray getJSONArray(JSONObject jsonObj, String key) {
        if (jsonObj == null)
            return null;

        try {
            if (jsonObj.has(key)) {
                return jsonObj.getJSONArray(key);
            }
        } catch (Exception e) {
            L.w(e);
        }

        return null;
    }

    /**
     * get json object from json array with an index in the array and return null if failed
     *
     * @param jsonArr
     * @param index
     * @return
     */
    public static JSONObject getJSONObject(JSONArray jsonArr, int index) {
        if (jsonArr == null)
            return null;

        try {
            return jsonArr.getJSONObject(index);
        } catch (JSONException e) {
            L.w(e);
        }

        return null;
    }

    /**
     * convenience method for convert json string to json object
     *
     * @param jsonStr
     * @return: the json object convert from the json string or null if convert failed
     */
    public static JSONObject toJSONObject(String jsonStr) {
        if (jsonStr == null)
            return null;

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            L.w(e);
        }

        return jsonObject;
    }

    public static JSONArray toJSONArray(String jsonStr) {
        if (jsonStr == null)
            return null;

        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(jsonStr);
        } catch (JSONException e) {
            L.w(e);
        }

        return jsonArr;
    }

    /**
     * silent method for putting json object
     *
     * @param originJSON
     * @param key
     * @param value
     */
    public static void putJSONObject(JSONObject originJSON, String key, JSONObject value) {
        try {
            // we not judge originJSON here, because we need developer find the problem in there code
            originJSON.put(key, value);
        } catch (JSONException e) {
            L.w(e);
        }
    }

    public static void putJSONInt(JSONObject originJSON, String key, int value) {
        try {
            // we not judge originJSON here, because we need developer find the problem in there code
            originJSON.put(key, value);
        } catch (JSONException e) {
            L.w(e);
        }
    }

    public static void putJSONString(JSONObject originJSON, String key, String value) {
        try {
            // we not judge originJSON here, because we need developer find the problem in there code
            originJSON.put(key, value);
        } catch (JSONException e) {
            L.w(e);
        }
    }

    /**
     * get json object quietly
     *
     * @param originJSON
     * @param key
     * @return
     */
    public static JSONObject getJSONObject(JSONObject originJSON, String key) {
        if (originJSON == null)
            return null;

        JSONObject jsonObj = null;
        try {
            if (originJSON.has(key)) {
                jsonObj = originJSON.getJSONObject(key);
            }
        } catch (Exception e) {
            L.w(e);
        }

        return jsonObj;
    }

    /**
     * get object quietly
     *
     * @param originJSON
     * @param key
     * @return
     */
    public static Object getObject(JSONObject originJSON, String key) {
        if (originJSON == null)
            return null;

        Object obj = null;
        try {
            if (originJSON.has(key)) {
                obj = originJSON.get(key);
            }
        } catch (Exception e) {
            L.w(e);
        }

        return obj;
    }

    public static boolean isScreenlandspace(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth() > display.getHeight();
    }

    /**
     * set data source for media player
     *
     * @param resources
     * @param player
     * @param res
     * @throws IOException
     */
    public static void setDataSourceFromResource(Resources resources, MediaPlayer player, int res) throws IOException {
        AssetFileDescriptor afd = resources.openRawResourceFd(res);

        if (afd != null) {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        }
    }

    public final static String appendStrings(final String... values) {
        if (values == null || values.length <= 0) {
            return null;
        }

        if (values.length == 1) {
            return values[0];
        }

        StringBuilder sb = new StringBuilder(values.length * 6);

        for (String value : values) {
            sb.append(value);
        }

        String key = sb.toString();
        return key;
    }

    /**
     * 根据随机范围获取随机数
     *
     * @param intValue 随机范围
     * @return 随机所得
     */
    public static Integer getRandom(Integer intValue) {
        Random random = new Random();
        Integer randomValue;

        if (intValue != null) {
            randomValue = Math.abs(random.nextInt(intValue));

        } else {
            randomValue = Math.abs(random.nextInt());
        }

        return randomValue;
    }

    /**
     * 计算速度(数字)
     **/
    public static double getProcess(long downloadedBytes, long fileLength) {
        if (fileLength == 0) {
            return 0;
        } else {
            return (downloadedBytes * 100f / fileLength);
        }
    }

    /***
     * 从字符串时间格式获取long类型毫秒数
     *
     * @param time 字符串时间
     ***/
    public static long getTimeFromString(String time) {
        if (TextUtils.isEmpty(time))
            return 0;

        if (THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER == null) {
            synchronized (Util.class) {
                if (THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER == null) {
                    THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER = new ThreadLocal<DateFormat>();
                }
            }
        }

        DateFormat df = THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER.get();
        if (df == null) {
            df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_FORMAT_FORMAT_FOR_SERVER_STR);
            THREAD_SAFE_YYYY_MM_DD_HH_MM_SS_FORMAT_FOR_SERVER.set(df);
        }

        try {
            return df.parse(time).getTime();
        } catch (ParseException e) {
            L.e(e);
        }
        return 0;
    }

    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public static boolean hasEnoughRamToPlayDemo() {
        ActivityManager am = (ActivityManager) AppLib.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);

        final long playDemoMemoryByte = 90L * 1024 * 1024;// 运行试玩需要的内存
        if (memoryInfo.availMem > (playDemoMemoryByte + memoryInfo.threshold)) {
            return true;
        }

        return false;
    }

    /**
     * 比较两个版本号
     *
     * @param version1 版本号1
     * @param version2 版本号2
     * @return @return 0 if version1 = version2, less than 0 if version1 &lt; version2, and greater than 0 if version1 &gt; version2
     * @throws IllegalArgumentException
     * @throws NumberFormatException
     */
    public static int versionCompareTo(String version1, String version2) throws IllegalArgumentException, NumberFormatException {
        if (TextUtils.isEmpty(version1) || TextUtils.isEmpty(version2)) {
            throw new IllegalArgumentException("compare version can not be null.");
        }
        if (!version1.contains(".") || !version2.contains(".")) {
            throw new IllegalArgumentException("version format error, version should contains '.'");
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        if (version1Array.length != version2Array.length) {
            throw new IllegalArgumentException("compare version's length is not the same");
        }
        for (int i = 0; i < version1Array.length; i++) {
            int splitVersion1 = Integer.parseInt(version1Array[i]);
            int splitVersion2 = Integer.parseInt(version2Array[i]);
            int splitResult = splitVersion1 < splitVersion2 ? -1 : (splitVersion1 == splitVersion2 ? 0 : 1);
            if (splitResult != 0) {
                return splitResult;
            }
        }
        return 0;
    }

    /**
     * 调用系统InstalledAppDetails界面显示已安装应用程序的详细信息。 对于Android 2.3（Api Level 9）以上，使用SDK提供的接口； 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）。
     *
     * @param context     上下文
     * @param packageName 应用程序的包名
     */
    public static void showInstalledAppDetails(Context context, String packageName) {
        final String SCHEME = "package";
        /**
         * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
         */
        final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
        /**
         * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
         */
        final String APP_PKG_NAME_22 = "pkg";
        /**
         * InstalledAppDetails所在包名
         */
        final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
        /**
         * InstalledAppDetails类名
         */
        final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts(SCHEME, packageName, null);
            intent.setData(uri);
        } else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
            // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
            final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
            intent.putExtra(appPkgName, packageName);
        }
        try {
            if (context instanceof Application) {
                L.d("start pkg activity from application");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            L.e(e);
        }
    }

    /**
     * 应用程序是否打开了显示浮窗的开关（部分rom试用，如小米）
     *
     * @param context 当前应用程序的上下文
     * @return boolean
     */
    public static boolean hasOpenedFloatingWindow(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return true;
        }
        Class<? extends ApplicationInfo> clazz = applicationInfo.getClass();
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            if (f.getName().equals("FLAG_SHOW_FLOATING_WINDOW")) {
                try {
                    int i = f.getInt(context.getApplicationInfo());
                    int flags = context.getApplicationInfo().flags;
                    if ((flags & i) == i) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (IllegalArgumentException e) {
                    L.w(e);
                } catch (IllegalAccessException e) {
                    L.w(e);
                } catch (Exception e) {
                    L.w(e);
                }
            }
        }
        return true;
    }

    /**
     * 获取当前手机的总内存大小（
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static long getTotalMemory(Context context) {
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
                actManager.getMemoryInfo(memInfo);
                return memInfo.totalMem;
            } catch (Exception e) {
                L.w(e);
            }
        } else {
            String str1 = "/proc/meminfo";
            String str2;
            String[] arrayOfString;
            long initial_memory = 0;
            try {
                FileReader localFileReader = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
                str2 = localBufferedReader.readLine();// meminfo
                arrayOfString = str2.split("\\s+");
                // total Memory
                initial_memory = Integer.valueOf(arrayOfString[1]) * 1024;
                localBufferedReader.close();
                return initial_memory;
            } catch (IOException e) {
                L.w(e);
            } catch (Exception e) {
                L.w(e);
            }
        }
        return -1;
    }

    /**
     * 给一个View设置透明度
     *
     * @param view  需要设置透明度的view
     * @param alpha 透明度值
     */
    public static void setAlpha(View view, float alpha) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        // 设置透明度
        view.startAnimation(alphaAnimation);
    }

    /**
     * stream转string,默认utf-8编码
     *
     * @param stream
     * @return
     * @throws IOException
     */
    public static String inputStreamToString(InputStream stream) throws IOException {
        return inputStreamToString(stream, "UTF-8");
    }

    /**
     * stream转string
     *
     * @param stream
     * @param charsetName 编码
     * @return
     * @throws IOException
     */
    public static String inputStreamToString(InputStream stream, String charsetName) throws IOException {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, charsetName);
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) {
            writer.write(buffer, 0, n);
        }
        return writer.toString();
    }

    /**
     * 读取Sssets文件
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(String fileName, Context context) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    /**
     * 字符串转stream
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static InputStream stringToInputStream(String str) throws Exception {

        ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        return is;
    }

    /**
     * 字符串转byte[]
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] stringToBytes(String str) throws UnsupportedEncodingException {
        if (null == str) {
            return null;
        }
        return str.getBytes("ISO-8859-1");
    }

    /**
     * byte[]转字符串
     *
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String bytesToString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "ISO-8859-1");
    }

    public static void writeFile(String path, String content) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(content);
            output.close();
        } catch (Exception e) {
            L.e(e);
        }
    }

    /**
     * int转byte[]
     *
     * @param num
     * @return
     */
    public static byte[] intTobytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >>> 24);
        result[1] = (byte) (num >>> 16);
        result[2] = (byte) (num >>> 8);
        result[3] = (byte) (num);
        return result;
    }

    /**
     * byte[] 转 int
     *
     * @param b
     * @param offset
     * @return
     */
    public static int bytesToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < b.length; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    /**
     * 验证合法手机号（在未出新号段前可用）
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、 178 联通：130、131、132、152、155、156、185、186、176
         * 电信：133、153、180、189、177、（1349卫通） 总结起来就是第一位必定为1，第二位必定为3或5或8、7，其他位置的可以为0-9
         */
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8、7中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            return mobiles.matches(telRegex);
        }
    }

    public static boolean isListEmpty(List<?> list) {
        return list == null || list.size() <= 0;
    }

    /**
     * 获取字符串数组中的字符串
     *
     * @param resId
     * @param index
     * @return
     */
    public static String getStringToArray(int resId, int index) {
        String[] array = AppLib.getResources().getStringArray(resId);
        if (index < 0 || index >= array.length) {
            return "";
        } else {
            return array[index];
        }
    }

    private static final String[] IMAGE_SCHEME = new String[]{".jpg", ".png", ".bmp"};

    /**
     * 获取远程图片大图地址
     *
     * @param url
     * @return
     */
    public static String getRemoteLargeImageUrl(String url) {
        return getRemoteThumbImageUrl(url, 2000, 1500);
    }

    /**
     * 获取远程图片大图地址
     *
     * @param url
     * @return
     */
    public static String getRemoteImageUrl320x240(String url) {
        return getRemoteThumbImageUrl(url, 320, 240);
    }

    /**
     * 获取远程图片小图地址
     *
     * @param url
     * @return
     */
    public static String getRemoteThumbImageUrl(String url) {
        return getRemoteThumbImageUrl(url, 180, 180);
    }

    /**
     * 把缩略图转换成大图
     * @param url
     * @return
     */
    public static String changeThumbToLargeImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String thumbStr = "_180x180";
        String largeStr = "_2000x1500";
        int index = url.indexOf(thumbStr);
        if (index != -1) {
            int tmp = url.length() -4;
            String end = url.substring(tmp, url.length());
            StringBuilder sb = getThreadSafeStringBuilder().append(url.substring(0, index));
            sb.append(largeStr).append(end);
            return sb.toString();
        }
        return url;
    }

    /**
     * 获取远程图片小图地址，指定高宽
     *
     * @param url
     * @return
     */
    public static String getRemoteThumbImageUrl(String url, int width, int height) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        String nu = url.toLowerCase();
        int index = -1;
        for (String schema : IMAGE_SCHEME) {
            index = nu.indexOf(schema);
            if (index != -1 && index == nu.length() - 4) {
                break;
            }
        }
        if (index != -1) {
            int tmp = nu.length() - 4;
            StringBuilder sb = getThreadSafeStringBuilder().append(url.substring(0, tmp));
            sb.append('_').append(width).append('x').append(height).append(url.substring(tmp, nu.length()));
            return sb.toString();
        }
        return url;
    }




    public static int[] toIntArray(List<Integer> list) {
        int size = list.size();
        int[] ret = new int[size];
        for (int i = 0; i < size; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    private static final DecimalFormat sDecimalFormat = new DecimalFormat("#0.00");

    /**
     * 保留小数点后两位
     *
     * @param data
     * @return
     */
    public static String getFormatData(Double data) {
        try {
            return sDecimalFormat.format(data);
        } catch (Exception e) {
            L.e(e);
        }
        return "";
    }

    /**
     * 保留小数点后两位
     *
     * @param data
     * @return
     */
    public static String getFormatData(float data) {
        try {
            return sDecimalFormat.format(data);
        } catch (Exception e) {
            L.e(e);
        }
        return "";
    }

    /**
     * 不保留小数点
     *
     * @param data
     * @return
     */
    public static String getFormatData2(Double data) {
        try {
            return sDecimalFormat2.format(data);
        } catch (Exception e) {
            L.e(e);
        }
        return "";
    }




    /**
     * uri.getQueryParameterNames()的替代实现方式，解决api level小于11，android.net.Uri未实行这个方法
     *
     * @param uri
     * @return
     */
    public static Set<String> getQueryParameterNames(Uri uri) {
        if (uri.isOpaque()) {
            throw new UnsupportedOperationException("This isn't a hierarchical URI.");
        }

        String query = uri.getEncodedQuery();
        if (query == null) {
            return Collections.emptySet();
        }

        Set<String> names = new LinkedHashSet<String>();
        int start = 0;
        do {
            int next = query.indexOf('&', start);
            int end = (next == -1) ? query.length() : next;

            int separator = query.indexOf('=', start);
            if (separator > end || separator == -1) {
                separator = end;
            }

            String name = query.substring(start, separator);
            names.add(Uri.decode(name));

            // Move start to end of name.
            start = end + 1;
        } while (start < query.length());

        return Collections.unmodifiableSet(names);
    }
}
