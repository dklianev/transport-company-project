package org.nbu.transport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
public class Shipment extends BaseEntity implements Serializable {

    @NotBlank(message = "Start point cannot be blank!")
    @Size(max = 100, message = "Start point must be up to 100 characters!")
    @Column(name = "start_point", nullable = false)
    private String startPoint;

    @NotBlank(message = "End point cannot be blank!")
    @Size(max = 100, message = "End point must be up to 100 characters!")
    @Column(name = "end_point", nullable = false)
    private String endPoint;

    @NotNull(message = "Departure date cannot be null!")
    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @NotNull(message = "Arrival date cannot be null!")
    @FutureOrPresent(message = "Arrival date must be in the present or future!")
    @Column(name = "arrival_date", nullable = false)
    private LocalDateTime arrivalDate;

    @NotNull(message = "Cargo type cannot be null!")
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_type", nullable = false)
    private CargoType cargoType;

    @PositiveOrZero(message = "Weight must be positive or zero!")
    @Column(name = "weight")
    private BigDecimal weight;

    @PositiveOrZero(message = "Passenger count must be positive or zero!")
    @Column(name = "passenger_count")
    private Integer passengerCount;

    @NotNull(message = "Price cannot be null!")
    @Positive(message = "Price must be positive!")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "paid", nullable = false)
    private boolean paid = false;

    @NotNull(message = "Company cannot be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private TransportCompany company;

    @NotNull(message = "Client cannot be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NotNull(message = "Vehicle cannot be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull(message = "Driver cannot be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Employee driver;

    public enum CargoType {
        GOODS, PASSENGERS
    }

    public Shipment() {
    }

    public Shipment(String startPoint, String endPoint, LocalDateTime departureDate,
                   LocalDateTime arrivalDate, CargoType cargoType, BigDecimal price,
                   TransportCompany company, Client client, Vehicle vehicle, Employee driver) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.cargoType = cargoType;
        this.price = price;
        this.company = company;
        this.client = client;
        this.vehicle = vehicle;
        this.driver = driver;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public TransportCompany getCompany() {
        return company;
    }

    public void setCompany(TransportCompany company) {
        this.company = company;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return String.format("Shipment #%d: %s -> %s (%s, %.2f BGN, %s)",
                getId(), startPoint, endPoint, cargoType, price, paid ? "PAID" : "UNPAID");
    }
}
