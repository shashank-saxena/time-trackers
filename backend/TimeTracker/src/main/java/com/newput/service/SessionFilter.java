package com.newput.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Service
public class SessionFilter implements Filter {
	
	@Autowired
	private LoginService loginService;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String path = request.getRequestURI().substring(23);  
				
        if(path.equals("register") || path.equals("login") || path.equals("verify")) {        	
        	//response.sendRedirect("login.jsp");
        }else{        	
        	String token = request.getHeader("token");
    		if(token == null || token.equals("")){
    			response.setHeader("response status", ""+false);
    			response.setHeader("response error", ""+HttpServletResponse.SC_BAD_REQUEST);    			
    		}else{
    			if(loginService.loginSessionFilter(token)){
    				response.setHeader("response status", ""+loginService.loginSessionFilter(token));
    			}else{
    				response.setHeader("response status", ""+loginService.loginSessionFilter(token));
    				response.setHeader("response error", ""+HttpServletResponse.SC_UNAUTHORIZED);
    			}    			
    		}    		
        }
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
