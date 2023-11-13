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

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
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


    @FXML
    void initialize() {
        pujaController = new PujaController();
        initView();
    }

    void initView() {
        llenarCombox();
        obtenerPujas();
        obtenerAnuncios();
        initDataBinding();
        listenerSelection();
    }

    private void listenerSelection() {
        tableViewTabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pujaDtoSelecionado = newSelection;
            mostrarInformacionAnuncio(pujaDtoSelecionado);
        });

    }

    private void mostrarInformacionAnuncio(PujaDto pujaDtoSelecionado) {
        if(pujaDtoSelecionado!=null){
            String  nombre=listaAnuncio.stream().filter(Anuncio->Anuncio.codigo().equals(pujaDtoSelecionado.
                    codigoAnuncio())).map(AnuncioDto::nombre).findFirst().orElse(null);// esta lamba filtar el anuncio y deja de nombre del combbox

            textFieldValorInicial.setText(String.valueOf(pujaDtoSelecionado.oferta()));
            textFieldCodigo.setText(pujaDtoSelecionado.codigo());
            comboBoxAnuncio.setValue(nombre);
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
                , codigoAnuncio);
    }

    public void obtenerAnuncios() {
        listaAnuncio.addAll(pujaController.obtenerListaNuncio());
    }

    public void eliminarPuja(ActionEvent actionEvent) throws Exception {
        if(pujaController.elimnarPuja(pujaDtoSelecionado)){
            AlertaUtil.mostrarMensajeOk("Se elimino con éxito.");
            listaPuja.remove(pujaDtoSelecionado);
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
