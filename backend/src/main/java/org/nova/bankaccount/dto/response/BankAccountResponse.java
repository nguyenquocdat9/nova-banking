package org.nova.bankaccount.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nova.bankaccount.entity.BankAccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountResponse {

    private UUID id;

    private String accountNumber;

    private UUID customerId;

    private BigDecimal balance;

    private String currency;

    private BankAccountStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
