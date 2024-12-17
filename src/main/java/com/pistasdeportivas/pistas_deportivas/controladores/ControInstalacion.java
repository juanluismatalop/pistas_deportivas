package com.pistasdeportivas.pistas_deportivas.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pistasdeportivas.pistas_deportivas.modelos.Instalacion;
import com.pistasdeportivas.pistas_deportivas.repos.RepoInstalacion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/instalacion")
public class ControInstalacion {
    
    @Autowired 
    RepoInstalacion repoInstalacion;

    @GetMapping("")
    public String getInstalaciones(Model model) {
        List<Instalacion> instalaciones = repoInstalacion.findAll();
        model.addAttribute("instalaciones", instalaciones);
        return "instalaciones/instalaciones";
    }


    @GetMapping("/add")
    public String addInstalacion(Model modelo) {
        modelo.addAttribute("instalacion", new Instalacion());
        return "/instalaciones/add";
    }

    @PostMapping("/add")
    public String addInstalacion(
        @ModelAttribute("instalacion") Instalacion instalacion)  {
        repoInstalacion.save(instalacion);
        return "redirect:/instalacion";
    }

    @GetMapping("/edit/{id}")
    public String editInstalacion( 
        @PathVariable @NonNull Long id,
        Model modelo) {

        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            modelo.addAttribute("instalacion", oInstalacion.get());
            return "/instalaciones/add";
        } else {
            modelo.addAttribute("mensaje", "La instalación no exsiste");
            modelo.addAttribute("titulo", "Error editando instalación.");
            return "/error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editInstalacion(
        @ModelAttribute("instalacion") Instalacion instalacion)  {
        repoInstalacion.save(instalacion);
        return "redirect:/instalacion";
    }


    @GetMapping("/del/{id}")
    public String delInstalacion( 
        @PathVariable @NonNull Long id,
        Model modelo) {

        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            modelo.addAttribute("borrando", "verdadero");
            modelo.addAttribute("instalacion", oInstalacion.get());
            return "/instalaciones/add";
        } else {
            modelo.addAttribute("mensaje", "La instalación no exsiste");
            modelo.addAttribute("titulo", "Error borrando instalación.");
            return "/error";
        }
    }

    @PostMapping("/del/{id}")
    public String delInstalacion(
        @ModelAttribute("instalacion") Instalacion instalacion)  {
        repoInstalacion.delete(instalacion);
        return "redirect:/instalacion";
    }
    
}
