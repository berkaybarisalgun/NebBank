package com.nebbank.customermanagement.controller;

import com.nebbank.CommonModel.dto.CustomerDto;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;
import com.nebbank.customermanagement.exceptions.NoCustomerToListException;
import com.nebbank.customermanagement.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Customers in NebBank",
        description = "CRUD REST APIs in NebBank to CREATE,UPDATE,FETCH and DELETE customer details"
)
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Create Customer REST API",
            description = "REST API to create new Customer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class)
                    )
            )
            ,
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = CustomerCreationException.class)
                    )
            )

    }
    )
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        try {
            customerService.createCustomer(customerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
        } catch (CustomerCreationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "Rest API to fetch customer details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = CustomerDto.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = CustomerNotFoundException.class)
                    )

            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<?> fetchCustomerByAttribute(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Type of the attribute to search by",
                    required = true,
                    schema = @Schema(allowableValues = {"phone", "email", "id"})
            )
            @RequestParam(name = "attributeType") String attributeType,
            @RequestParam(name = "attributeValue") @NotBlank String attributeValue) {

        try {
            CustomerDto customer = customerService.findCustomerByAttribute(attributeType, attributeValue);
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Fetch All Customers Details REST API",
            description = "Rest API to fetch all customer details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = CustomerDto.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = NoCustomerToListException.class)
                    )

            )
    })
    @GetMapping("/fetchall")
    public ResponseEntity<?> fetchAllCustomers() {
        try {
            List<CustomerDto> customers = customerService.findAllCustomers();
            return ResponseEntity.status(HttpStatus.OK).body(customers);
        } catch (NoCustomerToListException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Update Customer Details REST API",
            description = "REST API to update Customer details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = CustomerDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = CustomerNotFoundException.class)
                    )

            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        try {
            customerService.updateCustomer(customerDto);
            return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Delete Customer Details REST API",
            description = "REST API to delete Customer details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = CustomerNotFoundException.class)
                    )

            )
    }
    )

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }


}
