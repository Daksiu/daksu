package com.daksu.security.filter;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.daksu.provisioner.domain.Company;
import com.daksu.provisioner.repository.CompanyRepository;
import com.daksu.security.filter.exception.CompanyDoesNotExistException;
import com.daksu.user.domain.User;
import com.daksu.user.repositoty.UserRepository;

public class DaksuAuthenticationProvider implements AuthenticationProvider {

	private UserRepository userRepository;

	private CompanyRepository companyRepository;

	private PasswordEncoder passwordEncoder;

	public DaksuAuthenticationProvider(UserRepository userRepository, CompanyRepository companyRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();
		String companyId = ((DaksuUserAuthenticationToken) authentication).getCompany();

		Company company = companyRepository.findCompanyById(companyId);

		if (company == null) {
			throw new CompanyDoesNotExistException(
					"Company " + companyId + " does not exist. Please register you compnay free with daksu");
		}

		User user = userRepository.findUserByUsername(name);

		if (user == null) {
			throw new UsernameNotFoundException("Username " + name + " is not registered with company " + companyId);
		}

		if (passwordEncoder.matches(rawPassword, user.getPassword())) {
			return new DaksuUserAuthenticationToken(name, rawPassword, company.getCompanyId(), user.getAuthorities());
		} else {
			return new DaksuUserAuthenticationToken(name, rawPassword, company.getCompanyId());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (DaksuUserAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
