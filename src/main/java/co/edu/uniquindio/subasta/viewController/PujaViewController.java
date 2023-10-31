package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.model.Anuncio;
import co.edu.uniquindio.subasta.model.Puja;
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
    public TableColumn<PujaDto, String> tableColumnValor;
    @FXML
    public TableColumn<PujaDto, String> tableColumnDireccion;
    @FXML
    public TableColumn<PujaDto, String> tableColumnValorPuja;
    @FXML
    public TableColumn<PujaDto, String> tableColumnFecha;


    @FXML
    void initialize() {
        pujaController = new PujaController();
        obtenerlistas();
        tableViewTabla.setItems(listaPuja);
        initDataBinding();
    }

    public void obtenerlistas() {
        listaPuja.clear();
        listaAnuncio.clear();
        listaAnuncio.addAll(pujaController.obtenerListaNuncio());
        listaPuja.addAll(pujaController.obtenerLitaPuja());
        llenarCombox();
    }
    private void initDataBinding() {
        tableColumnDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccion()));
        tableColumnCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().codigo())));
        tableColumnValorPuja.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
        tableColumnFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaPuja())));
    }

    public void llenarCombox() {
        List<AnuncioDto> combo = new ArrayList<>(pujaController.obtenerListaNuncio());
        List<String> nombre = new ArrayList<>();
        for(AnuncioDto anuncioDto:pujaController.obtenerListaNuncio()){
            nombre.add(anuncioDto.nombre());

        }
        ObservableList<String> lista = FXCollections.observableArrayList(nombre);
        comboBoxAnuncio.setItems(lista);
    }

    public void updateCombo() {
        initialize();
    }

    public void relizarPuja(ActionEvent actionEvent) throws Exception {

        String nombreAnuncio=comboBoxAnuncio.getValue();
        String direccion=textFieldDireccion.getText();
        String codigo=textFieldCodigo.getText();
        float ofertaInicial=Float.valueOf(textFieldValorInicial.getText());
        LocalDate fecha= LocalDate.now();
        Anuncio anuncio=pujaController.salvarAnuncio(nombreAnuncio);
        PujaDto pujaDto=new PujaDto(direccion,codigo,ofertaInicial,fecha,anuncio);
        pujaController.realizarPuja(pujaDto);


    }
}
