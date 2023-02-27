package bookshop.mySpringMvc.ioc;


import bookshop.mySpringMvc.Util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alitar
 * @date 2023-01-30 17:14
 */
public class ClassPathXmlApplicationContext implements BeanFactory{
    private Map<String,Object> beanMap = new HashMap<>();
    private String path = "applicationContext.xml" ;
    public ClassPathXmlApplicationContext(){
        this("applicationContext.xml");
    }
    public ClassPathXmlApplicationContext(String path){
        if(StringUtil.isEmpty(path)){
            throw new RuntimeException("IOC�����������ļ�û��ָ��...");
        }
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            //1.����DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.����DocumentBuilder����
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder() ;
            //3.����Document����
            Document document = documentBuilder.parse(inputStream);

            //4.��ȡ���е�bean�ڵ�
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for(int i = 0 ; i<beanNodeList.getLength() ; i++){
                Node beanNode = beanNodeList.item(i);
                if(beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element)beanNode ;
                    String beanId =  beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class beanClass = Class.forName(className);
                    //����beanʵ��
                    Object beanObj = beanClass.getDeclaredConstructor().newInstance() ;
                    //��beanʵ�����󱣴浽map������
                    beanMap.put(beanId , beanObj) ;
                    //��ĿǰΪֹ���˴���Ҫע����ǣ�bean��bean֮���������ϵ��û������
                }
            }
            //5.��װbean֮���������ϵ
            for(int i = 0 ; i<beanNodeList.getLength() ; i++){
                Node beanNode = beanNodeList.item(i);
                if(beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    NodeList beanChildNodeList = beanElement.getChildNodes();
                    for (int j = 0; j < beanChildNodeList.getLength() ; j++) {
                        Node beanChildNode = beanChildNodeList.item(j);
                        if(beanChildNode.getNodeType()==Node.ELEMENT_NODE && "property".equals(beanChildNode.getNodeName())){
                            Element propertyElement = (Element) beanChildNode;
                            String propertyName = propertyElement.getAttribute("name");
                            String propertyRef = propertyElement.getAttribute("ref");
                            //1) �ҵ�propertyRef��Ӧ��ʵ��
                            Object refObj = beanMap.get(propertyRef);
                            //2) ��refObj���õ���ǰbean��Ӧ��ʵ����property������ȥ
                            Object beanObj = beanMap.get(beanId);
                            Class beanClazz = beanObj.getClass();
                            Field propertyField = beanClazz.getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj,refObj);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}