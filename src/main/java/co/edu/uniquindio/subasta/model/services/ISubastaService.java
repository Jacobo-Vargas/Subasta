package co.edu.uniquindio.subasta.model.services;

import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Comprador;
import co.edu.uniquindio.subasta.model.Persona;

public interface ISubastaService {

    //------------------------- Registro & Login -----------------------------//

    boolean verificarExistenciaComprador(String cedula);
    boolean verificarExistenciaAnunciante(String cedula);


    // ------------------------------------------------------------------//
}
