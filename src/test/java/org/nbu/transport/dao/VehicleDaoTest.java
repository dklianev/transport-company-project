package org.nbu.transport.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nbu.transport.entity.TransportCompany;
import org.nbu.transport.entity.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDaoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private List<String> validate(Vehicle vehicle) {
        return validator.validate(vehicle)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    @Test
    void whenValidVehicle_thenNoConstraintViolations() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Vehicle vehicle = new Vehicle("Truck", "CB1234AB", company);

        List<String> messages = validate(vehicle);

        assertTrue(messages.isEmpty());
    }

    @Test
    void whenVehicleTypeIsBlank_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Vehicle vehicle = new Vehicle("", "CB1234AB", company);

        List<String> messages = validate(vehicle);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Vehicle type cannot be blank!"));
    }

    @Test
    void whenVehicleTypeTooLong_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        String longType = "T".repeat(51); // 51 characters
        Vehicle vehicle = new Vehicle(longType, "CB1234AB", company);

        List<String> messages = validate(vehicle);

        assertEquals(1, messages.size());
        assertEquals("Vehicle type must be up to 50 characters!", messages.get(0));
    }

    @Test
    void whenRegistrationNumberIsBlank_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Vehicle vehicle = new Vehicle("Truck", "", company);

        List<String> messages = validate(vehicle);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Registration number cannot be blank!"));
    }

    @Test
    void whenRegistrationNumberContainsLowercase_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Vehicle vehicle = new Vehicle("Truck", "cb1234ab", company);

        List<String> messages = validate(vehicle);

        assertEquals(1, messages.size());
        assertEquals("Registration number must contain only uppercase letters and digits!", messages.get(0));
    }

    @Test
    void whenRegistrationNumberContainsSpecialCharacters_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Vehicle vehicle = new Vehicle("Truck", "CB-1234-AB", company);

        List<String> messages = validate(vehicle);

        assertEquals(1, messages.size());
        assertEquals("Registration number must contain only uppercase letters and digits!", messages.get(0));
    }

    @Test
    void whenRegistrationNumberTooLong_thenAssertConstraintViolation() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        String longRegNumber = "A".repeat(21); // 21 characters
        Vehicle vehicle = new Vehicle("Truck", longRegNumber, company);

        List<String> messages = validate(vehicle);

        assertEquals(1, messages.size());
        assertEquals("Registration number must be up to 20 characters!", messages.get(0));
    }

    @Test
    void whenCompanyIsNull_thenAssertConstraintViolation() {
        Vehicle vehicle = new Vehicle("Truck", "CB1234AB", null);

        List<String> messages = validate(vehicle);

        assertEquals(1, messages.size());
        assertEquals("Company cannot be null!", messages.get(0));
    }

    @Test
    void whenRegistrationNumberValidUppercaseAndDigits_thenNoConstraintViolations() {
        TransportCompany company = new TransportCompany("ValidCompany", BigDecimal.valueOf(10000));
        Vehicle vehicle = new Vehicle("Truck", "CB9999XX", company);

        List<String> messages = validate(vehicle);

        assertTrue(messages.isEmpty());
    }
}
