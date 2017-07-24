package cn.inphoto.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断当前模块拦截器
 * Created by root on 17-7-12.
 */
public class CurrentAdminModuleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 判断当前用户访问的模块
        String url = httpServletRequest.getRequestURL().toString();
        int currentModule = 0; // 默认0是首页
        if (url.contains("clientManage")) {
            currentModule = 1;
        } else if (url.contains("categoryManage")) {
            currentModule = 2;
        } else if (url.contains("userManage")) {
            currentModule = 3;
        } else if (url.contains("roleManage")) {
            currentModule = 4;
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
