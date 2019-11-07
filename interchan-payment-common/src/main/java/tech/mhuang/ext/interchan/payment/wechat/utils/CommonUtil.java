package tech.mhuang.ext.interchan.payment.wechat.utils;


import tech.mhuang.core.util.StringUtil;

import java.math.BigDecimal;

public class CommonUtil {
    /**
     * 除法
     * @param arg1 arg1
     * @param arg2 arg2
     * @return BigDecimal
     */
    public static BigDecimal divide(String arg1, String arg2) {
        return convertBigDesimal(arg1, arg2, BigDesimalType.DIVIDE);
    }

    /**
     * 乘法
     * @param arg1 arg1
     * @param arg2 arg2
     * @return BigDecimal
     */
    public static BigDecimal mul(String arg1, String arg2) {
        return convertBigDesimal(arg1, arg2, BigDesimalType.MULTIPLY);
    }

    /**
     * 减法
     * @param arg1 arg1
     * @param arg2 arg2
     * @return BigDecimal
     */
    public static BigDecimal sub(String arg1, String arg2) {
        return convertBigDesimal(arg1, arg2, BigDesimalType.SUBTRACT);
    }

    /**
     * 加法
     * @param arg1 arg1
     * @param arg2 arg2
     * @return BigDecimal
     */
    public static BigDecimal add(String arg1, String arg2) {
        return convertBigDesimal(arg1, arg2, BigDesimalType.ADD);
    }

    /**
     * 加法
     * @param arg1 arg1
     * @param arg2 arg2
     * @return String
     */
    public static String add2(String arg1, String arg2) {
        return add(arg1, arg2).toString();
    }

    private static enum BigDesimalType {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    private static BigDecimal convertBigDesimal(String arg1, String arg2, BigDesimalType type) {
        if (StringUtil.isEmpty(arg1)) {
            arg1 = "0.0";
        }
        if (StringUtil.isEmpty(arg2)) {
            arg2 = "0.0";
        }
        BigDecimal big1 = new BigDecimal(arg1);
        BigDecimal big2 = new BigDecimal(arg2);
        BigDecimal big3 = null;
        if (type == BigDesimalType.ADD) {
            big3 = big1.add(big2);
        } else if (type == BigDesimalType.SUBTRACT) {
            big3 = big1.subtract(big2);
        } else if (type == BigDesimalType.MULTIPLY) {
            big3 = big1.multiply(big2);
        } else if (type == BigDesimalType.DIVIDE) {
            if (Double.parseDouble(arg2) != 0) {
                big3 = big1.divide(big2, 2, BigDecimal.ROUND_HALF_EVEN);
            } else {
                big3 = new BigDecimal(0.0);
            }
        }
        return big3;
    }

    /**
     * 四舍五入保留N位小数 先四舍五入在使用double值自动去零
     *
     * @param arg   参数
     * @param scare 保留位数
     * @return String
     */
    public static String setScare(BigDecimal arg, int scare) {
        BigDecimal bl = arg.setScale(scare, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(bl.doubleValue());
    }

    public static double setDifScare(double arg) {
        BigDecimal bd = new BigDecimal(arg);
        BigDecimal bl = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return Double.parseDouble(bl.toString());
    }

    /**
     * 四舍五入保留两位小数 先四舍五入在使用double值自动去零
     *
     * @param arg 参数
     * @return String
     */
    public static String setDifScare(String arg) {
        BigDecimal bd = new BigDecimal(arg);
        BigDecimal bl = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bl.toString();
    }

    /**
     * 四舍五入保留N位小数 先四舍五入在使用double值自动去零（传参String类型）
     *
     * @param arg 参数
     * @param i   保留的几位小数
     * @return String
     */
    public static String setDifScare(String arg, int i) {
        BigDecimal bd = new BigDecimal(arg);
        BigDecimal bl = bd.setScale(i, BigDecimal.ROUND_HALF_UP);
        return bl.toString();
    }

    /**
     * 转化成百分数 先四舍五入在使用double值自动去零
     *
     * @param arg 参数
     * @return String
     */
    public static String setFenScare(BigDecimal arg) {
        BigDecimal bl = arg.setScale(3, BigDecimal.ROUND_HALF_UP);
        String scare = String.valueOf(mul(bl.toString(), "100").doubleValue());
        String fenScare = scare + "%";
        return fenScare;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s 参数
     * @return String
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");
            // 去掉多余的0
            s = s.replaceAll("[.]$", "");
            // 如最后一位是.则去掉
        }
        return s;
    }
}