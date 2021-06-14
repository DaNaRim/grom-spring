CREATE TABLE PLANE
(
    ID                   NUMBER PRIMARY KEY,
    MODEL                NVARCHAR2(50)        NOT NULL,
    CODE                 NVARCHAR2(20) UNIQUE NOT NULL,
    YEAR_PRODUCED        DATE                 NOT NULL,
    AVG_FUEL_CONSUMPTION NUMBER(*, 2)         NOT NULL
);