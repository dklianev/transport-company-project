package org.nbu.transport.dto;

import java.math.BigDecimal;

public class EmployeeCompanyDto {
    private Long employeeId;
    private String employeeName;
    private String qualification;
    private BigDecimal salary;
    private Long companyId;
    private String companyName;

    public EmployeeCompanyDto(Long employeeId, String employeeName, String qualification, 
                              BigDecimal salary, Long companyId, String companyName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.qualification = qualification;
        this.salary = salary;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "EmployeeCompanyDto{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", qualification='" + qualification + '\'' +
                ", salary=" + salary +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}

