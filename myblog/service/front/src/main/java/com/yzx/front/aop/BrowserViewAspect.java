package com.yzx.front.aop;


import com.yzx.front.entity.YzxArticles;
import com.yzx.front.service.YzxArticlesService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author yzx
 * @create 2023-01-13 1:05
 */
@Aspect
@Component
public class BrowserViewAspect {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    YzxArticlesService yzxArticlesService;
    @Pointcut(value = "@annotation(com.yzx.front.annotation.View)")
    public void viewPointcut() {
    }


    //在方法調用之後
    @After("viewPointcut()")
    public void viewAfterMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //拿到当前的参数
        String id = (String) args[0];
        Integer o = (Integer) redisTemplate.opsForValue().get("article" + id);
        if(Objects.isNull(o)||o<=0){
            YzxArticles yzxArticles = yzxArticlesService.getById(id);
            //设置观看的数量
            redisTemplate.opsForValue().set("article"+id, yzxArticles.getArticleViews());
        }else{
            Integer num = (Integer) redisTemplate.opsForValue().get("article" + id);
            redisTemplate.opsForValue().set("article"+id, num+1);
        }
    }
}
