package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class VentanaEmergenteDosController {


    ObservableList<PujaDto> listaPujaDto = FXCollections.observableArrayList();
    ObservableList<AnuncioDto> listaAnuncioDto = FXCollections.observableArrayList();
    //////////////////////////////////////////////////////////////////7

    @FXML
    public TableView<PujaDto> tableViewTabla;

    @FXML
    public TableColumn<PujaDto, String> tableColumnDireccion;
    @FXML
    public TableColumn<PujaDto, String> tableColumnValorPuja;
    @FXML
    public TableColumn<PujaDto, String> tableColumnFecha;
    @FXML
    public TableColumn<PujaDto, String> tableColumnCodigoAnuncio;
    /////////////////////////////////////////////////////////////////////////////
    @FXML
    public TableView<AnuncioDto> tablaInformacioAnunio;
    @FXML
    public TableColumn<AnuncioDto,String> tableColumnValorInicial;
    @FXML
    public TableColumn<AnuncioDto,String> tableColumnTipoProducto;
    @FXML
    public TableColumn<AnuncioDto,String> tableColumnNombreAnuncio;

    @FXML
    PujaController pujaController;
    @FXML
    String codigoAnuncio;
    public void initialize(){
        pujaController=new PujaController();
        tableViewTabla.setItems(listaPujaDto);
        tablaInformacioAnunio.setItems(listaAnuncioDto);
        initDataBinding();
    }
    private void initDataBinding() {
        tableColumnDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccion()));
        tableColumnCodigoAnuncio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigoAnuncio()) );
        tableColumnValorPuja.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
        tableColumnFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().fechaPuja())));
        tableColumnValorInicial.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().valorInicial())));
        tableColumnTipoProducto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().producto().tipoArticulo())));
        tableColumnNombreAnuncio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().producto().nombre())));
    }
    public List<PujaDto> buscarPujas(){
        List<PujaDto>listaPuja=FXCollections.observableArrayList();


        for(PujaDto pujaDto:pujaController.obtenerLitaPuja()){
            if(codigoAnuncio.equals(pujaDto.codigoAnuncio())){
                listaPuja.add(pujaDto);
                listaAnuncioDto.add(pujaController.salvarAnuncioCodigo(codigoAnuncio));
            }
        }
        return listaPuja;
    }
    public void recibirCodigo(String codigo){
        codigoAnuncio=codigo;
        System.out.println(codigo);
    }


    public void refrescar() {
        listaPujaDto.addAll(buscarPujas());
        tableViewTabla.refresh();
        tablaInformacioAnunio.refresh();
    }
}
