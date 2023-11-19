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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GestionAnunciosViewController {

    AnuncioController anuncioController;
    ObservableList<AnuncioDto> listaAnuncioDto = FXCollections.observableArrayList();
    AnuncioDto anuncioDtoSelecionado;


    public TableColumn<PujaDto, String> tcNombreComprador;
    public TableColumn<PujaDto, String> tcFechaOferta;
    public TableColumn<PujaDto, String> tcCodigo;
    public TableColumn<PujaDto, String> tcOferta;
    public TableView<PujaDto> tvPujas;
    public Label lblDireccionComprador;
    public Label lblTelefonoComprador;
    public ComboBox<String> cBoxAnuncios;
    public ImageView imageViewFoto;

    public void actualizarBox() {
    }

    public void elegirPuja() {
    }


    private void initDataBinding() {

       // tcNombreComprador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().codigo())));
        tcFechaOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaPuja())));
        tcOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
    }
}

