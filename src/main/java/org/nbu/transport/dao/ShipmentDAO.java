package org.nbu.transport.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nbu.transport.configuration.SessionFactoryUtil;
import org.nbu.transport.dto.CreateShipmentDto;
import org.nbu.transport.dto.ShipmentDto;
import org.nbu.transport.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ShipmentDao {

    public static void createShipment(Shipment shipment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(shipment);
            transaction.commit();
        }
    }

    public static void createShipmentDto(CreateShipmentDto dto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            
            TransportCompany company = session.find(TransportCompany.class, dto.getCompanyId());
            Client client = session.find(Client.class, dto.getClientId());
            Vehicle vehicle = session.find(Vehicle.class, dto.getVehicleId());
            Employee driver = session.find(Employee.class, dto.getDriverId());
            
            Shipment shipment = new Shipment();
            shipment.setStartPoint(dto.getStartPoint());
            shipment.setEndPoint(dto.getEndPoint());
            shipment.setDepartureDate(dto.getDepartureDate());
            shipment.setArrivalDate(dto.getArrivalDate());
            shipment.setCargoType(dto.getCargoType());
            shipment.setWeight(dto.getWeight());
            shipment.setPassengerCount(dto.getPassengerCount());
            shipment.setPrice(dto.getPrice());
            shipment.setCompany(company);
            shipment.setClient(client);
            shipment.setVehicle(vehicle);
            shipment.setDriver(driver);
            
            session.persist(shipment);
            transaction.commit();
        }
    }

    public static Shipment getShipment(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Shipment.class, id);
        }
    }

    public static List<Shipment> getAllShipments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s FROM Shipment s", Shipment.class)
                    .getResultList();
        }
    }

    public static List<ShipmentDto> getAllShipmentsDto() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT new org.nbu.transport.dto.ShipmentDto(" +
                    "s.id, s.startPoint, s.endPoint, s.departureDate, s.arrivalDate, " +
                    "s.cargoType, s.price, s.paid, c.name, cl.name, " +
                    "CONCAT(v.type, ' ', v.registrationNumber), d.name) " +
                    "FROM Shipment s " +
                    "JOIN s.company c " +
                    "JOIN s.client cl " +
                    "JOIN s.vehicle v " +
                    "JOIN s.driver d", ShipmentDto.class)
                    .getResultList();
        }
    }

    public static List<Shipment> getShipmentsByCompany(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT s FROM Shipment s WHERE s.company.id = :companyId", Shipment.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

    public static List<Shipment> getShipmentsByDestination(String destination) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT s FROM Shipment s WHERE s.endPoint = :destination", Shipment.class)
                    .setParameter("destination", destination)
                    .getResultList();
        }
    }

    public static List<Shipment> getShipmentsByClient(Long clientId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT s FROM Shipment s WHERE s.client.id = :clientId", Shipment.class)
                    .setParameter("clientId", clientId)
                    .getResultList();
        }
    }

    public static List<Shipment> getUnpaidShipments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT s FROM Shipment s WHERE s.paid = false", Shipment.class)
                    .getResultList();
        }
    }

    public static List<Shipment> getPaidShipments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT s FROM Shipment s WHERE s.paid = true", Shipment.class)
                    .getResultList();
        }
    }

    public static List<Shipment> getShipmentsSortedByDestination() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s FROM Shipment s ORDER BY s.endPoint", Shipment.class)
                    .getResultList();
        }
    }

    public static void updateShipment(Long id, Shipment updatedShipment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Shipment shipment = session.find(Shipment.class, id);
            if (shipment != null) {
                shipment.setStartPoint(updatedShipment.getStartPoint());
                shipment.setEndPoint(updatedShipment.getEndPoint());
                shipment.setDepartureDate(updatedShipment.getDepartureDate());
                shipment.setArrivalDate(updatedShipment.getArrivalDate());
                shipment.setCargoType(updatedShipment.getCargoType());
                shipment.setWeight(updatedShipment.getWeight());
                shipment.setPassengerCount(updatedShipment.getPassengerCount());
                shipment.setPrice(updatedShipment.getPrice());
                shipment.setPaid(updatedShipment.isPaid());
                shipment.setCompany(updatedShipment.getCompany());
                shipment.setClient(updatedShipment.getClient());
                shipment.setVehicle(updatedShipment.getVehicle());
                shipment.setDriver(updatedShipment.getDriver());
                session.merge(shipment);
            }
            transaction.commit();
        }
    }

    public static void markShipmentAsPaid(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Shipment shipment = session.find(Shipment.class, id);
            if (shipment != null) {
                shipment.setPaid(true);
                session.merge(shipment);
            }
            transaction.commit();
        }
    }

    public static void deleteShipment(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Shipment shipment = session.find(Shipment.class, id);
            if (shipment != null) {
                session.remove(shipment);
            }
            transaction.commit();
        }
    }

    public static Long getTotalShipmentsCount() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(s) FROM Shipment s", Long.class)
                    .getSingleResult();
        }
    }

    public static BigDecimal getTotalRevenue() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT SUM(s.price) FROM Shipment s", BigDecimal.class)
                    .getSingleResult();
        }
    }

    public static BigDecimal getRevenueByCompany(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT SUM(s.price) FROM Shipment s WHERE s.company.id = :companyId", BigDecimal.class)
                    .setParameter("companyId", companyId)
                    .getSingleResult();
        }
    }

    public static List<Object[]> getShipmentsCountPerDriver() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT s.driver.id, COUNT(s) FROM Shipment s GROUP BY s.driver.id", Object[].class)
                    .getResultList();
        }
    }

    public static List<Object[]> getRevenuePerDriver() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT s.driver.id, SUM(s.price) FROM Shipment s GROUP BY s.driver.id", Object[].class)
                    .getResultList();
        }
    }

    public static BigDecimal getRevenueForPeriod(Long companyId, LocalDateTime startDate, LocalDateTime endDate) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT SUM(s.price) FROM Shipment s " +
                    "WHERE s.company.id = :companyId " +
                    "AND s.departureDate >= :startDate " +
                    "AND s.departureDate <= :endDate", BigDecimal.class)
                    .setParameter("companyId", companyId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getSingleResult();
        }
    }
}
