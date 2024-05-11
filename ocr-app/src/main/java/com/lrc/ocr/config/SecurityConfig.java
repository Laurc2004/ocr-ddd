package com.lrc.ocr.config;

import com.lrc.ocr.domain.user.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private CustomPasswordEncoder customPasswordEncoder;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(customPasswordEncoder);
    }

    /**
     * 配置security
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/auth/getAuth").anonymous()
                // 对于这些接口 任何人都能访问
                .antMatchers("/wechat/protal/**","/product/pay_notify").permitAll()
                //放行swagger
                .antMatchers("/doc.html","/swagger-resources/**","/webjars/**","/v2/**","/api/**").permitAll()
                // 添加Sentinel监控端点的放行规则
                .antMatchers("/actuator/sentinel/**").permitAll()
                .antMatchers("/metric").permitAll()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        // 把token校验过滤器添加到过滤器链，在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
