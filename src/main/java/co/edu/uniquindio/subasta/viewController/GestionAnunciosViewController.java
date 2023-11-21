package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.AnuncioController;
import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.controller.RabbitController;
import co.edu.uniquindio.subasta.exceptions.AnuncioException;
import co.edu.uniquindio.subasta.exceptions.CompradorException;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.CompradorDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.util.AlertaUtil;
import co.edu.uniquindio.subasta.util.GenerarReportePdf;
import co.edu.uniquindio.subasta.util.Persistencia;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionAnunciosViewController {

    RabbitController rabbitController;

    PujaDto pujaDtoSelecionado;
    CompradorDto compradorDtoSeleccionado;
    AnuncioDto anuncioDtoSeleccionado;
    AnuncioController anuncioController;
    ArrayList<CompradorDto> listaCompradores = new ArrayList<>();
    ObservableList<AnuncioDto> listaAnuncioDto = FXCollections.observableArrayList();
    ObservableList<PujaDto> listaPujasDto = FXCollections.observableArrayList();


    public TableColumn<CompradorDto, String> tcNombreComprador;
    public TableColumn<PujaDto, String> tcFechaOferta;
    public TableColumn<PujaDto, String> tcCodigo;
    public TableColumn<PujaDto, String> tcOferta;
    public TableView<PujaDto> tvPujas;
    public Label lblDireccionComprador;
    public Label lblTelefonoComprador;
    public Label lblNombreComprador;
    public Label lblCedula;
    public ComboBox<String> cBoxAnuncios;
    public ImageView imageViewFoto;

    public void initialize() {
        anuncioController = new AnuncioController();
        rabbitController = new RabbitController();
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
        if(pujaDtoSelecionado != null){
            boolean  bandera = false;
            for (AnuncioDto a: listaAnuncioDto) {
                if(a.codigo().equals(anuncioDtoSeleccionado.codigo())){
                    try {
                        if(anuncioController.eliminarAnuncio(a)){
                            GenerarReportePdf.generarPDFVenta(pujaDtoSelecionado,a,compradorDtoSeleccionado);
                            AlertaUtil.mostrarMensajeOk("Se ha generado reporte\n se ha elegido con exito.");
                            rabbitController.enviarMensajeRabbit(compradorDtoSeleccionado.cedula(),anuncioDtoSeleccionado.toString());
                            bandera = true;

                            break;
                        }
                    } catch (AnuncioException e) {
                        AlertaUtil.mostrarMensajeError("No se pudo escoger la puja");
                    } catch (IOException e) {
                        System.out.println("Error generando pdf.");
                    }
                }
            }
            if(!bandera){
                AlertaUtil.mostrarMensajeError("No se pudo escoger la puja, intente más tarde.");
            }else{
                System.out.println("No hay pujas para mostrar");
            }
        }

    }

    private void obtenerAnuncios() {
        listaAnuncioDto.clear();
        listaAnuncioDto.addAll(anuncioController.obtenerAnuncio());
    }

    private void obtenerPujas(ArrayList<PujaDto> listPujaDto) {
        listaPujasDto.clear();
        listaPujasDto.addAll(listPujaDto);
        tvPujas.setItems(listaPujasDto);
        tvPujas.refresh();
    }

    private void obtenerCompradores() {
        try {
            listaCompradores.clear();
            listaCompradores.addAll(anuncioController.obtenerCompradores());
        } catch (CompradorException e) {
            System.out.println("Error obteniendo compradores.");
        }
    }


    private void llenarComboBox() {
        List<String> lista = listaAnuncioDto.stream()
                .map(AnuncioDto::nombre)
                .collect(Collectors.toList());

        cBoxAnuncios.setItems(FXCollections.observableArrayList(lista));
    }


    private void initDataBinding() {
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
        File carpeta = new File(Persistencia.RUTA_FOTOS_SUBASTA);

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
            try {
                pujaDtoSelecionado = newSelection;
                if (pujaDtoSelecionado != null) {
                    obtenerCompradores();
                    for (CompradorDto c : listaCompradores) {
                        if (c.cedula().equals(pujaDtoSelecionado.cedulaComprador())) {
                            mostrarInformacionComprador(c);
                            compradorDtoSeleccionado = c;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error en listener puja");
            }
        });
    }

    private void listenerSelectionAnuncio() {
        cBoxAnuncios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            for (AnuncioDto a : listaAnuncioDto) {
                if (a.nombre().equals(cBoxAnuncios.getValue())) {
                    anuncioDtoSeleccionado = a;
                    imageViewFoto.setImage(mostrarFoto(a.foto()));
                    obtenerPujas(a.listaPujas());
                    break;
                }

            }
        });
    }

    private void mostrarInformacionComprador(CompradorDto compradorDto) {
        if (compradorDto != null) {
            lblDireccionComprador.setText(compradorDto.direccion());
            lblNombreComprador.setText(compradorDto.nombre());
            lblTelefonoComprador.setText(compradorDto.telefono());
            lblCedula.setText(compradorDto.cedula());

        } else {
            System.out.println();
        }
    }
}

