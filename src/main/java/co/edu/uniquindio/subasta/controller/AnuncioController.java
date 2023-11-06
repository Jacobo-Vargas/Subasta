package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.IAnuncioControllerService;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
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
    public String recuperarNombre() {
        return modelFactoryController.recuperarNombreAnunciante();
    }

    @Override
    public List<AnuncioDto> obtenerAnunciosGlobales() {
        return modelFactoryController.obtenerAnunciosGlobales();
    }

    @Override
    public int agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        return modelFactoryController.agregarAnuncio(anuncioDto);
    }

    @Override
    public boolean eliminarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        return modelFactoryController.eliminarAnuncio(anuncioDto);
    }

    @Override
    public boolean actualizarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        return modelFactoryController.actualizarAnuncio(anuncioDto);
    }
}
