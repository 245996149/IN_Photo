package cn.inphoto.interceptor;


import org.apache.log4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by kaxia on 2017/6/13.
 * 过滤器
 */
public class ResFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器开始初始化了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 设置日志默认
        MDC.put("user_id", 0);
        MDC.put("category_id", 0);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁了");
    }
}
