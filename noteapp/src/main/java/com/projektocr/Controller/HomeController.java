package com.projektocr.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Endpoint, który obsługuje stronę główną
    @GetMapping("/")
    public String home() {
        return "index";  // Zwróci widok 'index.html' z folderu 'src/main/resources/templates'
    }
}
