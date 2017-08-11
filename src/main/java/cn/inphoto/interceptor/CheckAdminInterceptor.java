package cn.inphoto.interceptor;

import cn.inphoto.dbentity.admin.AdminInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 登陆拦截器
 *
 * @author Ming.C
 * @date 17-3-7 上午11:18
 */
public class CheckAdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        AdminInfo admin = (AdminInfo) session.getAttribute("adminUser");
        if (admin == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/admin/login/toLogin.do");
            return false;
        } else {
            // 判断当前用户访问的模块
            String url = httpServletRequest.getRequestURL().toString();
            int currentModule = 0; // 默认0是首页
            if (url.contains("clientManage")) {
                currentModule = 1;
            } else if (url.contains("categoryManage")) {
                currentModule = 2;
            } else if (url.contains("adminManage")) {
                currentModule = 3;
            } else if (url.contains("roleManage")) {
                currentModule = 4;
            }

            httpServletRequest.getSession().setAttribute(
                    "currentModule", currentModule);

            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
