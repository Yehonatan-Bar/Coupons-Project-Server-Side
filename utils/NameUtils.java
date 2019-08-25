package com.bar.coupons.utils;

import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public class NameUtils {

	/**
	 * @param name This function set a new name if valid
	 * @throws ApplicationException Throw an exception by name
	 */
	public static void isValidName(String name) throws CouponsProjectExceptions {

		if (name == null || name.isEmpty())
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());

		if (name.length() < 2)
			throw new CouponsProjectExceptions(ErrorType.INVALID_NAME.getMessage());

	}
}
