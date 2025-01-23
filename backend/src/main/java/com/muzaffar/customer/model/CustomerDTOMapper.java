package com.muzaffar.customer.model;


import com.muzaffar.customer.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Created on 21/01/25.
 */

@Service
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {

    @Override
    public CustomerDTO apply(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getGender(),
                customer.getAge(),
                customer.getAuthorities()
                        .stream()
                        .map(r -> r.getAuthority())
                        .toList(),
                customer.getUsername()
        );
    }
}
