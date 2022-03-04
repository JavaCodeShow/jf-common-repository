package com.jf.common.utils.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 江峰
 * @date 2022/3/4 16:13
 */
@WebFilter(filterName = "logbackFilter", urlPatterns = "/*")
public class LogbackFilter implements Filter {

    private static final String TRACE_UUID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UUID uuid = UUID.randomUUID();
        MDC.put(TRACE_UUID, uuid.toString().replace("-", ""));
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(TRACE_UUID);//保证一次请求一个唯一标识
        }
    }

    @Override
    public void destroy() {
    }
}
