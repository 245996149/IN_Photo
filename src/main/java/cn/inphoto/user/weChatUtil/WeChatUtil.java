package cn.inphoto.user.weChatUtil;


import cn.inphoto.user.weChatEntity.WeChatToken;
import cn.inphoto.user.weChatEntity.WeChatUserInfo;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.inphoto.user.util.DirUtil.getConfigInfo;

/**
 * 获取公众号AccessToken
 *
 * @author Ming.c
 * @date 2016年7月5日下午4:24:59
 */
public class WeChatUtil {

    private static Logger logger = Logger.getLogger(WeChatUtil.class);

    static String appid = getConfigInfo("appid");
    static String appsecret = getConfigInfo("appsecret");

    /**
     * 获取公众号AccessToken授权凭证
     *
     * @param appId
     * @param appSecret
     * @return
     */
    public static WeChatToken getAccessToken(String appId, String appSecret) {
        WeChatToken wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("APPSECRET", appSecret);
        // 获取网页授权凭证
        JSONObject jsonObject = HttpRequest.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            logger.info("获取公众号Token授权凭证---------->"+ "拼接WeChatToken请求地址：" + requestUrl + "，接收到WeChatToken的json：" + jsonObject.toString());
            try {
                wat = new WeChatToken();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setTime(System.currentTimeMillis());
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.info("获取公众号Token授权凭证---------->" + "获取WeChatToken授权凭证失败: " + errorCode + "," + errorMsg);
            }
        }
        logger.info("获取公众号Token授权凭证---------->" + "获取到的公众号Token：" + wat.toString());
        return wat;
    }

    /**
     * 判断WeChatToken是否在有效期内，返回一个有效的WeChatToken
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public static WeChatToken judgeWeChatTokenOvertime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        ServletContext application = request.getSession().getServletContext();
        WeChatToken token = (WeChatToken) application.getAttribute("token");

        if (token == null) {
            logger.info("判断WeChatToken是否有效中---------->" + "token为空，获取新的token");
            token = WeChatUtil.getAccessToken(appid, appsecret);
            application.setAttribute("token", token);
        } else if (token.getTime() + (7000 * 1000) < System.currentTimeMillis()) {
            logger.info("判断WeChatToken是否有效中---------->" + "token即将超时，获取新的token");
            token = WeChatUtil.getAccessToken(appid, appsecret);
            application.setAttribute("token", token);
        } else {
            logger.info("判断WeChatToken是否有效中---------->" + "token可以使用，" + application.getAttribute("token").toString());

        }
        logger.info("判断WeChatToken是否有效中---------->" + "比对后获得的WeChatToken：" + token.toString());
        return token;
    }

    /**
     * 获取公众号的用户信息
     *
     * @param token
     * @param openid
     * @return
     */
    public static WeChatUserInfo getWeChatUserInfo(String token, String openid) {
        WeChatUserInfo wcUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
        requestUrl = requestUrl.replace("OPENID", openid);
        JSONObject jsonObject = HttpRequest.httpsRequest(requestUrl, "GET", null);
        logger.info("获取公众号的用户信息中--------->" + "拼接WeChatUserInfo请求地址：" + requestUrl+ "，接收到UserInfo的json：" + jsonObject.toString());
        if (jsonObject != null) {
            try {
                wcUserInfo = new WeChatUserInfo();
                wcUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                wcUserInfo.setCity(jsonObject.getString("city"));
                wcUserInfo.setCountry(jsonObject.getString("country"));
                wcUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                wcUserInfo.setLanguage(jsonObject.getString("language"));
                wcUserInfo.setNickname(jsonObject.getString("nickname"));
                wcUserInfo.setOpenId(jsonObject.getString("openid"));
                wcUserInfo.setProvince(jsonObject.getString("province"));
                wcUserInfo.setSex(jsonObject.getInt("sex"));
                wcUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
            } catch (Exception e) {
                wcUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.info("获取公众号的用户信息中--------->" + "获取用户信息失败: " + errorCode + "," + errorMsg);
            }
        }
        logger.info("获取公众号的用户信息中--------->" + "获取到的公众号的用户信息为：" + wcUserInfo.toString());
        return wcUserInfo;
    }
}
