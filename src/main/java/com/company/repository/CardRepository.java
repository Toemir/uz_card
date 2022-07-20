package com.company.repository;

import com.company.entity.CardEntity;
import com.company.enums.CardStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends PagingAndSortingRepository<CardEntity,String> {
    boolean existsByProfileIdAndCompanyIdAndVisibleTrue(String profileId, String companyId);

    @Modifying
    @Transactional
    @Query("update CardEntity set status=?1 where id=?2")
    void changeStatus(CardStatus status, String id);

    Optional<CardEntity> findByIdAndVisibleTrue(String id);

    Optional<CardEntity> findByIdAndAndCompanyIdAndVisibleTrue(String id, String companyId);

    List<CardEntity> findAllByPhone(String phone);

    List<CardEntity> findAllByClientId(String clientId);

    Optional<CardEntity> findByNumberAndVisibleTrue(String number);

    Optional<CardEntity> findByNumberAndCompanyIdAndVisibleTrue(String number, String companyId);

    @Query("select new CardEntity(c.balance)from CardEntity c where c.number=?1 and c.visible=true")
    Optional<CardEntity> findCardBalanceByNumberAndVisibleTrue(String number);

    Optional<CardEntity> findCardBalanceByNumberAndCompanyIdAndVisibleTrue(String number, String companyId);
}
