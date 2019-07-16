package com.example.a004893625csusbedu.simplecalc;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText etNum1;
    EditText etNum2;

    ImageButton btnAdd;
    ImageButton btnSub;
    ImageButton btnMult;
    ImageButton btnDiv;

    TextView tvResult;

    String oper = "";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the elements
        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnSub = (ImageButton) findViewById(R.id.btnSub);
        btnMult = (ImageButton) findViewById(R.id.btnMult);
        btnDiv = (ImageButton) findViewById(R.id.btnDiv);

        tvResult = (TextView) findViewById(R.id.tvResult);

        // set a listener
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String num1 = "";
        String num2 = "";
        String result = "";

        // check if the fields are empty
        if (TextUtils.isEmpty(etNum1.getText().toString())
                || TextUtils.isEmpty(etNum2.getText().toString())) {
            return;
        }

        // read EditText and fill variables with numbers
        num1 = etNum1.getText().toString();
        num2 = etNum2.getText().toString();

        // defines the button that has been clicked and performs the corresponding operation
        // write operation into oper, we will use it later for output
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                break;
            case R.id.btnSub:
                oper = "-";
                break;
            case R.id.btnMult:
                oper = "*";
                break;
            case R.id.btnDiv:
                oper = "/";
                break;
            default:
                break;
        }
        String message = num1 + oper + num2;
        Client c = new Client(new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                tvResult = (TextView) findViewById(R.id.tvResult);
                String currentText = tvResult.getText().toString();
                tvResult.setText(currentText + result);
            }
        });
        c.execute(message);

        // form the output line
        tvResult.setText(num1 + " " + oper + " " + num2 + " = ");
    }


}