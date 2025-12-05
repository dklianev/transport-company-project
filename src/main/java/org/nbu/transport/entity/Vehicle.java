package org.nbu.transport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {

    @NotBlank(message = "Vehicle type cannot be blank!")
    @Size(max = 50, message = "Vehicle type must be up to 50 characters!")
    @Column(name = "type", nullable = false)
    private String type;

    @NotBlank(message = "Registration number cannot be blank!")
    @Size(max = 20, message = "Registration number must be up to 20 characters!")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Registration number must contain only uppercase letters and digits!")
    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @NotNull(message = "Company cannot be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private TransportCompany company;

    public Vehicle() {
    }

    public Vehicle(String type, String registrationNumber) {
        this.type = type;
        this.registrationNumber = registrationNumber;
    }

    public Vehicle(String type, String registrationNumber, TransportCompany company) {
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.company = company;
    }

    public Vehicle(Long id, String type, String registrationNumber, TransportCompany company) {
        super(id);
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public TransportCompany getCompany() {
        return company;
    }

    public void setCompany(TransportCompany company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return String.format("%s %s (ID: %d)", type, registrationNumber, getId());
    }
}
