/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lyl.base.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.cache.ehcache.EhCacheCacheManager;


/**
 * Cache工具类
 */
public class CacheUtils {
	
	private static EhCacheCacheManager ehcacheManager = ((EhCacheCacheManager)SpringContextHolder.getBean("ehCacheManager"));

	private static final String SYS_CACHE = "sysCache";

	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	public static Object get(String cacheName, String key) {
		Element  element = getCache(cacheName).get(key);
		return element==null?null:element.getValue();
	}

	public static void put(String cacheName, String key, Object value) {
		Element element=new Element(key, value);
		getCache(cacheName).put(element);
	}

	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	private static Cache getCache(String cacheName){
		Cache cache = getCacheManager().getCache(cacheName);
		if (cache == null){
			getCacheManager().addCache(cacheName);
			cache = getCacheManager().getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return ehcacheManager.getCacheManager();
	}
	
}
