package com.example.disso;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Censor extends AppCompatActivity implements
        SensorEventListener {


    Sensor accelerometer;


    Socket s;
    DataOutputStream dos;
    PrintWriter pw;
    private static String ip = "192.168.0.40";

    float messagex;
    float messagey;
    private String messageX;
    private String messageY;
    private String Testing = "work";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_censor);


        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);







    }


//this part only works in virtual phone

    public void send(String s){

        MyTask my = new MyTask();
        my.execute(messageX);



    }

    class MyTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... voids) {

            try {
                s = new Socket(ip, 1000);
                pw = new PrintWriter(s.getOutputStream());
                pw.write(messageX);
                pw.flush();
                pw.close();
                s.close();

//                s = new Socket(ip, 2002);
//                pw = new PrintWriter(s.getOutputStream());
//                pw.write(messageY);
//                pw.flush();
//                pw.close();
//                s.close();





            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

//practicing to work on IRL phone, only display null

    public void send1(String s){

        MyTask1 my = new MyTask1();
        my.execute(messageY);


    }

    class MyTask1 extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... voids) {

            try {
                s = new Socket(ip,1000);
                pw = new PrintWriter(s.getOutputStream());
                pw.write(messageY);
                pw.flush();
                pw.close();
                s.close();



            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("TAG","onSensorChange X: " +  event.values[0] + " Y: " + event.values[1] );
        messagex = event.values[0];
        messageX = Float.toString(messagex);
        messagey = event.values[1];
        messageY = Float.toString(messagey);

        send(messageX);
//        send1(messageY);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
