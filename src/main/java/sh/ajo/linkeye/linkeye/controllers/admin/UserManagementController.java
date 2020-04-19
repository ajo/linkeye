package sh.ajo.linkeye.linkeye.controllers.admin;

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
import sh.ajo.linkeye.linkeye.LinkeyeApplication;
import sh.ajo.linkeye.linkeye.dto.UserDTO;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.repositories.AuthorityRepository;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;
import sh.ajo.linkeye.linkeye.services.UserService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Controller
public class UserManagementController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementController(UserService userService, UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('USER_ADMIN')")
    @GetMapping("/users")
    public String getAllUsers(Model model, @RequestParam(required = false, defaultValue="1") @Min(1) int page, @RequestParam(required = false, defaultValue="10") @Min(10) @Max(100) int usersShown, Authentication authentication) {

        model.addAttribute("users", userService.findPaginated(page - 1, usersShown));
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("totalUsersAvailable", userRepository.count());
        model.addAttribute("authorityRepository", authorityRepository);
        model.addAttribute("lastPage", (int) Math.ceil(userRepository.count() / (double) usersShown));
        model.addAttribute("page", page);
        model.addAttribute("usersShown", usersShown);
        return "users";
    }

    @PreAuthorize("hasAuthority('USER_ADMIN')")
    @PostMapping("/users")
    public String createUser(@Valid UserDTO userDTO, BindingResult bindingResult, @RequestParam(required = false, defaultValue="false")  boolean isAdmin){

        // Validate the form
        if (bindingResult.hasErrors()) {
            LinkeyeApplication.LOGGER.debug("User Creation Failed - submitted form was not valid. Binding Result:\n " + bindingResult);
            return "redirect:/users?error";
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEnabled(userDTO.isEnabled());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        try {
            userRepository.saveAndFlush(newUser);
        } catch (ConstraintViolationException e){
            return "redirect:/users?error";
        }

        Authority authority = new Authority();
        authority.setUser(userRepository.getOneByUsername(newUser.getUsername()));

        if (userDTO.isAdmin()){
            authority.setAuthorityLevel("USER_ADMIN");
        } else {
            authority.setAuthorityLevel("USER_STANDARD");
        }


        authorityRepository.saveAndFlush(authority);

        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('USER_ADMIN')")
    @GetMapping("/users/{userId}")
    public String getUser(Model model, @PathVariable Long userId) {

        if (userRepository.existsById(userId)) {
            User user = userRepository.getOne(userId);
            Authority authority = authorityRepository.getByUser(user);

            // Requester owns
            model.addAttribute("user", user);
            model.addAttribute("userDto", new UserDTO(user, authority));

            return "userdetails";
        }

        return "redirect:/users";

    }

    @PreAuthorize("hasAuthority('USER_ADMIN')")
    @PostMapping("/users/{userId}")
    public String updateUser(@Valid UserDTO userDTO, BindingResult bindingResult, @PathVariable long userId) {

        // Validate the form
        if (bindingResult.hasErrors()) {
            LinkeyeApplication.LOGGER.debug("User Modification Failed - submitted form was not valid. Binding Result:\n " + bindingResult);
            return "redirect:/users/" + userId + "?error";
        }

        if (userRepository.existsById(userId)){

            User newUser = userRepository.getOne(userId);

            newUser.setUsername(userDTO.getUsername());
            newUser.setEnabled(userDTO.isEnabled());

            // If the password is blank, do not update.
            if(!userDTO.getPassword().isBlank()){
                newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            try{
                userRepository.saveAndFlush(newUser);
            } catch (Exception e){
                LinkeyeApplication.LOGGER.debug("User Modification Failed:\n " + e);
                return "redirect:/users?error";
            }

            Authority authority = authorityRepository.getByUser(newUser);

            if (userDTO.isAdmin()){
                authority.setAuthorityLevel("USER_ADMIN");
            } else {
                authority.setAuthorityLevel("USER_STANDARD");
            }


            authorityRepository.saveAndFlush(authority);
        }

        return "redirect:/users/" + userId;
    }

    @PreAuthorize("hasAuthority('USER_ADMIN')")
    @GetMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable long userId){

        userRepository.deleteById(userId);

        return "redirect:/users";
    }


}
