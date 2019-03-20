package lu.elio.sample.samplespringrest.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * The Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestSecurityConfig.class);

    /**
     * The authentication provider
     */
    private AuthenticationProvider authenticationProvider;

    /**
     * The error handler
     */
    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    public RestSecurityConfig(
            final AuthenticationProvider authenticationProvider,
            @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver exceptionResolver
    ) {
        super();
        this.authenticationProvider = authenticationProvider;
        this.exceptionResolver = exceptionResolver;
    }

    /**
     * Security configuration setup.
     *
     * @param http the http security configuration object
     * @throws Exception configuration exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Access control
        http.authorizeRequests()
                .anyRequest().authenticated();

        // Custom exception handling for returning RestError
        http.exceptionHandling()
                .authenticationEntryPoint(loginAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());

        // Login
        http.formLogin()
                .loginProcessingUrl("/api/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(loginFailureHandler())
                .successHandler(loginSuccessHandler())
                .permitAll();

        // Logout
        http.logout()
                .logoutUrl("/api/logout");

        // Enabling CORS
        http.cors();

        // Disabling CSRF
        http.csrf().disable();
    }

    /**
     * The access denied handler.
     * It resolves the access denied exception to a REST response
     *
     * @return the access denied handler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            LOGGER.warn("Access denied, forwarding exception");
            exceptionResolver.resolveException(request, response, null, accessDeniedException);
        };
    }

    /**
     * Triggered when the user is not authenticated but tries to access a secured resource.
     * It resolves the authentication exception to a REST response
     *
     * @return the authentication entry point
     */
    @Bean
    public AuthenticationEntryPoint loginAuthenticationEntryPoint() {
        return (request, response, exception) -> {
            LOGGER.warn("User not authenticated, resolving authentication exception");
            exceptionResolver.resolveException(request, response, null, exception);
        };
    }

    /**
     * The login failure handler.
     * Triggered when the authentication failed (like a bad credentials exception).
     *
     * @return the authentication failure handler
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            LOGGER.warn("Authentication failure '{}'", exception.getMessage());
            exceptionResolver.resolveException(request, response, null, exception);
        };
    }

    /**
     * The login success handler.
     *
     * @return the authentication success handler
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                LOGGER.info("Login successful");
                clearAuthenticationAttributes(request);
            }
        };
    }

    /**
     * Configures the authentication manager.
     * Defines the authentication provider to use.
     *
     * @param auth the authentication manager builder
     * @throws Exception configuration exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
        auth.eraseCredentials(true);
        super.configure(auth);
    }

}
