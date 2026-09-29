package org.nova.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nova.customer.dto.request.CustomerRequest;
import org.nova.customer.dto.response.CustomerResponse;
import org.nova.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
