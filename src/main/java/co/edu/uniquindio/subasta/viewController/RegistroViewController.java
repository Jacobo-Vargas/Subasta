package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.ProductoController;
import co.edu.uniquindio.subasta.controller.RegistroController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;
import co.edu.uniquindio.subasta.model.Usuario;
import co.edu.uniquindio.subasta.util.AlertaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class RegistroViewController {


    // este es el mensajero
    RegistroController registroControllerService;
    @FXML
    private CheckBox chkAnunciante;

    @FXML
    private CheckBox chkComprador;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;


    @FXML
    void initialize() {
        registroControllerService = new RegistroController();
    }

    @FXML
    void confirmarRegistro(ActionEvent event) {
        if (!(chkComprador.isSelected() || chkAnunciante.isSelected())) {
            AlertaUtil.mostrarMensajeError("Seleccione si es anunciante o comprador.");
        } else if (validarSeleccion()) {
            registrarAnunciante(crearAnunciante());
        } else {
            registrarComprador(crearComprador());
        }


    }

    private void registrarComprador(CompradorDto compradorDto) {
        registroControllerService.registrarComprador(compradorDto);
    }

    private void registrarAnunciante(AnuncianteDto anuncianteDto) {
        registroControllerService.registrarAnunciante(anuncianteDto);
    }


    public AnuncianteDto crearAnunciante() {
        Usuario u = new Usuario();
        u.setUser(txtCedula.getText());
        u.setContrasenia(txtContrasenia.getText());


        return new AnuncianteDto(
                txtNombre.getText(),
                txtApellido.getText(),
                txtCedula.getText(),
                Integer.parseInt(txtEdad.getText()),
                txtTelefono.getText(),
                u,
                new ArrayList<>(),
                new ArrayList<>());

    }

    public CompradorDto crearComprador() {

        Usuario u = new Usuario();
        u.setUser(txtCedula.getText());
        u.setContrasenia(txtContrasenia.getText());


        return new CompradorDto(
                txtNombre.getText(),
                txtApellido.getText(),
                txtCedula.getText(),
                Integer.parseInt(txtEdad.getText()),
                txtTelefono.getText(),
                u,
                txtDireccion.getText(),
                new ArrayList<>());
    }

    public boolean validarSeleccion() {
        boolean seleccion = false;
        if (chkAnunciante.isSelected()) {
            seleccion = true;
        } else if (chkComprador.isSelected()) {
            chkAnunciante.isDisable();
        }
        return seleccion;
    }

    public void seleccionComprador(ActionEvent actionEvent) {
        if (chkComprador.isSelected()) {
            chkAnunciante.setDisable(true);
        } else {
            chkAnunciante.setDisable(false);
        }
    }

    public void seleccionAnunciante(ActionEvent actionEvent) {
        if (chkAnunciante.isSelected()) {
            chkComprador.setDisable(true);
        } else {
            chkComprador.setDisable(false);
        }
    }
}