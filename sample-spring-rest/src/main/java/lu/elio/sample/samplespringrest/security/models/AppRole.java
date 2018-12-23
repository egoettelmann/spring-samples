package lu.elio.sample.samplespringrest.security.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public enum AppRole {
    SAMPLE_ACTION_1,
    SAMPLE_ACTION_2,
    SAMPLE_ACTION_3;

    public static final String ROLE_PREFIX = "ROLE_";

    public GrantedAuthority buildAuthority() {
        return new SimpleGrantedAuthority(this.getRole());
    }

    public String getRole() {
        return ROLE_PREFIX + this.name();
    }

    public boolean isAllowed(User user) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(this.getRole()));
    }

    public static AppRole resolveFromAuthority(GrantedAuthority authority) {
        for (AppRole appRole : AppRole.values()) {
            if (appRole.getRole().equals(authority.getAuthority())) {
                return appRole;
            }
        }
        return null;
    }

}
