package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.util.List;

public interface IAnuncioControllerService {

    List<ProductoDto> obtenerProducto();
    List<AnuncioDto> obtenerAnuncio();

    boolean agregarAnuncio(AnuncioDto anuncioDto);
    boolean eliminarAnuncio(AnuncioDto anuncioDto);
    boolean actuaizarAnuncio(AnuncioDto anuncioDto);
}
