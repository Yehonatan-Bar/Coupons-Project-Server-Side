package com.bar.coupons.utils;

import com.bar.coupons.enums.ClientType;
import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public class TypeUtils {

	public static void isValidType(ClientType type) throws CouponsProjectExceptions {

		if (type == null)
			throw new CouponsProjectExceptions(ErrorType.INVALID_TYPE.getMessage());

	}

}
