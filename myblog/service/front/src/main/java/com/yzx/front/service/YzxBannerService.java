package com.yzx.front.service;

import com.yzx.common_utils.result.R;
import com.yzx.front.entity.YzxBanner;
import com.baomidou.mybatisplus.extension.service.IService;

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

    R selectBanner(Integer id);
}
