 package com.bar.coupons.utils;

import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public class PhoneNumberUtils {

	public static void isValidPhoneNumber(String phoneNumber) throws CouponsProjectExceptions {

		if (phoneNumber == null || phoneNumber.isEmpty())
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());

		if (phoneNumber.charAt(0) != '0')
			throw new CouponsProjectExceptions(ErrorType.INVALID_PHONE_NUMBER.getMessage());

		if (phoneNumber.length() != 10)
			throw new CouponsProjectExceptions(ErrorType.INVALID_PHONE_NUMBER.getMessage());

		for (int i = 0; i < phoneNumber.length(); i++) {
			if (phoneNumber.charAt(i) > '9' || phoneNumber.charAt(i) < '0')
				throw new CouponsProjectExceptions(ErrorType.INVALID_PHONE_NUMBER.getMessage());
		}

	}
}
