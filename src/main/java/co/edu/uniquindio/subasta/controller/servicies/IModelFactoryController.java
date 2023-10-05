package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;

import java.util.List;

public interface IModelFactoryController {
    List<ProductoDto> obtenerProducto(String cedula);
    boolean agregarProducto(ProductoDto empleadoDto,String cedula);

    boolean eliminarProducto(String cedula);

    boolean actualizarProducto(String cedulaActual, ProductoDto empleadoDto);



    //--------------------------- Registro --------------------------------

    boolean agregarAnunciante(AnuncianteDto anuncianteDto);
    boolean agregarComprador(CompradorDto compradorDto);

}
