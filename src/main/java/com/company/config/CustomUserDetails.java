package com.company.config;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.company.enums.ProfileRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class CustomUserDetails implements UserDetails {

    private String id;
    private String username;
    private String password;
    private Boolean visible;
    private GeneralStatus status;
    private GeneralRole role; //BANK,PAYMENT, ADMIN,MODERATOR

    public CustomUserDetails(ProfileEntity profile) {
        this.id = profile.getId();
        this.username = profile.getSurname();
        this.role = GeneralRole.valueOf(String.valueOf(profile.getRole()));
        this.password = profile.getName(); // getPassword()
    }
    public CustomUserDetails(CompanyEntity company) {
        this.id = company.getId();
        this.username = company.getUsername();
        this.role = GeneralRole.valueOf(String.valueOf(company.getRole()));
        this.password = company.getName(); // getPassword()
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new LinkedList<>(Collections.singletonList(new SimpleGrantedAuthority(role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public GeneralRole getRole() {
        return role;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (status.equals(GeneralStatus.ACTIVE)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return visible;
    }

    public ProfileEntity getProfile() {
        ProfileEntity profile = new ProfileEntity();
        profile.setId(id);
        profile.setRole(ProfileRole.valueOf(role.name()));
        profile.setUsername(username);
        return profile;
    }
}
