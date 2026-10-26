package org.nova.bankaccount.service;

import org.nova.bankaccount.dto.request.BankAccountCreateRequest;
import org.nova.bankaccount.dto.response.BankAccountResponse;

public interface BankAccountService {

    BankAccountResponse createBankAccount(BankAccountCreateRequest request);

}
