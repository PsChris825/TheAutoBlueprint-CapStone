package learn.autoblueprint.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

                .antMatchers(HttpMethod.POST, "/api/car").authenticated()
                .antMatchers(HttpMethod.GET, "/api/car/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car/make/{make}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car/model/{model}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car/year/{year}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car/makes").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car/models").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car/years/{make}/{model}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/car//make/{make}/model/{model}/year/{year}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/car/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/car/{id}").hasAuthority("ADMIN")


                .antMatchers(HttpMethod.POST, "/api/part-category").authenticated()
                .antMatchers(HttpMethod.GET, "/api/part-category").permitAll()
                .antMatchers(HttpMethod.GET, "/api/part-category/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/part-category/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/part-category/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/modification-plan").authenticated()
                .antMatchers(HttpMethod.GET, "/api/modification-plan").permitAll()
                .antMatchers(HttpMethod.GET, "/api/modification-plan/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/modification-plan/appUser/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/modification-plan/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/modification-plan/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/plan-part").authenticated()
                .antMatchers(HttpMethod.GET, "/api/plan-part/plan/{planId}*").authenticated()
                .antMatchers(HttpMethod.GET, "/api/plan-part/*").authenticated()
                .antMatchers(HttpMethod.POST, "/api/plan-part").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/plan-part/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/plan-part/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/parts").permitAll()
                .antMatchers(HttpMethod.GET, "/api/parts/category/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/parts/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/parts").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/parts/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/parts/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/comments").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/post/*").authenticated()
                .antMatchers(HttpMethod.GET, "/api/comments/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/comments").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/comments/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/comments/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/{postId}/comments*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/user/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/posts").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/posts/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/posts/*").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/external-car/**").permitAll()

                .antMatchers(HttpMethod.POST, "/security/refresh").authenticated()

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