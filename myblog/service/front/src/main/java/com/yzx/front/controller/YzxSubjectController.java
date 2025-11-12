package com.yzx.front.controller;


import com.yzx.front.entity.YzxSubject;
import com.yzx.front.service.YzxSubjectService;
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
@RequestMapping("/front/subject")
@CrossOrigin
public class YzxSubjectController {
    @Resource
    YzxSubjectService yzxSubjectService;

    //显示所有的分类
    @GetMapping("/show/list")
    public R showArticles() {
        List<YzxSubject> list = yzxSubjectService.list();

        return R.ok().setData("item", list);
    }

    @PostMapping("/saveSubject")
    public R saveArticles(@RequestBody YzxSubject yzxSubject) {
        boolean save = yzxSubjectService.save(yzxSubject);
        if (save) {
            return R.ok();
        }
        return R.fail();
    }

    @DeleteMapping("/deleteSubjectById/{subjectId}")
    public R deleteArticleById(@PathVariable String subjectId) {
        boolean b = yzxSubjectService.removeById(subjectId);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }
}

