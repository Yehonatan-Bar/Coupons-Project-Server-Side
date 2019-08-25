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

import com.bar.coupons.beans.Coupon;
import com.bar.coupons.enums.Category;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.logic.CouponsController;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {
	@Autowired
	private CouponsController couponsController = new CouponsController();

@PostMapping
public long createCoupon(@RequestBody Coupon coupon) throws CouponsProjectExceptions {
	System.out.println("create couponAPI. date:" + coupon.getStartDate());
return	couponsController.createCoupon(coupon);
}

@DeleteMapping("/{couponId}")
public void deleteCoupon(@PathVariable("couponId") long couponId) throws CouponsProjectExceptions {
	couponsController.deleteCoupon(couponId);
	}

@PutMapping
public void updateCoupon(@RequestBody Coupon coupon) throws CouponsProjectExceptions {
	couponsController.updateCoupon(coupon);
	}
	
@GetMapping
public Collection<Coupon> getAllCoupons() throws CouponsProjectExceptions {
	System.out.println("api check");
	return couponsController.getAllCoupons();	
	
	}
@GetMapping("/{couponId}")
public Coupon getCoupon(@PathVariable("couponId") long couponId) throws CouponsProjectExceptions {
	return couponsController.getCoupon(couponId);	
	
}

@GetMapping("/byCompanyId")
public Collection<Coupon> getAllCouponsByCompanyId (@RequestParam("companyId") long companyId) throws CouponsProjectExceptions {
	
return 	couponsController.getAllCouponsByCompanyId(companyId);
}

@GetMapping("/byCategory")
public Collection<Coupon> getAllCouponsByCategory(@RequestParam("category") Category category) throws CouponsProjectExceptions {
	return 	couponsController.getAllCouponsByCategory(category);
}

@GetMapping("/byCategotyAndCompanyId")
public Collection<Coupon> getCompanyCouponsByCategory(@RequestParam("companyId") long companyId, @RequestParam("category") Category category) throws CouponsProjectExceptions{
	return 	couponsController.getCompanyCouponsByCategory(companyId, category);
}

@GetMapping("/byMaxPrice")
public Collection<Coupon> getCouponsByMaxPrice(@RequestParam("maxPrice") double maxPrice) throws CouponsProjectExceptions {
	return 	couponsController.getCouponsByMaxPrice(maxPrice);
	
}

@GetMapping("/byCompanyIdAndMaxPrice")
public Collection<Coupon> getCompanyCouponsByMaxPrice(@RequestParam("companyId")long companyId,@RequestParam("maxPrice")  double maxPrice) throws CouponsProjectExceptions {
	return 	couponsController.getCompanyCouponsByMaxPrice( companyId,  maxPrice);
}
@GetMapping("/byCustomerId")
public Collection<Coupon> getCouponsByCustomerId(@RequestParam("customerId") long customerId) throws CouponsProjectExceptions {
	System.out.println("*********8\n In CouponApi/getCouponsByCustomerId: customerId = " + customerId);
	return 	couponsController.getCouponsByCustomerId(customerId);
}

@GetMapping("/byTheCustomerId")
public Collection<Coupon> getCouponsByCustomerTheId(@RequestParam("customerId") long customerId) throws CouponsProjectExceptions {
	System.out.println("\n**************\n\n In CouponApi/getCouponsByCustomerId: customerId = " + customerId + "\n\n**************\n");
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


}
