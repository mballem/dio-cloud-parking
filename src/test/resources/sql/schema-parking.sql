 drop table if exists parkings cascade;
 create table if not exists parkings (
     id_parking varchar(36) not null,
     license varchar(8),
     model varchar(50),
     state varchar(2),
     color varchar(50),
     entry_date timestamp,
     exit_date timestamp,
     bill double,
     primary key (id_parking)
 );