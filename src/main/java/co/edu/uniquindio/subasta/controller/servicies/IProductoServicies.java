package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;

import java.util.List;

public interface IProductoServicies {
    List<ProductoDto> obtenerProducto();
    boolean agregarProducto(ProductoDto productoDto,String cedula);

    boolean eliminarProducto(String cedula);

    boolean actualizarProducto(String cedulaActual, ProductoDto empleadoDto);
}
