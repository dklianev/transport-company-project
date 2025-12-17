package org.nbu.transport.service;

import org.nbu.transport.dao.*;
import org.nbu.transport.dto.*;
import org.nbu.transport.entity.*;
import org.nbu.transport.exception.ValidationException;
import org.nbu.transport.util.FileUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportService {

    // --- Entity-based methods ---

    public static void createCompany(String name, BigDecimal revenue) throws ValidationException {
        validateNotEmpty(name, "Company name");
        validatePositiveOrZero(revenue, "Revenue");

        TransportCompany company = new TransportCompany(name, revenue);
        TransportCompanyDao.createCompany(company);
    }

    // --- DTO-based methods ---

    public static void createCompanyDto(CreateCompanyDto dto) throws ValidationException {
        validateNotEmpty(dto.getName(), "Company name");
        validatePositiveOrZero(dto.getRevenue(), "Revenue");
        TransportCompanyDao.createCompanyDto(dto);
    }

    public static void createEmployeeDto(CreateEmployeeDto dto) throws ValidationException {
        validateNotEmpty(dto.getName(), "Employee name");
        validateNotEmpty(dto.getQualification(), "Qualification");
        validatePositive(dto.getSalary(), "Salary");
        EmployeeDao.createEmployeeDto(dto);
    }

    public static void createClientDto(CreateClientDto dto) throws ValidationException {
        validateNotEmpty(dto.getName(), "Client name");
        ClientDao.createClientDto(dto);
    }

    public static void createVehicleDto(CreateVehicleDto dto) throws ValidationException {
        validateNotEmpty(dto.getType(), "Vehicle type");
        validateNotEmpty(dto.getRegistrationNumber(), "Registration number");
        VehicleDao.createVehicleDto(dto);
    }

    public static void createShipmentDto(CreateShipmentDto dto) throws ValidationException {
        validateNotEmpty(dto.getStartPoint(), "Start point");
        validateNotEmpty(dto.getEndPoint(), "End point");
        validateNotNull(dto.getDepartureDate(), "Departure date");
        validateNotNull(dto.getArrivalDate(), "Arrival date");
        validateNotNull(dto.getCargoType(), "Cargo type");
        validatePositive(dto.getPrice(), "Price");

        if (dto.getArrivalDate().isBefore(dto.getDepartureDate())) {
            throw new ValidationException("Arrival date must be after departure date");
        }
        ShipmentDao.createShipmentDto(dto);
    }

    public static List<CompanyDto> getAllCompaniesDto() {
        return TransportCompanyDao.getAllCompaniesDto();
    }

    public static CompanyDto getCompanyDto(Long id) {
        return TransportCompanyDao.getCompanyDto(id);
    }

    public static List<EmployeeDto> getAllEmployeesDto() {
        return EmployeeDao.getAllEmployeesDto();
    }

    public static List<EmployeeCompanyDto> getEmployeesWithCompanyDto(Long companyId) {
        return EmployeeDao.getEmployeesWithCompanyDto(companyId);
    }

    public static List<ClientDto> getAllClientsDto() {
        return ClientDao.getAllClientsDto();
    }

    public static List<VehicleDto> getAllVehiclesDto() {
        return VehicleDao.getAllVehiclesDto();
    }

    public static List<ShipmentDto> getAllShipmentsDto() {
        return ShipmentDao.getAllShipmentsDto();
    }

    public static void createEmployee(String name, String qualification, BigDecimal salary, Long companyId)
            throws ValidationException {
        validateNotEmpty(name, "Employee name");
        validateNotEmpty(qualification, "Qualification");
        validatePositive(salary, "Salary");

        TransportCompany company = TransportCompanyDao.getCompany(companyId);
        if (company == null) {
            throw new ValidationException("Company with ID " + companyId + " not found");
        }

        Employee employee = new Employee(name, qualification, salary);
        employee.setCompany(company);
        EmployeeDao.createEmployee(employee);
    }

    public static void createVehicle(String type, String registrationNumber, Long companyId)
            throws ValidationException {
        validateNotEmpty(type, "Vehicle type");
        validateNotEmpty(registrationNumber, "Registration number");

        TransportCompany company = TransportCompanyDao.getCompany(companyId);
        if (company == null) {
            throw new ValidationException("Company with ID " + companyId + " not found");
        }

        Vehicle vehicle = new Vehicle(type, registrationNumber, company);
        VehicleDao.createVehicle(vehicle);
    }

    public static void createClient(String name, String phone, String email) throws ValidationException {
        validateNotEmpty(name, "Client name");

        Client client = new Client(name, phone, email);
        ClientDao.createClient(client);
    }

    public static void createShipment(String startPoint, String endPoint, LocalDateTime departureDate,
                                     LocalDateTime arrivalDate, Shipment.CargoType cargoType, BigDecimal weight,
                                     Integer passengerCount, BigDecimal price, Long companyId,
                                     Long clientId, Long vehicleId, Long driverId)
            throws ValidationException {
        validateNotEmpty(startPoint, "Start point");
        validateNotEmpty(endPoint, "End point");
        validateNotNull(departureDate, "Departure date");
        validateNotNull(arrivalDate, "Arrival date");
        validateNotNull(cargoType, "Cargo type");
        validatePositive(price, "Price");

        if (arrivalDate.isBefore(departureDate)) {
            throw new ValidationException("Arrival date must be after departure date");
        }

        if (cargoType == Shipment.CargoType.GOODS && weight == null) {
            throw new ValidationException("Weight is required for goods shipment");
        }

        if (cargoType == Shipment.CargoType.PASSENGERS && passengerCount == null) {
            throw new ValidationException("Passenger count is required for passenger shipment");
        }

        TransportCompany company = TransportCompanyDao.getCompany(companyId);
        Client client = ClientDao.getClient(clientId);
        Vehicle vehicle = VehicleDao.getVehicle(vehicleId);
        Employee driver = EmployeeDao.getEmployee(driverId);

        if (company == null || client == null || vehicle == null || driver == null) {
            throw new ValidationException("One or more referenced entities not found");
        }

        Shipment shipment = new Shipment(startPoint, endPoint, departureDate, arrivalDate,
                                         cargoType, price, company, client, vehicle, driver);
        shipment.setWeight(weight);
        shipment.setPassengerCount(passengerCount);
        ShipmentDao.createShipment(shipment);
    }

    public static List<TransportCompany> getCompaniesSortedByName() {
        return TransportCompanyDao.getCompaniesSortedByName();
    }

    public static List<TransportCompany> getCompaniesSortedByRevenue() {
        return TransportCompanyDao.getCompaniesSortedByRevenue();
    }

    public static List<Employee> getEmployeesSortedByQualification() {
        return EmployeeDao.getEmployeesSortedByQualification();
    }

    public static List<Employee> getEmployeesSortedBySalary() {
        return EmployeeDao.getEmployeesSortedBySalary();
    }

    public static List<Employee> getEmployeesByQualification(String qualification) {
        return EmployeeDao.getEmployeesByQualification(qualification);
    }

    public static List<Shipment> getShipmentsSortedByDestination() {
        return ShipmentDao.getShipmentsSortedByDestination();
    }

    public static List<Shipment> getShipmentsByClient(Long clientId) {
        return ShipmentDao.getShipmentsByClient(clientId);
    }

    public static List<Shipment> getUnpaidShipments() {
        return ShipmentDao.getUnpaidShipments();
    }

    public static List<Shipment> getPaidShipments() {
        return ShipmentDao.getPaidShipments();
    }

    public static void markShipmentAsPaid(Long shipmentId) {
        ShipmentDao.markShipmentAsPaid(shipmentId);
    }

    public static void exportShipmentsToFile(List<Shipment> shipments, String filename) {
        FileUtil.exportShipments(shipments, filename);
    }

    public static List<Shipment> importShipmentsFromFile(String filename) {
        return FileUtil.importShipments(filename);
    }

    public static Long getTotalShipmentsCount() {
        return ShipmentDao.getTotalShipmentsCount();
    }

    public static BigDecimal getTotalRevenue() {
        return ShipmentDao.getTotalRevenue();
    }

    public static Map<Long, Integer> getShipmentsCountPerDriver() {
        List<Object[]> results = ShipmentDao.getShipmentsCountPerDriver();
        Map<Long, Integer> map = new HashMap<>();
        for (Object[] row : results) {
            map.put((Long) row[0], ((Long) row[1]).intValue());
        }
        return map;
    }

    public static Map<Long, BigDecimal> getRevenuePerDriver() {
        List<Object[]> results = ShipmentDao.getRevenuePerDriver();
        Map<Long, BigDecimal> map = new HashMap<>();
        for (Object[] row : results) {
            map.put((Long) row[0], (BigDecimal) row[1]);
        }
        return map;
    }

    public static BigDecimal getRevenueForPeriod(Long companyId, LocalDateTime startDate, LocalDateTime endDate) {
        return ShipmentDao.getRevenueForPeriod(companyId, startDate, endDate);
    }

    public static BigDecimal getRevenueByCompany(Long companyId) {
        return ShipmentDao.getRevenueByCompany(companyId);
    }

    private static void validateNotEmpty(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty");
        }
    }

    private static void validateNotNull(Object value, String fieldName) throws ValidationException {
        if (value == null) {
            throw new ValidationException(fieldName + " cannot be null");
        }
    }

    private static void validatePositive(BigDecimal value, String fieldName) throws ValidationException {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(fieldName + " must be positive");
        }
    }

    private static void validatePositiveOrZero(BigDecimal value, String fieldName) throws ValidationException {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(fieldName + " must be positive or zero");
        }
    }
}
