package com.nebbank.customermanagement.service.impl;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.entity.Customer;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;
import com.nebbank.customermanagement.repository.CustomerRepository;
import com.nebbank.customermanagement.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;



    @Override
    public void createCustomer(CustomerDto customerDto){

        Customer customer = modelMapper.map(customerDto, Customer.class);

        checkCustomerExistence(customerDto.getMobileNumber(), "mobile number");
        checkCustomerExistence(customerDto.getEmail(), "email");

        customerRepository.save(customer);
    }



    private void checkCustomerExistence(String value, String valueType) throws CustomerCreationException {
        Optional<Customer> existingCustomer = valueType.equals("email") ?
                customerRepository.findByEmail(value) :
                customerRepository.findByMobileNumber(value);

        if(existingCustomer.isPresent()){
            throw new CustomerCreationException("Customer already exists with given " + valueType + ": " + value);
        }
    }





    @Override
    public CustomerDto findCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException {
        Customer customer = null;


        if ("email".equals(attributeType.toLowerCase())) {
            customer = customerRepository.findByEmail(attributeValue).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        } else if ("phone".equals(attributeType.toLowerCase())) {
            customer = customerRepository.findByMobileNumber(attributeValue).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
       }
        else if("id".equals(attributeType.toLowerCase())){
            Long id= Long.valueOf(attributeValue);
            customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        }
        return modelMapper.map(customer, CustomerDto.class);
    }




    @Override
    @Transactional
    public Boolean updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException {

        boolean isUpdated = false;

        Optional<Customer> customer=customerRepository.findByEmail(customerDto.getEmail());
        if(customer.isPresent()){
            customer.get().setEmail(customerDto.getEmail());
            customer.get().setMobileNumber(customerDto.getMobileNumber());
            customer.get().setFirstName(customerDto.getFirstName());
            customer.get().setLastName(customerDto.getLastName());
            customerRepository.save(customer.get());
            isUpdated=true;
        }
        else{
            throw new CustomerNotFoundException("Customer not found");
        }

        return isUpdated;

    }

    @Override
    public void deleteCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException {
        if ("email".equalsIgnoreCase(attributeType)) {
            Customer customer = customerRepository.findByEmail(attributeValue)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + attributeValue + " not found"));
            customerRepository.delete(customer);
        } else if ("phone".equalsIgnoreCase(attributeType)) {
            Customer customer = customerRepository.findByMobileNumber(attributeValue)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with phone " + attributeValue + " not found"));
            customerRepository.delete(customer);
        } else if ("id".equalsIgnoreCase(attributeType)) {
            Long id = Long.valueOf(attributeValue);
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + attributeValue + " not found"));
            customerRepository.delete(customer);
        } else {
            throw new IllegalArgumentException("Unknown attribute type: " + attributeType);
        }
    }


}
