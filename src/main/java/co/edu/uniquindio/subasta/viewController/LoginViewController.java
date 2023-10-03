package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.SubastaAnuncianteApp;
import co.edu.uniquindio.subasta.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

public class LoginViewController {
    LoginController loginController;
    @FXML
    public TextField txtUser;
    @FXML
    public TextField txtPassword;

    @FXML
    public void initialize() {
        loginController = new LoginController();

    }

    public boolean validarAcceso(){
      return loginController.validarAcceso(txtUser.getText(),txtPassword.getText());
    }


    public void ingresar() {
        validarAcceso();
        if (validarAcceso()){
            SubastaAnuncianteApp.launch();
        }else{
            mostrarMensajeConfirmacion("No se encontro el usuario");
        }
    }

    public void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    public void mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
        } else {
        }
    }
}
