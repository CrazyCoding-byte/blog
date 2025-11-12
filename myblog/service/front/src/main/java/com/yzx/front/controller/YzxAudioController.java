package com.yzx.front.controller;


import com.yzx.common_utils.result.R;
import com.yzx.front.entity.YzxAudio;
import com.yzx.front.service.YzxAudioService;
import com.yzx.front.vo.AudioVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
        List<AudioVo> collect = list.stream().map(item -> {
            AudioVo audioVo = new AudioVo();
            BeanUtils.copyProperties(item, audioVo);
            return audioVo;
        }).collect(Collectors.toList());
        return R.ok().setData("item", collect);
    }
}

