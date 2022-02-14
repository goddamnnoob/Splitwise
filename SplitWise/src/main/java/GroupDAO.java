import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class GroupDAO {
	public static int createNewGroup(Group group) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO groupstable (groupname,createdby) values(?,?)",Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(2,group.getCreatedBy());
		preparedStatement.setString(1, group.getGroupName());
		int result = preparedStatement.executeUpdate();
		 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                result =  (int) generatedKeys.getLong(1);
	            }
	        }
		connection.close();
		addUserToGroup(group.getCreatedBy(), result);
		return result;
	}
	
	public static boolean checkGroupId(int groupId) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT groupid from groupstable WHERE groupid =?");
		preparedStatement.setInt(1, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		boolean result = resultSet.next();
		connection.close();
		return result;
	}
	public static int addUserToGroup(int userId,int groupId) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO groupuserrelationship (userid,groupid) values(?,?)");
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, groupId);
		int result = preparedStatement.executeUpdate();
		connection.close();
		return result;
	}
	public static boolean isUserinGroup(int userId,int groupId) throws SQLException, ClassNotFoundException {
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT userid FROM groupuserrelationship WHERE userid = ? AND groupid = ?");
		preparedStatement.setInt(2, groupId);
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		boolean result = resultSet.next();
		connection.close();
		return result;
	}
	
	public static ArrayList<User> getUsersInGroup(int groupId) throws ClassNotFoundException, SQLException{
		ArrayList<User> users = new ArrayList<User>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM groupuserrelationship WHERE groupid = ?");
		preparedStatement.setInt(1, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			users.add ( UserDAO.getUserDetails(resultSet.getInt("userId")));
		}
		connection.close();
		return users;
	}
	public static ArrayList<Group> getGroupsofUser(int userId) throws ClassNotFoundException, SQLException{
		ArrayList<Group> groups = new ArrayList<Group>();
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM groupuserrelationship WHERE userid = ?");
		preparedStatement.setInt(1, userId);
		ResultSet resultSet =  preparedStatement.executeQuery();
		while(resultSet.next()) {
			groups.add(new Group(resultSet.getInt("groupid")));
		}
		connection.close();
		return groups;
	}
	public static Group getGroupDetails(int groupId) throws ClassNotFoundException, SQLException {
		Group group = new Group(-1);
		Connection connection = DatabaseConnector.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM groupstable WHERE groupid = ?");
		preparedStatement.setInt(1, groupId);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			group = new Group(resultSet.getInt("groupid"), resultSet.getInt("createdby"), resultSet.getString("groupname"));
		}
		connection.close();
		return group;
	}
}
