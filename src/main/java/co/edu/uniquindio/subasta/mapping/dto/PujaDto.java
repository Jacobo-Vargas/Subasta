package co.edu.uniquindio.subasta.mapping.dto;

import co.edu.uniquindio.subasta.model.Anuncio;

import java.time.LocalDate;

public record PujaDto(
        String direccion,
        String codigo,
        float oferta,
        String fechaPuja,
        String codigoAnuncio,
        String cedulaComprador
) {
}
