package learn.autoblueprint.Security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {
        http.csrf().disable();
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/security/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/api/auto-blueprint").permitAll()
                .antMatchers(HttpMethod.GET, "/api/auto-blueprint/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auto-blueprint").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/auto-blueprint/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/auto-blueprint/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/car").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/car/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/car/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/modification-plan").authenticated()
                .antMatchers(HttpMethod.GET, "/api/modification-plan/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/modification-plan/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/modification-plan/*").hasAuthority("ADMIN")

                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtRequestFilter(authenticationManager(config), jwtConverter), BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
