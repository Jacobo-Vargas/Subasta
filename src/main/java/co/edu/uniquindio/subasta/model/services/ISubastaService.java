package co.edu.uniquindio.subasta.model.services;

import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.*;

import java.util.List;

public interface ISubastaService {

    //------------------------- Registro & Login -----------------------------//

    boolean verificarExistenciaComprador(String cedula);
    boolean verificarExistenciaAnunciante(String cedula);


    // --------------------------- CRUD PRODUCTO --------------------------//

    List<Producto> obtenerProducto();
    boolean agregarProducto(Producto producto) throws ProductoException;

    boolean eliminarProducto(Producto producto) throws ProductoException;

    boolean actualizarProducto(Producto empleado) throws ProductoException;

//    ---------------------------- CRUD ANUNCIO -------------------------------//

    List<Anuncio> obtenerAnuncio();

    boolean agregarAnuncio(Anuncio anuncio) throws AnuncioException;
    boolean eliminarAnuncio(Anuncio anuncio);
    boolean actuaizarAnuncio(Anuncio anuncio);


}
