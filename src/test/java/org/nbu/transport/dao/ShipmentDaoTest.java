package org.nbu.transport.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.nbu.transport.entity.Shipment;
import org.nbu.transport.entity.TransportCompany;
import org.nbu.transport.entity.Client;
import org.nbu.transport.entity.Vehicle;
import org.nbu.transport.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentDaoTest {

    private List<String> validate(Shipment shipment) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(shipment)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    private Shipment createValidShipment() {
        TransportCompany company = new TransportCompany("Transport Ltd", new BigDecimal("50000.00"));
        Client client = new Client("Client EOOD", "+359888123456", "client@mail.bg");
        Vehicle vehicle = new Vehicle("Truck", "CB1234AB", company);
        Employee driver = new Employee("Ivan Petrov", "C - trucks", new BigDecimal("2000.00"));
        driver.setCompany(company);

        Shipment shipment = new Shipment();
        shipment.setStartPoint("Sofia");
        shipment.setEndPoint("Plovdiv");
        shipment.setDepartureDate(LocalDateTime.now().plusDays(1));
        shipment.setArrivalDate(LocalDateTime.now().plusDays(2));
        shipment.setCargoType(Shipment.CargoType.GOODS);
        shipment.setPrice(new BigDecimal("500.00"));
        shipment.setCompany(company);
        shipment.setClient(client);
        shipment.setVehicle(vehicle);
        shipment.setDriver(driver);
        
        return shipment;
    }

    @Test
    void testShipmentEntityCreation() {
        Shipment shipment = createValidShipment();

        assertEquals("Sofia", shipment.getStartPoint());
        assertEquals("Plovdiv", shipment.getEndPoint());
        assertEquals(Shipment.CargoType.GOODS, shipment.getCargoType());
        assertEquals(new BigDecimal("500.00"), shipment.getPrice());
        
        List<String> messages = validate(shipment);
        assertTrue(messages.isEmpty(), "Valid shipment should have no validation errors");
    }

    @Test
    void testShipmentCargoTypeGoods() {
        Shipment shipment = createValidShipment();
        shipment.setCargoType(Shipment.CargoType.GOODS);
        shipment.setWeight(new BigDecimal("1500.00"));

        assertEquals(Shipment.CargoType.GOODS, shipment.getCargoType());
        assertEquals(new BigDecimal("1500.00"), shipment.getWeight());
        assertNull(shipment.getPassengerCount());
    }

    @Test
    void testShipmentCargoTypePassengers() {
        Shipment shipment = createValidShipment();
        shipment.setCargoType(Shipment.CargoType.PASSENGERS);
        shipment.setPassengerCount(35);

        assertEquals(Shipment.CargoType.PASSENGERS, shipment.getCargoType());
        assertEquals(35, shipment.getPassengerCount());
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
    void whenInvalidShipmentEmptyStartPoint_thenAssertConstraintViolations() {
        Shipment shipment = createValidShipment();
        shipment.setStartPoint("");

        List<String> messages = validate(shipment);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("blank")));
    }

    @Test
    void whenInvalidShipmentEmptyEndPoint_thenAssertConstraintViolations() {
        Shipment shipment = createValidShipment();
        shipment.setEndPoint("");

        List<String> messages = validate(shipment);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("blank")));
    }

    @Test
    void whenInvalidShipmentNegativePrice_thenAssertConstraintViolations() {
        Shipment shipment = createValidShipment();
        shipment.setPrice(new BigDecimal("-50.00"));

        List<String> messages = validate(shipment);

        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> m.contains("positive")));
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
