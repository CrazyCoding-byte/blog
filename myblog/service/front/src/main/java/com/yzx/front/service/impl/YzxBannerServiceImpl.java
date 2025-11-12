package com.yzx.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yzx.common_utils.result.R;
import com.yzx.front.entity.YzxArticles;
import com.yzx.front.entity.YzxBanner;
import com.yzx.front.entity.YzxInformal;
import com.yzx.front.entity.YzxSubject;
import com.yzx.front.mapper.YzxBannerMapper;
import com.yzx.front.service.YzxArticlesService;
import com.yzx.front.service.YzxBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.front.service.YzxInformalService;
import com.yzx.front.service.YzxSubjectService;
import com.yzx.front.vo.BannerVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yzx
 * @since 2022-12-27
 */
@Service
public class YzxBannerServiceImpl extends ServiceImpl<YzxBannerMapper, YzxBanner> implements YzxBannerService {
    @Resource
    YzxArticlesService yzxArticlesService;
    @Resource
    YzxSubjectService yzxSubjectService;
    @Resource
    YzxInformalService yzxInformalService;

    @Override
    public R getBanner() {
        QueryWrapper<YzxBanner> yzxBannerQueryWrapper = new QueryWrapper<>();
        yzxBannerQueryWrapper.last("ORDER BY create_time desc limit 4");
        List<YzxBanner> yzxBanners = baseMapper.selectList(yzxBannerQueryWrapper);
        List<BannerVo> collect = yzxBanners.stream().map(item -> {
            YzxArticles yzxArticles = yzxArticlesService.getById(item.getArticleId());
            if (!Objects.isNull(yzxArticles)) {
                BannerVo bannerVo = new BannerVo();
                bannerVo.setId(yzxArticles.getArticleId());
                bannerVo.setArticleCover(yzxArticles.getArticleCover());
                bannerVo.setArticleTitle(yzxArticles.getArticleTitle());
                bannerVo.setSubjectId(yzxArticles.getSubjectId());
                YzxSubject subject = yzxSubjectService.getById(yzxArticles.getSubjectId());
                if (!Objects.isNull(subject)) {
                    bannerVo.setSubjectName(subject.getSortName());
                }
                return bannerVo;
            }
            return null;
        }).collect(Collectors.toList());
        return R.ok().setData("item", collect);
    }

    @Override
    public R selectBanner(Integer id) {
        QueryWrapper<YzxArticles> yzxArticlesQueryWrapper = new QueryWrapper<>();
        yzxArticlesQueryWrapper.last("ORDER BY create_time desc  limit 4 ");
        if (!StringUtils.isEmpty(id)) {
            //如果当前是首页或者是文章就查询所有articles前4个
            if (id <= 2) {
                List<YzxArticles> list = yzxArticlesService.list(yzxArticlesQueryWrapper);
                List<BannerVo> collect = list.stream().map(item -> {
                    BannerVo bannerVo = new BannerVo();
                    bannerVo.setId(item.getArticleId());
                    bannerVo.setArticleCover(item.getArticleCover());
                    bannerVo.setArticleTitle(item.getArticleTitle());
                    bannerVo.setSubjectId(item.getSubjectId());
                    YzxSubject subject = yzxSubjectService.getById(item.getSubjectId());
                    if (!Objects.isNull(subject)) {
                        bannerVo.setSubjectName(subject.getSortName());
                    }
                    return bannerVo;
                }).collect(Collectors.toList());
                return R.ok().setData("item", collect);
            } else if (id == 3) {
                //查询源码的banner
                yzxArticlesQueryWrapper.eq("subject_id", 3);
                List<YzxArticles> list = yzxArticlesService.list(yzxArticlesQueryWrapper);
                List<BannerVo> collect = list.stream().map(item -> {
                    BannerVo bannerVo = new BannerVo();
                    bannerVo.setId(item.getArticleId());
                    bannerVo.setArticleCover(item.getArticleCover());
                    bannerVo.setArticleTitle(item.getArticleTitle());
                    bannerVo.setSubjectId(item.getSubjectId());
                    YzxSubject subject = yzxSubjectService.getById(item.getSubjectId());
                    if (!Objects.isNull(subject)) {
                        bannerVo.setSubjectName(subject.getSortName());
                    }
                    return bannerVo;
                }).collect(Collectors.toList());
                return R.ok().setData("item", collect);
            } else if (id == 4) {
                //查询算法的banner
                yzxArticlesQueryWrapper.eq("subject_id", 4);
                List<YzxArticles> list = yzxArticlesService.list(yzxArticlesQueryWrapper);
                List<BannerVo> collect = list.stream().map(item -> {
                    BannerVo bannerVo = new BannerVo();
                    bannerVo.setId(item.getArticleId());
                    bannerVo.setArticleCover(item.getArticleCover());
                    bannerVo.setArticleTitle(item.getArticleTitle());
                    bannerVo.setSubjectId(item.getSubjectId());
                    YzxSubject subject = yzxSubjectService.getById(item.getSubjectId());
                    if (!Objects.isNull(subject)) {
                        bannerVo.setSubjectName(subject.getSortName());
                    }
                    return bannerVo;
                }).collect(Collectors.toList());
                return R.ok().setData("item", collect);
            } else if (id == 5) {
                //查询当前的随感信息
                QueryWrapper<YzxInformal> yzxInformalQueryWrapper = new QueryWrapper<>();
                yzxInformalQueryWrapper.last("order by create_time desc limit 4 ");
                List<YzxInformal> list = yzxInformalService.list(yzxInformalQueryWrapper);
                List<BannerVo> collect = list.stream().map(item -> {
                    BannerVo bannerVo = new BannerVo();
                    bannerVo.setId(item.getId());
                    bannerVo.setArticleCover(item.getCover());
                    bannerVo.setArticleTitle(item.getInformalTitle());
                    bannerVo.setSubjectId(item.getSubjectId());
                    YzxSubject subject = yzxSubjectService.getById(item.getSubjectId());
                    if (!Objects.isNull(subject)) {
                        bannerVo.setSubjectName(subject.getSortName());
                    }
                    return bannerVo;
                }).collect(Collectors.toList());
                return R.ok().setData("item", collect);
            }
        }
        return null;
    }
}
