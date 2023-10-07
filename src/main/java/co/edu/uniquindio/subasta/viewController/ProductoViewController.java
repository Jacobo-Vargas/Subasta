package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.ProductoController;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.TipoArticulo;
import co.edu.uniquindio.subasta.util.AlertaUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductoViewController {
    public static int codigo = 2;
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
        obtenerProducto();
        initDataBinding();
        tvProducto.getItems().clear();
        tvProducto.setItems(listaProductoDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcTipoArticulo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().tipoArticulo())));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));

    }

    private void obtenerProducto() {
        listaProductoDto.addAll(productoController.obtenerProducto());
    }


    public void agregarProducto() {
        codigo +=1;
        ProductoDto productoDto = new ProductoDto(txtNombre.getText(), String.valueOf(cBoxTipoArticulo.getValue()),String.valueOf(codigo));
        productoController.agregarProducto(productoDto);
        listaProductoDto.add(productoDto);
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
        if(productoController.eliminarProducto(productoSeleccionado)){
            AlertaUtil.mostrarMensajeOk("Se elimino con éxito");
            listaProductoDto.remove(productoSeleccionado);
            productoSeleccionado = null;
            tvProducto.getSelectionModel().clearSelection();
            limpiarCampos();
        }

    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        cBoxTipoArticulo.setPromptText("null");
    }

    private void listenerSelection() {
        tvProducto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionProducto(productoSeleccionado);
        });
    }

    private void mostrarInformacionProducto(ProductoDto productoSeleccionado) {
        if(productoSeleccionado != null){
            txtNombre.setText(productoSeleccionado.nombre());
            txtCodigo.setText(String.valueOf(productoSeleccionado.codigo()));
            cBoxTipoArticulo.setValue(TipoArticulo.valueOf(productoSeleccionado.tipoArticulo()));
        }
    }

}