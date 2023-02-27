package bookshop.mySpringMvc.Util;

/**
 * @author Alitar
 * @date 2023-01-16 21:43
 */
public class StringUtil {

    public static boolean isEmpty(String str){

        return str==null  || "".equals(str);
    }
    public static boolean isNoEmpty(String str){

        return !isEmpty(str);
    }
}