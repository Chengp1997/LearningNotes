create schema payment;
use payment;

create table deviceUser(
  ID int auto_increment,
  username varchar(20),
  balance decimal(10,2) check(balance>=0),
  address varchar(50),
  primary key(ID)
);

create table balanceChangesRecord(
  ID int not null,
  changeTime dateTime not null,
  beforeChange decimal(10,2),
  afterChange decimal(10,2),
  changeAmount decimal(10,2) check(changeAmount>0),
  reason varchar(5),
  primary key(ID,changeTime,beforeChange),
  foreign key (ID) references deviceuser(ID),
  check(reason in ('01','02')) -- 01为支取   02为存入
);

create table device(
  DID int auto_increment,
  deviceType varchar(5) not null,
  ID int not null,
  primary key(DID),
  foreign key(ID) references deviceUser(ID),
  check(deviceType in ('01','02'))
);
/*抄表记录*/
create table meterRecord(
DID int not null,
  dateOfReadMeter date not null,
  currentMeterAmount int default 0,
  meterReader varchar(50) not null,
  primary key (meterReader, currentMeterAmount,dateOfReadMeter,DID),
  foreign key(DID) references device(DID)
  );
/*欠费记录*/
create table arrearageRecord(
DID int not null,
  arrearageDate date not null,
  beginMeterAmount int default 0 ,
  currentMeterAmount int default 0,
  basicAmount decimal(10,2) default 0,
  appendAmount1 decimal(10,2) default 0,
  appendAmount2 decimal(10,2) default 0,  
  capital decimal(10,2) default 0,-- 所有费用的本金
  actualReceivebleAmount decimal(10,2),
  receivedAmount decimal(10,2) check(receivedAmount between 0 and capital),
  overdueFine decimal(10,2) ,
  payableDate date,
  payDate date,
  finish boolean default false,
  primary key(arrearageDate,DID),
  foreign key(DID) references device(DID)
);

create table bank(
  bankID varchar(20),
  bankName varchar(15),
  primary key(bankID)
);

create table bankAccount(
  ID int,
  bankID varchar(20) ,
  accountNumber int auto_increment,
  primary key(accountNumber),
  foreign key(ID) references deviceUser(ID),
  foreign key(bankID) references bank(bankID)
);

create table paymentRecord (
  serialNumber int not null,
  DID int not null,
  bankID varchar(20),
  accountNumber int,
  paymentAmount decimal(10,2) default 0,
  inDeviceAmount decimal(10,2) ,
  paymentDate date,
  reversal boolean default false,
  primary key(serialNumber,reversal),
  foreign key(DID) references device(DID)
);

create table resultRecord(
  checkDate date not null,
  companyCheckingTotal int ,
  checkingTotal int,
  companyCheckingAmount decimal(10,2) default 0,
  checkingAmount decimal(10,2) default 0,
  success boolean default true,
  bankID varchar(20) not null,
  primary key(checkDate, bankID),
  foreign key(bankID) references bank(bankID)
);

create table checkErrorRecord(
  serialNumber int not null,
  checkDate date not null,
  checkErrorReason varchar(100) ,
  bankID varchar(20) not null,
  primary key (serialNumber),
  foreign key(bankID)references bank(bankID),
  check(reason in ('01','02','03'))-- 01  银行无此流水记录  02  公司无此流水记录   03 金额不正确
);

create table serialRecord(
  paymentDate date not null,
  serialNumber int not null,
  paymentAmount decimal(10,2) default 0,
  accountNumber int  ,
  bankID varchar(20) not null,
  primary key (serialNumber),
  foreign key(bankID)references bank(bankID)
);

insert into deviceUser (ID, username, address, balance)
values (1000, '裴行俭', '沈阳海润国际', 4.98);
insert into deviceUser (ID, username, address, balance)
values (1001, '秦良玉', '沈阳碧桂园银河城', 130.32);
insert into deviceUser (ID, username, address, balance)
values (1002, '潘育龙', '沈阳长白岛', 101.45);
insert into deviceUser (ID, username, address, balance)
values (1003, '卫青', '沈阳华强城', 90.81);
insert into deviceUser (ID, username, address, balance)
values (1004, '周亚夫', '沈阳恒大绿洲', 80);

