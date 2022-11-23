DROP TABLE IF EXISTS TransactionReviews;
DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Companies;

CREATE TABLE IF NOT EXISTS Employees (
    SSN varchar(10) primary key,
    first_name varchar(128),
    last_name varchar(128) not null,
    company varchar(32) not null,
    job_title varchar(128) not null,
    salary FLOAT,
    phone_number varchar(128) not null,
    email varchar(128) not null,
    start_date DATE,
    vacation_days_used INTEGER,
    sick_days_used INTEGER
);

CREATE TABLE IF NOT EXISTS Transactions (
    ID SERIAL PRIMARY KEY,
    SSN varchar(10),
    status VARCHAR(50),
    clock_in TIMESTAMP WITH TIME ZONE,
    clock_out TIMESTAMP WITH TIME ZONE,
    -- Calculates work duration
    duration INTERVAL GENERATED ALWAYS AS ( clock_out - clock_in ) STORED,
    finished BOOLEAN
);

CREATE TABLE IF NOT EXISTS TransactionReviews (
    ID SERIAL PRIMARY KEY REFERENCES Transactions(ID),
    SSN VARCHAR(10),
    status VARCHAR(50),
    changed_clock_in TIMESTAMP WITH TIME ZONE,
    changed_clock_out TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS Companies (
    SSN varchar(10) primary key,
    company_name varchar(32) NOT NULL,
    company_address varchar(128) NOT NULL,
    company_manager varchar(128) NOT NULL,
    phone_number varchar(128)
);

CREATE TABLE IF NOT EXISTS Users (
    SSN varchar(10) PRIMARY KEY,
    account_type INTEGER,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    constraint valid_type check (account_type < 9)
);

CREATE TABLE IF NOT EXISTS DrivingLog (
    ID SERIAL PRIMARY KEY,
    SSN varchar(10),
    dags date,
    licence_plate VARCHAR(50),
    odometer_start INT,
    odometer_end INT,
    /* REDUNDANT distance_driven INT GENERATED ALWAYS AS ( odometer_end - odometer_start) STORED, */
    cp_km FLOAT
);

CREATE TABLE IF NOT EXISTS Deviations (
    ID SERIAL PRIMARY KEY,
    SSN varchar(10),
    comment varchar(100),
    type varchar(5),
    start_date date,
    end_date date
);

/*INSERT INTO users (username, password) VALUES ('admin', '$2a$11$pgj3.zySyFOvIQEpD7W6Aund1Tw.BFarXxgLJxLbrzIv/4Nteisii');*/