package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
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

public class PujaViewController {
    PujaController pujaController;
    ObservableList<PujaDto> listaPuja = FXCollections.observableArrayList();
    ObservableList<AnuncioDto> listaAnuncio = FXCollections.observableArrayList();

    PujaDto pujaSelecionada;
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
    void initialize() throws MalformedURLException {
        pujaController = new PujaController();
        initView();
        listenerSelection();
    }
    void initView(){
        llenarCombox();
        obtenerPujas();
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

    public void updateCombo() throws MalformedURLException {
        initialize();
    }

    public void relizarPuja() throws Exception {

        obtenerPujas();
        Anuncio anuncio=pujaController.salvarAnuncio(comboBoxAnuncio.getValue());
        Float valor= Float.valueOf(textFieldValorInicial.getText());
        PujaDto pujaDtoo=new PujaDto(textFieldDireccion.getText(),textFieldCodigo.getText(),valor,String.valueOf(LocalDate.now()),anuncio);

         if(pujaController.realizarPuja(pujaDtoo, anuncio.getCodigo()) ) {
            AlertaUtil.mostrarMensajeOk("Se realizo la puja con exito");
        }





    }


    public void mostarTabla() {
        String nombre = comboBoxAnuncio.getValue();
        List<PujaDto> list = pujaController.listaPujaCompradorLogueado();
        List<PujaDto>mostrar=new ArrayList<>();
        for(PujaDto pujaDto:list){
            System.out.println(pujaDto);
        }
        for(PujaDto pujaDto:list){
            if(pujaDto.anuncio().getNombre().equals(nombre)){
                mostrar.add(pujaDto);
            }
        }

        listaPuja = FXCollections.observableList(mostrar);
        tableViewTabla.setItems(listaPuja);
        tableViewTabla.refresh();
    }

    public void obtenerPujas(){
        listaPuja.clear();
        listaPuja.addAll(pujaController.obtenerLitaPuja());
        tableViewTabla.setItems(listaPuja);
        tableViewTabla.refresh();
    }

    public PujaDto armarPujaDto(){
        Anuncio anuncio=pujaController.salvarAnuncio(comboBoxAnuncio.getValue());
       return new PujaDto(textFieldDireccion.getText()
               ,textFieldCodigo.getText()
               ,Float.parseFloat(textFieldValorInicial.getText())
               ,String.valueOf(LocalDate.now())
               ,anuncio);
    }


    private void listenerSelection() throws MalformedURLException {
        tableViewTabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pujaSelecionada = newSelection;

                mostrarInformacionAnuncio(pujaSelecionada);

        });
    }

    private void mostrarInformacionAnuncio(PujaDto pujaSelecionada) {
        if(pujaSelecionada!=null){
            textFieldCodigo.setText(pujaSelecionada.codigo());
            textFieldDireccion.setText(pujaSelecionada.direccion());
            textFieldValorInicial.setText(String.valueOf(pujaSelecionada.oferta()));
        }
    }


    public void eliminarPuja(ActionEvent actionEvent) throws Exception {
        if(pujaController.elimnarPuja(pujaSelecionada)){
            listaPuja.remove(pujaSelecionada);
            tableViewTabla.refresh();
            pujaSelecionada=null;
            tableViewTabla.getSelectionModel().clearSelection();
            textFieldDireccion.setText("");
            textFieldCodigo.setText("");
            textFieldValorInicial.setText("");
            comboBoxAnuncio.setPromptText("null");
            AlertaUtil.mostrarMensajeOk("Se elmino la puja con exito.");
        }else{
            AlertaUtil.mostrarMensajeError("No se pudo eliminar la puja");
        }

    }
}
