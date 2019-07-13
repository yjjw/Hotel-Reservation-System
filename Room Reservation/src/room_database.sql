-- create database RoomReservation_xxxx;
-- go

drop table Approve;
-- cascade delete from Employee and Make_Reservation
drop table VIP;
-- cascade delete from Guest
drop table Booked_At;
-- cascade delete from Room and Make_Reservation
drop table Includes_Meal;
-- cascade delete from Make_Reservation
drop table Provides;
-- cascade delete from Parking_Space and Make_Reservation
drop table Make_Reservation;
-- cascade delete from Guest
drop table Guest;
-- has no dependencies
drop table Room;
-- has no dependencies
drop table Employee;
-- has no dependencies
drop table Parking_Space;
-- has no dependencies


commit;

CREATE TABLE Guest(
	ID					INT,
	gname 	 			VARCHAR(20),
	birthday 			DATE,
	phone_num 			INT CHECK (phone_num <= 9999999999 AND phone_num >=1000000000),
	credit_card_num 	INT CHECK (credit_card_num <= 9999999999999999 AND credit_card_num>=1000000000000000),
	CONSTRAINT pk_Guest PRIMARY KEY (ID)
);
grant select on Guest to public;

CREATE TABLE VIP(
	ID					INT,
	points 				INT,
	CONSTRAINT pk_VIP PRIMARY KEY (ID),
	CONSTRAINT fk_VIP FOREIGN KEY (ID) REFERENCES Guest(ID)
	ON DELETE CASCADE
);
grant select on VIP to public;

CREATE TABLE Make_Reservation(
	reserve_num						INT,
	number_of_guest			INT,
	start_date      		  DATE,
	end_date     					DATE,
	discount							FLOAT,
	ID						INT NOT NULL,
	CONSTRAINT pk_Make_Reservation PRIMARY KEY (reserve_num),
	CONSTRAINT fk_Make_Reservation FOREIGN KEY (ID) REFERENCES Guest(ID)
	ON DELETE CASCADE
);
grant select on Make_Reservation to public;

CREATE TABLE Room(
	room_num		INT,
	type			VARCHAR(20),
	price			FLOAT,
	CONSTRAINT pk_Room PRIMARY KEY (room_num)
);
grant select on Room to public;

CREATE TABLE Booked_At(
	room_num		INT,
	reserve_num   	INT,
	CONSTRAINT pk_Booked_At PRIMARY KEY (room_num, reserve_num),
	CONSTRAINT fk_Booked_At FOREIGN KEY (room_num) REFERENCES Room(room_num)
               ON DELETE SET NULL,
	CONSTRAINT fk_Booked FOREIGN KEY (reserve_num) REFERENCES Make_Reservation(reserve_num)
               ON DELETE CASCADE
);
grant select on Booked_At to public;

CREATE TABLE Employee(
	ename					VARCHAR(20),
	employee_ID		INT,
	phone_num			INT CHECK (phone_num <= 9999999999 AND phone_num >=1000000000),
	CONSTRAINT pk_Employee PRIMARY KEY (employee_ID)
);
grant select on Employee to public;

CREATE TABLE Approve(
	reserve_num		INT,
	employee_ID		INT,
	CONSTRAINT pk_Approve PRIMARY KEY (reserve_num,employee_ID),
	CONSTRAINT fk_Approve FOREIGN KEY (reserve_num) REFERENCES Make_Reservation(reserve_num)
		ON DELETE CASCADE,
	CONSTRAINT fk_Approved FOREIGN KEY (employee_ID) REFERENCES Employee(employee_ID)
		ON DELETE SET NULL
);
grant select on Approve to public;

CREATE TABLE Includes_Meal(
	reserve_num		INT NOT NULL,
	mname			VARCHAR(20),
	CONSTRAINT pk_Includes_Meal PRIMARY KEY(reserve_num, mname),
	CONSTRAINT fk_Includes_Meal FOREIGN KEY(reserve_num) REFERENCES Make_Reservation(reserve_num)
	  ON DELETE CASCADE
);
grant select on Includes_Meal to public;

CREATE TABLE Parking_Space(
	plate_num		CHAR(20),
	stall_num		INT,
	CONSTRAINT pk_Parking_Space PRIMARY KEY (stall_num)
);
grant select on Parking_Space to public;

