package com.company.service;

import com.company.dto.CompanyCreateAndUpdateDTO;
import com.company.dto.CompanyDTO;
import com.company.entity.CompanyEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.ProfileNotFoundException;
import com.company.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void create(CompanyCreateAndUpdateDTO dto) {
        if (companyRepository.existsByUsernameAndVisible(dto.getUsername(),Boolean.TRUE)) {
            log.error("Company already exists");
            throw new ItemAlreadyExistsException(
                    "Company already exists"
            );
        }

        CompanyEntity entity = toEntity(dto);

        companyRepository.save(entity);
    }

    public void update(String companyId, CompanyCreateAndUpdateDTO dto) {
        Optional<CompanyEntity> optional = companyRepository.findByIdAndVisible(companyId, Boolean.TRUE);

        if (optional.isEmpty()) {
            log.error("Company not found!");
            throw new ItemNotFoundException(
                    "Company not found!"
            );
        }

        CompanyEntity entity = optional.get();
        entity.setUsername(dto.getUsername());
        entity.setRole(dto.getRole());
        entity.setPassword(dto.getPassword());
        entity.setContact(dto.getContact());
        entity.setAddress(dto.getAddress());

        companyRepository.save(entity);
    }

    public List<CompanyDTO> pagination(int size, int page) {
        Sort sort = Sort.by(Sort.Direction.ASC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<CompanyEntity> all = companyRepository.findAll(pageable);

        if (all == null) {
            log.error("Companies not found");
            throw new ItemNotFoundException(
                    "Companies not found"
            );
        }

        return toResponseList(all);
    }

    private List<CompanyDTO> toResponseList(Page<CompanyEntity> all) {
        List<CompanyDTO> dtoList = new LinkedList<>();

        all.forEach(entity -> {
            CompanyDTO dto = new CompanyDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setAddress(entity.getAddress());
            dto.setContact(entity.getContact());
            dto.setPassword(entity.getPassword());
            dto.setRole(entity.getRole());
            dto.setUsername(entity.getUsername());
            dto.setCreatedDate(entity.getCreatedDate());

            dtoList.add(dto);
        });

        return dtoList;
    }

    public void delete(String companyId) {
        if (!companyRepository.existsByIdAndVisible(companyId,Boolean.TRUE)) {
            log.error("Company not found!");
            throw new ItemNotFoundException(
                    "Company not found!"
            );
        }

        companyRepository.changeVisible(Boolean.FALSE,companyId);
    }

    private CompanyEntity toEntity(CompanyCreateAndUpdateDTO dto) {
        CompanyEntity entity = new CompanyEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setRole(dto.getRole());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setContact(dto.getContact());

        return entity;
    }

    public CompanyEntity get(String username) {
        return companyRepository.findByUsernameAndVisible(username,Boolean.TRUE).orElseThrow(() ->
                new ProfileNotFoundException("Company not found!"));
    }
}
