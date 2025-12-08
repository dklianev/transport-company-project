package org.nbu.transport.dto;

import java.math.BigDecimal;

public class CreateEmployeeDto {
    private String name;
    private String qualification;
    private BigDecimal salary;
    private Long companyId;

    public CreateEmployeeDto(String name, String qualification, BigDecimal salary, Long companyId) {
        this.name = name;
        this.qualification = qualification;
        this.salary = salary;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CreateEmployeeDto{" +
                "name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", salary=" + salary +
                ", companyId=" + companyId +
                '}';
    }
}

