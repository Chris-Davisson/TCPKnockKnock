//Christopher Davisson
//10/22/2020
//330 Networking
//Assingment 2

import  java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] argv) throws Exception
    {
        String sentence;
        String responce;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost" , 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        responce = inFromServer.readLine();

        //Loop allows for multiple jokes
        do{
            System.out.println("FROM SERVER: " + responce);
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            responce = inFromServer.readLine();
        }while(!responce.equals("done"));

        clientSocket.close();

    }
}
