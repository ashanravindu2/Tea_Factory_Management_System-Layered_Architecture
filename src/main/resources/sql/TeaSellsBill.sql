Order Bill
SELECT
    o.unitPrice,
    o.quantity,
    o.total,
    o.orderId,
    t.teaTypeName,
    (SELECT cusName FROM customer WHERE cusId='C001')AS CUSNAME,
    (SELECT SUM(total) FROM orders WHERE cusId = 'C001' AND time='19:00:31') AS total_orders
FROM orders o
    JOIN tea_Types t ON o.teaTypeId = t.teaTypeId
WHERE o.cusId = 'C001' AND o.time = '19:00:31';



SELECT payment.*, suppliers.suppName
FROM payment
         JOIN suppliers ON payment.EmpId = suppliers.supplierId
WHERE payment.transctionId = 'T001';


supplier bill
SELECT
    p.transctionId,
    p.EmpId,
    p.netWeight,
    p.fertilizerReduced,
    p.netTotal,
    s.suppName,
    t.supplierId,
    t.teaColecId,
    t.grosWeight,
    t.waterCon,
    t.netWeight AS TeaLeafNetWeight,
    t.date
FROM payment AS p
         JOIN suppliers AS s ON p.EmpId = s.supplierId
         JOIN tea_leafentry AS t ON p.EmpId = t.supplierId
WHERE p.transctionId = 'T013'
  AND t.date BETWEEN ' 2023-11-22 ' AND ' 2023-11-23 '

attendence get
SELECT *
FROM empattendence
WHERE EmpAttenId = 'Lakmal' AND DATE = '2023-11-23' AND attMark =1;


SELECT orders.unitPrice, orders.quantity, orders.total,orders.orderId,tea_types.teaTypeName
FROM orders
         JOIN tea_types ON orders.teaTypeId = tea_types.teaTypeId
WHERE orders.cusId = 'C002' AND orders.time = ' 18:03:10';

SELECT o.unitPrice, o.quantity, o.total, t.teaTypeName
FROM orders o
         JOIN tea_Types t ON o.teaTypeId = t.teaTypeId
WHERE o.cusId = 'C002' AND o.time = '18:03:10';

SELECT SUM(total) AS total_orders
FROM orders
WHERE cusId = 'C002';

SELECT
    o.unitPrice,
    o.quantity,
    o.total,
    o.orderId,
    t.teaTypeName,
    (SELECT cusName FROM customer WHERE cusId='C001')AS CUSNAME,
    (SELECT SUM(total) FROM orders WHERE cusId = 'C001' AND time='19:00:31') AS total_orders
FROM orders o
    JOIN tea_Types t ON o.teaTypeId = t.teaTypeId
WHERE o.cusId = 'C001' AND o.time = '19:00:31';


SELECT SUM(netWeight) AS NETWEIGHT
FROM tea_leafEntry
WHERE supplierId = 'S002'
  AND DATE(date) BETWEEN '2023-11-22' AND '2023-11-22';




SELECT payment.transctionId,payment.EmpId,payment.netWeight,payment.fertilizerReduced,payment.netTotal, suppliers.suppName
FROM payment
         JOIN suppliers ON payment.EmpId = suppliers.supplierId
WHERE payment.transctionId = 'T001';



SELECT
    p.transctionId,
    p.EmpId,
    p.netWeight,
    p.fertilizerReduced,
    p.netTotal,
    s.suppName,
    t.supplierId,
    t.teaColecId,
    t.grosWeight,
    t.waterCon,
    t.netWeight AS TeaLeafNetWeight,
    t.date
FROM payment AS p
         JOIN suppliers AS s ON p.EmpId = s.supplierId
         JOIN tea_leafentry AS t ON p.EmpId = t.supplierId
WHERE p.transctionId = 'T001'
  AND t.date BETWEEN ' 2023-11-22 ' AND ' 2023-11-23 '


SELECT T.transctionId,T.transactionDate, T.netTotal, T.netWeight, E.suppName
FROM payment T
         JOIN suppliers E ON T.EmpId = E.supplierId
WHERE T.EmpId LIKE 'S%'


SELECT *
FROM empattendence
WHERE EmpAttenId = 'Lakmal' AND DATE = '2023-11-23' AND attMark =1;

SELECT SUM(grosWeight),SUM(waterCon),SUM(netWeight),date,supplierId,teaColecId FROM tea_leafentry WHERE supplierId = 'S003' AND date '2023-11-27';

SELECT
    SUM(grosWeight) AS totalGrosWeight,
    SUM(waterCon) AS totalWaterCon,
    SUM(netWeight) AS totalNetWeight,
    tea_leafentry.date AS date,
    tea_leafentry.supplierId,
    tea_leafentry.teaColecId

