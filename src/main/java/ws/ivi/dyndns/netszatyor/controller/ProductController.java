package ws.ivi.dyndns.netszatyor.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ws.ivi.dyndns.netszatyor.model.Ckt;
import ws.ivi.dyndns.netszatyor.model.User;
import ws.ivi.dyndns.netszatyor.repository.CktRepository;

import java.util.*;

@Controller
@RequestMapping("/termek")
public class ProductController {

    private final CktRepository cktRepository;

    public ProductController(CktRepository cktRepository) {
        this.cktRepository = cktRepository;
    }

    @GetMapping("/{cktkod}")
    public String reszletek(@PathVariable String cktkod, Model model) {
        Optional<Ckt> optionalCkt = cktRepository.findById(cktkod);
        if (optionalCkt.isEmpty()) {
            return "redirect:/";
        }

        Ckt termek = optionalCkt.get();
        model.addAttribute("termek", termek);
        return "detail";
    }

    @PostMapping("/kosarba")
    public String kosarba(@RequestParam String cktkod,
                          @RequestParam(defaultValue = "1") int mennyiseg,
                          HttpSession session,
                          @AuthenticationPrincipal User user) {

        if (user == null) {
            return "redirect:/login";
        }

        if (mennyiseg < 1) {
            mennyiseg = 1;
        }

        Map<String, Integer> kosar = (Map<String, Integer>) session.getAttribute("kosar");
        if (kosar == null) {
            kosar = new HashMap<>();
        }

        kosar.put(cktkod, kosar.getOrDefault(cktkod, 0) + mennyiseg);
        session.setAttribute("kosar", kosar);

        return "redirect:/kosar";
    }
}
