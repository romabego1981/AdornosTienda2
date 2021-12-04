/**
 *
 * @author Grupo6
 * @description Este módulo se creó para manejar el backen de la App de adornos en su pantalla principal
 */
package usa.sesion1.adornostienda;

//Sección de importación de componentes
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import java.util.Timer;

import dmax.dialog.SpotsDialog;

/**
 *
 * @description Clase principal para manipular la pantalla inicial de la app
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaración de atributos de la clase
    private EditText txtUsuario; //VAriable para contener el boton
    private EditText txtClave; //VAriable para contener el boton
    private Button btnLogin; //VAriable para contener el boton
    private ImageView imgIngreso; //VAriable para contener el boton
    private TextView txtIngreso; //VAriable para contener el boton
    private MenuItem opcGestionarProductos; //VAriable para contener el boton
    private MenuItem opcComprarProductos; //VAriable para contener el boton
    private MenuItem opcServicios; //VAriable para contener el boton
    private MenuItem opcContacto; //VAriable para contener el boton

     AlertDialog mDialog;
    /**
     *
     * @description Sobreescritura del método onCreate para realizar actividades cuando se crea la activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"Creado menu opcComprarProductos",Toast.LENGTH_LONG).show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBar_Title);
        getSupportActionBar().setSubtitle(R.string.actionBar_Subtitle);
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        /*
        MyOpenHelper dataBase = new MyOpenHelper(this);
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.delete("productos", null, null);
        */

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        imgIngreso = (ImageView)findViewById(R.id.imgIngreso);
        txtIngreso = (TextView)findViewById(R.id.txtIngreso);
        imgIngreso.setVisibility(View.INVISIBLE);
        txtIngreso.setVisibility(View.INVISIBLE);

        opcGestionarProductos = (MenuItem)findViewById(R.id.opcGestionarProductos);
        opcComprarProductos = (MenuItem)findViewById(R.id.opcComprarProductos);
        opcServicios = (MenuItem)findViewById(R.id.opcServicios);
        opcContacto = (MenuItem)findViewById(R.id.opcContacto);
        opcContacto = (MenuItem)findViewById(R.id.opcRegUser);
        Toast.makeText(getApplicationContext(),"Creado menu opcComprarProductos",Toast.LENGTH_LONG).show();
        mDialog = new SpotsDialog.Builder().setContext(MainActivity.this).setMessage(R.string.msg_espere).build();
    }

    /**
     *
     * @description Sobreescritura del método onClick para realizar actividades cuando el usuario
     * haga click
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(this, R.string.msg_pessBtn, Toast.LENGTH_LONG).show();
        imgIngreso.setVisibility(View.VISIBLE);
        txtIngreso.setVisibility(View.VISIBLE);
        btnLogin.setText(R.string.regresar);
    }

    /**
     *
     * @description Sobreescritura del método onCreateOptionsMenu para realizar actividades cuando
     * se crean las opciones del menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuprincipal, menu);

        return true;
    }

    /**
     *
     * @description Sobreescritura del método onOptionsItemSelected para realizar actividades cuando el usuario seleccione una opción del menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcGestionarProductos){
            Toast.makeText(getApplicationContext(),"Ingresa a Gestionar Productos",Toast.LENGTH_LONG).show();
            Intent pantallaGestionaProductos = new Intent(this, GestionaProductos.class);
            startActivity(pantallaGestionaProductos);
        }
        if (id == R.id.opcComprarProductos){
            Toast.makeText(getApplicationContext(),"Ingresa a Comprar Productos",Toast.LENGTH_LONG).show();
            Intent pantallaCompraProductos = new Intent(this, Productos.class);
            startActivity(pantallaCompraProductos);
        }
        if (id == R.id.opcContacto){
            Toast.makeText(getApplicationContext(),R.string.ingresarContact,Toast.LENGTH_LONG).show();
            Intent pantallaContacto = new Intent(this, Contacto.class);
            startActivity(pantallaContacto);
        }
        if (id == R.id.opcServicios){
            Toast.makeText(getApplicationContext(),R.string.ingresarServ,Toast.LENGTH_LONG).show();
            Intent pantallaServicios = new Intent(this, Servicios.class);
            startActivity(pantallaServicios);
        }
        if (id == R.id.opcRegUser){
            Toast.makeText(getApplicationContext(),R.string.ingresarUser,Toast.LENGTH_LONG).show();
            Intent pantallaRegUser = new Intent(this, RegistroUsuario.class);
            startActivity(pantallaRegUser);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(),R.string.ingresarOnresume,Toast.LENGTH_LONG).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 3000ms
                mDialog.hide();
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDialog.show();
    }
} //Fin de la clase