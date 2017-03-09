VolleyREST

Aquí creamos el Singleton que vamos a utilizar en el OnCreate de cada una de nuestras clase Vehiculo y Concesionario.//

public class MySingleton {

private static MySingleton myInstance;
private RequestQueue requestQueue;
private static Context myContext;

public RequestQueue getRequestQueue(){
    if(requestQueue== null){
        requestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
    }
    return requestQueue;
}

public MySingleton(Context context) {
    myContext= context;
    requestQueue= getRequestQueue();
}

public static synchronized MySingleton getInstance(Context context){

    if(myInstance== null){
        myInstance= new MySingleton(context);
    }
    return myInstance;
}

public<T> void addRequestQueue(Request<T> request){

    requestQueue.add(request);

}

}
Tenemos una pantalla de inicio que extiende de la clase Thread de la cual generamos dos hilos diferentes
uno para cada una de las consultas a nuestra base de datos. En nuestro caso las consultas se realizan a un servidor
node.js con mySql.

class HiloConcesionario extends Thread{

    @Override
    public void run(){

        btn_concesionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intro.this, ActivityConcesionario.class);
                startActivity(i);
            }
        });

    }
}

class HiloVehiculo extends Thread{

    @Override
    public void run(){


        btn_vehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intro.this, ActivityVehiculo.class);
                startActivity(i);
            }
        });

    }
}

Por último un ejemplo de la clase que realiza la petición REST. En este caso de un UPDATE

private void actualizar() {

    StringRequest strq = new StringRequest(Request.Method.PUT, server_url3+editTextId.getText().toString(),
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

            parametros.put("Localidad", editTextLocalidad.getText().toString());
            parametros.put("Nombre", editTextNombre.getText().toString());
            //=======¡¡¡¡Ojo!!! en el servidor, el update espera matricula, EN MINÚSCULAS ========//
            parametros.put("matricula", editTextMatri.getText().toString());
            parametros.put("IdConcesionario", editTextId.getText().toString());

            return parametros;
        }
    };

    requestQueue.add(strq);
    leerDatos();

}

Volley siempre necesita implementar tres métodos al String request, los sobreescribimos tal y como aparecen arriba.
El delete, create y read serían similar, lo único que en el read, utilizamos Gson para parsear los objetos que nos
devuelve el servidor y crear una SimpleArrayAdapter que nos lo muestra en la vista.
Javier GFM.
Eloy Castro.
