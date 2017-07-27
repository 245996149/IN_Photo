package cn.inphoto.util;

import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;

public class CookieUtil {

    /**
     *生成一个有效时间为7天的cookie
     * @param cookie_name 生成的cookie的名字，用于提取
     * @param cookie_content 生成的cookie的内容
     * @return cookie
     */
    public static Cookie createCookie(String cookie_name, String cookie_content) throws UnsupportedEncodingException {
        BASE64Encoder encoder = new BASE64Encoder();
        Cookie cookie = new Cookie(cookie_name,encoder.encode(cookie_content.getBytes("utf-8")));
        cookie.setMaxAge(60 * 60 * 24 * 7);
        return cookie;
    }

    /**
     * 清除一个cookie
     * @param cookie_name 要清除的cookie的名字
     * @return 该cookie
     */
    public static Cookie cleanCookie(String cookie_name) {
       return new Cookie(cookie_name,null);
    }

}
