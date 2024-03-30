package com.nebbank.customermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long Id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "account_id")
    private String accountId;


}
