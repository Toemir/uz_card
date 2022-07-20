package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.ProfileCreateAndUpdateDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.ProfileFilterDTO;
import com.company.entity.ProfileEntity;
import com.company.exceptions.ProfileAlreadyExists;
import com.company.exceptions.ProfileNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.repository.custom.CustomProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final CustomProfileRepository customProfileRepository;

    public ProfileService(ProfileRepository profileRepository, CustomProfileRepository customProfileRepository) {
        this.profileRepository = profileRepository;
        this.customProfileRepository = customProfileRepository;
    }

    public ProfileEntity get(String id) {
        return profileRepository.findById(id).orElseThrow(() ->
                new ProfileNotFoundException(
                        "Profile not found!"
                ));
    }

    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = getCurrentUser();
        return user.getProfile();
    }

    public void create(ProfileCreateAndUpdateDTO dto) {
        if (profileRepository.existsByUsernameAndVisible(dto.getUsername(),Boolean.TRUE)) {
            log.error("Username is already taken");
            throw new ProfileAlreadyExists(
                    "Username is already taken"
            );
        }

        ProfileEntity entity = toEntity(dto);

        profileRepository.save(entity);
    }

    public void update(String profileId,ProfileCreateAndUpdateDTO dto) {
        Optional<ProfileEntity> optional = profileRepository
                .findByIdAndVisibleTrue(profileId);

        if (optional.isEmpty()) {
        log.error("Profile not found!");
            throw new ProfileNotFoundException(
                    "Profile not found!"
            );
        }

        ProfileEntity entity = optional.get();
        entity.setUsername(dto.getUsername());
        entity.setSurname(dto.getSurname());
        entity.setMiddleName(dto.getMiddleName());
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());

        profileRepository.update(entity.getName(),entity.getSurname(),
                entity.getMiddleName(),entity.getUsername(),
                entity.getPassword(),entity.getRole(),entity.getId());
    }

    private ProfileEntity toEntity(ProfileCreateAndUpdateDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getRole());

        return entity;
    }

    public List<ProfileDTO> paginationWithFilter(int size, int page, ProfileFilterDTO dto) {
        List<ProfileEntity> list = customProfileRepository.paginationWithFilter(size, page, dto);

        return toResponseList(list);
    }

    private List<ProfileDTO> toResponseList(List<ProfileEntity> list) {
        List<ProfileDTO> dtoList = new LinkedList<>();

        list.forEach(entity -> {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setStatus(entity.getStatus());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setRole(entity.getRole());
            dto.setMiddleName(entity.getMiddleName());
            dto.setUsername(entity.getUsername());

            dtoList.add(dto);
        });

        return dtoList;
    }
}
