package com.bar.coupons.utils;


import java.util.Date;

import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public class DateUtils {

	/**
	 * @param startDate Receive a start date
	 * @param endDate   Receive a end date
	 * @return This function return true if date is valid or throw exception if
	 *         isn't
	 * @throws CouponsProjectExceptions Can throw an exception by name
	 */
	public static void isValidDate(Date startDate, Date endDate) throws CouponsProjectExceptions {
		System.out.println("isValidDate" + startDate);
		if ((endDate.before(startDate))) {
			throw new CouponsProjectExceptions(ErrorType.INVALID_DATES.getMessage());

		}
		if (startDate == null || endDate == null)
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());

	}
}
