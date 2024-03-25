package com.nebbank.customermanagement.repository;

import com.nebbank.customermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    /**
     * Find a customer by their mobile number
     * @param mobileNumber the mobile number of the customer
     * @return an optional customer
     */
    Optional<Customer> findByMobileNumber(String mobileNumber);

    /**
     * Find a customer by their email
     * @param email the email of the customer
     * @return an optional customer
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Find a customer by their first name
     * @param firstName the first name of the customer
     * @return an optional customer
     */
    Optional<Customer> findByFirstName(String firstName);

    /**
     * Find a customer by their last name
     * @param lastName the last name of the customer
     * @return an optional customer
     */
    Optional<Customer> findByLastName(String lastName);

    /**
     * Find all customers created at a specific date and time
     * @param localDateTime the date and time the customer was created
     * @return a list of customers
     */
    List<Customer> findByCreatedAt(LocalDateTime localDateTime);

    /**
     * Find all customers updated at a specific date and time
     * @param localDateTime the date and time the customer was updated
     * @return a list of customers
     */
    List<Customer> findByUpdatedAt(LocalDateTime localDateTime);

    /**
     * Find all active customers
     * @param isActive whether the customer is active or not
     * @return a list of customers
     */
    List<Customer> findByIsActive(Boolean isActive);

    /**
     * Find all deleted customers
     * @param isDeleted whether the customer is deleted or not
     * @return a list of customers
     */
    List<Customer> findByIsDeleted(Boolean isDeleted);

}
