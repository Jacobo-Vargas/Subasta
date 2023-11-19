package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.ProductoDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Anuncio;
import co.edu.uniquindio.subasta.model.Puja;
import co.edu.uniquindio.subasta.util.AlertaUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
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

public class PujaViewController {
    @FXML
    public TableColumn<AnuncioDto, String> tableColumnValorInicial;
    @FXML
    public TableColumn<AnuncioDto, String> tableColumnTipoProducto;
    @FXML
    public TableColumn<AnuncioDto, String> NombreProducto;
    @FXML
    public TableView<AnuncioDto> TablaInformacioAnunioc;
    ObservableList<AnuncioDto> anunciosMostrar = FXCollections.observableArrayList();

    ////para mostrar en la tabla de anuncio y complementar la informacion



    PujaController pujaController;
    ObservableList<PujaDto> listaPuja = FXCollections.observableArrayList();

    ObservableList<AnuncioDto> listaAnuncio = FXCollections.observableArrayList();

    String codigoAnuncio;
    @FXML
    public TextField textFieldCodigo;
    @FXML
    public ComboBox<String> comboBoxAnuncio;
    @FXML
    public TextField textFieldValorInicial;
    @FXML
    public TextField textFieldDireccion;
    @FXML
    public Button botonPuja;
    @FXML
    public TableView<PujaDto> tableViewTabla;
    @FXML
    public TableColumn<PujaDto, String> tableColumnCodigo;

    @FXML
    public TableColumn<PujaDto, String> tableColumnDireccion;

    @FXML
    public TableColumn<PujaDto, String> tableColumnValorPuja;
    @FXML
    public TableColumn<PujaDto, String> tableColumnFecha;
    PujaDto pujaDtoSelecionado;

    ////////para mostrar la foto
    @FXML
    File fileGlobal;
    @FXML
    String nombreFoto;

    @FXML
    public ImageView imagenView;





    @FXML
    void initialize() {
        pujaController = new PujaController();
        initView();
    }

    void initView() {
        llenarCombox();
        obtenerAnuncios();
        obtenerPujas();
        initDataBinding();
        listenerSelection();
    }

