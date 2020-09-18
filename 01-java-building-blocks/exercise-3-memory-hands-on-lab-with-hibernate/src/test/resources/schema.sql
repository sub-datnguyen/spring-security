drop table if exists STUDENT cascade;
drop table if exists ADDRESS cascade;
drop table if exists STUDENT_AUDIT cascade;

create table ADDRESS (
                         ID   int         auto_increment primary key,
                         NAME VARCHAR(50)
);

create table STUDENT (
                         ID              int        auto_increment primary key,
                         NAME            varchar(50),
                         PASSPORT_NUMBER varchar(50),
                         ADDRESS_ID      int,
                         constraint ADDRESS_FK foreign key(ADDRESS_ID) references address(ID)
);

create unique index STUDENT_ADDRESS_ID_UINDEX on STUDENT (ADDRESS_ID);

create table STUDENT_AUDIT (
                         ID                 int        auto_increment primary key,
                         NAME               varchar(50),
                         PASSPORT_NUMBER    varchar(50),
                         ADDRESS            varchar(500),
                         PREVIOUS_CHANGE_ID int,
                         STUDENT_ID         int,
                         CREATED_DATE       datetime

);