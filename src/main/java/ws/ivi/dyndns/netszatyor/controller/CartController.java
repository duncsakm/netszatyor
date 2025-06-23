package ws.ivi.dyndns.netszatyor.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ws.ivi.dyndns.netszatyor.model.Ckt;
import ws.ivi.dyndns.netszatyor.model.User;
import ws.ivi.dyndns.netszatyor.model.CartItem; // <-- EZ HIÁNYZOTT
import ws.ivi.dyndns.netszatyor.repository.CktRepository;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/kosar")
public class CartController {

    private final CktRepository cktRepository;

    public CartController(CktRepository cktRepository) {
        this.cktRepository = cktRepository;
    }

    @GetMapping
    public String kosarView(Model model, HttpSession session, @AuthenticationPrincipal User user) {
        if (user == null) return "redirect:/login";

        Map<String, Integer> kosar = (Map<String, Integer>) session.getAttribute("kosar");
        if (kosar == null) kosar = new HashMap<>();

        List<CartItem> termekek = new ArrayList<>();
        BigDecimal osszeg = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : kosar.entrySet()) {
            Optional<Ckt> optionalCkt = cktRepository.findById(entry.getKey());
            if (optionalCkt.isPresent()) {
                Ckt termek = optionalCkt.get();
                int mennyiseg = entry.getValue();
                BigDecimal ossz = termek.getCktakc().multiply(BigDecimal.valueOf(mennyiseg));
                osszeg = osszeg.add(ossz);
                termekek.add(new CartItem(termek, mennyiseg));
            }
        }

        model.addAttribute("termekek", termekek);
        model.addAttribute("osszeg", osszeg);
        return "cart";
    }

    @PostMapping("/modosit")
    public String modosit(@RequestParam String cktkod,
                          @RequestParam int mennyiseg,
                          HttpSession session,
                          @AuthenticationPrincipal User user) {

        if (user == null) return "redirect:/login";
        if (mennyiseg < 1) mennyiseg = 1;

        Map<String, Integer> kosar = (Map<String, Integer>) session.getAttribute("kosar");
        if (kosar != null && kosar.containsKey(cktkod)) {
            kosar.put(cktkod, mennyiseg);
            session.setAttribute("kosar", kosar);
        }

        return "redirect:/kosar";
    }

    @PostMapping("/torol")
    public String torol(@RequestParam String cktkod,
                        HttpSession session,
                        @AuthenticationPrincipal User user) {

        if (user == null) return "redirect:/login";

        Map<String, Integer> kosar = (Map<String, Integer>) session.getAttribute("kosar");
        if (kosar != null) {
            kosar.remove(cktkod);
            session.setAttribute("kosar", kosar);
        }

        return "redirect:/kosar";
    }

    @PostMapping("/urites")
    public String urites(HttpSession session, @AuthenticationPrincipal User user) {
        if (user == null) return "redirect:/login";

        session.removeAttribute("kosar");
        return "redirect:/kosar";
    }
}
