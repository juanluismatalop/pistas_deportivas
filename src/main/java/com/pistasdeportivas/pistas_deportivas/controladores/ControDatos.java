package com.pistasdeportivas.pistas_deportivas.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pistasdeportivas.pistas_deportivas.modelos.Usuario;
import com.pistasdeportivas.pistas_deportivas.repos.RepoUsuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/mis-datos")
public class ControDatos {

    @Autowired
    RepoUsuario repoUsuario;

    private Usuario getLoggedUser(){
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();
        return repoUsuario.findByUsername(
            authentication.getName()).get(0);
    }
    
    @GetMapping("")
    public String misDatos(Model modelo) {
        modelo.addAttribute("usuario", getLoggedUser());

        return "mis-datos/mis-datos";
    }

    
    @GetMapping("/edit")
    public String getMisDatos(Model modelo) {
        modelo.addAttribute("usuario", getLoggedUser());

        return "mis-datos/mis-datos";
    }

    @PostMapping("/edit")
    public String postMisDatos(
        @ModelAttribute("usuario") Usuario u,
        Model modelo) {
        Usuario loggedUser = getLoggedUser();
        if (loggedUser.getId() == u.getId()) {

            u.setTipo(loggedUser.getTipo());
            u.setPassword(loggedUser.getPassword());
            u.setEnabled(loggedUser.isEnabled());

            repoUsuario.save(u);
            return "redirect:/mis-datos";
        } else {
            modelo.addAttribute("mensaje", "Error editando datos de usuario");
            modelo.addAttribute("titulo", "No ha sido posible editar sus datos.");
            return "/error";
        }
        
    }
    

    
}
