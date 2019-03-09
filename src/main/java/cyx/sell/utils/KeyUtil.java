package cyx.sell.utils;

import java.util.Random;

/**
 * 生成唯一的随机字符串
 */
public class KeyUtil {
    public static synchronized String getUniqueKey(){//加上synchronized避免在多线程环境下key值一样
        Random random=new Random();
        Integer num=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(num);
    }
}
