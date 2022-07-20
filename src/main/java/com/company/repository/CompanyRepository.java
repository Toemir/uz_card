package com.company.repository;

import com.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity,Integer> {
    boolean existsByUsernameAndVisible(String username, Boolean visible);

    Optional<CompanyEntity> findByIdAndVisible(String companyId, Boolean visible);

    boolean existsByIdAndVisible(String companyId, Boolean visible);

    @Modifying
    @Transactional
    @Query("update CompanyEntity set visible=?1 where id=?2")
    void changeVisible(Boolean visible, String companyId);

    Optional<CompanyEntity> findByUsernameAndVisible(String username, Boolean visible);

}
