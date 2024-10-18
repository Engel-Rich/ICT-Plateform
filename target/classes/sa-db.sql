CREATE DATABASE  IF NOT EXISTS SA COLLATE 'utf8mb3_german2_ci';

CREATE table Clients(
    id integer primary key not null auto_increment ,
    email varchar(70) not null unique,

    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE table Avis (
    id integer primary key not null auto_increment ,
    text varchar(100),
    type varchar(10),
    client_id integer NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT client_fk foreign key (client_id) REFERENCES Clients(id)
);

ALTER TABLE Clients
ADD COLUMN name varchar(150) not null unique;

ALTER TABLE Clients
ADD COLUMN age integer not null ;