package org.nbu.transport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    @NotBlank(message = "Employee name cannot be blank!")
    @Size(max = 100, message = "Employee name must be up to 100 characters!")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Qualification cannot be blank!")
    @Size(max = 100, message = "Qualification must be up to 100 characters!")
    @Column(name = "qualification", nullable = false)
    private String qualification;

    @NotNull(message = "Salary cannot be null!")
    @Positive(message = "Salary must be positive!")
    @DecimalMin(value = "0.01", message = "Salary must be at least 0.01!")
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @NotNull(message = "Company cannot be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private TransportCompany company;

    public Employee() {
    }

    public Employee(String name, String qualification, BigDecimal salary) {
        this.name = name;
        this.qualification = qualification;
        this.salary = salary;
    }

    public Employee(Long id, String name, String qualification, BigDecimal salary) {
        super(id);
        this.name = name;
        this.qualification = qualification;
        this.salary = salary;
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

    public TransportCompany getCompany() {
        return company;
    }

    public void setCompany(TransportCompany company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Qualification: %s, Salary: %.2f BGN)",
                name, getId(), qualification, salary);
    }
}
