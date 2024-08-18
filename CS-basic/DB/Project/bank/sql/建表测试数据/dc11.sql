prompt PL/SQL Developer import file
prompt Created on 2018��8��20�� by lenovo
set feedback off
set define off
prompt Creating ACCOUNT_ERROR_DETAILS...
create table ACCOUNT_ERROR_DETAILS
(
  AE_ID             NUMBER(9) not null,
  ACCOUNT_TIME      DATE not null,
  BANK_ID           VARCHAR2(9) not null,
  BT_ID             NUMBER(9) not null,
  CUSTOMER_ID       NUMBER(9) not null,
  BANK_AMOUNT       NUMBER(9,2) not null,
  ENTERPRISE_AMOUNT NUMBER(9,2) not null,
  ACCOUNT_INFO      VARCHAR2(30)
)
;
alter table ACCOUNT_ERROR_DETAILS
  add primary key (AE_ID);

prompt Creating ACCOUNT_TOTAL...
create table ACCOUNT_TOTAL
(
  AT_ID             NUMBER(9) not null,
  ACCOUNT_DATE      DATE not null,
  BANK_ID           VARCHAR2(9) not null,
  BANK_COUNT        NUMBER(9) not null,
  BANK_AMOUNT       NUMBER(9,2) not null,
  ENTERPRISE_COUNT  NUMBER(9) not null,
  ENTERPRISE_AMOUNT NUMBER(9,2) not null,
  IS_SUCCESS        VARCHAR2(2)
)
;
alter table ACCOUNT_TOTAL
  add primary key (AT_ID);

prompt Creating BANK...
create table BANK
(
  BANK_ID   VARCHAR2(9) not null,
  BANK_NAME VARCHAR2(30) not null
)
;
alter table BANK
  add primary key (BANK_ID);

prompt Creating CUSTOMER...
create table CUSTOMER
(
  CUSTOMER_ID   NUMBER(9) not null,
  CUSTOMER_NAME VARCHAR2(30) not null,
  ADDRESS       VARCHAR2(90) not null,
  BALANCE       NUMBER(9,2)
)
;
alter table CUSTOMER
  add primary key (CUSTOMER_ID);

prompt Creating BANK_TRANSFER_RECORD...
create table BANK_TRANSFER_RECORD
(
  BT_ID           NUMBER(9) not null,
  BANK_ID         VARCHAR2(9) not null,
  CUSTOMER_ID     NUMBER(9) not null,
  TRANSFER_AMOUNT NUMBER(9,2) not null,
  TRANSFER_TIME   DATE not null
)
;
alter table BANK_TRANSFER_RECORD
  add primary key (BT_ID);
alter table BANK_TRANSFER_RECORD
  add constraint BANK_TRANSFER_CUSTOMER_ID_FK foreign key (CUSTOMER_ID)
  references CUSTOMER (CUSTOMER_ID);

prompt Creating CODE_TABLE...
create table CODE_TABLE
(
  C_KEY   VARCHAR2(30) not null,
  C_VALUE VARCHAR2(9) not null
)
;
alter table CODE_TABLE
  add primary key (C_KEY);

prompt Creating DEVICE...
create table DEVICE
(
  DEVICE_ID   NUMBER(9) not null,
  CUSTOMER_ID NUMBER(9) not null,
  DEVICE_TYPE VARCHAR2(2) not null
)
;
alter table DEVICE
  add primary key (DEVICE_ID);

prompt Creating METER_READER...
create table METER_READER
(
  MR_ID   NUMBER(9) not null,
  MR_NAME VARCHAR2(30) not null
)
;
alter table METER_READER
  add primary key (MR_ID);

prompt Creating METER_LOG...
create table METER_LOG
(
  MT_ID       NUMBER(9) not null,
  MT_DATE     DATE not null,
  DEVICE_ID   NUMBER(9) not null,
  CUSTOMER_ID NUMBER(9) not null,
  MT_NUMBER   NUMBER(9) not null,
  MR_ID       NUMBER(9)
)
;
alter table METER_LOG
  add primary key (MT_ID);
