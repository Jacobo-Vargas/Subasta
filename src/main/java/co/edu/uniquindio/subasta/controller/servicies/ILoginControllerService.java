package co.edu.uniquindio.subasta.controller.servicies;

public interface ILoginControllerService {

    public boolean validarAccesoAnunciante(String cedula, String contrasenia);
    public boolean validarAccesoComprador(String cedula, String contrasenia);

}
