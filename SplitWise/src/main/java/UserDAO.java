
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.jasper.tagplugins.jstl.core.Remove;


public class UserDAO {
	public static boolean checkUserId(int userId) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT userid FROM users WHERE userid = ?");
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		boolean result  = resultSet.next();
		connection.close();
		return result;
	}
	public static int createNewUser(User user) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username,email,password,debt,liability) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, user.getUserName());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.password);
		preparedStatement.setFloat(4, user.getDebt());
		preparedStatement.setFloat(5, user.getLiability());
		int result = preparedStatement.executeUpdate();
		connection.close();
		return result;
	}
	public static boolean checkUsernameAvailability(String username) throws SQLException, ClassNotFoundException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username=?");
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		boolean result = resultSet.next();
		connection.close();
		return result;
	}
	
	public static boolean authenticate(String username, String password) throws SQLException, ClassNotFoundException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		boolean result = rs.next();
		connection.close();
		return result;
	}
	
	public static User getUserDetails(int userId) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE userid = ?");
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		User user = new User(-1,null, null, 0,0);
		if(resultSet.next()) {
			int userid = Integer.parseInt((String)resultSet.getString("userid"));
			String username = resultSet.getString("username");
			String email = resultSet.getString("email");
			float debt = Float.parseFloat((String)resultSet.getString("debt"));
			float liability = Float.parseFloat((String)resultSet.getString("liability")); 
			user = new User( userid,username, email, debt,liability);
		}
		connection.close();
		return user;
	}
	public static User getUserDetails(String userName) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
		preparedStatement.setString(1, userName);
		ResultSet resultSet = preparedStatement.executeQuery();
		User user = new User(-1,null, null, 0,0);
		if(resultSet.next()) {
			int userid = Integer.parseInt((String)resultSet.getString("userid"));
			String username = resultSet.getString("username");
			String email = resultSet.getString("email");
			float debt = Float.parseFloat((String)resultSet.getString("debt"));
			float liability = Float.parseFloat((String)resultSet.getString("liability")); 
			user = new User(userid,username, email, debt,liability);
		}
		connection.close();
		return user;
	}
	
	public static void updateUserDebt(User user) throws SQLException, ClassNotFoundException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET debt = ? WHERE userid = ?");
		preparedStatement.setFloat(1, user.getDebt());
		preparedStatement.setInt(2, user.getUserid());
		int result = preparedStatement.executeUpdate();
		connection.close();
	}
	
	public static void updateUserLiability(User user) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET liability=? WHERE userid = ?");
		preparedStatement.setFloat(1, user.getLiability());
		preparedStatement.setInt(2, user.getUserid());
		int result = preparedStatement.executeUpdate();
		connection.close();
	}
	
	public static ArrayList<User> peopleOwedToUser(int userId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> expenses = ExpenseDAO.peopleOwedToUser(userId);
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0;i<expenses.size();i++) {
			int index = -1;
			for(int j=0;j<users.size();j++) {
				if(users.get(j).getUserid() == expenses.get(i).getPaidFor() ) {
					users.get(j).setDebt(expenses.get(i).getAmount()+users.get(j).getDebt());
					index = j;
				}
			}
			if(index ==-1) {
				User user = UserDAO.getUserDetails(expenses.get(i).getPaidFor());
				user.setDebt(expenses.get(i).getAmount());
				users.add(user);
			}
		}
		for(int i=0;i<users.size();i++) {
			if(users.get(i).getUserid() == userId) {
				users.remove(i);
			}
		}
		return users;
		
	}
	
	public static ArrayList<User> peopleOwedByUser(int userId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> expenses = ExpenseDAO.peopleOwedByUser(userId);
		ArrayList<User> users = new ArrayList<User>();
		
		for(int i=0;i<expenses.size();i++) {
			int index = -1;
			for(int j=0;j<users.size();j++) {
				if(users.get(j).getUserid() == expenses.get(i).getPaidBy() ) {
					users.get(j).setLiability(expenses.get(i).getAmount()+users.get(j).getLiability());
					index = j;
				}
			}
			if(index ==-1) {
				User user = UserDAO.getUserDetails(expenses.get(i).getPaidBy());
				user.setLiability(expenses.get(i).getAmount());
				users.add(user);
			}
		}
		for(int i=0;i<users.size();i++) {
			if(users.get(i).getUserid() == userId) {
				users.remove(i);
			}
		}
		return users;
		
	}

}
