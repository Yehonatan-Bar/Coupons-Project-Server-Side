package com.bar.coupons.dailyJob;

import java.util.Timer;
import java.util.TimerTask;

import com.bar.coupons.exceptions.CouponsProjectExceptions;


public class MyTimer {

	public static void createTimer() throws CouponsProjectExceptions {

		// Creating a task
		TimerTask task = new MyTimerTask();

		// Creating a timer
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(task, 0, 1000);

		System.out.println("Timer task started");

	}

}