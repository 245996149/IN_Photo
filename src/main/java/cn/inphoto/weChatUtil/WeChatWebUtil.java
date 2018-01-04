/**
 *
 */
package cn.inphoto.weChatUtil;


import cn.inphoto.weChatEntity.JsapiTicket;
import cn.inphoto.weChatEntity.WeChatOauth2Token;
import cn.inphoto.weChatEntity.WeChatToken;
import cn.inphoto.weChatEntity.WeChatWebUserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author ming.C
 * @date 2016年7月2日 下午6:51:28
 */
public class WeChatWebUtil {

    private static Logger logger = Logger.getLogger(WeChatWebUtil.class);

    /**
     * 获取网页授权凭证
     *
     * @param appId     公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static WeChatOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeChatOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = HttpRequest.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {

            logger.info("获取网页授权凭证中---------->" + "拼接AccessToken请求地址：" + requestUrl + "，接收到AccessToken的json：" + jsonObject.toString());
            try {
                wat = new WeChatOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.info("获取网页授权凭证中---------->" + "获取网页授权凭证失败: " + errorCode + "," + errorMsg);
            }
        }
        logger.info("获取网页授权凭证中---------->" + "获取到的网页授权凭证: " + wat.toString());
        return wat;
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @return SNSUserInfo
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public static WeChatWebUserInfo getWebUserInfo(String accessToken, String openId) {
        WeChatWebUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpRequest.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {

            logger.info("通过网页授权获取用户信息中---------->" + "拼接UserInfo请求地址：" + requestUrl + "，接收到User的json：" + jsonObject.toString());

            try {
                snsUserInfo = new WeChatWebUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.info("通过网页授权获取用户信息中---------->" + "获取用户信息失败: " + errorCode + "," + errorMsg);
            }
        }
        logger.info("通过网页授权获取用户信息中---------->" + "获取到的用户信息：" + snsUserInfo.toString());
        return snsUserInfo;
    }

    /**
     * 获取js接口临时票据
     *
     * @param token
     * @return
     */
    public static JsapiTicket getJsapiTicket(WeChatToken token) {
        JsapiTicket ticket = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token.getAccessToken());
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpRequest.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {

            logger.info("通过WeChatToken获取JsapiTicket中---------->" + "拼接JsapiTicket请求地址：" + requestUrl + "，接收到JsapiTicket的json：" + jsonObject.toString());

            try {
                ticket = new JsapiTicket();
                ticket.setTicket(jsonObject.getString("ticket"));
                ticket.setExpiresIn(jsonObject.getInt("expires_in"));
                ticket.setTime(System.currentTimeMillis());
            } catch (Exception e) {
                ticket = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.info("通过WeChatToken获取JsapiTicket中---------->" + "获取JsapiTicket失败: " + errorCode + "," + errorMsg);
            }
        }
        logger.info("通过WeChatToken获取JsapiTicket中---------->" + "获取到的JsapiTicket：" + ticket.toString());
        return ticket;
    }

    /**
     * 判断JsapiTicket是否在有效期内，返回一个有效的JsapiTicket
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public static JsapiTicket judgeJsapiTicketOvertime(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        ServletContext application = request.getSession().getServletContext();
        JsapiTicket ticket = (JsapiTicket) application.getAttribute("weixin_ticket");

        if (ticket == null) {
            // ticket为空，获取新的ticket
            ticket = getJsapiTicket(WeChatUtil.judgeWeChatTokenOvertime(request, response));
            application.setAttribute("weixin_ticket", ticket);
        } else if (ticket.getTime() + (7000 * 1000) < System.currentTimeMillis()) {
            // ticket超时，获取新的ticket
            ticket = getJsapiTicket(WeChatUtil.judgeWeChatTokenOvertime(request, response));
            application.setAttribute("weixin_ticket", ticket);
        }
        logger.info("判断JsapiTicket是否可用中---------->" + "比对后获得的JsapiTicket：" + ticket.toString());
        return ticket;
    }
}
