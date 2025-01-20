package com.pistasdeportivas.pistas_deportivas.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pistasdeportivas.pistas_deportivas.modelos.Rol;
import com.pistasdeportivas.pistas_deportivas.modelos.Usuario;
import com.pistasdeportivas.pistas_deportivas.repos.RepoUsuario;


@Controller
public class ControRegistro {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String procesarRegistro(
            @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            usuario.setTipo(Rol.USER);

            usuario.setEnabled(true);

            repoUsuario.save(usuario);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Hubo un problema al registrar al usuario. Inténtelo nuevamente.");
            return "register";
        }
    }
}
