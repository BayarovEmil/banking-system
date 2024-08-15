package com.apponex.bank_system_management.core.security.model;

import com.apponex.bank_system_management.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phoneNumber;
    private String verificationCode;
    private LocalDateTime sentAt;
    private boolean confirmed;
    private boolean expired;
    private BigDecimal sendAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
}
