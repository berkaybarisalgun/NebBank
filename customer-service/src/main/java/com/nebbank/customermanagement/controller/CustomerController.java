package com.nebbank.customermanagement.controller;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;
import com.nebbank.customermanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/fetch")
    public ResponseEntity fetchCustomerByAttribute(@RequestParam String attributeType, @RequestParam String attributeValue) throws CustomerNotFoundException, CustomerNotFoundException {
        return ResponseEntity
               .status(HttpStatus.OK)
               .body(customerService.findCustomerByAttribute(attributeType, attributeValue));
    }


    @PutMapping("/update")
    public ResponseEntity updateCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
        customerService.updateCustomer(customerDto);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body(customerDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomerByAttribute(@RequestParam String attributeType, @RequestParam String attributeValue) throws CustomerNotFoundException {
        customerService.deleteCustomerByAttribute(attributeType, attributeValue);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body("deleted successfully");
    }

}
