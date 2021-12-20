package usa.sesion1.adornostienda;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usa.sesion1.adornostienda.WEBSERVICE.WEBSERVICE;
import usa.sesion1.adornostienda.controlador.AdaptadorSucursal;
import usa.sesion1.adornostienda.modelo.MySingleton;
import usa.sesion1.adornostienda.modelo.Sucursal;
import usa.sesion1.adornostienda.util.ACTIONS;
import usa.sesion1.adornostienda.vista.SucursalActivity;

public class SucursalesActivityMain extends AppCompatActivity {

    ArrayList<Sucursal> sucursales;
    RecyclerView rcvSucursales;

    Button btnNuevo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNuevo = (Button)findViewById(R.id.btnNuevo);

        rcvSucursales = (RecyclerView)findViewById(R.id.rcvSucursales);
        rcvSucursales.setLayoutManager(new LinearLayoutManager(this));
        getSucursalesApex();
        //getSucursalApex(1);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SucursalesActivityMain.this, SucursalActivity.class);
                i.putExtra("nuevo", ACTIONS.NUEVO);
                i.putExtra("id", 0);
                startActivity(i);
            }
        });
    }

    public void getSucursalesApex(){
        String url = WEBSERVICE.GET_SUCURSALES;

        sucursales = new ArrayList<>();
        ProgressDialog barra = new ProgressDialog(SucursalesActivityMain.this);
        barra.setMessage("Cargando Información del servidor...");
        barra.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        barra.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Sucursal sucursal = null;
                            JSONArray arraySucursales = response.getJSONArray("items");
                            JSONObject objeto = null;
                            for(int i = 0; i < arraySucursales.length();i++){
                                objeto = arraySucursales.getJSONObject(i);
                                int id = objeto.getInt("id");
                                String nombre = objeto.getString("nombre");
                                String direccion = objeto.getString("direccion");
                                double latitud = objeto.getDouble("latitud");
                                double longitud = objeto.getDouble("longitud");
                                String imagen = objeto.getString("imagen");
                                sucursal = new Sucursal(id, nombre, direccion, latitud, longitud, imagen);
                                sucursales.add(sucursal);
                            }
                            //Log.w("REST", "VOLLEY: " + arraySucursales.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AdaptadorSucursal adapter = new AdaptadorSucursal(sucursales);
                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editarSucursal(sucursales.get(rcvSucursales.getChildAdapterPosition(view)));
                            }
                        });
                        rcvSucursales.setAdapter(adapter);
                        //textView.setText("Response: " + response.toString());
                        barra.cancel();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("REST", "VOLLEY: " + error.toString());
                        barra.cancel();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void getSucursalApex(int id){
        String url = WEBSERVICE.GET_SUCURSALES_BY_ID + id;

        sucursales = new ArrayList<>();
        ProgressDialog barra = new ProgressDialog(SucursalesActivityMain.this);
        barra.setMessage("Cargando Información del servidor...");
        barra.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        barra.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Sucursal sucursal = null;
                            JSONArray arraySucursales = response.getJSONArray("items");
                            JSONObject objeto = null;
                            for(int i = 0; i < arraySucursales.length();i++){
                                objeto = arraySucursales.getJSONObject(i);
                                int id = objeto.getInt("id");
                                String nombre = objeto.getString("nombre");
                                String direccion = objeto.getString("direccion");
                                double latitud = objeto.getDouble("latitud");
                                double longitud = objeto.getDouble("longitud");
                                String imagen = objeto.getString("imagen");
                                sucursal = new Sucursal(id, nombre, direccion, latitud, longitud, imagen);
                                sucursales.add(sucursal);
                            }
                            //Log.w("REST", "VOLLEY: " + arraySucursales.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AdaptadorSucursal adapter = new AdaptadorSucursal(sucursales);
                        rcvSucursales.setAdapter(adapter);
                        //textView.setText("Response: " + response.toString());
                        barra.cancel();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("REST", "VOLLEY: " + error.toString());
                        barra.cancel();

                    }
                });



        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void editarSucursal(Sucursal s){
        Intent i = new Intent(SucursalesActivityMain.this, SucursalActivity.class);
        i.putExtra("nuevo", ACTIONS.ACTUALIZAR);

        i.putExtra("id", s.getId());
        i.putExtra("nombre", s.getNombre());
        i.putExtra("direccion", s.getDireccion());
        i.putExtra("latitud", s.getLatitud());
        i.putExtra("longitud", s.getLongitud());
        i.putExtra("imagen", s.getImagen());

        startActivity(i);
    }
}