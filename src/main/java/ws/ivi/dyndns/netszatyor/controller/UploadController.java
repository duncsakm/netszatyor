package ws.ivi.dyndns.netszatyor.controller;

import ws.ivi.dyndns.netszatyor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    @Autowired
    private ProductService productService;

    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            productService.importFromDbf(file);
            model.addAttribute("message", "Sikeres import!");
        } catch (Exception e) {
            model.addAttribute("message", "Hiba: " + e.getMessage());
        }
        return "upload";
    }
}
