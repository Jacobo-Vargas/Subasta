package co.edu.uniquindio.subasta.mapping.dto;

import co.edu.uniquindio.subasta.model.Producto;
import javafx.scene.image.Image;
import java.time.LocalDate;

public record AnuncioDto(
        String codigoAnuncio,
        Producto producto,
        String descripcion,
        float valorInicial,
        Image fotoAnuncio,
        String nombreAnunciante,
        LocalDate fechaPublicacion,
        LocalDate fechaFinPublicacion


) {
}
