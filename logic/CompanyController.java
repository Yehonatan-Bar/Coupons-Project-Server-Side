package com.bar.coupons.logic;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.bar.coupons.beans.Company;
import com.bar.coupons.beans.Coupon;
import com.bar.coupons.dao.CompaniesDAO;
import com.bar.coupons.dao.CouponsDAO;
import com.bar.coupons.dao.PurchasesDAO;
import com.bar.coupons.dao.UsersDAO;
import com.bar.coupons.enums.ErrorType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.utils.EmailUtils;
import com.bar.coupons.utils.IdUtils;
import com.bar.coupons.utils.NameUtils;
import com.bar.coupons.utils.PhoneNumberUtils;


@Controller
public class CompanyController {

	@Autowired
	private CompaniesDAO compDao = new CompaniesDAO();
	@Autowired
	private CouponsDAO couponsDao = new CouponsDAO();
	@Autowired
	private PurchasesDAO purchasesDao = new PurchasesDAO();
	@Autowired
	private UsersDAO usersDao = new UsersDAO();
	@Autowired
	private UsersController userController = new UsersController();
	@Autowired
	private CouponsController couponsController = new CouponsController();
	@Autowired
	private PurchaseController purchasesController = new PurchaseController();

	public CompanyController() {
		super();
	}

	public long createCompany(Company company) throws CouponsProjectExceptions {

		if (company == null)
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());

		NameUtils.isValidName(company.getCompName());
		PhoneNumberUtils.isValidPhoneNumber(company.getContactPhone());
		EmailUtils.isValidEmail(company.getContactEmail());
		if (company.getCompanyID() > 0) {
			throw new CouponsProjectExceptions(ErrorType.NEW_COMPANY_CANT_HAVE_ID.getMessage());

		}
		if (compDao.getCompany(company.getCompName()) != null) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_ALREADY_EXISTS.getMessage());
		}
		return compDao.createCompany(company);

	}

	public void deleteCompany(long companyId) throws CouponsProjectExceptions {

		IdUtils.isValidId(companyId);
		if (!compDao.isCompanyExists(companyId)) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
		}
		if (couponsDao.isCouponsByCompanyIdExist(companyId)) {
			Collection<Coupon> allCouponsForCompany = new ArrayList<>();
			allCouponsForCompany = couponsController.getAllCouponsByCompanyId(companyId);
					for (Coupon coupon : allCouponsForCompany) {
				couponsController.deleteCoupon(coupon.getCouponID());
				if (purchasesDao.getAllPurchasesByCoupon(coupon.getCouponID()) != null) {
					System.out.println("********** coupon.getCouponID())" + coupon.getCouponID());
					purchasesController.deletePurchaseBycouponId(coupon.getCouponID());
				}
			}
		}
		if (usersDao.getUserByCompanyId(companyId)!=null){
		userController.deleteUserByCompanyId(companyId);
		}
		compDao.deleteCompany(companyId);
	}

	public void updateCompany(Company company) throws CouponsProjectExceptions {

		if (company == null)
			throw new CouponsProjectExceptions(ErrorType.EMPTY.getMessage());

		IdUtils.isValidId(company.getCompanyID());
		NameUtils.isValidName(company.getCompName());
		PhoneNumberUtils.isValidPhoneNumber(company.getContactPhone());
		EmailUtils.isValidEmail(company.getContactEmail());

		if (!compDao.isCompanyExists(company.getCompanyID())) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
		}
		compDao.updateCompany(company);

	}

	public Collection<Company> getAllCompanies() throws CouponsProjectExceptions {

		return compDao.getAllCompanies();

	}

	public Company getCompany(long companyId) throws CouponsProjectExceptions {

		IdUtils.isValidId(companyId);

		if (compDao.getCompany(companyId) == null) {
			throw new CouponsProjectExceptions(ErrorType.COMPANY_IS_NOT_EXISTS.getMessage());
		}
		return compDao.getCompany(companyId);

	}

}
