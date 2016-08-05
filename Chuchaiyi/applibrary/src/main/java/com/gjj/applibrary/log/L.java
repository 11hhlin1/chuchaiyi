package com.gjj.applibrary.log;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 
 * 类/接口注释
 * 
 * @author chuck
 * @createDate Dec 29, 2014
 *
 */
public class L {

    private final static ThreadLocal<StringBuilder> threadSafeStrBuilder = new ThreadLocal<StringBuilder>();

    // webview打log的各种等级
    private static final int WEB_LEVEL_DEBUG = MessageLevel.DEBUG.ordinal();
    private static final int WEB_LEVEL_LOG = MessageLevel.LOG.ordinal();
    private static final int WEB_LEVEL_TIP = MessageLevel.TIP.ordinal();
    private static final int WEB_LEVEL_WARNING = MessageLevel.WARNING.ordinal();
    private static final int WEB_LEVEL_ERROR = MessageLevel.ERROR.ordinal();
    
    public static void d(String message, Object...args) {
        boolean canPrint = LogManager.canPrint(LogManager.LOG_LEVEL_DEBUG);
        boolean canSave = LogManager.canSave(LogManager.LOG_LEVEL_DEBUG);
        if (canPrint || canSave) {
            message = formatMessage(message, args);
            if (canPrint) {
                Log.d(getTag(), message);
            }
            if (canSave) {
                save("D", getTag(), message);
            }
        }
    }

    public static void i(String message, Object...args) {
        boolean canPrint = LogManager.canPrint(LogManager.LOG_LEVEL_INFO);
        boolean canSave = LogManager.canSave(LogManager.LOG_LEVEL_INFO);
        if (canPrint || canSave) {
            message = formatMessage(message, args);
            if (canPrint) {
                Log.i(getTag(), message);
            }
            if (canSave) {
                save("I", getTag(), message);
            }
        }
        
    }

    public static void w(String message, Object...args) {
        boolean canPrint = LogManager.canPrint(LogManager.LOG_LEVEL_WARN);
        boolean canSave = LogManager.canSave(LogManager.LOG_LEVEL_WARN);
        if (canPrint || canSave) {
            message = formatMessage(message, args);
            if (canPrint) {
                Log.w(getTag(), message);
            }
            if (canSave) {
                save("W", getTag(), message);
            }
        }
    }
    
    public static void w(Throwable e) {
        if (LogManager.canPrint(LogManager.LOG_LEVEL_WARN)) {
            e.printStackTrace();
        }
        if (LogManager.canSave(LogManager.LOG_LEVEL_WARN)) {
            save("W", getTag(), e.toString());
        }
    }

    public static void e(String message, Object...args) {
        boolean canPrint = LogManager.canPrint(LogManager.LOG_LEVEL_ERROR);
        boolean canSave = LogManager.canSave(LogManager.LOG_LEVEL_ERROR);
        if (canPrint || canSave) {
            message = formatMessage(message, args);
            if (canPrint) {
                Log.e(getTag(), message);
            }
            if (canSave) {
                save("E", getTag(), message);
            }
        }
    }
    
    public static void e(Throwable e) {
        if (LogManager.canPrint(LogManager.LOG_LEVEL_ERROR)) {
            e.printStackTrace();
        }
        if (LogManager.canSave(LogManager.LOG_LEVEL_ERROR)) {
            Writer w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            save("E", getTag(), w.toString());
        }
    }

    public static void v(String message, Object...args) {
        boolean canPrint = LogManager.canPrint(LogManager.LOG_LEVEL_TRACE);
        boolean canSave = LogManager.canSave(LogManager.LOG_LEVEL_TRACE);
        if (canPrint || canSave) {
            message = formatMessage(message, args);
            if (canPrint) {
                Log.v(getTag(), message);
            }
            if (canSave) {
                save("T", getTag(), message);
            }
        }
    }
    
    public static void t(String message, Object...args) {
        v(message, args);
    }
    
    public static void f(String message, Object...args) {
        boolean canPrint = LogManager.canPrint(LogManager.LOG_LEVEL_FATAL);
        boolean canSave = LogManager.canSave(LogManager.LOG_LEVEL_FATAL);
        if (canPrint || canSave) {
            message = formatMessage(message, args);
            if (canPrint) {
                Log.e(getTag(), message);
            }
            if (canSave) {
                save("F", getTag(), message);
            }
        }
    }
    
