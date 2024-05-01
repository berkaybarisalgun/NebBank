package com.nebbank.customermanagement.service.impl;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.entity.Customer;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;
import com.nebbank.customermanagement.exceptions.WrongArgumentException;
import com.nebbank.customermanagement.repository.CustomerRepository;
import com.nebbank.customermanagement.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public void createCustomer(CustomerDto customerDto) {
        log.info("Attempting to create a new customer:{}", customerDto);
        Customer customer = modelMapper.map(customerDto, Customer.class);

        checkCustomerExistence(customerDto.getMobileNumber(), "mobile number");
        checkCustomerExistence(customerDto.getEmail(), "email");

        customerRepository.save(customer);
        log.info("Customer created successfully: {}", customerDto);

    }


    private void checkCustomerExistence(String value, String valueType) throws CustomerCreationException {
        log.info("Checking customer existince with {} attribute,value:{}", valueType, value);
        Optional<Customer> existingCustomer = valueType.equals("email") ?
                customerRepository.findByEmail(value) :
                customerRepository.findByMobileNumber(value);
        if (existingCustomer.isPresent()) {
            log.error("customer already exist with {} attribute,value:{}", valueType, value);
            throw new CustomerCreationException("Customer already exists with given " + valueType + ": " + value);
        }
    }


    @Override
    public CustomerDto findCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException {
        Customer customer = null;
        log.info("Find customer with {} attribute,value:{}", attributeType, attributeValue);

        if ("email".equals(attributeType.toLowerCase())) {
            customer = customerRepository.findByEmail(attributeValue).orElseThrow(() -> new CustomerNotFoundException("Customer not found with given email:" + attributeValue));
        } else if ("phone".equals(attributeType.toLowerCase())) {
            customer = customerRepository.findByMobileNumber(attributeValue).orElseThrow(() -> new CustomerNotFoundException("Customer not found with given phone:" + attributeValue));
        } else if ("id".equals(attributeType.toLowerCase())) {
            Long id = Long.valueOf(attributeValue);
            customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with given id:" + id));
        } else {
            throw new WrongArgumentException("Attempted to access customer with unsupported attribute type: " + attributeType);
        }
        log.info("Found customer with {} attribute,value:{}", attributeType, attributeValue);
        return modelMapper.map(customer, CustomerDto.class);
    }


    @Override
    @Transactional
    public Boolean updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException {
        boolean isUpdated = false;

        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerDto.getEmail());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setEmail(customerDto.getEmail());
            customer.setMobileNumber(customerDto.getMobileNumber());
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setDateOfBirth(customerDto.getDateOfBirth());
            log.info("Customer updated to {}", customerDto);
            customerRepository.save(customer);
            isUpdated = true;
        } else {
            log.error("Customer Not Found to update:{}", customerDto);
            throw new CustomerNotFoundException("Customer not found");
        }

        return isUpdated;
    }


    @Override
    @Transactional
    public Boolean deleteCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException {
        Boolean isDeleted = false;
        log.info("Deleting customer with {} attribute,value:{}", attributeType, attributeValue);

        Customer customer = null;
        if ("email".equalsIgnoreCase(attributeType)) {
            customer = customerRepository.findByEmail(attributeValue)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + attributeValue + " not found"));

        } else if ("phone".equalsIgnoreCase(attributeType)) {
            customer = customerRepository.findByMobileNumber(attributeValue)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with phone " + attributeValue + " not found"));
        } else if ("id".equalsIgnoreCase(attributeType)) {
            Long id = Long.valueOf(attributeValue);
            customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + attributeValue + " not found"));
        } else {
            throw new WrongArgumentException("Attempted to access customer with unsupported attribute type: " + attributeType);
        }
        customerRepository.delete(customer);
        isDeleted = !customerRepository.existsById(customer.getId());
        if(isDeleted) {
            log.info("Deleted customer with {} attribute,value:{}", attributeType, attributeValue);
        } else {
            log.info("Failed to delete customer with {} attribute,value:{}", attributeType, attributeValue);
        }
        return isDeleted;
    }




}
