package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;

import java.util.List;

public interface IProductoService {
    List<ProductoDto> obtenerProducto();
    boolean agregarProducto(ProductoDto productoDto);

    boolean eliminarProducto(ProductoDto productoDto);

    boolean actualizarProducto(ProductoDto empleadoDto);
}
