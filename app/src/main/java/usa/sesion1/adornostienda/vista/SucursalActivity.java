package usa.sesion1.adornostienda.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import usa.sesion1.adornostienda.SucursalesActivityMain;
import usa.sesion1.adornostienda.R;
import usa.sesion1.adornostienda.WEBSERVICE.WEBSERVICE;
import usa.sesion1.adornostienda.modelo.Sucursal;
import usa.sesion1.adornostienda.util.ACTIONS;
import usa.sesion1.adornostienda.util.Respuesta;

public class SucursalActivity extends AppCompatActivity {

    EditText edtNombre, edtDireccion, edtLatitud, edtLongitud, edtImagen;
    Button btnAccion, btnBorrar;
    int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        edtLatitud = (EditText) findViewById(R.id.edtLatitud);
        edtLongitud = (EditText) findViewById(R.id.edtLongitud);
        edtImagen = (EditText) findViewById(R.id.edtImagen);

        btnAccion = (Button) findViewById(R.id.btnAccion);
        btnBorrar = (Button) findViewById(R.id.btnEliminar);

        Bundle bundle = getIntent().getExtras();
        int accion = bundle.getInt("nuevo");
        int id = bundle.getInt("id");

        if (accion == ACTIONS.NUEVO) {
            btnAccion.setText("GUARDAR");
        } else {
            btnAccion.setText("ACTUALIZAR");
            String nombre = bundle.getString("nombre");
            String direccion = bundle.getString("direccion");
            double latitud = bundle.getDouble("latitud");
            double longitud = bundle.getDouble("longitud");
            String imagen = bundle.getString("imagen");

            edtNombre.setText(nombre);
            edtDireccion.setText(direccion);
            edtLatitud.setText("" + latitud);
            edtLongitud.setText("" + longitud);
            edtImagen.setText(imagen);
        }

        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = edtNombre.getText().toString();
                String direccion = edtDireccion.getText().toString();
                double latitud = Double.parseDouble(edtLatitud.getText().toString());
                double longitud = Double.parseDouble(edtLongitud.getText().toString());
                String imagen = edtImagen.getText().toString();

                if (accion == ACTIONS.NUEVO) {
                    Sucursal s = new Sucursal(nombre, direccion, latitud, longitud, imagen);
                    guardarSucursal(s);
                } else {
                    Sucursal s = new Sucursal(id, nombre, direccion, latitud, longitud, imagen);
                    editarSucursal(s);
                }
            }
        });


        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accion == ACTIONS.ACTUALIZAR) {
                    eliminarSucursal(id);
                }
            }
        });
    }

    public void guardarSucursal(Sucursal s) {
        ProgressDialog barra = new ProgressDialog(SucursalActivity.this);
        barra.setMessage("Cargando Información del servidor...");
        barra.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        barra.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WEBSERVICE.POST_SUCURSAL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                        barra.cancel();
                        edtNombre.setText("");
                        edtDireccion.setText("");
                        edtLatitud.setText("");
                        edtLongitud.setText("");
                        edtImagen.setText("");

                        Intent i = new Intent(SucursalActivity.this, SucursalesActivityMain.class);
                        startActivity(i);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", "Error: " + error.getMessage());
                barra.cancel();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("nombre", s.getNombre());
                parametros.put("direccion", s.getDireccion());
                parametros.put("latitud", String.valueOf(s.getLatitud()));
                parametros.put("longitud", String.valueOf(s.getLongitud()));
                parametros.put("imagen", s.getImagen());
                return parametros;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                status = response.statusCode;
                Log.w("STATUS", "STATUC CODE: " + status);
                return super.parseNetworkResponse(response);
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void editarSucursal(Sucursal s) {
        ProgressDialog barra = new ProgressDialog(SucursalActivity.this);
        barra.setMessage("Cargando Información del servidor...");
        barra.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        barra.show();

        JSONObject json = new JSONObject();
        try {
            json.put("id", s.getId());
            json.put("nombre", s.getNombre());
            json.put("direccion", s.getDireccion());
            json.put("latitud", s.getLatitud());
            json.put("longitud", s.getLongitud());
            json.put("imagen", s.getImagen());
        } catch (Exception e) {

        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WEBSERVICE.PUT_SUCURSAL;

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            String respuesta = (String) response.get("mensaje");
                            if (respuesta.equals(Respuesta.OK)) {
                                Toast.makeText(getApplicationContext(), "SE ACTUALIZÓ EL REGISTRO", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(SucursalActivity.this, SucursalesActivityMain.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", "Error: " + error.getMessage());
                barra.cancel();
            }
        }) {

            /*
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", String.valueOf(s.getId()));
                parametros.put("nombre", s.getNombre());
                parametros.put("direccion", s.getDireccion());
                parametros.put("latitud", String.valueOf(s.getLatitud()));
                parametros.put("longitud", String.valueOf(s.getLongitud()));
                parametros.put("imagen", s.getImagen());
                return parametros;
            }
            */


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                status = response.statusCode;
                Log.w("STATUS", "STATUC CODE: " + status);
                return super.parseNetworkResponse(response);
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void eliminarSucursal(int id) {
        ProgressDialog barra = new ProgressDialog(SucursalActivity.this);
        barra.setMessage("Cargando Información del servidor...");
        barra.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        barra.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WEBSERVICE.DELETE_SUCURSAL_V2;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            if (status == 200) {
                                Toast.makeText(getApplicationContext(), "SE HA BORRADO EL REGISTRO", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(SucursalActivity.this, SucursalesActivityMain.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", "Error: " + error.getMessage());
                barra.cancel();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", String.valueOf(id));
                return parametros;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                status = response.statusCode;
                Log.w("STATUS", "STATUC CODE: " + status);
                return super.parseNetworkResponse(response);
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
