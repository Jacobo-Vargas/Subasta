package co.edu.uniquindio.subasta.model;

import java.io.Serial;
import java.io.Serializable;

public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String user;
    private String contrasenia;


    public Usuario() {

    }

    public String getUser() {
        return user;
    }


    public void setUser(String usuario) {
        this.user = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "user='" + user + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }

}
