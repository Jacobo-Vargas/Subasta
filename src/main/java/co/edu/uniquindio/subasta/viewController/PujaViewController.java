package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PujaViewController {
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
    }

    private void initDataBinding() {
        tableColumnDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccion()));
        tableColumnCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().codigo())));
        tableColumnValorPuja.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
        tableColumnFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaPuja())));
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

    }

    public void obtenerPujas() {
        listaPuja.clear();
        listaPuja.addAll(pujaController.obtenerLitaPuja());
        tableViewTabla.setItems(listaPuja);
        tableViewTabla.refresh();
    }

    public PujaDto armarPujaDto() {
        AnuncioDto anuncioDto = listaAnuncio.stream().filter(anuncioDto1 -> anuncioDto1.nombre().equals(comboBoxAnuncio.getValue())).findFirst().orElse(null);
        if(anuncioDto != null){
            codigoAnuncio = anuncioDto.codigo();
        }else{
            AlertaUtil.mostrarMensajeError("No se encontro el codigo de anuncio");
        }
        return new PujaDto(textFieldDireccion.getText()
                , textFieldCodigo.getText()
                , Float.parseFloat(textFieldValorInicial.getText())
                , String.valueOf(LocalDate.now())
                , codigoAnuncio);
    }

    public void obtenerAnuncios (){
        listaAnuncio.addAll(pujaController.obtenerListaNuncio());
    }
}
