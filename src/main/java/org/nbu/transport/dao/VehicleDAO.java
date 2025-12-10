package org.nbu.transport.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nbu.transport.config.SessionFactoryUtil;
import org.nbu.transport.dto.CreateVehicleDto;
import org.nbu.transport.dto.VehicleDto;
import org.nbu.transport.entity.TransportCompany;
import org.nbu.transport.entity.Vehicle;

import java.util.List;

public class VehicleDAO {

    public static void createVehicle(Vehicle vehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(vehicle);
            transaction.commit();
        }
    }

    public static void createVehicleDto(CreateVehicleDto createVehicleDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TransportCompany company = session.find(TransportCompany.class, createVehicleDto.getCompanyId());
            Vehicle vehicle = new Vehicle();
            vehicle.setType(createVehicleDto.getType());
            vehicle.setRegistrationNumber(createVehicleDto.getRegistrationNumber());
            vehicle.setCompany(company);
            session.persist(vehicle);
            transaction.commit();
        }
    }

    public static Vehicle getVehicle(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Vehicle.class, id);
        }
    }

    public static List<Vehicle> getAllVehicles() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT v FROM Vehicle v", Vehicle.class)
                    .getResultList();
        }
    }

    public static List<VehicleDto> getAllVehiclesDto() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT new org.nbu.transport.dto.VehicleDto(v.id, v.type, v.registrationNumber, c.id, c.name) " +
                    "FROM Vehicle v JOIN v.company c", VehicleDto.class)
                    .getResultList();
        }
    }

    public static List<Vehicle> getVehiclesByCompany(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT v FROM Vehicle v WHERE v.company.id = :companyId", Vehicle.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

    public static void updateVehicle(Long id, Vehicle updatedVehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Vehicle vehicle = session.find(Vehicle.class, id);
            if (vehicle != null) {
                vehicle.setType(updatedVehicle.getType());
                vehicle.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
                vehicle.setCompany(updatedVehicle.getCompany());
                session.merge(vehicle);
            }
            transaction.commit();
        }
    }

    public static void deleteVehicle(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Vehicle vehicle = session.find(Vehicle.class, id);
            if (vehicle != null) {
                session.remove(vehicle);
            }
            transaction.commit();
        }
    }
}
