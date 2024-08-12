package com.apponex.bank_system_management.entity.account;

import com.apponex.bank_system_management.core.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Card {
    @Column(unique = true, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private String cvv;

    private CardStatus cardStatus;
}
