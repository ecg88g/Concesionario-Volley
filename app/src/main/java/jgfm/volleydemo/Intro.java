package jgfm.volleydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by javier on 23/01/2017.
 */

public class Intro  extends AppCompatActivity{

    Button btn_concesionario;
    Button btn_vehiculo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        btn_concesionario = (Button) findViewById(R.id.btnConces);
        btn_vehiculo = (Button) findViewById(R.id.btnVehi);



        btn_vehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intro.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
