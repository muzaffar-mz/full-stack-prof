package com.muzaffar.customer.controller;


import com.muzaffar.customer.model.CustomerDTO;
import com.muzaffar.customer.model.CustomerEditRequest;
import com.muzaffar.customer.model.CustomerRegistrationRequest;
import com.muzaffar.customer.service.CustomerService;
import com.muzaffar.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    private final JWTUtil jwtUtil;

    //	@RequestMapping(path = "api/v2/customer", method = RequestMethod.GET)
    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken).build();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomerById(id);
    }

    @PutMapping("/{id}")
    public void editCustomer(@PathVariable("id") Long id, @RequestBody CustomerEditRequest request) {
        customerService.editCustomerById(id, request);
    }


}
