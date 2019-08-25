package com.bar.coupons.dailyJob;

import java.util.TimerTask;

import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.logic.CouponsController;

public class MyTimerTask extends TimerTask {

	private CouponsController couponController;

	public MyTimerTask() throws CouponsProjectExceptions {

		couponController = new CouponsController();

	}

	@Override
	public void run() {

		try {

			couponController.deleteExpiredCoupon();

		} catch (CouponsProjectExceptions e) {

			e.printStackTrace();

		}

	}

}