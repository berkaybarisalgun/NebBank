package com.nebbank.Account.Service.entity;

import com.nebbank.Account.Service.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Account extends BaseEntity{

    @Id
    @Column(name = "account_id")
    private Long id;

    @OneToOne
    private Customer customerId;

    private int balance;

    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

}
