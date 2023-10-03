package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.ILoginController;
import co.edu.uniquindio.subasta.model.Anunciante;

public class LoginController implements ILoginController {
    ModelFactoryController modelFactoryController;
    Anunciante anuncianteLogueado;

    public LoginController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }


    @Override
    public boolean validarAcceso(String cedula, String contrasenia) {
        boolean acceso = false;
        for (Anunciante a: modelFactoryController.getSubasta().getListaAnunciante()) {
            if(a.getCedula().equals(cedula) && a.getUsuario().getContrasenia().equals(contrasenia)){
                acceso = true;
                break;
            }
        }
        if(acceso){
            return acceso;
        }else{
            System.out.println("no esta registrado");
            return acceso;
        }
    }

    @Override
    public Anunciante anuncianteLogueado(Anunciante anunciante) {
        return null;
    }
}
