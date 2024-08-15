package com.apponex.bank_system_management.core.common;

import com.apponex.bank_system_management.dto.transaction.TransactionResponse;
import lombok.*;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;

}
