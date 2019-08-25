package com.bar.coupons.api;

import java.util.ArrayList;
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

import com.bar.coupons.beans.Purchase;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.logic.PurchaseController;


@RestController
@RequestMapping("/Purchases")
public class PurchaseApi {
	@Autowired
	private  PurchaseController purchaseController = new PurchaseController();

@PostMapping
public long purchaseCoupon(@RequestBody Purchase newPurchase) throws CouponsProjectExceptions {
	return purchaseController.purchaseCoupon(newPurchase);
}
@PutMapping
public void updatePurchase(@RequestBody Purchase newPurchase) throws CouponsProjectExceptions {
	 purchaseController.updatePurchase(newPurchase);
}

@DeleteMapping("/{purchaseId}")
public void deletePurchase(@PathVariable("purchaseId")long purchaseId) throws CouponsProjectExceptions {
	 purchaseController.deletePurchase(purchaseId);
}

@DeleteMapping("/byCouponId")
public void deletePurchaseBycouponId(@RequestParam("couponId") long couponId) throws CouponsProjectExceptions {
	purchaseController.deletePurchaseBycouponId(couponId);
}
	
@DeleteMapping("/byCustomerIdAndCouponId")
public void deletePurchaseBycustomerAndCouponId(@RequestParam("customerId") long customerId, @RequestParam("couponId") long couponId) throws CouponsProjectExceptions {
	purchaseController.deletePurchaseBycustomerAndCouponId(customerId, couponId);
}

@GetMapping
public Collection<Purchase> getAllPurchase() throws CouponsProjectExceptions {
	return purchaseController.getAllPurchase();

}
@GetMapping("/byCouponId")
public Collection<Purchase> getAllPurchasesbyCoupon(@RequestParam("couponId")long couponId) throws CouponsProjectExceptions {
	return purchaseController.getAllPurchasesbyCoupon(couponId);
}

@GetMapping("/byCustomerId")
public Collection<Purchase> getCustomerPurchases(@RequestParam("customerId")long customerId) throws CouponsProjectExceptions {
	return purchaseController.getCustomerPurchases(customerId);
}

@GetMapping("/byCompanyId")
public Collection<Purchase> getCompanyPurchases( @RequestParam("companyId")long companyId)  throws CouponsProjectExceptions {

	//	Collection<Purchase> companyPurchases = new ArrayList<Purchase>();
//	companyPurchases = purchaseController.getCompanyPurchases(companyId);
//	for (Purchase purchase:companyPurchases) {
//		System.out.println("API: " + purchase.getCouponID());
//	}

	return purchaseController.getCompanyPurchases(companyId);
}

}
