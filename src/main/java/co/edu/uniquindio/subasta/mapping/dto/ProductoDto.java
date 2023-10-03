package co.edu.uniquindio.subasta.mapping.dto;


import co.edu.uniquindio.subasta.model.TipoArticulo;

public record ProductoDto(
        String nombre,
        TipoArticulo tipoArticulo,
        int codigoProducto
        ) {


}