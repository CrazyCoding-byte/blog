package com.yzx.spring_security.config;

import com.yzx.spring_security.filter.JwtAuthTokenFilter;
import com.yzx.spring_security.filter.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author yzx
 * @create 2022-12-22 3:55
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    //设置当前的加密方式
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint;
    @Autowired
    private JwtAuthTokenFilter jwtAuthTokenFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .authorizeRequests()
                //允许当前下面路径匿名访问 (已登录的状态是访问不了的) //也就是说 登录接口肯定是匿名访问的 因为 已经登录的状态已经不需要去登录了
                .antMatchers("/acl/user/login").anonymous()
                .antMatchers("/articles/**").hasAuthority("zzzz")
                //除了上面外的都需要验证 认证的用户都可以访问(也就是说下面的意思就是 任意请求都需要验证 并且顺序不能在上面之前要不然没有权限!)
                .anyRequest().authenticated();
        //添加我们需要的过滤器jwtAuthTokenFilter这个过滤器 在UsernamePasswordAuthenticationFilter 他之前
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
