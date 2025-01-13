package com.projektocr.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String redirectToIndex() {
        return "index"; // Przekierowanie na główną stronę
    }

    @GetMapping("/ocr")
    public String redirectToOcr() {
        return "ocr"; // Przekierowanie na stronę ocr
    }

}
