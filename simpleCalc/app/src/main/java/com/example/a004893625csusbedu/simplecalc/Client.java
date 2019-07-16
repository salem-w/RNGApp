package com.example.a004893625csusbedu.simplecalc;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;

    private static Socket s;
    private static ServerSocket serverSocket;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static DataOutputStream dos;
    private static PrintWriter pw;
    private static String finalResult;

    @Override
    protected String doInBackground(String... voids) {
        final String message = voids[0];
        final byte[] result = new byte[1500];
        String rs = "";

        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress ia = InetAddress.getByName("192.168.1.23");
            int msglength = message.length();
            byte[] m = message.getBytes();
            DatagramPacket dp = new DatagramPacket(m, msglength, ia, 9004);
            ds.send(dp);
            Log.i("Open","opening socket");
            //s = new Socket("192.168.1.23", 6000);
            Log.i("Opened","socked opened");
            //pw = new PrintWriter(s.getOutputStream());
            //pw.write(message);
            //pw.println(message);
            Log.v( "Message", message);
            //pw.flush();
            //pw.close();
            DatagramPacket dp1 = new DatagramPacket(result, result.length);
            ds.close();
            DatagramSocket ds1 = new DatagramSocket(9004);
            ds1.receive(dp1);
            ds1.close();
            rs =  new String(result, 0, dp1.getLength());
            finalResult = rs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public Client(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(finalResult);

    }
}
