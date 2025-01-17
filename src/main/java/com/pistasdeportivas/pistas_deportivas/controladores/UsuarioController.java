
package com.pistasdeportivas.pistas_deportivas.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pistasdeportivas.pistas_deportivas.modelos.Rol;
import com.pistasdeportivas.pistas_deportivas.modelos.Usuario;
import com.pistasdeportivas.pistas_deportivas.repos.RepoUsuario;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String listarUsuarios(Model modelo) {
        List<Usuario> usuarios = repoUsuario.findAll();
        modelo.addAttribute("usuarios", usuarios);
        return "usuarios/usuarios";
    }

    @GetMapping("/add")
    public String agregarUsuario(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        return "usuarios/add";
    }

    @PostMapping("/add")
public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

    if (usuario.getTipo() == null) {
        usuario.setTipo(Rol.USER);
    }

    usuario.setEnabled(usuario.isEnabled());

    repoUsuario.save(usuario);
    return "redirect:/usuario";
}



    @GetMapping("/edit/{id}")
    public String editarUsuario(@PathVariable @NonNull Long id, Model modelo) {
        Optional<Usuario> oUsuario = repoUsuario.findById(id);
        if (oUsuario.isPresent()) {
            modelo.addAttribute("usuario", oUsuario.get());
            return "usuarios/add";
        } else {
            modelo.addAttribute("mensaje", "El usuario no existe");
            modelo.addAttribute("titulo", "Error editando usuario");
            return "/error";
        }
    }

    @PostMapping("/edit/{id}")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repoUsuario.save(usuario);
        return "redirect:/usuario";
    }

    @GetMapping("/del/{id}")
    public String confirmarEliminarUsuario(@PathVariable @NonNull Long id, Model modelo) {
        Optional<Usuario> oUsuario = repoUsuario.findById(id);
        if (oUsuario.isPresent()) {
            modelo.addAttribute("usuario", oUsuario.get());
            modelo.addAttribute("borrando", "verdadero");
            return "usuarios/add";
        } else {
            modelo.addAttribute("mensaje", "El usuario no existe");
            modelo.addAttribute("titulo", "Error eliminando usuario");
            return "/error";
        }
    }

    @PostMapping("/del/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        repoUsuario.deleteById(id);
        return "redirect:/usuario";
    }
}
