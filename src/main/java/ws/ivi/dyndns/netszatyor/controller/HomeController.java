package ws.ivi.dyndns.netszatyor.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ws.ivi.dyndns.netszatyor.model.Ckt;
import ws.ivi.dyndns.netszatyor.model.User;
import ws.ivi.dyndns.netszatyor.service.CktService;
import ws.ivi.dyndns.netszatyor.service.CspService;
import ws.ivi.dyndns.netszatyor.service.CsaService;

import java.util.List;

@Controller
public class HomeController {

    private final CktService cktService;
    private final CspService cspService;
    private final CsaService csaService;

    public HomeController(CktService cktService, CspService cspService, CsaService csaService) {
        this.cktService = cktService;
        this.cspService = cspService;
        this.csaService = csaService;
    }

    // Főoldal: 9 véletlenszerű akciós termék
    @GetMapping("/")
    public String index(Model model,
                        @AuthenticationPrincipal User user) {

        List<Ckt> akcios = cktService.getRandom9();

        model.addAttribute("ckt", akcios);
        model.addAttribute("totalPages", 0);  // nincs lapozás
        model.addAttribute("currentPage", 0);
        model.addAttribute("kategoriak", cspService.findAll());
        model.addAttribute("alcsoportok", csaService.findAll());
        model.addAttribute("rendezes", null);
        model.addAttribute("loggedInUser", user);

        return "index";
    }

    // Szűréses lista oldal
    @GetMapping("/termekek")
    public String termekekList(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false) String keres,
                               @RequestParam(required = false) String csp,
                               @RequestParam(required = false) String csa,
                               @RequestParam(required = false) String rendezes,
                               @AuthenticationPrincipal User user) {

        Pageable pageable = PageRequest.of(page, 9);

        Page<Ckt> cktPage = cktService.getFilteredProducts(keres, csp, csa, pageable);

        model.addAttribute("ckt", cktPage.getContent());
        model.addAttribute("totalPages", cktPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("kategoriak", cspService.findAll());
        model.addAttribute("alcsoportok", csaService.findAll());
        model.addAttribute("rendezes", rendezes);
        model.addAttribute("loggedInUser", user);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
