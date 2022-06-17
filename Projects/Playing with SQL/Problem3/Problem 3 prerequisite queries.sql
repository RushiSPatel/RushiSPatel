use rspatel;
/*My changes for Assignment 5 - Problem 3*/

create table tax(`officeCode` VARCHAR(10) NOT NULL,
  `taxNumber` INT NOT NULL,
  `taxAmount` INT NULL,
  PRIMARY KEY (`officeCode`, `taxNumber`),
  CONSTRAINT `officeCode`
    FOREIGN KEY (`officeCode`)
    REFERENCES `rspatel`.`offices` (`officeCode`));

create table promotion (`officeCode` VARCHAR(10) NOT NULL,
  `promotionAmount` INT NULL,
  `promotionCode` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`officeCode`, `promotionCode`)
 );

create table invoice (`orderNumber` INT(11) NOT NULL,
  `totalValue` VARCHAR(45) NULL,
  `taxAmount` VARCHAR(45) default 0,
  `promotionAmount` VARCHAR(45) default 0,
  `shippingAmount` VARCHAR(45) default 0,
  `finalValue` VARCHAR(45) NULL,
  PRIMARY KEY (`orderNumber`));

create table shipper (`shipperID` VARCHAR(10) NOT NULL,
 `company_name` VARCHAR(45) NULL,
  `shippingAmount` VARCHAR(45) NULL,
  `totalOrders` VARCHAR(45) NULL,
  `totalValue` VARCHAR(45) NULL,
 
  PRIMARY KEY (`shipperID`));

alter table orders add column (shipperID VARCHAR(10));

insert into tax values(1, 10, 15);
insert into tax values(2, 11, 16);
insert into tax values(3, 12, 17);
insert into tax values(4, 13, 18);
insert into tax values(5, 14, 19);
insert into tax values(6, 15, 20);
insert into tax values(7, 16, 21);

insert into promotion values(1, 250, 777);
insert into promotion values(2, 300, 888);
insert into promotion values(3, 400, 999);
insert into promotion values(4, 410, 111);
insert into promotion values(5, 420, 222);
insert into promotion values(6, 430, 333);
insert into promotion values(7, 450, 21);

insert into shipper values(1, 'ABC Company',20,3,1000);
insert into shipper values(2, 'XYZ Company',21,2,500);
insert into shipper values(3, 'LKM Company',22,1,100);

update orders set shipperID=1 where orderNumber=10100;
update orders set shipperID=1 where orderNumber=10101;
update orders set shipperID=1 where orderNumber=10102;
update orders set shipperID=2 where orderNumber=10103;
update orders set shipperID=2 where orderNumber=10104;
update orders set shipperID=3 where orderNumber=10105;

/*Populating all the order numbers in the invoice table*/
INSERT INTO invoice (orderNumber)
SELECT orderNumber FROM orders;
