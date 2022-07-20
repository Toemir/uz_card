package com.company.config;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.CompanyRepository;
import com.company.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    private final CompanyRepository companyRepository;

    public CustomUserDetailsService(ProfileRepository profileRepository,
                                    CompanyRepository companyRepository) {
        this.profileRepository = profileRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // company or profile(uz_card_.....)
        if (username.startsWith("uz_card")) {
            Optional<ProfileEntity> entity = profileRepository
                    .findByUsernameAndVisible(username,Boolean.TRUE);
            if (entity.isEmpty()) {
                throw new UsernameNotFoundException("User Not Found");
            }
            return new CustomUserDetails(entity.get());
        } else { // company
            Optional<CompanyEntity> entity = companyRepository
                    .findByUsernameAndVisible(username,Boolean.TRUE);
            if (entity.isEmpty()) {
                throw new UsernameNotFoundException("Company Not Found");
            }

            return new CustomUserDetails(entity.get());
        }

    }
}
