package com.yzx.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.common_utils.result.R;
import com.yzx.front.entity.YzxArticles;
import com.yzx.front.entity.YzxInformal;
import com.yzx.front.mapper.YzxInformalMapper;
import com.yzx.front.service.YzxArticlesService;
import com.yzx.front.service.YzxInformalService;
import com.yzx.front.utils.getIp;
import com.yzx.front.utils.getIpAddress;
import com.yzx.front.vo.InformalVo;
import com.yzx.front.vo.PieData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yzx
 * @since 2022-12-28
 */
@Service
public class YzxInformalServiceImpl extends ServiceImpl<YzxInformalMapper, YzxInformal> implements YzxInformalService {
    @Resource
    private YzxArticlesService yzxArticlesService;
    @Resource
    RestTemplate restTemplate;

    @Override
    public R getIndexInformal(Integer page, Integer limit) {
        InformalVo informalVo = new InformalVo();
        Page<YzxInformal> yzxInformalPage = baseMapper.selectPage(new Page<YzxInformal>(page, limit), null);
        informalVo.setInformals(yzxInformalPage.getRecords());
        List<PieData> pieData = yzxArticlesService.articlesCount();
        informalVo.setPieData(pieData);
        int count = this.count();
        informalVo.setPageCount(count);
        return R.ok().setData("item", informalVo);
    }

    @Override
    public R getWeatherByip(HttpServletRequest request) {
        String ipAddress = getIpAddress.getIpAddress(request);
        String ipAddr = getIp.getIpAddr(request);
        HashMap forObject = restTemplate.getForObject("https://restapi.amap.com/v3/ip?ip="+ipAddress+"&output=json&key=cac3397470f714b56745ccb70538d311", HashMap.class);
        String adcode = (String) forObject.get("adcode");
        HashMap forObject1 = restTemplate.getForObject("https://restapi.amap.com/v3/weather/weatherInfo?city=" + adcode + "&key=cac3397470f714b56745ccb70538d311", HashMap.class);
        return R.ok().setData("item",forObject1.get("lives"));
    }
}
