package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * to get the connection of database
 * @author 发爷
 *
 */
public class DatabaseConn {
	/**
	 * connect to the database
	 * @return the connection of database
	 */
	public static Connection getConn(){
		
	    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	    // 在当前项目目录下创建DemoDB
	    // String dbName="DemoDB";
	    // 指定目录下创建DemoDB
	    File directory = new File("");
	    String dbName=directory.getAbsolutePath()+"\\mywork";
	    String connectionURL = "jdbc:derby:"  +dbName + ";create=true";     
	    Connection connection = null;
	    try {
			Class.forName(driver);
			connection  = DriverManager.getConnection(connectionURL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}    
		return connection;		
	}

}
