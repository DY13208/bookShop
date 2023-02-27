package bookshop.mySpringMvc.filters;

import bookshop.mySpringMvc.Util.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Alitar
 * @date 2023-02-21 16:16
 */
@WebFilter(urlPatterns = {"*.do","*.html"},initParams = {
        @WebInitParam(name = "bai",value = "/bookShop/page.do?operate=page&page=user/login,/bookShop/user.do?null,/bookShop/page.do?operate=page&page=user/regist")
})
public class SessionFilter implements Filter {
    List<String> strings= null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String bai = filterConfig.getInitParameter("bai");
        String[] split = bai.split(",");
        strings = Arrays.asList(split);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String substring = null;

        if (StringUtil.isNoEmpty(queryString)){
              substring = queryString.substring(8, 15);
        }
        String s = requestURI + "?" + queryString;
        if (strings.contains(s) || Objects.equals(substring, "ckUname")){
            filterChain.doFilter(request,response);
        }else {
            HttpSession session = request.getSession();
            Object currUser = session.getAttribute("currUser");
            if (currUser!=null){
//                System.out.println("登录放行");
                filterChain.doFilter(request,response);
            }else {
                response.sendRedirect("page.do?operate=page&page=login");
            }
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}