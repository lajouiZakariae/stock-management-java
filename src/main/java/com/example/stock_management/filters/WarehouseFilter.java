package com.example.stock_management.filters;

import javax.validation.constraints.Size;

public class WarehouseFilter {

    private String name = null;

    private Integer page;

    private Integer perPage;

    public WarehouseFilter(String name, Integer page, Integer perPage) {
        this.name = name;
        this.page = page;
        this.perPage = perPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }
}
