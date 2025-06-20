package ws.ivi.dyndns.netszatyor.controller;

import ws.ivi.dyndns.netszatyor.model.User;
import ws.ivi.dyndns.netszatyor.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. REGISZTRÁCIÓS ŰRLAP MEGJELENÍTÉSE
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // ez a register.html
    }

    // 2. REGISZTRÁCIÓS ADATOK FELDOLGOZÁSA
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, Model model) {
        // Ellenőrzés: már regisztrált email?
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Ez az e-mail cím már regisztrált.");
            return "register"; // visszairányítás hiba esetén
        }

        // Ellenőrzés: jelszavak egyeznek?
        if (user.getConfirmPassword() != null && !user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "A jelszavak nem egyeznek.");
            return "register";
        }

        // Mentés, ha minden rendben
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        return "redirect:/login"; // csak sikeres mentés után
    }
}
