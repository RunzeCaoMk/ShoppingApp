package com.cao.shoppingApp.config;

import com.cao.shoppingApp.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtFilter jwtFilter;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/registration",
                        "/cancel/*").permitAll()
                .antMatchers("/user/home",
                        "/user/product/*",
                        "/user/orders",
                        "/user/order/*",
                        "/user/recent3",
                        "/user/frequent3",
                        "/purchase").hasAuthority("User_Permission")
                .antMatchers("/complete/*",
                        "/admin/home",
                        "/admin/updatePrice/*",
                        "/admin/updateStock/*",
                        "/admin/product/*",
                        "/admin/addProduct",
                        "/admin/top3product",
                        "/admin/top3user",
                        "/admin/amount").hasAuthority("Admin_Permission")
                .anyRequest()
                .authenticated();
    }



}
