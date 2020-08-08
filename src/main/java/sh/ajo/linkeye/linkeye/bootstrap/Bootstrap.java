package sh.ajo.linkeye.linkeye.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.model.AuthorityLevel;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.services.AuthorityService;
import sh.ajo.linkeye.linkeye.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public Bootstrap(UserService userService, PasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Check authorities
        if (!authorityService.findByAuthority(AuthorityLevel.USER.getAuthorityLevel()).isPresent()) {
            Authority userLevel = new Authority(AuthorityLevel.USER.getAuthorityLevel());
            authorityService.saveAndFlush(userLevel);
            logger.info("Created authority ".concat(AuthorityLevel.USER.getAuthorityLevel()));
        }

        if (!authorityService.findByAuthority(AuthorityLevel.ADMIN.getAuthorityLevel()).isPresent()) {
            Authority adminLevel = new Authority(AuthorityLevel.ADMIN.getAuthorityLevel());
            authorityService.saveAndFlush(adminLevel);
            logger.info("Created authority ".concat(AuthorityLevel.ADMIN.getAuthorityLevel()));
        }

        // Check accounts
        if (userService.count() == 0) {

            User user = new User();
            user.setUsername("linkeye");
            user.setPassword(passwordEncoder.encode("linkeye"));
            user.setEnabled(true);
            user.setAuthorities(new ArrayList<>(Arrays.asList(authorityService.getByAuthority(AuthorityLevel.ADMIN.getAuthorityLevel()))));


            userService.saveAndFlush(user);
            logger.info("Created default account linkeye/linkeye");

        }

    }
}
