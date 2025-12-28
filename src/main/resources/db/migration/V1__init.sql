-- 1. Таблица permissions
CREATE TABLE t_permissions (
                               id BIGSERIAL PRIMARY KEY,
                               permission VARCHAR(100) NOT NULL
);

-- 2. Таблица departments
CREATE TABLE departments (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(100),
                             department VARCHAR(100)
);

-- 3. Таблица users
CREATE TABLE t_users (
                         id BIGSERIAL PRIMARY KEY,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         full_name VARCHAR(150)
);

-- 4. Связующая таблица user_permissions
CREATE TABLE t_user_permissions (
                                    user_id BIGINT NOT NULL,
                                    permission_id BIGINT NOT NULL,
                                    PRIMARY KEY (user_id, permission_id),
                                    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES t_users(id),
                                    CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES t_permissions(id)
);

-- 5. Таблица doctor_departments
CREATE TABLE doctor_departments (
                                    id BIGSERIAL PRIMARY KEY,
                                    doctor_id BIGINT NOT NULL,
                                    department_id BIGINT NOT NULL,
                                    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES t_users(id),
                                    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- 6. Таблица appointments
CREATE TABLE appointments (
                              id BIGSERIAL PRIMARY KEY,
                              patient_id BIGINT NOT NULL,
                              doctor_id BIGINT NOT NULL,
                              appointment_date DATE,
                              status VARCHAR(50),
                              notes TEXT,
                              CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES t_users(id),
                              CONSTRAINT fk_doctor_appointment FOREIGN KEY (doctor_id) REFERENCES t_users(id)
);

-- 7. Таблица medical_records
CREATE TABLE medical_records (
                                 id BIGSERIAL PRIMARY KEY,
                                 patient_id BIGINT NOT NULL,
                                 doctor_id BIGINT NOT NULL,
                                 diagnosis TEXT,
                                 treatment TEXT,
                                 record_date TIMESTAMP,
                                 CONSTRAINT fk_patient_record FOREIGN KEY (patient_id) REFERENCES t_users(id),
                                 CONSTRAINT fk_doctor_record FOREIGN KEY (doctor_id) REFERENCES t_users(id)
);

-- 8. Таблица payments
CREATE TABLE payments (
                          id BIGSERIAL PRIMARY KEY,
                          patient_id BIGINT NOT NULL,
                          appointment_id BIGINT NOT NULL,
                          amount DECIMAL(10,2),
                          method VARCHAR(50),
                          status VARCHAR(50),
                          CONSTRAINT fk_patient_payment FOREIGN KEY (patient_id) REFERENCES t_users(id),
                          CONSTRAINT fk_appointment_payment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
