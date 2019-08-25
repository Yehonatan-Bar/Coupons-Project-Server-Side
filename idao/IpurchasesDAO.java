package com.bar.coupons.idao;

import java.util.Collection;

import com.bar.coupons.beans.Purchase;
import com.bar.coupons.exceptions.CouponsProjectExceptions;


public interface IpurchasesDAO {

	long createPurchase(Purchase purchase) throws CouponsProjectExceptions;

	void deletePurchase(long purchaseID) throws CouponsProjectExceptions;

	void deletePurchaseBycouponId(long couponId) throws CouponsProjectExceptions;

	void deletePurchaseBycustomerIdAndCouponId(long customerid, long couponId) throws CouponsProjectExceptions;

	void updatePurchase(Purchase purchase) throws CouponsProjectExceptions;

	Purchase getPurchaseByID(long purchaseID) throws CouponsProjectExceptions;

	Purchase getPurchaseBycustomerIdAndCouponId(long customerId, long couponId) throws CouponsProjectExceptions;

	Collection<Purchase> getAllPurchasesbyCustomer(long customerID) throws CouponsProjectExceptions;

	Collection<Purchase> getAllPurchasesByCoupon(long couponID) throws CouponsProjectExceptions;

	Collection<Purchase> getAllPurchases() throws CouponsProjectExceptions;

	boolean isPurchasExistsBypurchasId(long purchasId) throws CouponsProjectExceptions;

	boolean isPurchasExistsBycustomerId(long customerId) throws CouponsProjectExceptions;

	void deleteExpiredPurchase() throws CouponsProjectExceptions;
	
	Collection<Purchase> getCompanyPurchases( long companyId) throws CouponsProjectExceptions;

}
