package com.daksu.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import com.daksu.user.repositoty.UserRepository;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private UserRepository userRepository;

	public AuthenticationTokenProcessingFilter(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String authToken = this.extractAuthTokenFromRequest(httpRequest);		

		if (authToken != null) {
			String userName = TokenUtils.getUserNameFromToken(authToken);
			String company = TokenUtils.getCompanyFromToken(authToken);			
			if(company!=null) {
				SecurityContextHolder.getContext().setAuthentication(new DaksuUserAuthenticationToken(company));
			}
			UserDetails user = this.userRepository.findUserByUsername(userName);
			if (TokenUtils.validateToken(authToken, user)) {
				UsernamePasswordAuthenticationToken authentication = new DaksuUserAuthenticationToken(user, null,
						company, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Auth-Token");

		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
}