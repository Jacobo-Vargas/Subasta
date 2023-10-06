package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.LogOutController;
import co.edu.uniquindio.subasta.util.AlertaUtil;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LogOutViewController {
    LogOutController logOutController;

    public void initialize(){
        logOutController = new LogOutController();

    }
    public void salirApp(ActionEvent actionEvent) {
        if(logOutController.salirDeApp()){
            cerrarVentana(actionEvent);
            AlertaUtil.mostrarMensajeOk("Sesion cerrada correctamente.");

        }else{
            AlertaUtil.mostrarMensajeError("No se pudo cerrar sesión, intentelo nuevamente.");
        }
    }

    private void cerrarVentana(ActionEvent event) {         // metodo para recuperar el stage actual y cerrarlo
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
