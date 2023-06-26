package com.company;

import  java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {


    public static String getDVDAvailable() {
        //a.search for the DVDs that can be rented by the member.
        //you can see the DVD available at each library
        String sql = "SELECT  * FROM  dvdcopy where lName is not null";
        return sql;
    }
    public static void executeDVDAvailable( ResultSet res) throws SQLException {
        System.out.println("copyID   title    releaseYear   lName ");
        while (res.next()) {
            String copyID = res.getString("copyID");
            String title = res.getString("title");
            String releaseYear = res.getString("releaseYear").substring(0, 4);
            String lName = res.getString("lName");
            System.out.println(copyID + "  " + title + "  " + releaseYear + "  " + lName);
        }
    }
    public static String getDVDAvailableInEachGenre() {
        //2. generate the list of DVDs	available in each genre
        String sql = "select genre, dvdgenre.title, dvdgenre.releaseYear " +
                "from dvdgenre,dvdcopy\r\n" +
                "where dvdgenre.title = dvdcopy.title and dvdgenre.releaseYear = dvdcopy.releaseYear and dvdcopy.lName is not null " +
                "group by genre,dvdgenre.title, dvdgenre.releaseYear";
        return sql;
    }
    public static void executeDVDAvailableInEachGenre( ResultSet res) throws SQLException {
        System.out.println("genre        title        releaseYear");
        while (res.next()) {
            String genre = res.getString("genre");
            String title = res.getString("title");
            String releaseYear = res.getString("releaseYear").substring(0, 4);
            System.out.println(genre + "     " + title + "     " + releaseYear);
        }
    }
    public static String getBalance() {
        //3. select the account balance for each member
        String sql = "select membershipNum,balance " +
                "from member";
        return sql;
    }
    public static void executeGetBalance( ResultSet res) throws SQLException {
        System.out.println("membershipNum        balance");
        while (res.next()) {
            String membershipNum = res.getString("membershipNum");
            String balance = res.getString("balance");
            System.out.println(membershipNum + "     " + balance);
        }
    }

    public static String getRecordsOf2018() {
        //4. generate the rental records of all members in 2018
        String sql = "select membershipNum,rentDate,returnDate,copyID " +
                "from operate " +
                "where rentDate between '2018-1-1' and '2018-12-31' " +
                "order by membershipNum";
        return sql;
    }
    public static void executeRecordsOf2018( ResultSet res) throws SQLException {
        System.out.println("membershipNum        rentDate        returnDate        copyID");
        while (res.next()) {
            String membershipNum = res.getString("membershipNum");
            String rentDate = res.getString("rentDate");
            String returnDate = res.getString("returnDate");
            String copyID = res.getString("copyID");
            System.out.println(membershipNum + "     " + rentDate + "     " + returnDate + "     " + copyID);
        }
    }

    public static String getDVDNotReturned() {
        //5. generate the records of DVDs that haven't been returned
        String sql = "select * " +
                "from operate " +
                "where returnDate is null";
        return sql;
    }
    public static void executeDVDNotReturned( ResultSet res) throws SQLException {
        System.out.println("rentDate        returnDate        membershipNum        copyID");
        while (res.next()) {
            String rentDate = res.getString("rentDate");
            String returnDate = res.getString("returnDate");
            String membershipNum = res.getString("membershipNum");
            String copyID = res.getString("copyID");
            System.out.println(rentDate + "     " + returnDate + "     " + membershipNum + "     " + copyID);
        }
    }
    public static String getAverageMoney() {
        //6. return the average money that normal members and premium members have spent
        String sql = "select category, round(avg(totalPay),2) as avgPaid " +
                "from member " +
                "group by category";
        return sql;
    }
    public static void executeAverageMoney( ResultSet res) throws SQLException {
        System.out.println("category        avgPaid");
        while (res.next()) {
            String category = res.getString("category");
            String avgPaid = res.getString("avgPaid");
            System.out.println(category + "     " + avgPaid + "     ");
        }
    }

    public static String getPotentialPremium() {
        //7. generate a list of normal members whose balance>=50rmb and have 3 or more rental
        String sql = "select * from (select member.membershipNum, balance, count(member.membershipNum) as rentAmount from member, operate where member.membershipNum = operate.membershipNum group by member.membershipNum, balance ) as rentAmountTable where rentAmountTable.balance>=50 and rentAmountTable.rentAmount>=3";
        return sql;
    }
    public static void executePotentialPremium( ResultSet res) throws SQLException {
        System.out.println("membershipNum        balance        rentAmount");
        while (res.next()) {
            String membershipNum = res.getString("membershipNum");
            String balance = res.getString("balance");
            String rentAmount = res.getString("rentAmount");
            System.out.println(membershipNum + "     " + balance + "     " + rentAmount);
        }
    }

    public static void main (String[]args){
            Scanner input = new Scanner(System.in);
            Scanner DVDid = new Scanner(System.in);
            Scanner libraryName=new Scanner(System.in);
            Scanner membershipNumber=new Scanner(System.in);
            Date rent_Date = null;
            String category = null;
            Double totalValue = null;
            Double totalPaid=null;
            Double yourBalance=null;
            int days=0;
            String sql;
            int update;
            String id;
            String library;
//      1.注册驱动
            ResultSet res = null;
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    System.out.println("Driver could not be loaded");
                    System.exit(0);
                }
//            2.获取数据库连接
                String url = "jdbc:mysql://localhost:3306/library?useSSL=false";
                String user = "root";
                String password = "cgp5226926+123";
                Connection conn = DriverManager.getConnection(url, user, password);
//            3.获取数据操作的对象
                Statement statement = conn.createStatement();

                //获得当天日期
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate=df.format(System.currentTimeMillis());
                System.out.println(currentDate);
                System.out.println("welcome to the library!");
                System.out.println("please input your membership number:");
                int mNum=membershipNumber.nextInt();

                System.out.println("menu\n" +
                        "0. charge balance"+
                        "1. rent DVD\n" +
                        "2. return DVD\n" +
                        "3. DVD available\n" +
                        "4. DVDs available in each genre\n"+
                "5. balance for each member\n"+
                "6. rental records of members in 2018\n"+
                "7. NOT returned records of DVDs \n"+
                "8. average cost of member\n"+
                "9. potential premium\n"+
                "10. exit");
                    while(true){
                        System.out.println("your manipulation");
                int a= input.nextInt();
                switch (a){
                    case 0://充钱
                        //charge your balance
                        System.out.println("How much do you want to charge?");
                        Scanner charge=new Scanner(System.in);
                        Double money=charge.nextDouble();
                        sql="UPDATE member SET balance=balance+"+"'"+money+"'"+"WHERE membershipNum=" + "'" + mNum + "'";
                        update=statement.executeUpdate(sql);
                        break;
                    case 1://借书
                        //rent DVD from the library
                        //想还的书
                        System.out.println("choose the book you want to rent");
                        id="'"+DVDid.next()+"'";
                        //借书时，将lName设为null，表示书被借出
                        sql = "UPDATE dvdcopy SET lName = NULL WHERE copyID="+id;
                        update =statement.executeUpdate(sql);
                        //新增一条租借记录。
                        sql="INSERT  INTO  operate (copyID, membershipNum, returnDate, rentDate)values ("+id+","+"'"+mNum+"'"+", NULL ,"+"'"+currentDate+"'"+")";
                        update=statement.executeUpdate(sql);
                        break;
                    case 2://还书
                        //return your book at library
                        System.out.println("please input the DVD's copyID that you want to return");
                        id="'"+DVDid.next()+"'";
                        System.out.println("please input the library you are in");
                        library="'"+libraryName.next()+"'";
                        //更新当前数目存储的图书馆
                        sql="UPDATE dvdcopy SET lName="+library+" WHERE copyID="+id;
                        update=statement.executeUpdate(sql);
                        //查询出借书日期
                        sql="SELECT rentDate FROM operate WHERE copyID="+id;
                        res=statement.executeQuery(sql);
                        while (res.next()) {
                            rent_Date=res.getDate("rentDate");
                        }
                        //设置归还日期
                        sql="UPDATE operate SET returnDate="+"'"+currentDate+"'"+"WHERE copyID="+id+"AND membershipNum="+"'"+mNum+"'"+"AND rentDate="+"'"+rent_Date+"'";
                        update=statement.executeUpdate(sql);
                        //计算借的天数
                        sql="SELECT DATEDIFF(returnDate,rentDate) AS days FROM  operate WHERE copyID="+id+"AND membershipNum="+"'"+mNum+"'"+"AND rentDate="+"'"+rent_Date+"'";
                        res=statement.executeQuery(sql);
                        while (res.next()) {
                            days=res.getInt("days");
                        }
                        //选出当前成员的category，判断是否为会员
                        sql = "SELECT category,totalVal,totalPay,balance FROM member WHERE membershipNum=" + "'" + mNum + "'";
                        res=statement.executeQuery(sql);
                        while (res.next()) {
                            category=res.getString("category");
                            totalValue=res.getDouble("totalVal");
                            totalPaid=res.getDouble("totalPay");
                            yourBalance=res.getDouble("balance");
                            System.out.println(category+totalValue);
                        }
                        if (category.equals("normal")){
                            totalValue=totalValue+5*days;
                            totalPaid=totalPaid+5*days;
                            yourBalance=yourBalance-5*days;
                        }else{
                            totalValue=totalValue+3*days;
                            totalPaid=totalPaid+3*days;
                            yourBalance=yourBalance-3*days;
                        }

                        //设置member
                        sql="UPDATE member SET totalVal="+"'"+totalValue+"'"+", totalPay="+"'"+totalPaid+"'"+", balance="+"'"+yourBalance+"'"+"WHERE  membershipNum="+"'"+mNum+"'";
                        update=statement.executeUpdate(sql);
                        break;
                    case 3://所有可借阅书目
                        //3.search for the DVDs that can be rented by the member.
                        //you can see the DVD available at each library
                        sql = getDVDAvailable();
                        res = statement.executeQuery(sql);
                        executeDVDAvailable(res);
                        break;
                    case 4://各个类别的可借阅DVD
                        //search for the DVD that is available group by genre
                        sql=getDVDAvailableInEachGenre();
                        res=statement.executeQuery(sql);
                        executeDVDAvailableInEachGenre(res);
                        break;
                    case 5://查询每个会员今年的所有余额
                        //search for the balace of every member
                        sql=getBalance();
                        res=statement.executeQuery(sql);
                        executeGetBalance(res);
                        break;
                    case 6://查询所有会员今年的借阅记录
                        //search for the record of every member this year
                        sql=getRecordsOf2018();
                        res=statement.executeQuery(sql);
                        executeRecordsOf2018(res);
                         break;
                    case 7://查询未归还的借阅记录
                        //search for the DVDs that are rented by members
                        sql=getDVDNotReturned();
                        res=statement.executeQuery(sql);
                        executeDVDNotReturned(res);
                        break;
                    case 8://查询高级会员的平均花费
                        //search for the average fee for the premium member
                        sql=getAverageMoney();
                        res=statement.executeQuery(sql);
                        executeAverageMoney(res);
                        break;
                    case 9://查询余额大于50，借阅过3次以上的DVD普通会员
                        //generate a list of normal members whose balance>=50rmb and have 3 or more rental
                        sql=getPotentialPremium();
                        res=statement.executeQuery(sql);
                        executePotentialPremium(res);
                    case 10://退出
                        //exit
                        System.exit(1);
                        break;
                    default:
                        System.out.println("input error, input again!");
                        break;

                }}

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (res != null) {
                    try {
                        res.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
