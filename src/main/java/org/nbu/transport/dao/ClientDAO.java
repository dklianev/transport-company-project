package org.nbu.transport.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nbu.transport.configuration.SessionFactoryUtil;
import org.nbu.transport.dto.ClientDto;
import org.nbu.transport.dto.CreateClientDto;
import org.nbu.transport.entity.Client;

import java.util.List;

public class ClientDao {

    public static void createClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
        }
    }

    public static void createClientDto(CreateClientDto createClientDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = new Client();
            client.setName(createClientDto.getName());
            client.setPhone(createClientDto.getPhone());
            client.setEmail(createClientDto.getEmail());
            session.persist(client);
            transaction.commit();
        }
    }

    public static Client getClient(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Client.class, id);
        }
    }

    public static List<Client> getAllClients() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM Client c", Client.class)
                    .getResultList();
        }
    }

    public static List<ClientDto> getAllClientsDto() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT new org.nbu.transport.dto.ClientDto(c.id, c.name, c.phone, c.email) " +
                    "FROM Client c", ClientDto.class)
                    .getResultList();
        }
    }

    public static void updateClient(Long id, Client updatedClient) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.find(Client.class, id);
            if (client != null) {
                client.setName(updatedClient.getName());
                client.setPhone(updatedClient.getPhone());
                client.setEmail(updatedClient.getEmail());
                session.merge(client);
            }
            transaction.commit();
        }
    }

    public static void deleteClient(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.find(Client.class, id);
            if (client != null) {
                session.remove(client);
            }
            transaction.commit();
        }
    }
}
