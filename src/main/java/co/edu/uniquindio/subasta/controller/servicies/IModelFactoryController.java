package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;

import java.util.List;

public interface IModelFactoryController {

//------------------------ CRUD PRODUCTO ---------------------------
    List<ProductoDto> obtenerProducto();
    boolean agregarProducto(ProductoDto empleadoDto) throws ProductoException;

    boolean eliminarProducto(ProductoDto productoDto) throws ProductoException;

    boolean actualizarProducto(ProductoDto empleadoDto) throws ProductoException;

//    ----------------------- CRUD ANUNCIO ---------------------------------------
    List<AnuncioDto> obtenerAnuncio();

    String recuperarNombreAnunciante();

    boolean agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;
    boolean eliminarAnuncio(AnuncioDto anuncioDto);
    boolean actuaizarAnuncio(AnuncioDto anuncioDto);




    //--------------------------- Registro --------------------------------

    boolean agregarAnunciante(AnuncianteDto anuncianteDto);
    boolean agregarComprador(CompradorDto compradorDto);


    //------------------------  Login ---------------------------------------

    boolean verificarAccesoComprador(String cedula,String contrasenia);
    boolean verificarAccesoAnunciante(String cedula,String contrasenia);

}
