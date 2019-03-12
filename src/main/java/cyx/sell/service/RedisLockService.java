package cyx.sell.service;

import cyx.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisLockService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 时间戳，等于当前时间+超时时间
     * @return
     */
    public boolean lock(String key,String value){
        if (redisTemplate.opsForValue().setIfAbsent(key,value)){//可以成功设置返回true,说明已获得锁
            return true;
        }
        //这一段代码是为了预防死锁（加锁后抛出异常导致执行不了解锁）
        String currentValue=redisTemplate.opsForValue().get(key);
        //如果锁过期
        if(!StringUtils.isEmpty(currentValue)&&Long.parseLong(currentValue)<System.currentTimeMillis()){//如果currentValue非空并且超过当前时间ze说明锁过期
            //获取上一个锁的时间
            String oldValue=redisTemplate.opsForValue().getAndSet(key,value);
            if (!StringUtils.isEmpty(oldValue)&&oldValue.equals(currentValue)){//如果oldValue非空并且等于当前时间
                return true;
            }
        }
        return false;
    }

    public void unLock(String key,String value){
        try {
            String currentValue=redisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue)&&currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【redis分布式锁】解锁异常");
        }
    }

//    public static void main(String[] args) {
//        //加锁
//        final int TIMEOUT=10*1000;//10s
//        Long time=System.currentTimeMillis()+TIMEOUT;
//        if(!redisTemplate.lock(productId,String.value(time))) {//秒杀商品的id作为key
//            throw new SellException(101,"太多人秒杀啦，换个姿势再试试吧");
//        }
//        //秒杀的其他业务逻辑...
//        //解锁
//        redisTemplate.unLock(productId,String.value(time));//秒杀商品的id作为key
//    }
}
