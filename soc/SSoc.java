import java.net.*;
import java.io.*;

public class SSoc{
private static ServerSocket serverSocket;
private static Socket clientSocket;
private static BufferedReader bufferedReader;
private static String inputLine;
private static int port = 4333;

public static void main(String[] args){
    // Wait for client to connect on 63400
    try{
        System.out.println("Starting the socket server at port:" + port);
        serverSocket = new ServerSocket(port);

        System.out.println("Waiting for clients...");
        clientSocket = serverSocket.accept();

        System.out.println("You're now connected to the server.");

        //START of DB connection
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						System.out.println(bufferedReader.readLine());

        } catch (Exception err){

				}
        //END of DB connection

    } catch(Exception e) {
    }
}
}
