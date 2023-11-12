package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.util.List;

public interface IPujaControllerService {
    List<PujaDto> obtenerLitaPuja();
    List<AnuncioDto>obtenerListaNuncio();
    boolean realizarPuja(PujaDto pujaDto, String codigo) throws Exception;
    boolean elimnarPuja(PujaDto pujaDto) throws Exception;
    AnuncioDto salvarAnuncio(String nombre);

}
