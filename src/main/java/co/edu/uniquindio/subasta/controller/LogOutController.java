package co.edu.uniquindio.subasta.controller;

public class LogOutController {
    ModelFactoryController modelFactoryController;

    public LogOutController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }
    public boolean salirDeApp(){
        return modelFactoryController.restaurarLogueo();
    }
}
