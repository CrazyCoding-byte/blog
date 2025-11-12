package com.yzx.spring_security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yzx
 * @create 2022-12-22 4:02
 */
@Data
public class LoginUser implements UserDetails {
    private YzxUser yzxUser;

    private List<String> permissions;
    //不要序列化到redis中 因为我们只需要permissions 这个simpleGrantedAuthorities是给security看的
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

    public LoginUser() {

    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public LoginUser(YzxUser yzxUser, List<String> permissions) {
        this.yzxUser = yzxUser;
        this.permissions = permissions;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = permissions.stream().map(item -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(item);
            return simpleGrantedAuthority;
        }).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return yzxUser.getPassword();
    }

    @Override
    public String getUsername() {
        return yzxUser.getUsername();
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
