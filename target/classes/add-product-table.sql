

CREATE TABLE Products (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    price integer not null,
    description VARCHAR(255) ,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

--CREATE TABLE Avis_Product (
--    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
--    product_id INTEGER NOT NULL,
--    avis_id INTEGER NOT NULL,
--    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
--    CONSTRAINT avis_id_fk FOREIGN KEY (avis_id) REFERENCES Avis(id),
--    CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES Products(id)
--);


    ALTER TABLE Products
    ADD COLUMN name VARCHAR(100) NOT NULL;

    ALTER TABLE Avis
    ADD COLUMN product_id INTEGER NOT NULL,
    ADD CONSTRAINT product_id_avis_fk FOREIGN KEY (product_id) REFERENCES Products(id);


--CONSTRAINT client_fk foreign key (client_id) REFERENCES Clients(id)

DROP TABLE Avis_Product;