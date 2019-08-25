package com.bar.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.bind.annotation.RestController;

import com.bar.coupons.beans.Customer;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.idao.IcustomersDAO;
import com.bar.coupons.utils.JdbcUtils;

@RestController
public class CustomersDAO implements IcustomersDAO {
	UsersDAO userDao = new UsersDAO();

	@Override
	public void createCustomer(Customer customer) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			String create = "INSERT INTO customers(cust_id, cust_email,cust_phone,cust_name) VALUES(?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(create, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1,(customer.getCustId()));
			preparedStatement.setString(2, customer.getCustEmail());
			preparedStatement.setString(3, customer.getCustPhone());
			preparedStatement.setString(4, customer.getCustName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to add a new customer", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public void deleteCustomer(long customerID) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM customers WHERE cust_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, customerID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the customer", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			long custID = customer.getCustId();

			String update = "UPDATE customers SET cust_email=?, cust_phone=?,cust_name=? WHERE cust_id=?";
			preparedStatement = connection.prepareStatement(update);
			preparedStatement.setString(1, customer.getCustEmail());
			preparedStatement.setString(2, customer.getCustPhone());
			preparedStatement.setString(3, customer.getCustName());
			preparedStatement.setLong(4, custID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to update the customer", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public Customer getCustomer(long customerID) throws CouponsProjectExceptions {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Customer customer = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM customers WHERE cust_id=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, customerID);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				customer = extractCustomerFromResultSet(result);
			}
			return customer;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			e.printStackTrace();
			// add here detailed exception with date and type of error
			throw new CouponsProjectExceptions("failed to retrieve the customer", e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	@Override
	public Collection<Customer> getAllCustomers() throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCustomers = "SELECT * FROM customers";
			preparedStatement = connection.prepareStatement(getAllCustomers);
			result = preparedStatement.executeQuery();

			Collection<Customer> AllCustomers = new ArrayList<>();

			while (result.next()) {
				AllCustomers.add(extractCustomerFromResultSet(result));
			}

			return AllCustomers;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retreive all customers", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public boolean isCustomerExistsById(long customerId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT cust_id FROM customers WHERE cust_id = ?");
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();

			 return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}

	}
	@Override
	public boolean isCustomerExistsByEmail(String customerEmail) throws CouponsProjectExceptions {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT cust_email FROM customers WHERE cust_email = ?");
			preparedStatement.setString(1, customerEmail);
			resultSet = preparedStatement.executeQuery();
			
			return resultSet.next(); 

			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		
	}

	private Customer extractCustomerFromResultSet(ResultSet result) throws SQLException, CouponsProjectExceptions {
		Customer customer = new Customer();
		customer.setCustId(result.getLong("cust_id"));
		customer.setCustEmail(result.getString("cust_email"));
		customer.setCustPhone(result.getString("cust_phone"));
		customer.setCustName(result.getString("cust_name"));
		customer.setUser(userDao.getUserByUserEmail(result.getString("cust_email")));

		return customer;
	}

}
