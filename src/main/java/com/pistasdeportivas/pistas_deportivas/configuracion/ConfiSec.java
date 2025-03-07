package com.pistasdeportivas.pistas_deportivas.configuracion;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class ConfiSec {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select username, password, enabled "+
            "from usuario where username = ?")
        .authoritiesByUsernameQuery("select username, tipo as 'authority' "+
            "from usuario where username = ?" );
            
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean 
    public SecurityFilterChain filtro(HttpSecurity httpSec) throws Exception {

    return httpSec.authorizeHttpRequests(
        (request) -> request
            .requestMatchers(
                "/webjars/**", "/img/**", "/login", 
                "/logout", "/acerca", "/denegado", "/favicon.ico",
                "/register")
                .permitAll()
            .requestMatchers(
                "/horario/**", "/instalacion/**", "/instalacion-detalle/**")
                .hasAnyAuthority("ADMIN", "OPERARIO")   
            .requestMatchers( "/reserva/edit/**", "/reserva/del/**")
                .hasAnyAuthority("ADMIN", "OPERARIO")
            .requestMatchers("/reserva/**","/reserva/add")
                .authenticated()
            .requestMatchers(
                "/usuario/**")
                .hasAuthority("ADMIN")
            .requestMatchers(
                "/mis-datos/**", "/mis-datos/*/**")
                .authenticated())
            .exceptionHandling((exception)-> exception.
                accessDeniedPage("/denegado") )
            .formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .permitAll())
            .logout((logout) -> logout
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .permitAll())
                .csrf((csrf) -> csrf.disable())
            .build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
