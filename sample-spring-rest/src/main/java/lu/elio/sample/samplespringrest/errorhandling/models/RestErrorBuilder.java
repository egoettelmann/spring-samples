package lu.elio.sample.samplespringrest.errorhandling.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class RestErrorBuilder {

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
     * Adds a translation key to the builder.
     *
     * @param translationKey the translation key
     * @return the rest error builder
     */
    public RestErrorBuilder translationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    /**
     * Adds an exception to the builder.
     *
     * @param exception     the exception
     * @param logStacktrace the flag to indicate if the stacktrace should be logged or not
     * @return the rest error builder
     */
    public RestErrorBuilder exception(Throwable exception, boolean logStacktrace) {
        this.exception = exception;
        this.logStacktrace = logStacktrace;
        return this;
    }

    /**
     * Adds a parameter to the params map.
     * Will create the map if it does not exist yet.
     * Will replace any existing value.
     *
     * @param key the key to set
     * @param value the value to set
     * @return the rest error builder
     */
    public RestErrorBuilder param(String key, Object value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
        return this;
    }

    /**
     * Calls {@link #build()}
     * then {@link RestError}.{@link RestError#log()}
     * then {@link RestError}.{@link RestError#asResponse()}.
     *
     * @return {@link RestError}.{@link RestError#asResponse()}
     */
    public ResponseEntity<RestError> handle() {
        return build()
                .log()
                .asResponse();
    }

    /**
     * Adds an error code to the builder.
     *
     * @param code the error code
     * @return the rest error builder
     */
    RestErrorBuilder code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Adds an HttpStatus to the builder.
     *
     * @param httpStatus the http status
     * @return the rest error builder
     */
    RestErrorBuilder httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    /**
     * Builds the rest error.
     *
     * @return the rest error
     */
    private RestError build() {
        return new RestError(
                code,
                translationKey,
                params,
                httpStatus,
                exception,
                logStacktrace
        );
    }

}
