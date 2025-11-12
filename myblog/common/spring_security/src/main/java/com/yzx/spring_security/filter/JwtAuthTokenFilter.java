package com.yzx.spring_security.filter;

import com.yzx.common_utils.exceptionhandle.MyException;
import com.yzx.spring_security.entity.LoginUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author yzx
 * @create 2022-12-22 3:57
 */
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        System.out.println("当前的token");
        //如果当前的用户没有token就放行(说明是登录)
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //如果当前用户有token 就查出当前用户的信息
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get("login:" + token + "20010103001x");
        System.out.println(loginUser);
        if (Objects.isNull(loginUser)) {
            throw new MyException(103, "当前的用户不存在");
        }
        System.out.println(loginUser.getAuthorities());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
