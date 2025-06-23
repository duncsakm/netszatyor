package ws.ivi.dyndns.netszatyor.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ws.ivi.dyndns.netszatyor.model.*;
import ws.ivi.dyndns.netszatyor.repository.CktRepository;
import ws.ivi.dyndns.netszatyor.repository.RendelesRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CktRepository cktRepository;
    private final RendelesRepository rendelesRepository;

    public CheckoutController(CktRepository cktRepository, RendelesRepository rendelesRepository) {
        this.cktRepository = cktRepository;
        this.rendelesRepository = rendelesRepository;
    }

    @GetMapping
    public String showCheckout(Model model, HttpSession session, @AuthenticationPrincipal User user) {
        if (user == null) return "redirect:/login";

        Map<String, Integer> kosar = (Map<String, Integer>) session.getAttribute("kosar");
        if (kosar == null || kosar.isEmpty()) {
            return "redirect:/kosar";
        }

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
        model.addAttribute("user", user);
        model.addAttribute("fizetesiModok", FizetesiMod.values());
        return "checkout";
    }

    @PostMapping
    public String processCheckout(@RequestParam("fizetes") FizetesiMod fizetes,
                                  HttpSession session,
                                  @AuthenticationPrincipal User user,
                                  Model model) {

        if (user == null) return "redirect:/login";

        Map<String, Integer> kosar = (Map<String, Integer>) session.getAttribute("kosar");
        if (kosar == null || kosar.isEmpty()) {
            return "redirect:/kosar";
        }

        BigDecimal osszeg = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : kosar.entrySet()) {
            Optional<Ckt> optionalCkt = cktRepository.findById(entry.getKey());
            if (optionalCkt.isPresent()) {
                Ckt termek = optionalCkt.get();
                int mennyiseg = entry.getValue();
                osszeg = osszeg.add(termek.getCktakc().multiply(BigDecimal.valueOf(mennyiseg)));
            }
        }

        Rendeles rendeles = new Rendeles();
        rendeles.setUser(user);
        rendeles.setDatum(LocalDateTime.now());
        rendeles.setFizetesiMod(fizetes);
        rendeles.setVegosszeg(osszeg);

        // Felhasználói adatok betöltése (ha van UserData)
        UserData adat = user.getUserData();
        if (adat != null) {
            rendeles.setNev(adat.getNev());
            rendeles.setIrsz(adat.getIranyitoszam());
            rendeles.setTelepules(adat.getTelepules());
            rendeles.setUtca(adat.getUtca());
            rendeles.setHazszam(adat.getHazszam());
            rendeles.setEgyeb(adat.getCimegyeb());
            rendeles.setTelefon(adat.getTelefon());
            rendeles.setCeg(adat.isCeges());
            rendeles.setAdoszam(adat.getAdoszam());
            rendeles.setSzall_irsz(adat.getSzall_iranyitoszam());
            rendeles.setSzall_telepules(adat.getSzall_telepules());
            rendeles.setSzall_utca(adat.getSzall_utca());
            rendeles.setSzall_hazszam(adat.getSzall_hazszam());
            rendeles.setSzall_egyeb(adat.getSzall_egyeb());
        }

        rendelesRepository.save(rendeles);
        session.removeAttribute("kosar");

        return "redirect:/checkout/sikeres";
    }

    @GetMapping("/sikeres")
    public String sikeres() {
        return "checkout_sikeres";
    }

    public record CartItem(Ckt termek, int mennyiseg) {}
}
