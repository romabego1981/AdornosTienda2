package usa.sesion1.adornostienda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {

    private static final String PRODUCTOS_TABLE_CREATE = "CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio INTEGER, imagen INTEGER, cantidad INTEGER) ";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "compras.db";

    public MyOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PRODUCTOS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS productos ");
        onCreate(db);
    }

    public void insertarProducto(String nombre, int precio, int imagen, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("precio", precio);
        cv.put("imagen", imagen);

        db.insert("productos", null, cv);
    }

    public void actualizarProducto(String nombre, int precio, int id, int cantidad, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("precio", precio);
        cv.put("cantidad", cantidad);

        String[] arg = new String[]{""+id};
        db.update("productos", cv, "id=?", arg);

    }

    public void borrarProducto(int id, SQLiteDatabase db){
        String[] arg = new String[]{""+id};
        db.delete("productos", "id=?", arg);

    }

    public Cursor leerProductos(SQLiteDatabase db){
        return db.query("productos", null, null, null, null, null, null);
    }
}
