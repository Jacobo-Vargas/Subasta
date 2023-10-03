package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.ProductoController;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.TipoArticulo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class ProductoViewController {
    private static int codigo = 0;
    ProductoController productoController;
    ObservableList<ProductoDto> listaProductoDto = FXCollections.observableArrayList();

    ProductoDto productoSeleccionado;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtCodigo;
    @FXML
    public ComboBox<TipoArticulo> cBoxTipoArticulo;


    @FXML
    public TableView<ProductoDto> tvProducto;
    @FXML
    public TableColumn<ProductoDto, String> tcNombre;
    @FXML
    public TableColumn<ProductoDto, String> tcCodigo;
    @FXML
    public TableColumn<ProductoDto, String> tcTipoArticulo;

    @FXML
    void initialize() {
        productoController = new ProductoController();
        intiView();
    }

    private void intiView() {
        llenarComboBox();
        initDataBinding();
        obtenerProducto();
        tvProducto.getItems().clear();
        tvProducto.setItems(listaProductoDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcTipoArticulo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().tipoArticulo())));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().codigoProducto())));

    }

    private void obtenerProducto() {
        listaProductoDto.addAll(productoController.obtenerProducto());
    }


    public void agregarProducto() {
        codigo += 1;

        ProductoDto productoDto = new ProductoDto(txtNombre.getText(), cBoxTipoArticulo.getValue(), codigo);
        productoController.agregarProducto(productoDto, productoController.obtenerCedulaLogueo() );

        listaProductoDto.add(productoDto);
        tvProducto.setItems(listaProductoDto);
        tvProducto.refresh();


    }

    public void llenarComboBox() {
        cBoxTipoArticulo.getItems().addAll(TipoArticulo.values());
    }

    public void nuevo() {
        txtCodigo.clear();
        txtNombre.clear();
    }

    public void actualizarProducto() {
    }

    public void eliminarProducto() {
        productoController.eliminarProducto(String.valueOf(productoSeleccionado.codigoProducto()));
        boolean productoEliminado = false;
//        if (productoSeleccionado != null) {
//            if (mostrarMensajeConfirmacion("¿Estas seguro de elmininar al empleado?")) {
//                productoEliminado = productoController.eliminarProducto(String.valueOf(productoSeleccionado.codigoProducto()));
//                if (productoEliminado) {
//                    listaProductoDto.remove(productoSeleccionado);
//                    productoSeleccionado = null;
//                    tvProducto.getSelectionModel().clearSelection();
//                    nuevo();
//                    mostrarMensaje("Notificación empleado", "Empleado eliminado", "El empleado se ha eliminado con éxito", Alert.AlertType.INFORMATION);
//                } else {
//                    mostrarMensaje("Notificación empleado", "Empleado no eliminado", "El empleado no se puede eliminar", Alert.AlertType.ERROR);
//                }
//            }
//        } else {
//            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Seleccionado un empleado de la lista", Alert.AlertType.WARNING);
//        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private void listenerSelection() {
        tvProducto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionEmpleado(productoSeleccionado);
        });
    }

    private void mostrarInformacionEmpleado(ProductoDto productoSeleccionado) {
        if(productoSeleccionado != null){
            txtNombre.setText(productoSeleccionado.nombre());
            txtCodigo.setText(String.valueOf(productoSeleccionado.codigoProducto()));
        }
    }
}