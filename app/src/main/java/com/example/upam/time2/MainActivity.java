package com.example.upam.time2;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    boolean bandera =false;
    Handler mensaje=new Handler();
    Handler alarma_msj=new Handler();
    int h = 0, m = 0, s = 0,h1=0,m1=0,s1=0;
    String tag = "sms";
    TextView horaText;
    EditText alarma;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        horaText=(TextView) findViewById(R.id.textView);
        alarma=(EditText) findViewById(R.id.editText);
        t1=(TextView) findViewById(R.id.textView3);
        reloj();

    }


    public void reloj () {

        Thread t=new Thread(){
            public void run(){

                Calendar ahora = Calendar.getInstance();
                h = ahora.get(Calendar.HOUR);
                m = ahora.get(Calendar.MINUTE);
                s = ahora.get(Calendar.SECOND);

                while (true) {
                    Log.d(tag, String.valueOf(h) + ":" + String.valueOf(m) + ":" + String.valueOf(s));

                    if(bandera==true)
                    {
                        if(h==h1 && m==m1 && s==s1)
                        {
                            alarma_msj.post(activar_msj);

                        }

                    }

                    mensaje.post(Mostrar_msj);


                    //bandera=false;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s++;
                    if (s == 60) {
                        s = 0;
                        m++;

                    }


                    if (m == 60) {
                        m = 0;
                        h++;
                    }

                }

                // mhandler.post(ejecutar_accion);
            }
        }; t.start();

    }

   final Runnable activar_msj =new Runnable() {
       @Override
       public void run() {
           t1.setTextColor(Color.YELLOW);
           t1.setBackgroundColor(Color.RED);
           ring();

       }
   };


    final Runnable  Mostrar_msj=new Runnable() {
        @Override
        public void run() {
            horaText.setText(String.valueOf(h) + ":" + String.valueOf(m) + ":" + String.valueOf(s));
            // horaText.setText("hola");


        }
    };


    public void ring()
    {
        final ProgressDialog ring=ProgressDialog.show(this,"Espere por favor..","Descargando algo",true);
        ring.setCancelable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ring.dismiss();

            }
        }).start();
    }


    public void activar(View v)
    {
        //bandera para machear


       String m= alarma.getText().toString();
        String m2[]=m.split(":");
         h1=Integer.valueOf(m2[0]);
         m1=Integer.valueOf(m2[1]);
         s1=Integer.valueOf(m2[2]);

        bandera=true;

    }



}
