package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.AnuncioController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.Anuncio;
import co.edu.uniquindio.subasta.model.Producto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.List;
import java.util.stream.Collectors;

public class CrearAnuncioViewController {

    public static int codigo = 0;

    AnuncioController anuncioController;

    ObservableList<AnuncioDto> listaAnuncioDto = FXCollections.observableArrayList();

    ObservableList<ProductoDto> listaProductos = FXCollections.observableArrayList();
    AnuncioDto anuncioDtoSelecionado;

    @FXML
    private ComboBox<String> cBoxProducto;

    @FXML
    private DatePicker dateFin;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private TableColumn<Anuncio, String> tcCodigo;

    @FXML
    private TableColumn<Anuncio, String> tcFechaFin;

    @FXML
    private TableColumn<Anuncio, String> tcFechaInicio;

    @FXML
    private TableColumn<Anuncio, String> tcNombreProducto;

    @FXML
    private TableColumn<Anuncio, String> tcValorInicial;

    @FXML
    private TableView<AnuncioDto> tvAnuncios;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtValorInicial;

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombrePublicacion;

    @FXML
    public void actualizarBox(javafx.scene.input.MouseEvent mouseEvent) {
        initialize();
    }

    @FXML
    void actualizarAnuncio(ActionEvent event) {

    }

    @FXML
    void agregarAnuncio(ActionEvent event) {
        ProductoDto productoDtoAsociado = null;
        for (ProductoDto p: listaProductos) {
            if(p.nombre().equals(cBoxProducto.getValue())){
                 productoDtoAsociado = new ProductoDto(p.nombre(),p.tipoArticulo(), p.codigo());
            }
        }
//        AnuncioDto anuncioDto = new AnuncioDto(productoDtoAsociado,txtNombrePublicacion.getText(),
//                txtDescripcion.getText(),)
    }

    @FXML
    void anuncioNuevo(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void eliminarAnuncio(ActionEvent event) {

    }

    public void initialize() {
        anuncioController = new AnuncioController();
        initView();
    }

    private void initView() {
        obtenerProductos();
        llenarComboBox();
        initDataBinding();
        listenerSelection();
    }

    private void initDataBinding() {

        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCodigo())));
        tcFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaTerminacion())));
        tcFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaPublicacion())));
        tcNombreProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProducto().getNombre()));
        tcValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValorInicial())));
    }

    private void llenarComboBox() {
        List<String> lista = listaProductos.stream()
                .map(ProductoDto::nombre)
                .collect(Collectors.toList());

        cBoxProducto.setItems(FXCollections.observableArrayList(lista));
    }

    private void obtenerProductos() {
        listaProductos.clear();
        listaProductos.addAll(anuncioController.obtenerProducto());
    }

    private void obtenerAnuncios() {
        listaAnuncioDto.clear();
        listaAnuncioDto.addAll(anuncioController.obtenerAnuncio());
    }

    private void limpiarCampos() {
        txtDescripcion.setText("");
        txtValorInicial.setText("");
        dateFin.setValue(LocalDate.parse(""));
        dateInicio.setValue(LocalDate.parse(""));
        cBoxProducto.setValue("Producto");
        txtCodigo.setText("");
    }

    private void listenerSelection() {
        tvAnuncios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            anuncioDtoSelecionado = newSelection;
            mostrarInformacionAnuncio(anuncioDtoSelecionado);
        });
    }

    private void mostrarInformacionAnuncio(AnuncioDto anuncioDtoSelecionado) {

        txtDescripcion.setText(anuncioDtoSelecionado.descripcion());
        txtValorInicial.setText(String.valueOf(anuncioDtoSelecionado.valorInicial()));
        txtCodigo.setText(String.valueOf(anuncioDtoSelecionado.codigo()));
        dateInicio.setChronology(Chronology.from(anuncioDtoSelecionado.fechaPublicacion()));
        dateFin.setChronology(Chronology.from(anuncioDtoSelecionado.fechaFinPublicacion()));
        cBoxProducto.setValue(anuncioDtoSelecionado.productoDto().nombre());
    }
}

