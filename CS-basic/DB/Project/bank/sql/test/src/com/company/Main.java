package com.company;

import  java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static String selectedDay = "2018-09-01";

    public static java.sql.Date strToDate(String strDate) {
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }
    /*SQL 部分*/
    public static String getDetailArrearage() {
        String sql = "select STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\") as selectedDay, username, sum(capital) as capital, sum(overduefine) as overduefine, sum(receivedAmount) as receivedAmount, payableDate,sum(overduefine+capital) as actualFee, DID,round(sum(capital+overdueFine-receivedAmount),2) as shouldPay\n" +
                "from (select id, username,capital,(capital*0) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish\n" +
                "from device join deviceuser using (ID) join arrearagerecord using(DID)\n" +
                "where  STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\")<=payableDate and STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\")>=arrearageDate and finish=0\n" +
                "union\n" +
                "/*01设备*/\n" +
                "/*超过应缴日期未还款*/\n" +
                "select id, username,capital,(capital*datediff(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"),payableDate)*0.001) as overduefine, receivedAmount,arrearageDate, payableDate, payDate,DID,finish\n" +
                "from device join deviceuser using (ID) join arrearagerecord using(DID)\n" +
                "where deviceType=01 and STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\")>payableDate and finish=0\n" +
                "union\n" +
                "/*02设备*/\n" +
                "/*跨年*/\n" +
                "select id, username, capital,(capital*(datediff(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"),date_sub(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"),interval dayofyear(now())-1 day))+1)*0.002\n" +
                "\t\t\t\t\t\t\t\t\t\t+capital*(datediff(date_sub(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"),interval dayofyear(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"))-1 day),payableDate))*0.003) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish\n" +
                "from device join deviceuser using (ID) join arrearagerecord using(DID)\n" +
                "where deviceType=02 and year(payableDate)<year(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\")) and finish=0\n" +
                "union\n" +
                "/*未跨年*/\n" +
                "select id, username,capital,(capital*(datediff(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"),date_sub(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"),interval dayofyear(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"))-1 day))+1)*0.002) as overduefine, receivedAmount,arrearageDate,payableDate,payDate,DID,finish\n" +
                "from device join deviceuser using (ID) join arrearagerecord using(DID)\n" +
                "where deviceType=02 and year(payableDate)=year(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\")) and finish=0\n" +
                "union\n" +
                "select id, username, capital, overduefine,receivedAmount,arrearageDate,payableDate,payDate,DID,finish\n" +
                "from device join deviceuser using (ID) join arrearagerecord using(DID)\n" +
                "where finish=1) T";
        return sql;
    }

    public static String getAllPeopleHaveArrearage() {
        String sql = getDetailArrearage() +
                " where finish=0\n" +
                "group by username;\n";
        return sql;
    }
    public static void executePeopleHaveArrearage(ResultSet res) throws SQLException {
        System.out.println("date       username  capital   overduefine    receivedAmount   payableDate   actualFee   DID   shouldPay");
        while (res.next()) {
            String date = res.getString("selectedDay");
            String username = res.getString("username");
            double capital = res.getDouble("capital");
            double overduefine = res.getDouble("overduefine");
            double receivedAmount = res.getDouble("receivedAmount");
            Date payableDate = res.getDate("payableDate");
            double actualFee = res.getDouble("actualFee");
            int DID = res.getInt("DID");
            double shouldPay = res.getDouble("shouldPay");
            System.out.println(date + "  " + username + "  " + capital + "  " + overduefine + "  " +
                    receivedAmount + "  " + payableDate + "  " + actualFee + "  " + DID + "  " + shouldPay);
        }
    }

    public static String getPeopleHaveArrearageOverHalfYear() {

        String sql = "-- 查询出欠费超过半年的用户\n" +
                "select distinct id, username, did, arrearageDate\n" +
                "from device join deviceuser using (ID) join arrearagerecord A using(DID)\n" +
                "where beginMeterAmount=0 and finish=0 and datediff(STR_TO_DATE('" + selectedDay + "',\"%Y-%m-%d\"),arrearageDate)>182;";
        return sql;
    }
    public static void executePeopleHaveArrearageOverHalfYear(ResultSet res) throws SQLException {
        System.out.println("id      username   did  arrearageDate");
        while (res.next()) {
            int id = res.getInt("id");
            String username = res.getString("username");
            int did = res.getInt("did");
            Date arrearageDate = res.getDate("arrearageDate");
            System.out.println(id + "     " + username + "     " + did + "     " + arrearageDate);
        }
    }

    //某月用电量最高前3名的用户
    public static String get3HigestUse(int month) {
        String sql = "select id, username, (currentMeterAmount-beginMeterAmount) as consumption\n" +
                "from device join deviceuser using (ID) join arrearagerecord A using(DID)\n" +
                "where month(payableDate)=" + month + "\n" +
                "group by id\n" +
                "order by currentMeterAmount-beginMeterAmount desc\n" +
                "LIMIT 3;";
        return sql;
    }
    public static void executeGet3HigestUse(ResultSet res) throws SQLException {
        System.out.println("id  username   consumption");
        while (res.next()) {
            int id = res.getInt("id");
            String username = res.getString("username");
            Double consumption = res.getDouble("consumption");
            System.out.println(id + "   " + username + "  " + consumption);
        }
    }


    public static String getArrearageDeviceOver2() {
        //4. generate the rental records of all members in 2018
        String sql = "-- 查询出欠费两个以上设备的用户\n" +
                "select distinct id, username\n" +
                "from (select *, count(*) as amount\n" +
                "      from device join deviceuser using(id)\n" +
                "      group by id\n" +
                "      having amount>2)\n" +
                "as T join arrearagerecord using(DID)\n" +
                "where finish=0\n" +
                "group by did;";
        return sql;
    }
    public static void executeGetArrearageDeviceOver2(ResultSet res) throws SQLException {
        System.out.println("id  username ");
        while (res.next()) {
            int id = res.getInt("id");
            String username = res.getString("username");
            System.out.println(id + "     " + username);
        }
    }

    public static String getArrearageAmountOfaPerson(int id) {
        //5. generate the records of DVDs that haven't been returned
        String sql = getDetailArrearage() + " where finish=0 and T.id=" + id + " ;";
        return sql;
    }

    //获得各设备使用人数，从高到低排序
    public static String getUsers() {
        //6. return the average money that normal members and premium members have spent
        String sql = "select deviceType, count(distinct id) as amountOfUsers\n" +
                "from device\n" +
                "group by deviceType;";
        return sql;
    }
    public static void executeGetUsers(ResultSet res) throws SQLException {
        System.out.println("deviceType  amountOfUsers");
        while (res.next()) {
            String deviceType = res.getString("deviceType");
            int amountOfUsers = res.getInt("amountOfUsers");
            System.out.println(deviceType + "     " + amountOfUsers + "     ");
        }
    }

    //获得银行缴费人次按数排序某月
    public static String getPaymentTotalPerMonth(int month) {
        //7. generate a list of normal members whose balance>=50rmb and have 3 or more rental
        String sql = "select bankID, count(bankID) as amount " +
                "from serialrecord   where month(paymentDate)" +
                "=" + month + " group by bankID order by count(bankID) desc;";
        return sql;
    }
    public static void executeGetPaymentTotalPerMonth(ResultSet res) throws SQLException {
        System.out.println("bankID   amount");
        while (res.next()) {
            String bankID = res.getString("bankID");
            int amount = res.getInt("amount");
            System.out.println(bankID + "     " + amount);
        }
    }

    //获得未冲过半年的新用户
    public static String getNewUser() {
        String sql = "select distinct id, username, did, arrearageDate\n" +
                "from device join deviceuser using (ID) join arrearagerecord A using(DID)\n" +
                "where beginMeterAmount=0 and datediff('" + selectedDay + "',arrearageDate)<182;";
        return sql;
    }
    public static void executeGetNewUser(ResultSet res) throws SQLException {
        System.out.println("id  username  did arrearageDate");
        while (res.next()) {
            int id = res.getInt("id");
            String username = res.getString("username");
            int did = res.getInt("did");
            Date arrearageDate = res.getDate("arrearageDate");
            System.out.println(id + " " + username + " " + did + " " + arrearageDate);
        }
    }

    //获得公司实收费用
    public static void executeGetActualFeeForCompany(ResultSet res) throws SQLException {
        System.out.println("date   shouldPay");
        double total=0;
        String date = null;
        double shouldPay=0;
        while (res.next()) {
             date = res.getString("selectedDay");
             shouldPay = res.getDouble("shouldPay");
            total+=shouldPay;
        }
        System.out.println(date + "  " +shouldPay);
    }

    public static String getPaymentDate() {
        String sql = "select month(paymentDate) as month, paymentDate, count(paymentDate) as amount\n" +
                "from paymentrecord\n" +
                "group by month(paymentDate)\n" +
                "having (amount)>=all(select count(paymentDate) as amount\n" +
                "\t\t\t\t\tfrom paymentrecord\n" +
                "\t\t\t\t\tgroup by paymentDate\n" +
                ");";
        return sql;
    }
    public static void executeGetPaymentDate(ResultSet res) throws SQLException {
        System.out.println("month   paymentDate   amount");
        while (res.next()) {
            String month=res.getString("month");
            Date paymentDate=res.getDate("paymentDate");
            int amount =res.getInt("amount");
            System.out.println(month + "  " +paymentDate+"  "+amount);
        }
    }

    /*存储过程部分*/
    public static void readMeter(Connection conn,String dateOfReadMeter,int DID,int currentMeterAmount,String meterReader) throws SQLException {
            java.sql.Date date=strToDate(dateOfReadMeter);
            //存储过程函数固定格式：{call xxx}
            CallableStatement cs = conn.prepareCall("{call meterRead(?,?,?,?)}");
            cs.setDate(1, date);
            cs.setInt(2,DID);
            cs.setInt(3,currentMeterAmount);
            cs.setString(4,meterReader);
            cs.executeUpdate();
    }

    public static void pay(Connection conn,int ID,int accountNumber,double paymentAmount,int DID,String day) throws SQLException {
        java.sql.Date date=strToDate(day);
        CallableStatement cs = conn.prepareCall("{call pay(?,?,?,?,?)}");
       cs.setInt(1,ID);
       cs.setInt(2,accountNumber);
       cs.setDouble(3,paymentAmount);
       cs.setInt(4,DID);
       cs.setDate(5,date);
        cs.executeUpdate();

    }

    public static void search(Connection conn,int ID,String day) throws SQLException {
        java.sql.Date date=strToDate(day);
        System.out.println(date);
        CallableStatement cs = conn.prepareCall("{call search(?,?)}");
        cs.setInt(1,ID);
        cs.setDate(2,date);
        cs.executeUpdate();
        ResultSet res=cs.executeQuery();
        System.out.println("用户名  地址  应收费用  实收费用");
        while (res.next()) {
            String username=res.getString("username");
            String address=res.getString("address");
            double shouldPay =res.getDouble("shouldPay");
            double alreadyPay=res.getDouble("alreadyPay");
            System.out.println(username + "  " +address+"  "+shouldPay+"  "+alreadyPay);
        }

    }

    public static void check(Connection conn,String bankID,int checkingTotal,double checkingAmount,String day) throws SQLException {
        java.sql.Date date=strToDate(day);
        CallableStatement cs = conn.prepareCall("{call checkTotal(?,?,?,?)}");
        cs.setString(1,bankID);
        cs.setInt(2,checkingTotal);
        cs.setDouble(3,checkingAmount);
        cs.setDate(4,date);
        cs.executeUpdate();

    }

    public static void reverse(Connection conn,int serialNumber,String day) throws SQLException {
        java.sql.Date date=strToDate(day);
        CallableStatement cs = conn.prepareCall("{call reverse(?,?)}");

        cs.setInt(1,serialNumber);
        cs.setDate(2,date);
        cs.executeUpdate();

    }


        public static void main (String[]args){
//      1.注册驱动
            ResultSet res = null;
            try {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    System.out.println("Driver could not be loaded");
                    System.exit(0);
                }
//            2.获取数据库连接
                String url = "jdbc:mysql://localhost:3306/payment?useSSL=false&serverTimezone=Asia/Shanghai";
                String user = "root";
                String password = "cgp5226926+123";
                Connection conn = DriverManager.getConnection(url, user, password);
//            3.获取数据操作的对象
                Statement statement = conn.createStatement();

                String sql;


                //获得所有有欠费的用户
                System.out.println("获得所有有欠费的用户");
                sql = getAllPeopleHaveArrearage();
                res = statement.executeQuery(sql);
                executePeopleHaveArrearage(res);
//                所有欠费超过半年的用户
                System.out.println("所有欠费超过半年的用户");
                sql = getPeopleHaveArrearageOverHalfYear();
                res = statement.executeQuery(sql);
                executePeopleHaveArrearageOverHalfYear(res);
//                每个月用电量最高的三名用户
                System.out.println("每个月用电量最高的三名用户");
                sql = get3HigestUse(7);
                res = statement.executeQuery(sql);
                executeGet3HigestUse(res);
//                拥有超过两个设备的用户
                System.out.println("拥有超过两个设备的用户");
                sql = getArrearageDeviceOver2();
                res = statement.executeQuery(sql);
                executeGetArrearageDeviceOver2(res);
//                获得各设备使用人数
                System.out.println("获得各设备使用人数");
                sql = getUsers();
                res = statement.executeQuery(sql);
                executeGetUsers(res);
//                获得某月缴费人次
                System.out.println("获得某月缴费人次");
                sql = getPaymentTotalPerMonth(8);
                res = statement.executeQuery(sql);
                executeGetPaymentTotalPerMonth(res);
    //                获得未超过半年的新用户
                System.out.println("获得未超过半年的用户");
                sql = getNewUser();
                res = statement.executeQuery(sql);
                executeGetNewUser(res);
//                 获得银行每天应收，实收
                System.out.println("获得银行每天应收，实收");
                sql = getDetailArrearage();
                res = statement.executeQuery(sql);
                executeGetActualFeeForCompany(res);

                System.out.println("获得每月缴费记录最多的那一天");
                sql = getPaymentDate();
                res = statement.executeQuery(sql);
                executeGetPaymentDate(res);

                System.out.println("\n\nsearch");
                search(conn,1000,"2018-09-01");
                System.out.println("\n\npay");
                pay(conn,1000,10001000,200,2000,"2018-09-01");
                sql = getAllPeopleHaveArrearage();
                res = statement.executeQuery(sql);
                executePeopleHaveArrearage(res);
                System.out.println("\n\nreadMeter");
                readMeter(conn,"2018-09-01",2001,450,"lalala");
                sql = getAllPeopleHaveArrearage();
                res = statement.executeQuery(sql);
                executePeopleHaveArrearage(res);
                System.out.println("\n\npay");
                pay(conn,1004,10001004,200,2003,"2018-09-01");
                sql = getAllPeopleHaveArrearage();
                res = statement.executeQuery(sql);
                executePeopleHaveArrearage(res);
                System.out.println("\n\nreverse");
                reverse(conn,5004,"2018-09-01");
                sql = getAllPeopleHaveArrearage();
                res = statement.executeQuery(sql);
                executePeopleHaveArrearage(res);

               check(conn,"CMB",2,250,"2018-08-21");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

