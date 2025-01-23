package com.muzaffar.customer.repo;


import com.muzaffar.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 31/10/24.
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);
    Optional<Customer> findCustomerByEmail(String email);
}
