package com.bar.coupons.idao;

import java.util.Collection;

import com.bar.coupons.beans.Customer;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public interface IcustomersDAO {

	void createCustomer(Customer customer) throws CouponsProjectExceptions;

	void deleteCustomer(long customerID) throws CouponsProjectExceptions;

	void updateCustomer(Customer customer) throws CouponsProjectExceptions;

	Customer getCustomer(long customerID) throws CouponsProjectExceptions;

	Collection<Customer> getAllCustomers() throws CouponsProjectExceptions;

	boolean isCustomerExistsById(long customerId) throws CouponsProjectExceptions;

	boolean isCustomerExistsByEmail(String customerEmail) throws CouponsProjectExceptions;

}
