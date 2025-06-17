package ws.ivi.dyndns.netszatyor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ws.ivi.dyndns.netszatyor.repository.ProductRepository;

@Controller
public class HomeController {

    private final ProductRepository productRepository;

    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("akciok", productRepository.findTop9ByOrderByArAsc()); // Pl. legolcsóbb termékek
        return "index";
    }
}
