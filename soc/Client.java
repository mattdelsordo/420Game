import java.io.*;
import java.net.*;

public class Client {

  static ObjectOutputStream objectOut;
  static ObjectInputStream objectIn;

  public static void main(String [] args){
	System.out.println("Client Start");
    /*if(args.length != 2){
      System.out.println("Usage: arg0 = server IP address");
      System.exit(1);
    }*/
    try {
      try {
      	   Socket soc = new Socket("184.72.127.7", 4333);
		OutputStreamWriter os = new OutputStreamWriter(soc.getOutputStream());
		PrintWriter pw = new PrintWriter(os);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		
		pw.println("ready");
		pw.flush();
		Thread.sleep(1000);
		pw.println("done");
		pw.flush();
		Thread.sleep(1000);
		pw.println("1000\n Bob");
		pw.flush();
		System.out.println("message written to socket");
		
		String readOut = br.readLine();
		System.out.println(readOut);
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
