package com.yahya.growth.stockmanagementsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.yahya.growth.stockmanagementsystem.security.UserRole.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails newStaffUser = User.builder()
                .username("newstaff")
                .password(passwordEncoder.encode("123"))
                .roles(NEW_STAFF.name())
                .build();
        UserDetails staffUser = User.builder()
                .username("staff")
                .password(passwordEncoder.encode("123"))
                .roles(STAFF.name())
                .build();
        UserDetails managerUser = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("123"))
                .roles(MANAGER.name())
                .build();
        UserDetails itUser = User.builder()
                .username("it")
                .password(passwordEncoder.encode("1234"))
                .roles(IT_PERSON.name())
                .build();

        return new InMemoryUserDetailsManager(
                newStaffUser, staffUser, managerUser, itUser
        );
    }
}
