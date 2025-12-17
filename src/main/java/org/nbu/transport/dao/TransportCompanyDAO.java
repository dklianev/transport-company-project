package org.nbu.transport.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.nbu.transport.configuration.SessionFactoryUtil;
import org.nbu.transport.dto.CompanyDto;
import org.nbu.transport.dto.CreateCompanyDto;
import org.nbu.transport.entity.TransportCompany;

import java.math.BigDecimal;
import java.util.List;

public class TransportCompanyDao {

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

    // --- Criteria API methods (like in example project) ---

    public static List<TransportCompany> findByRevenueBetween(BigDecimal bottom, BigDecimal top) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TransportCompany> cr = cb.createQuery(TransportCompany.class);
            Root<TransportCompany> root = cr.from(TransportCompany.class);

            cr.select(root).where(cb.between(root.get("revenue"), bottom, top));

            Query<TransportCompany> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    public static List<TransportCompany> findByNameStartingWithAndRevenueGreaterThan(String name, BigDecimal revenue) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TransportCompany> cr = cb.createQuery(TransportCompany.class);
            Root<TransportCompany> root = cr.from(TransportCompany.class);

            Predicate greaterThanRevenue = cb.greaterThan(root.get("revenue"), revenue);
            Predicate nameStartingWith = cb.like(root.get("name"), name + "%");

            cr.select(root).where(cb.and(nameStartingWith, greaterThanRevenue));

            Query<TransportCompany> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    public static BigDecimal sumRevenue() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cr = cb.createQuery(BigDecimal.class);
            Root<TransportCompany> root = cr.from(TransportCompany.class);

            cr.select(cb.sum(root.get("revenue")));

            Query<BigDecimal> query = session.createQuery(cr);
            return query.getSingleResult();
        }
    }

    public static List<TransportCompany> findByNameLike(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TransportCompany> cr = cb.createQuery(TransportCompany.class);
            Root<TransportCompany> root = cr.from(TransportCompany.class);
            cr.select(root).where(cb.like(root.get("name"), "%" + name + "%"));

            Query<TransportCompany> query = session.createQuery(cr);
            return query.getResultList();
        }
    }
}
