

USE `payment`;
DROP procedure IF EXISTS `search`;

DELIMITER $$
USE `payment`$$
-- ----------------------------
-- 查询存储过程
-- ----------------------------
CREATE PROCEDURE `search` ( in p_ID int, in selectedDay date)
BEGIN
     DECLARE notice varchar(500);
     declare recordNumber int ;
     declare existArrearge int;
     select count(*) into recordNumber from deviceuser where ID=p_ID;
     select count(*) into existArrearge from device join deviceuser using (ID) join arrearagerecord using(DID) where ID=p_ID and finish=0;-- 用于判断是否欠费
     
     if (recordNumber=0) then
        set notice='不存在此客户';
        select notice;
      else -- 找到此用户    
        if(existArrearge=0) then-- 如果该用户不欠费
            set notice='不欠费，欠费金额为0';
            select username as 用户名, address as 地址,selectedDay as 今日日期, notice
            from deviceuser
            where ID=p_ID;
        else
            select username , address , round(sum(capital+overdueFine),2) as shouldPay, round(SUM(receivedAmount),2) AS alreadyPay
             from (
				select id,address, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
				from device join deviceuser using (ID) join arrearagerecord using(DID)
				where  selectedDay<=payableDate and selectedDay>=arrearageDate and finish=0
				union
				/*01设备*/
				/*超过应缴日期未还款*/
				select id,address, username,capital,(capital*datediff(selectedDay,payableDate)*0.001) as overduefine,
						receivedAmount,arrearageDate, payableDate, payDate,DID,finish
				from device join deviceuser using (ID) join arrearagerecord using(DID)
				where deviceType=01 and selectedDay>payableDate and finish=0
				union
				/*02设备*/
				/*跨年*/
				select id,address, username, capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(now())-1 day))+1)*0.002
														+capital*(datediff(date_sub(selectedDay,interval dayofyear(selectedDay)-1 day),payableDate))*0.003) as overduefine,
                                                        receivedAmount,arrearageDate,payableDate,payDate,DID,finish
				from device join deviceuser using (ID) join arrearagerecord using(DID)
				where deviceType=02 and year(payableDate)<year(selectedDay) and finish=0
				union
				/*未跨年*/
				select id,address, username,capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(selectedDay)-1 day))+1)*0.002) as overduefine,
						receivedAmount,arrearageDate,payableDate,payDate,DID,finish
				from device join deviceuser using (ID) join arrearagerecord using(DID)
				where deviceType=02 and year(payableDate)=year(selectedDay) and finish=0
				union
				select id,address, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish
				from device join deviceuser using (ID) join arrearagerecord using(DID)
				where finish=1) T
            where T.ID=p_ID and finish=0;
        end if;
     end if;
            
END$$
DELIMITER ;
	
