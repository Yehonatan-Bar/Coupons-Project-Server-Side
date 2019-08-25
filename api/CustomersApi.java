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
import org.springframework.web.bind.annotation.RestController;

import com.bar.coupons.beans.Customer;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.logic.CustomersController;


@RestController
@RequestMapping("/Customers")
public class CustomersApi {
	@Autowired
	private CustomersController customersController = new CustomersController();
	
	@PostMapping
	public long createCustomer(@RequestBody Customer customer) throws CouponsProjectExceptions {
		return customersController.createCustomer(customer);
		
	}
	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable("customerId") long customerId) throws CouponsProjectExceptions {
		customersController.deleteCustomer(customerId);
	}
	
	@PutMapping
	public void  updateCustomer(@RequestBody Customer customer) throws CouponsProjectExceptions {
		customersController.updateCustomer(customer);
	}
	
	@GetMapping("/{customerId}")
	public Customer getCustomer(@PathVariable("customerId") long customerId) throws CouponsProjectExceptions {
		return	customersController.getCustomer(customerId);	
	}
	
	@GetMapping
	public Collection<Customer> getAllCustomer() throws CouponsProjectExceptions {
	return	customersController.getAllCustomer();
	}
}

