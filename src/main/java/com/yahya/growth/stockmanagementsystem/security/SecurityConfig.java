package com.yahya.growth.stockmanagementsystem.security;

import com.yahya.growth.stockmanagementsystem.model.security.CustomAuthenticationProvider;
import com.yahya.growth.stockmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static com.yahya.growth.stockmanagementsystem.security.Permission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserService userService, CustomAuthenticationProvider customAuthenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll() // TODO Remove this line
                .antMatchers("/", "/css/*", "/js/*", "/images/**").permitAll()

                .antMatchers("/brand/**", "/brand/").hasAuthority(BRAND_READ.getPermission())
                .antMatchers("/category/**", "/category/").hasAuthority(CATEGORY_READ.getPermission())
                .antMatchers("/credit/**", "/credit/").hasAuthority(CREDIT_SETTLEMENT_READ.getPermission())
                .antMatchers("/company/**", "/company/").hasAuthority(COMPANY_READ.getPermission())
                .antMatchers("/customer/**", "/customer/").hasAuthority(CUSTOMER_READ.getPermission())
                .antMatchers("/group/**", "/group/").hasAuthority(ROLE_READ.getPermission())
                .antMatchers("/transaction/**", "/transaction/").hasAuthority(TRANSACTION_READ.getPermission())
                .antMatchers("/users/**", "/users/").hasAuthority(USER_READ.getPermission())
                .antMatchers("/reports/**", "/reports/").hasAuthority(VIEW_STORE_REPORT.getPermission())

                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(customAuthenticationProvider)
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/dashboard")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(((int) TimeUnit.DAYS.toSeconds(21)))// defaults to 2 weeks
                    .key("BEAUTIFUL")
                    .userDetailsService(userService)
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

}
