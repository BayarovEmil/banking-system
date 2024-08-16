package com.apponex.bank_system_management.entity.payment;

import com.apponex.bank_system_management.core.common.BaseEntity;
import com.apponex.bank_system_management.entity.account.Account;
import com.apponex.bank_system_management.entity.contribution.Cashback;
import jakarta.persistence.*;
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
@Table(name = "transaction_history")
public class TransactionHistory extends BaseEntity {
    private BigDecimal amount;
    private boolean success;
    private boolean ownAccount;
    private String description;
    private BigDecimal commission;

    private TransactionStatus transactionStatus;
    private TransactionType transactionType;

    private String amountDeletedCardNumber;
    private String amountReceiverCardNumber;
    private String receiverUsername;

    @OneToOne
    @JoinColumn(name = "transaction_history_id")
    private Cashback cashback;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
