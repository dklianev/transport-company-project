package org.nbu.transport.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nbu.transport.entity.Client;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ClientDaoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private List<String> validate(Client client) {
        return validator.validate(client)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    @Test
    void whenValidClient_thenNoConstraintViolations() {
        Client client = new Client("Georgi Ivanov", "+359888123456", "georgi@example.bg");

        List<String> messages = validate(client);

        assertTrue(messages.isEmpty());
    }

    @Test
    void whenClientNameIsBlank_thenAssertConstraintViolation() {
        Client client = new Client("", "+359888123456", "georgi@example.bg");

        List<String> messages = validate(client);

        assertFalse(messages.isEmpty());
        assertTrue(messages.contains("Client name cannot be blank!"));
    }

    @Test
    void whenClientNameTooLong_thenAssertConstraintViolation() {
        String longName = "A".repeat(101); // 101 characters
        Client client = new Client(longName, "+359888123456", "georgi@example.bg");

        List<String> messages = validate(client);

        assertEquals(1, messages.size());
        assertEquals("Client name must be up to 100 characters!", messages.get(0));
    }

    @Test
    void whenPhoneNumberContainsInvalidCharacters_thenAssertConstraintViolation() {
        Client client = new Client("Georgi Ivanov", "abc123", "georgi@example.bg");

        List<String> messages = validate(client);

        assertEquals(1, messages.size());
        assertEquals("Phone number must contain only digits and optional + prefix!", messages.get(0));
    }

    @Test
    void whenPhoneNumberWithPlusPrefix_thenNoConstraintViolations() {
        Client client = new Client("Georgi Ivanov", "+359888123456", "georgi@example.bg");

        List<String> messages = validate(client);

        assertTrue(messages.isEmpty());
    }

    @Test
    void whenPhoneNumberOnlyDigits_thenNoConstraintViolations() {
        Client client = new Client("Georgi Ivanov", "0888123456", "georgi@example.bg");

        List<String> messages = validate(client);

        assertTrue(messages.isEmpty());
    }

    @Test
    void whenPhoneNumberTooLong_thenAssertConstraintViolation() {
        String longPhone = "0".repeat(21); // 21 characters
        Client client = new Client("Georgi Ivanov", longPhone, "georgi@example.bg");

        List<String> messages = validate(client);

        assertEquals(1, messages.size());
        assertEquals("Phone number must be up to 20 characters!", messages.get(0));
    }

    @Test
    void whenEmailIsInvalid_thenAssertConstraintViolation() {
        Client client = new Client("Georgi Ivanov", "+359888123456", "invalid-email");

        List<String> messages = validate(client);

        assertEquals(1, messages.size());
        assertEquals("Email must be a valid email address!", messages.get(0));
    }

    @Test
    void whenEmailTooLong_thenAssertConstraintViolation() {
        // Create a valid-format email that exceeds 100 characters
        String longEmail = "valid" + "a".repeat(85) + "@example.bg"; // >100 characters
        Client client = new Client("Georgi Ivanov", "+359888123456", longEmail);

        List<String> messages = validate(client);

        assertFalse(messages.isEmpty());
        // May have multiple violations if email format also fails
    }

    @Test
    void whenPhoneAndEmailAreNull_thenNoConstraintViolations() {
        Client client = new Client("Georgi Ivanov", null, null);

        List<String> messages = validate(client);

        assertTrue(messages.isEmpty());
    }
}
