package com.pistasdeportivas.pistas_deportivas.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pistasdeportivas.pistas_deportivas.modelos.Instalacion;

@Repository
public interface RepoInstalacion extends JpaRepository<Instalacion,Long>{

    @Query("SELECT DISTINCT instalacion FROM Instalacion instalacion LEFT JOIN Reserva reserva ON instalacion.id = reserva.instalacion.id " +
    "WHERE reserva.id IS NULL OR reserva.fecha <> CURRENT_DATE")    
    List<Instalacion> findAvailableInstalaciones();
    
}
