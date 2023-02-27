package bookshop.mySpringMvc.filters;


import bookshop.mySpringMvc.Util.StringUtil;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alitar
 * @date 2023-02-01 19:07
 */
//@WebFilter("*.do")
public class CharacterEncodingFilters implements Filter {

    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        String encodingStr = filterConfig.getInitParameter("encoding");
        if (StringUtil.isNoEmpty(encodingStr)){
            encoding= encodingStr;
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("CharacterEncoding Filter ......");
//        System.out.println(encoding);
        ((HttpServletRequest)servletRequest).setCharacterEncoding(encoding);
        ((HttpServletResponse)servletResponse).setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest,servletResponse);
//        System.out.println("CharacterEncoding Filter ok !");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}