-- MySQL Workbench Sample Data

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

USE `transport_company_db` ;

INSERT INTO `transport_company` (`name`, `revenue`) VALUES
('Speedy Transport Ltd.', 125000.00),
('Express Cargo Services', 89500.00),
('Global Logistics Bulgaria', 210000.00);

INSERT INTO `employee` (`name`, `qualification`, `salary`, `company_id`) VALUES
('Ivan Petrov', 'Standard Driver License', 1500.00, 1),
('Georgi Dimitrov', 'ADR - Dangerous Goods', 2200.00, 1),
('Maria Georgieva', 'D Category - Passenger Transport', 1800.00, 1),
('Petar Nikolov', 'Standard Driver License', 1600.00, 2),
('Elena Ivanova', 'ADR - Flammable Materials', 2400.00, 2),
('Stoyan Stoyanov', 'D Category - Passenger Transport', 1900.00, 3),
('Dimitar Georgiev', 'C+E Category - Heavy Trucks', 2100.00, 3);

INSERT INTO `vehicle` (`type`, `registration_number`, `company_id`) VALUES
('Truck', 'CB 1234 AB', 1),
('Van', 'CB 5678 CD', 1),
('Bus', 'CB 9012 EF', 1),
('Truck', 'PB 2345 GH', 2),
('Tanker', 'PB 6789 IJ', 2),
('Bus', 'CA 3456 KL', 3),
('Truck', 'CA 7890 MN', 3);

INSERT INTO `client` (`name`, `phone`, `email`) VALUES
('Tech Solutions EOOD', '+359 888 123 456', 'contact@techsolutions.bg'),
('Retail Group AD', '+359 887 234 567', 'info@retailgroup.bg'),
('Manufacturing Corp', '+359 886 345 678', 'sales@manufacturing.bg'),
('Import Export Ltd', '+359 885 456 789', 'office@importexport.bg'),
('University of Sofia', '+359 884 567 890', 'transport@uni-sofia.bg');

INSERT INTO `shipment` (`start_point`, `end_point`, `departure_date`, `arrival_date`,
                        `cargo_type`, `weight`, `passenger_count`, `price`, `paid`,
                        `company_id`, `client_id`, `vehicle_id`, `driver_id`) VALUES
('Sofia', 'Plovdiv', '2024-11-01 08:00:00', '2024-11-01 10:30:00', 'GOODS', 1500.00, NULL, 450.00, 1, 1, 1, 1, 1),
('Varna', 'Burgas', '2024-11-02 09:00:00', '2024-11-02 10:15:00', 'PASSENGERS', NULL, 35, 350.00, 1, 1, 5, 3, 3),
('Sofia', 'Ruse', '2024-11-03 07:00:00', '2024-11-03 11:00:00', 'GOODS', 3200.00, NULL, 720.00, 0, 2, 2, 4, 4),
('Plovdiv', 'Varna', '2024-11-05 06:30:00', '2024-11-05 11:30:00', 'GOODS', 2800.00, NULL, 650.00, 1, 2, 3, 5, 5),
('Sofia', 'Blagoevgrad', '2024-11-07 10:00:00', '2024-11-07 12:00:00', 'PASSENGERS', NULL, 42, 420.00, 1, 3, 5, 6, 6),
('Burgas', 'Sofia', '2024-11-10 08:00:00', '2024-11-10 13:00:00', 'GOODS', 4500.00, NULL, 980.00, 0, 3, 4, 7, 7),
('Sofia', 'Veliko Tarnovo', '2024-11-12 09:00:00', '2024-11-12 12:00:00', 'GOODS', 1800.00, NULL, 520.00, 1, 1, 1, 1, 1),
('Plovdiv', 'Sofia', '2024-11-15 14:00:00', '2024-11-15 16:30:00', 'PASSENGERS', NULL, 28, 280.00, 1, 1, 5, 3, 3);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
