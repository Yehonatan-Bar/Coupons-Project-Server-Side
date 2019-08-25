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

import com.bar.coupons.beans.Company;
import com.bar.coupons.exceptions.CouponsProjectExceptions;
import com.bar.coupons.logic.CompanyController;

@RestController
@RequestMapping("/Companies")
public class CompanyApi {
	

	@Autowired
	private CompanyController companyController;

	@PostMapping
	public long createCompany(@RequestBody Company company) throws CouponsProjectExceptions {
		return companyController.createCompany(company);
	}

	@DeleteMapping("/{companyId}")
	public void deleteCompany(@PathVariable("companyId") long companyId) throws CouponsProjectExceptions {
		System.out.println("got here to API apdate");
		companyController.deleteCompany(companyId);
	}

	@PutMapping
	public void updateCompany(@RequestBody Company company) throws CouponsProjectExceptions {
		companyController.updateCompany(company);
	}

	// @RequestParam = get the value from the query string
	@GetMapping
	public Collection<Company> getAllCompanies() throws CouponsProjectExceptions {
		return companyController.getAllCompanies();
	}

	@GetMapping("/{companyId}")
	public Company getCompany(@PathVariable("companyId") long companyId) throws CouponsProjectExceptions {
		return companyController.getCompany(companyId);
	}
}
