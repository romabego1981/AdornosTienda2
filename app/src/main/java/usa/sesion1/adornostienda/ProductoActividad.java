package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductoActividad extends AppCompatActivity {

    LinearLayout linearPadre;
    LinearLayout linearHorizontal;
    LinearLayout linearVerticalInterno;
    LinearLayout linearHorizontalUltimo;
    int costoTotal;
    int aux1;
    int aux2;
    String cant1;
    String precio1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_actividad);

        int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        linearPadre = findViewById(R.id.listacarrito);
        int costoTotal;

        Intent carritoRecibido = getIntent();
        ArrayList<Producto> carritoDeCompras = (ArrayList<Producto>)carritoRecibido.getSerializableExtra("carrito");
        //Toast.makeText(getApplicationContext(),"Cantidad de productos en el Carrito --> "+carritoDeCompras.size(),Toast.LENGTH_LONG).show();

        costoTotal=0;
        for (Producto p: carritoDeCompras){
            linearHorizontal = new LinearLayout(this);
            linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            linearHorizontal.setLayoutParams(new LinearLayout.LayoutParams(matchParent,wrapContent));

            ImageView imagen = new ImageView(this);
            imagen.setImageResource(p.getImagen());
            imagen.setLayoutParams(new LinearLayout.LayoutParams(1,60,1));

            linearVerticalInterno = new LinearLayout(this);
            linearVerticalInterno.setLayoutParams(new LinearLayout.LayoutParams(0,wrapContent,1));
            linearVerticalInterno.setOrientation(LinearLayout.VERTICAL);
            TextView txtNombre = new TextView(this);
            txtNombre.setText(p.getNombre());
            txtNombre.setLayoutParams(new LinearLayout.LayoutParams(wrapContent,wrapContent));
            TextView txtPrecio = new TextView(this);
            txtPrecio.setText(" "+p.getPrecio());
            txtPrecio.setLayoutParams(new LinearLayout.LayoutParams(wrapContent,wrapContent));
            linearVerticalInterno.addView(txtNombre);
            linearVerticalInterno.addView(txtPrecio);

            TextView txtCant = new TextView(this);
            txtCant.setText(" "+p.getCantidad());
            txtCant.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

            try {
                cant1=txtCant.getText().toString();
                precio1=txtPrecio.getText().toString();
                //aux1 = Integer.parseInt(cant1);
                //aux2 = Integer.decode(txtPrecio.getText().toString());
                costoTotal = costoTotal + (Integer.parseInt(txtCant.getText().toString())*Integer.parseInt(txtPrecio.getText().toString()));
                precio1=" "+costoTotal;
                //costoTotal = Integer.parseInt(cant1);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                precio1="error";
            }

            Button btnMas = new Button(this);
            btnMas.setText("+");
            btnMas.setLayoutParams(new LinearLayout.LayoutParams(20,wrapContent,1));
            Button btnMenos = new Button(this);
            btnMenos.setText("-");
            btnMenos.setLayoutParams(new LinearLayout.LayoutParams(20,wrapContent,1));

            linearHorizontal.addView(imagen);
            linearHorizontal.addView(linearVerticalInterno);
            linearHorizontal.addView(txtCant);
            linearHorizontal.addView(btnMas);
            linearHorizontal.addView(btnMenos);

            linearPadre.addView(linearHorizontal);

            btnMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aumentaProductoEnCarrito(p);
                    //String msg="Se añadió el producto " + p.getNombre();
                    //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    txtCant.setText(" "+p.getCantidad());
                }
            });
            btnMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disminuyeProductoEnCarrito(p);
                    String msg="Se quitó el producto " + p.getNombre();
                    //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    txtCant.setText(" "+p.getCantidad());
                }
            });
        }

        linearHorizontalUltimo = new LinearLayout(this);
        linearHorizontalUltimo.setLayoutParams(new LinearLayout.LayoutParams(matchParent,wrapContent));
        linearHorizontalUltimo.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtTotal = new TextView(this);
        txtTotal.setText(" "+precio1);
        txtTotal.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

        Button btnVerCarrito = new Button(this);
        btnVerCarrito.setText("Ver Carrito");
        btnVerCarrito.setLayoutParams(new LinearLayout.LayoutParams(100,wrapContent,1));

        linearHorizontalUltimo.addView(txtTotal);
        linearHorizontalUltimo.addView(btnVerCarrito);
        //linearHorizontal.addView(linearHorizontalUltimo);
        linearPadre.addView(linearHorizontalUltimo);
        //linearProductos.addView(linearHorizontal);

    }

    private void aumentaProductoEnCarrito(Producto producto){
        int cantidadActual = producto.getCantidad();
        producto.setCantidad(cantidadActual+1);
        //costoTotal = costoTotal + producto.getPrecio();
    }

    private void disminuyeProductoEnCarrito(Producto producto){
        int cantidadActual = producto.getCantidad();
        producto.setCantidad(cantidadActual-1);
        //costoTotal = costoTotal - producto.getPrecio();
    }
}
