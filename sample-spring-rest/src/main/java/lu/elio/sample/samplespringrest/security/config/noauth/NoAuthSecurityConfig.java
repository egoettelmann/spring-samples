package lu.elio.sample.samplespringrest.security.config.noauth;

import lu.elio.sample.samplespringrest.security.models.AppRole;
import lu.elio.sample.samplespringrest.security.models.AppUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class NoAuthSecurityConfig {

    /**
     * The map of no-auth users
     */
    private Map<String, Collection<AppRole>> usersMap = new HashMap<>();

    /**
     * Instantiates a new No auth security configuration.
     */
    public NoAuthSecurityConfig() {
        usersMap.put("admin", AppRole.adminRoles());
        usersMap.put("user", AppRole.defaultRoles());
    }

    /**
     * No auth authentication provider.
     * Simply checks that the user exists in the map, and builds a new user out of the associated roles.
     *
     * @return the authentication provider
     */
    @Bean
    public AuthenticationProvider noAuthAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication auth) throws AuthenticationException {
                String username = auth.getName();
                String password = auth.getCredentials().toString();

                // We don't care about the password, we just check that the user exists
                if (usersMap.containsKey(username)) {
                    // Building the user details
                    AppUserDetails user = new AppUserDetails(
                            username,
                            "",
                            usersMap.get(username)
                    );
                    return new UsernamePasswordAuthenticationToken(user, password, usersMap.get(username));
                } else {
                    throw new BadCredentialsException("Bad credentials: user/password");
                }
            }

            @Override
            public boolean supports(Class<?> auth) {
                return auth.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }

}
