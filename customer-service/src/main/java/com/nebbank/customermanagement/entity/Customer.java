package com.nebbank.customermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;

@Entity
@Data
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
