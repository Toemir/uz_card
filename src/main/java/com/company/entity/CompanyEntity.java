package com.company.entity;

import com.company.enums.CompanyRole;
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
@Table(name = "company")
public class CompanyEntity extends BaseEntity{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;


    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "contact",nullable = false)
    private String contact;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyRole role;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


}
