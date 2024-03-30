package com.nebbank.customermanagement.controller;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody CustomerDto customerDto) throws CustomerCreationException {

        customerService.createCustomer(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerDto);

    }

}
