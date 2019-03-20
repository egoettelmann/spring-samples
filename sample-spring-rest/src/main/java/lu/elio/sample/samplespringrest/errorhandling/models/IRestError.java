package lu.elio.sample.samplespringrest.errorhandling.models;

import java.util.Map;

public interface IRestError {

    /**
     * Gets the internal code of the error.
     *
     * @return the error code
     */
    String getCode();

    /**
     * Gets the translation key of the error.
     *
     * @return the translation key
     */
    String getTranslationKey();

    /**
     * Gets the params of the error.
     *
     * @return the params
     */
    Map<String, Object> getParams();

}
