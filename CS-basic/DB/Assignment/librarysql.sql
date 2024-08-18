create schema library;
use library;

create table library (
  lName varchar(50) not null,
  lAddress varchar(50) NULL,
  primary key (lName)
);
 
create table member(
 membershipNum INT not null,
 mName varchar(50) null,
 mAddress varchar(50) null,
 category varchar(50),
 balance double(7,2) default 0.00,
 totalVal double(7,2) default 0.00,
 totalPay double(7,2) default 0.00,
 primary key(membershipNum),
 CHECK (balance>=0),
 CHECK (totalVal>=0),
 CHECK (totalPay>=0),
 CHECK(totalVal>=totalPay),
 check(category in ('normal','premium'))
);

create table DVD(
 title varchar(50) not null,
 releaseYear year(4) not null,
 director varchar(50) null,
 primary key(title,releaseYear)
);

create table DVDCast(
 title varchar(50) not null,
 releaseYear year(4) not null,
 castName varchar(50) not null,
 primary key(title,releaseYear,castName),
 foreign key(title, releaseYear) references DVD(title, releaseYear)
);

create table DVDGenre(
 title varchar(50) not null,
 releaseYear year(4) not null,
 genre varchar(50) not null,
 primary key(title,releaseYear,genre),
 foreign key(title, releaseYear) references DVD(title, releaseYear)
);

create table DVDCopy(
 copyID varchar(50) not null,
 title varchar(50)null,
 releaseYear year(4) null,
 lName varchar(50) null,
 primary key(copyID),
 foreign key(title, releaseYear) references DVD(title, releaseYear),
 foreign key(lName) references library(lName)
);

