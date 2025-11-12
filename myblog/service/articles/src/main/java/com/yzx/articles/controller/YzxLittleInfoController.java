package com.yzx.articles.controller;


import com.yzx.articles.entity.YzxLittleInfo;
import com.yzx.articles.service.YzxLittleInfoService;
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
@RequestMapping("/articles/littleinfo")
@CrossOrigin
public class YzxLittleInfoController {

    @Resource
    YzxLittleInfoService yzxLittleInfoService;
    @GetMapping("/list")
    public R getLittleInfo(){
        List<YzxLittleInfo> list = yzxLittleInfoService.list();
        return R.ok().setData("item", list);
    }

    @PostMapping("/save")
    public void saveLittleInfo(@RequestBody YzxLittleInfo yzxLittleInfo){
        boolean save = yzxLittleInfoService.save(yzxLittleInfo);
    }

    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable String id){
        boolean b = yzxLittleInfoService.removeById(id);
    }
}

