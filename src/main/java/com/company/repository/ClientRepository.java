package com.company.repository;

import com.company.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<ClientEntity,String> {
    boolean existsByPassportSeriesAndPassportNumberAndVisibleTrue(String passportSeries, String passportNumber);

    Optional<ClientEntity> findByIdAndVisibleTrue(String id);

    Optional<ClientEntity> findByIdAndCompanyIdAndVisibleTrue(String id, String companyId);

    boolean existsByIdAndVisibleTrue(String clientId);
}
