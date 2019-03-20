package lu.elio.sample.samplespringrest.errorhandling.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * The type Rest error.
 */
@JsonSerialize(as = IRestError.class)
public class RestError implements IRestError {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestError.class);

    /**
     * The error code
     */
    private String code;

    /**
     * The translation key
     */
    private String translationKey;

    /**
     * The error params
     */
    private Map<String, Object> params;

    /**
     * The http status
     */
    private HttpStatus httpStatus;

    /**
     * The original exception
     */
    private Throwable exception;

    /**
     * The flag to indicate if the stacktrace should be logged
     */
    private boolean logStacktrace;

    /**
     * Builder rest error builder.
     *
     * @param errorType the error type
     * @return the rest error builder
     */
    public static RestErrorBuilder of(RestErrorType errorType) {
        return new RestErrorBuilder()
                .httpStatus(errorType.getHttpStatus())
                .code(errorType.getCode());
    }

    /**
     * Instantiates a new Rest error.
     *
     * @param code           the code
     * @param translationKey the translation key
     * @param params         the params
     * @param httpStatus     the http status
     * @param exception      the exception
     * @param logStacktrace  the log stacktrace
     */
    RestError(
            final String code,
            final String translationKey,
            final Map<String, Object> params,
            final HttpStatus httpStatus,
            final Throwable exception,
            final boolean logStacktrace
    ) {
        this.code = code;
        this.translationKey = translationKey;
        this.params = params;
        this.httpStatus = httpStatus;
        this.exception = exception;
        this.logStacktrace = logStacktrace;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * Logs the exception (depending on the given error level)
     *
     * @return the rest error
     */
    RestError log() {
        if (exception != null) {
            LOGGER.warn("Intercepted '{}' exception with message: {}", exception.getClass().getCanonicalName(), exception.getMessage());
            if (this.logStacktrace) {
                LOGGER.error("Exception: ", exception);
            }
        } else {
            LOGGER.info("Intercepted error with code '{}', but no exception was specified", this.code);
        }
        return this;
    }

    /**
     * As response entity.
     * Also logs the exception if applicable.
     *
     * @return the response entity
     */
    ResponseEntity<RestError> asResponse() {
        return new ResponseEntity<>(this, httpStatus);
    }

}
