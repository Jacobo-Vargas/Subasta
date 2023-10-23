package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncianteDto;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import co.edu.uniquindio.subasta.model.Anunciante;
import co.edu.uniquindio.subasta.model.Anuncio;
import co.edu.uniquindio.subasta.model.Puja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PujaViewController {
    PujaController pujaController;
    ObservableList<PujaDto>listaPuja= FXCollections.observableArrayList();
    ObservableList<AnuncianteDto>listaAnuncio= FXCollections.observableArrayList();
    @FXML
    public ComboBox<String> comboBoxAnuncio;
    @FXML
    public TextField textFieldValorInicial;
    @FXML
    public TextField textFieldDireccion;
    @FXML
    public Button botonPuja;
    @FXML
    public TableView<PujaDto> TableViewTabla;
    @FXML
    public TableColumn<PujaDto,String> tableColumnCodigo;
    @FXML
    public TableColumn<PujaDto,String> tableColumnValor;
    @FXML
    public TableColumn<PujaDto,String> tableColumnDireccion;
    @FXML
    public TableColumn<PujaDto,String> tableColumnValorPuja;
    @FXML
    public TableColumn<PujaDto,String> tableColumnFecha;
    @FXML
    public TableColumn<PujaDto,String> tableColumnNumeroPujas;

    @FXML
    void initialize(){
        pujaController=new PujaController();
        obtenerlistas();
    }

    public void obtenerlistas(){
        listaPuja.clear();
        listaAnuncio.clear();
        listaAnuncio.addAll(pujaController.ObenerlistaAnuncio());
        listaPuja.addAll(pujaController.obtenerLitaPuja());
        llenarCombox();
    }
    public void llenarCombox(){
        List<Anunciante> combo = new ArrayList<>(pujaController.retoar());
        List<String> nombre=new ArrayList<>();
        for(int i=0;i<combo.size();i++){
            List<Anuncio>lista=combo.get(i).getListaAnucio();
            if(lista != null){
                for(int h=0;h<lista.size();h++){
                    nombre.add(lista.get(h).getNombre());
                    System.out.println(lista.get(h).getNombre());
                }
            }
        }
        ObservableList<String>lista=FXCollections.observableArrayList(nombre);
        comboBoxAnuncio.setItems(lista);
    }

    public void updateCombo(){
        initialize();
    }
}
