package com.yzx.articles.controller;


import com.yzx.articles.entity.YzxTag;
import com.yzx.articles.service.YzxTagService;
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
 * @since 2023-01-02
 */
@RestController
@RequestMapping("/articles/tag")
@CrossOrigin
public class YzxTagController {
    @Resource
    YzxTagService tagService;

    @GetMapping("/list")
    public R list() {
        List<YzxTag> list = tagService.list();
        return R.ok().setData("item", list);
    }

    @PostMapping("/saveTag")
    public R saveTag(@RequestBody YzxTag tag) {
        boolean save = tagService.save(tag);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }

    @DeleteMapping("/deleteTag/{id}")
    public R deleteTag(@PathVariable String id) {
        boolean b = tagService.removeById(id);
        if(b){
            return R.ok();
        }
        return R.fail();
    }
}

