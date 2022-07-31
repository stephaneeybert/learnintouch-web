package com.thalasoft.learnintouch.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageWrapper<T> {

    public static final int DISPLAYED_PAGE_NUMBERS = 5;
    private Page<T> page;
    private List<PageItem> items;
    private int currentNumber;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageWrapper(Page<T> page, String url) {
        this.page = page;
        this.url = url;
        items = new ArrayList<PageItem>();

        // Start from 1 so as to match page.page
        currentNumber = page.getNumber() + 1; 

        int start, size;
        if (page.getTotalPages() <= DISPLAYED_PAGE_NUMBERS) {
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= DISPLAYED_PAGE_NUMBERS - DISPLAYED_PAGE_NUMBERS / 2) {
                start = 1;
                size = DISPLAYED_PAGE_NUMBERS;
            } else if (currentNumber >= page.getTotalPages() - DISPLAYED_PAGE_NUMBERS / 2) {
                start = page.getTotalPages() - DISPLAYED_PAGE_NUMBERS + 1;
                size = DISPLAYED_PAGE_NUMBERS;
            } else {
                start = currentNumber - DISPLAYED_PAGE_NUMBERS / 2;
                size = DISPLAYED_PAGE_NUMBERS;
            }
        }

        for (int i = 0; i < size; i++) {
            items.add(new PageItem(start + i, (start + i) == currentNumber));
        }
    }

    public List<PageItem> getItems() {
        return items;
    }

    public int getNumber() {
        return currentNumber;
    }

    public List<T> getContent() {
        return page.getContent();
    }

    public int getSize() {
        return page.getSize();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public boolean isFirstPage() {
        if (getTotalElements() == 0) {
            return true;
        } else {
            return page.isFirst();
        }
    }

    public boolean isLastPage() {
        return page.isLast();
    }

    public boolean isHasPreviousPage() {
        if (getTotalElements() == 0) {
            return false;
        } else {
            return page.hasPrevious();
        }
    }

    public boolean isHasNextPage() {
        return page.hasNext();
    }

    public class PageItem {

        private int number;
        private boolean isCurrentPage;

        public PageItem(int number, boolean isCurrentPage) {
            this.number = number;
            this.isCurrentPage = isCurrentPage;
        }

        public int getNumber() {
            return this.number;
        }

        public boolean isCurrentPage() {
            return this.isCurrentPage;
        }
        
    }

}
