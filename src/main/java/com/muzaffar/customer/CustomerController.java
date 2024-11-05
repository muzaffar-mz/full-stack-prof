package com.muzaffar.customer;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 30/10/24.
 */

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    //	@RequestMapping(path = "api/v2/customer", method = RequestMethod.GET)
    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomerById(id);
    }

    @PutMapping("/{id}")
    public void editCustomer(@PathVariable("id") Integer id, @RequestBody CustomerEditRequest request) {
        customerService.editCustomerById(id, request);
    }


}
