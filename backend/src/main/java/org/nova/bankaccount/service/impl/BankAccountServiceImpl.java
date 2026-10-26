package org.nova.bankaccount.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nova.bankaccount.entity.BankAccount;
import org.nova.bankaccount.repository.BankAccountRepository;
import org.nova.bankaccount.entity.BankAccountStatus;
import org.nova.bankaccount.dto.request.BankAccountCreateRequest;
import org.nova.bankaccount.dto.response.BankAccountResponse;
import org.nova.bankaccount.service.BankAccountService;
import org.nova.customer.entity.Customer;
import org.nova.customer.entity.CustomerStatus;
import org.nova.customer.exception.CustomerAccountNotActiveException;
import org.nova.customer.exception.CustomerNotFoundException;
import org.nova.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public BankAccountResponse createBankAccount(BankAccountCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + request.getCustomerId() + " not found"));

        if (customer.getStatus() != CustomerStatus.ACTIVE) {
            throw new CustomerAccountNotActiveException("Customer with id " + request.getCustomerId() + " is not active for bank account");
        }
        LocalDateTime now = LocalDateTime.now();
        Long accountNumber = bankAccountRepository.getNextAccountNumber();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomer(customer);
        bankAccount.setAccountNumber(String.valueOf(accountNumber));
        bankAccount.setBalance(BigDecimal.ZERO);
        bankAccount.setCurrency("VND");
        bankAccount.setStatus(BankAccountStatus.ACTIVE);
        bankAccount.setCreatedAt(now);
        bankAccount.setUpdatedAt(now);
        bankAccountRepository.save(bankAccount);

        return toBankAccountResponse(bankAccount);
    }

    public BankAccountResponse toBankAccountResponse(BankAccount bankAccount) {
        BankAccountResponse response = new BankAccountResponse();
        response.setId(bankAccount.getId());
        response.setCustomerId(bankAccount.getCustomer().getId());
        response.setAccountNumber(bankAccount.getAccountNumber());
        response.setBalance(bankAccount.getBalance());
        response.setCurrency(bankAccount.getCurrency());
        response.setCreatedAt(bankAccount.getCreatedAt());
        response.setUpdatedAt(bankAccount.getUpdatedAt());
        response.setStatus(bankAccount.getStatus());
        return response;
    }
}
