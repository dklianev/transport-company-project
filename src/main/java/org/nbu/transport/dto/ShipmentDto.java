package org.nbu.transport.dto;

import org.nbu.transport.entity.Shipment.CargoType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShipmentDto {
    private Long id;
    private String startPoint;
    private String endPoint;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private CargoType cargoType;
    private BigDecimal price;
    private boolean paid;
    private String companyName;
    private String clientName;
    private String vehicleInfo;
    private String driverName;

    public ShipmentDto(Long id, String startPoint, String endPoint, 
                       LocalDateTime departureDate, LocalDateTime arrivalDate,
                       CargoType cargoType, BigDecimal price, boolean paid,
                       String companyName, String clientName, String vehicleInfo, String driverName) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.cargoType = cargoType;
        this.price = price;
        this.paid = paid;
        this.companyName = companyName;
        this.clientName = clientName;
        this.vehicleInfo = vehicleInfo;
        this.driverName = driverName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return "ShipmentDto{" +
                "id=" + id +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", cargoType=" + cargoType +
                ", price=" + price +
                ", paid=" + paid +
                ", companyName='" + companyName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", vehicleInfo='" + vehicleInfo + '\'' +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}

