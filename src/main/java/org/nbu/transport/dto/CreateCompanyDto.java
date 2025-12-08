package org.nbu.transport.dto;

import java.math.BigDecimal;

public class CreateCompanyDto {
    private String name;
    private BigDecimal revenue;

    public CreateCompanyDto(String name, BigDecimal revenue) {
        this.name = name;
        this.revenue = revenue;
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
        return "CreateCompanyDto{" +
                "name='" + name + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}

