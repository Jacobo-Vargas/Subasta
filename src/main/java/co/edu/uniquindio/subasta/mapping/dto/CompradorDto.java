package co.edu.uniquindio.subasta.mapping.dto;


import co.edu.uniquindio.subasta.model.Puja;

import java.util.List;

public record CompradorDto(
        String direccion,
        List<Puja> listaPujas
) {
}