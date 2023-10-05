package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;

public interface IRegistroService {

    public boolean registrarComprador(CompradorDto compradorDto);
    public boolean registrarAnunciante(AnuncianteDto anuncianteDto);
}
