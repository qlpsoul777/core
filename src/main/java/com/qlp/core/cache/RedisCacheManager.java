package com.qlp.core.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings({"unchecked","rawtypes"})
public class RedisCacheManager implements CacheManager,Destroyable{

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();
	
	private  RedisTemplate<String, Object> redisTemplate;
	private  String keyPrefix;
	
	@Override
	public void destroy() throws Exception {
		for(Cache cache : caches.values()){
			cache.clear();
		}
	}

	@Override
	public <K, V>Cache<K, V> getCache(String name) throws CacheException {
		Cache<K, V> cache = caches.get(name);
		if(cache == null){
			cache = new RedisCache(redisTemplate,keyPrefix);
			caches.put(name, cache);
		}
		return cache;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	
	

}
