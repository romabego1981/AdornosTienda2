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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_actividad);

        int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        linearPadre = findViewById(R.id.listacarrito);
        //TextView txtNombre1 = new TextView(this);
        //txtNombre1.setText("Cantidad de productos en el Carrito --> ");
        //linearPadre.addView(txtNombre1);

        Intent carritoRecibido = getIntent();
        ArrayList<Producto> carritoDeCompras = (ArrayList<Producto>)carritoRecibido.getSerializableExtra("carrito");
        Toast.makeText(getApplicationContext(),"Cantidad de productos en el Carrito --> "+carritoDeCompras.size(),Toast.LENGTH_LONG).show();
        //TextView txtNombre = new TextView(this);
        //txtNombre.setText(carritoDeCompras.get(0).getNombre());
        //linearPadre.addView(txtNombre);
        int cont = 0;

        for (Producto p: carritoDeCompras){
            Toast.makeText(getApplicationContext(),"Contador --> "+cont,Toast.LENGTH_LONG).show();
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
            txtNombre.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));
            TextView txtPrecio = new TextView(this);
            txtPrecio.setText(" "+p.getPrecio());
            txtPrecio.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));
            linearVerticalInterno.addView(txtNombre);
            linearVerticalInterno.addView(txtPrecio);

            TextView txtCant = new TextView(this);
            txtCant.setText(" "+p.getCantidad());
            txtCant.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setLayoutParams(new LinearLayout.LayoutParams(100,wrapContent,1));

            linearHorizontal.addView(imagen);
            linearHorizontal.addView(linearVerticalInterno);
            linearHorizontal.addView(txtCant);
            linearHorizontal.addView(btnEliminar);

            linearPadre.addView(linearHorizontal);
            cont++;
        }
         /*
        for (Producto p: carritoDeCompras){
            TextView txtNombre = new TextView(this);
            txtNombre.setText(p.getNombre());
            txtNombre.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));
            TextView txtPrecio = new TextView(this);
            txtPrecio.setText(" "+p.getCantidad());
            txtPrecio.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));
            linearVerticalInterno = new LinearLayout(this);
            linearVerticalInterno.setLayoutParams(new LinearLayout.LayoutParams(0,wrapContent,1));
            linearVerticalInterno.setOrientation(LinearLayout.VERTICAL);
            linearVerticalInterno.addView(txtNombre);
            linearVerticalInterno.addView(txtPrecio);
            linearPadre.addView(linearVerticalInterno);
        }
        */

    }
}