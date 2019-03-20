package lu.elio.sample.samplespringrest.errorhandling;

import lu.elio.sample.samplespringrest.errorhandling.models.RestError;
import lu.elio.sample.samplespringrest.errorhandling.models.RestErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    /*********************************************************************************
     * Security exceptions
     *********************************************************************************/
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<RestError> authenticationHandler(AuthenticationException exception) {
        return RestError.of(RestErrorType.NOT_LOGGED_IN)
                .translationKey("not_logged_in")
                .exception(exception, false)
                .handle();
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<RestError> badCredentialsHandler(BadCredentialsException exception) {
        return RestError.of(RestErrorType.BAD_CREDENTIALS)
                .translationKey("bad_credentials")
                .exception(exception, false)
                .handle();
    }

    @ExceptionHandler({MissingCsrfTokenException.class, InvalidCsrfTokenException.class})
    public ResponseEntity<RestError> invalidCsrfTokenHandler(CsrfException exception) {
        return RestError.of(RestErrorType.CSRF_ERROR)
                .translationKey("invalid_csrf_token")
                .exception(exception, false)
                .handle();
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<RestError> accessDeniedHandler(AccessDeniedException exception) {
        return RestError.of(RestErrorType.ACCESS_DENIED)
                .translationKey("access_denied")
                .exception(exception, false)
                .handle();
    }

    /*********************************************************************************
     * Technical exceptions
     *********************************************************************************/
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<RestError> fallbackHandler(Throwable exception) {
        return RestError.of(RestErrorType.SYSTEM_ERROR)
                .translationKey("system_error")
                .exception(exception, true)
                .handle();
    }

}
