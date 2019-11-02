package lu.elio.sample.samplespringrest.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CsrfConfig {

    /**
     * The logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CsrfConfig.class);

    /**
     * The CSRF Token Repository
     */
    private CsrfTokenRepository csrfTokenRepository;

    /**
     * The CSRF Response Header Filter
     */
    private Filter csrfResponseHeaderFilter;

    /**
     * Instantiates the CSRF config
     */
    public CsrfConfig() {
        this.csrfTokenRepository = buildCsrfTokenRepository();
        this.csrfResponseHeaderFilter = buildCsrfResponseHeaderFilter();
    }

    /**
     * Gets the CSRF token repository.
     *
     * @return the CSRF token repository
     */
    public CsrfTokenRepository csrfTokenRepository() {
        return csrfTokenRepository;
    }

    /**
     * Gets the CSRF response header filter.
     *
     * @return the CSRF response header filter
     */
    public Filter csrfResponseHeaderFilter() {
        return csrfResponseHeaderFilter;
    }

    /**
     * The Http Session CSRF token repository.
     *
     * @return the CSRF token repository
     */
    private CsrfTokenRepository buildCsrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

    /**
     * The CSRF Response header filter.
     * <p>
     * Adds the token to the response headers.
     *
     * @return the filter
     */
    private Filter buildCsrfResponseHeaderFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String header = request.getHeader("X-CSRF-TOKEN");
                LOGGER.debug("Extracted csrf token from request: '{}'", header);
                CsrfToken csrfToken = (CsrfToken) request.getSession().getAttribute("_csrf");
                if (csrfToken != null) {
                    LOGGER.debug("Added CSRF token to headers '{}' on session '{}'", csrfToken.getToken(), request.getSession().getId());
                    response.setHeader("X-CSRF-TOKEN", csrfToken.getToken());
                }
                filterChain.doFilter(request, response);
            }

        };
    }

}
