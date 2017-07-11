package cn.inphoto.user.log;

import org.apache.log4j.Level;

/**
 * Created by kaxia on 2017/6/23.
 */
public class TaskLog extends Level {

    private static final long serialVersionUID = 1L;

    protected TaskLog(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }

    /**
     * 定义log的权重为介于OFF和FATAL之间，便于打印LIFE级别的log
     */
    public static final int TASK_INT = FATAL_INT + 20;

    public static final Level TASK = new UserLog(TASK_INT, "USER", 10);

    public static Level toLevel(String logArgument) {
        if (logArgument != null && logArgument.toUpperCase().equals("USER")) {
            return TASK;
        }
        return (Level) toLevel(logArgument);
    }

    public static Level toLevel(int val) {
        if (val == TASK_INT) {
            return TASK;
        }
        return (Level) toLevel(val, Level.DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        if (val == TASK_INT) {
            return TASK;
        }
        return Level.toLevel(val, defaultLevel);
    }

    public static Level toLevel(String logArgument, Level defaultLevel) {
        if (logArgument != null && logArgument.toUpperCase().equals("USER")) {
            return TASK;
        }
        return Level.toLevel(logArgument, defaultLevel);
    }
}
