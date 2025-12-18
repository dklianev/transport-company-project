# Transport Company – документация

## 1) Общ преглед
Конзолно Java приложение за управление на транспортна компания с персистентност в MySQL чрез Hibernate.

## 2) Технологии
- Java 21
- Gradle
- Hibernate ORM
- MySQL
- Jakarta Validation (Hibernate Validator)
- Log4j2

## 3) Конфигурация на база данни
Настройките са в `src/main/resources/hibernate.properties`:
- `hibernate.connection.url`
- `hibernate.connection.username`
- `hibernate.connection.password`

## 4) Домейн модел
Основните ентити класове са в `src/main/java/org/nbu/transport/entity/`:
- `TransportCompany` (име, приходи)
- `Employee` (име, квалификация, заплата, компания)
- `Vehicle` (тип, регистрационен номер, компания)
- `Client` (име, телефон, email)
- `Shipment` (начална/крайна точка, дати, вид товар, цена, paid, връзки към company/client/vehicle/driver)

## 5) Функционалности (покритие на изискванията)
- CRUD операции (create/read/update/delete) за `TransportCompany`, `Employee`, `Client`, `Vehicle`, `Shipment`.
- Маркиране на превоз като платен/неплатен:
  - `ShipmentDao.getUnpaidShipments()`, `ShipmentDao.getPaidShipments()`, `ShipmentDao.markShipmentAsPaid(...)`.
- Сортиране/филтриране:
  - компании по име/приходи, служители по квалификация/заплата, превози по дестинация и др. (DAO + service слой).
- Експорт/импорт на превози:
  - `TransportService.exportShipmentsToFile(...)` създава `shipments/<име>.txt` и `shipments/<име>.ser`.
  - `TransportService.importShipmentsFromFile(...)` зарежда от `shipments/<име>.ser`.
- Справки:
  - общ брой превози, общ приход, приход по компания, брой/приход по шофьор, приход за период.

## 6) Архитектура
- DAO слой: `src/main/java/org/nbu/transport/dao/*Dao.java` (static CRUD + заявки)
- Service слой: `src/main/java/org/nbu/transport/service/TransportService.java` (валидация и бизнес логика)
- Hibernate конфигурация: `src/main/java/org/nbu/transport/configuration/SessionFactoryUtil.java`

## 7) Валидация
Валидиране на входните данни чрез:
- Bean Validation анотации върху ентити класовете (например `@NotBlank`, `@Size`, `@Pattern`, `@Positive`).
- Допълнителни проверки в service слоя.

## 8) Стартиране
Стартирайте `org.nbu.transport.app.Main` (например от IDE). Приложението изпълнява демонстрационен сценарий за CRUD, DTO операции, сортиране/филтриране, справки и файлови операции.
