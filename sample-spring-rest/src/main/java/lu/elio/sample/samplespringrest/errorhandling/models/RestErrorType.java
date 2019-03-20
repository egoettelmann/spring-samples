package lu.elio.sample.samplespringrest.errorhandling.models;

import org.springframework.http.HttpStatus;

public enum RestErrorType {
    /**
     * Access errors
     */
    NOT_LOGGED_IN("A010", HttpStatus.UNAUTHORIZED),
    BAD_CREDENTIALS("A020", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("A030", HttpStatus.FORBIDDEN),
    CSRF_ERROR("A040", HttpStatus.BAD_REQUEST),
    /**
     * Data errors
     */
    BAD_PARAMS("D010", HttpStatus.BAD_REQUEST),
    NOT_FOUND("D020", HttpStatus.NOT_FOUND),
    /**
     * Technical errors
     */
    SYSTEM_ERROR("T010", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_HANDLER("T040", HttpStatus.NOT_FOUND),
    ;

    private String code;

    private HttpStatus httpStatus;

    RestErrorType(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
