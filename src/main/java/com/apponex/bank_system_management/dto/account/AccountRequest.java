package com.apponex.bank_system_management.dto.account;

import com.apponex.bank_system_management.entity.account.CardType;

public record AccountRequest(
    CardType cardType
) {
}
