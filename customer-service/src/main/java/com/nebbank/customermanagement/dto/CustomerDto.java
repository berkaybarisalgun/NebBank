package com.nebbank.customermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for Customer
 */
@Data
public class CustomerDto {

    /**
     * First name of the customer
     */
    @NotEmpty(message = "Name can not  e a null or empty")
    @Size(min=2,max = 30,message = "The Length of the customer name should be between 2 and 30")
    private String firstName;

    /**
     * Last name of the customer
     */
    @NotEmpty(message = "Name can not  e a null or empty")
    @Size(min=2,max = 30,message = "The Length of the customer surname should be between 2 and 30")
    private String lastName;

    /**
     * Email of the customer
     */
    @NotEmpty(message = "Email can not  e a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    /**
     * Mobile number of the customer
     */
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    @NotEmpty(message = "Mobile number can not  e a null or empty")
    private String mobileNumber;

    /**
     * Date of birth of the customer
     */
    @NotEmpty(message = "Please enter your date of birth")
    private String dateOfBirth;



}
