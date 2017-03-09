package Connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by javier on 09/01/2017.
 */


// ============  Aquí creamos nuestro Singleton de conexión  que será el que se utilice para el primer get
    //========= en cada uno de los OnCreate de nuestras clases =====//

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
