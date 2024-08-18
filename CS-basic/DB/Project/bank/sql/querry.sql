
-- 1. 查询出所有欠费用户
select distinct id, username, receivedAmount
from device join deviceuser using (ID) join arrearagerecord using (DID)
where finish=0;
                

-- 2. 查询出拥有超过2个设备的用户
select distinct id, username, count(*) as amount
from device join deviceuser using(id)
group by id
having amount>2;


-- 顺便能够查询出欠费两个以上设备的用户
select distinct id, username
from (select *, count(*) as amount
      from device join deviceuser using(id)
      group by id
      having amount>2)
as T join arrearagerecord using (DID)
where finish=0
group by did;


-- 3. 某天电力公司应收，实收
set @selectedDay:='2018-08-30';
select @selectedDay as selectedDay, sum(capital), sum(overduefine),
        sum(receivedAmount), sum(overduefine+capital) as actualFee, sum(receivedAmount),round(sum(capital+overdueFine-receivedAmount),2) as 应收费用
from (select id, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
        from device join deviceuser using (ID) join arrearagerecord using(DID)
        where  @selectedDay<payableDate and @selectedDay>=arrearageDate and finish=0
        union
        select id, username,capital,(capital*datediff(@selectedDay,payableDate)*0.001) as overduefine,
        receivedAmount,arrearageDate, payableDate, payDate,DID,finish
        from device join deviceuser using (ID) join arrearagerecord using(DID)
        where deviceType=01 and @selectedDay>payableDate and finish=0
        union
        select id, username, capital,(capital*(datediff( @selectedDay, date_sub( @selectedDay, interval dayofyear(now())-1 day))+1)*0.002
                                +capital*(datediff(date_sub(@selectedDay,interval dayofyear(@selectedDay)-1 day),payableDate))*0.003) as overduefine,
                                                receivedAmount,arrearageDate,payableDate,payDate,DID,finish
        from device join deviceuser using (ID) join arrearagerecord using(DID)
        where deviceType=02 and year(payableDate)<year(@selectedDay) and finish=0
        union
        select id, username,capital,(capital*(datediff(@selectedDay,date_sub(@selectedDay,interval dayofyear(@selectedDay)-1 day))+1)*0.002) as overduefine,
                                                receivedAmount,arrearageDate,payableDate,payDate,DID,finish
        from device join deviceuser using (ID) join arrearagerecord using(DID)
        where deviceType=02 and year(payableDate)=year(@selectedDay) and finish=0
        union
        select id, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish
        from device join deviceuser using (ID) join arrearagerecord using(DID)
        where finish=1) T
where finish=0;

-- 4. 查询出欠费半年的用户
select distinct id, username, did, arrearageDate
from device join deviceuser using (ID) join arrearagerecord A using(DID)
where beginMeterAmount=0 and finish=0 and datediff(@selectedDay,arrearageDate)>182;


-- 5. 查询任意用户的欠费金额
set @id=1002;
select @selectedDay as 指定日期, username,sum(capital) ,sum(overduefine) ,sum(receivedAmount) , payableDate,sum(overduefine+capital) as actualFee,
        DID,sum(round(capital+overdueFine-receivedAmount,2)) as 应收费用
from (select id, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish
from device join deviceuser using (ID) join arrearagerecord using(DID)
where  @selectedDay<=payableDate and @selectedDay>=arrearageDate and finish=0
union
/*01设备*/
/*超过应缴日期未还款*/
select id, username,capital,(capital*datediff(@selectedDay,payableDate)*0.001) as overduefine,
        receivedAmount,arrearageDate, payableDate, payDate,DID,finish
from device join deviceuser using (ID) join arrearagerecord using(DID)
where deviceType=01 and @selectedDay>payableDate and finish=0
union
/*02设备*/
/*跨年*/
select id, username, capital,(capital*(datediff(@selectedDay,date_sub(@selectedDay,interval dayofyear(now())-1 day))+1)*0.002
                                        +capital*(datediff(date_sub(@selectedDay,interval dayofyear(@selectedDay)-1 day),payableDate))*0.003) as overduefine,
                                        receivedAmount,arrearageDate,payableDate,payDate,DID,finish
from device join deviceuser using (ID) join arrearagerecord using(DID)
where deviceType=02 and year(payableDate)<year(@selectedDay) and finish=0
union
/*未跨年*/
select id, username,capital,(capital*(datediff(@selectedDay,date_sub(@selectedDay,interval dayofyear(@selectedDay)-1 day))+1)*0.002) as overduefine,
                                        receivedAmount,arrearageDate,payableDate,payDate,DID,finish
from device join deviceuser using (ID) join arrearagerecord using(DID)
where deviceType=02 and year(payableDate)=year(@selectedDay) and finish=0
union
select id, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish
from device join deviceuser using (ID) join arrearagerecord using(DID)
where finish=1) T
where finish=0 and T.id=@id;


-- 查询出某月用电量最高的用户
set @theMonth:=7;
select id, username, (currentMeterAmount-beginMeterAmount) as 用电量
from device join deviceuser using (ID) join arrearagerecord A using(DID)
where month(payableDate)=@theMonth
group by id
order by currentMeterAmount-beginMeterAmount desc
LIMIT 3;


-- 7. /*每个月缴费人数最多的一天*/

select month(paymentDate), paymentDate, count(paymentDate) as amount
from paymentrecord
group by month(paymentDate)
having (amount)>=all(select count(paymentDate) as amount
					from paymentrecord
					group by paymentDate
);

-- 8. /*按设备类型使用人数从高到低排序查询列出设备类型，使用人数。*/
select deviceType, count(distinct id) as amountOfUsers
from device
group by deviceType;


-- 9. /*统计某个月各银行缴费人次，从高到低排序。*/
set @month=8;
select @month, bankID, count(bankID) as amount from serialrecord   where month(paymentDate)=@month group by bankID order by count(bankID) desc;

-- 10. 所有新增用户
set @selectedDay='2018-09-02';
select distinct id, username, did, arrearageDate
from device join deviceuser using (ID) join arrearagerecord A using(DID)
where beginMeterAmount=0 and datediff(@selectedDay,arrearageDate)<182;