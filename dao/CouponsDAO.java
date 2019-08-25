package com.bar.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.bind.annotation.RestController;

import com.bar.coupons.beans.Coupon;
import com.bar.coupons.enums.Category;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.idao.IcouponsDAO;
import com.bar.coupons.utils.JdbcUtils;


@RestController
public class CouponsDAO implements IcouponsDAO {

	@Override
	public long createCoupon(Coupon coupon) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		long id = 0;


		try {
			connection = JdbcUtils.getConnection();
			String add ="INSERT INTO coupons(comp_id, title, start_date,end_date,amount,category,description,price,image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(add, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, coupon.getCompanyID());
			preparedStatement.setString(2, coupon.getTitle());
			preparedStatement.setObject(3,  coupon.getStartDate());
			preparedStatement.setObject(4, coupon.getEndDate());
			preparedStatement.setInt(5, coupon.getAmount());
			preparedStatement.setString(6, coupon.getCategory().name());
			preparedStatement.setString(7, coupon.getDescription());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				id = result.getLong(1);
				return id;
			}		
			} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to add a new coupon", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		return id;
	}


	@Override
	public void deleteCoupon(long couponID) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete ="DELETE FROM coupons WHERE coup_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, couponID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the coupon", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String update = "UPDATE coupons SET title=?, start_date=?, end_date=?, amount=?, category=?, description=?, price=?, image=? WHERE coup_id=?";
			preparedStatement = connection.prepareStatement(update);
			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setObject(2,  coupon.getStartDate());
			preparedStatement.setObject(3, coupon.getEndDate());
			preparedStatement.setInt(4, coupon.getAmount());
			preparedStatement.setString(5, coupon.getCategory().name());
			preparedStatement.setString(6, coupon.getDescription());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());
			preparedStatement.setLong(9, coupon.getCouponID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to update the coupon", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}


	@Override
	public Coupon getCoupon(long couponID) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Coupon coupon = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM coupons WHERE coup_id=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, couponID);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {	
			coupon = extractCouponFromResultSet(result);
			}
			return coupon;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			e.printStackTrace();
			// add here detailed exception with date and type of error
			throw new CouponsProjectExceptions("failed to retrieve the coupon" , e);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	
	@Override
	public Collection<Coupon> getAllCouponsByCompanyId(long companyID) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCouponsByCompanyId = "SELECT * FROM coupons Where comp_id=?";
			preparedStatement = connection.prepareStatement(getAllCouponsByCompanyId);
			preparedStatement.setLong(1, companyID);
			result = preparedStatement.executeQuery();

			Collection<Coupon> CouponsByCompanyId = new ArrayList<>();

			while (result.next()) {
				CouponsByCompanyId.add(extractCouponFromResultSet(result));
			}

			return CouponsByCompanyId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve coupons by company id", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	@Override
	public Collection<Coupon> getCouponsByMaxPrice(double maxPrice) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String getCouponsByMaxPrice = "SELECT * FROM coupons Where price <= ?";
			preparedStatement = connection.prepareStatement(getCouponsByMaxPrice);
			preparedStatement.setDouble(1, maxPrice);
			result = preparedStatement.executeQuery();
			
			Collection<Coupon> CouponsByMaxPrice = new ArrayList<>();
			
			while (result.next()) {
				CouponsByMaxPrice.add(extractCouponFromResultSet(result));
			}
			
			return CouponsByMaxPrice;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve coupons by max price", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	@Override
	public Collection<Coupon> getCouponsByMaxPriceAndCompany(long companyId , double maxPrice) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String getCouponsByMaxPrice = "SELECT * FROM coupons Where comp_id = ? AND price <= ?";
			preparedStatement = connection.prepareStatement(getCouponsByMaxPrice);
			preparedStatement.setLong(1, companyId);
			preparedStatement.setDouble(2, maxPrice);
			result = preparedStatement.executeQuery();
			
			Collection<Coupon> CouponsByMaxPrice = new ArrayList<>();
			
			while (result.next()) {
				CouponsByMaxPrice.add(extractCouponFromResultSet(result));
			}
			
			return CouponsByMaxPrice;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve coupons by max price and company", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	@Override
	public 	Collection<Coupon> getAllCouponsByCategory(Category category) throws CouponsProjectExceptions {
		Collection<Coupon> CouponsForCompany = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCouponsForOneCompany = "SELECT * FROM coupons Where category=?";
			preparedStatement = connection.prepareStatement(getAllCouponsForOneCompany);
			preparedStatement.setString(1, category.toString().toUpperCase());
			result = preparedStatement.executeQuery();


			while (result.next()) {
				CouponsForCompany.add(extractCouponFromResultSet(result));
			}

			return CouponsForCompany;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve coupons by category", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	@Override
	public 	Collection<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws CouponsProjectExceptions {
		Collection<Coupon> CouponsForCompany = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String getAllCouponsForOneCompany = ("SELECT * FROM coupons WHERE COMP_ID = ? AND CATEGORY = ?");
			preparedStatement = connection.prepareStatement(getAllCouponsForOneCompany);
			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, category.toString().toUpperCase());
			result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				CouponsForCompany.add(extractCouponFromResultSet(result));
			}
			
			return CouponsForCompany;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve company coupons by company ", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	@Override
	public Collection<Coupon> getCouponsByCustomerId(long customerID) throws CouponsProjectExceptions {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet result = null;
			
			try {
				connection = JdbcUtils.getConnection();
				String getCouponsByCustomerId = "SELECT * from coupons WHERE coup_id IN (SELECT coupon_id FROM  purchases WHERE customer_id = ?)";

				preparedStatement = connection.prepareStatement(getCouponsByCustomerId);
				preparedStatement.setLong(1, customerID);
				result = preparedStatement.executeQuery();
				
				Collection<Coupon> CouponsByCustomerId = new ArrayList<>();
				
				while (result.next()) {
					CouponsByCustomerId.add(extractCouponFromResultSet(result));
				}
				
				return CouponsByCustomerId;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CouponsProjectExceptions("failed to retrieve coupons by customer id", e);
			} finally {
				JdbcUtils.closeResources(connection, preparedStatement);
			}
		}
	
	public Collection<Coupon>  getCustomerCouponsByCategory(long customerId, Category category) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String getCouponsByCustomerId = "SELECT * from purchases JOIN coupons ON coup_id = purchases.coupon_id WHERE customer_id = ? AND category = ?";
			preparedStatement = connection.prepareStatement(getCouponsByCustomerId);
			preparedStatement.setLong(1, customerId);
			preparedStatement.setString(2, category.toString().toUpperCase());
			result = preparedStatement.executeQuery();
			
			Collection<Coupon> customerCouponsByCustomerId = new ArrayList<>();
			
			while (result.next()) {
				customerCouponsByCustomerId.add(extractCouponFromResultSet(result));
			}
			
			return customerCouponsByCustomerId;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve customer coupons by customer id and category", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public Collection<Coupon>  getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws CouponsProjectExceptions {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String getCouponsByCustomerId = "SELECT * from purchases JOIN coupons ON coup_id = purchases.coupon_id WHERE customer_id = ? AND price <= ?";
			preparedStatement = connection.prepareStatement(getCouponsByCustomerId);
			preparedStatement.setLong(1, customerId);
			preparedStatement.setDouble(2, maxPrice);
			result = preparedStatement.executeQuery();
			
			Collection<Coupon> CustomerCouponsByMaxPrice = new ArrayList<>();
			
			while (result.next()) {
				CustomerCouponsByMaxPrice.add(extractCouponFromResultSet(result));
			}
			
			return CustomerCouponsByMaxPrice;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve customer coupons by customer id and max price", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
		
		
		
	}
	
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		System.out.println("dao check before sqlQuery");
		try {
			connection = JdbcUtils.getConnection();
			String getAllCoupons = "SELECT * FROM coupons";
			preparedStatement = connection.prepareStatement(getAllCoupons);
			result = preparedStatement.executeQuery();

			Collection<Coupon> allCoupons = new ArrayList<>();

			while (result.next()) {
				allCoupons.add(extractCouponFromResultSet(result));
			}
			System.out.println("dao check after sqlQuery");
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retrieve all coupons", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	@Override
	public boolean isCouponExists(long couponId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT coup_id FROM coupons WHERE coup_id = ?");
			preparedStatement.setLong(1, couponId);
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
		public boolean isCouponsExistByTitle(String couponTitle) throws CouponsProjectExceptions {

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {
				connection = JdbcUtils.getConnection();
				preparedStatement = connection.prepareStatement("SELECT title FROM coupons WHERE title = ?");
				preparedStatement.setString(1, couponTitle);
				resultSet = preparedStatement.executeQuery();

				return resultSet.next();
					
				

			} catch (SQLException e) {
				e.printStackTrace();
				throw new CouponsProjectExceptions("fail", e);
			} finally {
				JdbcUtils.closeResources(connection, preparedStatement, resultSet);
			}

	}
	/*
	@Override
	public boolean isCouponsByCustomerIdExist(long customerID) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * from coupons WHERE coup_id IN (SELECT coupon_id FROM  purchases WHERE customer_id = ?)");
			preparedStatement.setLong(1, customerID);
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
*/
	
	@Override
	public boolean isCouponsByCompanyIdExist(long customerID) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT comp_id FROM coupons WHERE comp_id = ?");
			preparedStatement.setLong(1, customerID);
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
	public void deleteExpiredCoupon() throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("DELETE FROM coupons WHERE end_date < NOW()");

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			throw new CouponsProjectExceptions(com.bar.coupons.enums.ErrorType.GENERAL_ERROR, e);

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	private Coupon extractCouponFromResultSet(ResultSet result) throws SQLException{
		
		Coupon coupon = new Coupon();
		
		coupon.setCouponID(result.getLong("coup_id"));
		coupon.setCompanyID(result.getLong("comp_id"));
		coupon.setTitle(result.getString("title"));
		coupon.setStartDate(result.getDate("start_date"));
		coupon.setEndDate(result.getDate("end_date"));
		coupon.setAmount(result.getInt("amount"));
		coupon.setCategory(Category.valueOf(result.getString("category")));
		coupon.setDescription(result.getString("description"));
		coupon.setPrice(result.getDouble("price"));
		coupon.setImage(result.getString("image"));
		
		return coupon;
	}
	
}
