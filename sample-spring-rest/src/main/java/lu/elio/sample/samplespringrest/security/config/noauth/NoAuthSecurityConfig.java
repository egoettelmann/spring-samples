package lu.elio.sample.samplespringrest.security.config.noauth;

import lu.elio.sample.samplespringrest.security.models.AppRole;
import lu.elio.sample.samplespringrest.security.models.AppUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class NoAuthSecurityConfig {

    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public UserDetailsService noAuthUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        inMemoryUserDetailsManager = new InMemoryUserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserDetails userDetails = super.loadUserByUsername(username);
                return new AppUserDetails(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities().stream()
                                .map(AppRole::resolveFromAuthority)
                                .collect(Collectors.toList())
                );

            }
        };
        createUsers();
        return inMemoryUserDetailsManager;
    }

    private void createUsers() {
        inMemoryUserDetailsManager.createUser(
                new AppUserDetails(
                        "user",
                        passwordEncoder.encode("password"),
                        Arrays.asList(
                                AppRole.SAMPLE_ACTION_1,
                                AppRole.SAMPLE_ACTION_2
                        )
                )
        );
        inMemoryUserDetailsManager.createUser(
                new AppUserDetails(
                        "admin",
                        passwordEncoder.encode("password"),
                        Arrays.asList(AppRole.values())
                )
        );
    }

}
