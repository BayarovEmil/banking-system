package com.apponex.bank_system_management.entity.contribution;

import com.apponex.bank_system_management.core.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Category extends BaseEntity {
    private String categoryName;
    private double percent;

    @OneToMany(mappedBy = "category")
    private List<Cashback> cashback;
}
