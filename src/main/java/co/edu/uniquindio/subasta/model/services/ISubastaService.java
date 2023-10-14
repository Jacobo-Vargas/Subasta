package co.edu.uniquindio.subasta.model.services;

import co.edu.uniquindio.subasta.exceptions.ProductoException;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Comprador;
import co.edu.uniquindio.subasta.model.Persona;
import co.edu.uniquindio.subasta.model.Producto;

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


}
