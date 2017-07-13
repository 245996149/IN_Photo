package cn.inphoto.interceptor;

import cn.inphoto.dbentity.admin.ModuleInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by root on 17-7-12.
 */
public class CheckModuleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取登录用户有权限的所有模块
        List<ModuleInfo> modules = (List<ModuleInfo>)
                httpServletRequest.getSession().getAttribute("allModules");
        //获取用户当前要访问的模块
        int currentModule = (Integer)
                httpServletRequest.getSession().getAttribute("currentModule");
        //判断用户有权限的模块是否包含当前模块
        for (ModuleInfo module : modules) {
            if (module.getModuleId() == currentModule) {
                //有当前访问模块的权限
                return true;
            }
        }
        //没有当前访问模块的权限
        httpServletResponse.sendRedirect(
                httpServletRequest.getContextPath() + "/login/nopower.do");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
