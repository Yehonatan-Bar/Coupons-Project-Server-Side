package com.bar.coupons.logic;

import java.util.ArrayList;
import java.util.Collection;

import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bar.coupons.beans.Coupon;
import com.bar.coupons.dao.CompaniesDAO;
import com.bar.coupons.dao.CouponsDAO;
import com.bar.coupons.dao.CustomersDAO;
import com.bar.coupons.dao.PurchasesDAO;
import com.bar.coupons.enums.Category;
import com.bar.coupons.utils.AmountUtils;
import com.bar.coupons.utils.DateUtils;
import com.bar.coupons.utils.IdUtils;
import com.bar.coupons.utils.NameUtils;


@Controller
public class CouponsController {
	@Autowired
	private CouponsDAO couponsDao = new CouponsDAO();
	@Autowired
	private PurchasesDAO purchasesDao = new PurchasesDAO();
	@Autowired
	private CustomersDAO customerDao = new CustomersDAO();
	@Autowired
	private CompaniesDAO companyDao = new CompaniesDAO();

	public CouponsController() {
		super();
	}

	public CouponsController(CouponsDAO couponsDao, PurchasesDAO purchasesDao, CustomersDAO customerDao,
			CompaniesDAO companyDao, Coupon couponFromDataBase) {
		super();
		this.couponsDao = couponsDao;
		this.purchasesDao = purchasesDao;
		this.customerDao = customerDao;
		this.companyDao = companyDao;
	}

	public long createCoupon(Coupon coupon) throws CouponsProjectExceptions {

		if (coupon == null)
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());

		IdUtils.isValidId(coupon.getCompanyID());
		isValidCategory(coupon.getCategory());
		NameUtils.isValidName(coupon.getTitle());
		DateUtils.isValidDate(coupon.getStartDate(), coupon.getEndDate()); 
		AmountUtils.isValidAmount(coupon.getAmount());
		isValidPrice(coupon.getPrice());
		isValidImage(coupon.getImage());

