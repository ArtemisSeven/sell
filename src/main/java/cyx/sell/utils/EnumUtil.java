package cyx.sell.utils;

import cyx.sell.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each:enumClass.getEnumConstants()){
            if (each.getCode()==code){
                return each;
            }
        }
        return null;
    }
}
