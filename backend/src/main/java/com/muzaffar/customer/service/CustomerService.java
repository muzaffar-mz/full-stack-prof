package com.muzaffar.customer.service;


import com.muzaffar.customer.entity.Customer;
import com.muzaffar.customer.model.CustomerDTO;
import com.muzaffar.customer.model.CustomerDTOMapper;
import com.muzaffar.customer.model.CustomerEditRequest;
import com.muzaffar.customer.model.CustomerRegistrationRequest;
import com.muzaffar.customer.repo.CustomerDao;
import com.muzaffar.exception.DuplicateResourceException;
import com.muzaffar.exception.RequestValidationException;
import com.muzaffar.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 30/10/24.
 */

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao, CustomerDTOMapper customerDTOMapper,
                           PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDao.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .toList();
    }

    public CustomerDTO getCustomerById(long id) {
        return customerDao.selectCustomerById(id)
                .map(customerDTOMapper)
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
                new Customer(request.name(), request.email(), passwordEncoder.encode(request.password()), request.age(), request.gender())
        );
    }


    public void deleteCustomerById(Long id) {
        if (!customerDao.existsWithId(id)) {
            throw new ResourceNotFoundException("Customer with id [" + id + "] not found");
        }

        customerDao.deleteById(id);
    }


    public void editCustomerById(Long id, CustomerEditRequest request) {
        var customer = customerDao.selectCustomerById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer with id [" + id + "] not found")
                );

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
