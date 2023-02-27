package bookshop.mySpringMvc.filters;

import bookshop.mySpringMvc.trans.TransactionManager;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Alitar
 * @date 2023-02-01 19:51
 */
//ÊÂÎñ
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      //  Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            TransactionManager.beginTrans();
            filterChain.doFilter(servletRequest,servletResponse);
            TransactionManager.commit();
        }catch (Exception e){
            e.printStackTrace();
            TransactionManager.rollback();
        }

    }
    @Override
    public void destroy() {
      //  Filter.super.destroy();
    }
}