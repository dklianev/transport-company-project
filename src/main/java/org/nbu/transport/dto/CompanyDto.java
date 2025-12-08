package org.nbu.transport.dto;

import java.math.BigDecimal;

public class CompanyDto {
    private Long id;
    private String name;
    private BigDecimal revenue;

    public CompanyDto(Long id, String name, BigDecimal revenue) {
        this.id = id;
        this.name = name;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}

