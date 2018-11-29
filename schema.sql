
    create table Admin (
        id bigint not null auto_increment,
        password varchar(255),
        username varchar(255),
        primary key (id)
    )

    create table Categoria (
        id bigint not null auto_increment,
        cargoFijo double precision not null,
        cargoVariable double precision not null,
        consumoMaximo double precision not null,
        consumoMinimo double precision not null,
        nombre varchar(255) not null,
        primary key (id)
    )

    create table Cliente (
        id bigint not null auto_increment,
        ahorroAutomatico bit not null,
        apellido varchar(255) not null,
        domicilio varchar(255),
        fechaAlta date,
        nombre varchar(255) not null,
        nroDocumento bigint not null,
        password varchar(255),
        puntos double precision not null,
        telefono bigint not null,
        tipoDocumento varchar(255) not null,
        ubicacion varchar(255) not null,
        username varchar(255),
        idCategoria bigint,
        primary key (id)
    )

    create table CondicionSobreSensor (
        tipo varchar(31) not null,
        id bigint not null auto_increment,
        factorDeComparacion double precision,
        idSensor bigint not null,
        idRegla bigint,
        primary key (id)
    )

    create table Dispositivo (
        id bigint not null auto_increment,
        kwPorHora double precision not null,
        nombre varchar(255) not null,
        tipoDispositivo bigint not null,
        idCliente bigint,
        primary key (id)
    )

    create table HistorialConsumos (
        id bigint not null auto_increment,
        consumo double precision not null,
        fecha datetime not null,
        idDispositivoInteligente bigint,
        primary key (id)
    )

    create table Regla (
        tipo varchar(31) not null,
        id bigint not null auto_increment,
        idDispositivo bigint not null,
        primary key (id)
    )

    create table Regla_actuadores (
        Regla_id bigint not null,
        actuador integer not null,
        primary key (Regla_id, actuador)
    )

    create table RestriccionUsoDispositivo (
        id bigint not null auto_increment,
        actuadorAlExcederse integer,
        usoMensualMaximo double precision not null,
        usoMensualMinimo double precision not null,
        dispositivo_id bigint,
        primary key (id)
    )

    create table Sensor (
        tipo varchar(31) not null,
        id bigint not null auto_increment,
        idDispositivo bigint,
        primary key (id)
    )

    create table TipoDispositivo (
        tipo varchar(31) not null,
        id bigint not null auto_increment,
        dispositivoConcreto varchar(255),
        primary key (id)
    )

    create table Transformador (
        id bigint not null,
        ubicacion varchar(255) not null,
        primary key (id)
    )

    create table Zona (
        id bigint not null,
        ubicacion varchar(255) not null,
        radio double precision not null,
        primary key (id)
    )

    alter table Categoria 
        add constraint UK_g22h6awq25eaqohosg6icwj8b  unique (nombre)

    alter table Cliente 
        add constraint UK_g7aer5ydwwrpt04p5pt90bh9n  unique (tipoDocumento, nroDocumento)

    alter table Dispositivo 
        add constraint UK_eyvhvt7h09v5l2em8xxwtwmwe  unique (tipoDispositivo)

    alter table Cliente 
        add constraint FK_ocyljh04ut8y4ocrk7ocv91bx 
        foreign key (idCategoria) 
        references Categoria (id)

    alter table CondicionSobreSensor 
        add constraint FK_h5tkigb9y5ckvnnx5tcpgcamd 
        foreign key (idSensor) 
        references Sensor (id)

    alter table CondicionSobreSensor 
        add constraint FK_9rna43b6se79ta1gj6kbo0wt9 
        foreign key (idRegla) 
        references Regla (id)

    alter table Dispositivo 
        add constraint FK_eyvhvt7h09v5l2em8xxwtwmwe 
        foreign key (tipoDispositivo) 
        references TipoDispositivo (id)

    alter table Dispositivo 
        add constraint FK_q57uofk60oah5ncey1wrevdf9 
        foreign key (idCliente) 
        references Cliente (id)

    alter table HistorialConsumos 
        add constraint FK_atfxo9v0pl5iubfj7cmu1cif 
        foreign key (idDispositivoInteligente) 
        references TipoDispositivo (id)

    alter table Regla 
        add constraint FK_iuis018asthb5yuq7wugubn3v 
        foreign key (idDispositivo) 
        references Dispositivo (id)

    alter table Regla_actuadores 
        add constraint FK_6qdfup6vmj77vylm1nyebcfng 
        foreign key (Regla_id) 
        references Regla (id)

    alter table RestriccionUsoDispositivo 
        add constraint FK_bk3jtvhm5dcaidx2yxhcbi396 
        foreign key (dispositivo_id) 
        references Dispositivo (id)

    alter table Sensor 
        add constraint FK_1cmni0r71s91jpq2bide40os4 
        foreign key (idDispositivo) 
        references Dispositivo (id)

    create table hibernate_sequences (
         sequence_name varchar(255),
         sequence_next_hi_value integer 
    )
