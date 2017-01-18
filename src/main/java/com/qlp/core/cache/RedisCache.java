package com.qlp.core.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import com.qlp.core.exception.MyException;
import com.qlp.core.util.CollectionUtil;
import com.qlp.core.util.SerializeUtil;

@SuppressWarnings("unchecked")
public class RedisCache<K, V> implements Cache<K, V>{
	
	private RedisTemplate<String, Object> redisTemplate;
	private String keyPrefix;
	private static final long DEFAULT_TIMEOUT = 0L; 
	

	public RedisCache(RedisTemplate<String, Object> redisTemplate, String keyPrefix) {
		this.redisTemplate = redisTemplate;
		this.keyPrefix = keyPrefix;
	}

	
	@Override
	public V get(K key) throws CacheException {
		try {
			return (V) redisTemplate.boundValueOps(covertKey(key)).get();
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		try {
			redisTemplate.restore(covertKey(key),SerializeUtil.serialize(value),DEFAULT_TIMEOUT,TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new MyException(e);
		}
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		V v = get(key);
		try {
			redisTemplate.delete(covertKey(key));
		} catch (Exception e) {
			throw new MyException(e);
		}
		return v;
	}

	@Override
	public void clear() throws CacheException {
		try {
			Set<String> keys = redisTemplate.keys(this.keyPrefix + "*");
			redisTemplate.delete(keys);
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public int size() {
		Set<String> keys = redisTemplate.keys(this.keyPrefix + "*");
		return keys.size();
	}

	@Override
	public Set<K> keys() {
		try {
			Set<String> keys = redisTemplate.keys(this.keyPrefix + "*");
            if (CollectionUtil.isBlank(keys)) {
            	return Collections.emptySet();
            }
            
        	Set<K> newKeys = new HashSet<K>();
        	for(String key:keys){
        		newKeys.add((K)key);
        	}
        	return Collections.unmodifiableSet(newKeys);
        } catch (Exception e) {
            throw new MyException(e);
        }
	}

	@Override
	public Collection<V> values() {
		Set<String> keys = redisTemplate.keys(this.keyPrefix + "*");
		if(CollectionUtil.isBlank(keys)){
			return Collections.emptyList();
		}
		
		List<V> values = new ArrayList<V>(keys.size());
		for(String key : keys){
			values.add((V)redisTemplate.boundValueOps(key).get());
		}
		return Collections.unmodifiableList(values);
	}
	
	private String covertKey(K key) {
		if(key instanceof String){
			String preKey = this.keyPrefix + key;
    		return preKey;
    	}
		return null;
	}

}
