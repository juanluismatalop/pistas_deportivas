package com.pistasdeportivas.pistas_deportivas.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pistasdeportivas.pistas_deportivas.modelos.Reserva;
import com.pistasdeportivas.pistas_deportivas.modelos.Usuario;

@Repository
public interface RepoReserva extends JpaRepository<Reserva, Long>{
    List<Reserva> findByUsuario(Usuario usuario);
    List<Reserva> findByFecha(LocalDate fecha);
}
