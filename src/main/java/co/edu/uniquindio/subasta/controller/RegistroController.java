package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IRegistroService;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;

public class RegistroController implements IRegistroService {

    ModelFactoryController modelFactoryController;

    public RegistroController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    @Override
    public boolean registrarComprador(CompradorDto compradorDto) {
        return modelFactoryController.agregarComprador(compradorDto);
    }

    @Override
    public boolean registrarAnunciante(AnuncianteDto anuncianteDto) {
        return modelFactoryController.agregarAnunciante(anuncianteDto);
    }
}
