package com.ay.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
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
    @Order(1)
    public class RestWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        /**
         * Note about CSRF configuration:
         * CSRF token should be send in header when calling rest services. To retrieve token in client,
         * server should send token as cookie in each request but this is not a security problem. Read the following:
         * "It is not adequate for CSRF protection to rely on a cookie being sent back to the server
         * because the browser will automatically send it even if you are not in a page loaded
         * from your application (a Cross Site Scripting attack, otherwise known as XSS).
         * The header is not automatically sent, so the origin is under control.
         * You might see that in our application the CSRF token is sent to the client as a cookie,
         * so we will see it being sent back automatically by the browser, but it is the header that provides the protection."
         * from https://spring.io/guides/tutorials/spring-security-and-angular-js/
         */

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // for cors configuration, spring uses a default Bean by the name of corsConfigurationSource
            if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                    profile -> (profile.equalsIgnoreCase("dev")))) {
                http.cors();
                //use default spring login in development mode
                http.formLogin(Customizer.withDefaults());
            } else {
                //In real scenario, form login does not necessary in restful api services
                //I enable it here because i will use this service in place of authenticaton service in k8s cluster
                //default success url is redirected to root in production (necessary in k8s cluster)
                http.formLogin().defaultSuccessUrl("/",true);
            }
            http.authorizeRequests().anyRequest().authenticated().and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .httpBasic(Customizer.withDefaults());

            //if not explicitly defined, spring will return 302 when request comes from browser
            //Nginx ingress auth_url only accept 4xx codes (for unauthorized) and 2xx codes(when authorized)
            //otherwise returns 500. /auth url will be used for Authentication check in nginx ingress
            //Edit: When Http401 returned, nginx can redirect to auth-signin url defined in ingress.
            //      When Http403 returned, nginx shows 403 to the user and does not redirect.
            http.exceptionHandling()
                    .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                            new AntPathRequestMatcher("/auth/**"));
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource()
        {
            if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                    profile -> (profile.equalsIgnoreCase("dev")))) {
                final CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
                configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-XSRF-TOKEN"));
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