CREATE TABLE Provides(
	reserve_num		INT,
	stall_num		INT,
	CONSTRAINT pk_Provides PRIMARY KEY (reserve_num, stall_num),
	CONSTRAINT fk_Provides FOREIGN KEY(reserve_num) REFERENCES Make_Reservation(reserve_num)
		ON DELETE CASCADE,
	CONSTRAINT fk_Provide FOREIGN KEY(stall_num) REFERENCES Parking_Space(stall_num)
	  ON DELETE SET NULL
);
grant select on Provides to public;


commit;





-- insert data into diffrent tables
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(52033688,'Andy','1992-08-18',6205869176,1925827681016543);
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(13424149,'Andydada','1992-08-18',5488888888,1925827681018888);
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(57483960,'Jingwei','1997-01-21',5847105827,1029756472102760);
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(23847112,'yege','1997-01-21',7788398166,1029756472102762);
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(19387981,'liqingzhao','1997-01-21',7788338162,1029756472102763);
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(58395748,'Happy','1997-04-19',5748102958,1029584710295647);
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(58492049,'Lynch','1996-10-24',5849307810,2857304918275940);
INSERT
INTO		Guest(ID, gname, birthday, phone_num, credit_card_num)
VALUES	(67583920,'Ling','1987-05-04',1029584710,1928503948124123);


INSERT
INTO		VIP(ID, points)
VALUES	(52033688,1000);
INSERT
INTO		VIP(ID, points)
VALUES	(57483960,2000);
INSERT
INTO		VIP(ID, points)
VALUES	(23847112, 500);
INSERT
INTO		VIP(ID, points)
VALUES	(19387981,0);
INSERT
INTO		VIP(ID, points)
VALUES	(13424149,5000);


INSERT
INTO		Make_Reservation(reserve_num, number_of_guest, start_date, end_date, discount, ID)
VALUES	(12345,2,'2005-10-24', '2005-10-25',100,52033688);
INSERT
INTO		Make_Reservation(reserve_num, number_of_guest, start_date, end_date, discount, ID)
VALUES	(12346,4,'2005-08-14', '2005-08-15',100,52033688);
INSERT
INTO		Make_Reservation(reserve_num, number_of_guest, start_date, end_date, discount, ID)
VALUES	(12347,1,'2011-12-31', '2012-01-01',50,19387981);
INSERT
INTO		Make_Reservation(reserve_num, number_of_guest, start_date, end_date, discount, ID)
VALUES	(54321,2,'2014-12-14', '2014-12-18',90,57483960);
INSERT
INTO		Make_Reservation(reserve_num, number_of_guest, start_date, end_date, discount, ID)
VALUES	(99873,2,'2015-05-24', '2015-06-01',0,58395748);
INSERT
INTO		Make_Reservation(reserve_num, number_of_guest, start_date, end_date, discount, ID)
VALUES	(19283,3,'2016-11-11', '2016-11-27',0,67583920);
INSERT
INTO		Make_Reservation(reserve_num, number_of_guest, start_date, end_date, discount, ID)
VALUES	(28493,2,'2017-02-14', '2017-02-28',0,58492049);


INSERT
INTO		Room(room_num, type, price)
VALUES	(101,'Single Room',150);
INSERT
INTO		Room(room_num, type, price)
VALUES	(103,'Single Room',150);
INSERT
INTO		Room(room_num, type, price)
VALUES	(105,'Single Room',170);
INSERT
INTO		Room(room_num, type, price)
VALUES	(108,'Single Room',180);
INSERT
INTO		Room(room_num, type, price)
VALUES	(110,'Single Room',180);
INSERT
INTO		Room(room_num, type, price)
VALUES	(200,'Double Room',250);
INSERT
INTO		Room(room_num, type, price)
VALUES	(202,'Double Room',250);
INSERT
INTO		Room(room_num, type, price)
VALUES	(204,'Double Room',250);
INSERT
INTO		Room(room_num, type, price)
VALUES	(206,'Double Room',270);
INSERT
INTO		Room(room_num, type, price)
VALUES	(208,'Double Room',290);
INSERT
INTO		Room(room_num, type, price)
VALUES	(210,'Double Room',290);
INSERT
INTO		Room(room_num, type, price)
VALUES	(212,'Double Room',310);
INSERT
INTO		Room(room_num, type, price)
VALUES	(302,'Executive Room',420);
INSERT
INTO		Room(room_num, type, price)
VALUES	(304,'Executive Room',420);
INSERT
INTO		Room(room_num, type, price)
VALUES	(306,'Executive Room',450);
INSERT
INTO		Room(room_num, type, price)
VALUES	(308,'Executive Room',480);
INSERT
INTO		Room(room_num, type, price)
VALUES	(310,'Executive Room',480);
INSERT
INTO		Room(room_num, type, price)
VALUES	(507,'Deluxe Suite',800);
INSERT
INTO		Room(room_num, type, price)
VALUES	(509,'Deluxe Suite',1000);
INSERT
INTO		Room(room_num, type, price)
VALUES	(909,'Presidential Suite',2000);
INSERT
INTO		Room(room_num, type, price)
VALUES	(910,'Presidential Suite',2000);
INSERT
INTO		Room(room_num, type, price)
VALUES	(911,'Presidential Suite',2000);


