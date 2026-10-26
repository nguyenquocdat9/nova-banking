package org.nova.bankaccount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nova.bankaccount.dto.request.BankAccountCreateRequest;
import org.nova.bankaccount.dto.response.BankAccountResponse;
import org.nova.bankaccount.entity.BankAccount;
import org.nova.bankaccount.entity.BankAccountStatus;
import org.nova.bankaccount.repository.BankAccountRepository;
import org.nova.bankaccount.service.impl.BankAccountServiceImpl;
import org.nova.customer.entity.Customer;
import org.nova.customer.entity.CustomerStatus;
import org.nova.customer.exception.CustomerAccountNotActiveException;
import org.nova.customer.exception.CustomerNotFoundException;
import org.nova.customer.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<BankAccount> bankAccountCaptor;

    @Test
    void createBankAccount_success() {
        BankAccountCreateRequest request = new BankAccountCreateRequest();
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setStatus(CustomerStatus.ACTIVE);
        UUID customerId = customer.getId();
        request.setCustomerId(customerId);
        when(bankAccountRepository.getNextAccountNumber()).thenReturn(100000000000L);
        when(customerRepository.findById(request.getCustomerId())).thenReturn(Optional.of(customer));
        BankAccountResponse response = bankAccountService.createBankAccount(request);
        verify(bankAccountRepository, times(1)).save(bankAccountCaptor.capture());

        BankAccount savedAccount = bankAccountCaptor.getValue();

        assertEquals(BigDecimal.ZERO, savedAccount.getBalance());
        assertEquals("VND", savedAccount.getCurrency());
        assertEquals(BankAccountStatus.ACTIVE, savedAccount.getStatus());
        assertEquals("100000000000", savedAccount.getAccountNumber());
        assertEquals(customerId, savedAccount.getCustomer().getId());

        assertNotNull(response);
        assertEquals(customerId, response.getCustomerId());
        assertEquals("100000000000", response.getAccountNumber());
        assertEquals(BankAccountStatus.ACTIVE, response.getStatus());
        assertEquals(BigDecimal.ZERO, response.getBalance());
    }

    @Test
    void createBankAccount_customerSuspended_throwsException() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setStatus(CustomerStatus.SUSPENDED);

        BankAccountCreateRequest request = new BankAccountCreateRequest();
        request.setCustomerId(customer.getId());

        when(customerRepository.findById(request.getCustomerId())).thenReturn(Optional.of(customer));
        assertThrows(CustomerAccountNotActiveException.class, () -> bankAccountService.createBankAccount(request));
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void createBankAccount_customerNotFound_throwsException() {
        BankAccountCreateRequest request = new BankAccountCreateRequest();
        UUID customerId = UUID.randomUUID();
        request.setCustomerId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> bankAccountService.createBankAccount(request));
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

}
