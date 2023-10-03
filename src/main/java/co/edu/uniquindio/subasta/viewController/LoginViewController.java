package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.MainApp;
import co.edu.uniquindio.subasta.SubastaAnuncianteApp;
import co.edu.uniquindio.subasta.controller.LoginController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.util.SubastaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
            SubastaUtil
        }
    }
}
