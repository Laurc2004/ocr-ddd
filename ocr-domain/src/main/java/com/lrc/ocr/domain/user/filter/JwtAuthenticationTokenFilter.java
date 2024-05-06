package com.lrc.ocr.domain.user.filter;

import com.lrc.ocr.enums.BaseError;
import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器
 * 该过滤器放在user的过滤器之前
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        // 判断token是否为空，为空则放行
        // 放行进入下一个登录注册的拦截器
        if (StringUtils.isBlank(token)){
            filterChain.doFilter(request,response);
            return;
        }
        // 创建id
        String id;
        try {
            // 解析token
            Claims claims = JwtUtil.parseJWT(token);
            id = claims.getSubject();
        } catch (Exception e) {
            throw new ServiceException(BaseError.TOKEN_ERROR);
        }

        if (ObjectUtils.isEmpty(id)){
            throw new ServiceException(BaseError.LOGIN_USER_NOT_LOGIN_ERROR);
        }

        // 存入SecurityContextHolder
        // 放入过滤器中每次都执行一次可以确保处理请求整个过程中都可以方便的获取认证信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(id,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);

    }
}
