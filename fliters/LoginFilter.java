
package com.bar.coupons.fliters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bar.coupons.beans.UserDataMap;
import com.bar.coupons.logic.ICacheManager;

 
@Component
@WebFilter("/*")
public class LoginFilter implements Filter {

	@Autowired
	private ICacheManager cacheManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		System.out.println(" LoginFilter check1");

		
		String path = ((HttpServletRequest) request).getRequestURI();
		if (path.startsWith("/users/login") || path.startsWith("/customers/register") || path.startsWith("/users/register")) {

			System.out.println("LoginFilter check2");
			chain.doFilter(request, response); // Just continue chain.
              return;
		} else {

			System.out.println("LoginFilter check3");
		
			
			HttpServletRequest req = (HttpServletRequest) request;
			Integer token = Integer.parseInt(req.getParameter("token"));
			System.out.println("LoginFilter check4: in doFilter: token = " + token );
			UserDataMap userData = (UserDataMap) cacheManager.get(token);
			System.out.println("LoginFilter check5:   userData.toString() = " + userData.toString());

			if (userData != null) {
				System.out.println("LoginFilter check6");

				request.setAttribute("userData", userData);
				chain.doFilter(request, response);
				return;

			}

			HttpServletResponse res = (HttpServletResponse) response;
//	        401 = Unauthorized http error code
			res.setStatus(401);

		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
