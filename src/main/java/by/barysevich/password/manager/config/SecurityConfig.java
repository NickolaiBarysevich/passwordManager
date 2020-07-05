package by.barysevich.password.manager.config;

import by.barysevich.password.manager.security.AuthenticationHandler;
import by.barysevich.password.manager.security.ForbiddenHandler;
import by.barysevich.password.manager.service.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTH_PATH = "/api/v1/auth/*";

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final ObjectMapper objectMapper;

    /**
     * Constructs java config.
     *
     * @param userDetailsService    to get user from db
     * @param bcryptPasswordEncoder for user password
     * @param objectMapper          for mapping errors
     */
    public SecurityConfig(UserService userDetailsService, BCryptPasswordEncoder bcryptPasswordEncoder,
                          ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//.requiresChannel().anyRequest().requiresSecure().and()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/login*").not().authenticated()
                .antMatchers("/auth/login*").not().authenticated()
                .antMatchers("/registration*").not().authenticated()
                .antMatchers("/errorPage*").permitAll()
                .antMatchers(AUTH_PATH).not().authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error=true")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);
    }

    /**
     * Configure CORS to accept connections from any host.
     *
     * @return cors configuration bean
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new ForbiddenHandler(objectMapper);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationHandler(objectMapper);
    }


}
