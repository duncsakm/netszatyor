package ws.ivi.dyndns.netszatyor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.ivi.dyndns.netszatyor.model.FeldolgozasStatusz;
import ws.ivi.dyndns.netszatyor.repository.FeldolgozasStatuszRepository;
import ws.ivi.dyndns.netszatyor.service.ProductService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

@Controller
public class UploadController {

    private final ProductService productService;
    private final FeldolgozasStatuszRepository statuszRepository;

    public UploadController(ProductService productService, FeldolgozasStatuszRepository statuszRepository) {
        this.productService = productService;
        this.statuszRepository = statuszRepository;
    }

    // 1. Űrlap megjelenítése
    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    // 2. Fájl mentése a media/ könyvtárba
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String basePath = System.getProperty("user.dir");
            Path uploadDir = Paths.get(basePath, "media");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path targetPath = uploadDir.resolve(file.getOriginalFilename());
            file.transferTo(targetPath.toFile());

            model.addAttribute("message", "Fájl sikeresen feltöltve: " + file.getOriginalFilename());
            model.addAttribute("filename", file.getOriginalFilename());
        } catch (IOException e) {
            model.addAttribute("error", "Hiba történt a fájl feltöltésekor: " + e.getMessage());
        }

        return "upload";
    }

    // 3. Feldolgozás külön kérésre, aszinkron módon
    @PostMapping("/process")
    public String processFile(@RequestParam("filename") String filename, Model model) {
        try {
            String basePath = System.getProperty("user.dir");
            Path filePath = Paths.get(basePath, "media").resolve(filename);

            if (Files.exists(filePath)) {
                FileInputStream fis = new FileInputStream(filePath.toFile());
                productService.importFromDbf(fis, filename);  // ✅ aszinkron, InputStream és fájlnév
                model.addAttribute("message", "Feldolgozás elindítva: " + filename);
            } else {
                model.addAttribute("error", "A fájl nem található: " + filename);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Hiba történt a feldolgozás során: " + e.getMessage());
        }

        return "upload";
    }

    // 4. Állapot lekérdezése
    @GetMapping("/statusz")
    @ResponseBody
    public String getStatus(@RequestParam("filename") String filename) {
        Optional<FeldolgozasStatusz> statusOpt = statuszRepository.findTopByFajlNevOrderByFrissitveDesc(filename);
        if (statusOpt.isPresent()) {
            FeldolgozasStatusz status = statusOpt.get();
            return String.format(
                    "Fájl: %s\nKész: %s\nÚj rekord: %d\nMódosított rekord: %d\nFeldolgozott sor: %d\nFrissítve: %s",
                    status.getFajlNev(),
                    status.isKesz() ? "Igen" : "Nem",
                    status.getUjRekord(),
                    status.getModositottRekord(),
                    status.getFeldolgozottSor(),
                    status.getFrissitve()
            );
        } else {
            return "Nincs állapotadat a megadott fájlhoz: " + filename;
        }
    }
}
