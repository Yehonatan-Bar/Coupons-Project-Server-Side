package com.bar.coupons.utils;

import java.util.regex.Pattern;

import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public class PasswordUtils {

	/**
	 * @param password This function receive a password and check if the password
	 *                 valid
	 * @throws ApplicationException Throw an exception by name
	 */
//	    This checkPassword has the following policy :

//	    At least 8 chars
	//
//	    Contains at least one digit
	//
//	    Contains at least one lower alpha char and one upper alpha char
	//
//	    Contains at least one char within a set of special chars (@#%$^ etc.)
	//
//	    Does not contain space, tab, etc.
	public static void isValidPassword(String password) throws CouponsProjectExceptions {

		Pattern ptr = Pattern.compile("\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z");

		if (!ptr.matcher(password).matches()) {
			throw new CouponsProjectExceptions(ErrorType.INVALID_PASSWORD.getMessage());
		}
	}

}
