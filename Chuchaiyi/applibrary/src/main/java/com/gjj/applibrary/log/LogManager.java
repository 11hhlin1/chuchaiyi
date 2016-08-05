package com.gjj.applibrary.log;

/**
 * 
 * 类/接口注释
 * 
 * @author chuck
 * @createDate Dec 29, 2014
 *
 */
class LogManager {

    static final int LOG_LEVEL_TRACE = 1;
    static final int LOG_LEVEL_DEBUG = 2;
    static final int LOG_LEVEL_INFO  = 3;
    static final int LOG_LEVEL_WARN  = 4;
    static final int LOG_LEVEL_ERROR = 5;
    static final int LOG_LEVEL_FATAL = 6;
    static final int LOG_LEVEL_STAT = 7;
    static final int LOG_LEVEL_NO   = 255;
    
    /**
     * 当前日志打印级别
     * 打包测试包时为LOG_LEVEL_TRACE，即所有级别都会打印，
     * 打包release包时为LOG_LEVEL_NO，即所有级别都不会打印
     */
    private int printLevel = LOG_LEVEL_TRACE;
    /**
     * 当前日志上传级别
     * 打包测试包时为LOG_LEVEL_TRACE，即所有级别都不会上传，
     * 打包release包时为LOG_LEVEL_WARN，即LOG_LEVEL_WARN及以上级别都会打印
     */
    private int uploadLevel = LOG_LEVEL_TRACE;
    /**
     * 是否是Monkey包
     */
    private boolean isMonkeyRun = false;

    private static volatile LogManager mLogManager;
    
    private static LogManager getInstance() {
        if (mLogManager == null) {
            synchronized(LogManager.class) {
                if (mLogManager == null) {
                    mLogManager = new LogManager();
                }
            }
        }
        return mLogManager;
    }


    LogManager() {

    }

    /**
     * 指定日志级别是否达到打印级别
     * @param level
     * @return
     */
    static boolean canPrint(int level) {
        return level >= getInstance().printLevel;
    }

    /**
     * 指定日志级别是否达到上传级别
     * @param level
     * @return
     */
    static boolean canSave(int level) {
        return level >= getInstance().uploadLevel;
    }
    /**
     * 是否处于调试级别
     * @return
     */
    static boolean isDebugMode() {
        return getInstance().printLevel == LOG_LEVEL_TRACE;
    }
    /**
     * 是否处于Monkey测试阶段
     * @return
     */
    static boolean isMonkeyMode(){
        return getInstance().isMonkeyRun;
    }
}
