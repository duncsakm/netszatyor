package ws.ivi.dyndns.netszatyor.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ws.ivi.dyndns.netszatyor.model.*;
import ws.ivi.dyndns.netszatyor.repository.CktRepository;
import ws.ivi.dyndns.netszatyor.repository.RendelesRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CktRepository cktRepository;
    private final RendelesRepository rendelesRepository;

    public String showCheckout(Model model, HttpSession session, User user) {
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

    public String processCheckout(FizetesiMod fizetesiMod, HttpSession session, User user) {
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

        UserData adat = user.getUserData();

        Rendeles rendeles = Rendeles.builder()
                .user(user)
                .datum(LocalDateTime.now())
                .fizetesiMod(fizetesiMod)
                .vegosszeg(osszeg)
                .nev(adat != null ? adat.getNev() : null)
                .irsz(adat != null ? adat.getIranyitoszam() : null)
                .telepules(adat != null ? adat.getTelepules() : null)
                .utca(adat != null ? adat.getUtca() : null)
                .hazszam(adat != null ? adat.getHazszam() : null)
                .egyeb(adat != null ? adat.getCimegyeb() : null)
                .telefon(adat != null ? adat.getTelefon() : null)
                .ceg(adat != null && adat.isCeges())
                .adoszam(adat != null ? adat.getAdoszam() : null)
                .szall_irsz(adat != null ? adat.getSzall_iranyitoszam() : null)
                .szall_telepules(adat != null ? adat.getSzall_telepules() : null)
                .szall_utca(adat != null ? adat.getSzall_utca() : null)
                .szall_hazszam(adat != null ? adat.getSzall_hazszam() : null)
                .szall_egyeb(adat != null ? adat.getSzall_egyeb() : null)
                .build();

        rendelesRepository.save(rendeles);
        session.removeAttribute("kosar");

        return "redirect:/checkout/sikeres";
    }
}
