package com.yzx.front.controller;


import com.yzx.front.annotation.View;
import com.yzx.front.entity.YzxArticles;
import com.yzx.front.entity.YzxSubject;
import com.yzx.front.service.YzxArticlesService;
import com.yzx.front.service.YzxSubjectService;
import com.yzx.front.vo.ArticlesDetails;
import com.yzx.front.vo.SearchObjVo;
import com.yzx.common_utils.result.R;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/front/service")
@CrossOrigin
public class YzxArticlesController {

    @Resource
    YzxArticlesService yzxArticlesService;
    @Resource
    YzxSubjectService yzxSubjectService;
    @Resource
    RedisTemplate redisTemplate;
    //显示所有的博文
    @PostMapping("/index/{page}/{limit}")
    public R showArticles(@PathVariable Integer page, @PathVariable Integer limit, @RequestBody SearchObjVo searchObjVo) {
        R result = yzxArticlesService.showArticles(page, limit, searchObjVo);
        return result;
    }

    @GetMapping("/indexDateils/{ArticleId}")
    //显示详情的接口
    @View(defaultValue = "articles详情方法")
    public R getArticleById(@PathVariable String ArticleId) {
        YzxArticles articles = yzxArticlesService.getById(ArticleId);
        if (!Objects.isNull(articles)) {
            ArticlesDetails articlesIndexVo = new ArticlesDetails();
            BeanUtils.copyProperties(articles, articlesIndexVo);
            YzxSubject subject = yzxSubjectService.getById(articles.getSubjectId());
            Integer o = (Integer) redisTemplate.opsForValue().get("article" + ArticleId);
            //表设计的有问题 如果当前是小于0的那么就说明也不做 因为上面可以直接查询数据库
            if(Objects.isNull(o)||o<=0){

            }else{
             //因为做了一个切面 第二次查询这个的时候就有了
                articlesIndexVo.setArticleViews(o.longValue());
            }
            if (!Objects.isNull(subject)) {
                articlesIndexVo.setSubjectName(subject.getSortName());
            }
            return R.ok().setData("item", articlesIndexVo);
        }
        return null;
    }

    @GetMapping("/indexSource/{page}/{limit}")
    public R getIndexSource(HttpServletRequest servletRequest, @PathVariable Integer page, @PathVariable Integer limit) {
        R result = yzxArticlesService.getSource(servletRequest, page, limit);
        return result;
    }


    @GetMapping("/indexArgorithm/{page}/{limit}")
    public R getIndexArgorithm(@PathVariable Integer page, @PathVariable Integer limit) {
        R result = yzxArticlesService.Argorithm(page, limit);
        return result;
    }

    @PostMapping("/searchArticlsOrInformal/{page}/{limit}")
    public R searchArticlsOrInformal(@PathVariable Integer page, @PathVariable Integer limit, @RequestBody SearchObjVo searchObjVo) {
        R result = yzxArticlesService.searchArticlsOrInformal(page, limit, searchObjVo);
        return result;
    }
}

