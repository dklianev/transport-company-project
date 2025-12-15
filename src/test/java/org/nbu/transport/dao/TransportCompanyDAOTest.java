package org.nbu.transport.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.nbu.transport.entity.TransportCompany;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TransportCompanyDAOTest {

    private List<String> validate(TransportCompany company) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(company)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    @Test
    void testCompanyEntityCreation() {
        // Test real entity creation with valid data
        TransportCompany company = new TransportCompany("Valid Transport Ltd", new BigDecimal("100000.00"));

        assertEquals("Valid Transport Ltd", company.getName());
        assertEquals(new BigDecimal("100000.00"), company.getRevenue());
        assertNull(company.getId()); // ID is null before persistence
    }

    @Test
    void testCompanyWithZeroRevenue() {
        // Company can have zero revenue initially
        TransportCompany company = new TransportCompany("New Company", BigDecimal.ZERO);

        assertEquals("New Company", company.getName());
        assertEquals(BigDecimal.ZERO, company.getRevenue());
        
        List<String> messages = validate(company);
        assertTrue(messages.isEmpty(), "Valid company should have no validation errors");
    }

    @Test
    void whenInvalidCompanyNameEmpty_thenAssertConstraintViolations() {
        TransportCompany company = new TransportCompany("", new BigDecimal("10000.00"));

        List<String> messages = validate(company);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("cannot be empty") || m.contains("blank")));
    }

    @Test
    void whenInvalidCompanyNameTooLong_thenAssertConstraintViolations() {
        // Name longer than 100 characters
        String longName = "A".repeat(101);
        TransportCompany company = new TransportCompany(longName, new BigDecimal("10000.00"));

        List<String> messages = validate(company);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("100 characters")));
    }

    @Test
    void whenInvalidCompanyNameContainsReservedWord_thenAssertConstraintViolations() {
        // Name contains reserved word "Test"
        TransportCompany company = new TransportCompany("Test Company", new BigDecimal("10000.00"));

        List<String> messages = validate(company);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("reserved word")));
    }

    @Test
    void whenNegativeRevenue_thenAssertConstraintViolations() {
        TransportCompany company = new TransportCompany("Valid Company", new BigDecimal("-100.00"));

        List<String> messages = validate(company);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("positive") || m.contains("zero")));
    }

    @Test
    void testCompanyToStringFormat() {
        TransportCompany company = new TransportCompany("Format Company", new BigDecimal("25000.00"));

        String result = company.toString();

        assertTrue(result.contains("Format Company"));
        assertTrue(result.contains("25000"));
    }
}
