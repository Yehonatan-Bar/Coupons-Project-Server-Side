package com.bar.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.bind.annotation.RestController;

import com.bar.coupons.beans.Company;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.idao.IcompaniesDAO;
import com.bar.coupons.utils.JdbcUtils;

@RestController
public class CompaniesDAO implements IcompaniesDAO {

	@Override
	public long createCompany(Company company) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		long id = 0;

		try {
			connection = JdbcUtils.getConnection();
			String create = "INSERT INTO companies(comp_name, contact_email, contact_phone) VALUES(?, ?, ?) ";
			preparedStatement = connection.prepareStatement(create, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getContactEmail());
			preparedStatement.setString(3, company.getContactPhone());
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			System.out.println(company);
			if (result.next()) {
				id = result.getLong(1);
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to add a new company", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		return id;
	}

	@Override
	public void deleteCompany(long companyID) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM companies WHERE comp_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, companyID);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the company", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void updateCompany(Company company) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			long companyID = company.getCompanyID();
			String update = "UPDATE companies SET comp_name=?, contact_email=?, contact_phone=? WHERE comp_id=?";
			preparedStatement = connection.prepareStatement(update);
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getContactEmail());
			preparedStatement.setString(3, company.getContactPhone());
			preparedStatement.setLong(4, companyID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to update the company", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public Company getCompany(long companyID) throws CouponsProjectExceptions {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Company company = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM companies WHERE comp_id=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, companyID);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				company = extractCompanyFromResultSet(result);
			}
			return company;
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
	public Company getCompany(String compName) throws CouponsProjectExceptions {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Company company = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String sqlStatement = "SELECT * FROM companies WHERE comp_name =?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, compName);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (!result.next()) {
				return null;
			}
			company = extractCompanyFromResultSet(result);
			return company;
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
	public Collection<Company> getAllCompanies() throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCompanies = "SELECT * FROM companies";
			preparedStatement = connection.prepareStatement(getAllCompanies);
			result = preparedStatement.executeQuery();

			Collection<Company> allCompanies = new ArrayList<>();

			while (result.next()) {
				allCompanies.add(extractCompanyFromResultSet(result));
			}

			return allCompanies;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retreive all companies", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	@Override
	public boolean isCompanyExists(long companyId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT comp_id FROM companies WHERE comp_id = ?");
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();

			return resultSet.next();
//			) {
//				return true;
//			} else {
//				return false;
//			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("fail", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}


	}


	private Company extractCompanyFromResultSet(ResultSet result) throws SQLException {
		Company company = new Company();
		company.setCompanyID(result.getLong("comp_id"));
		company.setCompName(result.getString("comp_name"));
		company.setContactEmail(result.getString("contact_email"));
		company.setContactPhone(result.getString("contact_phone"));

		return company;

	}

}