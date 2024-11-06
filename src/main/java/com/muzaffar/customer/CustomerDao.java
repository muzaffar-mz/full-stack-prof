package com.muzaffar.customer;


import java.util.List;
import java.util.Optional;

/**
 * Created on 30/10/24.
 */

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(long id);
    void insertCustomer(Customer customer);
    boolean existsCustomerWithEmail(String email);

    boolean existsWithId(long id);

    void deleteById(long id);

    void updateCustomer(Customer customer);
}
