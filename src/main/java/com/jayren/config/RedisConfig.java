package com.jayren.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


/**
 * @author jayren
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
