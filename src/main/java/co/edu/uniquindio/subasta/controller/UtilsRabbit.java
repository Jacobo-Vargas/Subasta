package co.edu.uniquindio.subasta.controller;

import jakarta.xml.bind.JAXBException;
import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.io.StringWriter;


public class UtilsRabbit {
    public static final String QUEUE_NUEVA_PUBLICACION = "nueva_publicacion";
    public static final String RUTA_XML = "src/main/resources/persistencia/model.xml";

    //Convierte un objeto XML a una cadena
    public static String convertObjectToXml(Object object) throws Exception {
        StringWriter stringWriter = new StringWriter();
        JAXB.marshal(object, stringWriter);
        return stringWriter.toString();
    }
    // Convierte una cadena XML a un objeto
    public static <T> T convertXmlToObject(String xmlString, Class<T> type) throws JAXBException {
        return JAXB.unmarshal(new StringReader(xmlString), type);
    }
}
