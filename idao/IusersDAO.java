package com.bar.coupons.idao;

import java.util.Collection;

import com.bar.coupons.beans.Login;
import com.bar.coupons.beans.User;
import com.bar.coupons.beans.UserDataMap;
import com.bar.coupons.enums.ClientType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public interface IusersDAO {

	ClientType login (Login login) throws CouponsProjectExceptions;

	long createUser(User user) throws CouponsProjectExceptions;

	void deleteUser(long userID) throws CouponsProjectExceptions;

	void deleteUser(String userEmail) throws CouponsProjectExceptions;

	void deleteCompanysUsers(long companyID) throws CouponsProjectExceptions;

	void updateUser(User user) throws CouponsProjectExceptions;

	User getUserByUserID(long userID) throws CouponsProjectExceptions;

	User getUserByUserEmail(String userEmail) throws CouponsProjectExceptions;
		
	Collection<User> getUserByCompanyId(long companyId) throws CouponsProjectExceptions;

	Collection<User> getAllUsers() throws CouponsProjectExceptions;
	

	Collection<User> getAllUsersByType(ClientType type) throws CouponsProjectExceptions;

	Long getUserIDFromDataBase(String userEmail) throws CouponsProjectExceptions;

	boolean isUserExistsById(long couponId) throws CouponsProjectExceptions;

	boolean isUserExistsByEmail(String userEmail) throws CouponsProjectExceptions;
	
	public UserDataMap getUserDataMap(String userName) throws CouponsProjectExceptions;
	
	public Long getUserCompanyIDFromDataBase(String userEmail) throws CouponsProjectExceptions;
	

}
