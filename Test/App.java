package com.bar.coupons.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import java.time.LocalDate;

import com.bar.coupons.beans.Company;
import com.bar.coupons.beans.Coupon;
import com.bar.coupons.beans.Customer;
import com.bar.coupons.beans.Purchase;
import com.bar.coupons.beans.User;
import com.bar.coupons.dao.CompaniesDAO;
import com.bar.coupons.dao.CouponsDAO;
import com.bar.coupons.dao.CustomersDAO;
import com.bar.coupons.dao.PurchasesDAO;
import com.bar.coupons.dao.UsersDAO;
import com.bar.coupons.enums.Category;
import com.bar.coupons.enums.ClientType;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.logic.CompanyController;
import com.bar.coupons.logic.CouponsController;
import com.bar.coupons.logic.CustomersController;
import com.bar.coupons.logic.PurchaseController;
import com.bar.coupons.logic.UsersController;

public class App {

	public static void main(String[] args) throws CouponsProjectExceptions, ParseException {
/*
1. COMANY - done
2. CUOPONS - done
3. USERS - done
4. COSTOMERS
5. PURCHASES
 */
		
		PurchaseController purchasesController = new PurchaseController();
		CouponsController coupController = new CouponsController();
		UsersController userCNTLR = new UsersController();
		CustomersController custController = new CustomersController();
		CompanyController compController = new CompanyController();
		CouponsDAO couponsDao = new CouponsDAO();
		PurchasesDAO purchasesDao = new PurchasesDAO();
		CustomersDAO customerDao = new CustomersDAO();
		CompaniesDAO companyDao = new CompaniesDAO();
		UsersDAO usersDao = new UsersDAO();

		Coupon coupon = new Coupon();
		Company company = new Company();
		User user = new User();
		Customer customer = new Customer();
		Purchase purchase = new Purchase();

//		dd-MM-yyyy	
		Company company1  =new Company("Lothlorien", "Celegrom", "0503498398");
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date1 = simpleDateFormat.parse("22-11-2018");
		Date date2 = simpleDateFormat.parse("22-11-2022");
		user.setPassword("Ypassword%8");
		user.setUserEmail("admin@gmail.com");
		user.setType(ClientType.ADMINISTRATOR);
		userCNTLR.createUser(user);
//		Date date2 = new Date(2022-12-10);
		
		//		purchasesDao.deletePurchase(29);
//		customerDao.deleteCustomer(2); 

		Coupon cup12 = new Coupon(1,1, "title1", date1, date2, 8778, Category.LIVESHOWS, "description", 7656, "uuur.l_image");
		Coupon cup1 = new Coupon( 2, 1, "jerusalem-paris", date1, date2, 100, Category.FLIGHTS, "Cheap flight to paris",30, "uuur.l_image");
		Coupon cup11 = new Coupon(3, 1, "jerusalem-paris", date1, date2, 100, Category.FLIGHTS, "Cheap flight to paris",30, "ur.l_image");
		Coupon cup2 = new Coupon( 4, 1, "Ered_luin", date1, date2, 100, Category.HOTELS, "deal in mountains", 200,	"ur.ul");
		Coupon cup3 = new Coupon( 5, 2, "Mordor", date1, date2, 100, Category.HOTELS, "best deal in hell", 200, "uuu.rl");
		Coupon cup4 = new Coupon( 6, 3, "Aaman", date1, date2, 100, Category.HOTELS, "best deal in paradais", 200,	"uu.rl");
		Coupon cup5 = new Coupon( 7, 3, "Valinor", date1, date2, 100, Category.HOTELS, "best deal in valinor the fair",	200, "ur.ul");
		Coupon cup6 = new Coupon( 8, 3, "Sting", date1, date2, 100, Category.ELECTRICS, "best deal in Middle-Earth the dark", 200, "ur.lu");
		Coupon cup7 = new Coupon( 9, 2, "Mithrim", date1, date2, 100, Category.FLIGHTS, "best deal in Middle-Earth the dark", 200, "ur.lu");
		Coupon cup8 = new Coupon( 10, 2, "Palantir", date1, date2, 100, Category.ELECTRICS, "best deal in the lake of Middle-Earth the dark", 200, "uuu.uurl");
		Coupon cup9 = new Coupon(11, 1, "The Great Ring", date1, date2, 100, Category.COMPUTERS, "Great deal but only if you want to be a dark lord", 499, "uuuu.rl");
		Coupon cup10 = new Coupon(12, 2, "Narnia", date1, date2, 100, Category.FLIGHTS, "the ring of fire", 200, "ur.ul");
		Coupon cup13 = new Coupon(13, 3, "Ivrin The Lake", date1, date2, 654, Category.VACATIONS, "The Holy Lake", 111, "ur.ul");
//		couponsDao.updateCoupon(cup1);
//		couponsDao.updateCoupon(cup2);
//		couponsDao.updateCoupon(cup3);
//		couponsDao.updateCoupon(cup4);
//		couponsDao.updateCoupon(cup5);
//		couponsDao.updateCoupon(cup6);
//		couponsDao.updateCoupon(cup7);
//		couponsDao.updateCoupon(cup8);
//		couponsDao.updateCoupon(cup9);
//		couponsDao.updateCoupon(cup10);
//		couponsDao.updateCoupon(cup11);
//		couponsDao.updateCoupon(cup12);
//		couponsDao.updateCoupon(cup13);
//		User user22 = new User("Eilrond@waplla.com", "Ypasispword%8", ClientType.CUSTOMER);
//		System.out.println(userCNTLR.createUser(user22));
//		userCNTLR.deleteUser(18);
//		userCNTLR.deleteUser(20);
//		userCNTLR.deleteUser(21);
//		userCNTLR.deleteUser(22);
//		custController.deleteCustomer(17);
//		custController.deleteCustomer(19);
//		userCNTLR.createUser(user17);
		Purchase purchase1 = new Purchase(7,1,1);
		Purchase purchase2 = new Purchase(7,2,1);
		Purchase purchase3 = new Purchase(31,3,1);
		Purchase purchase4 = new Purchase(32,4,1);
		Purchase purchase5 = new Purchase(33,5,1);
		Purchase purchase6 = new Purchase(34,5,1);
		Purchase purchase7 = new Purchase(34,6,1);
		purchasesController.purchaseCoupon(purchase1);
		purchasesController.purchaseCoupon(purchase2);
		purchasesController.purchaseCoupon(purchase3);
		purchasesController.purchaseCoupon(purchase4);
		purchasesController.purchaseCoupon(purchase5);
		purchasesController.purchaseCoupon(purchase6);
		purchasesController.purchaseCoupon(purchase7);
		User user22 = (new User("dov2@wahxa.com", "Ypadgxhhxword%8", ClientType.CUSTOMER));
//		userCNTLR.createUser(user22);
			Customer customer12 = new Customer("dov2@wahxa.com", "0548976384", "Ulmo", user22);
//			custController.createCustomer(customer12);
			
			
			
//			Customer customer13 = new Customer("The_Naugrim@walla.com", "0548976384", "Bilbo", user17);
//			custController.createCustomer(customer12);
		
		
		
//			  createCustomer(Customer customer) deleteCustomer updateCustomer(Customer customer)  getAllCustomer() 
		
/*		
		
		 Customer customer1 = new Customer("Gandalf@walla.com", "0548976384", "Beren",
				 * user1); Customer customer2 = new Customer("ert@fgh.com", "0548976384",
				 * "Galadriel", user2); Customer customer3 = new Customer("dasdasbar@fgh.com",
				 * "0548976384", "Galadriel", user3); Customer customer4 = new
				 * Customer("yonzbar2@gmail.com", "0548976384", "Galadriel", user4); Customer
				 * customer5 = new Customer("yonzbar3@gmail.com", "0548976384", "Galadriel",
				 * user5); Customer customer6 = new Customer("Lulerin@gmail.com", "0548976384",
				 * "Galadriel", user6); Customer customer7 = new Customer("Telperion@gmail.com",
				 * "0548976384", "Galadriel", user7); Customer customer8 = new
				 * Customer("Yavanna@gmail.com", "0548976384", "Galadriel", user8); Customer
				 * customer9 = new Customer("Feanor@walla.com", "0548976384", "Galadriel",
				 * user9); Customer customer10 = new Customer("Fingolfin@walla.com",
				 * "0548976384", "Galadriel", user10); Customer customer11 = new
				 * Customer("Valinor@walla.com", "0548976384", "Galadriel", user12); Customer
				 * customer12 = new Customer("Morgoth@walla.com", 15, "0548976384", "Ulmo",
				 * user13);
*/

	}
	}