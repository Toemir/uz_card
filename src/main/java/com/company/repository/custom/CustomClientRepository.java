package com.company.repository.custom;

import com.company.dto.ClientFilterDTO;
import com.company.dto.ProfileFilterDTO;
import com.company.entity.ClientEntity;
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
public class CustomClientRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ClientEntity> paginationWithFilter(int size, int page, ClientFilterDTO dto) {
        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT * FROM client ");
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

        if (dto.getPhone() != null ) {
            builder.append(" and phone LIKE  %'"+dto.getPhone()+"'% ");
        }

        if (dto.getPassportNumber() != null ) {
            builder.append(" and passport_number LIKE  %'"+dto.getPassportNumber()+"' ");
        }

        if (dto.getPassportSeries() != null ) {
            builder.append(" and passport_series LIKE  %'"+dto.getPassportSeries()+"' ");
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
