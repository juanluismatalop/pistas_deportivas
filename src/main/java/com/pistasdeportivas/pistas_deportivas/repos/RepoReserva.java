package com.pistasdeportivas.pistas_deportivas.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pistasdeportivas.pistas_deportivas.modelos.Reserva;

@Repository
public interface RepoReserva extends JpaRepository<Reserva, Long>{
    
}
