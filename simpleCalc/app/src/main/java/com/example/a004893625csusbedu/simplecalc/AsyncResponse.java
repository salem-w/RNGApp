package com.example.a004893625csusbedu.simplecalc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public interface AsyncResponse {
    void processFinish(String result);
}