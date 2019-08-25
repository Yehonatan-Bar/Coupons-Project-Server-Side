package com.bar.coupons.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bar.coupons.beans.Coupon;
import com.bar.coupons.beans.Purchase;
import com.bar.coupons.dao.CouponsDAO;
import com.bar.coupons.dao.CustomersDAO;
import com.bar.coupons.dao.PurchasesDAO;
import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.utils.AmountUtils;
import com.bar.coupons.utils.IdUtils;

@Controller
public class PurchaseController {
	@Autowired
	private PurchasesDAO purchasesDao = new PurchasesDAO();
	@Autowired
	private CouponsDAO couponsDao = new CouponsDAO();
	@Autowired
	private CustomersDAO customerDao = new CustomersDAO();
	@Autowired
	private Coupon coupon = new Coupon();

	public long purchaseCoupon(Purchase newPurchase) throws CouponsProjectExceptions {

		IdUtils.isValidId(newPurchase.getCustomerID());
		IdUtils.isValidId(newPurchase.getCouponID());
		AmountUtils.isValidAmount(newPurchase.getAmount());
		if (!customerDao.isCustomerExistsById(newPurchase.getCustomerID())) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}

		if (!couponsDao.isCouponExists(newPurchase.getCouponID())) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
		}
		coupon = couponsDao.getCoupon(newPurchase.getCouponID());
		System.out.println(new Date());
		if (coupon.getEndDate().before(new Date())) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_EXPIRED.getMessage());
		}

		if (coupon.getAmount() < 1) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_OUT_OF_ORDER.getMessage());
		}
		if (coupon.getAmount() < newPurchase.getAmount()) {
			throw new CouponsProjectExceptions(ErrorType.AMOUNT_REQUIRED_MORE_THAN_AVAILABLE.getMessage());
		}
		coupon.setAmount(coupon.getAmount() - newPurchase.getAmount());
		couponsDao.updateCoupon(coupon);

		return purchasesDao.createPurchase(newPurchase);

	}

	public void updatePurchase(Purchase newPurchase) throws CouponsProjectExceptions {

		IdUtils.isValidId(newPurchase.getCustomerID());
		IdUtils.isValidId(newPurchase.getCouponID());
		AmountUtils.isValidAmount(newPurchase.getAmount());
		if (!customerDao.isCustomerExistsById(newPurchase.getCustomerID())) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}

		if (!couponsDao.isCouponExists(newPurchase.getCouponID())) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
		}

		coupon = couponsDao.getCoupon(newPurchase.getCouponID());

		if (coupon.getEndDate().before(new Date())) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_EXPIRED.getMessage());
		}

		if (coupon.getAmount() < 1) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_OUT_OF_ORDER.getMessage());
		}
		if (coupon.getAmount() < newPurchase.getAmount()) {
			throw new CouponsProjectExceptions(ErrorType.AMOUNT_REQUIRED_MORE_THAN_AVAILABLE.getMessage());
		}
		Purchase oldPurchase = purchasesDao.getPurchaseByID(newPurchase.getPurchaseID());
		int oldPurchaseAmount = oldPurchase.getAmount();
		coupon.setAmount(coupon.getAmount() - (newPurchase.getAmount() - oldPurchaseAmount));
		couponsDao.updateCoupon(coupon);
		purchasesDao.updatePurchase(newPurchase);

	}

	public void deletePurchaseBycustomerAndCouponId(long customerId, long couponId) throws CouponsProjectExceptions {

		IdUtils.isValidId(customerId);
		IdUtils.isValidId(couponId);
		if (purchasesDao.getPurchaseBycustomerIdAndCouponId(customerId, couponId) == null) {
			throw new CouponsProjectExceptions(ErrorType.PURCHASE_IS_NOT_EXISTS.getMessage());
		}
		purchasesDao.deletePurchaseBycustomerIdAndCouponId(customerId, couponId);

	}

	public void deletePurchase(long purchaseId) throws CouponsProjectExceptions {

		IdUtils.isValidId(purchaseId);

		if (!purchasesDao.isPurchasExistsBypurchasId(purchaseId)) {
			throw new CouponsProjectExceptions(ErrorType.PURCHASE_IS_NOT_EXISTS.getMessage());
		}
		purchasesDao.deletePurchase(purchaseId);

	}

	public void deletePurchaseBycouponId(long couponId) throws CouponsProjectExceptions {

		IdUtils.isValidId(couponId);
		Collection<Purchase> purchasesByCouponId = new ArrayList<>();
		purchasesByCouponId = getAllPurchasesbyCoupon(couponId);
		if (purchasesByCouponId.isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.PURCHASE_IS_NOT_EXISTS.getMessage());
		}
		purchasesDao.deletePurchaseBycouponId(couponId);

	}

	public Collection<Purchase> getAllPurchase() throws CouponsProjectExceptions {

		return purchasesDao.getAllPurchases();

	}

	public Collection<Purchase> getCustomerPurchases(long customerId) throws CouponsProjectExceptions {

		IdUtils.isValidId(customerId);

		if (!customerDao.isCustomerExistsById(customerId)) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}
		if (!purchasesDao.isPurchasExistsBycustomerId(customerId)) {
			throw new CouponsProjectExceptions(ErrorType.PURCHASE_IS_NOT_EXISTS.getMessage());
		}
		return purchasesDao.getAllPurchasesbyCustomer(customerId);

	}

	public Collection<Purchase> getAllPurchasesbyCoupon(long couponId) throws CouponsProjectExceptions {

		IdUtils.isValidId(couponId);
		System.out.println("*** in getAllPurchasesbyCoupon() - couponsDao.getCoupon(couponId).getCouponID() = " + couponsDao.getCoupon(couponId).getCouponID());
		if (couponsDao.getCoupon(couponId) == null) {
			throw new CouponsProjectExceptions(	ErrorType.PURCHASE_IS_NOT_EXISTS.getMessage());
		}
		return purchasesDao.getAllPurchasesByCoupon(couponId);
	}
	public Collection<Purchase> getCompanyPurchases( long companyId)  throws CouponsProjectExceptions {
		
		IdUtils.isValidId(companyId);
		if (couponsDao.getCoupon(companyId) == null) {
			throw new CouponsProjectExceptions(ErrorType.PURCHASE_IS_NOT_EXISTS.getMessage());
		}

		return purchasesDao.getCompanyPurchases(companyId);
	}
}
