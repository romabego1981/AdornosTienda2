package usa.sesion1.adornostienda;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

    private int id;
    private String nombre;
    private int imagen;
    private  int precio;
    private int cantidad;

    public Producto(int id, String nombre, int imagen, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
        this.cantidad = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeInt(imagen);
        dest.writeInt(precio);
        dest.writeInt(cantidad);
    }

    public Producto(Parcel in){
        id = in.readInt();
        nombre = in.readString();
        imagen = in.readInt();
        precio = in.readInt();
        cantidad = in.readInt();
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        public Producto createFromParcel(Parcel in){
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[0];
        }
    };
}
