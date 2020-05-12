package com.security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private UserPrincipalDetailsServiceImp userPrincipalDetailsServiceImp;

  @Autowired
  public WebSecurityConfig(UserPrincipalDetailsServiceImp userPrincipalDetailsServiceImp) {
    this.userPrincipalDetailsServiceImp = userPrincipalDetailsServiceImp;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.authenticationProvider(daoAuthenticationProvider());
    //IN MEMORY Authentication
   /* auth
       .inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder().encode("admin123"))
        .authorities("READ_RESOURCE1", "READ_RESOURCE2", "ROLE_ADMIN")
        .and()
        .withUser("user")
        .password(passwordEncoder().encode("user123"))
        .authorities("READ_RESOURCE1", "ROLE_USER");*/
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .antMatchers("/user/*").authenticated()
            .antMatchers("/").authenticated()
            .antMatchers("/admin/*").hasRole("ADMIN")
            .antMatchers("/api/resource1").hasAuthority("READ_RESOURCE1")
            .antMatchers("/api/resource2").hasAuthority("READ_RESOURCE2")
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
            .and()
            .rememberMe().tokenValiditySeconds(1800);
  }

  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsServiceImp);

    return daoAuthenticationProvider;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
