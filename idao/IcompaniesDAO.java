package com.bar.coupons.idao;


import java.util.Collection;

import com.bar.coupons.beans.Company;
import com.bar.coupons.exceptions.CouponsProjectExceptions;


public interface IcompaniesDAO {
	
	long createCompany(Company company) throws CouponsProjectExceptions;
    void deleteCompany(long companyID) throws CouponsProjectExceptions;   
    void updateCompany(Company company) throws CouponsProjectExceptions;
    Company getCompany(long companyID) throws CouponsProjectExceptions;
    Company getCompany(String compName) throws CouponsProjectExceptions;
	Collection<Company> getAllCompanies() throws CouponsProjectExceptions;
	boolean isCompanyExists(long companyId) throws CouponsProjectExceptions;

    
}