    private void listenerSelection() {
        tableViewTabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pujaDtoSelecionado = newSelection;
            mostrarInformacionAnuncio(pujaDtoSelecionado);
        });

    }
    //para mostrar la foto
    public void buscarFoto(){
        FileChooser fileChooser =new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        fileGlobal =fileChooser.showOpenDialog(new Stage());
        if(fileGlobal!=null){
            copiarArchivo(fileGlobal.toPath());
            Image image=new Image(fileGlobal.toURI().toString());
            imagenView.setImage(image);
        }
    }

    private Image mostrarFoto(String nombreArchivo) {
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

    private void copiarArchivo(Path source) {
        nombreFoto=generarNombre();
        try{
            Path destinoPath = Paths.get("src/main/resources/imagenesAnuncios/", nombreFoto );
            Files.createFile(destinoPath);
            Files.copy(source, destinoPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public String generarNombre(){
        SimpleDateFormat dateFoarmat= new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaActual= dateFoarmat.format(new Date());
        return fileGlobal.getName().replace(".", "_" + fechaActual + ".");

    }

    private void mostrarInformacionAnuncio(PujaDto pujaDtoSelecionado) {
        if (pujaDtoSelecionado != null) {
            AnuncioDto anuncio = listaAnuncio.stream()
                    .filter(an -> an.codigo().equals(pujaDtoSelecionado.codigoAnuncio()))
                    .findFirst().orElse(null);

            if (anuncio != null) {
                textFieldValorInicial.setText(String.valueOf(pujaDtoSelecionado.oferta()));
                textFieldCodigo.setText(pujaDtoSelecionado.codigo());
                comboBoxAnuncio.setValue(anuncio.nombre());

                Image imagen = mostrarFoto(anuncio.foto());
                if (imagen != null ) {
                    imagenView.setImage(imagen);
                }else if(imagen==null){
                    //preguntar a jacobo
                } else {
                    // La imagen es nula o imageViewFoto es nulo
                    // Puedes mostrar un mensaje de error o manejar la situación según sea necesario
                    System.out.println("La imagen es nula o imageViewFoto no está inicializado.");
                }
            } else {
                // anuncio es nulo
                // Puedes mostrar un mensaje de error o manejar la situación según sea necesario
                System.out.println("Anuncio no encontrado.");
            }
        }
    }

    private void initDataBinding() {
        tableColumnDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccion()));
        tableColumnCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().codigo())));
        tableColumnValorPuja.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
        tableColumnFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaPuja())));
        tableColumnValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().valorInicial())));
        tableColumnTipoProducto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().producto().tipoArticulo())));
        NombreProducto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().producto().nombre())));
    }

    public void llenarCombox() {
        List<String> nombre = new ArrayList<>();
        for (AnuncioDto anuncioDto : pujaController.obtenerListaNuncio()) {
            nombre.add(anuncioDto.nombre());
        }
        ObservableList<String> lista = FXCollections.observableArrayList(nombre);
        comboBoxAnuncio.setItems(lista);
    }

    public void updateCombo() {
        initialize();
    }

    public void relizarPuja() throws Exception {

        PujaDto pujaDto = armarPujaDto();

        if (pujaController.realizarPuja(pujaDto, codigoAnuncio)) {
            listaPuja.add(pujaDto);
            tableViewTabla.refresh();
            AnuncioDto anuncioDto=listaAnuncio.stream().filter(anuncioDto1 ->anuncioDto1.codigo().equals(pujaDto.codigoAnuncio())).findFirst().orElse(null);
            anunciosMostrar.add(anuncioDto);
            AlertaUtil.mostrarMensajeOk("Se realizo con éxito.");
        } else {
            AlertaUtil.mostrarMensajeError("No se pudo realizar.");
        }
    }


    public void mostarTabla() {
        String nombreAnuncio = comboBoxAnuncio.getValue();
        AnuncioDto anuncioDto = pujaController.salvarAnuncio(nombreAnuncio);
        List<PujaDto> list = pujaController.obtenerLitaPuja();
        List<PujaDto> listaMostrar = new ArrayList<>();
        List<AnuncioDto> listaMostrarAnuncio = new ArrayList<>();

        for (PujaDto pujaDto : list) {
            if (pujaDto.codigoAnuncio().equals(anuncioDto.codigo())) {
                listaMostrar.add(pujaDto);
                listaMostrarAnuncio.add(anuncioDto);
            }
        }
        listaPuja.clear();
        listaPuja.addAll(listaMostrar);
        tableViewTabla.refresh();

        anunciosMostrar.clear();
        anunciosMostrar.addAll(listaMostrarAnuncio);
        TablaInformacioAnunioc.setItems(anunciosMostrar);
        TablaInformacioAnunioc.refresh();

    }

    public void obtenerPujas() {
        listaPuja.clear();
        listaPuja.addAll(pujaController.obtenerLitaPuja());

        tableViewTabla.setItems(listaPuja);
        tableViewTabla.refresh();
        List<AnuncioDto>listamostrar=new ArrayList<>();
        for(AnuncioDto anuncioDto:listaAnuncio){
            for(PujaDto pujaDto:listaPuja){
                if(pujaDto.codigoAnuncio().equals(anuncioDto.codigo())){
                    listamostrar.add(anuncioDto);

                }
            }
        }
        anunciosMostrar.clear();
        anunciosMostrar.addAll(listamostrar);
        TablaInformacioAnunioc.setItems(anunciosMostrar);// al table view se el asocia una lista
        TablaInformacioAnunioc.refresh();
        ///////////////////////////////

    }

    public PujaDto armarPujaDto() {
        AnuncioDto anuncioDto = listaAnuncio.stream().filter(anuncioDto1 -> anuncioDto1.nombre().equals(comboBoxAnuncio.getValue())).findFirst().orElse(null);
        if (anuncioDto != null) {
            codigoAnuncio = anuncioDto.codigo();
        } else {
            AlertaUtil.mostrarMensajeError("No se encontro el codigo de anuncio");
        }
        return new PujaDto("indefinido"
                , textFieldCodigo.getText()
                , Float.parseFloat(textFieldValorInicial.getText())
                , String.valueOf(LocalDate.now())
                , codigoAnuncio,pujaController.salvarCompradorLogueado().cedula());
    }

    public void obtenerAnuncios() {
        listaAnuncio.addAll(pujaController.obtenerListaNuncio());

    }

    public void eliminarPuja(ActionEvent actionEvent) throws Exception {
        if(pujaController.elimnarPuja(pujaDtoSelecionado)){
            AlertaUtil.mostrarMensajeOk("Se elimino con éxito.");
            listaPuja.remove(pujaDtoSelecionado);
            for(int i=0;i<anunciosMostrar.size();i++){
                if(anunciosMostrar.get(i).codigo().equals(pujaDtoSelecionado.codigoAnuncio())){
                    anunciosMostrar.remove(i);
                }
            }
            tableViewTabla.refresh();

            pujaDtoSelecionado=null;
            tableViewTabla.getSelectionModel().clearSelection();
            textFieldCodigo.clear();
            textFieldValorInicial.clear();
            comboBoxAnuncio.setValue(null);

        }else{
            AlertaUtil.mostrarMensajeOk("No se pudo eliminar la puja");
        }
    }
}
