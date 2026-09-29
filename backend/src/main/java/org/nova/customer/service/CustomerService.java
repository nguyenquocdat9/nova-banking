package org.nova.customer.service;

import org.nova.customer.dto.request.CustomerRequest;
import org.nova.customer.dto.response.CustomerResponse;

import java.util.UUID;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(UUID id);

}
