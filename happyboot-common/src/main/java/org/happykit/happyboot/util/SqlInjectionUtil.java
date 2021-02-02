package org.happykit.happyboot.util;

/**
 * sql注入处理工具类
 *
 * @author zhoujf
 */
public class SqlInjectionUtil {
    final static String xssStr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,";

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param value
     * @return
     */
    public static void filterContent(String value) {
        if (value == null || "".equals(value)) {
            return;
        }
        value = value.toLowerCase();// 统一转为小写
        String[] xssArr = xssStr.split("\\|");
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                System.out.println("请注意，值可能存在SQL注入风险!---> {}" + value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param value
     * @return
     */
    public static void filterContent(String[] values) {
        String[] xssArr = xssStr.split("\\|");
        for (String value : values) {
            if (value == null || "".equals(value)) {
                return;
            }
            value = value.toLowerCase();// 统一转为小写
            for (int i = 0; i < xssArr.length; i++) {
                if (value.indexOf(xssArr[i]) > -1) {
                    System.out.println("请注意，值可能存在SQL注入风险!---> {}" + value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }
        }
        return;
    }

    /**
     * @param value
     * @return
     * @特殊方法(不通用) 仅用于字典条件SQL参数，注入过滤
     */
    @Deprecated
    public static void specialFilterContent(String value) {
        String specialXssStr = "exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|+|,";
        String[] xssArr = specialXssStr.split("\\|");
        if (value == null || "".equals(value)) {
            return;
        }
        value = value.toLowerCase();// 统一转为小写
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                System.out.println("请注意，值可能存在SQL注入风险!---> {}" + value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }

}
