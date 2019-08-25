package com.bar.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.bind.annotation.RestController;

import com.bar.coupons.beans.Login;
import com.bar.coupons.beans.User;
import com.bar.coupons.beans.UserDataMap;
import com.bar.coupons.enums.ClientType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.idao.IusersDAO;
import com.bar.coupons.utils.JdbcUtils;



@RestController
public class UsersDAO implements IusersDAO {

	@Override
	public ClientType login(Login login) throws CouponsProjectExceptions {
		System.out.println("user DAO Login - check1");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT user_email , password , client_type FROM users WHERE user_Email=? AND Password=?;";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, login.getUserName());
			preparedStatement.setString(2, login.getPassword());

			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			System.out.println("user DAO Login - check2");
			if (result.next()) {
				System.out.println("Loged in successfully");
				return ClientType.valueOf(result.getString("client_type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to login", e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
			
		}
		throw new CouponsProjectExceptions("failed to login");

	}

		
	@Override
	public long createUser(User user) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		long id = 0;
		try {
			connection = JdbcUtils.getConnection();
			String create = null;
			if (user.getCompanyID() == null) {
				create = "INSERT INTO users(user_email, password, client_type) VALUES(?, ?, ?)";
			} else {
				create = "INSERT INTO users(user_email, password, client_type,company_id) VALUES(?, ?, ?, ?)";
			}
			preparedStatement = connection.prepareStatement(create, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getUserEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getType().name());
			if (user.getCompanyID() != null) {
			preparedStatement.setLong(4, user.getCompanyID());
			}
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				id = result.getLong(1);
				user.setUserID(id);
				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to add a new user");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		return id;	}


	@Override
	public void deleteUser(String userEmail) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM users WHERE user_email=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setString(1, userEmail);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the user", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	@Override
	public void deleteUser(long userID) throws CouponsProjectExceptions {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM users WHERE user_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, userID);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the user", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void deleteCompanysUsers(long companyID) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM users WHERE company_ID=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, companyID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the user", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	
	@Override
	public void updateUser(User user) throws CouponsProjectExceptions {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			long userID = user.getUserID();
			String update = "UPDATE users SET user_Email=?, password=?, client_type=?, company_id=? WHERE user_id=?";
			preparedStatement = connection.prepareStatement(update);
			preparedStatement.setString(1, user.getUserEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getType().name());
			preparedStatement.setLong(4, user.getCompanyID());
			preparedStatement.setLong(5, userID);
			preparedStatement.executeUpdate();
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to update the user", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}

	@Override
	public User getUserByUserID(long userID) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM users WHERE user_id=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, userID);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				user = extractUserFromResultSet(result);
			}
			return user;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			e.printStackTrace();
			// add here detailed exception with date and type of error
			throw new CouponsProjectExceptions("failed to retrieve the user", e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	@Override
	public User getUserByUserEmail(String userEmail) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM users WHERE user_email=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userEmail);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				user = extractUserFromResultSet(result);
			}
			return user;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			e.printStackTrace();
			// add here detailed exception with date and type of error
			throw new CouponsProjectExceptions("failed to retrieve the user", e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		
	}
	
	@Override
	public Collection<User> getUserByCompanyId(long companyId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM users WHERE company_id=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, companyId);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			
			Collection<User> companyUsers = new ArrayList<>();

			while (result.next()) {
				companyUsers.add(extractUserFromResultSet(result));
			}
			return companyUsers;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			e.printStackTrace();
			// add here detailed exception with date and type of error
			throw new CouponsProjectExceptions("failed to retrieve the user", e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		
	}

	@Override
	public Long getUserIDFromDataBase(String userEmail) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = null;
		Long userId = null;
		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM users WHERE user_email=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userEmail);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				user = extractUserFromResultSet(result);
				userId = user.getUserID();
			}

			return userId;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			e.printStackTrace();
			// add here detailed exception with date and type of error
			throw new CouponsProjectExceptions("failed to retrieve the company", e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		
	}
	@Override
	public Long getUserCompanyIDFromDataBase(String userEmail) throws CouponsProjectExceptions {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = null;
		Long userCompanyId = null;
		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM users WHERE user_email=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userEmail);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				user = extractUserFromResultSet(result);
				userCompanyId = user.getCompanyID();
				
			}
			
			return userCompanyId;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			e.printStackTrace();
			// add here detailed exception with date and type of error
			throw new CouponsProjectExceptions("failed to retrieve the company", e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		
	}

	
	@Override
	public Collection<User> getAllUsers() throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCompanies = "SELECT * FROM users";
			preparedStatement = connection.prepareStatement(getAllCompanies);
			result = preparedStatement.executeQuery();

			Collection<User> allUsers = new ArrayList<>();

			while (result.next()) {
				allUsers.add(extractUserFromResultSet(result));
			}

			return allUsers;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retreive all companies", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public Collection<User> getAllUsersByType(ClientType type) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCompanies = "SELECT * FROM users WHERE client_type=?";
			preparedStatement = connection.prepareStatement(getAllCompanies);
			preparedStatement.setString(1, type.name());
			result = preparedStatement.executeQuery();

			Collection<User> allUsers = new ArrayList<>();

			while (result.next()) {
				allUsers.add(extractUserFromResultSet(result));
			}

			return allUsers;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	@Override
	public boolean isUserExistsById(long userId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE user_id = ?");
			preparedStatement.setLong(1, userId);
			resultSet = preparedStatement.executeQuery();

			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("fail", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}


	}
	
	@Override
	public boolean isUserExistsByEmail(String userEmail) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT user_email FROM users WHERE user_email = ?");
			preparedStatement.setString(1, userEmail);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("fail", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

		return false;

	}
	
	@Override
	public UserDataMap getUserDataMap(String userName) throws CouponsProjectExceptions {

		Long companyID = null;
		UserDataMap userDataToMap = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT user_id , company_id FROM users WHERE user_email = ?");
			preparedStatement.setString(1, userName);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				// check if user is a company or else.
				// if user isn't get company id he will stay null
				if (resultSet.getLong("company_id") != 0) {

					companyID = resultSet.getLong("company_id");
				}

				userDataToMap = new UserDataMap(resultSet.getLong("user_id"), companyID);

				return userDataToMap;

			}

			throw new CouponsProjectExceptions("fail");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("fail", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
		
	private User extractUserFromResultSet(ResultSet result) throws SQLException {
		User user = new User();
		user.setUserID(result.getLong("user_id"));
		user.setUserEmail(result.getString("user_email"));
		user.setPassword(result.getString("password"));
		user.setType(ClientType.valueOf(result.getString("client_type")));
		user.setCompanyID(result.getLong("company_id"));

		return user;
	}

}