USE `payment`;
DROP procedure IF EXISTS `commonPay`;
-- 缴费逻辑
DELIMITER $$
USE `payment`$$
CREATE PROCEDURE `commonPay` (in serialNumber double,in p_ID int,in p_accountNumber int,in p_paymentAmount double, in p_DID int, in selectedDay date)
BEGIN
    declare countDID int;
    declare notice varchar(500);
    declare countDate int;
    declare p_payableDate date;
    declare countAccount double;
    declare serialNum int;
    declare i int;
    declare p_bankID varchar(20) ;
    declare actualFee double;
    declare p_receivedAmount double;
    declare userBalance double;
    declare userBalance2 double;
    declare p_ID_to int;
    declare storeBalance double;
    -- 用于记录原值，用来最后更新用户余额以及缴费记录表
    declare p_paymentAmount2 double;
    set p_paymentAmount2=p_paymentAmount;
    
    -- 查找是否有此设备,该设备主人为谁
    select count(*),ID into countDID,p_ID_to from device where DID=p_DID;
    select bankID into p_bankID from bankaccount where accountNumber=p_accountNumber;
            if(p_ID=p_ID_to) then
                select balance into userBalance from deviceuser where ID=p_ID;-- 获得我的用户余额
            else
                set userBalance=0;
            end if;
            set userBalance2=userBalance;
        
                select count(*) into countDate from arrearagerecord where DID=p_DID and finish=0;-- 这台设备一共有几个欠费记录
                set i=0;
                outer_lable: BEGIN
                    if(countDate=i) then
                        set notice='已无欠费账单' ;
                        select notice;-- 之后将多交的钱转入余额
                    else
                        while i<countDate do
                            select payableDate,(overdueFine+capital),receivedAmount into p_payableDate,actualFee, p_receivedAmount 
							   from (select id, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
									from device join deviceuser using (ID) join arrearagerecord using(DID)
									where  selectedDay<=payableDate and selectedDay>=arrearageDate and finish=0
									union
									select id, username,capital,(capital*datediff(selectedDay,payableDate)*0.001) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
									from device join deviceuser using (ID) join arrearagerecord using(DID)
									where deviceType=01 and selectedDay>payableDate and finish=0
									union
									select id, username, capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(now())-1 day))+1)*0.002
																			+capital*(datediff(date_sub(selectedDay,interval dayofyear(selectedDay)-1 day),payableDate))*0.003) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
									from device join deviceuser using (ID) join arrearagerecord using(DID)
									where deviceType=02 and year(payableDate)<year(selectedDay) and finish=0
									union
									select id, username,capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(selectedDay)-1 day))+1)*0.002) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
									from device join deviceuser using (ID) join arrearagerecord using(DID)
									where deviceType=02 and year(payableDate)=year(selectedDay) and finish=0
									union
									select id, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish
									from device join deviceuser using (ID) join arrearagerecord using(DID)
									where finish=1) T
								where T.DID=p_DID and T.finish=0 order by payableDate asc limit 0,1;
                            if((p_paymentAmount+userBalance)>=(actualFee-p_receivedAmount)) then -- 如果付的费用能够支付这个月的
                                if (userBalance<(actualFee-p_receivedAmount)) then-- 余额不足支付，则用到支付金
                                    -- 更新余额变化表
                                    jump_lable1: begin
                                    if(userBalance=0) then
                                        leave jump_lable1;
                                    end if;
                                    insert into balancechangesrecord(ID,changeTime,beforeChange,afterChange,changeAmount,reason)
                                    values(p_ID, current_timestamp(),userBalance,0, userBalance,'01');
                                    
                                    end jump_lable1;
                                    set p_paymentAmount=p_paymentAmount-((actualFee-p_receivedAmount)-userBalance) ;
                                    set userBalance=0 ;
                                else
                                    insert into balancechangesrecord(ID,changeTime,beforeChange,afterChange,changeAmount,reason)
                                    values(p_ID, current_timestamp(),userBalance,userBalance-(actualFee-p_receivedAmount),(actualFee-p_receivedAmount),'01');
                                    set userBalance=userBalance-(actualFee-p_receivedAmount);
                                end if;
                                
                                update arrearagerecord
                                set receivedAmount=actualFee,actualReceivebleAmount=actualFee,payDate=selectedDay,overdueFine=(actualFee-capital),finish=1
                                where DID=p_DID and payableDate=p_payableDate;
                            else if((p_paymentAmount+userBalance)<(actualFee-p_receivedAmount)) then -- 费用不够支付这个月的
                                jump_lable2: begin
                                    if(userBalance=0) then
                                        leave jump_lable2;
                                    end if;
                                insert into balancechangesrecord(ID,changeTime,beforeChange,afterChange,changeAmount,reason)
                                values(p_ID, current_timestamp(),userBalance,0, userBalance,'01');
                                end jump_lable2;
                                
                                update   arrearagerecord
                                set     receivedAmount=p_receivedAmount+(p_paymentAmount+userBalance)
                                where DID=p_DID and payableDate=p_payableDate;
                                
                                set p_paymentAmount=0;
                                set userBalance=0 ;
                                end if;
                            end if;
                            if(p_paymentAmount<=0) then-- 如果支付金额已经未0，则直接跳出循环
                                leave outer_lable;
                            end if;
                        set i=i+1;
                        end while;
                    end if;
                 end outer_lable;
               
                  insert into paymentrecord (serialNumber,DID,bankID,accountNumber,paymentAmount,inDeviceAmount,paymentDate,reversal)
                  values(serialNumber,p_DID,p_bankID,p_accountNumber,p_paymentAmount2,(userBalance2-userBalance)+(p_paymentAmount2-p_paymentAmount),selectedDay,false);
                  if((userBalance+p_paymentAmount)>0) then-- 如果余额>0 即，未使用银行转入的金额，多余的金额加入账户余额，更新用户余额，插入余额变化记录
                   if(p_ID<>p_ID_to) then
                        select balance into userBalance from deviceuser where ID=p_ID;
                    end if;
                        insert into balancechangesrecord(ID,changeTime,beforeChange,afterChange,changeAmount,reason)
                        values(p_ID, current_timestamp(),userBalance,userBalance+p_paymentAmount, p_paymentAmount,'02');
                  end if;
                    
                    update deviceuser set balance=userBalance+p_paymentAmount
                    where ID=p_ID;
