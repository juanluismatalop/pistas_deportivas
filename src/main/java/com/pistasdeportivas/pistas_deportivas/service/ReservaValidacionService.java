package com.pistasdeportivas.pistas_deportivas.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ReservaValidacionService {

    private static final int LIMITE_SEMANAS = 2;

    public boolean esFechaValida(LocalDate fechaReserva) {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusWeeks(LIMITE_SEMANAS);
        return !fechaReserva.isAfter(limite);
    }
}
