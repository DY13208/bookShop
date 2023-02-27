package bookshop.mySpringMvc.ioc;

/**
 * @author Alitar
 * @date 2023-01-30 17:13
 */
public interface BeanFactory {
    Object getBean(String id);
}