FROM
    tea_leafentry
WHERE
    tea_leafentry.supplierId = 'S003'
  AND DATE(tea_leafentry.date) = '2023-11-27'
GROUP BY
    tea_leafentry.supplierId,
    tea_leafentry.teaColecId,
    tea_leafentry.date;

SELECT SUM(grosWeight) AS totalGrosWeight, SUM(waterCon) AS totalWaterCon, SUM(netWeight) AS totalNetWeight, tea_leafentry.supplierId, tea_leafentry.teaColecId tea_leafentry.date FROM tea_leafentry WHERE tea_leafentry.supplierId = ? AND DATE(tea_leafentry.date) = ? GROUP BY tea_leafentry.supplierId, tea_leafentry.teaColecId,tea_leafentry.date


drop database tfms;
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

    orderId VARCHAR(25) PRIMARY KEY NOT NULL,
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
    fertilizerReduced DOUBLE(10,2)

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

CREATE TABLE IF NOT EXISTS logHistoty(
    userId VARCHAR(5),
    logOutTime time,
    logInTime  time
    );

CREATE TABLE IF NOT EXISTS empAttendence(
    EmpAttenId VARCHAR(50) NOT NULL ,
    name VARCHAR(25),
    attMark INT(1)NOT NULL,
    date date NOT NULL,
    time time NOT NULL,
    CONSTRAINT foreign key (EmpAttenId) references workers(workId)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT foreign key (EmpAttenId) references collector(teaColecId)
    ON DELETE CASCADE ON UPDATE CASCADE

    );

CREATE TABLE IF NOT EXISTS warehouse (
    wareHouseId VARCHAR(25) PRIMARY KEY,
    wareHouseName VARCHAR(25)NOT NULL,
    stockCount DOUBLE NOT NULL


    );

insert into warehouse(wareHouseId,wareHouseName,stockCount) values ("W001","TPStock",0);

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




CREATE TABLE IF NOT EXISTS contains (
    orderId VARCHAR(25) NOT NULL ,
    teaTypeId VARCHAR(25) NOT NULL ,
    date date,
    CONSTRAINT foreign key (orderId) references orders(orderId)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT foreign key (teaTypeId) references orders(teaTypeId)
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


select SUM(attMark)='1' FROM empAttendence WHERE EmpAttenId = 'W002' AND DATE(date) BETWEEN ' 2023-11-26'AND'2323-23-23';

SELECT P.transctionId,P.transactionDate, P.netTotal, P.workCount,P.extraSalary ,W.workName FROM payment P JOIN workers W ON P.EmpId = W.workId WHERE P.EmpId LIKE 'W%';


select SUM(attMark)=1 AS COUNT FROM empAttendence WHERE EmpAttenId = 'W001' AND DATE(date) BETWEEN '2023-11-26'AND'2023-11-28';

select SUM(useTea) AS COUNT FROM teepowder;

SELECT o.unitPrice,
       o.quantity,
       o.total,
       o.orderId,
       t.teaTypeName,
       (SELECT cusName FROM customer WHERE cusId='C001')AS CUSNAME,
       (SELECT SUM(total) FROM orders WHERE cusId = 'C001' AND orderId='O008') AS total_orders
FROM orders o
         JOIN tea_Types t ON o.teaTypeId = t.teaTypeId
WHERE o.cusId ='C001' AND o.orderId = 'O008';

select COUNT(barrelId)AS COUNT,SUM(bLeter) AS LETER FROM matirials;

SELECT WoodId FROM matirials ORDER BY WoodId DESC LIMIT 1;

SELECT matirials.barrelId, wCategory, wWeight
FROM matirials
WHERE  wCategory IS NOT NULL
  AND wWeight IS NOT NULL;

SELECT matirials.barrelId, bCategory, bLeter
FROM matirials
WHERE  bCategory IS NOT NULL
  AND bLeter IS NOT NULL;


DELETE wCategory,wWeight FROM matirials WHERE barrelId ='B001';


select COUNT(EmpAttenId LIKE 'W%') AS WORKCOUNT , COUNT(EmpAttenId LIKE 'K%') AS COLEC FROM empattendence WHERE DATE(date) = '2023-12-01' AND  attMark = 1;

SELECT
    SUM(CASE WHEN EmpAttenId LIKE 'W%' THEN 1 ELSE 0 END) AS WORKCOUNT,
    SUM(CASE WHEN EmpAttenId LIKE 'K%' THEN 1 ELSE 0 END) AS COLEC
FROM
    empattendence
WHERE
    DATE(date) = '2023-12-01'
  AND attMark = 1;
