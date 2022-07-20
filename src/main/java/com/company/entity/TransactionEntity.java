package com.company.entity;

import com.company.enums.TransactionStatus;
import com.company.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;


    @Column(name = "card_id")
    private String cardId;

    @ManyToOne
    @JoinColumn(name = "card_id",nullable = false,insertable = false,updatable = false)
    private CardEntity card;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "transfer_id")
    private String transferId;

    @ManyToOne
    @JoinColumn(name = "transfer_id",nullable = false,insertable = false,updatable = false)
    private TransferEntity transfer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.CREATED;

}