create table operate(
 rentDate date not null,
 returnDate date null,
 membershipNum int not null,
 copyID varchar(50) not null,
 primary key(rentDate, membershipNum, copyID),
 foreign key(membershipNum) references member(membershipNum),
 foreign key(copyID) references DVDCopy(copyID)
);
 
 insert into library(lAddress, lName) values ('Shenying Road', 'NEUlibrary');
 insert into library(lAddress, lName) values ('Sanhao Street', 'Nanhulibray');
 insert into library(lAddress, lName) values ('6th Avenue', 'SYlibrary');
 insert into library(lAddress, lName) values ('7th Road', 'LNlibrary');
 insert into library(lAddress, lName) values ('Wenguan Road', 'Somelibrary');
 
 -- 会员信息
 insert into member(membershipNum, mName, mAddress, category, balance, totalVal, totalPay) values (20161111,'Abigail', 'A1', 'normal', 5.5, 0, 0);
 insert into member(membershipNum, mName, mAddress, category, balance, totalVal, totalPay) values (20162222,'Bob', 'A2', 'premium', 50, 0, 0);
 insert into member(membershipNum, mName, mAddress, category, balance, totalVal, totalPay) values (20163333,'Candy', 'A3', 'premium', 70, 15, 15);
 insert into member(membershipNum, mName, mAddress, category, balance, totalVal, totalPay) values (20164444,'David','A4','normal',100,55,55); 
 insert into member(membershipNum, mName, mAddress, category, balance, totalVal, totalPay) values (20165555,'Emily','A5','normal',112,10,10); 
 
 -- DVD
 insert into DVD(title, releaseYear, director) values ('Handmaid''s Tale', '2017', 'Reed Morano');
 insert into DVD(title, releaseYear, director) values ('Westworld', '2016', 'Jonathan Nolan');
 insert into DVD(title, releaseYear, director) values ('Friends', '1994', 'Peter Bonerz');
 insert into DVD(title, releaseYear, director) values ('Game of Thrones','2016','HBO');
 insert into DVD(title, releaseYear, director) values ('Game of Thrones','2017','HBO');
 insert into DVD(title, releaseYear, director) values ('Modern Family','2009','ABC');
 insert into DVD(title, releaseYear, director) values ('Superman','1948','Thomas Carr');
 insert into DVD(title, releaseYear, director) values ('La La Land','2016','Damien Chazelle');
 
 -- DVD cast
 insert into DVDCast(title, releaseYear, castName) values ('Handmaid''s Tale', '2017','Elisabeth Moss');
 insert into DVDCast(title, releaseYear, castName) values ('Handmaid''s Tale', '2017','Alexis Bledel');
 insert into DVDCast(title, releaseYear, castName) values ('Handmaid''s Tale', '2017','Joseph Fiennes');
 insert into DVDCast(title, releaseYear, castName) values ('Handmaid''s Tale', '2017','Max Minghella');
 insert into DVDCast(title, releaseYear, castName) values ('Westworld', '2016','Evan Rachel Wood');
 insert into DVDCast(title, releaseYear, castName) values ('Westworld', '2016','Anthony Hopkins');
 insert into DVDCast(title, releaseYear, castName) values ('Westworld', '2016','Jeffrey Wright');
 insert into DVDCast(title, releaseYear, castName) values ('Friends', '1994','Jennifer Aniston');
 insert into DVDCast(title, releaseYear, castName) values ('Friends', '1994','Courteney Cox'); 
 insert into DVDCast(title, releaseYear, castName) values ('Friends', '1994','Lisa Diane Kudrow');
 insert into DVDCast(title, releaseYear, castName) values ('Game of Thrones','2016','John Snow');
 insert into DVDCast(title, releaseYear, castName) values ('Game of Thrones','2016','Daenerys Targaryen');
 insert into DVDCast(title, releaseYear, castName) values ('Game of Thrones','2016','Eddard Stark');
 insert into DVDCast(title, releaseYear, castName) values ('Game of Thrones','2017','John Snow');
 insert into DVDCast(title, releaseYear, castName) values ('Game of Thrones','2017','Daenerys Targaryen');
 insert into DVDCast(title, releaseYear, castName) values ('Game of Thrones','2017','Eddard Stark');
 insert into DVDCast(title, releaseYear, castName) values ('Modern Family','2009','Phill dumphy');
 insert into DVDCast(title, releaseYear, castName) values ('Modern Family','2009','Jay Pritchet');
 insert into DVDCast(title, releaseYear, castName) values ('Modern Family','2009','Cameron Tucker');
 insert into DVDCast(title, releaseYear, castName) values ('Superman','1948','Noel Neill');
 insert into DVDCast(title, releaseYear, castName) values ('Superman','1948','Tommy Bond');
 insert into DVDCast(title, releaseYear, castName) values ('Superman','1948','Jack Ingram');
 insert into DVDCast(title, releaseYear, castName) values ('La La Land','2016','Emma Stone');

 -- DVD Genre
 insert into DVDGenre(title, releaseYear, genre) values ('Handmaid''s Tale', '2017', 'Horror');
 insert into DVDGenre(title, releaseYear, genre) values ('Handmaid''s Tale', '2017', 'Fictional');
 insert into DVDGenre(title, releaseYear, genre) values ('Handmaid''s Tale', '2017', 'Scientific');
 insert into DVDGenre(title, releaseYear, genre) values ('Westworld', '2016', 'Horror');
 insert into DVDGenre(title, releaseYear, genre) values ('Westworld', '2016', 'Fictional');
 insert into DVDGenre(title, releaseYear, genre) values ('Westworld', '2016', 'Scientific');
 insert into DVDGenre(title, releaseYear, genre) values ('Westworld', '2016', 'Romance');
 insert into DVDGenre(title, releaseYear, genre) values ('Friends', '1994', 'Comedy');
 insert into DVDGenre(title, releaseYear, genre) values ('Friends', '1994', 'Romance');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2016','Fictional');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2016','Action');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2016','Scientific');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2016','Romance');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2017','Fictional');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2017','Action');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2017','Scientific');
 insert into DVDGenre(title, releaseYear, genre) values ('Game of Thrones','2017','Romance');
 insert into DVDGenre(title, releaseYear, genre) values ('Modern Family', '2009', 'Comedy');
 insert into DVDGenre(title, releaseYear, genre) values ('Modern Family', '2009', 'Romance');
 insert into DVDGenre(title, releaseYear, genre) values ('Superman','1948', 'Fictional');
 insert into DVDGenre(title, releaseYear, genre) values ('Superman','1948', 'Romance');
 insert into DVDGenre(title, releaseYear, genre) values ('La La Land','2016', 'Fictional');
 insert into DVDGenre(title, releaseYear, genre) values ('La La Land','2016', 'Romance');
 
 -- DVD Copy
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_1', 'Handmaid''s Tale', '2017', 'NEUlibrary');  
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_2', 'Handmaid''s Tale', '2017', 'NEUlibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_3', 'Handmaid''s Tale', '2017', 'NEUlibrary');  
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_4', 'Handmaid''s Tale', '2017', 'Nanhulibray');  
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_5', 'Handmaid''s Tale', '2017', 'Nanhulibray');  
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_6', 'Handmaid''s Tale', '2017', 'Nanhulibray'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_7', 'Handmaid''s Tale', '2017', 'Nanhulibray');  
 insert into DVDCopy(copyID, title, releaseYear, lName) values('HMT2017_8', 'Handmaid''s Tale', '2017', 'LNlibrary');  
 insert into DVDCopy(copyID, title, releaseYear, lName) values('WW2016_1', 'Westworld', '2016', 'Nanhulibray'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('WW2016_2', 'Westworld', '2016', 'Nanhulibray'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('WW2016_3', 'Westworld', '2016', 'LNlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('WW2016_4', 'Westworld', '2016', 'LNlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('WW2016_5', 'Westworld', '2016', 'SYlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('WW2016_6', 'Westworld', '2016', 'SYlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('F1994_1', 'Friends', '1994', 'Somelibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('F1994_2', 'Friends', '1994', 'Somelibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('F1994_3', 'Friends', '1994', 'Somelibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('F1994_4', 'Friends', '1994', 'Somelibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('F1994_5', 'Friends', '1994', 'Somelibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('GOT2016_1', 'Game of Thrones','2016', 'NEUlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('GOT2016_2', 'Game of Thrones','2016', 'SYlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('GOT2016_3', 'Game of Thrones','2016', 'Somelibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('GOT2017_1', 'Game of Thrones','2017', 'NEUlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('GOT2017_2', 'Game of Thrones','2017', 'SYlibrary'); 
 insert into DVDCopy(copyID, title, releaseYear, lName) values('GOT2017_3', 'Game of Thrones','2017', 'Somelibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('MF2009_2', 'Modern Family','2009', 'SYlibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('MF2009_3', 'Modern Family','2009', 'NEUlibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('MF2009_4', 'Modern Family','2009', 'Nanhulibray');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('SM1948_1', 'Superman','1948', 'NEUlibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('SM1948_2', 'Superman','1948', 'NEUlibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('LLD2016_1', 'La La Land','2016', 'NEUlibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('LLD2016_2', 'La La Land','2016', 'Nanhulibray');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('LLD2016_3', 'La La Land','2016', 'SYlibrary');
 insert into DVDCopy(copyID, title, releaseYear, lName) values('LLD2016_4', 'La La Land','2016', 'Somelibrary');


 -- 历史记录
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2017-1-1', '2017-1-15', 20161111,'HMT2017_1');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2017-3-13', '2017-4-17', 20161111,'WW2016_2');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2014-4-1', '2018-4-22', 20161111,'F1994_4');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-4-1', '2018-5-9', 20161111,'LLD2016_4');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-4-12', '2018-5-10', 20161111,'SM1948_2');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-3-1', '2018-3-10', 20162222,'HMT2017_2');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-3-10', '2018-3-13', 20162222,'SM1948_1');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-5-1', '2018-5-10', 20163333,'LLD2016_2');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2017-12-1', '2017-12-5', 20163333,'HMT2017_1');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-5-1', '2018-5-10', 20164444,'F1994_4');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2017-12-1', '2017-12-5', 20164444,'SM1948_2');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-5-11', '2018-5-13', 20164444,'HMT2017_3');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-5-11', '2018-5-13', 20164444,'GOT2016_1');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-4-2', '2018-4-9', 20165555,'GOT2016_2');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-4-2', '2018-4-30', 20165555,'F1994_1');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-4-11', '2018-4-29', 20165555,'LLD2016_4');
 insert into operate(rentDate, returnDate, membershipNum, copyID) values ('2018-3-11', '2018-4-7', 20165555,'HMT2017_1');
-- 借阅
 insert into operate(rentDate, membershipNum, copyID) values ('2018-5-10',20165555,'LLD2016_4');
 update DVDCopy SET lName = null where copyID = 'LLD2016_4';
 insert into operate(rentDate, membershipNum, copyID) values ('2018-5-11',20165555,'SM1948_2');
 update DVDCopy SET lName = null where copyID = 'SM1948_2';
 insert into operate(rentDate, membershipNum, copyID) values ('2018-5-11',20164444,'WW2016_4');
 update DVDCopy SET lName = null where copyID = 'WW2016_4';
 

