package com.yzx.articles.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.common_utils.result.R;
import com.yzx.articles.entity.YzxAudio;
import com.yzx.articles.service.YzxAudioService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzx
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/front/audio")
@CrossOrigin
public class YzxAudioController {

    @Resource
    YzxAudioService yzxAudioService;

    @GetMapping("/list")
    public R getAudioList(){
        List<YzxAudio> list = yzxAudioService.list();
        return R.ok().setData("item", list);
    }

    @PostMapping("/save")
    public R saveAudio(@RequestBody YzxAudio yzxAudio){
        boolean save = yzxAudioService.save(yzxAudio);
        if(save){
            return R.ok();
        }
        return R.fail();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id){
        boolean b = yzxAudioService.removeById(id);
        if(b){
            return R.ok();
        }
        return R.fail();
    }
}

