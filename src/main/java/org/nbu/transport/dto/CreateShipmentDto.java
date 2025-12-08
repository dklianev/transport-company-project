package org.nbu.transport.dto;

import org.nbu.transport.entity.Shipment.CargoType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateShipmentDto {
    private String startPoint;
    private String endPoint;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private CargoType cargoType;
    private BigDecimal weight;
    private Integer passengerCount;
    private BigDecimal price;
    private Long companyId;
    private Long clientId;
    private Long vehicleId;
    private Long driverId;

    public CreateShipmentDto(String startPoint, String endPoint, LocalDateTime departureDate,
                             LocalDateTime arrivalDate, CargoType cargoType, BigDecimal price,
                             Long companyId, Long clientId, Long vehicleId, Long driverId) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.cargoType = cargoType;
        this.price = price;
        this.companyId = companyId;
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "CreateShipmentDto{" +
                "startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", cargoType=" + cargoType +
                ", price=" + price +
                ", companyId=" + companyId +
                ", clientId=" + clientId +
                ", vehicleId=" + vehicleId +
                ", driverId=" + driverId +
                '}';
    }
}

