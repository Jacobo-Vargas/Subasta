package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;

import java.util.List;

public interface IProductoService {
    List<ProductoDto> obtenerProducto();
    boolean agregarProducto(ProductoDto productoDto) throws ProductoException;

    boolean eliminarProducto(ProductoDto productoDto) throws ProductoException;

    boolean actualizarProducto(ProductoDto empleadoDto) throws ProductoException;
}
