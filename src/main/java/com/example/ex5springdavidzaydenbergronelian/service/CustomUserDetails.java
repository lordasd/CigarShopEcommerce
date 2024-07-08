package com.example.ex5springdavidzaydenbergronelian.service;

import com.example.ex5springdavidzaydenbergronelian.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails
 * @param user
 */
public record CustomUserDetails(User user) implements UserDetails {

    /**
     * getAuthorities
     * @return Collection<? extends GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * getPassword
     * @return String
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * getUsername
     * @return String
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * isAccountNonExpired
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    /**
     * isAccountNonLocked
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    /**
     * isCredentialsNonExpired
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    /**
     * isEnabled
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}