package com.yzx.articles.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzx.common_utils.result.R;
import com.yzx.articles.entity.YzxBanner;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzx
 * @since 2022-12-27
 */
public interface YzxBannerService extends IService<YzxBanner> {

    R getBanner();
}
