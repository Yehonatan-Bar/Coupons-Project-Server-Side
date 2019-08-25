package com.bar.coupons.utils;

import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public class AmountUtils {

	public static void isValidAmount(int amount) throws CouponsProjectExceptions {

			if (amount <0)

			throw new CouponsProjectExceptions(ErrorType.INVALID_AMOUNT.getMessage());

	}

}
