package com.yzx.articles.controller;


import com.yzx.articles.entity.YzxArticles;
import com.yzx.articles.service.YzxArticlesService;
import com.yzx.articles.vo.SearchObjVo;
import com.yzx.common_utils.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/articles/service")
@CrossOrigin
public class YzxArticlesController {

    @Resource
    YzxArticlesService yzxArticlesService;

    //显示所有的博文
    @PostMapping("/show/list/{page}/{limit}")
    public R showArticles(@PathVariable Integer page, @PathVariable Integer limit, @RequestBody SearchObjVo searchObjVo) {
        R result = yzxArticlesService.showArticles(page, limit, searchObjVo);
        return result;
    }

    @GetMapping("/getArticleById/{ArticleId}")
    public R getArticleById(@PathVariable String ArticleId) {
        YzxArticles articles = yzxArticlesService.getById(ArticleId);
        return R.ok().setData("item", articles);
    }

    @PostMapping("/saveArticle")
    public R saveArticles(@RequestBody YzxArticles yzxArticles) {
        boolean save = yzxArticlesService.save(yzxArticles);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }

    //修改博文
    @PostMapping("/updateArticleById")
    public R updateArticleById(@RequestBody YzxArticles yzxArticles) {
        boolean b = yzxArticlesService.updateById(yzxArticles);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }

    @DeleteMapping("/deleteArticleById/{articleId}")
    public R deleteArticleById(@PathVariable String articleId) {
        boolean b = yzxArticlesService.removeArticleById(articleId);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }
}