INSERT
INTO		Booked_At(room_num, reserve_num)
VALUES	(306,12345);
INSERT
INTO		Booked_At(room_num, reserve_num)
VALUES	(304,12346);
INSERT
INTO		Booked_At(room_num, reserve_num)
VALUES	(507,12347);
INSERT
INTO		Booked_At(room_num, reserve_num)
VALUES	(909,54321);
INSERT
INTO		Booked_At(room_num, reserve_num)
VALUES	(306,28493);
INSERT
INTO		Booked_At(room_num, reserve_num)
VALUES	(507,99873);
INSERT
INTO		Booked_At(room_num, reserve_num)
VALUES	(909,19283);

INSERT
INTO		Employee(ename, employee_ID,phone_num)
VALUES	('Tony',1176,8376293810);
INSERT
INTO		Employee(ename, employee_ID,phone_num)
VALUES	('Amber',5748,5749102758);
INSERT
INTO		Employee(ename, employee_ID, phone_num)
VALUES	('Jack',2736,3647293782);
INSERT
INTO		Employee(ename, employee_ID,phone_num)
VALUES	('Tom',8746,8372947503);
INSERT
INTO		Employee(ename, employee_ID,phone_num)
VALUES	('Leon',6666,7362856391);

INSERT
INTO		Approve(reserve_num, employee_ID)
VALUES	(12345,1176);
INSERT
INTO		Approve(reserve_num, employee_ID)
VALUES	(12346,1176);
INSERT
INTO		Approve(reserve_num, employee_ID)
VALUES	(12347,8746);
INSERT
INTO		Approve(reserve_num, employee_ID)
VALUES	(28493,2736);
INSERT
INTO		Approve(reserve_num, employee_ID)
VALUES	(54321,6666);


INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(12345,'vegetable dumplings');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(12345,'chicken pizza');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(12346,'vegetable dumplings');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(54321,'vegetable dumplings');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(12346,'angus steak');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(12346,'chicken pizza');
INSERT
INTO		Includes_Meal(reserve_num,  mname)
VALUES	(12346,'cheese pasta');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(54321,'mashed potatoes');
INSERT
INTO		Includes_Meal(reserve_num,  mname)
VALUES	(28493,'vegetable dumplings');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(12347,'vegetable dumplings');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(99873,'vegetable dumplings');
INSERT
INTO		Includes_Meal(reserve_num, mname)
VALUES	(19283,'vegetable dumplings');

INSERT
INTO		Parking_Space(plate_num, stall_num)
VALUES	('ABC123',9);
INSERT
INTO		Parking_Space(plate_num, stall_num)
VALUES	('E596CV',45);
INSERT
INTO		Parking_Space(plate_num, stall_num)
VALUES	('F963LD',66);
INSERT
INTO		Parking_Space(plate_num, stall_num)
VALUES	('OP35W3',54);
INSERT
INTO		Parking_Space(plate_num, stall_num)
VALUES	('JG7F39',76);

INSERT
INTO		Provides(reserve_num, stall_num)
VALUES	(12345,9);
INSERT
INTO		Provides(reserve_num, stall_num)
VALUES	(12345,45);
INSERT
INTO		Provides(reserve_num, stall_num)
VALUES	(12346,66);
INSERT
INTO		Provides(reserve_num, stall_num)
VALUES	(54321,54);
INSERT
INTO		Provides(reserve_num, stall_num)
VALUES	(54321,76);

commit work;





