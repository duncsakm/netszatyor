package ws.ivi.dyndns.netszatyor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class FileUploadController {

    private final String UPLOAD_DIR = "media/";

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Nincs fájl kiválasztva.");
            return "upload";
        }

        try {
            File dest = new File(UPLOAD_DIR + file.getOriginalFilename());
            file.transferTo(dest);
            model.addAttribute("success", "Sikeres feltöltés: " + file.getOriginalFilename());
        } catch (IOException e) {
            model.addAttribute("error", "Hiba a fájl mentése közben: " + e.getMessage());
        }

        return "upload";
    }
}
