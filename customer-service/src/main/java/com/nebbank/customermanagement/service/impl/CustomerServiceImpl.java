package com.nebbank.customermanagement.service.impl;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.entity.Customer;
import com.nebbank.customermanagement.exceptions.*;
import com.nebbank.customermanagement.repository.CustomerRepository;
import com.nebbank.customermanagement.service.CustomerService;
import com.nebbank.customermanagement.util.RandomNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        customer.setIsDeleted(false);
        customer.setId(RandomNumberGenerator.generateSevenDigitNumber());

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
        if(customer.getIsDeleted()==true){
            log.error("Tried to access deleted user:{}",attributeValue);
            throw new DeletedUserException("Attempted to access customer that is deleted: " + attributeValue);
        }

        log.info("Found customer with {} attribute,value:{}", attributeType, attributeValue);
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> findAllCustomers() throws NoCustomerToListException {
        List<Customer> customers = customerRepository.findAll();
        log.info("Number of customers found {}",customers.size());
        if (CollectionUtils.isEmpty(customers)) {
            log.error("No customers to list");
            throw new NoCustomerToListException("No customer to list",HttpStatus.NOT_FOUND);
        }
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
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
        log.info("Deleting customer with {} attribute,value:{}", attributeType, attributeValue);

        Customer customer;
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

        customer.setIsDeleted(true);
        customerRepository.save(customer);

        log.info("Successfully marked as deleted customer with {} attribute,value:{}", attributeType, attributeValue);
        return true;
    }





}
