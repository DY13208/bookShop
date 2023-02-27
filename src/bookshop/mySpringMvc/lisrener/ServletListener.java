package bookshop.mySpringMvc.lisrener;




import bookshop.mySpringMvc.ioc.BeanFactory;
import bookshop.mySpringMvc.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Alitar
 * @date 2023-02-07 20:01
 */
@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("servlet:开启被监听到了");
        //1.获取ServletContext对象
        ServletContext application = sce.getServletContext();
        //2.获取上下文的初始化参数
        String path = application.getInitParameter("contextConfigLocation");
        //3.创建IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);
        //4.将IOC容器保存到application作用域
        application.setAttribute("beanFactory",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

//        System.out.println("servlet:销毁被监听到了");
    }
}