CREATE TABLE IF NOT EXISTS CLIENT (
    ID VARCHAR(255) not null primary key,
    EMAIL VARCHAR(255) not null unique,
    PASSWORD VARCHAR(100) not null,
    NAME VARCHAR(100) not null,
    TOKEN VARCHAR(255) not null,
    IS_ACTIVE VARCHAR(20) not null,
    CREATED datetime not null,
    MODIFIED datetime not null,
    LAST_LOGIN datetime not null
);

CREATE TABLE IF NOT EXISTS PHONE (
    ID VARCHAR(255) not null primary key,
    PHONE_NUMBER VARCHAR(100)  null,
    CITY_CODE VARCHAR(100)  null,
    COUNTRY_CODE VARCHAR(100)  null,
    CLIENT_ID VARCHAR(255) not null,
    FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT(ID)
);

