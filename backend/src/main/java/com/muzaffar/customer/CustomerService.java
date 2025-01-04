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

    public Customer getCustomerById(long id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer with id [" + id + "] not found")
                );
    }

    public void addCustomer(CustomerRegistrationRequest request) {
        //check if email exists
        if (customerDao.existsCustomerWithEmail(request.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        //add
        customerDao.insertCustomer(
                new Customer(request.name(), request.email(), request.age(), request.gender())
        );
    }


    public void deleteCustomerById(Long id) {
        if (!customerDao.existsWithId(id)) {
            throw new ResourceNotFoundException("Customer with id [" + id + "] not found");
        }

        customerDao.deleteById(id);
    }


    public void editCustomerById(Long id, CustomerEditRequest request) {
        var customer = getCustomerById(id);

        boolean changesMade = false;
        if (request.name() != null && !request.name().equals(customer.getName())) {
            customer.setName(request.name());
            changesMade = true;
        }

        if (request.email() != null && !request.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerWithEmail(request.email())) {
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(request.email());
            changesMade = true;
        }

        if (request.age() != null && !request.age().equals(customer.getAge())) {
            customer.setAge(request.age());
            changesMade = true;
        }

        if (request.gender() != null && !request.gender().equals(customer.getGender())) {
            customer.setGender(request.gender());
            changesMade = true;
        }

        if (!changesMade) {
            throw new RequestValidationException("no data changes found");
        }
        customerDao.updateCustomer(customer);
    }
}
