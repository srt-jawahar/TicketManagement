package com.foucsr.ticketmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.foucsr.ticketmanager.security.CustomUserDetailsService;
import com.foucsr.ticketmanager.security.JwtAuthenticationEntryPoint;
import com.foucsr.ticketmanager.security.JwtAuthenticationFilter;

/**
 * Created by FocusR.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js", "/*.css", "/*.js", "/webjars/**")
				.permitAll().antMatchers("/api/auth/**").permitAll().antMatchers("/index").permitAll()
				.antMatchers("/dashboard").permitAll().antMatchers("/openpo").permitAll().antMatchers("/closedpo")
				.permitAll().antMatchers("/rejectedpo").permitAll().antMatchers("/ack").permitAll()
				.antMatchers("/rescheduledpo").permitAll().antMatchers("/asn").permitAll()
				.antMatchers("/asnshipmentstatus").permitAll().antMatchers("/rtv").permitAll()
				.antMatchers("/buyer").permitAll()
				.antMatchers("/supplierCreation").permitAll()
				.antMatchers("/uploadinvoice").permitAll().antMatchers("/exportdetails").permitAll()
				.antMatchers("/statistics").permitAll().antMatchers("/buyeropenpo").permitAll()
				.antMatchers("/buyerapproval").permitAll().antMatchers("/mastercontrol").permitAll()
				.antMatchers("/createusers").permitAll().antMatchers("/viewerrorlogs").permitAll()
				.antMatchers("/definescreenaccess").permitAll()
				.antMatchers("/rfq").permitAll().antMatchers("/uploads/**").permitAll()
				.antMatchers("/quotation").permitAll()
				.antMatchers("/financeDashboard").permitAll()
				.antMatchers("/shipmentStatus").permitAll()
				.antMatchers("/financeCreditorAnalysis").permitAll()
				.antMatchers("/CashflowForecasting").permitAll()
				.antMatchers("/vendorInvoiceAndDoStatus").permitAll()
				.antMatchers("/register/**").permitAll()
				.antMatchers("/resetPwd/**").permitAll()
				.antMatchers("/invoicedetails").permitAll().antMatchers("/paymentdetails").permitAll()
				.antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability").permitAll()
				.antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**").permitAll().anyRequest().authenticated();

		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}