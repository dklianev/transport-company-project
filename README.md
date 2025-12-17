# Transport Company Management System

Приложение за обслужване на транспортна компания, разработено на Java с Hibernate ORM и MySQL база данни.

## Описание

Системата позволява на транспортни компании да управляват дейностите си, включително:
- Управление на МПС (превозни средства)
- Управление на служители (шофьори с различна квалификация)
- Управление на клиенти
- Регистриране и проследяване на превози
- Генериране на отчети и справки
- Експорт и импорт на данни за превози

## Технологии

- Java 21
- Hibernate ORM 7.1.0
- Hibernate Validator 9.1.0
- MySQL 8+
- Gradle build system
- JUnit 5 за тестване

## Структура на проекта

```
transport-company/
├── src/main/java/org/nbu/transport/
│   ├── entity/          # Entity класове с JPA анотации
│   ├── dao/              # Data Access Objects (with Criteria API)
│   ├── service/          # Бизнес логика и валидация
│   ├── configuration/    # Hibernate конфигурация
│   ├── exception/        # Custom exceptions
│   ├── util/             # Помощни класове
│   └── app/              # Main приложение
├── src/main/resources/
│   ├── hibernate.properties
│   └── log4j2.properties
├── sql/                  # SQL скриптове
└── build.gradle.kts
```

## База данни

### Схема

Базата данни съдържа следните таблици:
- `transport_company` - Транспортни компании
- `employee` - Служители (шофьори)
- `vehicle` - Превозни средства
- `client` - Клиенти
- `shipment` - Превози

### Инсталация на базата данни

1. Стартирайте MySQL сървър
2. Изпълнете SQL скриптовете в следния ред:
   ```bash
   mysql -u root -p < sql/01_create_database.sql
   mysql -u root -p < sql/02_create_tables.sql
   mysql -u root -p < sql/03_insert_sample_data.sql
   ```

3. Конфигурирайте connection settings в `src/main/resources/hibernate.properties`

## Функционалности

### 1. CRUD операции
- **Транспортни компании**: Създаване, редактиране, изтриване
- **Служители**: Управление на шофьори с квалификации и заплати
- **Превозни средства**: Регистриране на МПС
- **Клиенти**: Управление на клиентска база
- **Превози**: Регистриране на превози с дестинация, товар, цена

### 2. Сортиране и филтриране
- Компании по име и приходи
- Служители по квалификация и заплата
- Превози по дестинация

### 3. Управление на плащания
- Маркиране на превози като платени
- Проследяване на неплатени задължения

### 4. Файлови операции
- Експорт на данни за превози в текстов и сериализиран формат
- Импорт на данни от файлове

### 5. Справки и отчети
- Общ брой извършени превози
- Обща сума на превозите
- Брой превози на шофьор
- Приходи на шофьор
- Приходи на компания за период

## Използване

### Компилиране

```bash
gradlew.bat build
```

### Стартиране

```bash
java -cp build/classes/java/main org.nbu.transport.app.Main
```

### Примерен код

```java
// Създаване на компания
TransportService.createCompany("Fast Transport Ltd.", new BigDecimal("0.00"));

// Създаване на служител
TransportService.createEmployee("Ivan Petrov", "ADR - Dangerous Goods",
                               new BigDecimal("2200.00"), 1);

// Справки
Long totalShipments = TransportService.getTotalShipmentsCount();
BigDecimal totalRevenue = TransportService.getTotalRevenue();
Map<Integer, Integer> shipmentsPerDriver = TransportService.getShipmentsCountPerDriver();
```

## Валидация

Системата валидира всички входни данни:
- Празни/null стойности
- Отрицателни суми и заплати
- Невалидни дати (пристигане преди тръгване)
- Липсваща информация за товар/пътници според типа превоз

## Обработка на грешки

Използват се custom exceptions:
- `EntityNotFoundException` - Когато ентити не е намерен
- `ValidationException` - При невалидни данни
- `TransportException` - Базов exception клас

## Автор

Проект разработен за курс CSCB525 - Програмиране с Java
New Bulgarian University
