package com.yzx.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.front.entity.YzxArticles;
import com.yzx.front.entity.YzxInformal;
import com.yzx.front.entity.YzxSubject;
import com.yzx.front.entity.YzxTag;
import com.yzx.front.mapper.YzxArticlesMapper;
import com.yzx.front.service.YzxArticlesService;
import com.yzx.front.service.YzxInformalService;
import com.yzx.front.service.YzxSubjectService;
import com.yzx.front.service.YzxTagService;
import com.yzx.front.vo.*;
import com.yzx.common_utils.result.R;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    YzxSubjectService yzxSubjectService;
    @Resource
    YzxTagService yzxTagService;
    @Resource
    YzxInformalService yzxInformalService;

    @Override
    public R showArticles(Integer page, Integer limit, SearchObjVo searchObjVo) {
        Page<YzxArticles> yzxArticlesPage = new Page<YzxArticles>(page, limit);
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(searchObjVo.getArticleTitle())) {
            yzxArticlesQueryWrapper.eq("article_title", searchObjVo.getArticleTitle());
        }
        if(!StringUtils.isEmpty(searchObjVo.getTagId())){
            yzxArticlesQueryWrapper.eq("tag_id", searchObjVo.getTagId());
        }
        Page<YzxArticles> yzxArticlesPage1 = baseMapper.selectPage(yzxArticlesPage, yzxArticlesQueryWrapper);
        List<YzxArticles> records = yzxArticlesPage1.getRecords();
        List<ArticlesIndexVo> collect = records.stream().map(item -> {
            ArticlesIndexVo articlesIndexVo = new ArticlesIndexVo();
            articlesIndexVo.setArticleId(item.getArticleId());
            articlesIndexVo.setArticelCommentCount(item.getArticleCommentCount());
            articlesIndexVo.setArticleCover(item.getArticleCover());
            articlesIndexVo.setArticleTitle(item.getArticleTitle());
            articlesIndexVo.setArticleViews(item.getArticleViews());
            articlesIndexVo.setCreateTime(item.getCreateTime());
            articlesIndexVo.setSubjectId(item.getSubjectId());
            YzxSubject subject = yzxSubjectService.getById(item.getSubjectId());
            YzxTag tag = yzxTagService.getById(item.getTagId());
            if(!Objects.isNull(subject)){
                articlesIndexVo.setSubjectName(subject.getSortName());
            }
            if(!Objects.isNull(tag)){
                articlesIndexVo.setTagName(tag.getTagName());
            }
            return articlesIndexVo;
        }).collect(Collectors.toList());
        return R.ok().setData("item", collect).setData("page", yzxArticlesPage1);
    }

    @Override
    public R getSource(HttpServletRequest request, Integer page, Integer limit) {
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper = new QueryWrapper<>();
        yzxArticlesQueryWrapper.eq("subject_id", 3);
        Page<YzxArticles> objectPage = new Page<>(page, limit);
        Page<YzxArticles> yzxArticlesPage = baseMapper.selectPage(objectPage, yzxArticlesQueryWrapper);
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper2 = new QueryWrapper<>();
        yzxArticlesQueryWrapper2.eq("subject_id", 3);
        List<YzxArticles> yzxArticles = baseMapper.selectList(yzxArticlesQueryWrapper2);
        SourceCodeVo sourceCodeVo = new SourceCodeVo();
        //查询所有的文章数
        int count = this.count();
        sourceCodeVo.setArticlesCount(count);
        int subjectCount = yzxSubjectService.count();
        sourceCodeVo.setSubjctCount(subjectCount);
        sourceCodeVo.setPageCount(yzxArticles.size());
        //观看人数
        List<YzxArticles> yzxArticles2 = baseMapper.selectList(null);
        Long viewsum = yzxArticles2.stream().map(item -> {
            return item.getArticleViews();
        }).mapToLong(Long::intValue).sum();
        //总评论数
        Long commentsum = yzxArticles2.stream().map(item -> {
            return item.getArticleCommentCount();
        }).mapToLong(Long::intValue).sum();
        //最后创建文章的时间
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper1 = new QueryWrapper<>();
        yzxArticlesQueryWrapper1.orderByDesc("create_time");
        yzxArticlesQueryWrapper1.last("limit 1");
        YzxArticles yzxArticles1 = baseMapper.selectOne(yzxArticlesQueryWrapper1);
        sourceCodeVo.setArticles(yzxArticlesPage.getRecords());
        sourceCodeVo.setArticelsCommentCount(commentsum.intValue());
        sourceCodeVo.setArticlesViewCount(viewsum.intValue());
        sourceCodeVo.setUpdateTime(yzxArticles1.getCreateTime());
        //当前的标签数量
        int tagCount = yzxTagService.count();
        sourceCodeVo.setTagCount(tagCount);
        return R.ok().setData("item", sourceCodeVo);
    }

    @Override
    public R Argorithm(Integer page, Integer limit) {
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper = new QueryWrapper<>();
        yzxArticlesQueryWrapper.eq("subject_id", 4);
        Page<YzxArticles> yzxArticlesPage = baseMapper.selectPage(new Page<YzxArticles>(page, limit), yzxArticlesQueryWrapper);
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper1 = new QueryWrapper<>();
        yzxArticlesQueryWrapper1.eq("subject_id", 4);
        List<YzxArticles> yzxArticles = baseMapper.selectList(yzxArticlesQueryWrapper1);
        List<YzxTag> list = yzxTagService.list();
        ArgorithmVo argorithmVo = new ArgorithmVo();
        argorithmVo.setArticles(yzxArticlesPage.getRecords());
        argorithmVo.setPageCount(yzxArticles.size());
        argorithmVo.setTag(list);
        return R.ok().setData("item",argorithmVo);
    }

    @Override
    public List<PieData> articlesCount() {
       List<PieData> pieData =baseMapper.showArticlesCount();
        return pieData;
    }

    @Override
    public R searchArticlsOrInformal(Integer page, Integer limit, SearchObjVo searchObjVo) {
        //如果当前要查询的informalTitle 不为空那么就查询
        if(!StringUtils.isEmpty(searchObjVo.getInforTitle())){
            QueryWrapper<YzxInformal> yzxInformalQueryWrapper = new QueryWrapper<>();
            yzxInformalQueryWrapper.like("informal_title",searchObjVo.getInforTitle());
            List<YzxInformal> list = yzxInformalService.list(yzxInformalQueryWrapper);
            List<YzxInformal> collect = list.stream().map(item -> {
                if (!StringUtils.isEmpty(item.getSubjectId())) {
                    YzxSubject yzxSubject = yzxSubjectService.getById(item.getSubjectId());
                    item.setSubjectName(yzxSubject.getSortName());
                }
                if (!StringUtils.isEmpty(item.getTagId())) {
                    YzxTag tag = yzxTagService.getById(item.getSubjectId());
                    item.setTagName(tag.getTagName());
                }
                return item;
            }).collect(Collectors.toList());
            return R.ok().setData("informal",collect);
        }
        Page<YzxArticles> yzxArticlesPage = new Page<YzxArticles>(page, limit);
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(searchObjVo.getArticleTitle())) {
            yzxArticlesQueryWrapper.like("article_title", searchObjVo.getArticleTitle());
        }
        if(!StringUtils.isEmpty(searchObjVo.getTagId())){
            yzxArticlesQueryWrapper.eq("tag_id", searchObjVo.getTagId());
        }
        if(!StringUtils.isEmpty(searchObjVo.getAlgorithmTitle())){
            yzxArticlesQueryWrapper.like("article_title", searchObjVo.getAlgorithmTitle());
        }

        if(!StringUtils.isEmpty(searchObjVo.getSourceTitle())){
            yzxArticlesQueryWrapper.like("article_title", searchObjVo.getSourceTitle());
        }
        if(!StringUtils.isEmpty(searchObjVo.getSubjectName())){
            List<YzxSubject> list = yzxSubjectService.list();
            for(YzxSubject i:list){
                if(i.getSortName().equals(searchObjVo.getSubjectName())){
                    yzxArticlesQueryWrapper.like("subject_id", i.getSortId());
                }
            }
        }
        Page<YzxArticles> yzxArticlesPage1 = baseMapper.selectPage(yzxArticlesPage, yzxArticlesQueryWrapper);
        List<YzxArticles> records = yzxArticlesPage1.getRecords();
        List<ArticlesIndexVo> collect = records.stream().map(item -> {
            ArticlesIndexVo articlesIndexVo = new ArticlesIndexVo();
            articlesIndexVo.setArticleId(item.getArticleId());
            articlesIndexVo.setArticelCommentCount(item.getArticleCommentCount());
            articlesIndexVo.setArticleCover(item.getArticleCover());
            articlesIndexVo.setArticleTitle(item.getArticleTitle());
            articlesIndexVo.setArticleViews(item.getArticleViews());
            articlesIndexVo.setCreateTime(item.getCreateTime());
            articlesIndexVo.setSubjectId(item.getSubjectId());
            YzxSubject subject = yzxSubjectService.getById(item.getSubjectId());
            YzxTag tag = yzxTagService.getById(item.getTagId());
            if(!Objects.isNull(subject)){
                articlesIndexVo.setSubjectName(subject.getSortName());
            }
            if(!Objects.isNull(tag)){
                articlesIndexVo.setTagName(tag.getTagName());
            }
            return articlesIndexVo;
        }).collect(Collectors.toList());

        return R.ok().setData("item", collect).setData("page", yzxArticlesPage1);
    }
}

