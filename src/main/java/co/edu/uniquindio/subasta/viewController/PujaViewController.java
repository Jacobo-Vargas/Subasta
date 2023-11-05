package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.model.Anuncio;
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
        for (AnuncioDto anuncioDto : pujaController.obtenerListaNuncio()) {
            nombre.add(anuncioDto.nombre());

        }
        ObservableList<String> lista = FXCollections.observableArrayList(nombre);
        comboBoxAnuncio.setItems(lista);
    }

    public void updateCombo() {
        initialize();
    }

    public void relizarPuja(ActionEvent actionEvent) throws Exception {
        String nombreAnuncio = comboBoxAnuncio.getValue();
        Anuncio anuncio = pujaController.salvarAnuncio(nombreAnuncio);
        if ((pujaController.listaPujaCompradorLogueado(anuncio.getCodigo()).size()) == 3) {
            AlertaUtil.mostrarMensajeError("Un comprador solo puede hacer 3 pujas a un anuncio");

        } else {
            String direccion = textFieldDireccion.getText();
            String codigoo = textFieldCodigo.getText();
            float ofertaInicial = Float.valueOf(textFieldValorInicial.getText());
            System.out.println(ofertaInicial);
            LocalDate fecha = LocalDate.now();

            PujaDto pujaDto = new PujaDto(direccion, codigoo, ofertaInicial, fecha, anuncio);

            tableViewTabla.refresh();
            if(pujaController.realizarPuja(pujaDto, anuncio.getCodigo())) {
                AlertaUtil.mostrarMensajeConfirmacion("Puja reliazada con exito");
            }else {
                AlertaUtil.mostrarMensajeError("No se pudo relizar la puja");
            }
        }


    }


    public void mostarTabla(ActionEvent actionEvent) {
        String nombre = comboBoxAnuncio.getValue();
        Anuncio anuncio = pujaController.salvarAnuncio(nombre);
        List<PujaDto> list = pujaController.listaPujaCompradorLogueado(anuncio.getCodigo());
        ObservableList<PujaDto> listaConvertida = FXCollections.observableList(list);

        listaPuja = listaConvertida;
        tableViewTabla.setItems(listaPuja);
        tableViewTabla.refresh();
    }
}
