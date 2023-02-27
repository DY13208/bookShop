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
//        System.out.println("servlet:��������������");
        //1.��ȡServletContext����
        ServletContext application = sce.getServletContext();
        //2.��ȡ�����ĵĳ�ʼ������
        String path = application.getInitParameter("contextConfigLocation");
        //3.����IOC����
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);
        //4.��IOC�������浽application������
        application.setAttribute("beanFactory",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

//        System.out.println("servlet:���ٱ���������");
    }
}