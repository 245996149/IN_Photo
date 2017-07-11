package cn.inphoto.user.interceptor;

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
        Integer admin = (Integer) session.getAttribute("adminUser");
        if (admin == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/toAdminLogin.do");
            return false;
        } else if (admin == 1){
            return true;
        }else{
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/toAdminLogin.do");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
