package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.util.List;

public interface IAnuncioService {
    List<AnuncioDto> obtenerAnuncio();
    boolean agregarAnuncio(AnuncioDto anuncioDto) throws Exception;
    boolean alimanarAnuncio(AnuncioDto anuncioDto) throws Exception;
    boolean actualizarProducto(AnuncioDto anuncioDto) throws Exception;

}
