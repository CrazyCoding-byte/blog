package com.yzx.articles.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.articles.entity.YzxInformal;
import com.yzx.articles.service.YzxInformalService;
import com.yzx.common_utils.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/articles/informal")
@CrossOrigin
public class YzxInformalController {
    @Resource
    YzxInformalService yzxInformalService;

    @PostMapping("/saveInformal")
    public R saveInformal(@RequestBody YzxInformal yzxInformal) {
        boolean save = yzxInformalService.save(yzxInformal);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }

    @PostMapping("/update")
    public R updateInformal(@RequestBody YzxInformal yzxInformal) {
        boolean b = yzxInformalService.updateById(yzxInformal);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/list/{page}/{limit}")
    public R getInformal(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<YzxInformal> page1 = yzxInformalService.page(new Page<>(page, limit));
        return R.ok().setData("item", page1);
    }

    @DeleteMapping("/deleteInformal/{id}")
    public R deleteInformal(@PathVariable String id) {
        boolean b = yzxInformalService.removeById(id);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/getinformal/{id}")
    public R getInforByid(@PathVariable String id) {
        YzxInformal byId = yzxInformalService.getById(id);
        if (!Objects.isNull(byId)) {
            return R.ok().setData("item",byId);
        }
        return R.fail();
    }
}

