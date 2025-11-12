package com.yzx.articles.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.articles.entity.YzxArticles;
import com.yzx.articles.entity.YzxComments;
import com.yzx.articles.service.YzxArticlesService;
import com.yzx.articles.service.YzxCommentsService;
import com.yzx.articles.vo.SearchObjVo;
import com.yzx.common_utils.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/articles/comments")
@CrossOrigin
public class YzxCommentsController {
    @Resource
    YzxCommentsService yzxCommentsService;

    @GetMapping("/show/list/{page}/{limit}")
    //显示所有的评论信息
    public R showArticles(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<YzxComments> yzxCommentsPage = new Page<>(page, limit);
        Page<YzxComments> page1 = yzxCommentsService.page(yzxCommentsPage);
        return R.ok().setData("item", page1);
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

    @DeleteMapping("/deleteCommentById/{commentId}")
    //删除当前的评论
    public R deleteArticleById(@PathVariable String commentId) {

        boolean b = yzxCommentsService.removeById(commentId);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }
}

