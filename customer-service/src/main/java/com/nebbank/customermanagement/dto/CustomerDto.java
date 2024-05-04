package com.nebbank.customermanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Customer",
        description = "Schema to hold Customer information"
)
@Data
public class CustomerDto {

    @Schema(
                description ="Name of the customer",example = "Baris"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min=2,max = 30,message = "The length of the customer name should be between 2 and 30")
    private String firstName;

    @Schema(
            description ="LastName of the customer",example = "Algun"
    )
    @NotEmpty(message = "lastName cannot be null or empty")
    @Size(min=2,max = 30,message = "The Length of the customer surname should be between 2 and 30")
    private String lastName;

    @Schema(
            description ="Mail of the customer",example = "info@barisalgun.dev"
    )
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description ="Mobile Number of the customer",example = "5555555555"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    @NotEmpty(message = "Mobile number cannot be null or empty")
    private String mobileNumber;

    @Schema(
            description ="Customer date of birth",example = "19.03.2001"
    )
    @NotEmpty(message = "Date of birth is required")
    private String dateOfBirth;



}
