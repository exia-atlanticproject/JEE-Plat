#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Users
#------------------------------------------------------------

CREATE TABLE Users(
                      id      Int  Auto_increment  NOT NULL ,
                      uid     Varchar (50) NOT NULL ,
                      name    Varchar (50) NOT NULL ,
                      surname Varchar (50) NOT NULL ,
                      email   Varchar (50) NOT NULL,
                      role    ENUM('ADMIN', 'CLIENT') DEFAULT 'CLIENT' NOT NULL
    ,CONSTRAINT Users_PK PRIMARY KEY (id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Devices
#------------------------------------------------------------

CREATE TABLE Devices(
                        id          Int  Auto_increment  NOT NULL ,
                        model       Varchar (50) NOT NULL ,
                        mac_address Varchar (50) ,
                        uid         VARCHAR (50) NOT NULL,
                        name         VARCHAR (200),
                        id_Users    Int
    ,CONSTRAINT Devices_PK PRIMARY KEY (id)

    ,CONSTRAINT Devices_Users_FK FOREIGN KEY (id_Users) REFERENCES Users(id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Metrics
#------------------------------------------------------------

CREATE TABLE Metrics(
                        id         Int  Auto_increment  NOT NULL ,
                        value      Double NOT NULL ,
                        date       DATETIME NOT NULL ,
                        id_Devices Int NOT NULL
    ,CONSTRAINT Metrics_PK PRIMARY KEY (id)

    ,CONSTRAINT Metrics_Devices_FK FOREIGN KEY (id_Devices) REFERENCES Devices(id)
)ENGINE=InnoDB;