package bookshop.mySpringMvc;




import bookshop.mySpringMvc.Util.StringUtil;
import bookshop.mySpringMvc.ioc.BeanFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Alitar
 * @date 2023-01-28 19:18
 * 反射控制器，接受从客户端传来的一切请求
 * 把请求通过反射来进行作业
 *
 */
//*.do可以拦截一切以.do结尾的请求
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;
//    private Map<String,Object> beanMap = new HashMap<>();
   public DispatcherServlet(){

   }

    @Override
    public void init() throws ServletException {
        super.init();
      //  beanFactory = new ClassPathXmlApplicationContext();
        ServletContext servletContext = getServletContext();
        Object beanFactoryObj = servletContext.getAttribute("beanFactory");
        if (beanFactoryObj!=null){
            beanFactory = (BeanFactory) beanFactoryObj;
        }else {
            throw new RuntimeException("IOC容器异常！！");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码 已经在过滤器内实现CharacterEncodingFilter
     //   req.setCharacterEncoding("utf-8");

        //假设uri是;http://localhost:8080/pro15/hello.do//那么servlet路径是:/hell.do
        //第1步:/nello.do -> hello
        //第二部 -> HelloController
        String servletPath = req.getServletPath();
            //截取字符串 得到“hello”
          servletPath = servletPath.substring(1);
        int lastDotIndex  = servletPath.lastIndexOf(".do") ;
        servletPath = servletPath.substring(0,lastDotIndex);


        Object controllerBeanObj = beanFactory.getBean(servletPath);


        String operate = req.getParameter("operate");
        if (StringUtil.isEmpty(operate)){
            operate="index";
        }

        try {
            Method[] declaredMethods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method m:declaredMethods){
                if (operate.equals(m.getName())){
                    //1.统一获取请求参数

                    //获取当前方法的参数，返回参数数组
                    Parameter[] parameters = m.getParameters();
                    //parameterValues 用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];

                    for (int i=0;i<parameters.length;i++){

                        Parameter parameter = parameters[i];
                        //获取参数名
                        String parameterName = parameter.getName();

                        //如果参数名是request,response,session 那么就不是通过请求中获取参数的方式广
                        if ("req".equals(parameterName)){
                            parameterValues[i]=req;
                        }else if ("resp".equals(parameterName)){
                            parameterValues[i]=resp;
                        }else if ("session".equals(parameterName)){
                            parameterValues[i] = req.getSession();
                        }else {
                            //从请求中获取参数
                            String parameterValue = req.getParameter(parameterName);
                            String typeName = parameter.getType().getName();
                            Object parameterObj = parameterValue;
                            if (parameterObj!=null) {
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                } else if ("java.lang.Double".equals(typeName)) {
                                    parameterObj = Double.parseDouble(parameterValue);
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }

                    }


                    //Controller组件调用
                    m.setAccessible(true);
                    Object returnObj = m.invoke(controllerBeanObj,parameterValues);
                    String methodReturnStr =   (String)returnObj;

                    //3.视图处理
                    //如果以redirect:开头
                    if(StringUtil.isEmpty(methodReturnStr)){
                        //如果为null则不进行处理
                        return;
                    }
                    if (methodReturnStr.startsWith("redirect:")){
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else if (methodReturnStr.startsWith("json:")){
                        String jsonStr = methodReturnStr.substring("json:".length());
                        PrintWriter writer = resp.getWriter();
                        writer.print(jsonStr);
                        writer.flush();
                    }else{
                        super.processTemplate(methodReturnStr,req,resp);
                    }
                }
            }

        } catch ( IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        }



    }
