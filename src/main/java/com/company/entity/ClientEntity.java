package com.company.entity;

import com.company.enums.ClientStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"passport_series","passport_number"}), name = "client")
@NoArgsConstructor
public class ClientEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;


    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String surname;

    @Column(name = "middle_name",nullable = false)
    public String middleName;

    @Column(nullable = false)
    public String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public ClientStatus status = ClientStatus.NON_ACTIVE;

    @Column(nullable = false,name = "passport_series")
    private String passportSeries;

    @Column(nullable = false,name = "passport_number")
    private String passportNumber;

    @Column(name = "company_id")
    private String companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",nullable = false,insertable = false,updatable = false)
    public CompanyEntity company;

}
