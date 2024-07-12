-- liquibase formatted sql

-- changeset antipin:1720762121480-1
CREATE SEQUENCE IF NOT EXISTS department_seq START WITH 100 INCREMENT BY 1;

-- changeset antipin:1720762121480-2
CREATE SEQUENCE IF NOT EXISTS employee_seq START WITH 100 INCREMENT BY 1;

-- changeset antipin:1720762121480-3
CREATE TABLE departments
(
    id   BIGINT       NOT NULL DEFAULT nextval('department_seq'),
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_departments PRIMARY KEY (id)
);

-- changeset antipin:1720762121480-4
CREATE TABLE employees
(
    id            BIGINT       NOT NULL DEFAULT nextval('employee_seq'),
    firstname     VARCHAR(255) NOT NULL,
    lastname      VARCHAR(255) NOT NULL,
    position      VARCHAR(255) NOT NULL,
    salary        DECIMAL,
    department_id BIGINT,
    CONSTRAINT pk_employees PRIMARY KEY (id)
);

-- changeset antipin:1720762121480-5
ALTER TABLE employees
    ADD CONSTRAINT fk_employees_on_department FOREIGN KEY (department_id) REFERENCES departments (id);

INSERT INTO departments(id, name)
VALUES (1, 'department 1'),
       (2, 'department 2');

INSERT INTO employees(id, firstname, lastname, position, salary, department_id)
VALUES (1, 'Firstname1', 'Lastname1', 'Position1', 1000.00, 1),
       (2, 'Firstname2', 'Lastname2', 'Position2', 2000.00, 2);
