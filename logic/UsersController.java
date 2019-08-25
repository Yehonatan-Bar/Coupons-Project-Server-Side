package com.bar.coupons.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bar.coupons.dao.CompaniesDAO;
import com.bar.coupons.beans.Login;
import com.bar.coupons.beans.User;
import com.bar.coupons.beans.UserDataMap;
import com.bar.coupons.beans.UserDataClient;
import com.bar.coupons.dao.UsersDAO;
import com.bar.coupons.enums.ClientType;
import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.utils.IdUtils;
import com.bar.coupons.utils.NameUtils;
import com.bar.coupons.utils.PasswordUtils;
import com.bar.coupons.utils.TypeUtils;





@Controller
public class UsersController {
	@Autowired
	private UsersDAO usersDao;
	@Autowired
	private CacheManager cacheManager = new CacheManager();
	
	public UsersController() {
		this.usersDao = new UsersDAO();
	}

	public UserDataClient login(Login login) throws CouponsProjectExceptions {
		System.out.println("user CONTROLLER Login - check");
		ClientType clientType = usersDao.login(login);

		int token = generateEncryptedToken(login.getUserName());
		UserDataMap userDataToMap = generateUserDataToMap(login.getUserName());
		long id = usersDao.getUserIDFromDataBase(login.getUserName());
		long companyID = usersDao.getUserCompanyIDFromDataBase(login.getUserName());
		UserDataClient userDataToClient = generateUserDataToClient(clientType, token,id,companyID);
		cacheManager.put(token, userDataToMap);

		return userDataToClient;
	}

	
	
	public long createUser(User user) throws CouponsProjectExceptions {
		System.out.println("User: " + user + "\n is user null? -> " + (user==null));
		if ((user.equals(null))==(true)) {
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());
		}

		NameUtils.isValidName(user.getUserEmail());
		PasswordUtils.isValidPassword(user.getPassword());
		TypeUtils.isValidType(user.getType());

		if (usersDao.isUserExistsByEmail(user.getUserEmail())) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_ALREADY_EXISTS.getMessage());
		}
		if ((user.getCompanyID() != null && user.getType().equals(ClientType.CUSTOMER))) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_CUSTOMER.getMessage());
		}
		if (user.getCompanyID() == null && user.getType().equals(ClientType.COMPANY)) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_COMPNAY.getMessage());
		}
		CompaniesDAO ComoanyDao = new CompaniesDAO();
		if ((user.getCompanyID() != null)&&!ComoanyDao.isCompanyExists(user.getCompanyID())) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
		}
		System.out.println("UC: \n*******************************\n" + user);
		return usersDao.createUser(user);
	}

	public void updateUser(User user) throws CouponsProjectExceptions {

		IdUtils.isValidId(user.getUserID());
		NameUtils.isValidName(user.getUserEmail());
		PasswordUtils.isValidPassword(user.getPassword());
		TypeUtils.isValidType(user.getType());

		if ((user.getCompanyID() != 0 && user.getType().equals(ClientType.CUSTOMER))) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_CUSTOMER.getMessage());
		}
		if (user.getCompanyID() == 0 && user.getType().equals(ClientType.COMPANY)) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_COMPNAY.getMessage());
		}
		usersDao.updateUser(user);
	}

	public void deleteUser(long userId) throws CouponsProjectExceptions {

		IdUtils.isValidId(userId);

		if (!usersDao.isUserExistsById(userId)) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_NOT_EXISTS.getMessage());
		}
		usersDao.deleteUser(userId);

	}

	public void deleteUserByCompanyId(long companyId) throws CouponsProjectExceptions {

		IdUtils.isValidId(companyId);
		if (usersDao.getUserByCompanyId(companyId) == null) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_NOT_EXISTS.getMessage());
		}
		usersDao.deleteCompanysUsers(companyId);

	}

	public Collection<User> getAllUsers() throws CouponsProjectExceptions {

		return usersDao.getAllUsers();

	}
	public User getUserById(long userID) throws CouponsProjectExceptions {
		IdUtils.isValidId(userID);
		if (!usersDao.isUserExistsById(userID)) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_NOT_EXISTS.getMessage());
		}
		return usersDao.getUserByUserID(userID);
		
	}
	
	public Collection<User> getUsersByType(ClientType userCategory) throws CouponsProjectExceptions {
		
		TypeUtils.isValidType(userCategory);
			return usersDao.getAllUsersByType(userCategory);
	}
	
	public Collection<User> getUserByCompanyId(long companyId) throws CouponsProjectExceptions {
		IdUtils.isValidId(companyId);
		return usersDao.getUserByCompanyId(companyId);
	}
	
	public User getUserByUserEmail(String userEmail) throws CouponsProjectExceptions {
		NameUtils.isValidName(userEmail);
		if (!usersDao.isUserExistsByEmail(userEmail)) {
			throw new CouponsProjectExceptions(ErrorType.USER_IS_NOT_EXISTS.getMessage());
			
		}
		return usersDao.getUserByUserEmail(userEmail);
			
		}
	


	private int generateEncryptedToken(String userName) {

		int additionEncrypt = (int) (Math.random() * 100000) + 1;

		String token = "never FinD ThisEncrypt-coDe()" + additionEncrypt + "%G" + userName + "-/@by@` ";

		return token.hashCode();

	}

	private UserDataMap generateUserDataToMap(String userName) throws CouponsProjectExceptions {

		UserDataMap userData = new UserDataMap();

		userData = usersDao.getUserDataMap(userName);

		return userData;

	}

	private UserDataClient generateUserDataToClient(ClientType clientType, int token, long id, long companyID) throws CouponsProjectExceptions {

		UserDataClient userData = new UserDataClient();

		userData.setClientType(clientType);
		userData.setToken(token);
		userData.setId(id);
		userData.setCompanyID(companyID);

		return userData;

	}


	
}