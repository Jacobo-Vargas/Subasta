module com.example.ejemplodto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.mapstruct;
    requires  org.mapstruct.processor;
    requires com.jfoenix;
    requires java.desktop;
    requires java.logging;
    requires com.rabbitmq.client;
    requires com.sun.xml.bind.core;
    requires java.xml.bind;
    requires kernel;
    requires layout;

    opens co.edu.uniquindio.subasta to javafx.fxml;
    opens co.edu.uniquindio.subasta.model to javafx.graphics;
    opens co.edu.uniquindio.subasta.mapping.mappers to org.mapstruct;

    exports co.edu.uniquindio.subasta.model;
    exports co.edu.uniquindio.subasta.mapping.dto;
    exports co.edu.uniquindio.subasta.exceptions;
    exports co.edu.uniquindio.subasta to javafx.graphics;

    exports co.edu.uniquindio.subasta.viewController;
    opens co.edu.uniquindio.subasta.viewController to javafx.fxml;
}