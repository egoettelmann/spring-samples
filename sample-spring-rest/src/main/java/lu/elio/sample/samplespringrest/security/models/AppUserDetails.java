package lu.elio.sample.samplespringrest.security.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type App user details.
 */
@JsonSerialize(as = IAppUserDetails.class)
public class AppUserDetails extends User implements IAppUserDetails {

    /**
     * Instantiates a new App user details.
     *
     * @param username the username
     * @param password the password
     * @param roles    the roles
     */
    public AppUserDetails(String username, String password, Collection<AppRole> roles) {
        super(username, password, roles);
    }
    /**
     * Gets the list of roles as string.
     *
     * @return the list of roles
     */
    public List<String> getRoles() {
        return this.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    /**
     * Current app user details.
     *
     * @return the app user details
     */
    public static AppUserDetails current() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null
        ) {
            throw new AccessDeniedException("No user principal found");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof AppUserDetails)) {
            throw new AccessDeniedException("Principal instance does not match");
        }
        return (AppUserDetails) principal;
    }

    /**
     * Technical app user details.
     * Has all available roles.
     *
     * @return the app user details
     */
    public static AppUserDetails technical() {
        return new AppUserDetails(
                "technical",
                "",
                Arrays.asList(AppRole.values())
        );
    }

}
