package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.controller.servicies.IProductoService;

import java.util.List;

public class ProductoController implements IProductoService {
    ModelFactoryController modelFactoryController;

    public ProductoController() {
        modelFactoryController = ModelFactoryController.getInstance();

    }

    @Override
    public List<ProductoDto> obtenerProducto() {
    return modelFactoryController.obtenerProducto();
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto) throws ProductoException {
        return modelFactoryController.agregarProducto(productoDto);
    }

    @Override
    public boolean eliminarProducto(ProductoDto productoDto) throws ProductoException {
        return modelFactoryController.eliminarProducto(productoDto);
    }

    @Override
    public boolean actualizarProducto(ProductoDto productoDto) throws ProductoException {
        return modelFactoryController.actualizarAnuncio(productoDto);
    }

//    public String obtenerCedulaLogueo(){
//        return modelFactoryController.getSubasta().getAnuncianteLogueado().getCedula();
//    }
}
