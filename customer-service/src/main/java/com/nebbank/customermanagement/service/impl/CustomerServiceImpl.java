package com.nebbank.customermanagement.service.impl;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.entity.Customer;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerDeletionException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;
import com.nebbank.customermanagement.exceptions.CustomerUpdateException;
import com.nebbank.customermanagement.repository.CustomerRepository;
import com.nebbank.customermanagement.service.CustomerService;
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
    public void createCustomer(CustomerDto customerDto) throws CustomerCreationException {
        Customer customer=modelMapper.map(customerDto, Customer.class);


        Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerCreationException("Customer already exists with given mobile Number:"+customerDto.getMobileNumber());
        }

        Customer savedCustomer=customerRepository.save(customer);

        customerRepository.save(savedCustomer);


    }




    @Override
    public CustomerDto findCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException {
        return null;
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) throws CustomerUpdateException {

    }

    @Override
    public void deleteCustomerByAttribute(String attributeType, String attributeValue) throws CustomerDeletionException {

    }
}
