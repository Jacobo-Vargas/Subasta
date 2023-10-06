package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.ILoginController;

public class LoginController implements ILoginController {
    ModelFactoryController modelFactoryController;

    public LoginController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }


    @Override
    public boolean validarAccesoAnunciante(String cedula, String contrasenia) {
        return modelFactoryController.verificarAccesoAnunciante(cedula,contrasenia);
    }
    @Override
    public boolean validarAccesoComprador(String cedula, String contrasenia) {
        return modelFactoryController.verificarAccesoComprador(cedula,contrasenia);
    }
}
