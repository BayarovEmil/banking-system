package com.apponex.bank_system_management.entity.contribution;

import com.apponex.bank_system_management.core.common.BaseEntity;
import com.apponex.bank_system_management.entity.payment.TransactionHistory;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Cashback extends BaseEntity {
    private BigDecimal balance;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Category categories;

    @OneToOne(mappedBy = "cashback")
    private TransactionHistory transactionHistory;
}
