package com.maincrafts.contactapp.config;

import com.maincrafts.contactapp.security.JwtAccessDeniedHandler;
import com.maincrafts.contactapp.security.JwtAuthEntryPoint;
import com.maincrafts.contactapp.security.JwtAuthFilter;
import com.maincrafts.contactapp.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // Stateless - no server-side session. Every request must carry its
            // own valid JWT in the Authorization header.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // public: landing page, static assets, public contact form, auth endpoints
                .requestMatchers("/", "/index.html", "/login.html", "/register.html","/contacts.html",
                                  "/css/**", "/js/**", "/submit", "/auth/**").permitAll()
                // any authenticated user (ADMIN or USER) can view the dashboard
                // page and the contacts list - the UI hides admin actions for
                // USER, and the rules below also block them server-side
                .requestMatchers(HttpMethod.GET, "/contacts", "/contacts/**")
                    .hasAnyRole("ADMIN", "USER")
                // only ADMIN can create/edit/delete contacts
                .requestMatchers(HttpMethod.POST, "/contacts").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/contacts/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/contacts/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            // Custom JSON 401 / 403 responses instead of Spring's default HTML pages
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
