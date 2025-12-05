package org.nbu.transport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "client")
public class Client extends BaseEntity {

    @NotBlank(message = "Client name cannot be blank!")
    @Size(max = 100, message = "Client name must be up to 100 characters!")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 20, message = "Phone number must be up to 20 characters!")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Phone number must contain only digits and optional + prefix!")
    @Column(name = "phone")
    private String phone;

    @Email(message = "Email must be a valid email address!")
    @Size(max = 100, message = "Email must be up to 100 characters!")
    @Column(name = "email")
    private String email;

    public Client() {
    }

    public Client(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Client(Long id, String name, String phone, String email) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Phone: %s)", name, getId(), phone);
    }
}
