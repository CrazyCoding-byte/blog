package com.yzx.front.controller;


import com.yzx.common_utils.result.R;
import com.yzx.front.entity.YzxBanner;
import com.yzx.front.service.YzxBannerService;
import com.yzx.front.vo.BannerVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzx
 * @since 2022-12-27
 */
@RestController
@RequestMapping("/front/banner")
@CrossOrigin
public class YzxBannerController {

    @Resource
    YzxBannerService yzxBannerService;

    @GetMapping("/getBanner")
    //首页banner
    public R getBanner() {
        R result = yzxBannerService.getBanner();
        return result;
    }

    @GetMapping("/selectBanner/{id}")
    public R selectBanner(@PathVariable Integer id) {
        R result= yzxBannerService.selectBanner(id);
        return result;
    }
}

