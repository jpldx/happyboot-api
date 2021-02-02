package org.happykit.happyboot.config;//package com.litong.boot.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.util.Arrays;
//
///**
// * 缓存配置-使用Lettuce客户端，自动注入配置的方式
// *
// * @author shaoqiang
// * @version 1.0 2020/3/23
// */
//@Configuration
//@EnableCaching
//public class CacheConfig extends CachingConfigurerSupport {
//
//	/**
//	 * @return 自定义策略生成的key
//	 * @description 自定义的缓存key的生成策略 若想使用这个key
//	 * 只需要讲注解上keyGenerator的值设置为keyGenerator即可</br>
//	 */
//	@Override
//	@Bean
//	public KeyGenerator keyGenerator() {
//		return (target, method, params) -> {
//			StringBuilder sb = new StringBuilder();
//			sb.append(target.getClass().getName());
//			sb.append(method.getDeclaringClass().getName());
//			Arrays.stream(params).map(Object::toString).forEach(sb::append);
//			return sb.toString();
//		};
//	}
//
//	/**
//	 * 缓存配置管理器
//	 */
//	@Bean
//	public CacheManager cacheManager(LettuceConnectionFactory factory) {
//		// 以锁写入的方式创建RedisCacheWriter对象
//		RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
//		// 创建默认缓存配置对象
//		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//		RedisCacheManager cacheManager = new RedisCacheManager(writer, config);
//		return cacheManager;
//	}
//
//	/**
//	 * RedisTemplate配置
//	 *
//	 * @param connectionFactory
//	 * @return
//	 */
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
//		//创建Redis缓存操作助手RedisTemplate对象
//		RedisTemplate<String, Object> template = new RedisTemplate();
//		template.setConnectionFactory(connectionFactory);
//
//		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//		ObjectMapper om = new ObjectMapper();
//		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		// 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//
//
//		// 值采用json序列化
//		template.setValueSerializer(jackson2JsonRedisSerializer);
//		//使用StringRedisSerializer来序列化和反序列化redis的key值
//		template.setKeySerializer(new StringRedisSerializer());
//
//		// 设置hash key 和value序列化模式
//		template.setHashKeySerializer(new StringRedisSerializer());
//		template.setHashValueSerializer(jackson2JsonRedisSerializer);
//		template.afterPropertiesSet();
//
//		return template;
//	}
//}
