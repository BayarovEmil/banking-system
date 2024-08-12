package com.apponex.bank_system_management.mapper.account;

import com.apponex.bank_system_management.core.util.CardGenerator;
import com.apponex.bank_system_management.dto.account.CardResponse;
import com.apponex.bank_system_management.entity.account.Account;
import com.apponex.bank_system_management.entity.account.Card;
import com.apponex.bank_system_management.entity.account.CardStatus;
import com.apponex.bank_system_management.entity.account.CardType;
import org.springframework.stereotype.Service;

import static com.apponex.bank_system_management.core.util.CardGenerator.*;

@Service
public class CardMapper {

    public Card generateCard(CardType cardType) {
        return Card.builder()
                .cardType(cardType)
                .cardNumber(generateCardNumber())
                .cvv(generateCvv())
                .expirationDate(generateExpiryDate())
                .cardStatus(CardStatus.ACTIVE)
                .build();
    }

    public CardResponse toCardResponse(Account account) {
        return CardResponse.builder()
                .cardNumber(account.getCard().getCardNumber())
                .cardStatus(account.getCard().getCardStatus())
                .cardType(account.getCard().getCardType())
                .build();
    }
}
