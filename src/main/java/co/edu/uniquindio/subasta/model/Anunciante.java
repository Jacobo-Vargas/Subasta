    package co.edu.uniquindio.subasta.model;

    import java.io.Serial;
    import java.util.ArrayList;

    public class Anunciante extends Persona {
        @Serial
        private static final long serialVersionUID = 1L;
        private ArrayList<Producto> listaProducto = new ArrayList<>();
        private ArrayList<Anuncio> listaAnucio = new ArrayList<>();
        private Usuario usuario;

        public Anunciante() {
        }


        public ArrayList<Producto> getListaProducto() {
            return listaProducto;
        }

        public void setListaProducto(ArrayList<Producto> listaProducto) {
            this.listaProducto = listaProducto;
        }

        public ArrayList<Anuncio> getListaAnucio() {
            return listaAnucio;
        }

        public void setListaAnucio(ArrayList<Anuncio> listaAnucio) {
            this.listaAnucio = listaAnucio;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        public String toString() {
            return "Anunciante{" +
                    "listaProducto=" + listaProducto +
                    ", listaAnucio=" + listaAnucio +
                    ", usuario=" + usuario +
                    '}';
        }

    }
