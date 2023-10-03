package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.SubastaAnuncianteApp;
import co.edu.uniquindio.subasta.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
            mostrarSubastaAnuncianteApp();
        }else{
            mostrarMensajeConfirmacion("No se encontro el usuario");
        }
    }
    private void mostrarSubastaAnuncianteApp() {
        try {
            // Para este ejemplo, supondré que tienes una clase SubastaApp y un método start para mostrar la ventana
            SubastaAnuncianteApp subastaApp = new SubastaAnuncianteApp();
            subastaApp.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace(); // Maneja adecuadamente las excepciones
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

    public void recuperarPass(ActionEvent actionEvent) {
    }

    public void confirmarRegistro(ActionEvent actionEvent) {
    }
}
