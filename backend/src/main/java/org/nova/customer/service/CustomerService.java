package org.nova.customer.service;

import org.nova.customer.dto.request.CustomerRequest;
import org.nova.customer.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

}
