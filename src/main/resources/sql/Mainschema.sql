
create database tfms;
use tfms;

CREATE TABLE IF NOT EXISTS suppliers(
                                        supplierId VARCHAR(25) PRIMARY KEY,
                                        suppName VARCHAR(25) NOT NULL,
                                        suppAddres VARCHAR(50)  NOT NULL,
                                        suppTele VARCHAR(11) NOT NULL,
                                        suppGen VARCHAR(10)NOT NULL
);

CREATE TABLE IF NOT EXISTS workers(
                                      workId VARCHAR(50) PRIMARY KEY,
                                      workName VARCHAR(25)NOT NULL,
                                      workAdress VARCHAR(25)NOT NULL,
                                      workAge INT(3)NOT NULL,
                                      workTele VARCHAR(11)NOT NULL,
                                      workGen VARCHAR(6)NOT NULL,
                                      workJoin date NOT NULL,
                                      workBirth date NOT NULL
);

CREATE TABLE IF NOT EXISTS collector(
                                        teaColecId VARCHAR(50)PRIMARY KEY,
                                        Name VARCHAR(25) NOT NULL,
                                        Address VARCHAR(25)NOT NULL,
                                        Telephone VARCHAR(11)NOT NULL,
                                        Gender VARCHAR(6)NOT NULL
);

CREATE TABLE IF NOT EXISTS tea_leafEntry(
                                            supplierId VARCHAR(25) NOT NULL ,
                                            teaColecId VARCHAR(50),
                                            grosWeight DECIMAL(10,2)NOT NULL,
                                            waterCon DECIMAL(10,2) DEFAULT '0',
                                            netWeight DECIMAL(10,2),
                                            date date NOT NULL,
                                            CONSTRAINT foreign key (supplierId) references suppliers(supplierId)
                                                ON DELETE CASCADE ON UPDATE CASCADE,

                                            CONSTRAINT foreign key (teaColecId ) references collector(teaColecId )
                                                ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS tea_Types(
                                        teaTypeId VARCHAR(25) PRIMARY KEY,
                                        teaTypeName VARCHAR(25) NOT NULL,
                                        teaTypeDesc VARCHAR(50) NOT NULL,
                                        teaPerPrice DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS customer(
                                       cusId VARCHAR(10) PRIMARY KEY,
                                       cusName VARCHAR(25),
                                       cusTele VARCHAR(10)NOT NULL,
                                       cusAddress VARCHAR(25)

);

CREATE TABLE IF NOT EXISTS orders(

                                     orderId VARCHAR(25)NOT NULL,
                                     cusId VARCHAR(10)NOT NULL,
                                     date date NOT NULL,
                                     unitPrice DOUBLE(10,2) NOT NULL,
                                     teaTypeId VARCHAR(25)NOT NULL ,
                                     quantity decimal(10,0) NOT NULL,
                                     total decimal(10)NOT NULL,
                                     time VARCHAR(10),

                                     CONSTRAINT foreign key (cusId) references customer(cusId)
                                         ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT foreign key (teaTypeId) references tea_Types(teaTypeId)
                                         ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS payment(
                                      transctionId VARCHAR(25) PRIMARY KEY,
                                      EmpId VARCHAR(25) ,
                                      startTime date NOT NULL,
                                      endDate date NOT NULL,
                                      transactionDate date NOT NULL,
                                      netTotal DOUBLE(10,2) NOT NULL,
                                      netWeight DOUBLE(10,2),
                                      fertilizerReduced DOUBLE(10,2),
                                      workCount INT(10),
                                      extraSalary DOUBLE(10,2)

);

CREATE TABLE IF NOT EXISTS user(
                                   userId VARCHAR(5) PRIMARY KEY,
                                   userName VARCHAR(25) UNIQUE NOT NULL,
                                   userPass VARCHAR(10) UNIQUE NOT NULL,
                                   userTele VARCHAR(10),
                                   userAddress VARCHAR(25),
                                   name VARCHAR(25)NOT NULL,
                                   userEmail VARCHAR(25)UNIQUE NOT NULL

);

CREATE TABLE IF NOT EXISTS empAttendence(
                                            EmpAttenId VARCHAR(50),
                                            name VARCHAR(50),
                                            attMark INT(1)NOT NULL,
                                            date date NOT NULL,
                                            time time NOT NULL,
                                            workId VARCHAR(50),
                                            teaColecId VARCHAR(50),
                                            CONSTRAINT foreign key (workId) references workers(workId)
                                                ON DELETE CASCADE ON UPDATE CASCADE,
                                            CONSTRAINT foreign key (teaColecId) references collector(teaColecId)
                                                ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS warehouse (
                                         wareHouseId VARCHAR(25) PRIMARY KEY,
                                         wareHouseName VARCHAR(25)NOT NULL,
                                         stockCount DOUBLE NOT NULL


);

insert into warehouse(wareHouseId,wareHouseName,stockCount) values ('W001','TPStock',0);

CREATE TABLE IF NOT EXISTS storedetails(
                                           wareHouseId VARCHAR(25),
                                           teaTypeId VARCHAR(25)PRIMARY KEY NOT NULL ,
                                           date date NOT NULL,
                                           quantity DECIMAL(10,2) NOT NULL,
                                           CONSTRAINT foreign key (teaTypeId) references tea_Types(teaTypeId)
                                               ON DELETE CASCADE ON UPDATE CASCADE,
                                           CONSTRAINT foreign key (wareHouseId) references warehouse (wareHouseId )
                                               ON DELETE CASCADE ON UPDATE CASCADE

);



CREATE TABLE IF NOT EXISTS teaDetailSupplier(
                                                supplierId VARCHAR(25) NOT NULL ,
                                                wareHouseId VARCHAR(25) NOT NULL ,

                                                CONSTRAINT foreign key (wareHouseId) references warehouse(wareHouseId)
                                                    ON DELETE CASCADE ON UPDATE CASCADE,
                                                CONSTRAINT foreign key (supplierId) references suppliers(supplierId)
                                                    ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS teepowder(
                                        date date PRIMARY KEY NOT NULL ,
                                        useTea decimal (10,2) NOT NULL

);

CREATE TABLE IF NOT EXISTS matirials(
                                        barrelId VARCHAR(25),
                                        bCategory VARCHAR(25),
                                        bLeter DECIMAL(10,2),
                                        wCategory VARCHAR(25),
                                        wWeight DECIMAL(10,2)
);






