package ws.ivi.dyndns.netszatyor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.ivi.dyndns.netszatyor.service.ProductService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;

@Controller
public class UploadController {

    private final ProductService productService;

    public UploadController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

        // 1. LÉPÉS: fájl feltöltés a media/ mappába
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            Path uploadDir = Paths.get("media");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path targetPath = uploadDir.resolve(file.getOriginalFilename());
            file.transferTo(targetPath.toFile());

            model.addAttribute("message", "Fájl sikeresen feltöltve: " + file.getOriginalFilename());
            model.addAttribute("filename", file.getOriginalFilename());  // a feldolgozáshoz továbbítjuk
        } catch (IOException e) {
            model.addAttribute("error", "Hiba történt a fájl feltöltésekor: " + e.getMessage());
        }

        return "upload"; // a feltoltes.html megjelenítése
    }

    // 2. LÉPÉS: külön feldolgozás kérésre
    @PostMapping("/process")
    public String processFile(@RequestParam("filename") String filename, Model model) {
        try {
            Path filePath = Paths.get("media").resolve(filename);
            if (Files.exists(filePath)) {
                File dbfFile = filePath.toFile();
                try (FileInputStream fis = new FileInputStream(dbfFile)) {
                    productService.importFromDbf(fis);  // módosított szignatúra
                }
                model.addAttribute("message", "Feldolgozás sikeres: " + filename);
            } else {
                model.addAttribute("error", "A fájl nem található: " + filename);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Hiba történt a feldolgozás során: " + e.getMessage());
        }

        return "upload";
    }
}
