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
 * ��������������ܴӿͻ��˴�����һ������
 * ������ͨ��������������ҵ
 *
 */
//*.do��������һ����.do��β������
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
            throw new RuntimeException("IOC�����쳣����");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //���ñ��� �Ѿ��ڹ�������ʵ��CharacterEncodingFilter
     //   req.setCharacterEncoding("utf-8");

        //����uri��;http://localhost:8080/pro15/hello.do//��ôservlet·����:/hell.do
        //��1��:/nello.do -> hello
        //�ڶ��� -> HelloController
        String servletPath = req.getServletPath();
            //��ȡ�ַ��� �õ���hello��
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
                    //1.ͳһ��ȡ�������

                    //��ȡ��ǰ�����Ĳ��������ز�������
                    Parameter[] parameters = m.getParameters();
                    //parameterValues �������ز�����ֵ
                    Object[] parameterValues = new Object[parameters.length];

                    for (int i=0;i<parameters.length;i++){

                        Parameter parameter = parameters[i];
                        //��ȡ������
                        String parameterName = parameter.getName();

                        //�����������request,response,session ��ô�Ͳ���ͨ�������л�ȡ�����ķ�ʽ��
                        if ("req".equals(parameterName)){
                            parameterValues[i]=req;
                        }else if ("resp".equals(parameterName)){
                            parameterValues[i]=resp;
                        }else if ("session".equals(parameterName)){
                            parameterValues[i] = req.getSession();
                        }else {
                            //�������л�ȡ����
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


                    //Controller�������
                    m.setAccessible(true);
                    Object returnObj = m.invoke(controllerBeanObj,parameterValues);
                    String methodReturnStr =   (String)returnObj;

                    //3.��ͼ����
                    //�����redirect:��ͷ
                    if(StringUtil.isEmpty(methodReturnStr)){
                        //���Ϊnull�򲻽��д���
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
