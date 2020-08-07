package sh.ajo.linkeye.linkeye.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sh.ajo.linkeye.linkeye.dto.UserDTO;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.model.AuthorityLevel;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.services.AuthorityService;
import sh.ajo.linkeye.linkeye.services.UserService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Arrays;


@Controller
public class UserManagementController {

    private final UserService userService;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    public UserManagementController(UserService userService, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public String getAllUsers(Model model, @RequestParam(required = false, defaultValue = "1") @Min(1) int page, @RequestParam(required = false, defaultValue = "10") @Min(10) @Max(100) int usersShown, Authentication authentication) {

        model.addAttribute("users", userService.findPaginated(page - 1, usersShown));
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("totalUsersAvailable", userService.count());
        model.addAttribute("lastPage", (int) Math.ceil(userService.count() / (double) usersShown));
        model.addAttribute("page", page);
        model.addAttribute("usersShown", usersShown);
        return "users";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/users")
    public String createUser(@Valid UserDTO userDTO, BindingResult bindingResult, @RequestParam(required = false, defaultValue = "false") boolean isAdmin) {

        // Validate the form
        if (bindingResult.hasErrors()) {
            logger.debug("User Creation Failed - submitted form was not valid.");
            return "redirect:/users?error";
        }

        User newUser = new User();

        // Check if the name is new, and if so, is not registered
        if ((!userDTO.getUsername().equals(newUser.getUsername())) && userService.findOneByUsername(userDTO.getUsername()).isPresent()){
            logger.debug("User Modification Failed - duplicate username.");
            return "redirect:/users?error";
        }

        newUser.setUsername(userDTO.getUsername());
        newUser.setEnabled(userDTO.isEnabled());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Authority authority;

        if (userDTO.isAdmin()) {
            authority = authorityService.getByAuthority(AuthorityLevel.ADMIN.getAuthorityLevel());
        } else {
            authority = authorityService.getByAuthority(AuthorityLevel.USER.getAuthorityLevel());
        }

        newUser.setAuthorities(new ArrayList<>(Arrays.asList(authority)));

        try {
            userService.saveAndFlush(newUser);
        } catch (ConstraintViolationException e) {
            return "redirect:/users?error";
        }

        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users/{userId}")
    public String getUser(Model model, @PathVariable Long userId) {

        if (userService.existsById(userId)) {
            User user = userService.getOne(userId);

            // Requester owns
            model.addAttribute("user", user);
            model.addAttribute("userDto", new UserDTO(user));

            return "userdetails";
        }

        return "redirect:/users";

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/users/{userId}")
    public String updateUser(@Valid UserDTO userDTO, BindingResult bindingResult, @PathVariable long userId) {

        if (bindingResult.hasErrors()) {
            logger.debug("User Modification Failed - submitted form was not valid. Binding Result:\n " + bindingResult);
            return "redirect:/users/" + userId + "?error";
        }



        if (userService.existsById(userId)) {

            User newUser = userService.getOneById(userId);

            // Check if the name is new, and if so, is not registered
            if ((!userDTO.getUsername().equals(newUser.getUsername())) && userService.findOneByUsername(userDTO.getUsername()).isPresent()){
                logger.debug("User Modification Failed - duplicate username.");
                return "redirect:/users/" + userId + "?error";
            }

            newUser.setUsername(userDTO.getUsername());
            newUser.setEnabled(userDTO.isEnabled());

            // If the password is blank, do not update.
            if (!userDTO.getPassword().isBlank()) {
                newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            Authority authority;

            if (userDTO.isAdmin()) {
                authority = authorityService.getByAuthority(AuthorityLevel.ADMIN.getAuthorityLevel());
            } else {
                authority = authorityService.getByAuthority(AuthorityLevel.USER.getAuthorityLevel());
            }

            newUser.setAuthorities(new ArrayList<>(Arrays.asList(authority)));

            try {
                userService.saveAndFlush(newUser);
            } catch (Exception e) {
                logger.debug("User Modification Failed:\n " + e);
                return "redirect:/users?error";
            }
        }

        return "redirect:/users/" + userId;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable long userId) {

        userService.deleteById(userId);

        return "redirect:/users";
    }


}
