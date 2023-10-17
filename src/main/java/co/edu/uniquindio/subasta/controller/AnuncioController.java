package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IAnuncioService;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.model.Anuncio;

import java.util.List;

public class AnuncioController implements IAnuncioService {
    ModelFactoryController modelFactoryController;

    public AnuncioController() {
        modelFactoryController=ModelFactoryController.getInstance();
    }


    @Override
    public List<AnuncioDto> obtenerAnuncio() {
        return modelFactoryController.obtenerAnuncio();
    }

    @Override
    public boolean agregarAnuncio(AnuncioDto anuncioDto) throws Exception {
        return modelFactoryController.agregarAnuncio(anuncioDto);
    }

    @Override
    public boolean alimanarAnuncio(AnuncioDto anuncioDto) throws Exception {
        return modelFactoryController.alimanarAnuncio(anuncioDto);
    }

    @Override
    public boolean actualizarProducto(AnuncioDto anuncioDto) throws Exception {
        return modelFactoryController.actualizarAnuncio(anuncioDto);
    }
}
