package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.util.List;

public interface IAnuncioControllerService {

    List<ProductoDto> obtenerProducto();
    List<AnuncioDto> obtenerAnuncio();

    String recuperarNombre();

    boolean agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;
    boolean eliminarAnuncio(AnuncioDto anuncioDto);
    boolean actuaizarAnuncio(AnuncioDto anuncioDto);
}
