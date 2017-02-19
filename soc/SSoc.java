import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class SSoc{
	private static ServerSocket serverSocket;
	private static Socket[] clientSocket;
	private static BufferedReader[] bufferedReader;
	private static PrintWriter[] pWriter;
	private static String inputLine;
	private static String thisLine;
	private static int port = 4333;
	private static int players = 2;
	private static boolean ready=false;
	private static boolean done=false;
	private static boolean gameOver=false;
	private static String topPlayer = "";
	private static float topScore = (float)Integer.MIN_VALUE;



	public static void main(String[] args){


		clientSocket = new Socket[players];
		bufferedReader = new BufferedReader[players];
		pWriter = new PrintWriter[players];
		int finPlayers=players;
		// Wait for client to connect on 63400
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("pre start");
			for(int i=0;i<players;i++){
				clientSocket[i]= serverSocket.accept();
				bufferedReader[i] = new BufferedReader(new InputStreamReader(clientSocket[i].getInputStream()));
				pWriter[i] = new PrintWriter(new OutputStreamWriter(clientSocket[i].getOutputStream()));
			}
			System.out.println("start");
			while(!gameOver){
				for(int i=0;i<players;i++){
					//START of DB connection
					if(!ready){
						try{
							if(bufferedReader[i].ready())
							while (!ready&&(thisLine = bufferedReader[i].readLine()) != null) {
								if(thisLine.equalsIgnoreCase("ready")){
									ready=true;
									//send back ready
									Thread.sleep(2000);
									for(int j=0; j<players;j++ ){
										pWriter[j].println("ready");
									}

								}
							}
						}
						catch(Exception e){}

					}
					else if(!done){
						try{
							if(bufferedReader[i].ready()){
								while (!done&&(thisLine = bufferedReader[i].readLine()) != null) {
									//System.out.println(thisLine);
									if(thisLine.equalsIgnoreCase("done")){
										System.out.println("done");
										done=true;
										//send back done
										//pWriter[i].println("done");
									}
								}
							}
						}
						catch(Exception e){}
					}
					else{
						try{

							boolean higher=false;
							if(bufferedReader[i].ready())
							while (!gameOver&&(thisLine = bufferedReader[i].readLine()) != null) {
								if(thisLine.matches(".*\\d.*")){
									System.out.println("Score "+thisLine);
									if(Integer.parseInt(thisLine)>topScore){
										higher=true;
										topScore=Integer.parseInt(thisLine);
									}
									finPlayers--;
									System.out.println(finPlayers);
								}
								if(higher){
									topPlayer = thisLine;
								}
							}
						}
						catch(Exception e){}
					}
					if(finPlayers<=0){
						gameOver=true;
					}

				}

			}
			String scoreString = topPlayer + " got "+topScore+" meters high";
			System.out.println(scoreString);
			Thread.sleep(4000);
			for(int i=0;i<players;i++){
				//send top score
				pWriter[i].println(scoreString);
			}
			//push to devices, topPlayer
		}
		catch(Exception ee) {}



	}
}
