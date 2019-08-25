package com.bar.coupons.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bar.coupons.beans.Customer;
import com.bar.coupons.beans.User;
import com.bar.coupons.dao.CustomersDAO;
import com.bar.coupons.dao.PurchasesDAO;
import com.bar.coupons.dao.UsersDAO;
import com.bar.coupons.enums.ClientType;
import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.utils.EmailUtils;
import com.bar.coupons.utils.IdUtils;
import com.bar.coupons.utils.NameUtils;
import com.bar.coupons.utils.PasswordUtils;
import com.bar.coupons.utils.PhoneNumberUtils;
import com.bar.coupons.utils.TypeUtils;

@Controller
public class CustomersController {
	@Autowired
	private User user = new User();
	@Autowired
	private UsersDAO userDao = new UsersDAO();
	private long id = 0;
	@Autowired
	private CustomersDAO cDao = new CustomersDAO();
	@Autowired
	private PurchasesDAO purchasesDao = new PurchasesDAO();

	public CustomersController() {

	}

	public long createCustomer(Customer customer) throws CouponsProjectExceptions {

		if (cDao.isCustomerExistsByEmail(customer.getCustEmail())) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_ALREADY_EXISTS.getMessage());
		}
		if (!customer.getUser().getUserEmail().equals(customer.getCustEmail())) {
			throw new CouponsProjectExceptions(ErrorType.EMAIL_IS_NOT_EXISTS.getMessage());
		}
		if (userDao.isUserExistsByEmail(customer.getUser().getUserEmail())) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_ALREADY_EXISTS.getMessage());
		}

		NameUtils.isValidName(customer.getCustName());
		PhoneNumberUtils.isValidPhoneNumber(customer.getCustPhone());
		EmailUtils.isValidEmail(customer.getCustEmail());

		if ((customer.getUser().equals(null)) == (true)) {
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());
		}

		NameUtils.isValidName(customer.getUser().getUserEmail());
		PasswordUtils.isValidPassword(customer.getUser().getPassword());
		TypeUtils.isValidType(customer.getUser().getType());

		if (customer.getUser().getCompanyID() != null) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS__MUST_BE_CUSTOMER.getMessage());
		}
		if (customer.getUser().getCompanyID() == null && customer.getUser().getType().equals(ClientType.COMPANY)) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_COMPNAY.getMessage());
		}
//		CompaniesDAO CompanyDao = new CompaniesDAO();
//		if ((customer.getUser().getCompanyID() != null)) {
//			if (!CompanyDao.isCompanyExists(customer.getUser().getCompanyID())) {
//				throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
//			}
//		}
		
		

		User innerUser = new User();
		innerUser = customer.getUser();
		System.out.println("CC in create user: \n ***************************\n" + innerUser + "\n******");
		id = userDao.createUser(innerUser);
		customer.setCustId(id);
		cDao.createCustomer(customer);
		return id;

	}

//		if (!userDao.isUserExistsByEmail(customer.getCustEmail())) {
//			throw new CouponsProjectExceptions(ErrorType.EMAIL_IS_NOT_EXISTS.getMessage());
//
//		}
//		user = userDao.getUserByUserEmail(customer.getCustEmail());
//
	// userDao.getUserIDFromDataBase(customer.getUser().getUserEmail());
//		NameUtils.isValidName(customer.getUser().getUserEmail());
//		PasswordUtils.isValidPassword(customer.getUser().getPassword());
//		TypeUtils.isValidType(customer.getUser().getType());
//		usersController.createUser(innerUser);
	public void deleteCustomer(long customerId) throws CouponsProjectExceptions {

		IdUtils.isValidId(customerId);

		if (cDao.getCustomer(customerId) == null) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}
		purchasesDao.deletePurchase(customerId);
//		cDao.deleteCustomer(customerId);
//		usersController.deleteUser(customerId);

	}

	public void updateCustomer(Customer customer) throws CouponsProjectExceptions {
//		user = userDao.getUserByUserEmail(customer.getCustEmail());

//		if (!user.getUserEmail().equals(customer.getCustEmail())) {
//			throw new CouponsProjectExceptions(ErrorType.EMAIL_IS_NOT_EXISTS.getMessage());
//		}
//		if (!userDao.isUserExistsById(user.getUserID())) {
//			throw new CouponsProjectExceptions(ErrorType.EMAIL_IS_NOT_EXISTS.getMessage());
//		}
//		if (customer.getUser() == null) {
//			throw new CouponsProjectExceptions(ErrorType.EMPTY_USER_INPUT.getMessage());
//		}
//		IdUtils.isIdCompatible(customer.getCustId(), customer.getUser().getUserID());
		NameUtils.isValidName(customer.getCustName());
		PhoneNumberUtils.isValidPhoneNumber(customer.getCustPhone());
		EmailUtils.isValidEmail(customer.getCustEmail());
//		NameUtils.isValidName(customer.getUser().getUserEmail());
//		PasswordUtils.isValidPassword(customer.getUser().getPassword());
//		TypeUtils.isValidType(customer.getUser().getType());
//		IdUtils.isIdCompatible(customer.getCustId(), customer.getUser().getUserID());

		if (!cDao.isCustomerExistsById(customer.getCustId())) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}
		cDao.updateCustomer(customer);
	}

	public Collection<Customer> getAllCustomer() throws CouponsProjectExceptions {

		return cDao.getAllCustomers();

	}

	public Customer getCustomer(long customerId) throws CouponsProjectExceptions {

		IdUtils.isValidId(customerId);

		if (!cDao.isCustomerExistsById(customerId)) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}
		return cDao.getCustomer(customerId);

	}

}
