package co.edu.uniquindio.subasta.mapping.dto;


import co.edu.uniquindio.subasta.model.Anuncio;
import co.edu.uniquindio.subasta.model.Producto;

import java.util.ArrayList;

public record AnuncianteDto(
        String nombre,
        ArrayList<Producto> listaProducto,
        ArrayList<Anuncio> listaAnuncio) {
}

