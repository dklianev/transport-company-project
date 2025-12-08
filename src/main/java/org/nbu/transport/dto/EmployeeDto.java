package org.nbu.transport.dto;

import java.math.BigDecimal;

public class EmployeeDto {
    private Long id;
    private String name;
    private String qualification;
    private BigDecimal salary;

    public EmployeeDto(Long id, String name, String qualification, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.salary = salary;
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

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", salary=" + salary +
                '}';
    }
}

