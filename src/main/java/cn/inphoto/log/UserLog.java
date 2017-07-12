package cn.inphoto.log;

import org.apache.log4j.Level;

/**
 * 自定义日志级别
 */
public class UserLog extends Level {

    private static final long serialVersionUID = 1L;

    protected UserLog(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }

    /**
     * 定义log的权重为介于OFF和FATAL之间，便于打印LIFE级别的log
     */
    public static final int USER_INT = FATAL_INT + 10;

    public static final Level USER = new UserLog(USER_INT, "USER", 10);

    public static Level toLevel(String logArgument) {
        if (logArgument != null && logArgument.toUpperCase().equals("USER")) {
            return USER;
        }
        return (Level) toLevel(logArgument);
    }

    public static Level toLevel(int val) {
        if (val == USER_INT) {
            return USER;
        }
        return (Level) toLevel(val, Level.DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        if (val == USER_INT) {
            return USER;
        }
        return Level.toLevel(val, defaultLevel);
    }

    public static Level toLevel(String logArgument, Level defaultLevel) {
        if (logArgument != null && logArgument.toUpperCase().equals("USER")) {
            return USER;
        }
        return Level.toLevel(logArgument, defaultLevel);
    }

}