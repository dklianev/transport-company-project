package org.nbu.transport.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nbu.transport.config.SessionFactoryUtil;
import org.nbu.transport.dto.CreateEmployeeDto;
import org.nbu.transport.dto.EmployeeCompanyDto;
import org.nbu.transport.dto.EmployeeDto;
import org.nbu.transport.entity.Employee;
import org.nbu.transport.entity.TransportCompany;

import java.util.List;

public class EmployeeDAO {

    public static void createEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
        }
    }

    public static void createEmployeeDto(CreateEmployeeDto createEmployeeDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TransportCompany company = session.find(TransportCompany.class, createEmployeeDto.getCompanyId());
            Employee employee = new Employee();
            employee.setName(createEmployeeDto.getName());
            employee.setQualification(createEmployeeDto.getQualification());
            employee.setSalary(createEmployeeDto.getSalary());
            employee.setCompany(company);
            session.persist(employee);
            transaction.commit();
        }
    }

    public static Employee getEmployee(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Employee.class, id);
        }
    }

    public static List<Employee> getAllEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        }
    }

    public static List<EmployeeDto> getAllEmployeesDto() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT new org.nbu.transport.dto.EmployeeDto(e.id, e.name, e.qualification, e.salary) " +
                    "FROM Employee e", EmployeeDto.class)
                    .getResultList();
        }
    }

    public static List<EmployeeCompanyDto> getEmployeesWithCompanyDto(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT new org.nbu.transport.dto.EmployeeCompanyDto(" +
                    "e.id, e.name, e.qualification, e.salary, c.id, c.name) " +
                    "FROM Employee e JOIN e.company c WHERE c.id = :companyId", EmployeeCompanyDto.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

    public static List<Employee> getEmployeesByCompany(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT e FROM Employee e WHERE e.company.id = :companyId", Employee.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

    public static List<Employee> getEmployeesSortedByQualification() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT e FROM Employee e ORDER BY e.qualification", Employee.class)
                    .getResultList();
        }
    }

    public static List<Employee> getEmployeesSortedBySalary() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT e FROM Employee e ORDER BY e.salary DESC", Employee.class)
                    .getResultList();
        }
    }

    public static List<Employee> getEmployeesByQualification(String qualification) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT e FROM Employee e WHERE e.qualification = :qualification", Employee.class)
                    .setParameter("qualification", qualification)
                    .getResultList();
        }
    }

    public static void updateEmployee(Long id, Employee updatedEmployee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, id);
            if (employee != null) {
                employee.setName(updatedEmployee.getName());
                employee.setQualification(updatedEmployee.getQualification());
                employee.setSalary(updatedEmployee.getSalary());
                employee.setCompany(updatedEmployee.getCompany());
                session.merge(employee);
            }
            transaction.commit();
        }
    }

    public static void deleteEmployee(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, id);
            if (employee != null) {
                session.remove(employee);
            }
            transaction.commit();
        }
    }
}
