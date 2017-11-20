package cn.inphoto.weibo;

import cn.inphoto.weChatUtil.HttpRequest;
import cn.inphoto.weChatUtil.WeChatUtil;
import cn.inphoto.weibo.entity.JsapiTicket;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class WeiboService {

    private static Logger logger = Logger.getLogger(WeiboService.class);

    public static String appKey;

    public static String appsecret;

    @Value("#{properties['weibo_appKey']}")
    public void setAppKey(String a) {
        appKey = a;
    }

    @Value("#{properties['weixin_appsecret']}")
    public void setAppsecret(String a) {
        appsecret = a;
    }

    /**
     * 通过微博的应用appKey跟appsecret获取到微博的JsapiTicket
     *
     * @return
     */
    public static JsapiTicket getWeiboTicket() {

        JsapiTicket ticket = null;
        // 拼接请求地址
        String requestUrl = "https://api.weibo.com/oauth2/js_ticket/generate?client_id=APPKEY&client_secret=APPSECRET";
        requestUrl = requestUrl.replace("APPKEY", appKey);
        requestUrl = requestUrl.replace("APPSECRET", appsecret);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpRequest.httpsRequest(requestUrl, "POST", null);

        if (null != jsonObject) {

            logger.info("请求微博获取JsapiTicket中---------->" + "拼接JsapiTicket请求地址：" + requestUrl + "，接收到JsapiTicket的json：" + jsonObject.toString());

            try {
                ticket = new JsapiTicket();
                ticket.setJsTicket(jsonObject.getString("js_ticket"));
                ticket.setExpiresTime(jsonObject.getInt("expire_time"));
                ticket.setTime(System.currentTimeMillis());
            } catch (Exception e) {
                ticket = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("error");
                logger.info("请求微博获取JsapiTicket中---------->" + "获取JsapiTicket失败: " + errorCode + "," + errorMsg);
            }
        }
        return ticket;
    }

    public static JsapiTicket judgeWeiboJsapiTicketOvertime(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        ServletContext application = request.getSession().getServletContext();
        JsapiTicket ticket = (JsapiTicket) application.getAttribute("weibo_ticket");

        if (ticket == null) {
            // ticket为空，获取新的ticket
            logger.info("微博的ticket为空，获取新的ticket");
            ticket = getWeiboTicket();
            application.setAttribute("weibo_ticket", ticket);
        } else if (ticket.getTime() + (7000 * 1000) < System.currentTimeMillis()) {
            // ticket超时，获取新的ticket
            logger.info("微博的ticket超时，获取新的ticket");
            ticket = getWeiboTicket();
            application.setAttribute("weibo_ticket", ticket);
        }

        logger.info("判断微博的JsapiTicket是否可用中---------->" + "比对后获得的微博JsapiTicket：" + ticket.toString());

        return ticket;

    }

}