    public static void s(String message, Object...args) {
        boolean canPrint = LogManager.canPrint(LogManager.LOG_LEVEL_STAT);
        boolean canSave = LogManager.canSave(LogManager.LOG_LEVEL_STAT);
        if (canPrint || canSave) {
            message = formatMessage(message, args);
            if (canPrint) {
                Log.i(getTag(), message);
            }
            if (canSave) {
                save("S", getTag(), message);
            }
        }
        
    }
    
    private static String formatMessage(String message, Object...args) {
        if (message == null) {
            return "";
        }
        if (args != null && args.length > 0) {
            try {
                return String.format(message, args);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return message;
    }
    
    /**
     * 是否处于调试级别
     * @return
     */
    public static boolean isDebugMode() {
        return LogManager.isDebugMode();
    }
    /**
     * 是否为Money调试阶段
     * @return
     */
    public static boolean isMonkeyMode(){
        return LogManager.isMonkeyMode();
    }
    

    /**
     * 获取native日志tag
     * @return
     */
    private static String getTag() {
        StringBuilder sb = threadSafeStrBuilder.get();
        if (sb == null) {
            sb = new StringBuilder();
            threadSafeStrBuilder.set(sb);
        }
        sb.delete(0, sb.length());

        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
        String className = stackTrace.getClassName();
        sb.append(className.substring(className.lastIndexOf('.') + 1)).append('.')
                .append(stackTrace.getMethodName()).append('#').append(stackTrace.getLineNumber());
        return sb.toString();
    }
    
    /**
     * 打印到std
     * @param level
     * @param tag
     * @param message
     */
    private static void print(int level, String tag, String message) {
        if (message == null) {
            message = "";
        }
        switch (level) {
        case Log.VERBOSE:
            Log.v(tag, message);
            break;

        case Log.DEBUG:
            Log.d(tag, message);
            break;

        case Log.INFO:
            Log.i(tag, message);
            break;

        case Log.WARN:
            Log.w(tag, message);
            break;

        case Log.ERROR:
            Log.e(tag, message);
            break;

        }
    }
    
    /**
     * 获取level对应的文字描述
     * @param level
     * @return
     */
    public static String getLevelString(int level) {
        switch (level) {
        case LogManager.LOG_LEVEL_TRACE:
            return "TRACE";

        case LogManager.LOG_LEVEL_DEBUG:
            return "DEBUG";

        case LogManager.LOG_LEVEL_INFO:
            return "INFO";

        case LogManager.LOG_LEVEL_WARN:
            return "WARN";

        case LogManager.LOG_LEVEL_ERROR:
            return "ERROR";
            
        case LogManager.LOG_LEVEL_FATAL:
            return "FATAL";
            
        case LogManager.LOG_LEVEL_STAT:
            return "STAT";
            
        default:
            return "";
        }
    }

    /**
     * 获取level对应的文字描述
     * @param level
     * @return
     */
    public static String getShortLevelString(int level) {
        switch (level) {
        case LogManager.LOG_LEVEL_TRACE:
            return "T";

        case LogManager.LOG_LEVEL_DEBUG:
            return "D";

        case LogManager.LOG_LEVEL_INFO:
            return "I";

        case LogManager.LOG_LEVEL_WARN:
            return "W";

        case LogManager.LOG_LEVEL_ERROR:
            return "E";

        case LogManager.LOG_LEVEL_FATAL:
            return "F";

        case LogManager.LOG_LEVEL_STAT:
            return "S";

        default:
            return "";
        }
    }
    
    /**
     * 保存到发送队列
     * @param shortLevel
     * @param tag
     * @param message
     */
    private static void save(String shortLevel, String tag, String message) {

//        String str = L.getShortLevelString(level);

    }
    
    /**
     * 将h5日志级别转换为Android日志级别
     * @param webLevel
     * @return
     */
    private static int webLevel2LogLevel(int webLevel) {
        if (webLevel == WEB_LEVEL_LOG) {
            return LogManager.LOG_LEVEL_INFO;
        } else if (webLevel == WEB_LEVEL_WARNING) {
            return LogManager.LOG_LEVEL_WARN;
        } else if (webLevel == WEB_LEVEL_ERROR) {
            return LogManager.LOG_LEVEL_ERROR;
        } else if (webLevel == WEB_LEVEL_TIP || webLevel == WEB_LEVEL_DEBUG) {
            return LogManager.LOG_LEVEL_DEBUG;
        } else {
            return 0;
        }
    }

}
