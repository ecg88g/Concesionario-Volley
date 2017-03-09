package jgfm.volleydemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import Connection.MySingleton;
import model.Vehiculo;



public class MainActivity extends AppCompatActivity {

    private Button btnGet;
    private Button btnDelete;
    private Button btnActualizar;
    private Button btnCrear;

    private TextView txtConectado;
    private TextView scrolltxt;

    private ArrayList<Vehiculo> listaVehiculos;

    EditText editTextKms;
    EditText editTextMatri;
    EditText editTextId;

    ArrayAdapter arrayAdapter;
    ListView listView;

    // ========== Pruebas de GET ============ //
    private String server_url = "http://192.168.1.38:3000/";
    private String server_url2 = "http://192.168.1.38:3000/vehiculo/12345-J";
    private String server_url3 = "http://192.168.1.132:3000/vehiculo/";
    String idBorrar = "";

    private RequestQueue requestQueue;
    private Context ctx;

    public Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCrear = (Button) findViewById(R.id.button3);
        btnGet = (Button) findViewById(R.id.button);
        btnDelete = (Button) findViewById(R.id.button2);
        btnActualizar = (Button) findViewById(R.id.button4);


        editTextKms = (EditText) findViewById(R.id.editTextKms);
        editTextMatri = (EditText) findViewById(R.id.editTextMatri);
        editTextId = (EditText) findViewById(R.id.editTextId);


        txtConectado = (TextView) findViewById(R.id.textView2);
        scrolltxt = (TextView) findViewById(R.id.textView3);

        listaVehiculos = new ArrayList<Vehiculo>();

        listView = (ListView) findViewById(R.id.lista);


        ctx = MainActivity.this;
        requestQueue = Volley.newRequestQueue(ctx);


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });



        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerDatos();

            }
        });



        StringRequest stringRequestGET = new StringRequest(Request.Method.GET, server_url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                // ================= Búsqueda de un sólo vehículo. Funciona========================//

                //    Vehiculo v1 = gson.fromJson(response.substring(1,response.length()-1),Vehiculo.class);
                //    txt.setText(v1.toString());


                // ================ Búsqueda de todos los vehículos, creados como Java Object ==========//
                Vehiculo v[] = gson.fromJson(response, Vehiculo[].class);

                for (int i = 0; i < v.length - 1; i++) {
                    //txt.append(v[i].toString()+"\n");
                    listaVehiculos.add(v[i]);

                }
                txtConectado.setText("Conectado. Cargados datos de la BBDD" + "\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtConectado.setText("Error al cargar de la BBDD.");
                error.printStackTrace();
            }
        });


        MySingleton.getInstance(getApplicationContext()).addRequestQueue(stringRequestGET);



    }



    private  void leerDatos(){

        //scrolltxt.setText(listaVehiculos.toString());
        ArrayAdapter<Vehiculo> a = new ArrayAdapter<Vehiculo>(MainActivity.this, R.layout.simple_list_item_1, listaVehiculos);
        listView.setAdapter(a);
    }

    private void actualizar() {

        StringRequest strq = new StringRequest(Request.Method.PUT, server_url3+editTextMatri.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("Kms", editTextKms.getText().toString());
                //=======¡¡¡¡Ojo!!! en el servidor, el update espera matricula, EN MINÚSCULAS ========//
                parametros.put("matricula", editTextMatri.getText().toString());
                parametros.put("IdConcesionario", editTextId.getText().toString());

                return parametros;
            }
        };

        requestQueue.add(strq);
        leerDatos();

    }

    private void borrar() {

        StringRequest strq = new StringRequest(Request.Method.DELETE, server_url3+editTextMatri.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("Kms", editTextMatri.getText().toString());


                return parametros;
            }
        };

        requestQueue.add(strq);
        leerDatos();
    }

    private void crear() {


        StringRequest strq = new StringRequest(Request.Method.POST, server_url3, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast t = Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG);
                t.show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<>();

                parametros.put("Kms", editTextKms.getText().toString());
                parametros.put("Matricula", editTextMatri.getText().toString());
                parametros.put("idConcesionario", editTextId.getText().toString());

                return parametros;
                //metodo
            }

        };

        requestQueue.add(strq);
        leerDatos();

    }

}
