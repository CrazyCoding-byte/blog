package com.yzx.articles.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.articles.entity.YzxArticles;
import com.yzx.articles.entity.YzxComments;
import com.yzx.articles.mapper.YzxArticlesMapper;
import com.yzx.articles.service.YzxArticlesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.articles.service.YzxCommentsService;
import com.yzx.articles.vo.SearchObjVo;
import com.yzx.common_utils.result.R;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yzx
 * @since 2022-12-20
 */
@Service
public class YzxArticlesServiceImpl extends ServiceImpl<YzxArticlesMapper, YzxArticles> implements YzxArticlesService {
    @Resource
    YzxCommentsService yzxCommentsService;

    @Override
    public R showArticles(Integer page, Integer limit, SearchObjVo searchObjVo) {
        Page<YzxArticles> yzxArticlesPage = new Page<YzxArticles>(page, limit);
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(searchObjVo.getArticleTitle())) {
            yzxArticlesQueryWrapper.like("article_title", searchObjVo.getArticleTitle());
        }
        if (!StringUtils.isEmpty(searchObjVo.getCreateTime())) {
            yzxArticlesQueryWrapper.ge("create_time", searchObjVo.getCreateTime());
        }
        if (!StringUtils.isEmpty(searchObjVo.getUpdateTime())) {
            yzxArticlesQueryWrapper.le("update_Time", searchObjVo.getUpdateTime());
        }
        Page<YzxArticles> yzxArticlesPage1 = baseMapper.selectPage(yzxArticlesPage, yzxArticlesQueryWrapper);
        return R.ok().setData("item", yzxArticlesPage1);
    }

    @Override
    public boolean removeArticleById(String articleId) {
        baseMapper.deleteById(articleId);
        //根据当前的博文id删除当前的评论
        QueryWrapper<YzxComments> yzxCommentsQueryWrapper = new QueryWrapper<>();
        yzxCommentsQueryWrapper.eq("article_id", articleId);
        boolean remove = yzxCommentsService.remove(yzxCommentsQueryWrapper);
        return true;
    }
}
