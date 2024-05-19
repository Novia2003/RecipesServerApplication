package ru.vsu.cs.tp.recipesServerApplication.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vsu.cs.tp.recipesServerApplication.configuration.rest.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers("/api/v1/admin/**")
                .hasAuthority("ADMINISTRATOR")
                .requestMatchers("/api/v1/recipes/**")
                .permitAll()
                .requestMatchers("/api/v1/diets/**")
                .permitAll()
                .requestMatchers("/api/v1/favouriteRecipes/**")
                .hasAuthority("USER")
                .requestMatchers("/api/v1/userRecipes/**")
                .hasAuthority("USER")
                .requestMatchers("/api/v1/ingredients/**")
                .permitAll()
                .requestMatchers("/api/v1/mealTypes/**")
                .permitAll()
                .requestMatchers("/api/v1/recipes/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
