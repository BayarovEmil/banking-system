package com.apponex.bank_system_management.entity.customer;

import com.apponex.bank_system_management.core.common.BaseEntity;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.security.model.role.Role;
import com.apponex.bank_system_management.core.security.model.token.Token;
import com.apponex.bank_system_management.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {
    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;



}
