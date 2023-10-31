package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.AnuncioController;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.model.Anuncio;
import co.edu.uniquindio.subasta.model.Producto;
import co.edu.uniquindio.subasta.util.AlertaUtil;
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
import javafx.util.converter.LocalDateStringConverter;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.ArrayList;
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
    private TableColumn<AnuncioDto, String> tcCodigo;

    @FXML
    private TableColumn<AnuncioDto, String> tcFechaFin;

    @FXML
    private TableColumn<AnuncioDto, String> tcFechaInicio;

    @FXML
    private TableColumn<AnuncioDto, String> tcNombreProducto;

    @FXML
    private TableColumn<AnuncioDto, String> tcValorInicial;

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
    void agregarAnuncio(ActionEvent event) throws AnuncioException {
        ProductoDto productoDtoAsociado = null;

        for (ProductoDto p : listaProductos) {
            if (p.nombre().equals(cBoxProducto.getValue())) {
                productoDtoAsociado = new ProductoDto(p.nombre(), p.tipoArticulo(), p.codigo());
                break;
            }
        }

        AnuncioDto anuncioDto = crearAnuncioDto(productoDtoAsociado);

        if(anuncioController.agregarAnuncio(anuncioDto)){
            listaAnuncioDto.add(anuncioDto);
            tvAnuncios.refresh();
        }else {
            AlertaUtil.mostrarMensajeError("Tiene 3 Anuncios activos");
        }
    }

    @FXML
    void anuncioNuevo(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void eliminarAnuncio(ActionEvent event) {

    }

    public String recuperarNombreAnunciante() {
        return anuncioController.recuperarNombre();
    }

    public void initialize() {
        anuncioController = new AnuncioController();
        initView();
    }

    private void initView() {
        obtenerAnuncios();
        obtenerProductos();
        llenarComboBox();
        initDataBinding();
        listenerSelection();
    }

    private void initDataBinding() {

        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().codigo())));
        tcFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaTerminacion())));
        tcFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaPublicacion())));
        tcNombreProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().producto().nombre()));
        tcValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().valorInicial())));
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
        tvAnuncios.setItems(listaAnuncioDto);
        tvAnuncios.refresh();
    }

    private void limpiarCampos() {
        txtDescripcion.setText("");
        txtValorInicial.setText("");
        dateFin.setValue(null);
        dateInicio.setValue(null);
        cBoxProducto.setValue("Producto");
        txtCodigo.setText("");
        txtNombrePublicacion.setText("");
    }

    private void listenerSelection() {
        tvAnuncios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            anuncioDtoSelecionado = newSelection;
            mostrarInformacionAnuncio(anuncioDtoSelecionado);
        });
    }

    private void mostrarInformacionAnuncio(AnuncioDto anuncioDtoSelecionado) {
        if (anuncioDtoSelecionado != null) {
            txtDescripcion.setText(anuncioDtoSelecionado.descripcion());
            txtValorInicial.setText(String.valueOf(anuncioDtoSelecionado.valorInicial()));
            txtCodigo.setText(String.valueOf(anuncioDtoSelecionado.codigo()));
            dateInicio.setValue(LocalDate.parse(String.valueOf(anuncioDtoSelecionado.fechaPublicacion())));
            dateFin.setValue(LocalDate.parse(String.valueOf(anuncioDtoSelecionado.fechaTerminacion())));
            cBoxProducto.setValue(anuncioDtoSelecionado.producto().nombre());
        }
    }

    private AnuncioDto crearAnuncioDto(ProductoDto productoDtoAsociado) {
        return new AnuncioDto(productoDtoAsociado, txtNombrePublicacion.getText(),
                txtDescripcion.getText(), "src/main/resources/imagenes app/logo.png",
                recuperarNombreAnunciante(), String.valueOf(dateInicio.getValue()), String.valueOf(dateFin.getValue()),
                Float.parseFloat(txtValorInicial.getText()), txtCodigo.getText(), new ArrayList<>());
    }
}

