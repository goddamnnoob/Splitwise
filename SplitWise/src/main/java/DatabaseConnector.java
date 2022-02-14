import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql:// localhost:3306/";
        String dbName = "splitwise";
        String dbUsername = "root";
        String dbPassword = "root1234";
  
        Class.forName(dbDriver);
        Connection connection = DriverManager.getConnection(dbURL + dbName,
                                                     dbUsername, 
                                                     dbPassword);
		return connection;
	}
}