END$$

DELIMITER ;



USE `payment`;
DROP procedure IF EXISTS `pay`;
-- 用户缴费存储过程
DELIMITER $$
USE `payment`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pay`(in p_ID int,in p_accountNumber int,in p_paymentAmount double, in p_DID int,in selectedDay date)
BEGIN
    declare countDID int;
    declare notice varchar(500);
    declare countDate int;
    declare p_payableDate date;
    declare countAccount double;
    declare serialNum int;
    declare i int;
    declare p_bankID varchar(20) ;
    declare actualFee double;
    declare p_receivedAmount double;
    declare userBalance double;
    declare userBalance2 double;
    declare p_ID_to int;
    declare storeBalance double;
    -- 用于记录原值，用来最后更新用户余额以及缴费记录表
    declare p_paymentAmount2 double;
    set p_paymentAmount2=p_paymentAmount;
    select selectedDay;
    -- 查找是否有此设备,该设备主人为谁
    select count(*),ID into countDID,p_ID_to from device where DID=p_DID;
    select bankID into p_bankID from bankaccount where accountNumber=p_accountNumber;
    
   
    if(countDID=0) then
        set notice='输入错误，本公司无此设备';
        select notice;
    else    -- 输入正确，选择卡号进行缴费
        select count(*) into countAccount from bankaccount where accountNumber=p_accountNumber and ID=p_ID;
        if(countAccount=0) then
            set notice='输入错误';
            select notice;
        else-- 进行缴费
            if(p_paymentAmount<0) then
                set notice='缴费金额错误，金额不能为负数';
                select notice;
            else
                    select serialNumber into serialNum from paymentrecord where reversal=0 order by serialNumber desc limit 0,1;-- 用于记录的序列号
                    set serialNum=serialNum+1;
                    -- 先更新银行表格，银行清单表中需要增加一条缴费记录
                    insert into serialrecord(paymentDate,serialNumber,paymentAmount,accountNumber,bankID)
                    values(selectedDay,serialNum,p_paymentAmount,p_accountNumber,p_bankID);
                call commonPay(serialNum,p_ID,p_accountNumber,p_paymentAmount,p_DID,selectedDay);
            end if;
        end if;
    end if;
    
END$$

DELIMITER ;

USE `payment`;
DROP procedure IF EXISTS `checkTotal`;
-- 对总账
DELIMITER $$
USE `payment`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkTotal`(in p_bankID varchar(20), in p_checkingTotal int, in p_checkingAmount double,in p_checkDate date)
BEGIN
    declare p_companyCheckingTotal int;
    declare p_companyCheckingAmount double;
    declare bankCount int;
   
    select count(*),ifnull(sum(paymentAmount),0) into p_companyCheckingTotal,p_companyCheckingAmount -- 计算公司交易笔数/交易金额
    from paymentrecord
    where paymentDate=DATE_SUB(p_checkDate,INTERVAL 1 DAY) and bankID=p_bankID ;
    
    insert into resultrecord(checkDate,companyCheckingTotal,checkingTotal,companyCheckingAmount,checkingAmount,success,bankID)
        values(p_checkDate, p_companyCheckingTotal,p_checkingTotal,p_companyCheckingAmount,p_checkingAmount,true,p_bankID);-- 如果成功，则插入成功的记录

    if(!(p_companyCheckingTotal=p_checkingTotal&&p_companyCheckingAmount=p_checkingAmount))  then
        update  resultrecord  
        set success=false
        where checkDate=p_checkDate and bankID=p_bankID;
        SELECT p_checkDate,p_bankID;
        call check_detail(p_checkDate,p_bankID);
    end if;
        
