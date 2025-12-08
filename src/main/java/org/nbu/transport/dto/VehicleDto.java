package org.nbu.transport.dto;

public class VehicleDto {
    private Long id;
    private String type;
    private String registrationNumber;
    private Long companyId;
    private String companyName;

    public VehicleDto(Long id, String type, String registrationNumber, 
                      Long companyId, String companyName) {
        this.id = id;
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "VehicleDto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}

