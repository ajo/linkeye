package sh.ajo.linkeye.linkeye.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sh.ajo.linkeye.linkeye.dto.UserDTO;
import sh.ajo.linkeye.linkeye.exception.DuplicateUsernameException;
import sh.ajo.linkeye.linkeye.exception.InvalidPasswordException;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Controller
@RequestMapping("/users")
public class UserManagementController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public String getUser(Model model, @PathVariable Long userId) {

        if (userService.existsById(userId)) {

            User user = userService.getOneById(userId);

            // Requester owns
            model.addAttribute("user", user);
            model.addAttribute("userDto", new UserDTO(user));

            return "admin/userdetails";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public String getAllUsers(Model model, @RequestParam(required = false, defaultValue = "1") @Min(1) int page, @RequestParam(required = false, defaultValue = "10") @Min(10) @Max(100) int usersShown, Authentication authentication) {

        model.addAttribute("users", userService.findPaginated(page - 1, usersShown));
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("totalUsersAvailable", userService.count());
        model.addAttribute("lastPage", (int) Math.ceil(userService.count() / (double) usersShown));
        model.addAttribute("page", page);
        model.addAttribute("usersShown", usersShown);
        return "admin/users";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public String createUser(@Valid UserDTO userDTO, BindingResult bindingResult, @RequestParam(required = false, defaultValue = "false") boolean isAdmin) {

        if (bindingResult.hasErrors()) {
            logger.debug("User Creation Failed - submitted form was not valid.");
            return "redirect:/users?error";
        }

        try {
            userService.createUser(userDTO);
            return "redirect:/users?success";
        } catch (DuplicateUsernameException e) {
            logger.debug("User creation failed - duplicate username.");
            return "redirect:/users?badUsername";
        } catch (InvalidPasswordException e) {
            logger.debug("User creation failed - bad password.");
            return "redirect:/users?badPassword";
        }

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/{userId}")
    public String updateUser(@Valid UserDTO userDTO, BindingResult bindingResult, @PathVariable long userId) {

        if (bindingResult.hasErrors()) {
            logger.debug("User Modification Failed - submitted form was not valid.");
            return "redirect:/users/" + userId + "?error";
        }

        if (userService.existsById(userId)) {

            User targetUser = userService.getOneById(userId);

            try {
                userService.updateUser(targetUser, userDTO);
                return "redirect:/users?success";
            } catch (DuplicateUsernameException e) {
                logger.debug("User modification failed - duplicate username.");
                return "redirect:/users?badUsername";
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{userId}/delete")
    public String deleteUser(@PathVariable long userId) {

        if (userService.existsById(userId)) {
            userService.deleteById(userId);
            return "redirect:/users?success";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}