package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.AnuncioController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.model.Anuncio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class CrearAnuncioViewController {
    public static int coidogo=1;
    AnuncioController anuncioController;
    ObservableList<AnuncioDto>listaAnuncioDto= FXCollections.observableArrayList();
    @FXML
    public ComboBox comboBoxProducto;
    @FXML
    public DatePicker datePickerFechaFin;
    @FXML
    public DatePicker datePickerFechaInicio;
    @FXML
    public TextField textFieldValorInicial;
    @FXML
    public TextArea textAreaDescriṕcion;
    @FXML
    public TableView<Anuncio> tableViewAnuncios;
    @FXML
    public TableColumn<Anuncio,String> tebleColumnCodigo;
    @FXML
    public TableColumn<Anuncio,String> tebleColumnNombreProducto;
    @FXML

    public TableColumn<Anuncio,String> tebleColumnValorInicial;
    @FXML
    public TableColumn<Anuncio,String> tebleColumnFecheInicio;
    @FXML
    public TableColumn<Anuncio,String> tebleColumnFechaFin;
    public void initialize(){
        anuncioController=new AnuncioController();
    }


    public void agregarAnuncio(ActionEvent event) throws Exception {
        AnuncioDto anuncioDto=new AnuncioDto(null,"carro",textAreaDescriṕcion.getText(),"","",datePickerFechaInicio.getValue(),datePickerFechaFin.getValue(),1000,coidogo);
        coidogo+=1;
        anuncioController.agregarAnuncio(anuncioDto);
    }
}
