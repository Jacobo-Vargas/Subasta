package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.AnuncioController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionAnunciosViewController {

    AnuncioController anuncioController;
    ObservableList<AnuncioDto> listaAnuncioDto = FXCollections.observableArrayList();
    ObservableList<PujaDto> listaPujasDto = FXCollections.observableArrayList();
    PujaDto pujaDtoSelecionado;
    AnuncioDto anuncioDtoSeleccionado;


    public TableColumn<PujaDto, String> tcNombreComprador;
    public TableColumn<PujaDto, String> tcFechaOferta;
    public TableColumn<PujaDto, String> tcCodigo;
    public TableColumn<PujaDto, String> tcOferta;
    public TableView<PujaDto> tvPujas;
    public Label lblDireccionComprador;
    public Label lblTelefonoComprador;
    public Label lblNombreComprador;
    public ComboBox<String> cBoxAnuncios;
    public ImageView imageViewFoto;

    public void initialize(){
        anuncioController = new AnuncioController();
        initView();
    }

    private void initView() {
        obtenerAnuncios();
        llenarComboBox();
        initDataBinding();
        listenerSelectionAnuncio();
        listenerSelectionPuja();
    }

    public void actualizarBox() {
        initialize();
    }

    public void elegirPuja() {
    }

    private void obtenerAnuncios() {
        listaAnuncioDto.clear();
        listaAnuncioDto.addAll(anuncioController.obtenerAnuncio());
    }
    private void obtenerPujas(ArrayList<PujaDto> listPujaDto){
        listaPujasDto.clear();
        listaPujasDto.addAll(listPujaDto);
        tvPujas.setItems(listaPujasDto);
        tvPujas.refresh();
    }


    private void llenarComboBox() {
        List<String> lista = listaAnuncioDto.stream()
                .map(AnuncioDto::nombre)
                .collect(Collectors.toList());

        cBoxAnuncios.setItems(FXCollections.observableArrayList(lista));
    }


    private void initDataBinding() {

       // tcNombreComprador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().codigo())));
        tcFechaOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaPuja())));
        tcOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
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

    private void listenerSelectionPuja() {
        tvPujas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pujaDtoSelecionado = newSelection;
            try {
                mostrarInformacionComprador(pujaDtoSelecionado);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void listenerSelectionAnuncio() {
        cBoxAnuncios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            for (AnuncioDto a : listaAnuncioDto) {
                if(a.nombre().equals(cBoxAnuncios.getValue())){
                    imageViewFoto.setImage(mostrarFoto(a.foto()));
                    obtenerPujas(a.listaPujas());
                }
            }
        });
    }
    private void mostrarInformacionComprador(PujaDto pujaDtoSeleccionada) throws MalformedURLException {
        if (pujaDtoSeleccionada != null) {

//            txtDescripcion.setText(pujaDtoSeleccionada.descripcion());
//            txtValorInicial.setText(String.format("%.0f",pujaDtoSeleccionada.valorInicial()));
//            txtCodigo.setText(String.valueOf(pujaDtoSeleccionada.codigo()));
//            dateInicio.setValue(LocalDate.parse(String.valueOf(pujaDtoSeleccionada.fechaPublicacion())));
//            dateFin.setValue(LocalDate.parse(String.valueOf(pujaDtoSeleccionada.fechaTerminacion())));
//            cBoxProducto.setValue(pujaDtoSeleccionada.producto().nombre());
//            txtNombrePublicacion.setText(pujaDtoSeleccionada.nombre());
//            imageViewFoto.setImage(mostrarFoto(pujaDtoSeleccionada.foto()));
        }
    }
}

