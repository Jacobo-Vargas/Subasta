package co.edu.uniquindio.subasta.mapping.dto;

import co.edu.uniquindio.subasta.model.Producto;
import javafx.scene.image.Image;
import java.time.LocalDate;

public record AnuncioDto(
        Producto producto,
        String nombre,
        String descripcion,
        String foto,
        String nombreAnunciante,

        LocalDate fechaPublicacion,
        LocalDate fechaFinPublicacion,
        float valorInicial,
        int codigo


) {
}