		if (couponsDao.isCouponsExistByTitle(coupon.getTitle())){
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_ALREADY_EXISTS.getMessage());
		}
		if (!companyDao.isCompanyExists(coupon.getCompanyID())) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());

		}
		return couponsDao.createCoupon(coupon);

	}

	public void deleteCoupon(long couponId) throws CouponsProjectExceptions {

		IdUtils.isValidId(couponId);

		if (couponsDao.getCoupon(couponId) == null) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
		}
		if (purchasesDao.getAllPurchasesByCoupon(couponId) != null) {
		purchasesDao.deletePurchaseBycouponId(couponId);
		}
		couponsDao.deleteCoupon(couponId);

	}

	public void updateCoupon(Coupon coupon) throws CouponsProjectExceptions {

		if (coupon == null)
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());
		IdUtils.isValidId(coupon.getCouponID());
		IdUtils.isValidId(coupon.getCompanyID());
		isValidCategory(coupon.getCategory());
		NameUtils.isValidName(coupon.getTitle());
		DateUtils.isValidDate(coupon.getStartDate(), coupon.getEndDate());
		AmountUtils.isValidAmount(coupon.getAmount());
		isValidPrice(coupon.getPrice());
		isValidImage(coupon.getImage());
		if (!couponsDao.isCouponExists(coupon.getCouponID())) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
		}

		if (!companyDao.isCompanyExists(coupon.getCompanyID())) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());

		}


		 couponsDao.updateCoupon(coupon);
	}

	public Collection<Coupon> getAllCoupons() throws CouponsProjectExceptions {
		System.out.println("controller check before delete expierded");
		couponsDao.deleteExpiredCoupon();
		System.out.println("controller check after delete expierded");

		return couponsDao.getAllCoupons();

	}

	public Coupon getCoupon(long couponId) throws CouponsProjectExceptions {

		IdUtils.isValidId(couponId);

		if (!couponsDao.isCouponExists(couponId)) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
		}
		return couponsDao.getCoupon(couponId);
	}
	public Collection<Coupon> getAllCouponsByCompanyId(long companyId) throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();

		IdUtils.isValidId(companyId);
		if (!companyDao.isCompanyExists(companyId))  {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
		}
		if (couponsDao.getAllCouponsByCompanyId(companyId).isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_HAVE_NO_COUPONS.getMessage());
		}

		return couponsDao.getAllCouponsByCompanyId(companyId);

	}

	public Collection<Coupon> getAllCouponsByCategory(Category category) throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();

		isValidCategory(category);
		if (couponsDao.getAllCouponsByCategory(category).isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.NO_COUPONS_IN_THIS_CATEGORY.getMessage());
		}
		 
		return couponsDao.getAllCouponsByCategory(category);
	}

	public Collection<Coupon> getCompanyCouponsByCategory(long companyId, Category category)
			throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();
		IdUtils.isValidId(companyId);
		isValidCategory(category);

		if (!companyDao.isCompanyExists(companyId))  {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
		}

		if (couponsDao.getCompanyCouponsByCategory(companyId, category).isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_HAVE_NO_COUPONS_IN_CATEGORY.getMessage());
		}
		return couponsDao.getCompanyCouponsByCategory(companyId, category);

	}
	public Collection<Coupon> getCouponsByMaxPrice(double maxPrice) throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();
		isValidPrice(maxPrice);
		if (couponsDao.getCouponsByMaxPrice(maxPrice).isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.ALL_COUPONS_MORE_EXPENSIVE.getMessage());
		}
		return couponsDao.getCouponsByMaxPrice(maxPrice);

	}

	public Collection<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice)
			throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();
		isValidPrice(maxPrice);
		IdUtils.isValidId(companyId);
		if (!companyDao.isCompanyExists(companyId))  {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
		}
		
		if (couponsDao.getCouponsByMaxPriceAndCompany(companyId, maxPrice).isEmpty()) { 
			throw new CouponsProjectExceptions(ErrorType.ALL_COUPONS_MORE_EXPENSIVE_BY_CONPANY.getMessage());
		}
		return couponsDao.getCouponsByMaxPriceAndCompany(companyId, maxPrice);
	}
	
	

	
	public Collection<Coupon> getCouponsByCustomerId(long customerId) throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();
		IdUtils.isValidId(customerId);
		if (!customerDao.isCustomerExistsById(customerId)) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}
		Collection<Coupon> CouponsByCustomerId = new ArrayList<>();
		CouponsByCustomerId = couponsDao.getCouponsByCustomerId(customerId);
		if(CouponsByCustomerId.isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
		}
		return couponsDao.getCouponsByCustomerId(customerId);
	}

	public Collection<Coupon> getCustomerCouponsByCategory(long customerId, Category category)
			throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();

		IdUtils.isValidId(customerId);
		isValidCategory(category);

		if (!customerDao.isCustomerExistsById(customerId)) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}
		Collection<Coupon> CouponsCustomerCouponsByCategory = new ArrayList<>();
		CouponsCustomerCouponsByCategory = couponsDao.getCustomerCouponsByCategory(customerId, category);
		if(CouponsCustomerCouponsByCategory.isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
		}
		return couponsDao.getCustomerCouponsByCategory(customerId, category);

	}

	public Collection<Coupon> getCustomerCouponsByMaxPrice (long customerId, double maxPrice)
			throws CouponsProjectExceptions {
		couponsDao.deleteExpiredCoupon();
		IdUtils.isValidId(customerId);
		isValidPrice(maxPrice);
		if (!customerDao.isCustomerExistsById(customerId)) {
			throw new CouponsProjectExceptions(ErrorType.CUSTOMER_IS_NOT_EXISTS.getMessage());
		}
		
		if (couponsDao.getCustomerCouponsByMaxPrice(customerId,maxPrice).isEmpty()) {
			throw new CouponsProjectExceptions(ErrorType.COUPON_IS_NOT_EXISTS.getMessage());
			
		}
		
		return couponsDao.getCustomerCouponsByMaxPrice(customerId, maxPrice);
	}

	public void deleteExpiredCoupon() throws CouponsProjectExceptions {

		purchasesDao.deleteExpiredPurchase();

		couponsDao.deleteExpiredCoupon();

	}
	
	private void isValidPrice(double price) throws CouponsProjectExceptions {

		if (price <= 0)
			throw new CouponsProjectExceptions(ErrorType.INVALID_PRICE.getMessage());

	}

	private void isValidCategory(Category category) throws CouponsProjectExceptions {

		if (category == null)
			throw new CouponsProjectExceptions(ErrorType.INVALID_CATEGORY.getMessage());

	}

	private void isValidImage(String image) throws CouponsProjectExceptions {

		if (!(image.contains(".")) || image.charAt(image.length() - 1) == '.' || image.charAt(0) == '.')
			throw new CouponsProjectExceptions(ErrorType.INVALID_IMAGE_URL.getMessage());

	}

}
