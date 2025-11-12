package com.yzx.front.controller;


import com.yzx.front.entity.YzxLittleInfo;
import com.yzx.front.service.YzxLittleInfoService;
import com.yzx.common_utils.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzx
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/front/littleinfo")
@CrossOrigin
public class YzxLittleInfoController {

    @Resource
    YzxLittleInfoService yzxLittleInfoService;
    @GetMapping("/list")
    public R getLittleInfo(){
        List<YzxLittleInfo> list = yzxLittleInfoService.list();
        return R.ok().setData("item", list);
    }

}

