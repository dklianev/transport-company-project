package org.nbu.transport.dto;

public class CreateVehicleDto {
    private String type;
    private String registrationNumber;
    private Long companyId;

    public CreateVehicleDto(String type, String registrationNumber, Long companyId) {
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.companyId = companyId;
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

    @Override
    public String toString() {
        return "CreateVehicleDto{" +
                "type='" + type + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}

