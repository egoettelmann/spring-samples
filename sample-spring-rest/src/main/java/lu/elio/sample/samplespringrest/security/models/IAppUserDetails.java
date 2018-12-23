package lu.elio.sample.samplespringrest.security.models;

import java.util.List;

public interface IAppUserDetails {

    String getUsername();

    List<String> getRoles();

}
