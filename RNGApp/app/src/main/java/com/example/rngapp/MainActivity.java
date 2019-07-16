package com.example.rngapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText size;
    private EditText min;
    private EditText max;
    private TextView result;
    private Button submit;
    private Socket s;
    private String response;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        size = findViewById(R.id.numSize);
        min = findViewById(R.id.minRange);
        max = findViewById(R.id.maxRange);
        result = findViewById(R.id.results);
        submit = findViewById(R.id.submit);
        back = findViewById(R.id.back);

        back.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(size.getText().toString())
                || TextUtils.isEmpty(min.getText().toString())
                || TextUtils.isEmpty(max.getText().toString()))
            return;

        switch(v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.submit:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            response = "";
                            final byte[] res = new byte[3000];
                            DatagramSocket ds = new DatagramSocket();
                            InetAddress ia = InetAddress.getByName("192.168.1.23");
                            String message = "Rand " + size.getText() + " " + min.getText() + " " + max.getText();
                            int msglength = message.length();
                            byte[] m = message.getBytes();
                            DatagramPacket dp = new DatagramPacket(m, msglength, ia, 4565);
                            ds.send(dp);

                            DatagramPacket dp1 = new DatagramPacket(res, res.length);
                            ds.close();
                            DatagramSocket ds1 = new DatagramSocket(9004);
                            ds1.receive(dp1);
                            ds1.close();
                            response =  new String(res, 0, dp1.getLength());
                            //s = new Socket("192.168.1.23", 4565);
                            //DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                           // DataInputStream dis = new DataInputStream(s.getInputStream());
                           // dos.writeUTF("Rand " + size.getText() + " " + min.getText() + " "
                          //          + max.getText());
                          //  dos.flush()
                            Log.v("ok", "reached here");
                            //response = dis.readUTF();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    result.setText(response);
                                }
                            });

                            //dis.close();
                            //dos.close();
                            s.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
        }
    }
}
