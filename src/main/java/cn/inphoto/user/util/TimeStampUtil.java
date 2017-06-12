package cn.inphoto.user.util;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by kaxia on 2017/6/12.
 */
public class TimeStampUtil {

    /**
     * 输入两个时间戳，判断两个时间戳大小。
     *
     * @param t1
     * @param t2
     * @return 返回1，t1在t2之前；
     * 返回-1，t1在t2之后；
     * 返回0，ti=t2
     */
    public static int judgeTimeStamp(Timestamp t1, Timestamp t2) {

        if (t1.getTime() < t2.getTime()) {
            return 1;
        } else if (t1.getTime() > t2.getTime()) {
            return -1;
        } else {
            return 0;
        }

    }

    @Test
    public void judgeTimeStampTest() throws ParseException {

        Timestamp t1 = Timestamp.valueOf("2017-1-2 11:22:33");
        Timestamp t2 = Timestamp.valueOf("2017-1-2 11:22:33");

        System.out.println(judgeTimeStamp(t1,t2));
    }


}
