package cn.inphoto.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaxia on 2017/6/6.
 */
@Controller
@RequestMapping("/receive")
public class ReceiveController {

    @RequestMapping("/receiveMedia.do")
    @ResponseBody
    public Map<String, Object> receiveMedia(HttpServletRequest request, HttpServletResponse response,
                                            String names, String type, String code, Integer second,
                                            Integer number, Integer userId) throws IOException {

        // 设置返回日志的Map
        Map<String, Object> result = new HashMap<>();

        if (names == null || type == null || userId == null) {

            // 判断必填参数是否为空，为空直接反馈给客户端
            result.put("code", "error");
            result.put("message", "userId、names、type三者为必填参数，不能为空");
            return result;

        }

        return result;

    }

}
