package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;

import java.util.List;

public interface IPujaControllerService {
    List<PujaDto> obtenerLitaPuja();
    List<AnuncioDto>ObenerlistaAnuncio();
    boolean realizarPuja(PujaDto pujaDto) throws Exception;
    boolean elimnarPuja(PujaDto pujaDto) throws Exception;
    boolean actulizarPuja(PujaDto pujaDto) throws Exception;
}
