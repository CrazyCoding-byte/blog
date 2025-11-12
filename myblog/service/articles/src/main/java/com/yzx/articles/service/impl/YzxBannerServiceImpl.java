package com.yzx.articles.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.common_utils.result.R;
import com.yzx.articles.entity.YzxArticles;
import com.yzx.articles.entity.YzxBanner;
import com.yzx.articles.entity.YzxSubject;
import com.yzx.articles.mapper.YzxBannerMapper;
import com.yzx.articles.service.YzxArticlesService;
import com.yzx.articles.service.YzxBannerService;
import com.yzx.articles.service.YzxSubjectService;
import com.yzx.articles.vo.BannerVo;
import org.springframework.stereotype.Service;

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

    @Override
    public R getBanner() {
        List<YzxBanner> yzxBanners = baseMapper.selectList(null);
        List<BannerVo> collect = yzxBanners.stream().map(item -> {
            YzxArticles yzxArticles = yzxArticlesService.getById(item.getArticleId());
            if (!Objects.isNull(yzxArticles)) {
                BannerVo bannerVo = new BannerVo();
                bannerVo.setId(item.getId());
                bannerVo.setArticleId(yzxArticles.getArticleId());
                bannerVo.setArticleCover(yzxArticles.getArticleCover());
                bannerVo.setArticleTitle(yzxArticles.getArticleTitle());
                bannerVo.setSubjectId(yzxArticles.getSubjectId());
                bannerVo.setCreateTime(item.getCreateTime());
                bannerVo.setUpdateTime(item.getUpdateTime());
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
}
