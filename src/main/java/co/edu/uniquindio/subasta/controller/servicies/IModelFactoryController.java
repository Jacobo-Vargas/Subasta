package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.*;
import co.edu.uniquindio.subasta.model.Anuncio;

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
    List<AnuncioDto> obtenerAnunciosGlobales();

    int agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;

    boolean eliminarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;

    boolean actualizarAnuncio(AnuncioDto anuncioDto) throws AnuncioException;

//----------------------------Crud Puja--------------------


    //--------------------------- Registro --------------------------------

    boolean agregarAnunciante(AnuncianteDto anuncianteDto);

    boolean agregarComprador(CompradorDto compradorDto);


    //------------------------  Login ---------------------------------------

    boolean verificarAccesoComprador(String cedula, String contrasenia);

    boolean verificarAccesoAnunciante(String cedula, String contrasenia);

    //--------------------------Crud Puja-------------------------------------
    List<PujaDto> obtenerLitaPuja();

    boolean realizarPuja(PujaDto pujaDto, String codigo) throws Exception;

    boolean elimnarPuja(PujaDto pujaDto) throws Exception;

    boolean actulizarPuja(PujaDto pujaDto) throws Exception;
    Anuncio salvarAnuncio(String nombre);
    List<AnuncianteDto> obtenerListaAnunciante();

    List<AnuncioDto>obtenerListaNuncio();
    List<PujaDto>listaAnuncioCompradorLogueado();


    //-----------------------anunciante---------------------------




    //------------------ojo-------------


}
