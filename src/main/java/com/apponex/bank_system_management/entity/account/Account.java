package com.apponex.bank_system_management.entity.account;

import com.apponex.bank_system_management.core.common.BaseEntity;
import com.apponex.bank_system_management.core.security.model.Sms;
import com.apponex.bank_system_management.entity.customer.Customer;
import com.apponex.bank_system_management.entity.payment.TransactionHistory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "account")
public class Account extends BaseEntity {
    private BigDecimal balance;
    private boolean isActive;

    @Embedded
    private Card card;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "account")
    private List<TransactionHistory> transactions;

    @OneToMany(mappedBy = "account")
    private List<Sms> sms;
}
