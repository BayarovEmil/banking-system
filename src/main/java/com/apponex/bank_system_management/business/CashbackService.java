package com.apponex.bank_system_management.business;

import com.apponex.bank_system_management.dataAccess.CashbackRepository;
import com.apponex.bank_system_management.dto.contribution.CashbackResponse;
import com.apponex.bank_system_management.dto.contribution.CategoryResponse;
import com.apponex.bank_system_management.entity.account.Account;
import com.apponex.bank_system_management.entity.contribution.Cashback;
import com.apponex.bank_system_management.mapper.contribution.CashbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CashbackService {
    private final CashbackRepository cashbackRepository;
    private final CashbackMapper cashbackMapper;
    public Cashback calculateCashback(BigDecimal amount, String categoryName) {
        var cashback = cashbackRepository.findByCategoriesCategoryName(categoryName)
                .orElseThrow(()->new IllegalArgumentException("Cannot find cashback category"));
        BigDecimal cashbackAmount = amount.multiply(cashback.getCategories().getPercent());
        cashback.setBalance(cashback.getBalance().subtract(cashbackAmount));
        cashback.setAmount(cashbackAmount);
        cashbackRepository.save(cashback);
        return cashback;
    }
}
