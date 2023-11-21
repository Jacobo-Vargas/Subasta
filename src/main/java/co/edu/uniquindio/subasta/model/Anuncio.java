package co.edu.uniquindio.subasta.model;


import javafx.scene.image.Image;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Anuncio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Producto producto;
    private String nombre;
    private String descripcion;
    private String foto;
    private String nombreAnunciante;
    private String fechaPublicacion;
    private String fechaTerminacion;

    private double valorInicial;
    private String codigo;

    private ArrayList<Puja> listaPujas = new ArrayList<>();

    public Anuncio() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(String fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ArrayList<Puja> getListaPujas() {
        return listaPujas;
    }

    public void setListaPujas(ArrayList<Puja> listaPujas) {
        this.listaPujas = listaPujas;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "producto=" + producto +
                ", nombre='" + nombre + '\''+
                ", descripcion='" + descripcion + '\'' +
                ", foto=" + foto +
                ", nombreAnunciante='" + nombreAnunciante + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", fechaTerminacion='" + fechaTerminacion + '\'' +
                ", valorInicial=" + valorInicial +
                ", codigo='" + codigo + '\'' +
                ", listaPujas=" + listaPujas +
                '}';
    }
    public String toCsvString() {
        // Utiliza stream para concatenar los valores separados por comas
        String listaPujasCsv = listaPujas.stream()
                .map(Puja::toString) // Ajusta este método según la clase Puja
                .collect(Collectors.joining(","));

        // Ajusta este formato según tus necesidades
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                producto, nombre, descripcion, foto, nombreAnunciante,
                fechaPublicacion, fechaTerminacion, valorInicial, codigo, listaPujasCsv);
    }
}
