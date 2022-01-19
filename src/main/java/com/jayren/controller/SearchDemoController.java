package com.jayren.controller;

import com.jayren.service.RedisService;
import com.jayren.utils.GetPinyin;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  模糊搜索索引
 * @Author Jay
 * @Date 2022/1/18 15:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/demo")
public class SearchDemoController {

    private final String KEY = "auto_complete::";

    private final  int RANKING = 10;

    @Resource
    private RedisService redisService;

    @GetMapping("/feed/{name}")
    public String feed(@PathVariable String name){
        long l = System.currentTimeMillis();

        String pingYin = GetPinyin.getPingYin(name);
        for (int i = 1; i < pingYin.length()+1; i++) {
            redisService.zAdd(KEY + pingYin.substring(0,i),name,l);
        }
        return "ok";
    }

    @GetMapping("/hint/{keyword}")
    public Object hint(@PathVariable String keyword){
        return redisService.reverseRange(KEY+GetPinyin.getPingYin(keyword), 0, RANKING);
    }
}
