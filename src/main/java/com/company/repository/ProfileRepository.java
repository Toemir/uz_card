package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity,String> {
    Optional<ProfileEntity> findByUsernameAndVisible(String username,Boolean visible);

    boolean existsByUsernameAndVisible(String username, Boolean visible);

    boolean existsByIdAndVisibleTrue(String profileId);

    Optional<ProfileEntity> findByIdAndVisibleTrue(String profileId);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set name=?1,surname=?2,middleName=?3," +
            " username=?4,password=?5,role=?6 where id=?7")
    void update(String name, String surname,
                String middleName, String username,
                String password, ProfileRole role,
                String id);
}
