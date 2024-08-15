package com.apponex.bank_system_management.entity.contribution;

import com.apponex.bank_system_management.core.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Cashback extends BaseEntity {
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Category categories;
}
