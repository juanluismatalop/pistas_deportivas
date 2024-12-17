package com.pistasdeportivas.pistas_deportivas.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pistasdeportivas.pistas_deportivas.modelos.Usuario;
import java.util.List;

public interface RepoUsuario extends JpaRepository <Usuario, Long>{
    List<Usuario> findByUsername(String username);
}
