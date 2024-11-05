package com.muzaffar.customer;


import com.muzaffar.exception.DuplicateResourceException;
import com.muzaffar.exception.RequestValidationException;
import com.muzaffar.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 30/10/24.
 */

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer with id " + id + " not found")
                );
    }

    public void addCustomer(CustomerRegistrationRequest request) {
        //check if email exists
        if (customerDao.existsPesonWithEmail(request.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        //add
        customerDao.insertCustomer(
                new Customer(request.name(), request.email(), request.age())
        );
    }


    public void deleteCustomerById(Integer id) {
        if (!customerDao.existsWithId(id)) {
            throw new ResourceNotFoundException("Customer with id [" + id + "] not found");
        }

        customerDao.deleteById(id);
    }

    public void editCustomerById(Integer id, CustomerEditRequest request) {
        var customerOptional = customerDao.selectCustomerById(id);

        if (customerOptional.isEmpty()) {
            throw new ResourceNotFoundException("Customer with id [" + id + "] not found");
        }

        var customer = customerOptional.get();

        boolean changesMade = false;
        if (request.name() != null && !request.name().equals(customer.getName())) {
            customer.setName(request.name());
            changesMade = true;
        }

        if (request.email() != null && !request.email().equals(customer.getEmail())) {
            if (customerDao.existsPesonWithEmail(request.email())) {
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(request.email());
            changesMade = true;
        }

        if (request.age() != null && !request.age().equals(customer.getAge())) {
            customer.setAge(request.age());
            changesMade = true;
        }

        if (!changesMade) {
            throw new RequestValidationException("no data changes found");
        }
        customerDao.updateCustomer(customer);
    }
}
