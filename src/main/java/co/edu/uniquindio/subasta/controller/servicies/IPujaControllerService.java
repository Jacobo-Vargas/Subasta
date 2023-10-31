package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.util.List;

public interface IPujaControllerService {
    List<PujaDto> obtenerLitaPuja();
    List<AnuncioDto>obtenerListaNuncio();
    boolean realizarPuja(PujaDto pujaDto) throws Exception;
    boolean elimnarPuja(PujaDto pujaDto) throws Exception;
    boolean actulizarPuja(PujaDto pujaDto) throws Exception;
    Anuncio salvarAnuncio(String nombre);

}