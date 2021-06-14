CREATE TABLE ITEM
(
    ID                NUMBER PRIMARY KEY,
    NAME              NVARCHAR2(50)  NOT NULL,
    DATE_CREATED      DATE DEFAULT CURRENT_DATE,
    LAST_UPDATED_DATE DATE DEFAULT CURRENT_DATE,
    DESCRIPTION       NVARCHAR2(300) NOT NULL
);