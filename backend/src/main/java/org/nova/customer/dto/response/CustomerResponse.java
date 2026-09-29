package org.nova.customer.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nova.customer.entity.CustomerStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse {

    private UUID id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private CustomerStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
