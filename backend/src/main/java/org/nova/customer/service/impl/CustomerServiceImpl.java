package org.nova.customer.service.impl;

import org.nova.customer.dto.request.CustomerUpdateRequest;
import org.nova.customer.entity.CustomerStatus;
import org.nova.customer.exception.CustomerAccountClosedException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.nova.customer.entity.Customer;
import org.nova.customer.dto.request.CustomerCreateRequest;
import org.nova.customer.dto.response.CustomerResponse;
import org.nova.customer.exception.CustomerEmailAlreadyExistsException;
import org.nova.customer.exception.CustomerNotFoundException;
import org.nova.customer.exception.CustomerPhoneAlreadyExistsException;
import org.nova.customer.repository.CustomerRepository;
import org.nova.customer.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest request) {

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new CustomerEmailAlreadyExistsException(
                    "Customer email already exists: " + request.getEmail()
            );
        }

        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerPhoneAlreadyExistsException(
                    "Customer phone number already exists: " + request.getPhoneNumber()
            );
        }

        LocalDateTime now = LocalDateTime.now();

        Customer newCustomer = new Customer();
        newCustomer.setFullName(request.getFullName());
        newCustomer.setEmail(request.getEmail());
        newCustomer.setPhoneNumber(request.getPhoneNumber());
        newCustomer.setCreatedAt(now);
        newCustomer.setUpdatedAt(now);

        Customer savedCustomer = customerRepository.save(newCustomer);

        return toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found: " + id
                        )
                );

        return toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomers(CustomerStatus status, Pageable pageable) {
        if (status == null) {
            return customerRepository
                    .findAll(pageable)
                    .map(this::toResponse);
        }  else {
            return customerRepository
                    .findByStatus(status, pageable)
                    .map(this::toResponse);
        }
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(UUID id, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found: " + id
                        )
                );
        if (CustomerStatus.CLOSED == customer.getStatus()) {
            throw new CustomerAccountClosedException("Customer account closed");
        }
        if (customerRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new CustomerEmailAlreadyExistsException("Customer email already exists: " + request.getEmail());
        }
        if (customerRepository.existsByPhoneNumberAndIdNot(request.getPhoneNumber(), id)) {
            throw new CustomerPhoneAlreadyExistsException("Customer phone number already exists: " + request.getPhoneNumber());
        }

        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setUpdatedAt(LocalDateTime.now());

        return toResponse(customer);
    }

    private CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setStatus(customer.getStatus());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }

}
