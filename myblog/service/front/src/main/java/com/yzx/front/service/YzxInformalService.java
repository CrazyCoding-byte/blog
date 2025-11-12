package com.yzx.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzx.common_utils.result.R;
import com.yzx.front.entity.YzxInformal;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzx
 * @since 2022-12-28
 */
public interface YzxInformalService extends IService<YzxInformal> {

    R getIndexInformal(Integer page, Integer limit);

    R getWeatherByip(HttpServletRequest request) ;
}
