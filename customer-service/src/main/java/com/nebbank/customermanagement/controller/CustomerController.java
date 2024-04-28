package com.nebbank.customermanagement.controller;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;
import com.nebbank.customermanagement.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        try {
            customerService.createCustomer(customerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
        } catch (CustomerCreationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchCustomerByAttribute(@RequestParam String attributeType, @RequestParam String attributeValue) {
        try {
            CustomerDto customer = customerService.findCustomerByAttribute(attributeType, attributeValue);
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        try {
            customerService.updateCustomer(customerDto);
            return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomerByAttribute(@RequestParam String attributeType, @RequestParam String attributeValue) {
        try {
            customerService.deleteCustomerByAttribute(attributeType, attributeValue);
            return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
