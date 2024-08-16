package com.apponex.bank_system_management.core.security.config;

import com.apponex.bank_system_management.core.security.model.Permissions;
import com.apponex.bank_system_management.core.security.model.role.Role;
import com.apponex.bank_system_management.core.util.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    public final JwtFilter jwtFilter;
    private final LogoutHandler logoutHandler;
    private final RateLimiter rateLimiter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->
                                req.requestMatchers(permitSwagger).permitAll()
                                   .requestMatchers(permitUser).hasRole(Role.USER.name())

                                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.GET,"/admin/**").hasAuthority(Permissions.ADMIN_READ.name())
                                        .requestMatchers(HttpMethod.POST,"/admin/**").hasAuthority(Permissions.ADMIN_CREATE.name())
                                        .requestMatchers(HttpMethod.PUT,"/admin/**").hasAuthority(Permissions.ADMIN_UPDATE.name())
                                        .requestMatchers(HttpMethod.DELETE,"/admin/**").hasAuthority(Permissions.ADMIN_DELETE.name())

                                        .requestMatchers( "/customer/**","/account/**","/transaction/**").hasRole(Role.CUSTOMER.name())
                                        .requestMatchers(HttpMethod.GET,"/customer/**","/account/**","/transaction/**").hasAnyAuthority(Permissions.CUSTOMER_READ.name())
                                        .requestMatchers(HttpMethod.POST,"/customer/**","/account/**","/transaction/**").hasAnyAuthority(Permissions.CUSTOMER_CREATE.name())
                                        .requestMatchers(HttpMethod.PUT,"/customer/**","/account/**","/transaction/**").hasAnyAuthority(Permissions.CUSTOMER_UPDATE.name())
                                        .requestMatchers(HttpMethod.DELETE,"/customer/**","/account/**","/transaction/**").hasAnyAuthority(Permissions.CUSTOMER_DELETE.name())

                                        .requestMatchers("/manager/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                                        .requestMatchers(HttpMethod.GET,"/manager/**").hasAnyAuthority(Permissions.ADMIN_READ.name(), Permissions.MANAGER_READ.name())
                                        .requestMatchers(HttpMethod.POST,"/manager/**").hasAnyAuthority(Permissions.ADMIN_CREATE.name(), Permissions.MANAGER_CREATE.name())
                                        .requestMatchers(HttpMethod.PUT,"/manager/**").hasAnyAuthority(Permissions.ADMIN_UPDATE.name(), Permissions.MANAGER_UPDATE.name())
                                        .requestMatchers(HttpMethod.DELETE,"/manager/**").hasAnyAuthority(Permissions.ADMIN_DELETE.name(), Permissions.MANAGER_DELETE.name())
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(rateLimiter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout->logout
                        .logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                        )
                ;
        return security.build();
    }

    public static String[] permitSwagger = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    public static String[] permitUser = {
            "/user/**",
            "/manager/readCategory/**"
    };

}
