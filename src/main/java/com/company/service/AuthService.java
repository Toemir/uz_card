package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.AuthDTO;
import com.company.dto.AuthResponseDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO login(AuthDTO authDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();
        ProfileEntity profile = user.getProfile();


        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setId(profile.getId());
        dto.setUsername(profile.getUsername());
        dto.setRole(GeneralRole.valueOf(String.valueOf(profile.getRole())));
        dto.setJwt(JwtUtil.encode(profile.getId(), GeneralRole.valueOf(String.valueOf(profile.getRole()))));

        return dto;
    }
}
