package com.nebbank.customermanagement;

import com.nebbank.common.entity.Customer;
import com.nebbank.customermanagement.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    /**
     * Sets up the customer object and saves it to the database before each test.
     */
    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@example.com");
        customer.setMobileNumber("1234567890");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setIsActive(true);
        customer.setIsDeleted(false);

        customerRepository.save(customer);
    }

    /**
     * Cleans up the database after each test by deleting all customers.
     */
    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    /**
     * Tests that the findByMobileNumber method returns the correct customer when a valid mobile number is provided.
     */
    @Test
    void findByMobileNumber_WhenExists_ReturnsCustomer() {
        Optional<Customer> foundCustomer = customerRepository.findByMobileNumber("1234567890");
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getMobileNumber()).isEqualTo(customer.getMobileNumber());
    }

    /**
     * Tests that the findByEmail method returns the correct customer when a valid email is provided.
     */
    @Test
    void findByEmail_WhenExists_ReturnsCustomer() {
        Optional<Customer> foundCustomer = customerRepository.findByEmail("johndoe@example.com");
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getEmail()).isEqualTo(customer.getEmail());
    }

    /**
     * Tests that the findByFirstName method returns the correct customer when a valid first name is provided.
     */
    @Test
    void findByFirstName_WhenExists_ReturnsCustomers() {
        List<Customer> foundCustomers = customerRepository.findByFirstName("John");
        assertThat(foundCustomers).hasSize(1);
        assertThat(foundCustomers.get(0).getFirstName()).isEqualTo(customer.getFirstName());
    }

    /**
     * Tests that the findByLastName method returns the correct customer when a valid last name is provided.
     */
    @Test
    void findByLastName_WhenExists_ReturnsCustomers() {
        List<Customer> foundCustomers = customerRepository.findByLastName("Doe");
        assertThat(foundCustomers).hasSize(1);
        assertThat(foundCustomers.get(0).getLastName()).isEqualTo(customer.getLastName());
    }

    /**
     * Tests that the findByIsActive method returns the correct active customer when a valid active status is provided.
     */
    @Test
    void findByIsActive_WhenActive_ReturnsActiveCustomers() {
        List<Customer> activeCustomers = customerRepository.findByIsActive(true);
        assertThat(activeCustomers).isNotEmpty();
        assertThat(activeCustomers.get(0).getIsActive()).isTrue();
    }

    /**
     * Tests that the findByIsDeleted method returns the correct non-deleted customer when a valid deleted status is provided.
     */
    @Test
    void findByIsDeleted_WhenNotDeleted_ReturnsNonDeletedCustomers() {
        List<Customer> notDeletedCustomers = customerRepository.findByIsDeleted(false);
        assertThat(notDeletedCustomers).isNotEmpty();
        assertThat(notDeletedCustomers.get(0).getIsDeleted()).isFalse();
    }

}
