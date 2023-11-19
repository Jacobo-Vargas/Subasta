package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.SubastaAnuncianteApp;
import co.edu.uniquindio.subasta.SubastaCompradorApp;
import co.edu.uniquindio.subasta.controller.LoginControllerService;
import co.edu.uniquindio.subasta.util.AlertaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


public class LoginViewController extends Component {
    LoginControllerService loginController;
    @FXML
    public TextField txtUser;
    @FXML
    public TextField txtPassword;

    @FXML
    public void initialize() {
        loginController = new LoginControllerService();

    }

    public boolean validarAccesoAnunciante(){
      return loginController.validarAccesoAnunciante(txtUser.getText(),txtPassword.getText());
    }

    public boolean validarAccesoComprador(){
        return loginController.validarAccesoComprador(txtUser.getText(),txtPassword.getText());
    }

    public void ingresar() {
        mostrarVentanaConfirmacion();
    }

    private void mostrarSubastaCompradorApp() {
        try {
            SubastaCompradorApp compardorApp = new SubastaCompradorApp();
            compardorApp.start(new Stage());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarSubastaAnuncianteApp() {
        try {
            SubastaAnuncianteApp subastaApp = new SubastaAnuncianteApp();
            subastaApp.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recuperarPass(ActionEvent actionEvent) {
    }
    private void mostrarVentanaConfirmacion() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Quieres ingresar como comprador o anunciante?");
        alert.setContentText("Elija una opción:");

        ButtonType buttonComprador = new ButtonType("Comprador");
        ButtonType buttonAnunciante = new ButtonType("Anunciante");

        alert.getButtonTypes().setAll(buttonComprador, buttonAnunciante);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(result -> {
            if (result == buttonComprador) {
                if (validarAccesoComprador()) {
                    mostrarSubastaCompradorApp();
                } else {
                    AlertaUtil.mostrarMensajeError("No se encuentra el usuario para ingreso como comprador.");
                }
            } else if (result == buttonAnunciante) {
                if (validarAccesoAnunciante()) {
                    mostrarSubastaAnuncianteApp();
                } else {
                    AlertaUtil.mostrarMensajeError("No se encuentra el usuario para ingreso como anunciante.");
                }
            }
        });
    }
}
