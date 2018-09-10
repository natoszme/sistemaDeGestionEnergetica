
    create table Categoria (
        id bigint generated by default as identity (start with 1),
        cargoFijo double not null,
        cargoVariable double not null,
        consumoMaximo double not null,
        consumoMinimo double not null,
        nombre varchar(255) not null,
        primary key (id)
    )

    create table Cliente (
        id bigint generated by default as identity (start with 1),
        ahorroAutomatico boolean not null,
        apellido varchar(255) not null,
        domicilio varchar(255),
        fechaAlta date,
        nombre varchar(255) not null,
        nroDocumento bigint not null,
        puntos double not null,
        telefono bigint not null,
        tipoDocumento varchar(255) not null,
        idCategoria bigint not null,
        primary key (id)
    )

    create table CondicionSobreSensor (
        tipo varchar(31) not null,
        id bigint generated by default as identity (start with 1),
        factorDeComparacion double,
        sensor_id bigint not null,
        idRegla bigint not null,
        primary key (id)
    )

    create table Dispositivo (
        id bigint generated by default as identity (start with 1),
        kwPorHora double not null,
        nombre varchar(255) not null,
        idCliente bigint not null,
        primary key (id)
    )

    create table Regla (
        tipo varchar(31) not null,
        id bigint generated by default as identity (start with 1),
        dispositivo_id bigint not null,
        primary key (id)
    )

    create table Sensor (
        tipo varchar(31) not null,
        id bigint generated by default as identity (start with 1),
        primary key (id)
    )

    create table TipoDispositivo (
        tipo varchar(31) not null,
        id bigint generated by default as identity (start with 1),
        dispositivoConcreto varchar(255),
        primary key (id)
    )

    alter table Categoria 
        add constraint UK_g22h6awq25eaqohosg6icwj8b  unique (nombre)

    alter table Cliente 
        add constraint UK_g7aer5ydwwrpt04p5pt90bh9n  unique (tipoDocumento, nroDocumento)

    alter table Cliente 
        add constraint FK_ocyljh04ut8y4ocrk7ocv91bx 
        foreign key (idCategoria) 
        references Categoria

    alter table CondicionSobreSensor 
        add constraint FK_63jph0ldb87wse7wf6t9pt185 
        foreign key (sensor_id) 
        references Sensor

    alter table CondicionSobreSensor 
        add constraint FK_9rna43b6se79ta1gj6kbo0wt9 
        foreign key (idRegla) 
        references Regla

    alter table Dispositivo 
        add constraint FK_q57uofk60oah5ncey1wrevdf9 
        foreign key (idCliente) 
        references Cliente

    alter table Regla 
        add constraint FK_jlmnuc7r42qhcrce381jeuofd 
        foreign key (dispositivo_id) 
        references Dispositivo
