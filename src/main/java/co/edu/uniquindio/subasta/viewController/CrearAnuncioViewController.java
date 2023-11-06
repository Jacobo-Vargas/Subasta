package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.AnuncioController;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.util.AlertaUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CrearAnuncioViewController{

    public static int codigo = 0;

    AnuncioController anuncioController;
    File fileGlobal;

    String nombreFoto;

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
    private ImageView imageViewFoto;

    @FXML
    public void actualizarBox() {
        initialize();
    }

    @FXML
    void buscarFoto() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        fileGlobal = fileChooser.showOpenDialog(new Stage());
        if (fileGlobal != null) {
            copiarArchivo(fileGlobal.toPath());
            Image image = new Image(fileGlobal.toURI().toString());
            imageViewFoto.setImage(image);
        }
    }

    @FXML
    void actualizarAnuncio() throws AnuncioException {
        AnuncioDto anuncioDto = new AnuncioDto(anuncioDtoSelecionado.producto(), txtNombrePublicacion.getText(),
                txtDescripcion.getText(), nombreFoto, recuperarNombreAnunciante(), String.valueOf(dateInicio.getValue()), String.valueOf(dateFin.getValue()),
                Double.parseDouble(txtValorInicial.getText()), txtCodigo.getText(), new ArrayList<>());
        if (anuncioController.actualizarAnuncio(anuncioDto)) {
            AlertaUtil.mostrarMensajeOk("Se actualizo con éxito.");
            listaAnuncioDto.remove(anuncioDtoSelecionado);
            listaAnuncioDto.add(anuncioDto);
            tvAnuncios.refresh();
            limpiarCampos();

        } else {
            AlertaUtil.mostrarMensajeError("No se pudo actualizar, intente más tarde.");
        }
    }

    @FXML
    void agregarAnuncio() throws AnuncioException {
        AnuncioDto anuncioDto = crearAnuncioDto(armarProductoAcosiado());
        int opcion = anuncioController.agregarAnuncio(anuncioDto);
        switch (opcion) {
            case 1:
                AlertaUtil.mostrarMensajeAlerta("Ya existe el codigo.");
                break;
            case 2:
                AlertaUtil.mostrarMensajeAlerta("Ya tiene 3 anuncios activos.");
                break;
            case 3:
                listaAnuncioDto.add(anuncioDto);
                tvAnuncios.refresh();
                AlertaUtil.mostrarMensajeOk("Se agrego con éxito.");
                break;
        }
    }

    @FXML
    void anuncioNuevo() {
        limpiarCampos();
    }

    @FXML
    void eliminarAnuncio() throws AnuncioException {
        if (anuncioController.eliminarAnuncio(anuncioDtoSelecionado)) {
            AlertaUtil.mostrarMensajeOk("Se elimino con éxito.");
            listaAnuncioDto.remove(anuncioDtoSelecionado);
            anuncioDtoSelecionado = null;
            tvAnuncios.getSelectionModel().clearSelection();
            limpiarCampos();
        } else {
            AlertaUtil.mostrarMensajeError("No se puedo eliminar, intente más tarde.");
        }

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
        tcValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().valorInicial())));

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
            try {
                mostrarInformacionAnuncio(anuncioDtoSelecionado);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void mostrarInformacionAnuncio(AnuncioDto anuncioDtoSelecionado) throws MalformedURLException {
        if (anuncioDtoSelecionado != null) {
            txtDescripcion.setText(anuncioDtoSelecionado.descripcion());
            txtValorInicial.setText(String.format("%.2f",anuncioDtoSelecionado.valorInicial()));
            txtCodigo.setText(String.valueOf(anuncioDtoSelecionado.codigo()));
            dateInicio.setValue(LocalDate.parse(String.valueOf(anuncioDtoSelecionado.fechaPublicacion())));
            dateFin.setValue(LocalDate.parse(String.valueOf(anuncioDtoSelecionado.fechaTerminacion())));
            cBoxProducto.setValue(anuncioDtoSelecionado.producto().nombre());
            txtNombrePublicacion.setText(anuncioDtoSelecionado.nombre());
            imageViewFoto.setImage(mostrarFoto(anuncioDtoSelecionado.foto()));
        }
    }

    private AnuncioDto crearAnuncioDto(ProductoDto productoDtoAsociado) {
        return new AnuncioDto(productoDtoAsociado, txtNombrePublicacion.getText(),
                txtDescripcion.getText(), nombreFoto,
                recuperarNombreAnunciante(), String.valueOf(dateInicio.getValue()), String.valueOf(dateFin.getValue()),
                Float.parseFloat(txtValorInicial.getText()), txtCodigo.getText(), new ArrayList<>());
    }

    private ProductoDto armarProductoAcosiado() {

        ProductoDto productoDtoAsociado = null;

        for (ProductoDto p : listaProductos) {
            if (p.nombre().equals(cBoxProducto.getValue())) {
                productoDtoAsociado = new ProductoDto(p.nombre(), p.tipoArticulo(), p.codigo());
                break;
            }
        }
        return productoDtoAsociado;
    }

    private Image mostrarFoto(String nombreArchivo) {
        /*
         * esto crea un arreglo de archivos que esten en la carpeta mencionada
         *  luego itera la lista y si encuentra uno con el mismo nombre lo retorna
         */
        Image imagen = null;
        File carpeta = new File("src/main/resources/imagenesAnuncios/");

        File[] archivos = carpeta.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().equals(nombreArchivo)) {
                    String rutaImagen = archivo.toURI().toString();
                    imagen = new Image(rutaImagen);
                }
            }
        }
        return imagen;
    }

    public void copiarArchivo(Path source) {
        nombreFoto = generarNombre();
        try {
            Path destinoPath = Paths.get("src/main/resources/imagenesAnuncios/", nombreFoto );
            Files.createFile(destinoPath);
            Files.copy(source, destinoPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.getMessage();
        }
    }

    private String generarNombre() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaActual = dateFormat.format(new Date());
        return fileGlobal.getName().replace(".", "_" + fechaActual + ".");
    }

}