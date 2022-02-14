import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ExpenseDAO {
	public static int addExpense(Expense expense) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO expenses (paidby,paidfor,groupid,amount,datecreated) values(?,?,?,?,?)");
		preparedStatement.setInt(1, expense.getPaidBy());
		preparedStatement.setInt(2, expense.getPaidFor());
		preparedStatement.setInt(3, expense.getGroupId());
		preparedStatement.setFloat(4, expense.getAmount());
		preparedStatement.setString(5, expense.getDate());
		int result = preparedStatement.executeUpdate();
		connection.close();
		return result;
	}
	public static void addExpenseToGroup(Expense expense) throws ClassNotFoundException, SQLException {
		ArrayList<User> users = GroupDAO.getUsersInGroup(expense.getGroupId());
		float amountperuser = expense.getAmount()/users.size();
		for(int i=0;i<users.size();i++) {
			int userid = users.get(i).userid;
			expense.setPaidFor(userid);
			if(expense.getPaidBy() != expense.getPaidFor()) {
				User userde = UserDAO.getUserDetails(userid);
				userde.setDebt(userde.getDebt()+amountperuser);
				UserDAO.updateUserDebt(userde);
			}
			else {
				User userde = UserDAO.getUserDetails(userid);
				userde.setLiability(userde.getLiability()+amountperuser*(users.size()-1));
				UserDAO.updateUserLiability(userde);
			}
			expense.setAmount(amountperuser);
			addExpense(expense);
		}
	}
	public static ArrayList<Expense> getUserExpenses(int userId) throws SQLException, ClassNotFoundException{
		ArrayList<Expense> userExpenses = new ArrayList<Expense>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidby = ? OR paidfor = ?");
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int paidBy = resultSet.getInt("paidby");
			int paidFor = resultSet.getInt("paidfor");
			int groupid = resultSet.getInt("groupid");
			String date = resultSet.getString("datecreated");
			float amount = resultSet.getFloat("amount");
			userExpenses.add(new Expense(paidBy, paidFor, groupid, amount, date));
		}
		connection.close();
		return userExpenses;
	}
	public static ArrayList<Expense> getGroupExpenses(int groupId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> groupExpenses = new ArrayList<Expense>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE groupid = ?");
		preparedStatement.setInt(1, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int paidBy = resultSet.getInt("paidby");
			int paidFor = resultSet.getInt("paidfor");
			int groupid = resultSet.getInt("groupid");
			String date = resultSet.getString("datecreated");
			float amount = resultSet.getFloat("amount");
			groupExpenses.add(new Expense(paidBy, paidFor, groupid, amount, date));
		}
		connection.close();
		return groupExpenses;
	}
	public static ArrayList<Expense> getUserGroupExpenses(int userId,int groupId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> userGroupExpenses = new ArrayList<Expense>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE (paidby = ? OR paidfor = ?) AND groupid = ?");
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, userId);
		preparedStatement.setInt(3, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int paidBy = resultSet.getInt("paidby");
			int paidFor = resultSet.getInt("paidfor");
			int groupid = resultSet.getInt("groupid");
			String date = resultSet.getString("datecreated");
			float amount = resultSet.getFloat("amount");
			userGroupExpenses.add(new Expense(paidBy, paidFor, groupid, amount, date));
		}
		connection.close();
		return userGroupExpenses;
	}
	
	public static float totalDebtByUser(int userId) throws ClassNotFoundException, SQLException {
		float amountOwed = 0;
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidfor = ?");
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			amountOwed += resultSet.getFloat("amount");
		}
		connection.close();
		return amountOwed;
	}
	
	public static float totalDebtToUser(int userId) throws ClassNotFoundException, SQLException {
		float amountOwed = 0;
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidby = ? ");
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			amountOwed += resultSet.getFloat("amount");
		}
		connection.close();
		return amountOwed;
	}
	
	public static ArrayList<Expense> peopleOwedToUser(int userId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidby = ? ");
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int paidBy = resultSet.getInt("paidby");
			int paidFor = resultSet.getInt("paidfor");
			int groupid = resultSet.getInt("groupid");
			String date = resultSet.getString("datecreated");
			float amount = resultSet.getFloat("amount");
			expenses.add(new Expense(paidBy, paidFor, groupid, amount, date));
		}
		connection.close();
		return expenses;
	}
	
	public static ArrayList<Expense> peopleOwedByUser(int userId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidfor = ? ");
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int paidBy = resultSet.getInt("paidby");
			int paidFor = resultSet.getInt("paidfor");
			int groupid = resultSet.getInt("groupid");
			String date = resultSet.getString("datecreated");
			float amount = resultSet.getFloat("amount");
			expenses.add(new Expense(paidBy, paidFor, groupid, amount, date));
		}
		connection.close();
		return expenses;
	}
	
	public static float totalDebtByUserInGroup(int userId,int groupId) throws ClassNotFoundException, SQLException {
		float amountOwed = 0;
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidfor = ? AND groupid = ?");
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			amountOwed += resultSet.getFloat("amount");
		}
		connection.close();
		return amountOwed;
	}
	
	public static float totalDebtToUserInGroup(int userId,int groupId) throws ClassNotFoundException, SQLException {
		float amountOwed = 0;
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidby = ? AND groupid = ? ");
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			amountOwed += resultSet.getFloat("amount");
		}
		connection.close();
		return amountOwed;
	}
	
	public static ArrayList<Expense> peopleOwedToUserInGroup(int userId,int groupId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidby = ? AND groupid = ?");
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int paidBy = resultSet.getInt("paidby");
			int paidFor = resultSet.getInt("paidfor");
			int groupid = resultSet.getInt("groupid");
			String date = resultSet.getString("datecreated");
			float amount = resultSet.getFloat("amount");
			expenses.add(new Expense(paidBy, paidFor, groupid, amount, date));
		}
		connection.close();
		return expenses;
	}
	
	public static ArrayList<Expense> peopleOwedByUserInGroup(int userId,int groupId) throws ClassNotFoundException, SQLException{
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE paidfor = ? AND groupid = ?");
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int paidBy = resultSet.getInt("paidby");
			int paidFor = resultSet.getInt("paidfor");
			int groupid = resultSet.getInt("groupid");
			String date = resultSet.getString("datecreated");
			float amount = resultSet.getFloat("amount");
			expenses.add(new Expense(paidBy, paidFor, groupid, amount, date));
		}
		connection.close();
		return expenses;
	}
}
