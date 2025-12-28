Clinic Management System

A Java Spring Boot application for managing clinic operations, including users, departments, appointments, medical records, and payments. The system supports role-based access control, DTO-based data transfer, and can be run via Docker or directly through an IDE. All services are covered by unit tests using JUnit and Mockito.

Technologies

Java 17

Spring Boot

PostgreSQL

Flyway (database migrations)

Docker (optional)

Spring Security (role-based access)

JPA / Hibernate

Lombok

JUnit + Mockito (unit testing)

Postman (API testing)

Project Overview

The system manages clinic operations with three main roles:

ADMIN — full access, including user management, appointments, medical records, and payments.

DOCTOR — can manage medical records and view their appointments.

PATIENT — can book appointments, view medical records, and make payments.

Main Entities:

t_users — application users

t_permissions — roles (ADMIN, DOCTOR, PATIENT)

t_user_permissions — user-role mapping

departments — clinic departments

doctor_departments — doctors assigned to departments

appointments — appointment records

medical_records — medical records

payments — payment information

Architecture

The project follows a layered architecture:

Controller → Service → DTO → Repository → Entity


Controller: Handles REST API requests.

Service: Contains business logic for appointments, medical records, payments, and users.

DTO (Data Transfer Objects): Transfer data between layers; separate internal entities from API exposure.

Repository: Database access via Spring Data JPA.

Entity: Represents database tables in the application.

Example DTOs:

UserRequestDto / UserResponseDto

AppointmentRequestDto / AppointmentResponseDto

MedicalRecordRequestDto / MedicalRecordResponseDto

DepartmentRequestDto / DepartmentResponseDto

PaymentRequestDto / PaymentResponseDto

Service Methods Overview
UserService

signUp(UserRequestDto)

updateProfile(UserRequestDto)

changePassword(ChangePasswordRequestDto)

createUserByAdmin(AdminCreateUserRequestDto)

assignRoleByAdmin(Long userId, Long roleId)

removeRoleByAdmin(Long userId, Long roleId)

getAllUsers()

AppointmentService

createAppointment(AppointmentRequestDto)

updateStatus(Long id, String status)

getMyAppointments()

getAppointmentsByDoctor()

getAllAppointments()

DepartmentService

createDepartment(DepartmentRequestDto)

assignDoctorToDepartment(Long doctorId, Long departmentId)

getMyDepartments()

getAllDepartments()

MedicalRecordService

createMedRecord(MedicalRecordRequestDto)

updateRecord(MedicalRecordRequestDto)

getMyMedicalRecords()

getMedicalRecordsByDoctor()

getAllMedicalRecords()

PaymentService

createPayment(PaymentRequestDto)

updateStatus(Long id, String status)

getMyPayments()

getAllPayments()

Roles and API Endpoints
Endpoint	Role	Description
/registration	All	User registration
/change-password	Authenticated	Change password
/change-profile	Authenticated	Update profile
/admin/**	ADMIN	Admin-only actions
/get-my-appointments	PATIENT	View patient appointments
/get-doctor-appointments	DOCTOR	View doctor's appointments
/get-all-appointments	ADMIN	View all appointments
/get-my-payments	PATIENT	View own payments
/get-all-payments	ADMIN	View all payments
/get-my-medRecords	PATIENT	View own medical records
/get-doctor-medRecords	DOCTOR	View doctor's patients' medical records
/get-all-medRecords	ADMIN	View all medical records
Running the Application
1. Using Docker
   docker run --name my-postgresql-database-container \
   -e POSTGRES_DB=clinic \
   -e POSTGRES_USER=postgres \
   -e POSTGRES_PASSWORD=aruzhann \
   -p 2345:5432 \
   -d postgres

# Build and run the application
docker build -t clinic-app .
docker run -p 8080:8080 clinic-app

2. Using IDE (without Docker)

Set up PostgreSQL database:

Database: clinic

User: postgres

Port: 2345

Configure application.properties:

spring.application.name=final
spring.datasource.url=jdbc:postgresql://localhost:2345/clinic
spring.datasource.username=postgres
spring.datasource.password=aruzhann
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration


Run the application via IDE (main class).

Database Migrations

All tables are created using Flyway in db/migration.

Example initial data for roles:

INSERT INTO t_permissions (permission) VALUES ('ADMIN');
INSERT INTO t_permissions (permission) VALUES ('DOCTOR');
INSERT INTO t_permissions (permission) VALUES ('PATIENT');

Postman

Import postman_collection.json from the repository to test all endpoints.

Unit Testing

Each service is covered by unit tests using JUnit and Mockito.

Tests include scenarios for:

Retrieving data

Saving data

Business logic processing

Example test structure:

src/test/java/com/example/demo/services/
├─ UserServiceTest.java
├─ AppointmentServiceTest.java
├─ DepartmentServiceTest.java
├─ MedicalRecordServiceTest.java
└─ PaymentServiceTest.java