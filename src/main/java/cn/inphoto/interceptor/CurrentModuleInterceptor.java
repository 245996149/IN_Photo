package cn.inphoto.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 17-7-12.
 */
public class CurrentModuleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 判断当前用户访问的模块
        String url = httpServletRequest.getRequestURL().toString();
        int currentModule = 0; // 默认0是NETCTOSS首页
        if (url.contains("user")) {
            currentModule = 1;
        } else if (url.contains("category")) {
            currentModule = 2;
        } else if (url.contains("adminManage")) {
            currentModule = 3;
        }

        httpServletRequest.getSession().setAttribute(
                "currentModule", currentModule);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
