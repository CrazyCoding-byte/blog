package com.yzx.front.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.front.annotation.View;
import com.yzx.front.entity.YzxInformal;
import com.yzx.front.service.YzxInformalService;
import com.yzx.common_utils.result.R;
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
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/front/informal")
@CrossOrigin
public class YzxInformalController {
    @Resource
    YzxInformalService yzxInformalService;


    @GetMapping("/index/{page}/{limit}")
    public R getInformal(@PathVariable Integer page, @PathVariable Integer limit) {
       R result=yzxInformalService.getIndexInformal(page,limit);
        return result;
    }

    @GetMapping("/list/{page}/{limit}")
    public R getInformals(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<YzxInformal> page1 = yzxInformalService.page(new Page<YzxInformal>(page, limit));
        return R.ok().setData("item", page1);
    }
    @GetMapping("/getinformal/{id}")
    public R getInforByid(@PathVariable String id) {
        YzxInformal byId = yzxInformalService.getById(id);
        if (!Objects.isNull(byId)) {
            return R.ok().setData("item",byId);
        }
        return R.fail();
    }

    @GetMapping("/getWeatherByip")
    public R getWeatherByip(HttpServletRequest request){
     R result=yzxInformalService.getWeatherByip(request);
     return result;
    }
}

