package spring.angular.social.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import spring.angular.social.filter.SecurityFilter;
import spring.angular.social.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
	public SecurityFilter securityFilter() {
		return new SecurityFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConfig) 
					throws Exception {
		return authConfig.getAuthenticationManager();
	}	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeRequests().antMatchers("/api/users/register","/api/users/login","/h2-console/**").permitAll()
		.anyRequest().authenticated()
		
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
		
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class);
		
		// disable frame options(H2 console enable)
		http.headers().frameOptions().disable();
		
		return http.build();
	}
}
