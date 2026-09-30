package org.nova.customer.service;

import org.nova.customer.dto.request.CustomerCreateRequest;
import org.nova.customer.dto.request.CustomerUpdateRequest;
import org.nova.customer.dto.response.CustomerResponse;
import org.nova.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerCreateRequest request);

    CustomerResponse getCustomerById(UUID id);

    Page<CustomerResponse> getCustomers(CustomerStatus status, Pageable pageable);

    CustomerResponse updateCustomer(UUID id, CustomerUpdateRequest request);

}
