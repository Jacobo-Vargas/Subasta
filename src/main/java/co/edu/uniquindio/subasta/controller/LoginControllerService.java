package co.edu.uniquindio.subasta.controller;

import co.edu.uniquindio.subasta.controller.servicies.ILoginControllerService;

public class LoginControllerService implements ILoginControllerService {
    ModelFactoryController modelFactoryController;

    public LoginControllerService() {
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
