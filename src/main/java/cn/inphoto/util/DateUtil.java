package cn.inphoto.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取今天零时零分零秒的Date
     *
     * @return 今天零时零分零秒的Date
     */
    public static Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}
