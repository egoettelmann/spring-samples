package lu.elio.sample.samplespringrest.security.models;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

public enum AppRole implements GrantedAuthority {
    SAMPLE_ACTION_1("Sample Action 1"),
    SAMPLE_ACTION_2("Sample Action 2"),
    SAMPLE_ACTION_3("Sample Action 3");

    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * The description of the role
     */
    private String description;

    /**
     * Instantiates a new AppRole.
     *
     * @param description the description
     */
    AppRole(String description) {
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return ROLE_PREFIX + name();
    }

    @Override
    public String toString() {
        return getAuthority();
    }

    /**
     * Checks if a role is allowed for a user.
     *
     * @param userDetails the user details
     * @return true if the user has the role, false otherwise
     */
    public boolean isAllowed(IAppUserDetails userDetails) {
        return userDetails.getRoles().contains(getAuthority());
    }

    /**
     * All roles for Admin users.
     *
     * @return the admin user's roles
     */
    public static List<AppRole> adminRoles() {
        return Arrays.asList(
                SAMPLE_ACTION_1,
                SAMPLE_ACTION_2,
                SAMPLE_ACTION_3
        );
    }

    /**
     * All roles for Default users.
     *
     * @return the default user's roles
     */
    public static List<AppRole> defaultRoles() {
        return Arrays.asList(
                SAMPLE_ACTION_1,
                SAMPLE_ACTION_2
        );
    }

}
