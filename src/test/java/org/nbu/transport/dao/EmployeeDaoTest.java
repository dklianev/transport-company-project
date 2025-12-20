package org.nbu.transport.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nbu.transport.entity.Employee;
import org.nbu.transport.entity.TransportCompany;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDaoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private List<String> validate(Employee employee) {
        return validator.validate(employee)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    @Test
    void whenValidEmployee_thenNoConstraintViolations() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Employee employee = new Employee("Ivan Petrov", "C - trucks", BigDecimal.valueOf(2000));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertTrue(messages.isEmpty());
    }

    @Test
    void whenEmployeeNameIsBlank_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Employee employee = new Employee("", "C - trucks", BigDecimal.valueOf(2000));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Employee name cannot be blank!"));
    }

    @Test
    void whenQualificationIsBlank_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Employee employee = new Employee("Ivan Petrov", "", BigDecimal.valueOf(2000));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Qualification cannot be blank!"));
    }

    @Test
    void whenSalaryIsNull_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Employee employee = new Employee("Ivan Petrov", "C - trucks", null);
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Salary cannot be null!"));
    }

    @Test
    void whenSalaryIsZero_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Employee employee = new Employee("Ivan Petrov", "C - trucks", BigDecimal.ZERO);
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Salary must be positive!"));
    }

    @Test
    void whenSalaryIsNegative_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Employee employee = new Employee("Ivan Petrov", "C - trucks", BigDecimal.valueOf(-500));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Salary must be positive!"));
    }

    @Test
    void whenCompanyIsNull_thenAssertConstraintViolation() {
        Employee employee = new Employee("Ivan Petrov", "C - trucks", BigDecimal.valueOf(2000));
        // company is not set (null)

        List<String> messages = validate(employee);

        assertEquals(1, messages.size());
        assertEquals("Company cannot be null!", messages.get(0));
    }

    @Test
    void whenEmployeeNameTooLong_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        String longName = "A".repeat(101); // 101 characters
        Employee employee = new Employee(longName, "C - trucks", BigDecimal.valueOf(2000));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertEquals(1, messages.size());
        assertEquals("Employee name must be up to 100 characters!", messages.get(0));
    }

    @Test
    void whenQualificationTooLong_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        String longQualification = "Q".repeat(101); // 101 characters
        Employee employee = new Employee("Ivan Petrov", longQualification, BigDecimal.valueOf(2000));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertEquals(1, messages.size());
        assertEquals("Qualification must be up to 100 characters!", messages.get(0));
    }
}
