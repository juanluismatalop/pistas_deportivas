package com.pistasdeportivas.pistas_deportivas.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pistasdeportivas.pistas_deportivas.modelos.Horario;
import com.pistasdeportivas.pistas_deportivas.modelos.Instalacion;
import com.pistasdeportivas.pistas_deportivas.repos.RepoHorario;
import com.pistasdeportivas.pistas_deportivas.repos.RepoInstalacion;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MaestroDetalleInstalacion {
    @Autowired
    private RepoInstalacion repoInstalaciones;

    @Autowired
    private RepoHorario repoHorarios;

    @GetMapping("/instalacion-detalle")
    public String MostrarInstalaciones(Model model) {
        List<Instalacion> instalaciones = repoInstalaciones.findAll();
        model.addAttribute("instalaciones", instalaciones);
        model.addAttribute("instalacionSeleccionada", new Instalacion());
        return "instalacion-detalle/formulario";
    }

    @GetMapping("/instalacion-detalle/horarios")
        public String MostrarHorario(@RequestParam("idInstalacion") Long idInstalacion, Model model){
            Optional<Instalacion> optionalInstalacion = repoInstalaciones.findById(idInstalacion);
            if (optionalInstalacion.isPresent()) {
                Instalacion instalacion = optionalInstalacion.get();
                List<Horario> listaHorarios = repoHorarios.findByInstalacion(instalacion);
                model.addAttribute("instalacion", instalacion);
                model.addAttribute("horarios", listaHorarios);
                return "instalacion-detalle/horarios";
            } else {
                model.addAttribute("mensaje", "instalacionNoEncontrada");
                return "/error";
            }
        }
    }