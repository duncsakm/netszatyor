package ws.ivi.dyndns.netszatyor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ws.ivi.dyndns.netszatyor.model.User;
import ws.ivi.dyndns.netszatyor.model.UserData;
import ws.ivi.dyndns.netszatyor.repository.UserDataRepository;

@Controller
@RequestMapping("/profil")
@RequiredArgsConstructor
public class ProfileController {

    private final UserDataRepository adatRepository;

    // Profil megtekintése
    @GetMapping
    public String profilView(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }

        UserData adat = user.getUserData();
        if (adat == null) {
            adat = new UserData();
        }

        model.addAttribute("userData", adat);
        return "profil";
    }

    // Profil mentése
    @PostMapping("/mentes")
    public String profilMentes(@AuthenticationPrincipal User user,
                               @ModelAttribute("userData") UserData formUser) {
        if (user == null) {
            return "redirect:/login";
        }

        UserData currentData = user.getUserData();

        if (currentData == null) {
            currentData = new UserData();
            currentData.setUser(user);
        }

        // Frissítendő mezők
        currentData.setNev(formUser.getNev());
        currentData.setIranyitoszam(formUser.getIranyitoszam());
        currentData.setTelepules(formUser.getTelepules());
        currentData.setUtca(formUser.getUtca());
        currentData.setHazszam(formUser.getHazszam());
        currentData.setCimegyeb(formUser.getCimegyeb());
        currentData.setTelefon(formUser.getTelefon());
        currentData.setCeges(formUser.isCeges());
        currentData.setAdoszam(formUser.getAdoszam());

        currentData.setSzall_iranyitoszam(formUser.getSzall_iranyitoszam());
        currentData.setSzall_telepules(formUser.getSzall_telepules());
        currentData.setSzall_utca(formUser.getSzall_utca());
        currentData.setSzall_hazszam(formUser.getSzall_hazszam());
        currentData.setSzall_egyeb(formUser.getSzall_egyeb());

        adatRepository.save(currentData);
        return "redirect:/profil";
    }
}
