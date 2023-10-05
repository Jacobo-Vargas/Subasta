package co.edu.uniquindio.subasta.mapping.dto;


import co.edu.uniquindio.subasta.model.Anuncio;
import co.edu.uniquindio.subasta.model.Producto;
import co.edu.uniquindio.subasta.model.Usuario;

import java.util.ArrayList;

public record AnuncianteDto(
        String nombre,
        String apellido,
        String cedula,
        int edad,
        String telefono,
        Usuario usuario,
        ArrayList<Producto> listaProducto,
        ArrayList<Anuncio> listaAnuncio) {
}