alter table METER_LOG
  add constraint METER_LOG_CUSTOMER_ID_FK foreign key (CUSTOMER_ID)
  references CUSTOMER (CUSTOMER_ID);
alter table METER_LOG
  add constraint METER_LOG_DEVICE_ID_FK foreign key (DEVICE_ID)
  references DEVICE (DEVICE_ID);
alter table METER_LOG
  add constraint METER_LOG_MR_ID_FK foreign key (MR_ID)
  references METER_READER (MR_ID);
create unique index METER_LOG_DEVICE_ID_MT_DATE on METER_LOG (DEVICE_ID, MT_DATE);

prompt Creating PAY_LOG...
create table PAY_LOG
(
  PAY_ID      NUMBER(9) not null,
  CUSTOMER_ID NUMBER(9) not null,
  PAY_TIME    DATE not null,
  PAY_AMOUNT  NUMBER(9,2) not null,
  PAY_TYPE    VARCHAR2(2) not null,
  BANK_ID     VARCHAR2(9) not null,
  BT_ID       NUMBER(9) not null,
  NOTES       VARCHAR2(90)
)
;
alter table PAY_LOG
  add primary key (PAY_ID);
alter table PAY_LOG
  add constraint PAY_LOG_CUSTOMER_ID_FK foreign key (CUSTOMER_ID)
  references CUSTOMER (CUSTOMER_ID);

prompt Creating POWER_RATE_LIST...
create table POWER_RATE_LIST
(
  PR_ID             NUMBER(9) not null,
  DEVICE_ID         NUMBER(9) not null,
  CUSTOMER_ID       NUMBER(9) not null,
  MT_DATE           DATE not null,
  BEGIN_NUMBER      NUMBER(9),
  END_NUMBER        NUMBER(9),
  BASIC_COST        NUMBER(9,2),
  ADDITIONAL_COST_1 NUMBER(9,2),
  ADDITIONAL_COST_2 NUMBER(9,2),
  PAID_FEE          NUMBER(9,2),
  ACTUAL_FEE        NUMBER(9,2),
  LATE_FEE          NUMBER(9,2),
  PAYABLE_DATE      DATE,
  PAY_DATE          DATE,
  ALREADY_FEE       NUMBER(9,2) default 0.00,
  PAY_STATE         VARCHAR2(1)
)
;
alter table POWER_RATE_LIST
  add primary key (PR_ID);
alter table POWER_RATE_LIST
  add constraint POWER_RATE_LIST_CUSTOMER_ID_FK foreign key (CUSTOMER_ID)
  references CUSTOMER (CUSTOMER_ID);
alter table POWER_RATE_LIST
  add constraint POWER_RATE_LIST_DEVICE_ID_FK foreign key (DEVICE_ID)
  references DEVICE (DEVICE_ID);
create unique index POWER_DEVICE_ID_PAYABLE_DATE on POWER_RATE_LIST (DEVICE_ID, PAYABLE_DATE);