END$$

DELIMITER ;

USE `payment`;
DROP procedure IF EXISTS `check_detail`;
-- 对细账
DELIMITER $$
USE `payment`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_detail`(in p_checkDate date,in p_bankID varchar(20))
BEGIN
        DECLARE p_serialNumber int;
        DECLARE p_paymentAmount double;
        DECLARE p_accountNumber double;
        DECLARE done integer default 0;
        DECLARE count_check int;
        DECLARE bankrecordTotal int;
        DECLARE companyrecordTotal int;
        DECLARE companyCheckingAmount double;

        DECLARE myCursor1 cursor for
        select serialNumber, paymentAmount, accountNumber, bankID
        from serialrecord  
        where paymentDate=DATE_SUB(p_checkDate,INTERVAL 1 DAY);
        DECLARE myCursor2 cursor for
        select serialNumber, paymentAmount, accountNumber, bankID
        from paymentrecord
        where paymentDate=DATE_SUB(p_checkDate,INTERVAL 1 DAY);
        DECLARE continue HANDLER FOR NOT FOUND SET done=1;
        
        select companyCheckingTotal, checkingTotal into companyrecordTotal, bankrecordTotal
        from resultrecord
        where checkDate=p_checkDate and bankID=p_bankID;  -- 记录两个公司记录条数差，可判断是哪个公司少了记录
        select companyrecordTotal,bankrecordTotal;
        if(companyrecordTotal>=bankrecordTotal) then-- 如果公司记录多，则银行方面记录缺失
            open myCursor2;
             fetch myCursor2 into p_serialNumber,p_paymentAmount,p_accountNumber,p_bankID;
             repeat
                
                select count(*) into count_check from serialrecord where serialNumber=p_serialNumber;
				
                if(ifnull(count_check,0)=0) then
                    insert into checkerrorrecord(serialNumber,checkDate,checkErrorReason,bankID)
                    values(p_serialNumber,p_checkDate,'01',p_bankID);
                else
                    select count(*) into count_check from serialrecord where paymentAmount=p_paymentAmount;
   
                    if(ifnull(count_check,0)=0) then
                        insert into checkerrorrecord(serialNumber,checkDate,checkErrorReason,bankID)
                        values(p_serialNumber,p_checkDate,'03',p_bankID);
                    end if;
                end if;
                
                fetch myCursor2 into p_serialNumber,p_paymentAmount,p_accountNumber,p_bankID;
                
            until done end repeat;
            close myCursor2;
        else
             open myCursor1;
             fetch myCursor1 into p_serialNumber,p_paymentAmount,p_accountNumber,p_bankID;
             repeat
               
                select count(*) into count_check from paymentrecord where serialNumber=p_serialNumber;
                if(ifnull(count_check,0)=0) then
                    insert into checkerrorrecord(serialNumber,checkDate,checkErrorReason,bankID)
                    values(p_serialNumber,p_checkDate,'02',p_bankID);
                else
                    select count(*) into count_check from paymentrecord where paymentAmount=p_paymentAmount;
                    if(ifnull(count_check,0)=0) then
                        insert into checkerrorrecord(serialNumber,checkDate,checkErrorReason,bankID)
                        values(p_serialNumber,p_checkDate,'03',p_bankID);
                    end if;
                end if;
                
                fetch myCursor1 into p_serialNumber,p_paymentAmount,p_accountNumber,p_bankID;
                
            until done end repeat;
            close myCursor1;
        end if;
        
END$$

DELIMITER ;



USE `payment`;
DROP procedure IF EXISTS `meterRead`;
-- 抄表
DELIMITER $$
USE `payment`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `meterRead`(in p_dateOfReadMeter date,in p_DID int,in p_currentMeterAmount int, in p_meterReader varchar(50))
BEGIN

    declare userBalance double;
    declare accountNumber double;
    declare p_ID int;
    declare count int;
    declare p_username varchar(20);
    declare p_capital double;
    declare p_overduefine double;
    declare p_receivedAmount double;
    declare p_payableDate date;
    declare shouldPay double;
    declare counts int default 0;
    declare userBalanceBefore double;
    declare p_beginMeterAmount int;
    declare p_basicAmount double;
    declare appendFee2Rate double;
    declare p_deviceType varchar(5);
    declare notice varchar(20);
    declare i int;
    declare selectedDay date;
    
    select currentMeterAmount into p_beginMeterAmount from arrearagerecord where DID=p_DID order by payableDate desc limit 0,1;
    select count(*) into counts from arrearagerecord where DID=p_DID order by payableDate desc;
   if(counts=0) then
        set p_beginMeterAmount=0;
   end if;
   select deviceType into p_deviceType from device where DID=p_DID;
    exit_lable: begin
    if(p_currentMeterAmount<p_beginMeterAmount) then
        set notice='输入错误';
        select notice;
        leave exit_lable;
    end if;
    -- 生成抄表记录
    insert into meterrecord(DID, dateOfReadMeter, currentMeterAmount,meterReader)
    values(p_DID,p_dateOfReadMeter,p_currentMeterAmount,p_meterReader);
    if(p_deviceType='01') then
        set appendFee2Rate=0.1;
    else
        set appendFee2Rate=0.15;
    end if;
    
    select balance,ID into userBalance,p_ID from deviceuser join device using(ID) where DID=p_DID;-- 获得此用户余额
    set userBalanceBefore=userBalance;
   
-- 从余额扣费，将欠费记录补上   类似于缴费过程
   
    set p_basicAmount=(p_currentMeterAmount-p_beginMeterAmount)*0.46;
    insert into arrearagerecord(DID,arrearageDate,beginMeterAmount,currentMeterAmount,basicAmount,appendAmount1,
                                appendAmount2,capital,receivedAmount,payableDate)
                                values(p_DID,p_dateOfReadMeter,p_beginMeterAmount,p_currentMeterAmount,p_basicAmount,p_basicAmount*0.08,
                                       p_basicAmount*appendFee2Rate,p_basicAmount+ (p_basicAmount*0.08) +p_basicAmount*appendFee2Rate,0,
                                        date_sub(date_add(p_dateOfReadMeter - day(p_dateOfReadMeter) +1,interval 1 month ),interval 1 day));
    select count(*) into count from arrearagerecord where DID=p_DID and finish=0;
    set selectedDay =p_dateOfReadMeter;
    if(ifnull(count,0)>0) then -- 有欠费记录,补欠费记录
        if(userBalance>0) then
        set i=0;
            out_lable: begin
                while(i<ifnull(count,0)) do
                if(userBalance=0) then
                    leave out_lable;
                end if;
                select username, capital, overduefine, receivedAmount, payableDate, DID
                into p_username, p_capital, p_overduefine, p_receivedAmount, p_payableDate, p_DID
                from (select id, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where  selectedDay<=payableDate and selectedDay>=arrearageDate and finish=0
                    union
                    /*01设备*/
                    /*超过应缴日期未还款*/
                    select id, username,capital,(capital*datediff(selectedDay,payableDate)*0.001) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=01 and selectedDay>payableDate and finish=0
                    union
                    /*02设备*/
                    /*跨年*/
                    select id, username, capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(now())-1 day))+1)*0.002
                                                            +capital*(datediff(date_sub(selectedDay,interval dayofyear(selectedDay)-1 day),payableDate))*0.003) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=02 and year(payableDate)<year(selectedDay) and finish=0
                    union
                    /*未跨年*/
                    select id, username,capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(selectedDay)-1 day))+1)*0.002) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=02 and year(payableDate)=year(selectedDay) and finish=0
                    union
                    select id, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where finish=1) T
                where T.DID=p_DID and T.finish=0
                order by payableDate asc
                limit 0,1;
                    -- 开始进行缴费操作
                    set shouldPay=p_capital+p_overduefine-p_receivedAmount;
                    if(userBalance>shouldPay) then -- 如果余额足够支付
                        set userBalance=userBalance-shouldPay;
                        update arrearagerecord
                        set receivedAmount=p_capital+p_overduefine,actualReceivebleAmount=p_capital+p_overduefine,payDate=p_dateOfReadMeter,overdueFine=p_overduefine,finish=1
                        where DID=p_DID and payableDate=p_payableDate;
                    else
                        update arrearagerecord
                        set receivedAmount=userBalance
                        where DID=p_DID and payableDate=p_payableDate;
                        set userBalance=0;
                    end if;
                    insert into balancechangesrecord(ID, changeTime,beforeChange,afterChange,changeAmount,reason)
                    values(p_ID, current_timestamp(),userBalanceBefore,userBalance,userBalanceBefore-userBalance,'01');
                    set userBalanceBefore=userBalance;
                    set i=i+1;
                    
                end while;
                end out_lable;
        end if;
         update deviceuser set balance=userBalance
         where ID=p_ID;
    end if;
    
    end exit_lable;
END$$

DELIMITER ;

USE `payment`;
DROP procedure IF EXISTS `balancePayForReverse`;
-- 用于冲正的余额缴费
DELIMITER $$
USE `payment`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `balancePayForReverse`(in p_DID int,in p_serialNumber double, in selectedDay date)
BEGIN

    declare userBalance double;
    declare accountNumber double;
    declare p_ID int;
    declare count int;
    declare p_username varchar(20);
    declare p_capital double;
    declare p_overduefine double;
    declare p_receivedAmount double;
    declare p_payableDate date;
    declare shouldPay double;
    declare done Integer default 0;
    declare userBalanceBefore double;
    declare userBalance2 double;
    declare totalAmount double default 0;
    declare p_beginMeterAmount int;
    declare p_basicAmount double;
    declare appendFee2Rate double;
    declare p_deviceType varchar(5);
    declare notice varchar(20);
    declare i int;
    
    select balance,ID into userBalance,p_ID from deviceuser join device using(ID) where DID=p_DID;-- 获得此用户余额
    set userBalanceBefore=userBalance;
    set userBalance2=userBalance;
    select count(*) into count from arrearagerecord where DID=p_DID and finish=0;
-- 从余额扣费，将欠费记录补上   类似于缴费过程
    
    if(ifnull(count,0)>0) then -- 有欠费记录,补欠费记录
        if(userBalance>0) then
        set i=0;
            exitLable: begin
                while(i<count) do
             
                if(userBalance=0) then
                    leave exitLable;
                end if;
                select username, capital, overduefine, receivedAmount, payableDate, DID
                into p_username, p_capital, p_overduefine, p_receivedAmount, p_payableDate, p_DID
                from (select id, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where  selectedDay<=payableDate and selectedDay>=arrearageDate and finish=0
                    union
                    /*01设备*/
                    /*超过应缴日期未还款*/
                    select id, username,capital,(capital*datediff(selectedDay,payableDate)*0.001) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=01 and selectedDay>payableDate and finish=0
                    union
                    /*02设备*/
                    /*跨年*/
                    select id, username, capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(now())-1 day))+1)*0.002
                                                            +capital*(datediff(date_sub(selectedDay,interval dayofyear(selectedDay)-1 day),payableDate))*0.003) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=02 and year(payableDate)<year(selectedDay) and finish=0
                    union
                    /*未跨年*/
                    select id, username,capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(selectedDay)-1 day))+1)*0.002) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=02 and year(payableDate)=year(selectedDay) and finish=0
                    union
                    select id, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where finish=1) T
                where T.DID=p_DID and T.finish=0
                order by payableDate asc
                limit 0,1;
                    -- 开始进行缴费操作
                    set shouldPay=p_capital+p_overduefine-p_receivedAmount;
                    set totalAmount=totalAmount+shouldPay;
                    select totalAmount,shouldPay;
                    if(userBalance>shouldPay) then -- 如果余额足够支付
                        set userBalance=userBalance-shouldPay;
                        
                        update arrearagerecord
                        set receivedAmount=p_capital+p_overduefine,actualReceivebleAmount=p_capital+p_overduefine,payDate=selectedDay,overdueFine=p_overduefine,finish=1
                        where DID=p_DID and payableDate=p_payableDate;
                    else
                        
                        update arrearagerecord
                        set receivedAmount=userBalance
                        where DID=p_DID and payableDate=p_payableDate;
                        set userBalance=0;  
                        
                    end if;
                 
                    insert into balancechangesrecord(ID, changeTime,beforeChange,afterChange,changeAmount,reason)
                    values(p_ID, current_timestamp(),userBalanceBefore,userBalance,userBalanceBefore-userBalance,'01');
                    set userBalanceBefore=userBalance;
                    set i=i+1;
               
                end while;
                end exitLable;
        end if;
        
        
         update deviceuser set balance=userBalance
         where ID=p_ID;
         
         if(userBalance2<totalAmount) then
            update paymentrecord
            set inDeviceAmount=userBalance2
            where serialNumber=p_serialNumber;
         else
            update paymentrecord
            set inDeviceAmount=totalAmount
            where serialNumber=p_serialNumber;
         end if;
    end if;
END$$

DELIMITER ;

USE `payment`;
DROP procedure IF EXISTS `reverse`;
-- 冲正
DELIMITER $$
USE `payment`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `reverse`(in p_serialNumber int,in selectedDay date)
BEGIN
    
    declare countDID int;
    declare count int;
    declare countDate int;
    declare p_payableDate date;
    declare amount double;-- 设备最后更新缴费表时总共进入设备的钱
    declare p_inDeviceAmount_to double;
    declare p_inDeviceAmount_this double;
    declare p_inDeviceAmount_thisTotal double;
    declare p_accountNumber_to double;
    declare p_accountNumber_this double;
    declare p_paymentAmount_to double;  -- 要冲回的设备交的钱
    declare p_paymentAmount_this double;
    declare p_paymentAmount_thisTotal double; -- 交错的设备交的
    declare i int;
    declare p_bankID varchar(20);   
    declare actualFee double;
    declare p_receivedAmount double;
    declare userBalance_this double;
    declare userBalance_to double;
    declare p_ID_to int;
    declare p_ID_this int;
    declare p_DID_this int;
    declare totalAmount double default 0;
    declare p_capital double;
    declare p_username varchar(20);
    declare p_overduefine double;
    declare userBalanceBefore double;
    declare shouldPay double;
    declare p_serialNumber_this double;
    select count(*),DID into count,p_DID_this from paymentrecord where serialNumber=p_serialNumber; -- 公司是否有此记录
    select ID into p_ID_to from paymentrecord join bankaccount using(accountNumber) where serialNumber=p_serialNumber; -- 要冲正回的设备的主人 2007
    select ID into p_ID_this from device where DID=p_DID_this;
    if(ifnull(count,0)<>0) then  -- 公司有此流水号记录，开始进行冲正操作
        -- select count(*) from paymentrecord where paymentDate=selectedDay; -- 找到今天所有的支付记录条数
        -- 对于付款者    可找到自己付款的卡号，付款的到别人账上的总金额    
        select accountNumber,paymentAmount,round(inDeviceAmount,2) ,bankID into p_accountNumber_to,p_paymentAmount_to ,p_inDeviceAmount_to,p_bankID
        from paymentrecord
        where serialNumber=p_serialNumber;
        -- 只需要拿回p_inDeviceAmount_to 便可
        select balance into userBalance_to from deviceuser where ID=p_ID_to;  -- 此人原本有多少余额
        update deviceuser
        set balance=userBalance_to+p_inDeviceAmount_to
        where ID=p_ID_to;
   
        if(p_inDeviceAmount_to<>0) then
            insert into balancechangesrecord(ID, changeTime,beforeChange,afterChange,changeAmount,reason)
            values (p_ID_to,current_timestamp(),userBalance_to,userBalance_to+p_inDeviceAmount_to,p_inDeviceAmount_to,'02');
        end if;
        update paymentrecord
        set reversal=1
        where serialNumber=p_serialNumber;
        
        -- 对于收款者，找到这台设备所有收的款， 进入设备的金额
        select round(ifnull(sum(paymentAmount),0),2) , round(ifnull(sum(inDeviceAmount),0),2),count(*)  into p_paymentAmount_thisTotal,p_inDeviceAmount_thisTotal,count
        from paymentrecord join device using(DID)
        where paymentDate=selectedDay and DID=p_DID_this;
        
        set p_inDeviceAmount_this=p_inDeviceAmount_thisTotal-p_inDeviceAmount_to;
        
        select balance into userBalance_this from deviceuser where ID=p_ID_this;
        update deviceuser
        set balance=userBalance_this+p_inDeviceAmount_this
        where ID=p_ID_this;
        
        if(p_inDeviceAmount_this<>0) then
            insert into balancechangesrecord(ID, changeTime,beforeChange,afterChange,changeAmount,reason)
            values (p_ID_this,current_timestamp(),userBalance_this,userBalance_this+p_inDeviceAmount_this,p_inDeviceAmount_this,'02');
        end if;
        
        select balance into userBalance_this from deviceuser where ID=p_ID_this;-- 此人有多少余额
        select count(*) into count from arrearagerecord where DID=p_DID_this and receivedAmount<>0;
      
        set i=0;
        out_lable: begin
        while(i<count) do
            
            select round(receivedAmount,2) ,round(capital+overduefine,2) ,payableDate into p_receivedAmount,actualFee,p_payableDate
            from (select id, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where  selectedDay<=payableDate and selectedDay>=arrearageDate and finish=0
                    union
                    /*01设备*/
                    /*超过应缴日期未还款*/
                    select id, username,capital,(capital*datediff(selectedDay,payableDate)*0.001) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=01 and selectedDay>payableDate and finish=0
                    union
                    /*02设备*/
                    /*跨年*/
                    select id, username, capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(now())-1 day))+1)*0.002
                                                            +capital*(datediff(date_sub(selectedDay,interval dayofyear(selectedDay)-1 day),payableDate))*0.003) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=02 and year(payableDate)<year(selectedDay) and finish=0
                    union
                    /*未跨年*/
                    select id, username,capital,(capital*(datediff(selectedDay,date_sub(selectedDay,interval dayofyear(selectedDay)-1 day))+1)*0.002) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where deviceType=02 and year(payableDate)=year(selectedDay) and finish=0
                    union
                    select id, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish
                    from device join deviceuser using (ID) join arrearagerecord using(DID)
                    where finish=1) T
            where T.DID=p_DID_this and T.receivedAmount<>0
            order by T.payableDate desc
            limit 0,1;
            
            
            
            -- 更新欠费记录表回原样
            
            if(p_inDeviceAmount_thisTotal<=p_receivedAmount) then
                
                set p_receivedAmount=p_receivedAmount-p_inDeviceAmount_thisTotal;
                set p_inDeviceAmount_thisTotal=0;
            else
                set p_inDeviceAmount_thisTotal=p_inDeviceAmount_thisTotal-p_receivedAmount;
                set p_receivedAmount=0;
            end if;
            
            update arrearagerecord
            set actualReceivebleAmount=null, receivedAmount=p_receivedAmount, overdueFine=null, payDate=null,finish=0
            where DID=p_DID_this and receivedAmount<>0 and payableDate=p_payableDate
            order by payableDate desc;
            if( p_inDeviceAmount_thisTotal<=0) then
                leave out_lable;
            end if;
            set i=i+1;
        end while;
        end out_lable;
        
      select count(*) into count from paymentrecord join device using(DID) join deviceuser using(ID)  where ID=p_ID_this and paymentDate=selectedDay;
      set i=0;
      while(i<count) do
        select serialNumber into p_serialNumber_this
        from paymentrecord join device using(DID) join deviceuser using(ID)
        where accountNumber=p_accountNumber_this and paymentDate=selectedDay order by serialNumber asc limit i,1;
        select p_serialNumber_this;
        call balancePayForReverse(p_DID_this,p_serialNumber_this,selectedDay);
        set i=i+1;
      end while;
    end if;
END$$

DELIMITER ;

