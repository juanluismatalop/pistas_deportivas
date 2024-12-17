package com.pistasdeportivas.pistas_deportivas.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class ControMain {
    
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
    
    @GetMapping("/error")
    public String getError() {
        return "error";
    }

    @GetMapping("/acerca")
    public String acercade() {
        return "acercade";
    }
    
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    
    @GetMapping("/denegado")
    public String accesoDenegado() {
        return "denegado";
    }

}