insert into Device  (DID, ID, deviceType)
values (2000, 1000, '01');
insert into Device  (DID, ID, deviceType)
values (2001, 1000, '02');
insert into Device  (DID, ID, deviceType)
values (2002, 1001, '01');
insert into Device  (DID, ID, deviceType)
values (2003, 1002, '01');
insert into Device  (DID, ID, deviceType)
values (2006, 1003, '02');
insert into Device  (DID, ID, deviceType)
values (2004, 1002, '02');
insert into Device  (DID, ID, deviceType)
values (2005, 1002, '01');
insert into Device  (DID, ID, deviceType)
values (2007, 1004, '01');

insert into bank (bankID, bankName)
values ('CMB', '中国招商银行');
insert into bank (bankID, bankName)
values ('CCB', '中国建设银行');
insert into bank (bankID, bankName)
values ('ICBC', '中国工商银行');

insert into bankAccount(ID, bankID, accountNumber)
values(1000, 'CCB', 10001000);
insert into bankAccount(ID, bankID, accountNumber)
values(1001, 'CMB', 10001001);
insert into bankAccount(ID, bankID, accountNumber)
values(1002, 'CMB', 10001002);
insert into bankAccount(ID, bankID, accountNumber)
values(1003, 'ICBC', 10001003);
insert into bankAccount(ID, bankID, accountNumber)
values(1004, 'ICBC', 10001004);

insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-07-07', 2006, 64, '王进喜');
insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-01-05', 2000, 50, '王进喜');
insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-06-05', 2003, 75, '王进喜');
insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-07-05', 2003, 175, '王进喜');
insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-07-05', 2000, 90, '王进喜');
insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-03-05', 2002, 90, '王进喜');
insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-04-05', 2002, 210, '王进喜');
insert into MeterRecord (dateOfReadMeter, DID, currentMeterAmount, meterReader)
values ('2018-07-01', 2007, 98, '王进喜');

insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2006, '2018-07-07', 0, 64, 31.36, 2.51, 4.7, 38.57, 40.19, 1.62, '2018-07-31', '2018-08-19', 40.19, true);-- 1003
insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2000, '2018-01-05', 0, 50, 24.5, 1.96, 2.45, 28.91, null, null, '2018-01-31', null, 0, false);
insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2003, '2018-06-05', 0, 75, 36.75, 2.94, 3.68, 43.37, null, null, '2018-06-30', null, 0, false);
insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2003, '2018-07-05', 75, 175, 49, 3.92, 4.9, 57.82, null, null, '2018-07-31', null, 0, false);
insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2000, '2018-07-05', 50, 90, 19.6, 1.57, 1.96, 23.13, null, null, '2018-07-31', null, 0, false);
insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2002, '2018-03-05', 0, 90, 44.1, 3.53, 4.41, 52.04, 59.43, 7.39, '2018-03-31', '2018-08-20', 59.43, true);
insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2002, '2018-04-05', 90, 210, 58.8, 4.7, 5.88, 69.38, 77.15, 7.77, '2018-04-30', '2018-08-20', 77.15, true);
insert into arrearageRecord  (DID , arrearageDate, beginMeterAmount, currentMeterAmount, basicAmount, appendAmount1, appendAmount2, capital, actualReceivebleAmount, overdueFine, payableDate, payDate, receivedAmount, finish)
values (2007, '2018-07-01', 0, 98, 48.02, 3.84, 4.8, 56.66, null, null, '2018-07-31', null, 0, false);

insert into serialRecord (serialNumber, bankID, accountNumber, paymentAmount, paymentDate)
values (5000, 'CMB', 10001001, 100, '2018-08-20');
insert into serialRecord (serialNumber, bankID, accountNumber, paymentAmount, paymentDate)
values (5001, 'CMB', 10001001, 150, '2018-08-20');
insert into serialRecord (serialNumber, bankID, accountNumber, paymentAmount, paymentDate)
values (5002, 'ICBC', 10001003, 81, '2018-08-19');

insert into paymentRecord  (accountNumber, paymentDate, paymentAmount, reversal, bankID, serialNumber, DID)
values (10001001, '2018-08-20', 100, false, 'CMB', 5000, 2002);
insert into paymentRecord  (accountNumber, paymentDate, paymentAmount, reversal, bankID, serialNumber, DID)
values (10001001, '2018-08-20', 150, false, 'CMB', 5001, 2002);
insert into paymentRecord  (accountNumber, paymentDate, paymentAmount, reversal, bankID, serialNumber, DID)
values (10001003, '2018-08-19', 81, false, 'ICBC', 5002, 2006);