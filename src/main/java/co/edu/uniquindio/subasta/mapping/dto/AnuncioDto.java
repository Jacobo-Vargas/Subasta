package co.edu.uniquindio.subasta.mapping.dto;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public record AnuncioDto(
        ProductoDto producto,
        String nombre,
        String descripcion,
        String foto,
        String nombreAnunciante,
        String fechaPublicacion,
        String fechaTerminacion,
        double valorInicial,
        String codigo,

        ArrayList<PujaDto> listaPujas
) {

}