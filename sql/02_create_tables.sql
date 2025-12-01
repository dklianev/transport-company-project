-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

USE `transport_company_db` ;

-- -----------------------------------------------------
-- Table `transport_company_db`.`transport_company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transport_company_db`.`transport_company` ;

CREATE TABLE IF NOT EXISTS `transport_company_db`.`transport_company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `revenue` DECIMAL(15,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `transport_company_db`.`employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transport_company_db`.`employee` ;

CREATE TABLE IF NOT EXISTS `transport_company_db`.`employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `qualification` VARCHAR(255) NOT NULL,
  `salary` DECIMAL(10,2) NOT NULL,
  `company_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_company_idx` (`company_id` ASC) VISIBLE,
  CONSTRAINT `fk_employee_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `transport_company_db`.`transport_company` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `transport_company_db`.`vehicle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transport_company_db`.`vehicle` ;

CREATE TABLE IF NOT EXISTS `transport_company_db`.`vehicle` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(100) NOT NULL,
  `registration_number` VARCHAR(50) NOT NULL,
  `company_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `registration_number_UNIQUE` (`registration_number` ASC) VISIBLE,
  INDEX `fk_vehicle_company_idx` (`company_id` ASC) VISIBLE,
  CONSTRAINT `fk_vehicle_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `transport_company_db`.`transport_company` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `transport_company_db`.`client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transport_company_db`.`client` ;

CREATE TABLE IF NOT EXISTS `transport_company_db`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(50) NULL,
  `email` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `transport_company_db`.`shipment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transport_company_db`.`shipment` ;

CREATE TABLE IF NOT EXISTS `transport_company_db`.`shipment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_point` VARCHAR(255) NOT NULL,
  `end_point` VARCHAR(255) NOT NULL,
  `departure_date` DATETIME NOT NULL,
  `arrival_date` DATETIME NOT NULL,
  `cargo_type` ENUM('GOODS', 'PASSENGERS') NOT NULL,
  `weight` DECIMAL(10,2) NULL,
  `passenger_count` INT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `paid` TINYINT(1) NOT NULL DEFAULT 0,
  `company_id` INT NOT NULL,
  `client_id` INT NOT NULL,
  `vehicle_id` INT NOT NULL,
  `driver_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_shipment_company_idx` (`company_id` ASC) VISIBLE,
  INDEX `fk_shipment_client_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_shipment_vehicle_idx` (`vehicle_id` ASC) VISIBLE,
  INDEX `fk_shipment_driver_idx` (`driver_id` ASC) VISIBLE,
  CONSTRAINT `fk_shipment_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `transport_company_db`.`transport_company` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipment_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `transport_company_db`.`client` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipment_vehicle`
    FOREIGN KEY (`vehicle_id`)
    REFERENCES `transport_company_db`.`vehicle` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipment_driver`
    FOREIGN KEY (`driver_id`)
    REFERENCES `transport_company_db`.`employee` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
