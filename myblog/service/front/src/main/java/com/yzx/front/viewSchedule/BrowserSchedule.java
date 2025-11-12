package com.yzx.front.viewSchedule;

import com.yzx.front.entity.YzxArticles;
import com.yzx.front.service.YzxArticlesService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author yzx
 * @create 2023-01-13 1:47
 */
@Component
public class BrowserSchedule {
    @Resource
    YzxArticlesService yzxArticlesService;
    @Resource
    RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 00 * * ?")
    public void viewSchedule() {
        Set<String> article = redisTemplate.keys("*");
        List<String> vieKey=new ArrayList<>();
        for (String key : article) {
            if (key.startsWith("article")) {
                Integer key1 = (Integer) redisTemplate.opsForValue().get(key);
                vieKey.add(key);
                String str = key.replace("article","");
                YzxArticles byId = yzxArticlesService.getById(str);
                byId.setArticleViews(key1.longValue());
                yzxArticlesService.updateById(byId);
            }
        }
        //删除掉当前的key
        if(!CollectionUtils.isEmpty(vieKey)){
            redisTemplate.delete(vieKey);
        }
    }
}
