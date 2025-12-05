package org.nbu.transport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.nbu.transport.validator.ValidCompanyName;

import java.math.BigDecimal;

@Entity
@Table(name = "transport_company")
public class TransportCompany extends BaseEntity {

    @NotBlank(message = "Company name cannot be blank!")
    @Size(max = 100, message = "Company name must be up to 100 characters!")
    @Pattern(regexp = "^[A-Z].*", message = "Company name must start with a capital letter!")
    @ValidCompanyName(message = "Company name cannot contain reserved words!")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull(message = "Revenue cannot be null!")
    @PositiveOrZero(message = "Revenue must be positive or zero!")
    @Column(name = "revenue", nullable = false)
    private BigDecimal revenue;

    public TransportCompany() {
    }

    public TransportCompany(String name, BigDecimal revenue) {
        this.name = name;
        this.revenue = revenue;
    }

    public TransportCompany(Long id, String name, BigDecimal revenue) {
        super(id);
        this.name = name;
        this.revenue = revenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Revenue: %.2f BGN)", name, getId(), revenue);
    }
}
