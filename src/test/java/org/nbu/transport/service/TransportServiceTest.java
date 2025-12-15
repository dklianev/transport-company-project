package org.nbu.transport.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.nbu.transport.entity.Shipment;
import org.nbu.transport.entity.TransportCompany;
import org.nbu.transport.entity.Employee;
import org.nbu.transport.entity.Vehicle;
import org.nbu.transport.entity.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TransportService validation logic.
 * These tests verify entity creation and Jakarta Validation constraints.
 */
class TransportServiceTest {

    private <T> List<String> validate(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(entity)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    // ============ TransportCompany Tests ============

    @Test
    void testTransportCompanyCreation() {
        TransportCompany company = new TransportCompany("Valid Transport Ltd", new BigDecimal("50000.00"));

        assertEquals("Valid Transport Ltd", company.getName());
        assertEquals(new BigDecimal("50000.00"), company.getRevenue());
        assertNull(company.getId());
        
        List<String> messages = validate(company);
        assertTrue(messages.isEmpty(), "Valid company should have no validation errors");
    }

    @Test
    void testTransportCompanyWithZeroRevenue() {
        TransportCompany company = new TransportCompany("New Company", BigDecimal.ZERO);

        assertEquals("New Company", company.getName());
        assertEquals(BigDecimal.ZERO, company.getRevenue());
        
        List<String> messages = validate(company);
        assertTrue(messages.isEmpty());
    }

    @Test
    void whenInvalidCompanyName_thenAssertConstraintViolations() {
        // Name contains reserved word "Demo"
        TransportCompany company = new TransportCompany("Demo Transport", new BigDecimal("10000.00"));

        List<String> messages = validate(company);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("reserved word")));
    }

    // ============ Employee Tests ============

    @Test
    void testEmployeeCreation() {
        TransportCompany company = new TransportCompany("Transport Ltd", new BigDecimal("50000.00"));
        Employee employee = new Employee("Ivan Petrov", "ADR - Dangerous Goods", new BigDecimal("2200.00"));
        employee.setCompany(company);

        assertEquals("Ivan Petrov", employee.getName());
        assertEquals("ADR - Dangerous Goods", employee.getQualification());
        assertEquals(new BigDecimal("2200.00"), employee.getSalary());
        
        List<String> messages = validate(employee);
        assertTrue(messages.isEmpty(), "Valid employee should have no validation errors");
    }

    @Test
    void whenInvalidEmployeeEmptyName_thenAssertConstraintViolations() {
        TransportCompany company = new TransportCompany("Transport Ltd", new BigDecimal("50000.00"));
        Employee employee = new Employee("", "C - trucks", new BigDecimal("2000.00"));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("blank")));
    }

    @Test
    void whenInvalidEmployeeNegativeSalary_thenAssertConstraintViolations() {
        TransportCompany company = new TransportCompany("Transport Ltd", new BigDecimal("50000.00"));
        Employee employee = new Employee("Ivan Petrov", "C - trucks", new BigDecimal("-100.00"));
        employee.setCompany(company);

        List<String> messages = validate(employee);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("positive")));
    }

    // ============ Vehicle Tests ============

    @Test
    void testVehicleCreation() {
        TransportCompany company = new TransportCompany("Transport Ltd", new BigDecimal("50000.00"));
        Vehicle vehicle = new Vehicle("Truck", "CB1234AB", company);

        assertEquals("Truck", vehicle.getType());
        assertEquals("CB1234AB", vehicle.getRegistrationNumber());
        
        List<String> messages = validate(vehicle);
        assertTrue(messages.isEmpty(), "Valid vehicle should have no validation errors");
    }

    @Test
    void whenInvalidVehicleEmptyType_thenAssertConstraintViolations() {
        TransportCompany company = new TransportCompany("Transport Ltd", new BigDecimal("50000.00"));
        Vehicle vehicle = new Vehicle("", "CB1234AB", company);

        List<String> messages = validate(vehicle);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("blank")));
    }

    @Test
    void whenInvalidVehicleRegistrationInvalidChars_thenAssertConstraintViolations() {
        TransportCompany company = new TransportCompany("Transport Ltd", new BigDecimal("50000.00"));
        Vehicle vehicle = new Vehicle("Truck", "cb-1234", company);

        List<String> messages = validate(vehicle);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("uppercase") || m.contains("digits")));
    }

    // ============ Client Tests ============

    @Test
    void testClientCreation() {
        Client client = new Client("Tech Solutions EOOD", "+359888123456", "contact@tech.bg");

        assertEquals("Tech Solutions EOOD", client.getName());
        assertEquals("+359888123456", client.getPhone());
        assertEquals("contact@tech.bg", client.getEmail());
        
        List<String> messages = validate(client);
        assertTrue(messages.isEmpty(), "Valid client should have no validation errors");
    }

    @Test
    void whenInvalidClientEmptyName_thenAssertConstraintViolations() {
        Client client = new Client("", "+359888123456", "contact@tech.bg");

        List<String> messages = validate(client);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("blank")));
    }

    @Test
    void whenInvalidClientEmail_thenAssertConstraintViolations() {
        Client client = new Client("Tech Solutions", "+359888123456", "invalid-email");

        List<String> messages = validate(client);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("email")));
    }

    // ============ Shipment Tests ============

    @Test
    void testShipmentCargoTypeGoods() {
        Shipment shipment = new Shipment();
        shipment.setCargoType(Shipment.CargoType.GOODS);
        shipment.setWeight(new BigDecimal("1500.00"));
        shipment.setStartPoint("Sofia");
        shipment.setEndPoint("Plovdiv");
        shipment.setPrice(new BigDecimal("450.00"));

        assertEquals(Shipment.CargoType.GOODS, shipment.getCargoType());
        assertEquals(new BigDecimal("1500.00"), shipment.getWeight());
        assertNull(shipment.getPassengerCount());
    }

    @Test
    void testShipmentCargoTypePassengers() {
        Shipment shipment = new Shipment();
        shipment.setCargoType(Shipment.CargoType.PASSENGERS);
        shipment.setPassengerCount(35);
        shipment.setStartPoint("Varna");
        shipment.setEndPoint("Burgas");
        shipment.setPrice(new BigDecimal("350.00"));

        assertEquals(Shipment.CargoType.PASSENGERS, shipment.getCargoType());
        assertEquals(35, shipment.getPassengerCount());
        assertNull(shipment.getWeight());
    }

    @Test
    void testShipmentPaymentStatus() {
        Shipment shipment = new Shipment();
        shipment.setPaid(false);
        assertFalse(shipment.isPaid());

        shipment.setPaid(true);
        assertTrue(shipment.isPaid());
    }

    @Test
    void testShipmentDates() {
        LocalDateTime departure = LocalDateTime.of(2025, 12, 15, 8, 0);
        LocalDateTime arrival = LocalDateTime.of(2025, 12, 15, 12, 0);

        Shipment shipment = new Shipment();
        shipment.setDepartureDate(departure);
        shipment.setArrivalDate(arrival);

        assertEquals(departure, shipment.getDepartureDate());
        assertEquals(arrival, shipment.getArrivalDate());
        assertTrue(arrival.isAfter(departure));
    }

    @Test
    void testShipmentToStringFormat() {
        Shipment shipment = new Shipment();
        shipment.setStartPoint("Sofia");
        shipment.setEndPoint("Plovdiv");
        shipment.setCargoType(Shipment.CargoType.GOODS);
        shipment.setPrice(new BigDecimal("450.00"));
        shipment.setPaid(true);

        String result = shipment.toString();
        assertTrue(result.contains("Sofia"));
        assertTrue(result.contains("Plovdiv"));
        assertTrue(result.contains("PAID"));
    }
}
