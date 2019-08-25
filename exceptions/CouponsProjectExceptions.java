package com.bar.coupons.exceptions;

import java.sql.SQLException;

import com.bar.coupons.enums.ErrorType;

public class CouponsProjectExceptions extends Exception{


	/**
	 * This class create a new exception
	 * 
	 */

		private static final long serialVersionUID = -1835434097622353495L;

		private ErrorType errorType;
		private boolean isCritical;

		// constructor

		/**
		 * @param errorType  Receive an error type
		 * @param message    Receive a message
		 * @param isCritical Receive is critical
		 * @param throwable  Receive a throwable
		 */
		public CouponsProjectExceptions(ErrorType errorType, String message, boolean isCritical, Throwable throwable) {

			super(message, throwable);
			this.errorType = errorType;
			this.isCritical = isCritical;

		}

		/**
		 * @param errorType  Receive an error type
		 * @param message    Receive a message
		 * @param isCritical Receive is critical
		 */
		public CouponsProjectExceptions(ErrorType errorType, String message, boolean isCritical) {

			super(message);
			this.errorType = errorType;
			this.isCritical = isCritical;

		}
		public CouponsProjectExceptions(String message) {
			
			super(message);
			
		}

		// getter

		public CouponsProjectExceptions(String string, SQLException e) {
		}
		public CouponsProjectExceptions(ErrorType errorType, SQLException e) { 
		super(e);
		this.errorType = errorType;
		}
		public CouponsProjectExceptions(ErrorType errorType) { 
			super();
			this.errorType = errorType;
		}
		/**
		 * @return This function return the error type
		 */
		public ErrorType getErrorType() {

			return errorType;

		}

		/**
		 * @return This function return true if critical
		 */
		public boolean getIsCritical() {

			return isCritical;

		}

	}