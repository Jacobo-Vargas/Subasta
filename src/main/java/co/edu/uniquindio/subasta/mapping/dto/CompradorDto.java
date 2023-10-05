package co.edu.uniquindio.subasta.mapping.dto;


import co.edu.uniquindio.subasta.model.Puja;
import co.edu.uniquindio.subasta.model.Usuario;

import java.util.List;

public record CompradorDto(
        String nombre,
        String apellido,
        String cedula,
        int edad,
        String telefono,

        Usuario usuario,
        String direccion,
        List<Puja> listaPujas
) {
}