prompt Loading ACCOUNT_ERROR_DETAILS...
prompt Table is empty
prompt Loading ACCOUNT_TOTAL...
prompt Table is empty
prompt Loading BANK...
insert into BANK (BANK_ID, BANK_NAME)
values ('CMB', '�й���������');
insert into BANK (BANK_ID, BANK_NAME)
values ('CCB', '�й���������');
insert into BANK (BANK_ID, BANK_NAME)
values ('ICBC', '�й���������');
commit;
prompt 3 records loaded
prompt Loading CUSTOMER...
insert into CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, BALANCE)
values (1000, '���м�', '�����������', 4.98);
insert into CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, BALANCE)
values (1001, '������', '�����̹�԰���ӳ�', 130.32);
insert into CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, BALANCE)
values (1002, '������', '�������׵�', 101.45);
insert into CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, BALANCE)
values (1003, '����', '������ǿ��', 90.81);
insert into CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, BALANCE)
values (1004, '���Ƿ�', '�����������', 80);
commit;
prompt 5 records loaded
prompt Loading BANK_TRANSFER_RECORD...
insert into BANK_TRANSFER_RECORD (BT_ID, BANK_ID, CUSTOMER_ID, TRANSFER_AMOUNT, TRANSFER_TIME)
values (5000, 'CMB', 1001, 100, to_date('20-08-2018 00:46:27', 'dd-mm-yyyy hh24:mi:ss'));
insert into BANK_TRANSFER_RECORD (BT_ID, BANK_ID, CUSTOMER_ID, TRANSFER_AMOUNT, TRANSFER_TIME)
values (5001, 'CMB', 1001, 150, to_date('20-08-2018 00:47:48', 'dd-mm-yyyy hh24:mi:ss'));
insert into BANK_TRANSFER_RECORD (BT_ID, BANK_ID, CUSTOMER_ID, TRANSFER_AMOUNT, TRANSFER_TIME)
values (5002, 'ICBC', 1003, 81, to_date('20-08-2018 19:54:56', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 3 records loaded
prompt Loading CODE_TABLE...
insert into CODE_TABLE (C_KEY, C_VALUE)
values ('ÿ�ȵ�۸�', '0.49');
insert into CODE_TABLE (C_KEY, C_VALUE)
values ('������ΥԼ�����', '0.001');
insert into CODE_TABLE (C_KEY, C_VALUE)
values ('�������ΥԼ�����', '0.001');
insert into CODE_TABLE (C_KEY, C_VALUE)
values ('��ҵ����ΥԼ�����', '0.002');
insert into CODE_TABLE (C_KEY, C_VALUE)
values ('��ҵ����ΥԼ�����', '0.003');
commit;
prompt 5 records loaded
prompt Loading DEVICE...
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2000, 1000, '01');
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2001, 1000, '02');
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2002, 1001, '01');
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2003, 1002, '01');
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2006, 1003, '02');
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2004, 1002, '02');
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2005, 1002, '01');
insert into DEVICE (DEVICE_ID, CUSTOMER_ID, DEVICE_TYPE)
values (2007, 1004, '01');
commit;
prompt 8 records loaded
prompt Loading METER_READER...
insert into METER_READER (MR_ID, MR_NAME)
values (1, '������');
commit;
prompt 1 records loaded
prompt Loading METER_LOG...
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3006, to_date('07-07-2018', 'dd-mm-yyyy'), 2006, 1003, 64, 1);
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3000, to_date('05-01-2018', 'dd-mm-yyyy'), 2000, 1000, 50, 1);
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3001, to_date('05-06-2018', 'dd-mm-yyyy'), 2003, 1002, 75, 1);
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3002, to_date('05-07-2018', 'dd-mm-yyyy'), 2003, 1002, 175, 1);
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3003, to_date('05-07-2018', 'dd-mm-yyyy'), 2000, 1000, 90, 1);
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3004, to_date('05-03-2018', 'dd-mm-yyyy'), 2002, 1001, 90, 1);
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3005, to_date('05-04-2018', 'dd-mm-yyyy'), 2002, 1001, 210, 1);
insert into METER_LOG (MT_ID, MT_DATE, DEVICE_ID, CUSTOMER_ID, MT_NUMBER, MR_ID)
values (3007, to_date('01-07-2018', 'dd-mm-yyyy'), 2007, 1004, 98, 1);
commit;
prompt 8 records loaded
prompt Loading PAY_LOG...
insert into PAY_LOG (PAY_ID, CUSTOMER_ID, PAY_TIME, PAY_AMOUNT, PAY_TYPE, BANK_ID, BT_ID, NOTES)
values (6000, 1001, to_date('20-08-2018 00:46:27', 'dd-mm-yyyy hh24:mi:ss'), 100, '01', 'CMB', 5000, '4004');
insert into PAY_LOG (PAY_ID, CUSTOMER_ID, PAY_TIME, PAY_AMOUNT, PAY_TYPE, BANK_ID, BT_ID, NOTES)
values (6001, 1001, to_date('20-08-2018 00:47:48', 'dd-mm-yyyy hh24:mi:ss'), 150, '01', 'CMB', 5001, '4005');
insert into PAY_LOG (PAY_ID, CUSTOMER_ID, PAY_TIME, PAY_AMOUNT, PAY_TYPE, BANK_ID, BT_ID, NOTES)
values (6002, 1003, to_date('20-08-2018 19:54:56', 'dd-mm-yyyy hh24:mi:ss'), 81, '01', 'ICBC', 5002, '4006');
commit;
prompt 3 records loaded
prompt Loading POWER_RATE_LIST...
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4006, 2006, 1003, to_date('07-07-2018', 'dd-mm-yyyy'), 0, 64, 31.36, 2.51, 4.7, 38.57, 40.19, 1.62, to_date('31-07-2018', 'dd-mm-yyyy'), to_date('19-08-2018 19:54:56', 'dd-mm-yyyy hh24:mi:ss'), 40.19, '1');
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4000, 2000, 1000, to_date('05-01-2018', 'dd-mm-yyyy'), 0, 50, 24.5, 1.96, 2.45, 28.91, null, null, to_date('31-01-2018', 'dd-mm-yyyy'), null, 0, '0');
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4001, 2003, 1002, to_date('05-06-2018', 'dd-mm-yyyy'), 0, 75, 36.75, 2.94, 3.68, 43.37, null, null, to_date('30-06-2018', 'dd-mm-yyyy'), null, 0, '0');
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4002, 2003, 1002, to_date('05-07-2018', 'dd-mm-yyyy'), 75, 175, 49, 3.92, 4.9, 57.82, null, null, to_date('31-07-2018', 'dd-mm-yyyy'), null, 0, '0');
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4003, 2000, 1000, to_date('05-07-2018', 'dd-mm-yyyy'), 50, 90, 19.6, 1.57, 1.96, 23.13, null, null, to_date('31-07-2018', 'dd-mm-yyyy'), null, 0, '0');
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4004, 2002, 1001, to_date('05-03-2018', 'dd-mm-yyyy'), 0, 90, 44.1, 3.53, 4.41, 52.04, 59.43, 7.39, to_date('31-03-2018', 'dd-mm-yyyy'), to_date('20-08-2018 00:46:27', 'dd-mm-yyyy hh24:mi:ss'), 59.43, '1');
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4005, 2002, 1001, to_date('05-04-2018', 'dd-mm-yyyy'), 90, 210, 58.8, 4.7, 5.88, 69.38, 77.15, 7.77, to_date('30-04-2018', 'dd-mm-yyyy'), to_date('20-08-2018 00:47:48', 'dd-mm-yyyy hh24:mi:ss'), 77.15, '1');
insert into POWER_RATE_LIST (PR_ID, DEVICE_ID, CUSTOMER_ID, MT_DATE, BEGIN_NUMBER, END_NUMBER, BASIC_COST, ADDITIONAL_COST_1, ADDITIONAL_COST_2, PAID_FEE, ACTUAL_FEE, LATE_FEE, PAYABLE_DATE, PAY_DATE, ALREADY_FEE, PAY_STATE)
values (4007, 2007, 1004, to_date('01-07-2018', 'dd-mm-yyyy'), 0, 98, 48.02, 3.84, 4.8, 56.66, null, null, to_date('31-07-2018', 'dd-mm-yyyy'), null, 0, '0');
commit;
prompt 8 records loaded
set feedback on
set define on
prompt Done.
