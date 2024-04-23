package com.lrc.ocr.domain.user.model.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static com.lrc.ocr.constants.HttpConstants.SALT;

@Slf4j
public class LoginUserEntity implements UserDetails {
//    @Resource
//    private PasswordEncoder passwordEncoder;

//    @Resource
//    private RedisTemplate<String,String> redisTemplate;

    private UserEntity userEntity;

    public LoginUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return SALT;
    }

    @Override
    public String getUsername() {
        return userEntity.getOpenid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
