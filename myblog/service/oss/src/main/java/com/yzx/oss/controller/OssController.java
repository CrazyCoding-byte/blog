package com.yzx.oss.controller;

import com.yzx.common_utils.result.R;
import com.yzx.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yzx
 * @create 2022-11-22 23:43
 */
@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OssController {
    @Autowired
    OssService ossService;

    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        String upload = ossService.upload(file);

        return R.ok().setData("url", upload);
    }
}
