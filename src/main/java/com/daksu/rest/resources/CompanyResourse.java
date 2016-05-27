package com.daksu.rest.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.daksu.provisioner.bean.RegistrationBean;
import com.daksu.provisioner.service.ProvisioningService;
import com.daksu.rest.transfer.TokenTransfer;
import com.daksu.security.filter.DaksuUserAuthenticationToken;
import com.daksu.security.filter.TokenUtils;

@Component
@Path("/company")
public class CompanyResourse {
	
	@Autowired
	private ProvisioningService provisioningService;
	
	@Autowired
	private UserDetailsService userService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	
	@Autowired
	private UserResource userResourse;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper converter = new ObjectMapper();
		return converter.writeValueAsString(provisioningService.listCompanies());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String read(@PathParam("id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper converter = new ObjectMapper();
		return converter.writeValueAsString(provisioningService.getCompanyById(id));

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public TokenTransfer register(RegistrationBean registrationBean) throws JsonGenerationException, JsonMappingException, IOException {
		
		DaksuUserAuthenticationToken authenticationToken =
				new DaksuUserAuthenticationToken(registrationBean.getEmail(), registrationBean.getPassword(), registrationBean.getCompanyId());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		provisioningService.registerCompany(registrationBean);
		
		/*Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 
		UserDetails userDetails = this.userService.loadUserByUsername(registrationBean.getEmail());

		return new TokenTransfer(TokenUtils.createToken(registrationBean.getCompanyId(), userDetails));*/
		
		return userResourse.authenticate(registrationBean.getCompanyId(), registrationBean.getEmail(), registrationBean.getPassword());
	}
	
	

}
