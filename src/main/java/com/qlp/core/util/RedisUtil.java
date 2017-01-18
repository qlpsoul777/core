package com.qlp.core.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {
	
	private static final long  EXPIRE_TIME = 24L;//缓存失效时间
	
	private static RedisTemplate<Serializable, Object> redisTemplate;
	
	 /**
     * 保存数据至Redis,数据将按照unit时间单位保持timeout时长
     * @param key     
     * @param value
     * @param timeout  保存时长
     * @param unit     时间单位{@link TimeUnit}
     */
    public static void set(Serializable key, Object value, Long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }
    
    /**
     * 保存数据至Redis,数据不过期
     * @param key
     * @param value
     */
    public static void set(Serializable key, Object value){
    	redisTemplate.opsForValue().set(key, value);
    }
    
    /**
     * 保存数据至Redis,数据不过期
     * @param key
     * @param value
     */
    public static void setWithTime(Serializable key, Object value){
    	redisTemplate.opsForValue().set(key, value,EXPIRE_TIME,TimeUnit.HOURS);
    }
	
    /**
     * 泛型方法.通过key从Redis中取缓存数据
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Serializable key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    /**
     * 通过key从Redis中取缓存数据
     * @param key
     * @return
     */
    public static Object get(Serializable key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 根据key从Redis中删除缓存数据
     * @param key
     */
    public static void delete(Serializable... key) {
        redisTemplate.delete(Arrays.asList(key));
    }

    /**
     * 根据key模糊匹配并删除缓存数据
     * @param pattern
     */
    public static void deletePattern(Serializable... key) {
        for (Serializable pattern : key) {
            redisTemplate.delete(redisTemplate.keys(pattern + "*"));
        }
    }

    /**
     * 根据key判断数据是否存在Redis中
     * @param key
     * @return
     */
    public static boolean exists(Serializable key) {
        return redisTemplate.hasKey(key);
    }

}
