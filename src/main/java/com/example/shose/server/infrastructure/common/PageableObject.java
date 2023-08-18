package com.example.shose.server.infrastructure.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Nguyễn Vinh
 */

@Getter
public class PageableObject<T> {

    private List<T> data;
    private long totalPages;
    private int currentPage;
    public PageableObject(Page<T> page) {
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
    }
}
