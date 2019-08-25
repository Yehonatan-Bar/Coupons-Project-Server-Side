package com.bar.coupons.utils;

import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public class IdUtils {

	/**
	 * @param id Receive an id
	 * @return This function return true if id is valid or throw exception if didn't
	 * @throws ApplicationException throw exception by name
	 */
	public static void isValidId(long id) throws CouponsProjectExceptions {

//		if (id < 1)
//			System.out.println("isValidId   id: " + id);
//			throw new CouponsProjectExceptions(ErrorType.INVALID_ID.getMessage());

	}
	public static void isIdCompatible(long customerId, long userId) throws CouponsProjectExceptions {
		
		if (customerId!=userId) {
			System.out.println("customer Id = " + customerId + "; user Id = " + userId);
			throw new CouponsProjectExceptions(ErrorType.INVALID_ID_UNCOMPATIBLE.getMessage());
		}
	}
}
