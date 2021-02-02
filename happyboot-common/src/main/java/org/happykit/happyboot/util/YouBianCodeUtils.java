package org.happykit.happyboot.util;


import org.apache.commons.lang3.StringUtils;

/**
 * 流水号生成规则(按默认规则递增，数字从1-99开始递增，数字到99，递增字母;位数不够增加位数)
 *
 * @author shaoqiang
 * @version 1.0 2020/3/25
 */
public class YouBianCodeUtils {
    /**
     * 数字位数
     */
    private static final int NUM_LENGTH = 4;

    private static final char Z = 'Z';


    /**
     * 根据前一个code，获取同级下一个code
     * 例如:当前最大code为D01A04，下一个code为：D01A05
     *
     * @param code
     * @return
     */
    public static synchronized String getNextYouBianCode(String code) {
        String newCode = "";
        if (StringUtils.isNotBlank(code)) {
            String beforeCode = code.substring(0, code.length() - 1 - NUM_LENGTH);
            String afterCode = code.substring(code.length() - 1 - NUM_LENGTH, code.length());
            char afterCodeZimu = afterCode.substring(0, 1).charAt(0);
            Integer afterCodeNum = Integer.parseInt(afterCode.substring(1));
            String nextNum = "";
            char nextZimu = 'A';

            // 先判断数字等于999*，则计数从1重新开始，递增
            if (afterCodeNum == getMaxNumByLength(NUM_LENGTH)) {
                nextNum = getNextStrNum(0);
            } else {
                nextNum = getNextStrNum(afterCodeNum);
            }

            // 先判断数字等于999*，则字母从A重新开始,递增
            if (afterCodeNum == getMaxNumByLength(NUM_LENGTH)) {
                nextZimu = getNextZiMu(afterCodeZimu);
            } else {
                nextZimu = afterCodeZimu;
            }

            // 例如Z99，下一个code就是Z99A01
            if (Z == afterCodeZimu && getMaxNumByLength(NUM_LENGTH) == afterCodeNum) {
                newCode = code + (nextZimu + nextNum);
            } else {
                newCode = beforeCode + (nextZimu + nextNum);
            }
        } else {
            String zimu = "A";
            String num = getStrNum(1);
            newCode = zimu + num;
        }

        return newCode;
    }

    /**
     * 根据父亲code,获取下级的下一个code
     *
     * @param parentCode 上级code
     * @param localCode  同级code
     * @return
     */
    public static synchronized String getSubYouBianCode(String parentCode, String localCode) {
        if (StringUtils.isNotBlank(localCode)) {
            return getNextYouBianCode(localCode);
        } else {
            parentCode = parentCode + "A" + getNextStrNum(0);
        }
        return parentCode;
    }


    /**
     * 将数字前面位数补零
     *
     * @param num
     * @return
     */
    private static String getNextStrNum(int num) {
        return getStrNum(getNextNum(num));
    }

    /**
     * 将数字前面位数补零
     *
     * @param num
     * @return
     */
    private static String getStrNum(int num) {
        String s = String.format("%0" + NUM_LENGTH + "d", num);
        return s;
    }


    /**
     * 递增获取下个数字
     *
     * @param num
     * @return
     */
    private static int getNextNum(int num) {
        num++;
        return num;
    }

    /**
     * 递增获取下个字母
     *
     * @param zimu
     * @return
     */
    private static char getNextZiMu(char zimu) {
        if (zimu == Z) {
            return 'A';
        }
        zimu++;
        return zimu;
    }


    /**
     * 根据数字位数获取最大值
     *
     * @param length
     * @return
     */
    private static int getMaxNumByLength(int length) {
        if (length == 0) {
            return 0;
        }
        StringBuilder maxNum = new StringBuilder();
        for (int i = 0; i < length; i++) {
            maxNum.append("9");
        }
        return Integer.parseInt(maxNum.toString());
    }


    public static void main(String[] args) {
        System.out.println(getNextYouBianCode("A0001"));
        System.out.println(getSubYouBianCode("A0001A0002", "A0001A0010"));
    }
}
