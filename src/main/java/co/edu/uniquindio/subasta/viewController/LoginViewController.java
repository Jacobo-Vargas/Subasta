package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.LoginController;
import co.edu.uniquindio.subasta.controller.ModelFactoryController;
import co.edu.uniquindio.subasta.controller.ProductoController;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.InputStream;

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
        if(validarAcceso()){
            loginController.
        }
    }
}
