package org.prd.resourceserver.config;

import org.prd.resourceserver.util.RoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(TokenAuthenticationFilter authenticationFilter,HttpSecurity http) throws Exception {

        return http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement( sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authorizeHttpRequests( authReqConfig -> {
                    authReqConfig.requestMatchers("/user/find/**").permitAll();
                    authReqConfig.requestMatchers("/client/**").permitAll();
                    authReqConfig.requestMatchers("/authorization/**").permitAll();
                    authReqConfig.requestMatchers("/user/all").hasAnyAuthority(
                            RoleEnum.ROLE_ADMIN.name(),RoleEnum.ROLE_RECEPTIONIST.name());
                    authReqConfig.requestMatchers("/patient/all").hasAnyAuthority(
                            RoleEnum.ROLE_ADMIN.name(),RoleEnum.ROLE_RECEPTIONIST.name());
                    authReqConfig.requestMatchers("/doctor/all").hasAnyAuthority(
                            RoleEnum.ROLE_ADMIN.name(),RoleEnum.ROLE_RECEPTIONIST.name());
                    authReqConfig.requestMatchers("/specialty/all").hasAnyAuthority(
                            RoleEnum.ROLE_ADMIN.name(),RoleEnum.ROLE_RECEPTIONIST.name());
                    authReqConfig.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2ResourceServerConfig -> {
                    oauth2ResourceServerConfig.jwt(jwtConfig ->
                            jwtConfig.decoder(JwtDecoders.fromIssuerLocation(issuerUri)));
                })
                .addFilterAfter(authenticationFilter, BearerTokenAuthenticationFilter.class)

                .build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${spring.web.cors.allowed-origins}") String allowedOrigins) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin(allowedOrigins);
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}