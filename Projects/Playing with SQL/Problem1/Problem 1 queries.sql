/*Problem 1*/
/*a*/
select customerName from customers table1 join employees table2 on table1.salesRepEmployeeNumber = table2.employeeNumber join offices table3 on table2.officeCode = table3.officeCode where table1.city != table3.city;

/*b*/
select orderNumber from orderdetails table1 join products table2 on table1.productCode = table2.productCode where table1.priceEach<table2.MSRP group by table1.orderNumber;

/*c*/
with table1 as
(select orderdetails.productCode, sum(priceEach * quantityOrdered) as sales, sum(quantityOrdered) as quantity from orderdetails
join orders on orders.orderNumber = orderdetails.orderNumber where year(orderDate)=2003 group by orderdetails.productCode)
select productLine, (sales - ( table1.quantity * products.buyPrice))/(table1.quantity * products.buyPrice) as Profit_Margin from products
join table1
on products.productCode = table1.productCode
order by profit_margin desc limit 1;

/*d*/
with SalesTable as
(select orderNumber, (quantityOrdered * priceEach) as totalSales from orderdetails),
CustomerTable as
(select orders.customerNumber, final.totalSales from orders join SalesTable as final
on orders.orderNumber = final.orderNumber where year(orders.orderDate) = 2004),
EmployeeTable as
(select customers.salesRepEmployeeNumber, CustomerTable.totalSales from customers join CustomerTable
on CustomerTable.customerNumber = customers.customerNumber) 
select concat(employees.firstName," ",employees.lastName) as Employee_Name, sum(EmployeeTable.totalSales)as Total_Sales from employees join EmployeeTable
on employees.employeeNumber = EmployeeTable.salesRepEmployeeNumber
group by employees.employeeNumber order by sum(EmployeeTable.totalSales) desc limit 5;

/*e*/
with SalesTable2004 as
(select orderNumber, (quantityOrdered * priceEach) as totalSales from orderdetails),
CustomerTable2004 as
(select orders.customerNumber, SalesTable2004.totalSales from orders join SalesTable2004
on orders.orderNumber = SalesTable2004.orderNumber where year(orders.orderDate) = 2004),
EmployeeTable2004 as
(select customers.salesRepEmployeeNumber, CustomerTable2004.totalSales from customers
join CustomerTable2004
on CustomerTable2004.customerNumber = customers.customerNumber),
Table2004 as
(select employees.employeeNumber, concat(employees.firstName," ",employees.lastName) as employee_name, sum(EmployeeTable2004.totalSales) as totalSales from employees join EmployeeTable2004
on employees.employeeNumber = EmployeeTable2004.salesRepEmployeeNumber
group by employees.employeeNumber order by sum(EmployeeTable2004.totalSales)),
SalesTable2003 as
(select orderNumber, (quantityOrdered * priceEach) as totalSales from orderdetails),
CustomerTable2003 as
(select orders.customerNumber, SalesTable2004.totalSales from orders join SalesTable2004
on orders.orderNumber = SalesTable2004.orderNumber where year(orders.orderDate) = 2003),
EmployeeTable2003 as
(select customers.salesRepEmployeeNumber, CustomerTable2003.totalSales from customers
join CustomerTable2003
on CustomerTable2003.customerNumber = customers.customerNumber),
Table2003 as
(select employees.employeeNumber ,concat(employees.firstName," ",employees.lastName) as employee_name, sum(EmployeeTable2003.totalSales)as totalSales from employees join EmployeeTable2003
on employees.employeeNumber = EmployeeTable2003.salesRepEmployeeNumber
group by employees.employeeNumber order by sum(EmployeeTable2003.totalSales))
select Table2004.employeeNumber,Table2004.employee_name,Table2004.totalSales from Table2004 join Table2003
on Table2003.employeeNumber = Table2004.employeeNumber
where Table2003.totalSales < Table2004.totalSales order by Table2004.employeeNumber;
