package com.apponex.bank_system_management.business;

import com.apponex.bank_system_management.dataAccess.CashbackRepository;
import com.apponex.bank_system_management.dataAccess.CategoryRepository;
import com.apponex.bank_system_management.dto.contribution.CategoryRequest;
import com.apponex.bank_system_management.dto.contribution.CategoryResponse;
import com.apponex.bank_system_management.dto.contribution.UpdateCategoryRequest;
import com.apponex.bank_system_management.entity.contribution.Cashback;
import com.apponex.bank_system_management.mapper.contribution.CashbackMapper;
import com.apponex.bank_system_management.mapper.contribution.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CashbackRepository cashbackRepository;
    private final CashbackMapper cashbackMapper;

    public CategoryResponse createCategory(CategoryRequest categoryName) {
        var category = categoryRepository.save(categoryMapper.toCategory(categoryName));
        cashbackRepository.save(
                Cashback.builder()
                        .categories(category)
                        .build()
        );
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse readCategory() {
        return categoryMapper.toCategoryResponse(categoryRepository.findAll().get(0));
    }

    public CategoryResponse updateCategory(UpdateCategoryRequest request, Integer categoryId) {
        var category = categoryRepository.findById(categoryId)
               .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setCategoryName(request.categoryName());
        category.setPercent(request.percentage());
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }
}
