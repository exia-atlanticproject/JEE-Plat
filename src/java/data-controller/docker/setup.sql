create table Metrics
(
    id    int auto_increment
        primary key,
    value int not null
);

create table Users
(
    id      int auto_increment
        primary key,
    name    varchar(50) not null,
    surname varchar(50) not null,
    email   varchar(50) not null
);

create table Devices
(
    id          int auto_increment
        primary key,
    model       varchar(50) not null,
    mac_address varchar(50) not null,
    date        date        not null,
    id_Metrics  int         not null,
    id_Users    int         not null,
    constraint Devices_Metrics_FK
        foreign key (id_Metrics) references Metrics (id),
    constraint Devices_Users0_FK
        foreign key (id_Users) references Users (id)
);

