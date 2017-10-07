package micupongt.com.micupongt;

public class Contenedor {
    String fecha;
    String titulo;
    String noticia;
    String ruta;
    String imagen;
    String empresa;
    String direccion;
    public Contenedor(String fecha, String titulo, String noticia, String ruta, String imagen,String empresa,String direccion){
        this.fecha=fecha;
        this.titulo=titulo;
        this.noticia=noticia;
        this.ruta=ruta;
        this.imagen=imagen;
        this.empresa=empresa;
        this.direccion=direccion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getNoticia() {
        return noticia;
    }
    public String getRuta() {
        return ruta;
    }
    public String getImagen() {
        return imagen;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setNoticia(String noticia) {
        this.noticia = noticia;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}