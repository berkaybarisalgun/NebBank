package com.nebbank.customermanagement.service;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerDeletionException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;
import com.nebbank.customermanagement.exceptions.CustomerUpdateException;

public interface CustomerService {

    void createCustomer(CustomerDto customerDto) throws CustomerCreationException;

    CustomerDto findCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException;

    void updateCustomer(CustomerDto customerDto) throws CustomerUpdateException;

    void deleteCustomerByAttribute(String attributeType, String attributeValue) throws CustomerDeletionException;


}
