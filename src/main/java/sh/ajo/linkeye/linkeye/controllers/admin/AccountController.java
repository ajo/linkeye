package sh.ajo.linkeye.linkeye.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sh.ajo.linkeye.linkeye.dto.AccountUpdateDTO;
import sh.ajo.linkeye.linkeye.exception.InvalidPasswordException;
import sh.ajo.linkeye.linkeye.exception.MismatchedPasswordException;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.services.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAccount(Model model, Authentication authentication) {

        User user = userService.getOneByUsername(authentication.getName());

        model.addAttribute("accountUpdateDto", new AccountUpdateDTO());
        model.addAttribute("user", user);
        return "account/account";
    }

    @PostMapping
    public String updateAccount(@Valid AccountUpdateDTO accountUpdateDTO, BindingResult bindingResult, Authentication authentication) {

        User user = userService.getOneByUsername(authentication.getName());

        if (bindingResult.hasErrors()) {
            logger.debug("User Creation Failed - submitted form was not valid.");
            return "redirect:/account?error";
        }

        try {
            userService.changePassword(user, accountUpdateDTO);
            return "redirect:/account?success";
        } catch (InvalidPasswordException e) {
            logger.debug("Account update failed - bad password.");
            return "redirect:/account?badPassword";
        } catch (MismatchedPasswordException e){
            logger.debug("Account update failed - password mismatch.");
            return "redirect:/account?mismatchPassword";
        }

    }

}