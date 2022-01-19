package com.jayren.controller;

import com.jayren.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author Jay
 * @Date 2022/1/18 15:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    private final String KEY = "auto_complete::";

    @Resource
    private RedisService redisService;

    @GetMapping("/feed/{name}")
    public String feed(@PathVariable String name){
        long l = System.currentTimeMillis();
        for (int i = 1; i < name.length()+1; i++) {
            redisService.zAdd(KEY + name.substring(0,i),name,l);
        }
        return "ok";
    }

    @GetMapping("/hint/{keyword}")
    public Object hint(@PathVariable String keyword){
        return redisService.reverseRange(KEY+keyword, 0, 10);
    }
}
