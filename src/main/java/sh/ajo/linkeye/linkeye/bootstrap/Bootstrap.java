package sh.ajo.linkeye.linkeye.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sh.ajo.linkeye.linkeye.LinkeyeApplication;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.services.AuthorityService;
import sh.ajo.linkeye.linkeye.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {

    static final Logger LOGGER = LoggerFactory.getLogger(LinkeyeApplication.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public Bootstrap(UserService userService, PasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }


    // Create default accounts if this is a fresh install / all accounts are deleted.
    @Override
    public void run(String... args) throws Exception {

        if (userService.count() == 0) {

            Authority userLevel = new Authority("ROLE_USER");
            authorityService.saveAndFlush(userLevel);

            Authority adminLevel = new Authority("ROLE_ADMIN");
            authorityService.saveAndFlush(adminLevel);

            User user = new User();
            user.setUsername("linkeye");
            user.setPassword(passwordEncoder.encode("linkeye"));
            user.setEnabled(true);
            user.setAuthorities(new ArrayList<>(Arrays.asList(adminLevel)));


            userService.saveAndFlush(user);
            LOGGER.info("Created default account linkeye/linkeye");

        }

    }
}
