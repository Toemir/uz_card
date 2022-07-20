package com.company.entity;

import com.company.enums.TransferStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transfer")
public class TransferEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;


    @Column(name = "from_card_id")
    private String fromCardId;

    @ManyToOne
    @JoinColumn(name = "from_card_id",nullable = false,insertable = false,updatable = false)
    private CardEntity fromCard;

    @Column(name = "to_card_id")
    private String toCardId;

    @ManyToOne
    @JoinColumn(name = "to_card_id",nullable = false,insertable = false,updatable = false)
    private CardEntity toCard;

    @Column(nullable = false,name = "total_amount")
    private Long totalAmount;

    @Column(nullable = false,name = "transfer_amount")
    private Long transferAmount;

    @Column(nullable = false,name = "service_amount")
    private Long serviceAmount;

    @Column(nullable = false,name = "service_percentage")
    private Integer servicePercentage;

    @Column(nullable = false)
    private TransferStatus status;

    @Column(name = "company_id")
    private String companyId;

    @JoinColumn(name = "company_id",nullable = false,insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

}
