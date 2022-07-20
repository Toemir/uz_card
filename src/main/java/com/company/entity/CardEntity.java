package com.company.entity;

import com.company.enums.CardStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "card")
public class CardEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false,name = "expired_date")
    private LocalDateTime expiredDate = LocalDateTime.now().plusYears(3);

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.NOT_ACTIVE;

    @Column(nullable = false)
    private Long balance = 0l;

    @Column(name = "profile_id")
    private String profileId;

    @ManyToOne
    @JoinColumn(name = "profile_id",nullable = false,insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "company_id")
    private String companyId;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false,insertable = false,updatable = false)
    private CompanyEntity company;

    public CardEntity(Long balance) {
        this.balance = balance;
    }
}
