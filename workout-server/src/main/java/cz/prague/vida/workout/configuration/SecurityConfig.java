package cz.prague.vida.workout.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//
//        UserDetails john = User.builder()
//                .username("honza")
//                .password("{noop}cokolada")
//                .roles("ATHLETE", "ADMIN")
//                .build();
//
//        UserDetails dita = User.builder()
//                .username("dita")
//                .password("{noop}cokolada")
//                .roles("ATHLETE")
//                .build();
//        return new InMemoryUserDetailsManager(john, dita);
//    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasRole("ATHLETE")
                                .requestMatchers("/activity/**").hasRole("ATHLETE")
                                .requestMatchers("/manage/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showLogin")
                                .loginProcessingUrl("/login")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()

                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));

        return http.build();
    }
}












