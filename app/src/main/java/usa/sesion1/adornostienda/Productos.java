package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Productos extends AppCompatActivity {

    LinearLayout linearProductos;
    LinearLayout linearHorizontal;
    LinearLayout linearVerticalInterno;
    LinearLayout linearVerticalUltimo;
    ArrayList<Producto> carrito;
    ArrayList<Producto> catalogo;
    Intent pantallaCarrito;
    private MenuItem opcVerCarrito; //VAriable para contener el boton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("AppDORNOS");
        getSupportActionBar().setSubtitle("Materializamos ideas de diseño");
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        opcVerCarrito = (MenuItem)findViewById(R.id.opcVerCarrito);
        carrito = new ArrayList<>();
        linearProductos = (LinearLayout) findViewById(R.id.linearProductos);
        int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        catalogo = new ArrayList<>();
        catalogo.add(new Producto(1, "Adorno Corazón dorado", R.drawable.adorno1,120000));
        catalogo.add(new Producto(2, "Adorno Estrella mpultiple", R.drawable.adorno2,45000));
        catalogo.add(new Producto(3, "Adorno Navideño elegante", R.drawable.adorno3,37000));
        catalogo.add(new Producto(4, "Adorno corona navideña", R.drawable.adorno4,80000));
        for (Producto producto:catalogo){
            linearHorizontal = new LinearLayout(this);
            linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            linearHorizontal.setLayoutParams(new LinearLayout.LayoutParams(matchParent,wrapContent));
            ImageView imagen = new ImageView(this);
            imagen.setImageResource(producto.getImagen());
            imagen.setLayoutParams(new LinearLayout.LayoutParams(1,60,1));
            linearVerticalInterno = new LinearLayout(this);
            linearVerticalInterno.setLayoutParams(new LinearLayout.LayoutParams(0,wrapContent,1));
            linearVerticalInterno.setOrientation(LinearLayout.VERTICAL);

            TextView txtNombre = new TextView(this);
            txtNombre.setText(producto.getNombre());
            txtNombre.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

            TextView txtPrecio = new TextView(this);
            txtPrecio.setText(" "+producto.getPrecio());
            txtPrecio.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

            linearVerticalInterno.addView(txtNombre);
            linearVerticalInterno.addView(txtPrecio);
            Button btnAnadir = new Button(this);
            btnAnadir.setText("Añadir");
            btnAnadir.setLayoutParams(new LinearLayout.LayoutParams(100,wrapContent,1));
            linearHorizontal.addView(imagen);
            linearHorizontal.addView(linearVerticalInterno);
            linearHorizontal.addView(btnAnadir);

            linearProductos.addView(linearHorizontal);
            Toast.makeText(getApplicationContext(), " "+producto.getNombre()+" - "+producto.getPrecio(), Toast.LENGTH_LONG).show();
            btnAnadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!buscarProducto(carrito,producto)){
                        carrito.add(producto);
                        String msg="Se añadió el producto " + producto.getNombre();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }else{
                        aumentarProductoEnCarrito(carrito,producto);
                        String msg="Se añadió el producto " + producto.getNombre();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
        linearVerticalUltimo = new LinearLayout(this);
        linearVerticalUltimo.setLayoutParams(new LinearLayout.LayoutParams(0,wrapContent,1));
        linearVerticalUltimo.setOrientation(LinearLayout.HORIZONTAL);
        Button btnVerCarrito = new Button(this);
        btnVerCarrito.setText("Ver Carrito");
        btnVerCarrito.setLayoutParams(new LinearLayout.LayoutParams(100,wrapContent,1));
        linearVerticalUltimo.addView(btnVerCarrito);
        linearHorizontal.addView(linearVerticalUltimo);
        //linearProductos.addView(linearHorizontal);
        btnVerCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Ingresa a Ver Carrito..."+carrito.size(),Toast.LENGTH_LONG).show();
                pantallaCarrito = new Intent(getApplicationContext(), ProductoActividad.class);
                pantallaCarrito.putExtra("carrito",carrito);
                startActivity(pantallaCarrito);
            }
        });
    }

    private boolean buscarProducto(ArrayList<Producto> productos, Producto producto){
        for(Producto p: productos){
            if(producto.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }

    private void aumentarProductoEnCarrito(ArrayList<Producto> productos, Producto producto){
        for(int i=0; i<carrito.size(); i++){
            int cantidadActual = carrito.get(i).getCantidad();
            carrito.get(i).setCantidad(cantidadActual+1);
        }
    }

}