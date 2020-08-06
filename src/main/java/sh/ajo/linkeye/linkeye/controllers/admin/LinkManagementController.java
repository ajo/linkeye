package sh.ajo.linkeye.linkeye.controllers.admin;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sh.ajo.linkeye.linkeye.LinkeyeApplication;
import sh.ajo.linkeye.linkeye.dto.LinkDTO;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.repositories.ClickRepository;
import sh.ajo.linkeye.linkeye.repositories.LinkRepository;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;
import sh.ajo.linkeye.linkeye.services.mysql.ClickServiceImpl;
import sh.ajo.linkeye.linkeye.services.mysql.LinkServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Controller
public class LinkManagementController {

    private final LinkServiceImpl linkServiceImpl;
    private final ClickServiceImpl clickServiceImpl;
    private final LinkRepository linkRepository;
    private final ClickRepository clickRepository;
    private final UserRepository userRepository;

    public LinkManagementController(LinkServiceImpl linkServiceImpl, ClickServiceImpl clickServiceImpl, LinkRepository linkRepository, ClickRepository clickRepository, UserRepository userRepository) {
        this.linkServiceImpl = linkServiceImpl;
        this.clickServiceImpl = clickServiceImpl;
        this.linkRepository = linkRepository;
        this.clickRepository = clickRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/links")
    public String links(Model model, @RequestParam(required = false, defaultValue="1") @Min(1) int page, @RequestParam(required = false, defaultValue="10") @Min(10) @Max(100) int linksShown, Authentication authentication) {

        User requester = userRepository.getOneByUsername(authentication.getName());

        model.addAttribute("links", linkServiceImpl.findPaginated(page - 1, linksShown));
        model.addAttribute("linkDTO", new LinkDTO());
        model.addAttribute("totalLinksAvailable", linkRepository.countByOwner(requester));
        model.addAttribute("clickRepository", clickRepository);
        model.addAttribute("lastPage", (int) Math.ceil(linkRepository.countByOwner(requester) / (double) linksShown));
        model.addAttribute("page", page);
        model.addAttribute("linksShown", linksShown);
        return "links";
    }

    @PostMapping("/links")
    public String createLink(@Valid LinkDTO linkDTO, BindingResult bindingResult, Authentication authentication){

        // Validate the form
        if (bindingResult.hasErrors()) {
            LinkeyeApplication.LOGGER.debug("Link Creation Failed - submitted form was not valid. Binding Result:\n " + bindingResult);
            return "redirect:/links?error";
        }

        User requester = userRepository.getOneByUsername(authentication.getName());

        Link newLink = new Link();
        newLink.setName(linkDTO.getName());
        newLink.setDestination(linkDTO.getDestination());
        newLink.setSourcePath(linkDTO.getPath());
        newLink.setOwner(requester);
        newLink.setCreated(new Date(System.currentTimeMillis()));
        newLink.setActive(linkDTO.isActive());

        try {
            linkRepository.save(newLink);
        } catch (Exception e){
            LinkeyeApplication.LOGGER.debug("Link creation failed:\n " + e);
            return "redirect:/links?error";
        }

        return "redirect:/links";
    }

    @GetMapping("/links/{linkId}")
    public String links(Model model, @RequestParam(required = false, defaultValue="1") @Min(1) int page, @RequestParam(required = false, defaultValue="10") @Min(10) @Max(100) int linksShown, Authentication authentication, @PathVariable Long linkId) {

        User requester = userRepository.getOneByUsername(authentication.getName());
        Link link = linkRepository.getLinkById(linkId);

        if (link.getOwner() == requester){

            // Requester owns
            model.addAttribute("link", link);
            model.addAttribute("linkDTO", new LinkDTO(link));
            model.addAttribute("clicks", clickServiceImpl.findPaginated(page - 1, linksShown, link));
            model.addAttribute("totalClicksAvailable", clickRepository.countByLink(link));
            model.addAttribute("clickRepository", clickRepository);
            model.addAttribute("lastPage", (int) Math.ceil(clickRepository.countByLink(link) / (double) linksShown));
            model.addAttribute("page", page);
            model.addAttribute("linksShown", linksShown);

            return "linkdetails";
        }

        // Requester does not own
        return "redirect:/links";

    }

    @PostMapping("/links/{linkId}")
    public String createLink(@Valid LinkDTO linkDTO, BindingResult bindingResult, Authentication authentication, @PathVariable long linkId) {

        // Validate the form
        if (bindingResult.hasErrors()) {
            return "redirect:/links/" + linkId + "?error";
        }

        User requester = userRepository.getOneByUsername(authentication.getName());
        Link link = linkRepository.getLinkById(linkId);

        // Validate authorization
        if (link.getOwner() == requester){

            link.setName(linkDTO.getName());
            link.setDestination(linkDTO.getDestination());
            link.setSourcePath(linkDTO.getPath());
            link.setOwner(requester);
            link.setActive(linkDTO.isActive());

        try {
            linkRepository.save(link);
        } catch (Exception e){
            if (e instanceof ConstraintViolationException) {
                // Here you're sure you have a ConstraintViolationException, you can handle it
                LinkeyeApplication.LOGGER.debug("User Modification Failed:\n " + e);
            }
            return "redirect:/links/" + linkId + "?error";
        }
        }

        return "redirect:/links/" + linkId;
    }

    @GetMapping("/links/{linkid}/delete")
    public String deleteLink(Authentication authentication, @PathVariable long linkid){

        User requester = userRepository.getOneByUsername(authentication.getName());
        Link link = linkRepository.getLinkById(linkid);

        if (link.getOwner() == requester) {
            linkRepository.deleteById(linkid);
        }

        return "redirect:/links";
    }


}
