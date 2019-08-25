
package com.bar.coupons.beans;

import org.springframework.stereotype.Repository;

@Repository
public class Purchase {

		private long purchaseID;
		private long customerID;
		private long couponID;
		private int amount;
		
		
		public Purchase(long customerID, long couponID, int amount) {
			this.customerID = customerID;
			this.couponID = couponID;
			this.amount = amount;
		}


		public Purchase(long purchaseID, long customerID, long couponID, int amount) {
			this(customerID,couponID,amount);
			this.purchaseID = purchaseID;
		}

		public Purchase() {
		}


		public long getPurchaseID() {
			return purchaseID;
		}


		public void setPurchaseID(long purchaseID) {
			this.purchaseID = purchaseID;
		}


		public long getCustomerID() {
			return customerID;
		}


		public void setCustomerID(long customerID) {
			this.customerID = customerID;
		}


		public long getCouponID() {
			return couponID;
		}


		public void setCouponID(long couponID) {
			this.couponID = couponID;
		}


		public int getAmount() {
			return amount;
		}


		public void setAmount(int amount) {
			this.amount = amount;
		}


		@Override
		public String toString() {
			return "Purchase [purchaseID=" + purchaseID + ", customerID=" + customerID + ", couponID=" + couponID
					+ ", amount=" + amount + "]";
		}
		
		
		
}
