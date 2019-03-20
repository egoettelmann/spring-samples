package lu.elio.sample.spring.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("h2mem")
@Configuration
public class H2Config {
}
