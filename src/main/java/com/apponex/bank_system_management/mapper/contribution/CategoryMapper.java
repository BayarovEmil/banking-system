package com.apponex.bank_system_management.mapper.contribution;

import com.apponex.bank_system_management.dto.contribution.CategoryRequest;
import com.apponex.bank_system_management.dto.contribution.CategoryResponse;
import com.apponex.bank_system_management.entity.contribution.Cashback;
import com.apponex.bank_system_management.entity.contribution.Category;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public Category toCategoryResponse(Cashback cashback) {
        return Category.builder()
                .categoryName(cashback.getCategories().getCategoryName())
                .percent(cashback.getCategories().getPercent())
                .build();
    }

    public Category toCategory(CategoryRequest request) {
        return Category.builder()
                .categoryName(request.categoryName())
                .percent(request.percent())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
               .categoryName(category.getCategoryName())
               .percent(category.getPercent())
               .build();
    }
}
