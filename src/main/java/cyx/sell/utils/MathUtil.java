package cyx.sell.utils;

public class MathUtil {
    private static final Double MONEY_RANGE=0.01;

    /**
     * 判断两个金额是否相等，如果相减后小于0。01 则认为相等
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Double a,Double b){
        Double result=Math.abs(a-b);
        if (result<MONEY_RANGE){
            return true;
        }
        else return false;
    }
}
