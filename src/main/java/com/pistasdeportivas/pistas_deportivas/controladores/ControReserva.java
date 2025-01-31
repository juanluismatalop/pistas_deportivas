package com.pistasdeportivas.pistas_deportivas.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pistasdeportivas.pistas_deportivas.modelos.Instalacion;
import com.pistasdeportivas.pistas_deportivas.modelos.Reserva;
import com.pistasdeportivas.pistas_deportivas.modelos.Usuario;
import com.pistasdeportivas.pistas_deportivas.repos.RepoInstalacion;
import com.pistasdeportivas.pistas_deportivas.repos.RepoReserva;
import com.pistasdeportivas.pistas_deportivas.repos.RepoUsuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/reserva")
public class ControReserva {
    
    @Autowired
    private RepoReserva repoReserva;

    @Autowired
    private RepoInstalacion repoInstalacion;

    @GetMapping("")
    public String getReserva(Model model) {
        List<Reserva> reserva = repoReserva.findAll();
        model.addAttribute("reservas", reserva);
        return "/reserva";
    }

    @GetMapping("/add")
    public String addReserva(Model model) {
        List<Instalacion> instalacionesLibres = repoInstalacion.findAvailableInstalaciones();
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("instalaciones", instalacionesLibres);
        return "/reserva/add";
    }
    
    @PostMapping("/add")
    public String addReserva(
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

        Optional<Reserva> oReserva = repoReserva.findById(id);
        if (oReserva.isPresent()) {
            modelo.addAttribute("borrando", "verdadero");
            modelo.addAttribute("reserva", oReserva.get());
            return "/reserva";
        } else {
            modelo.addAttribute("mensaje", "La reserva no exsiste");
            modelo.addAttribute("titulo", "Error borrando reserva.");
            return "/error";
        }
    }

    @PostMapping("/del/{id}")
    public String delReserva(
        @ModelAttribute("reserva") Reserva reserva)  {
        repoReserva.delete(reserva);
        return "redirect:/reserva";
    }
    
}
