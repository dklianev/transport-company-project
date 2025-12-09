package org.nbu.transport.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nbu.transport.config.SessionFactoryUtil;
import org.nbu.transport.dto.CompanyDto;
import org.nbu.transport.dto.CreateCompanyDto;
import org.nbu.transport.entity.TransportCompany;

import java.util.List;

public class TransportCompanyDAO {

    public static void createCompany(TransportCompany company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(company);
            transaction.commit();
        }
    }

    public static void createCompanyDto(CreateCompanyDto createCompanyDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TransportCompany company = new TransportCompany();
            company.setName(createCompanyDto.getName());
            company.setRevenue(createCompanyDto.getRevenue());
            session.persist(company);
            transaction.commit();
        }
    }

    public static TransportCompany getCompany(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(TransportCompany.class, id);
        }
    }

    public static List<TransportCompany> getAllCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM TransportCompany c", TransportCompany.class)
                    .getResultList();
        }
    }

    public static List<CompanyDto> getAllCompaniesDto() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT new org.nbu.transport.dto.CompanyDto(c.id, c.name, c.revenue) " +
                    "FROM TransportCompany c", CompanyDto.class)
                    .getResultList();
        }
    }

    public static CompanyDto getCompanyDto(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT new org.nbu.transport.dto.CompanyDto(c.id, c.name, c.revenue) " +
                    "FROM TransportCompany c WHERE c.id = :id", CompanyDto.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public static List<TransportCompany> getCompaniesSortedByName() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM TransportCompany c ORDER BY c.name", TransportCompany.class)
                    .getResultList();
        }
    }

    public static List<TransportCompany> getCompaniesSortedByRevenue() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM TransportCompany c ORDER BY c.revenue DESC", TransportCompany.class)
                    .getResultList();
        }
    }

    public static void updateCompany(Long id, TransportCompany updatedCompany) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TransportCompany company = session.find(TransportCompany.class, id);
            if (company != null) {
                company.setName(updatedCompany.getName());
                company.setRevenue(updatedCompany.getRevenue());
                session.merge(company);
            }
            transaction.commit();
        }
    }

    public static void deleteCompany(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TransportCompany company = session.find(TransportCompany.class, id);
            if (company != null) {
                session.remove(company);
            }
            transaction.commit();
        }
    }
}
