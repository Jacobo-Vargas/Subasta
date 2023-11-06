package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.lang.reflect.AnnotatedArrayType;
import java.util.List;

public interface IAnuncioControllerService {

    List<ProductoDto> obtenerProducto();
    List<AnuncioDto> obtenerAnuncio();

    String recuperarNombre();

    List<AnuncioDto> obtenerAnunciosGlobales();

    int agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;
    boolean eliminarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;
    boolean actualizarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;
}
