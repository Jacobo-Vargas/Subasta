package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.ILoginController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;
import co.edu.uniquindio.subasta.model.Anunciante;

public class LoginController implements ILoginController {
    ModelFactoryController modelFactoryController;
    AnuncianteDto anuncianteLogueado;
    CompradorDto compradorDtoLogueado;

    public LoginController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }


    @Override
    public boolean validarAcceso(String cedula, String contrasenia) {
        Anunciante encontrado = null;
        boolean acceso = false;

        for (Anunciante a: modelFactoryController.getSubasta().getListaAnunciante()) {
            if(a.getCedula().equals(cedula) && a.getUsuario().getContrasenia().equals(contrasenia)){
                encontrado = a;
                modelFactoryController.getSubasta().setAnuncianteLogueado(a);
                acceso = true;
            }
        }
        anuncianteLogueado = modelFactoryController.mapper.anuncianteToAnuncianteDto(encontrado);
        return acceso;
    }
}
