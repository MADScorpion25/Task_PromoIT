package com.dealerapp.config;

import com.dealerapp.config.jwt.AuthEntryPointJwt;
import com.dealerapp.config.jwt.AuthTokenFilter;
import com.dealerapp.models.enums.UserRole;
import com.dealerapp.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
@EnableWebSecurity
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String path;

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt authEntryPointJwt;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + path + "/");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/sign-in", "/auth/register").not().fullyAuthenticated()
                .antMatchers("/orders/edit/:id", "/orders/*", "/configurations/transmission-types", "/configurations/drive-types", "/configurations/body-types", "/configurations/car-classes", "/configurations/edit/:id", "/configurations/*", "/cars/edit/:id", "/cars/*").hasAuthority(UserRole.DEALER.name())
                .antMatchers("/users/*", "/users/*/*", "/auth/*").hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/orders/edit/:id", "/orders/*").hasAuthority(UserRole.CLIENT.name())
                .antMatchers("/img/*", "/auth/*", "/", "/reviews", "/reviews/:id", "/cars/info/:id", "/cars", "/logout").permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/auth/sign-in")
                .and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsServiceImpl userDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
