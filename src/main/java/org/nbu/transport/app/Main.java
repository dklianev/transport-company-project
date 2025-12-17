package org.nbu.transport.app;

import org.nbu.transport.dao.*;
import org.nbu.transport.dto.*;
import org.nbu.transport.entity.*;
import org.nbu.transport.service.TransportService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("   TRANSPORT COMPANY MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println();

            // CRUD операции
            demonstrateCRUDOperations();
            
            // DTO операции
            demonstrateDtoOperations();
            
            // Четене и сортиране
            demonstrateCompanyOperations();
            demonstrateEmployeeOperations();
            demonstrateVehicleOperations();
            demonstrateClientOperations();
            demonstrateShipmentOperations();
            
            // Справки
            demonstrateReports();
            
            // Файлови операции
            demonstrateFileOperations();

            System.out.println("\n========================================");
            System.out.println("   DEMO COMPLETED SUCCESSFULLY");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("Error during demo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void demonstrateCRUDOperations() {
        System.out.println("--- CRUD OPERATIONS ---\n");

        // ============ CREATE ============
        System.out.println("[CREATE] Creating new records...\n");

        try {
            // Създаване на нова компания
            System.out.println("Creating new company 'Demo Transport Ltd'...");
            TransportService.createCompany("Demo Transport Ltd", new BigDecimal("0.00"));
            System.out.println("✓ Company created successfully!");

            // Вземаме последната компания (новосъздадената)
            List<TransportCompany> allCompanies = TransportCompanyDao.getAllCompanies();
            TransportCompany demoCompany = allCompanies.get(allCompanies.size() - 1);
            System.out.println("  Created: " + demoCompany);

            // Създаване на нов клиент
            System.out.println("\nCreating new client 'Demo Client'...");
            TransportService.createClient("Demo Client", "+359888999000", "demo@client.bg");
            System.out.println("✓ Client created successfully!");

            List<Client> allClients = ClientDao.getAllClients();
            Client demoClient = allClients.get(allClients.size() - 1);
            System.out.println("  Created: " + demoClient);

            // Създаване на нов служител
            System.out.println("\nCreating new employee 'Demo Driver'...");
            TransportService.createEmployee("Demo Driver", "C - trucks", new BigDecimal("2000.00"), demoCompany.getId());
            System.out.println("✓ Employee created successfully!");

            List<Employee> allEmployees = EmployeeDao.getAllEmployees();
            Employee demoEmployee = allEmployees.get(allEmployees.size() - 1);
            System.out.println("  Created: " + demoEmployee);

            // Създаване на ново превозно средство
            System.out.println("\nCreating new vehicle...");
            TransportService.createVehicle("Truck", "CB9999XX", demoCompany.getId());
            System.out.println("✓ Vehicle created successfully!");

            List<Vehicle> allVehicles = VehicleDao.getAllVehicles();
            Vehicle demoVehicle = allVehicles.get(allVehicles.size() - 1);
            System.out.println("  Created: " + demoVehicle);

            // ============ UPDATE ============
            System.out.println("\n[UPDATE] Updating records...\n");

            // Обновяване на компанията
            System.out.println("Updating company revenue to 5000.00...");
            TransportCompany updatedCompany = new TransportCompany("Demo Transport Ltd", new BigDecimal("5000.00"));
            TransportCompanyDao.updateCompany(demoCompany.getId(), updatedCompany);
            System.out.println("✓ Company updated successfully!");

            TransportCompany refreshedCompany = TransportCompanyDao.getCompany(demoCompany.getId());
            System.out.println("  Updated: " + refreshedCompany);

            // Обновяване на служител
            System.out.println("\nUpdating employee salary to 2500.00...");
            Employee updatedEmployee = new Employee("Demo Driver Senior", "C+E - trucks with trailer", new BigDecimal("2500.00"));
            EmployeeDao.updateEmployee(demoEmployee.getId(), updatedEmployee);
            System.out.println("✓ Employee updated successfully!");

            Employee refreshedEmployee = EmployeeDao.getEmployee(demoEmployee.getId());
            System.out.println("  Updated: " + refreshedEmployee);

            // Обновяване на клиент
            System.out.println("\nUpdating client phone number...");
            Client updatedClient = new Client("Demo Client Updated", "+359999888777", "updated@client.bg");
            ClientDao.updateClient(demoClient.getId(), updatedClient);
            System.out.println("✓ Client updated successfully!");

            Client refreshedClient = ClientDao.getClient(demoClient.getId());
            System.out.println("  Updated: " + refreshedClient);

            // Обновяване на превозно средство
            System.out.println("\nUpdating vehicle type...");
            Vehicle updatedVehicle = new Vehicle("Truck Large", "CB9999XX", demoCompany);
            VehicleDao.updateVehicle(demoVehicle.getId(), updatedVehicle);
            System.out.println("✓ Vehicle updated successfully!");

            Vehicle refreshedVehicle = VehicleDao.getVehicle(demoVehicle.getId());
            System.out.println("  Updated: " + refreshedVehicle);

            // ============ DELETE ============
            System.out.println("\n[DELETE] Deleting demo records...\n");

            // Изтриване в обратен ред (заради foreign keys)
            System.out.println("Deleting demo vehicle...");
            VehicleDao.deleteVehicle(demoVehicle.getId());
            System.out.println("✓ Vehicle deleted!");

            System.out.println("Deleting demo employee...");
            EmployeeDao.deleteEmployee(demoEmployee.getId());
            System.out.println("✓ Employee deleted!");

            System.out.println("Deleting demo client...");
            ClientDao.deleteClient(demoClient.getId());
            System.out.println("✓ Client deleted!");

            System.out.println("Deleting demo company...");
            TransportCompanyDao.deleteCompany(demoCompany.getId());
            System.out.println("✓ Company deleted!");

            System.out.println("\n✓ All CRUD operations completed successfully!\n");

        } catch (Exception e) {
            System.err.println("Error in CRUD operations: " + e.getMessage());
        }
    }

    private static void demonstrateDtoOperations() {
        System.out.println("--- DTO OPERATIONS ---\n");

        try {
            // ============ CREATE WITH DTOs ============
            System.out.println("[CREATE WITH DTO] Creating records using DTOs...\n");

            // 1. Създаване на компания чрез DTO
            System.out.println("1. Creating company via CreateCompanyDto...");
            CreateCompanyDto createCompanyDto = new CreateCompanyDto("DTO Transport OOD", new BigDecimal("10000.00"));
            TransportService.createCompanyDto(createCompanyDto);
            System.out.println("   ✓ Company created: " + createCompanyDto);

            // Вземаме новосъздадената компания
            List<TransportCompany> allCompanies = TransportCompanyDao.getAllCompanies();
            TransportCompany dtoCompany = allCompanies.get(allCompanies.size() - 1);

            // 2. Създаване на клиент чрез DTO
            System.out.println("\n2. Creating client via CreateClientDto...");
            CreateClientDto createClientDto = new CreateClientDto("DTO Client", "+359111222333", "dto@client.bg");
            TransportService.createClientDto(createClientDto);
            System.out.println("   ✓ Client created: " + createClientDto);

            // Вземаме новосъздадения клиент
            List<Client> allClients = ClientDao.getAllClients();
            Client dtoClient = allClients.get(allClients.size() - 1);

            // 3. Създаване на служител чрез DTO
            System.out.println("\n3. Creating employee via CreateEmployeeDto...");
            CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto(
                    "DTO Driver", "B - cars", new BigDecimal("1800.00"), dtoCompany.getId());
            TransportService.createEmployeeDto(createEmployeeDto);
            System.out.println("   ✓ Employee created: " + createEmployeeDto);

            // Вземаме новосъздадения служител
            List<Employee> allEmployees = EmployeeDao.getAllEmployees();
            Employee dtoEmployee = allEmployees.get(allEmployees.size() - 1);

            // 4. Създаване на превозно средство чрез DTO
            System.out.println("\n4. Creating vehicle via CreateVehicleDto...");
            CreateVehicleDto createVehicleDto = new CreateVehicleDto(
                    "Bus", "CB1111DTO", dtoCompany.getId());
            TransportService.createVehicleDto(createVehicleDto);
            System.out.println("   ✓ Vehicle created: " + createVehicleDto);

            // Вземаме новосъздаденото превозно средство
            List<Vehicle> allVehicles = VehicleDao.getAllVehicles();
            Vehicle dtoVehicle = allVehicles.get(allVehicles.size() - 1);

            // 5. Създаване на превоз чрез DTO
            System.out.println("\n5. Creating shipment via CreateShipmentDto...");
            CreateShipmentDto createShipmentDto = new CreateShipmentDto(
                    "Sofia", "Varna",
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1).plusHours(6),
                    Shipment.CargoType.PASSENGERS,
                    new BigDecimal("150.00"),
                    dtoCompany.getId(),
                    dtoClient.getId(),
                    dtoVehicle.getId(),
                    dtoEmployee.getId());
            createShipmentDto.setPassengerCount(25);
            TransportService.createShipmentDto(createShipmentDto);
            System.out.println("   ✓ Shipment created: " + createShipmentDto);

            // Вземаме новосъздадения превоз
            List<Shipment> allShipments = ShipmentDao.getAllShipments();
            Shipment dtoShipment = allShipments.get(allShipments.size() - 1);

            // ============ READ WITH DTOs ============
            System.out.println("\n[READ WITH DTO] Fetching data as DTOs...\n");

            // Получаване на компании като DTO
            System.out.println("All companies as CompanyDto:");
            List<CompanyDto> companyDtos = TransportService.getAllCompaniesDto();
            companyDtos.forEach(dto -> System.out.println("  " + dto));

            // Получаване на конкретна компания като DTO
            System.out.println("\nFetching single company as CompanyDto (ID=1):");
            CompanyDto singleCompanyDto = TransportService.getCompanyDto(1L);
            if (singleCompanyDto != null) {
                System.out.println("  " + singleCompanyDto);
            } else {
                System.out.println("  Company not found.");
            }

            // Получаване на служители като DTO
            System.out.println("\nAll employees as EmployeeDto:");
            List<EmployeeDto> employeeDtos = TransportService.getAllEmployeesDto();
            if (employeeDtos.isEmpty()) {
                System.out.println("  No employees found.");
            } else {
                employeeDtos.forEach(dto -> System.out.println("  " + dto));
            }

            // Получаване на служители с компания като DTO
            System.out.println("\nEmployees with company info (EmployeeCompanyDto) for company " + dtoCompany.getId() + ":");
            List<EmployeeCompanyDto> employeeCompanyDtos = TransportService.getEmployeesWithCompanyDto(dtoCompany.getId());
            if (employeeCompanyDtos.isEmpty()) {
                System.out.println("  No employees for this company.");
            } else {
                employeeCompanyDtos.forEach(dto -> System.out.println("  " + dto));
            }

            // Получаване на клиенти като DTO
            System.out.println("\nAll clients as ClientDto:");
            List<ClientDto> clientDtos = TransportService.getAllClientsDto();
            clientDtos.forEach(dto -> System.out.println("  " + dto));

            // Получаване на превозни средства като DTO
            System.out.println("\nAll vehicles as VehicleDto:");
            List<VehicleDto> vehicleDtos = TransportService.getAllVehiclesDto();
            if (vehicleDtos.isEmpty()) {
                System.out.println("  No vehicles found.");
            } else {
                vehicleDtos.forEach(dto -> System.out.println("  " + dto));
            }

            // Получаване на превози като DTO
            System.out.println("\nAll shipments as ShipmentDto:");
            List<ShipmentDto> shipmentDtos = TransportService.getAllShipmentsDto();
            if (shipmentDtos.isEmpty()) {
                System.out.println("  No shipments found.");
            } else {
                shipmentDtos.forEach(dto -> System.out.println("  " + dto));
            }

            // ============ CLEANUP ============
            System.out.println("\n[CLEANUP] Deleting DTO demo records...");
            
            // Изтриване в правилен ред (заради foreign keys)
            ShipmentDao.deleteShipment(dtoShipment.getId());
            System.out.println("✓ Deleted demo shipment");

            VehicleDao.deleteVehicle(dtoVehicle.getId());
            System.out.println("✓ Deleted demo vehicle");

            EmployeeDao.deleteEmployee(dtoEmployee.getId());
            System.out.println("✓ Deleted demo employee");

            ClientDao.deleteClient(dtoClient.getId());
            System.out.println("✓ Deleted demo client");

            TransportCompanyDao.deleteCompany(dtoCompany.getId());
            System.out.println("✓ Deleted demo company");

            System.out.println("\n✓ All DTO operations completed successfully!\n");

        } catch (Exception e) {
            System.err.println("Error in DTO operations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void demonstrateCompanyOperations() {
        System.out.println("--- COMPANY OPERATIONS ---\n");

        System.out.println("Fetching all companies...");
        List<TransportCompany> companies = TransportCompanyDao.getAllCompanies();
        companies.forEach(System.out::println);

        System.out.println("\nCompanies sorted by name:");
        TransportService.getCompaniesSortedByName().forEach(System.out::println);

        System.out.println("\nCompanies sorted by revenue (descending):");
        TransportService.getCompaniesSortedByRevenue().forEach(System.out::println);
    }

    private static void demonstrateEmployeeOperations() {
        System.out.println("\n--- EMPLOYEE OPERATIONS ---\n");

        System.out.println("Fetching all employees...");
        List<Employee> employees = EmployeeDao.getAllEmployees();
        employees.forEach(System.out::println);

        System.out.println("\nEmployees sorted by qualification:");
        TransportService.getEmployeesSortedByQualification().forEach(System.out::println);

        System.out.println("\nEmployees sorted by salary (descending):");
        TransportService.getEmployeesSortedBySalary().forEach(System.out::println);

        System.out.println("\nEmployees of company with ID 1:");
        EmployeeDao.getEmployeesByCompany(1L).forEach(System.out::println);

        System.out.println("\nEmployees with qualification 'C - trucks':");
        List<Employee> truckDrivers = TransportService.getEmployeesByQualification("C - trucks");
        if (truckDrivers.isEmpty()) {
            System.out.println("  No employees with this qualification.");
        } else {
            truckDrivers.forEach(System.out::println);
        }
    }

    private static void demonstrateVehicleOperations() {
        System.out.println("\n--- VEHICLE OPERATIONS ---\n");

        System.out.println("Fetching all vehicles...");
        List<Vehicle> vehicles = VehicleDao.getAllVehicles();
        vehicles.forEach(System.out::println);

        System.out.println("\nVehicles of company with ID 1:");
        VehicleDao.getVehiclesByCompany(1L).forEach(System.out::println);
    }

    private static void demonstrateClientOperations() {
        System.out.println("\n--- CLIENT OPERATIONS ---\n");

        System.out.println("Fetching all clients...");
        List<Client> clients = ClientDao.getAllClients();
        clients.forEach(System.out::println);
    }

    private static void demonstrateShipmentOperations() {
        System.out.println("\n--- SHIPMENT OPERATIONS ---\n");

        System.out.println("Fetching all shipments...");
        List<Shipment> shipments = ShipmentDao.getAllShipments();
        shipments.forEach(System.out::println);

        System.out.println("\nShipments sorted by destination:");
        TransportService.getShipmentsSortedByDestination().forEach(System.out::println);

        System.out.println("\nShipments to Plovdiv:");
        ShipmentDao.getShipmentsByDestination("Plovdiv").forEach(System.out::println);

        System.out.println("\nShipments by company 1:");
        ShipmentDao.getShipmentsByCompany(1L).forEach(System.out::println);

        // Превози на конкретен клиент
        System.out.println("\nShipments by client 1:");
        List<Shipment> clientShipments = TransportService.getShipmentsByClient(1L);
        if (clientShipments.isEmpty()) {
            System.out.println("  No shipments for this client.");
        } else {
            clientShipments.forEach(System.out::println);
        }

        // Неплатени превози
        System.out.println("\nUnpaid shipments:");
        List<Shipment> unpaidList = TransportService.getUnpaidShipments();
        if (unpaidList.isEmpty()) {
            System.out.println("  All shipments are paid!");
        } else {
            unpaidList.forEach(System.out::println);
        }

        // Платени превози
        System.out.println("\nPaid shipments:");
        List<Shipment> paidList = TransportService.getPaidShipments();
        if (paidList.isEmpty()) {
            System.out.println("  No paid shipments yet.");
        } else {
            paidList.forEach(System.out::println);
        }

        // Маркиране като платен (ако има непълатени превози)
        if (!unpaidList.isEmpty()) {
            Long unpaidId = unpaidList.get(0).getId();
            System.out.println("\nMarking shipment #" + unpaidId + " as paid...");
            TransportService.markShipmentAsPaid(unpaidId);
            System.out.println("✓ Shipment marked as paid!");
        }

        // Демонстрация на getShipment и updateShipment
        if (!shipments.isEmpty()) {
            Long shipmentId = shipments.get(0).getId();
            System.out.println("\nFetching shipment by ID " + shipmentId + ":");
            Shipment singleShipment = ShipmentDao.getShipment(shipmentId);
            if (singleShipment != null) {
                System.out.println("  " + singleShipment);
                
                // Обновяване на превоз (промяна на цената)
                System.out.println("\nUpdating shipment price...");
                BigDecimal originalPrice = singleShipment.getPrice();
                singleShipment.setPrice(originalPrice.add(new BigDecimal("10.00")));
                ShipmentDao.updateShipment(shipmentId, singleShipment);
                System.out.println("✓ Shipment updated! New price: " + singleShipment.getPrice() + " BGN");
                
                // Връщаме оригиналната цена
                singleShipment.setPrice(originalPrice);
                ShipmentDao.updateShipment(shipmentId, singleShipment);
                System.out.println("  (Price restored to original: " + originalPrice + " BGN)");
            }
        }
    }

    private static void demonstrateReports() {
        System.out.println("\n--- REPORTS ---\n");

        // Общ брой превози
        Long totalCount = TransportService.getTotalShipmentsCount();
        System.out.println("Total number of shipments: " + totalCount);

        // Обща сума
        BigDecimal totalRevenue = TransportService.getTotalRevenue();
        System.out.println("Total revenue from all shipments: " + 
                (totalRevenue != null ? totalRevenue + " BGN" : "0.00 BGN"));

        // Приходи по компания
        System.out.println("\nRevenue by company:");
        List<TransportCompany> companies = TransportCompanyDao.getAllCompanies();
        for (TransportCompany company : companies) {
            BigDecimal companyRevenue = TransportService.getRevenueByCompany(company.getId());
            System.out.println("  " + company.getName() + ": " + 
                    (companyRevenue != null ? companyRevenue : "0.00") + " BGN");
        }

        // Брой превози на шофьор
        System.out.println("\nShipments count per driver:");
        Map<Long, Integer> shipmentsPerDriver = TransportService.getShipmentsCountPerDriver();
        if (shipmentsPerDriver.isEmpty()) {
            System.out.println("  No shipments recorded yet.");
        } else {
            shipmentsPerDriver.forEach((driverId, count) ->
                    System.out.println("  Driver ID " + driverId + ": " + count + " shipments"));
        }

        // Приходи на шофьор
        System.out.println("\nRevenue per driver:");
        Map<Long, BigDecimal> revenuePerDriver = TransportService.getRevenuePerDriver();
        if (revenuePerDriver.isEmpty()) {
            System.out.println("  No revenue recorded yet.");
        } else {
            revenuePerDriver.forEach((driverId, revenue) ->
                    System.out.println("  Driver ID " + driverId + ": " + revenue + " BGN"));
        }

        // Приходи за период
        System.out.println("\nRevenue for company 1 in December 2025:");
        LocalDateTime startDate = LocalDateTime.of(2025, 12, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        BigDecimal periodRevenue = TransportService.getRevenueForPeriod(1L, startDate, endDate);
        System.out.println("  " + (periodRevenue != null ? periodRevenue : "0.00") + " BGN");
    }

    private static void demonstrateFileOperations() {
        System.out.println("\n--- FILE OPERATIONS ---\n");

        List<Shipment> shipments = ShipmentDao.getAllShipments();
        
        if (shipments.isEmpty()) {
            System.out.println("No shipments to export.");
            return;
        }

        // Експорт на всички превози
        System.out.println("Exporting all shipments to file...");
        TransportService.exportShipmentsToFile(shipments, "all_shipments");
        System.out.println("✓ Export completed! Files created:");
        System.out.println("  - shipments/all_shipments.txt (human-readable)");
        System.out.println("  - shipments/all_shipments.ser (serialized)");

        // Експорт по компания
        System.out.println("\nExporting shipments by company 1 to file...");
        List<Shipment> company1Shipments = ShipmentDao.getShipmentsByCompany(1L);
        if (!company1Shipments.isEmpty()) {
            TransportService.exportShipmentsToFile(company1Shipments, "company_1_shipments");
            System.out.println("✓ Export completed!");
        } else {
            System.out.println("  No shipments for company 1.");
        }

        // Импорт от файл
        System.out.println("\nImporting shipments from file...");
        List<Shipment> importedShipments = TransportService.importShipmentsFromFile("all_shipments");
        if (importedShipments != null && !importedShipments.isEmpty()) {
            System.out.println("✓ Import completed! Loaded " + importedShipments.size() + " shipments:");
            importedShipments.forEach(s -> System.out.println("  - " + s));
        } else {
            System.out.println("  No shipments imported (file may not exist yet).");
        }
    }
}
