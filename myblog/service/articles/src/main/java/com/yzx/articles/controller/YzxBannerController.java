package com.yzx.articles.controller;


import com.yzx.articles.entity.YzxArticles;
import com.yzx.articles.service.YzxArticlesService;
import com.yzx.common_utils.result.R;
import com.yzx.articles.entity.YzxBanner;
import com.yzx.articles.service.YzxBannerService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-27
 */
@RestController
@RequestMapping("/articles/banner")
@CrossOrigin
public class YzxBannerController {

    @Resource
    YzxBannerService yzxBannerService;
    @Resource
    YzxArticlesService articlesService;
    @GetMapping("/getBanner")
    public R getBanner() {
        R result = yzxBannerService.getBanner();
        return result;
    }

    @PostMapping("/save/banner")
    public R saveBanner(@RequestBody YzxBanner yzxBanner) {
        boolean save = yzxBannerService.save(yzxBanner);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }

    @DeleteMapping("/delete/banner/{bId}")
    public R deletedBanner(@PathVariable String bId) {
        boolean b = yzxBannerService.removeById(bId);
        if(b){
            return R.ok();
        }
        return R.fail();
    }
    @GetMapping("/listarticles")
    public R getListarticles(){
        List<YzxArticles> list = articlesService.list();
        if(!CollectionUtils.isEmpty(list)){
            return R.ok().setData("item",list);
        }
        return R.fail();
    }
}

