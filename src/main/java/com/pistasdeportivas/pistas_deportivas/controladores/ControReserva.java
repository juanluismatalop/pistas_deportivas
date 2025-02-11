package com.pistasdeportivas.pistas_deportivas.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pistasdeportivas.pistas_deportivas.modelos.Horario;
import com.pistasdeportivas.pistas_deportivas.modelos.Instalacion;
import com.pistasdeportivas.pistas_deportivas.modelos.Reserva;
import com.pistasdeportivas.pistas_deportivas.modelos.Usuario;
import com.pistasdeportivas.pistas_deportivas.repos.RepoHorario;
import com.pistasdeportivas.pistas_deportivas.repos.RepoInstalacion;
import com.pistasdeportivas.pistas_deportivas.repos.RepoReserva;
import com.pistasdeportivas.pistas_deportivas.repos.RepoUsuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/reserva")
public class ControReserva {
    
    @Autowired
    private RepoReserva repoReserva;

    @Autowired
    private RepoInstalacion repoInstalacion;

    @Autowired
    private RepoHorario repoHorario;

    @Autowired
    private RepoUsuario repoUsuario;

    @GetMapping("")
    public String getReserva(Model model) {
        List<Reserva> reserva = repoReserva.findAll();
        System.out.println(reserva.toString());
        model.addAttribute("reservas", reserva);
        return "/reserva/reserva";
    }

    @GetMapping("/add/{instalacionId}/{usuarioId}/{fecha}")
    public String addReserva(
        @PathVariable(name = "instalacionId", required = false) Long instalacionId,
        @PathVariable(name = "usuarioId", required = false) Long usuarioId,
        @PathVariable(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        Model model) {

        List<Instalacion> instalacionesLibres = repoInstalacion.findAll();
        List<Horario> horarios = new ArrayList<>();
        Instalacion instalacionSeleccionada = null;
        Usuario usuarioSeleccionado = null;

        if (instalacionId != null) {
            instalacionSeleccionada = repoInstalacion.findById(instalacionId).orElse(null);
        }

        if (usuarioId != null) {
            usuarioSeleccionado = repoUsuario.findById(usuarioId).orElse(null);
        }

        if (instalacionSeleccionada != null && fecha != null) {
            horarios = repoHorario.findAvailableByInstalacionAndFecha(instalacionSeleccionada, fecha);
        }

        model.addAttribute("reserva", new Reserva());
        model.addAttribute("instalaciones", instalacionesLibres);
        model.addAttribute("instalacionSeleccionada", instalacionSeleccionada);
        model.addAttribute("usuarioSeleccionado", usuarioSeleccionado);
        model.addAttribute("horarios", horarios);
        model.addAttribute("usuarios", repoUsuario.findAll());

        return "/reserva/add";
    }


    @PostMapping("/add")
    public String addReserva(@ModelAttribute("reserva") Reserva reserva) {
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
            List<Instalacion> instalaciones = repoInstalacion.findAll();
            modelo.addAttribute("instalaciones", instalaciones);
            modelo.addAttribute("horarios", repoHorario.findAll());
            modelo.addAttribute("usuarios", repoUsuario.findAll());
            return "/reserva/add";
        } else {
            modelo.addAttribute("mensaje", "La reserva no existe");
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
            List<Instalacion> instalaciones = repoInstalacion.findAll();
            modelo.addAttribute("instalaciones", instalaciones);
            return "/reserva/add";
        } else {
            modelo.addAttribute("mensaje", "La reserva no existe");
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
