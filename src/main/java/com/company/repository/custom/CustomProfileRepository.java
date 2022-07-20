package com.company.repository.custom;

import com.company.dto.ProfileFilterDTO;
import com.company.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class CustomProfileRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> paginationWithFilter(int size, int page,ProfileFilterDTO dto) {
        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT * FROM profile ");
        builder.append(" where visible = true ");

        if (dto.getName() != null ) {
            builder.append(" and name LIKE  %'"+dto.getName()+"'% ");
        }

        if (dto.getSurname() != null ) {
            builder.append(" and surname LIKE  %'"+dto.getSurname()+"'% ");
        }

        if (dto.getMiddleName() != null ) {
            builder.append(" and middle_name LIKE  %'"+dto.getMiddleName()+"'% ");
        }

        if (dto.getUsername() != null ) {
            builder.append(" and username LIKE  %'"+dto.getUsername()+"'% ");
        }

        if (dto.getRole() != null ) {
            builder.append(" and role = '"+dto.getRole()+"' ");
        }

        if (dto.getStatus() != null ) {
            builder.append(" and status = '"+dto.getStatus()+"' ");
        }

        if (dto.getFromCreatedDate() != null && dto.getToCreatedDate() == null) {
            LocalDate localDate = LocalDate.parse(dto.getFromCreatedDate());
            LocalDateTime fromTime = LocalDateTime.of(localDate, LocalTime.MIN); // yyyy-MM-dd 00:00:00
            LocalDateTime toTime = LocalDateTime.of(localDate, LocalTime.MAX); // yyyy-MM-dd 23:59:59
            builder.append(" and created_date between '" + fromTime + "' and '" + toTime + "' ");
        } else if (dto.getFromCreatedDate() != null && dto.getToCreatedDate() != null) {
            builder.append(" and created_date between '" + dto.getFromCreatedDate() + "' and '" + dto.getToCreatedDate() + "' ");
        }

        builder.append(" limit "+size+" offset "+page+" ");

        Query query = entityManager.createNativeQuery(builder.toString());

        return query.getResultList();
    }
}
