	package com.bar.coupons.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bar.coupons.beans.Login;
import com.bar.coupons.beans.User;
import com.bar.coupons.beans.UserDataClient;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.logic.UsersController;



@RestController
@RequestMapping("/users")
public class UsersApi {
	@Autowired
	private UsersController usersController = new UsersController();

	
	@PostMapping("/login")
	public UserDataClient login(@RequestBody Login login) throws CouponsProjectExceptions {
		System.out.println("user API Login - check1");
		return usersController.login(login);

	}
	@PostMapping("/register")
	public long createUser(@RequestBody User user) throws CouponsProjectExceptions {
		System.out.println("user API createUser - check1");
		return usersController.createUser(user);
	}
	
	@PutMapping
	public void updateUser(@RequestBody User user) throws CouponsProjectExceptions {
		usersController.updateUser(user);
	}
	@DeleteMapping("/{userId}")	
	public void deleteUser(@PathVariable("userId") long userId) throws CouponsProjectExceptions {
		usersController.deleteUser(userId);
	}

	@DeleteMapping("/companyId")
	public void deleteUserByCompanyId(@RequestParam("companyId")long companyId) throws CouponsProjectExceptions {
		usersController.deleteUserByCompanyId(companyId);
			}
	@GetMapping
	public Collection<User> getAllUsers() throws CouponsProjectExceptions {
		return usersController.getAllUsers();
	}
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable("userId") long userId) throws CouponsProjectExceptions {
		return usersController.getUserById(userId);
	}

    @GetMapping("/byCompanyId") 
	public Collection<User> getUserByCompanyId(@RequestParam("companyId") long companyId) throws CouponsProjectExceptions {
		return usersController.getUserByCompanyId(companyId);
	}
	

	}



/*




@PostMapping
public long createCoupon(@RequestBody Coupon coupon) throws CouponsProjectExceptions {
return	couponsController.createCoupon(coupon);
}

@DeleteMapping("/{couponId}")
public void deleteCoupon(@PathVariable("couponId") long coupId) throws CouponsProjectExceptions {
	couponsController.deleteCoupon(coupId);
	}

@PutMapping
public void updateCoupon(@RequestBody Coupon coupon) throws CouponsProjectExceptions {
	couponsController.updateCoupon(coupon);
	}
	
@GetMapping
public Collection<Coupon> getAllCoupons() throws CouponsProjectExceptions {
	return couponsController.getAllCoupons();	
	
	}

@GetMapping("/{couponId}")
public Collection<Coupon> getAllCouponsByCompanyId(@PathVariable("couponId") long companyId) throws CouponsProjectExceptions {
return 	couponsController.getAllCouponsByCompanyId(companyId);
}

@GetMapping("/byCategory")
public Collection<Coupon> getAllCouponsByCategory(@RequestParam("category") Category category) throws CouponsProjectExceptions {
	return 	couponsController.getAllCouponsByCategory(category);
}

@GetMapping("/byCategotyAndCompany")
public Collection<Coupon> getCompanyCouponsByCategory(@RequestParam("companyId") long companyId, @RequestParam("category") Category category) throws CouponsProjectExceptions{
	return 	couponsController.getCompanyCouponsByCategory(companyId, category);
}

@GetMapping("/maxPrice")
public Collection<Coupon> getCouponsByMaxPrice(@RequestParam("maxPrice") double maxPrice) throws CouponsProjectExceptions {
	return 	couponsController.getCouponsByMaxPrice(maxPrice);
	
}

@GetMapping("/byCompanyIdAndMaxPrice")
public Collection<Coupon> getCompanyCouponsByMaxPrice(@RequestParam("companyId")long companyId,@RequestParam("maxPrice")  double maxPrice) throws CouponsProjectExceptions {
	return 	couponsController.getCompanyCouponsByMaxPrice( companyId,  maxPrice);
}
@GetMapping("/customerId")
public Collection<Coupon> getCouponsByCustomerId(@RequestParam("customerId") long customerId) throws CouponsProjectExceptions {
	return 	couponsController.getCouponsByCustomerId(customerId);
}

@GetMapping("/byCustomerIdAndMaxPrice")
public Collection<Coupon> getCustomerCouponsByMaxPrice (@RequestParam("customerId") long customerId, @RequestParam("maxPrice") double maxPrice) throws CouponsProjectExceptions {
	return 	couponsController.getCustomerCouponsByMaxPrice(customerId, maxPrice);
}

@GetMapping("/byCustomerIdAndCategory")
public Collection<Coupon> getCustomerCouponsByCategory(@RequestParam("customerId") long customerId, @RequestParam("category") Category category) throws CouponsProjectExceptions{
	return 	couponsController.getCustomerCouponsByCategory(customerId, category);
}

*/