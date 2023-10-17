package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IAnuncioControllerService;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.Producto;

import java.util.List;

public class AnuncioController implements IAnuncioControllerService {

    ModelFactoryController modelFactoryController;
    public AnuncioController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    @Override
    public List<ProductoDto> obtenerProducto() {
        return modelFactoryController.obtenerProducto();
    }

    @Override
    public List<AnuncioDto> obtenerAnuncio() {
        return modelFactoryController.obtenerAnuncio();
    }

    @Override
    public boolean agregarAnuncio(AnuncioDto anuncioDto) {
        return false;
    }

    @Override
    public boolean eliminarAnuncio(AnuncioDto anuncioDto) {
        return false;
    }

    @Override
    public boolean actuaizarAnuncio(AnuncioDto anuncioDto) {
        return false;
    }
}
