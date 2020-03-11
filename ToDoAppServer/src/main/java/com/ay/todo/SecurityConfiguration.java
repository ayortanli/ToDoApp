package com.ay.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfiguration{

    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private Environment env;

    @Autowired
    private DataSource datasource;

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(datasource);
        return manager;
    }

    @Configuration
    public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // by default uses a Bean by the name of corsConfigurationSource
            if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                    profile -> (profile.equalsIgnoreCase("dev")))) {
                http.cors().and();
            }
            http.authorizeRequests(authorizeRequests ->authorizeRequests.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource()
        {
            if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                    profile -> (profile.equalsIgnoreCase("dev")))) {
                final CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
                configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
                // setAllowCredentials(true) is important, otherwise:
                // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
                configuration.setAllowCredentials(true);
                final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
            }
            return null;
        }
    }

}
