import java.io.*;
import java.net.*;

public class Client {

  static ObjectOutputStream objectOut;
  static ObjectInputStream objectIn;

  public static void main(String [] args){
	System.out.println("Client Start");
	if(args.length != 2){
      System.out.println("Usage: arg0 = server IP address");
      System.exit(1);
    }
    try {
      try {
        Socket soc = new Socket(args[0], 4333);
        
        //objectOut = new ObjectOutputStream(soc.getOutputStream());
        
        //objectIn = new ObjectInputStream(soc.getInputStream());
        
        soc.close();
      } catch(ConnectException ce) {
        System.out.println("Failed to connect. Check port compatibility, make sure Server is running");
        ce.printStackTrace();
        System.exit(1);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
