package com.bar.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.bind.annotation.RestController;

import com.bar.coupons.beans.Purchase;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.idao.IpurchasesDAO;
import com.bar.coupons.utils.JdbcUtils;

@RestController
public class PurchasesDAO implements IpurchasesDAO {

	@Override
	public long createPurchase(Purchase purchase) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		long id = 0;
		try {
			connection = JdbcUtils.getConnection();
			String create = "INSERT INTO purchases(customer_id,coupon_id,amount) VALUES(?,?,?) ";
			preparedStatement = connection.prepareStatement(create, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, purchase.getCustomerID());
			preparedStatement.setLong(2, purchase.getCouponID());
			preparedStatement.setInt(3, purchase.getAmount());
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				id = result.getLong(1);
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to add a new purchase", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
		return id;
	}

	@Override
	public void deletePurchase(long purchaseID) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM purchases WHERE purchase_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, purchaseID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the purchase", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void deletePurchaseBycouponId(long couponId) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM purchases WHERE purchase_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the purchase", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void deletePurchaseBycustomerIdAndCouponId(long customerid, long couponId) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM purchases WHERE coupon_id=? AND customer_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, couponId);
			preparedStatement.setLong(2, customerid);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to delete the purchase", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void updatePurchase(Purchase purchase) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String update = "UPDATE purchases SET customer_id=?, coupon_id=?, amount=? WHERE purchase_id=?";
			preparedStatement = connection.prepareStatement(update);
			preparedStatement.setLong(1, purchase.getCustomerID());
			preparedStatement.setLong(2, purchase.getCouponID());
			preparedStatement.setInt(3, purchase.getAmount());
			preparedStatement.setLong(4, purchase.getPurchaseID());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to update the purchase", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public Purchase getPurchaseByID(long purchaseID) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Purchase purchase = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM purchases WHERE purchase_id=?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, purchaseID);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				purchase = extractPurchaseFromResultSet(result);
			}
			return purchase;
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
	public Purchase getPurchaseBycustomerIdAndCouponId(long customerId, long couponId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Purchase purchase = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();
			// Creating the SQL query
			String select = "SELECT * FROM purchases WHERE customer_id=? AND coupon_id = ?";
			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(select);
			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, couponId);
			// Executing the query and saving the DB response in the resultSet.
			result = preparedStatement.executeQuery();
			if (result.next()) {
				purchase = extractPurchaseFromResultSet(result);
			}
			return purchase;
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
	public Collection<Purchase> getAllPurchasesbyCustomer(long customerID) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllPurchasesByCustomer = "SELECT * FROM purchases WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(getAllPurchasesByCustomer);
			preparedStatement.setLong(1, customerID);
			result = preparedStatement.executeQuery();

			Collection<Purchase> allPurchasesByCustomer = new ArrayList<>();

			while (result.next()) {
				allPurchasesByCustomer.add(extractPurchaseFromResultSet(result));
			}

			return allPurchasesByCustomer;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retreive all purchases by customer", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public Collection<Purchase> getAllPurchasesByCoupon(long couponID) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllPurchasesByCoupon = "SELECT * FROM purchases WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(getAllPurchasesByCoupon);
			preparedStatement.setLong(1, couponID);
			result = preparedStatement.executeQuery();

			Collection<Purchase> allPurchasesByCoupon = new ArrayList<>();
			while (result.next()) {
				allPurchasesByCoupon.add(extractPurchaseFromResultSet(result));
			}

			for (Purchase purchase : allPurchasesByCoupon) {
//				if (purchase.getCouponID() != 0) {
					System.out.println("purchaseDao(), purchase.getCouponID() =  " + purchase.getCouponID());
//				}
			}
			return allPurchasesByCoupon;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retreive all purchases by coupon", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public Collection<Purchase> getAllPurchases() throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllPurchases = "SELECT * FROM purchases";
			preparedStatement = connection.prepareStatement(getAllPurchases);
			result = preparedStatement.executeQuery();

			Collection<Purchase> allPurchases = new ArrayList<>();

			while (result.next()) {
				allPurchases.add(extractPurchaseFromResultSet(result));
			}

			return allPurchases;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retreive all purchases by coupon", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public Collection<Purchase> getCompanyPurchases(long companyId) throws CouponsProjectExceptions {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<Purchase> companyPurchases = new ArrayList<Purchase>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM purchases WHERE coupon_id IN (SELECT coup_id FROM coupons WHERE comp_id=?)");
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				companyPurchases.add(extractPurchaseFromResultSet(resultSet));
			}
			return companyPurchases;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponsProjectExceptions("failed to retreive all purchases by company", e);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public boolean isPurchasExistsBypurchasId(long purchasId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT purchase_id FROM purchases WHERE purchase_id = ?");
			preparedStatement.setLong(1, purchasId);
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
	public boolean isPurchasExistsBycustomerId(long customerId) throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT customer_id FROM purchases WHERE customer_id = ?");
			preparedStatement.setLong(1, customerId);
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
	public void deleteExpiredPurchase() throws CouponsProjectExceptions {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement(
					"DELETE FROM purchases WHERE coupon_id IN ( SELECT ID FROM coupons WHERE end_date < NOW() )");

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			throw new CouponsProjectExceptions(com.bar.coupons.enums.ErrorType.GENERAL_ERROR, e);

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	private Purchase extractPurchaseFromResultSet(ResultSet result) throws SQLException {
		Purchase purchase = new Purchase();
		purchase.setPurchaseID(result.getLong("purchase_id"));
		purchase.setCustomerID(result.getLong("customer_id"));
		purchase.setCouponID(result.getLong("coupon_id"));
		purchase.setAmount(result.getInt("amount"));

		return purchase;
	}

}
