package com.threebetasonematt.a420game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Tim on 2/19/2017.
 */

public class SocketHandler {
    private static Socket socket;
    private static OutputStreamWriter osw;
    private static PrintWriter pw;
    private static BufferedReader br;


    public static synchronized Socket getSocket(){
        return socket;
    }

    public static synchronized PrintWriter getPW(){
        return pw;
    }
    public static synchronized BufferedReader getBR(){
        return br;
    }

    public static synchronized void setSocket(Socket socket){
        try {
            SocketHandler.socket = socket;
            SocketHandler.osw = new OutputStreamWriter(socket.getOutputStream());
            SocketHandler.pw = new PrintWriter(osw);
            SocketHandler.br = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        }
        catch(Exception e){}
    }
}