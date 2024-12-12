package com.muzaffar.customer;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 31/10/24.
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);
}
