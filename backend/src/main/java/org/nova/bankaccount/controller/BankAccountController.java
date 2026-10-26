package org.nova.bankaccount.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nova.bankaccount.dto.request.BankAccountCreateRequest;
import org.nova.bankaccount.dto.response.BankAccountResponse;
import org.nova.bankaccount.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping
    ResponseEntity<BankAccountResponse> createBankAccount(
            @Valid @RequestBody BankAccountCreateRequest request
    ) {
        BankAccountResponse response = bankAccountService.createBankAccount(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
