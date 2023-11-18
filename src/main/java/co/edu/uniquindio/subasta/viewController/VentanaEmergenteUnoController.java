package co.edu.uniquindio.subasta.viewController;

import co.edu.uniquindio.subasta.controller.PujaController;
import co.edu.uniquindio.subasta.mapping.dto.PujaDto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class VentanaEmergenteUnoController {
    PujaController pujaController;
    @FXML
    public TextField texFieldOferta;
    @FXML
    public TextField textFieldCodigo;
    String codigoAnuncio;
    public void initialize(){
        pujaController=new PujaController();
    }
    public void recibirCodigo(String codigo){
        codigoAnuncio=codigo;
    }

    public void pujar() throws Exception {
        String codigo=textFieldCodigo.getText();
        float oferta= Float.parseFloat(textFieldCodigo.getText());
        PujaDto pujaDto=new PujaDto("indefinido",codigo,oferta, String.valueOf(LocalDate.now()),codigoAnuncio);
        if(pujaController.realizarPuja(pujaDto,codigoAnuncio)){
            System.out.println("se guardo en");
        }else{
            System.out.println("no se pudo guardar");
        }

    }
}
