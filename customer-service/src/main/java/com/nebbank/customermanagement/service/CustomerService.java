package com.nebbank.customermanagement.service;

import com.nebbank.customermanagement.dto.CustomerDto;
import com.nebbank.customermanagement.exceptions.CustomerCreationException;
import com.nebbank.customermanagement.exceptions.CustomerNotFoundException;

public interface CustomerService {

    void createCustomer(CustomerDto customerDto) throws CustomerCreationException;

    CustomerDto findCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException;

    Boolean updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException;

    Boolean deleteCustomerByAttribute(String attributeType, String attributeValue) throws CustomerNotFoundException;


}
