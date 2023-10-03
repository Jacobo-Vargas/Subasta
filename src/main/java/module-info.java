module com.example.ejemplodto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.mapstruct;
    requires  org.mapstruct.processor;
    requires com.jfoenix;

    opens co.edu.uniquindio.subasta to javafx.fxml;
    opens co.edu.uniquindio.subasta.model to javafx.graphics;
    opens co.edu.uniquindio.subasta.mapping.mappers to org.mapstruct;

    exports co.edu.uniquindio.subasta.model;
    exports co.edu.uniquindio.subasta.mapping.dto;
    exports co.edu.uniquindio.subasta to javafx.graphics;


    exports co.edu.uniquindio.subasta.viewController;
    opens co.edu.uniquindio.subasta.viewController to javafx.fxml;


}