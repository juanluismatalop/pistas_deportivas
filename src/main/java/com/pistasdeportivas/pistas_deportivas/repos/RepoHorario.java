package com.pistasdeportivas.pistas_deportivas.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pistasdeportivas.pistas_deportivas.modelos.Horario;
import com.pistasdeportivas.pistas_deportivas.modelos.Instalacion;

@Repository
public interface RepoHorario  extends JpaRepository<Horario, Long>{
    Page<Horario> findByInstalacion(Instalacion instalacion, Pageable pageable);

    List<Horario> findByInstalacion(Instalacion instalacion);
    
}
