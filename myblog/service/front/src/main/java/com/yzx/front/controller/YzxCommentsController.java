package com.yzx.front.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.front.entity.YzxComments;
import com.yzx.front.service.YzxCommentsService;
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
@RequestMapping("/front/comments")
@CrossOrigin
public class YzxCommentsController {
    @Resource
    YzxCommentsService yzxCommentsService;

    @GetMapping("/show/list/{page}/{limit}/{articlesId}")
    //显示所有的评论信息
    public R showArticles(@PathVariable Integer page, @PathVariable Integer limit,@PathVariable String articlesId) {
        Page<YzxComments> yzxCommentsPage = new Page<>(page, limit);
        int count = yzxCommentsService.count(new QueryWrapper<YzxComments>().eq("article_id", articlesId));
        QueryWrapper<YzxComments> yzxCommentsQueryWrapper = new QueryWrapper<>();
        yzxCommentsQueryWrapper.eq("article_id", articlesId);
        Page<YzxComments> page1 = yzxCommentsService.page(yzxCommentsPage,yzxCommentsQueryWrapper);
        return R.ok().setData("item", page1).setData("pageCount",count);
    }

    @PostMapping("/saveComment")
    //添加一条评论
    public R saveComment(@RequestBody YzxComments yzxComments) {
        boolean save = yzxCommentsService.save(yzxComments);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }


}

