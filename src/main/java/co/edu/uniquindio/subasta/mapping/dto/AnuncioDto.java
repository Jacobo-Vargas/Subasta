package co.edu.uniquindio.subasta.mapping.dto;

import co.edu.uniquindio.subasta.model.Producto;
import co.edu.uniquindio.subasta.model.Puja;
import javafx.scene.image.Image;
import java.time.LocalDate;
import java.util.ArrayList;

public record AnuncioDto(
        ProductoDto producto,
        String nombre,
        String descripcion,
        String foto,
        String nombreAnunciante,

        String fechaPublicacion,
        String fechaTerminacion,
        float valorInicial,
        String codigo,

        ArrayList<PujaDto> listaPujas


) {
}
