package com.pistasdeportivas.pistas_deportivas.controladores;
import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;red;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pistasdeportivas.pistas_deportivas.modelos.Instalacion;
import com.pistasdeportivas.pistas_deportivas.modelos.Reserva;
import com.pistasdeportivas.pistas_deportivas.repos.RepoInstalacion;
import com.pistasdeportivas.pistas_deportivas.repos.RepoReserva;

import ch.qos.logback.core.model.Model;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/reserva")
public class ControReserva {

    @Autowired
    RepoReserva repoReserva;

    @Autowired 
    RepoInstalacion repoInstalacion;

    @GetMapping("")
    public String getReservas(Model model){
        List<Instalacion> instalaciones = repoInstalacion.findAll();
        model.addAttribute("reserva", reserva);
        return "reserva/reserva"
    }

    @GetMapping("/add")
    public String addReservas(Model modelo) {
        modelo.addAttribute("reserva", new Reserva());
        return "/reserva/add";
    }

    @PostMapping("/add")
    public String addReservas(
        @ModelAttribute("reserva") Reserva reserva)  {
        repoReserva.save(reserva);
        return "redirect:/reserva";
    }

    @GetMapping("/edit/{id}")
    public String editReserva( 
        @PathVariable @NonNull Long id,
        Model modelo) {

        Optional<Reserva> oReserva = repoReserva.findById(id);
        if (oReserva.isPresent()) {
            modelo.addAttribute("reserva", oReserva.get());
            return "/reserva/add";
        } else {
            modelo.addAttribute("mensaje", "La reserva no exsiste");
            modelo.addAttribute("titulo", "Error editando reserva.");
            return "/error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editReserva(
        @ModelAttribute("reserva") Reserva reserva)  {
        repoReserva.save(reserva);
        return "redirect:/reserva";
    }


    @GetMapping("/del/{id}")
    public String delReserva( 
        @PathVariable @NonNull Long id,
        Model modelo) {

        Optional<Instalacion> oReserva = repoReserva.findById(id);
        if (oReserva.isPresent()) {
            modelo.addAttribute("borrando", "verdadero");
            modelo.addAttribute("reserva", oInstalacion.get());
            return "/reserva/add";
        } else {
            modelo.addAttribute("mensaje", "La reserva no exsiste");
            modelo.addAttribute("titulo", "Error borrando instalación.");
            return "/error";
        }
    }

    @PostMapping("/del/{id}")
    public String delReserva(
        @ModelAttribute("reserva") Reserva reserva)  {
        repoInstalacion.delete(reserva);
        return "redirect:/reserva";
    }
}