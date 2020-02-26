drop table continet if exists;

drop table country if exists;

create table continet (
       continent varchar(255) not null,
        primary key (continent)
    );

create table country (
       name varchar(255) not null,
        flag varchar(255),
        continet_continent varchar(255),
        primary key (name)
    );

alter table country
       add constraint FK_1
       foreign key (continet_continent)
       references continet;