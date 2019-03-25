package lu.elio.sample.samplespringrest.dtos;

import java.util.List;

public interface IAppUserDetails {

    String getUsername();

    List<String> getRoles();

}
