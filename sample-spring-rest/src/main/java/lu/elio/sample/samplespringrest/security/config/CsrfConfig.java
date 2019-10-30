package lu.elio.sample.samplespringrest.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class CsrfConfig {

    /**
     * The Http Session CSRF token repository.
     *
     * @return the CSRF token repository
     */
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

    /**
     * The CSRF Response header filter.
     *
     * Adds the token to the response headers.
     *
     * @return the filter
     */
    @Bean
    public Filter csrfResponseHeaderFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String header = request.getHeader("X-CSRF-TOKEN");
                log.debug("Extracted csrf token from request: '{}'", header);
                CsrfToken csrfToken = (CsrfToken) request.getSession().getAttribute("_csrf");
                if (csrfToken != null) {
                    log.debug("Added CSRF token to headers '{}' on session '{}'", csrfToken.getToken(), request.getSession().getId());
                    response.setHeader("X-CSRF-TOKEN", csrfToken.getToken());
                }
                filterChain.doFilter(request, response);
            }

        };
    }

}
