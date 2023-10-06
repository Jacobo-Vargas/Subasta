package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.controller.servicies.IProductoServicies;

import java.util.List;

public class ProductoController implements IProductoServicies {
    ModelFactoryController modelFactoryController;

    public ProductoController() {
        modelFactoryController = ModelFactoryController.getInstance();

    }

    @Override
    public List<ProductoDto> obtenerProducto() {
    return modelFactoryController.obtenerProducto();
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto,String cedula) {
        return modelFactoryController.agregarProducto(productoDto,cedula);
    }

    @Override
    public boolean eliminarProducto(String cedula) {
        return modelFactoryController.eliminarProducto(String.valueOf(cedula));
    }

    @Override
    public boolean actualizarProducto(String cedulaActual, ProductoDto empleadoDto) {
        return false;
    }

    public String obtenerCedulaLogueo(){
        return modelFactoryController.getSubasta().getAnuncianteLogueado().getCedula();
    }
}
