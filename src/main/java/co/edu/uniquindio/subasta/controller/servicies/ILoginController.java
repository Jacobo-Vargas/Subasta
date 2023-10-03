package co.edu.uniquindio.subasta.controller.servicies;

import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.model.Anunciante;

public interface ILoginController {

    public boolean validarAcceso(String cedula, String contrasenia);

}
