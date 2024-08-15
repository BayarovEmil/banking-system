package com.apponex.bank_system_management.dto.account;

import com.apponex.bank_system_management.entity.account.CardStatus;
import com.apponex.bank_system_management.entity.account.CardType;
import lombok.Builder;

@Builder
public record CardResponse(
        String cardNumber,
        CardType cardType,
        CardStatus cardStatus
) {
}
