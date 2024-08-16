package com.apponex.bank_system_management.mapper.contribution;

import com.apponex.bank_system_management.dto.contribution.CashbackResponse;
import com.apponex.bank_system_management.entity.contribution.Cashback;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashbackMapper {
    private final CategoryMapper categoryMapper;
    public CashbackResponse toCashbackResponse(Cashback cashback) {
        return CashbackResponse.builder()
                .balance(cashback.getBalance())
                .amount(cashback.getAmount())
                .build();
    }
}
