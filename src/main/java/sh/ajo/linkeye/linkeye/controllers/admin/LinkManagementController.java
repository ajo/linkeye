package sh.ajo.linkeye.linkeye.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import sh.ajo.linkeye.linkeye.dto.LinkDTO;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.services.ClickService;
import sh.ajo.linkeye.linkeye.services.LinkService;
import sh.ajo.linkeye.linkeye.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
public class LinkManagementController {

    private final LinkService linkService;
    private final ClickService clickService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(LinkManagementController.class);

    public LinkManagementController(LinkService linkService, ClickService clickService, UserService userService) {
        this.linkService = linkService;
        this.clickService = clickService;
        this.userService = userService;
    }

    @GetMapping("/links")
    public String links(Model model, @RequestParam(required = false, defaultValue = "1") @Min(1) int page, @RequestParam(required = false, defaultValue = "10") @Min(10) @Max(100) int linksShown, Authentication authentication) {

        User requester = userService.getOneByUsername(authentication.getName());

        model.addAttribute("links", linkService.findPaginated(page - 1, linksShown));
        model.addAttribute("linkDTO", new LinkDTO());
        model.addAttribute("totalLinksAvailable", linkService.countByOwner(requester));
        model.addAttribute("clickRepository", clickService);
        model.addAttribute("lastPage", (int) Math.ceil(linkService.countByOwner(requester) / (double) linksShown));
        model.addAttribute("page", page);
        model.addAttribute("linksShown", linksShown);
        return "links";
    }

    @PostMapping("/links")
    public String createLink(@Valid LinkDTO linkDTO, BindingResult bindingResult, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            logger.debug("Link Creation Failed - submitted form was not valid.");
            return "redirect:/links?error";
        }

        User requester = userService.getOneByUsername(authentication.getName());
        linkDTO.setOwner(requester);
        linkService.createLink(linkDTO);

        return "redirect:/links";
    }

    @GetMapping("/links/{linkId}")
    public String links(Model model, @RequestParam(required = false, defaultValue = "1") @Min(1) int page, @RequestParam(required = false, defaultValue = "10") @Min(10) @Max(100) int linksShown, Authentication authentication, @PathVariable Long linkId) {

        User requester = userService.getOneByUsername(authentication.getName());
        Link link = linkService.getLinkById(linkId);

        if (linkService.existsById(linkId) && (link.getOwner() == requester)){
            model.addAttribute("link", link);
            model.addAttribute("linkDTO", new LinkDTO(link));
            model.addAttribute("clicks", clickService.findPaginated(page - 1, linksShown, link));
            model.addAttribute("totalClicksAvailable", clickService.countByLink(link));
            model.addAttribute("clickRepository", clickService);
            model.addAttribute("lastPage", (int) Math.ceil(clickService.countByLink(link) / (double) linksShown));
            model.addAttribute("page", page);
            model.addAttribute("linksShown", linksShown);
            return "linkdetails";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/links/{linkId}")
    public String updateLink(@Valid LinkDTO linkDTO, BindingResult bindingResult, Authentication authentication, @PathVariable long linkId) {

        if (bindingResult.hasErrors()) {
            return "redirect:/links/" + linkId + "?error";
        }

        User requester = userService.getOneByUsername(authentication.getName());
        Link link = linkService.getLinkById(linkId);

        if (linkService.existsById(linkId) && (link.getOwner() == requester)){
            linkService.updateLink(link, linkDTO);
            return "redirect:/links";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/links/{linkid}/delete")
    public String deleteLink(Authentication authentication, @PathVariable long linkId) {

        User requester = userService.getOneByUsername(authentication.getName());
        Link link = linkService.getLinkById(linkId);

        if (linkService.existsById(linkId) && (link.getOwner() == requester)){
            linkService.deleteById(linkId);
            return "redirect:/links";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}