package lu.elio.sample.samplespringrest.security.controllers;

import lu.elio.sample.samplespringrest.security.models.AppUserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping
    public AppUserDetails getConnectedUser() {
        return AppUserDetails.current();
    }

}
