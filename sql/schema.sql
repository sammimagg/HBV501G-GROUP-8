CREATE TABLE IF NOT EXISTS Employees (
    SSN varchar(10) primary key,
    firstName varchar(128),
    lastName varchar(128) not null,
    company varchar(32) not null,
    jobTitle varchar(128) not null,
    salary FLOAT,
    phoneNumber varchar(128) not null
);

CREATE TABLE IF NOT EXISTS Transactions (
    ID SERIAL PRIMARY KEY,
    SSN varchar(10),
    clock_in TIMESTAMP WITH TIME ZONE,
    clock_out TIMESTAMP WITH TIME ZONE,
    finished BOOLEAN
);

CREATE TABLE IF NOT EXISTS Companies (
    SSN varchar(10) primary key,
    companyName varchar(32) NOT NULL,
    companyAddress varchar(128) NOT NULL,
    companyManager varchar(128) NOT NULL,
    phoneNumber varchar(128)
);

CREATE TABLE IF NOT EXISTS Users (
    SSN varchar(10) PRIMARY KEY,
    accountType INTEGER,
    userName varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    constraint valid_type check (accountType < 9)
);

/*INSERT INTO users (username, password) VALUES ('admin', '$2a$11$pgj3.zySyFOvIQEpD7W6Aund1Tw.BFarXxgLJxLbrzIv/4Nteisii');*/