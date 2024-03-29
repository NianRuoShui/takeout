package org.nian.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.nian.common.BaseContext;
import org.nian.common.Result;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private boolean check(String[] urls, String uri){
        for(String url :urls){
            boolean match = PATH_MATCHER.match(url, uri);
            log.info("匹配结果：{}，{}，{}",url,uri,match);
            if (match){
                return true;
            }
        }
        return false;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取uri
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}",requestURI);
        //定义不需要被拦截的请求
        //EmployeeController里login和logout是post请求，不能直接用这个访问，只能从/backend/**和/front/**访问
        //定义不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };
        //判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //如果是不需要被拦截的请求，直接放行
        if (check){
            //让请求继续走
            filterChain.doFilter(request, response);
            return;
        }
        //如果是需要被拦截的请求，判断是否已经登录
        if (request.getSession().getAttribute("employee") != null) {
            Long empId = (Long) request.getSession().getAttribute("employee");
            //使用BaseContext封装id
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        //如果没有登录，返回错误信息,通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    }
}
